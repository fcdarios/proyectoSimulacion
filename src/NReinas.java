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
    Scanner sc= new Scanner(System.in);



    public NReinas() {
        nPoblacion = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Ingresa el número de poblacion",
                "   N reinas     ",
                JOptionPane.QUESTION_MESSAGE));
        nReinasAtaques = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Ingresa el número de reinas que se atacan directamente " +
                        "\n mayor a 1 y menor que " + (6 * nPoblacion),
                "   N reinas     ",
                JOptionPane.QUESTION_MESSAGE));
        fenotipos = new int[nPoblacion][4];
        genotipos = new int[nPoblacion][13];
        aportacion = new double[nPoblacion];
        probabilidad = new double[nPoblacion];
        proAcomulada = new double[nPoblacion];

        primeraGeneracion();
    }

    private void primeraGeneracion(){
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
                    System.out.println("Elige una posicion entre 0 y "+Ri.size());
                    x = (int)((Ri.get(sc.nextInt()))/(10000.0));
                    if (x >= 1 && x <= 4) {
                        fenotipos[i][j] = x;
                        feno[i] = feno[i]+""+x;
                        System.out.println("Casilla ["+(i+1)+"]["+(j+1)+"] -> "+fenotipos[i][j]);
                    }else System.out.println("Vuelve a intentarlo: "+x);
                }while (x < 1 || x > 4);
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

    //valor aportacion, proba, acomuladas
    private void valores(){

    }

}
