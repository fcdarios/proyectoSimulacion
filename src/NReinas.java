import javax.swing.*;
import java.util.ArrayList;
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

    private void evaluacion(int[][] fTipos, int[][] gTipos){



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
        int[][] fenotiposTemp;
        int[][] genotiposTemp;
        int x, numIndividuo;
        Rango[] clases = new Rango[nPoblacion];

        // Sacar los rangos para las clases
        clases[0].setrIzq(0);
        for (int i = 0; i < nPoblacion; i++) {
            clases[i].setrDer(proAcomulada[i]);
            if (i < nPoblacion)
            clases[i+1].setrIzq(proAcomulada[i]);
        }

        for (int i = 0; i < nPoblacion; i++) {
            do {
                System.out.println("Elige una posicion entre 0 y "+Ri.size());
                if (nPoblacion < 10) x = (int)((Ri.get(sc.nextInt()))/(10000.0));
                else x = (int)((Ri.get(sc.nextInt()))/(1000.0));
                for (int j = 0; j < clases.length ; j++) {
                    if (x >= clases[j].getrIzq() || x < clases[j].getrDer() ){
                        numIndividuo = j;
                    }
                }
        ////////////////  AQUI VOY
            }while (x < 0 || x > nPoblacion);
        }
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
