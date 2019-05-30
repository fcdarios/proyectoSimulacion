import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;


public class NReinas {
    ArrayList<Double> Ri;
    ArrayList<Double> XiDistribucion1;
    ArrayList<Double> XiDistribucion2;
    int nPoblacion;
    int  nReinasAtaques;
    int[][] fenotipos;
    int[][] genotipos;
    double[] aportacion;
    double aportacionTotal;
    double[] probabilidad;
    double[] proAcomulada;
    int nPosicion = 0;
    Scanner sc= new Scanner(System.in);

    // 1 Inicio del programa
    public NReinas() {

        nPoblacion = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Ingresa el número de poblacion",
                "   N reinas     ",
                JOptionPane.QUESTION_MESSAGE));
        nReinasAtaques = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Ingresa el número maximo de ataques de reinas que se atacan directamente " +
                        "\n mayor a 1 y menor que " + (6 * nPoblacion),
                "   N reinas     ",
                JOptionPane.QUESTION_MESSAGE));

        // declaracion de los atributos
        fenotipos = new int[nPoblacion][4];
        genotipos = new int[nPoblacion][13];
        aportacion = new double[nPoblacion];
        probabilidad = new double[nPoblacion];
        proAcomulada = new double[nPoblacion];

        // Se genera la primera generacion de los individuos
        primeraGeneracion();
    }

    private void principal(){
        do {
            valores();
            ordenarClases(fenotipos, genotipos);
            seleccionPadres(genotipos);
            genotipos = mutacion(genotipos);
            fenotipos = genTofen(genotipos);
        }while (evaluacion(fenotipos));
    }
    private int[][] genTofen(int[][] gentotipos){
        String numero="";
        int[][] fenotiposT =new int[gentotipos.length][4];
        for (int i = 0; i < genotipos.length; i++) {
            for (int j = 0; j < genotipos[0].length; j++) {
                numero += ""+gentotipos[i][j];
            }
            fenotiposT[i] = convertirBinarioADecimalManual(numero);
        }
        return fenotiposT;
    }

    private boolean evaluacion(int[][] feno){
        boolean repite = false;
        int sumaR = 0;
        for (int i = 0; i < feno.length; i++) {
            sumaR+= ataquesReinas(feno[i]);
            mostrarTablero(feno[i]);
        }
        if (sumaR >= nReinasAtaques) repite = false;
        else repite = true;


        return repite;
    }

    private void primeraGeneracion(){
        // Se generan numeros aleatorios uniformes
        NumerosRi numerosRi = new NumerosRi();
        numerosRi.generarRi();
        Ri = numerosRi.getNumeros();
        int x;
        String[] feno = new String[fenotipos.length];
        String[] geno = new String[fenotipos.length];

        // sacar primera generacion de fenotipos
        for (int i = 0; i < fenotipos.length ; i++) {
            feno[i] = "";
            for (int j = 0; j < fenotipos[0].length; j++) {
                do {
                    // Se elige un numero aleatorio en base a su posicion
                    System.out.println("Elige una posicion entre 0 y "+Ri.size());
                    x = (int)((Ri.get(sc.nextInt()))/(10000.0));
                    // Si el numero cae en el rango de 0 a 3 se le asigna al fenotipo
                    if (x >= 0 && x <= 3) {
                        fenotipos[i][j] = x;
                        feno[i] = feno[i]+""+x;
                        System.out.println("Casilla ["+(i+1)+"]["+(j+1)+"] -> "+fenotipos[i][j]);
                        nPosicion++;
                    }else System.out.println("Vuelve a intentarlo: "+x);
                }while (x < 0 || x > 3);
            }
        }

        // pasar primera generacion de fenotipos  a genotipos
        for (int i = 0; i < genotipos.length ; i++) {
            geno[i] = obtenerBinario(Integer.parseInt(feno[i]));
            for (int j = 12; j >= 0; j--) {
                genotipos[i][j] = Integer.parseInt(""+geno[i].charAt(j));
            }
        }
    }
    //valor aportacion, proba, acomuladas
    private void valores(){
        String fe;
        int f;
        aportacionTotal = 0;
        for (int i = 0; i < nPoblacion ; i++) {
            fe = fenotipos[i][0]+""+fenotipos[i][1]+""+fenotipos[i][2]+""+fenotipos[i][3];
            f = Integer.parseInt(fe);
            aportacion[i] = (3333*f) - Math.pow(f,2);
            aportacionTotal += aportacion[i];
        }
        for (int i = 0; i < nPoblacion ; i++) {
            probabilidad[i] = aportacion[i] / aportacionTotal;
            if (i == 0) proAcomulada[i] = probabilidad[i];
            else proAcomulada[i] = proAcomulada[i-1] + probabilidad[i];
        }
    }

    // paso 8
    private void ordenarClases(int[][] fenotipos, int[][] genotipos) {
        int[][] fenotiposTemp = new int[fenotipos.length][fenotipos[0].length];
        int[][] genotiposTemp = new int[genotipos.length][genotipos[0].length];
        double x;
        int numIndividuo = 0;
        Rango[] clases = new Rango[nPoblacion];

        // Sacar los rangos para las clases
        clases[0].setrIzq(0);
        for (int i = 0; i < nPoblacion; i++) {
            clases[i].setrDer(proAcomulada[i]);
            if (i < nPoblacion)
            clases[i+1].setrIzq(proAcomulada[i]);
        }

        for (int i = 0; i < nPoblacion; i++) {
                System.out.println("Elige una posicion entre 0 y "+Ri.size());
                // Montecarlo
                x = (Ri.get(sc.nextInt()))/(100000.0);
                for (int j = 0; j < clases.length ; j++) {
                    if (x >= clases[j].getrIzq() || x < clases[j].getrDer() ){
                        numIndividuo = j;
                    }
                }
            for (int j = 0; j < fenotipos[0].length ; j++) {
                fenotiposTemp[i][j] = fenotipos[numIndividuo][j];
            }
            for (int j = 0; j < fenotipos[0].length ; j++) {
                genotiposTemp[i][j] = genotipos[numIndividuo][j];
            }
        ////////////////  AQUI VOY
        }
        this.fenotipos = fenotipos;
        this.genotipos = genotipos;
    }

    class Rango{
        double rIzq, rDer;
        public double getrIzq() {
            return rIzq;
        }
        public void setrIzq(double rIzq) {
            this.rIzq = rIzq;
        }
        public double getrDer() {
            return rDer;
        }
        public void setrDer(double rDer) {
            this.rDer = rDer;
        }
    }

    // Cruce
    private void seleccionPadres(int[][] genotipos) {
        int [][] genotiposPadres = new int[2][genotipos[0].length];
        int [][] genotiposPadresT;
        int [][] genotiposTemp = new int[nPoblacion][genotipos[0].length];
        int n = 0;
        boolean impar = false;
       if (nPoblacion % 2 == 0)impar = true;
        for (int i = 0; i < genotipos.length; i=i+2) {
            genotiposPadres[0] = genotipos[i];
            if (impar && i == genotipos.length-1)
                genotiposPadres[1] = genotipos[0];
            else genotiposPadres[1] = genotipos[i+1];
            genotiposPadresT = Cruce(genotiposPadres);
            genotiposTemp[i] = genotiposPadresT[0];
            genotiposTemp[i+1] = genotiposPadresT[1];
        }
        this.genotipos = genotiposTemp;
        }
    // Cruce
    private int[][] Cruce(int[][] padres) {
        int[][] genotiposPadresT = new int[2][padres[0].length];
        int n = 0;
        do {
            n = (int)(numAleatorio()/1000);
        }while (n < 1 || n > padres[0].length-1);

        for (int i = 0; i < n; i++) {
            genotiposPadresT[0][i] = padres[0][i];
            genotiposPadresT[1][i] = padres[1][i];
        }
        for (int i = n; i < padres[0].length; i++) {
            genotiposPadresT[0][i] = padres[0][i];
            genotiposPadresT[1][i] = padres[1][i];
        }
        return genotiposPadresT;
    }

    private int[][] mutacion(int[][] matrizInicial)
    {
        int mutaciones=0;
        double  probaMutacion,numAleatorio;
        int gen;
        int[][] matrizMutada = matrizInicial.clone();
        //usuario designa la probabilidad de mutacion
        do {
            probaMutacion = Double.parseDouble(JOptionPane.showInputDialog(null,
                    "Ingrese probabilidad de mutación(se recomienda valor muy pequeño)"));
            if(probaMutacion<0) {
                JOptionPane.showMessageDialog(null,"El valor de la probabilidad debe estar entre 0 y 100");
            }
        }while(probaMutacion<0);

        //obtener cada posicion del arreglo que contiene los genotipos
        for (int i=0; i < matrizMutada.length; i++) {
            for(int j=0; j < matrizMutada[i].length; j++) {
                gen = matrizMutada[i][j];
                numAleatorio=numAleatorio()/100000.0;
                //cambiar valor del gen que muta
                if(numAleatorio<= probaMutacion) {
                    if(gen == 1) gen = 0;
                    else gen=0;

                    //remplazar gen mutado en el cromosoma correspondiente
                    matrizMutada[i][j] = gen;
                    mutaciones++;
                }
            }
        }
        return matrizMutada;
    }

    public double numAleatorio(){
        Calendar calendario = Calendar.getInstance();
        int segundos = calendario.get(Calendar.SECOND);
        int milisegundos = calendario.get(Calendar.MILLISECOND);
        int posicion, numero, totalRi = Ri.size();
        double numeroA;

        do
        {
            posicion = segundos * milisegundos;
        }
        while(posicion < totalRi);

        numeroA = (Ri.get(posicion));
        return numeroA;
    }


    // pasar a binario un numero decimal
    public static String obtenerBinario(int numero){
        String binario = "";
        if (numero > 0) {
            while (numero > 0) {
                if (numero % 2 == 0) {
                    binario = "0" + binario;
                } else {
                    binario = "1" + binario;
                }
                numero = (int) numero / 2;
            }
        } else if (numero == 0) {
            binario = "0";
        } else {
            binario = "No se pudo convertir el numero. Ingrese solo números positivos";
        }

        int longitud = 13 - binario.length();
        if (longitud != 0)
            for (int i = 0; i < longitud; i++) {
                binario = 0+""+binario;
            }
        return binario;
    }

    public int[] convertirBinarioADecimalManual(String binario) {
        // A este número le vamos a sumar cada valor binario
        double decimal = 0;
        int posicion = 0;
        // Recorrer la cadena...
        for (int x = binario.length() - 1; x >= 0; x--) {
            // Saber si es 1 o 0; primero asumimos que es 1 y abajo comprobamos
            short digito = 1;
            if (binario.charAt(x) == '0') {
                digito = 0;
            }

      /*
          Se multiplica el dígito por 2 elevado a la potencia
          según la posición; comenzando en 0, luego 1 y así
          sucesivamente
       */
            double multiplicador = Math.pow(2, posicion);
            decimal += digito * multiplicador;
            posicion++;
        }
        int[] fenotipo = new int[4];
        String de = decimal+"";
        int n;
        for (int i = 0; i < de.length(); i++) {
            n = (int)de.charAt(i);
            if (n > 3 && n <= 5) n = 0;
            else if (n > 5 && n <= 7) n = 1;
            else if (n > 7 && n <= 8) n = 2;
            else if (n > 8 && n <= 9) n = 3;
            fenotipo[i] = n;
        }
        return fenotipo;
    }


// calcular el numero que se atacan entre reinas de un tablero
    private int ataquesReinas(int[] tablero){
        int ataques = 0;
        int n;
        int reina1, reina2;

        for (int i = 0; i < tablero.length ; i++) {
            reina1 = tablero[i];
            n = 0;
            for (int j = i+1; j < tablero.length ; j++) {
                n++;
                reina2 = tablero[j];
                if (reina1 == reina2 || (reina1-n) == reina2 || (reina1+n) == reina2){
                    ataques++;
                }
            }
        }
        return ataques;
    }

    private void mostrarTablero(int[] tablero){
        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < 4; j++) {
                if ((i+1) == tablero[j]) System.out.print("  R");
                else System.out.print("  ▄");
            }
            System.out.println();
        }
    }
    
}
