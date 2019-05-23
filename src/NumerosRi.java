import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class NumerosRi {
    private int iteraciones;
    private double a,c,m,x;
    private ArrayList<Double> numeros = new ArrayList<>();

    void generarRi(){
        int uniformes = 0;
        generarModo();
        if (validarFrecuencia()) uniformes++; else System.out.println("Fallo frecuencia");
        if (validarPromedios(numeros)) uniformes++; else System.out.println("Fallo promedios");
        if (validarIndependencia(numeros)) uniformes++; else System.out.println("Fallo Independecia");

        if (uniformes == 3) System.out.println("Numeros Ri generados correctamente");
        else {
            System.out.println("Numeros Ri NO generados correctamente");
            generarRi();
        }

    }

    private void generarModo()
    {
        int opcion;
        Scanner sc= new Scanner(System.in);

        //JOptionPane.showMessageDialog(null,"");
        opcion = Integer.parseInt(JOptionPane.showInputDialog(  null,"Como generar los numeros\n"
                        + " 1. Automatico \n"
                        + " 2. Manual\n"
                        +"Ingrese una opcion [1 ó 2]",
                "   GENERADOR DE NÚMEROS PSEUDOALEATORIOS     "    ,
                JOptionPane.QUESTION_MESSAGE));
        if (opcion == 2) {
            iteraciones = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Ingrese la cantidad de numeros pseudoaleatorios deseados\n",
                    "   GENERADOR DE NÚMEROS PSEUDOALEATORIOS     ", JOptionPane.QUESTION_MESSAGE));
            x = iteraciones = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "introduzca un número de 3 digitos que sea mayor a " + iteraciones + " para la semilla (Xn)\n",
                    "   GENERADOR DE NÚMEROS PSEUDOALEATORIOS     ", JOptionPane.QUESTION_MESSAGE));
            a = iteraciones = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "introduzca un número que sea mayor a 0 para el multiplicador (a)\n",
                    "   GENERADOR DE NÚMEROS PSEUDOALEATORIOS     ", JOptionPane.QUESTION_MESSAGE));
            c = iteraciones = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "introduzca un número que sea mayor a 0 para la constante aditiva (c)\n",
                    "   GENERADOR DE NÚMEROS PSEUDOALEATORIOS     ", JOptionPane.QUESTION_MESSAGE));
            m = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "introduzca un número de 4 digitos mayor a " + x + " \n" +
                            "para el modulo (el modulo debe ser m > " + x + ", m > " + a + ", m > " + c + ")\n",
                    "   GENERADOR DE NÚMEROS PSEUDOALEATORIOS     ", JOptionPane.QUESTION_MESSAGE));
        }else {
            iteraciones = 200000;
            x = 234560;
            a = 21;
            c = 23;
            m = 300000;
        }
        if(x>0 && a>0 && c>0 && m>x && m>a && m>c) generar();
        else
        {
            System.out.println("El modulo debe ser mayor que a, c y semilla");
            generarModo();
        }
    }

    private void generar()
    {
        double xn=0,n=0;
        Scanner sc= new Scanner(System.in);

        for(int i=0; i<iteraciones; i++)
        {
            xn = (a*x + c) % m;
            x=xn;
            n++;
            numeros.add(xn);
            }
    }

    // Se valida el periodo de repeticion
    private boolean validarFrecuencia() {
        int periodo = 50;
        boolean r = true;
        int p=0;
        int j, l=0;
        for(int i = 0; i < numeros.size(); i++){
            j = i+1;
            while (j < numeros.size()){
                if(i != j && numeros.get(i) == numeros.get(j)){
                    p = j - i;
                    System.out.println(numeros.get(i)+" Se repite en la posicion "+i+" y "+j+" periodo "+p);
                    if(p < periodo) r = true;
                    System.out.println("SI");
                }
                j++;
            }
        }
        return r;
    }

    // Se valida la prueba de promedios con Kolmogorov
    private boolean validarPromedios(ArrayList<Double>nums) {
        boolean valido = false;
        double mas, menos, sumatoriaMas = 0, sumatoriaMenos = 0, D;
        int n = 0, i = 1;
        double val;
        ArrayList<Double> nums2 = new ArrayList<>();
        ArrayList<Double> Dmas = new ArrayList<>();
        ArrayList<Double> Dmenos = new ArrayList<>();

        //añade los valores decimales a otro arraylist
        for (int x = 0; x < nums.size(); x++) {
            val = nums.get(x) / 10000;
            nums2.add(val);
            n++;
        }

        //ordena los valores
        Collections.sort(nums2);

        //saca los valores de D+ y D-
        for (int x = 0; x < nums2.size(); x++) {
            mas = (i / n) - nums2.get(x);
            menos = nums2.get(x) - ((i - 1) / n);

            Dmas.add(mas);
            Dmenos.add(menos);

            i++;
        }

        //saca la sumatoria de D+ y D-
        for (int x = 0; x < nums2.size(); x++) {
            sumatoriaMas += Dmas.get(x);
            sumatoriaMenos += Dmenos.get(x);
        }

        D = 1.36 / Math.sqrt(n);

        if (sumatoriaMas > sumatoriaMenos) {
            if (sumatoriaMas <= D) {
                System.out.println("Nivel de significancia (alfa) considerado 5% \n");
                System.out.println("Valor de D+: " + sumatoriaMas + "\n");
                System.out.println("Valor de D-: " + sumatoriaMenos + "\n");
                System.out.println("Valor de D: " + D + "\n");
                System.out.println("Total de numeros de la muestra: " + n + "\n");
                System.out.println("De acuerdo a la prueba de Kolmogorov Smirnov los numeros generados provienen de una distribución unifirme \n");
                valido = true;
            } else {
                System.out.println("Nivel de significancia (alfa) considerado 5% \n");
                System.out.println("Valor de D+: " + sumatoriaMas + "\n");
                System.out.println("Valor de D-: " + sumatoriaMenos + "\n");
                System.out.println("Valor de D: " + D + "\n");
                System.out.println("Total de numeros de la muestra: " + n + "\n");
                System.out.println("De acuerdo a la prueba de Kolmogorov Smirnov los numeros generados NO provienen de una distribución unifirme \n");
                valido = false;
            }

        } else {
            if (sumatoriaMenos <= D) {
                System.out.println("Nivel de significancia (alfa) considerado 5% \n");
                System.out.println("Valor de D+: " + sumatoriaMas + "\n");
                System.out.println("Valor de D-: " + sumatoriaMenos + "\n");
                System.out.println("Valor de D: " + D + "\n");
                System.out.println("Total de numeros de la muestra: " + n + "\n");
                System.out.println("De acuerdo a la prueba de Kolmogorov Smirnov los numeros generados provienen de una distribución unifirme \n");
                valido = true;
            } else {
                System.out.println("Nivel de significancia (alfa) considerado 5% \n");
                System.out.println("Valor de D+: " + sumatoriaMas + "\n");
                System.out.println("Valor de D-: " + sumatoriaMenos + "\n");
                System.out.println("Valor de D: " + D + "\n");
                System.out.println("Total de numeros de la muestra: " + n + "\n");
                System.out.println("De acuerdo a la prueba de Kolmogorov Smirnov los numeros generados NO provienen de una distribución unifirme \n");
                valido = false;
            }
        }
    return  valido;
    }

    // Se valida la prueba de independencia con Corridas
    private boolean validarIndependencia(ArrayList<Double>nums)
    {
        boolean valido = false;
        int totalN=1,R=0;
        double numsNeg=0,numsPos=0,mediaR,desvEstR,Z,nivelSignificancia=0.005f,valTablaZ=2.58f;
        double suma = 0,media,num;
        boolean contadorR,banderaR=true;

        for(int x=0; x < nums.size()-1; x++)
        {
            suma+=nums.get(x);
            totalN++;
        }

        //suma = suma / 10000;
        media = suma / totalN;

        for(int x=0; x < nums.size()-1; x++)
        {
            num = nums.get(x);

            if(num > media)
            {
                numsPos++;
                contadorR=true;
            }
            else
            {
                numsNeg++;
                contadorR=false;
            }

            if(banderaR != contadorR)
            {
                R++;
                banderaR=contadorR;
            }
        }

        if((nums.get(0) - media) < 0)
        {
            R--;
        }

        mediaR = ((2*numsPos*numsNeg)/(numsPos + numsNeg)) + 1;

        double s = numsPos + numsNeg,potencia = Math.pow(s, 2);

        desvEstR = (float)Math.sqrt(((2*numsPos*numsNeg)*(2*numsPos*numsNeg-numsPos-numsNeg)) / (potencia*(numsPos+numsNeg-1)));

        Z = (R - mediaR) / desvEstR;


        if(Z <= valTablaZ)
        {
            System.out.println("Nivel de significancia (alfa) considerado 5% \n");
            System.out.println("Total de corridas: "+R+"\n");
            System.out.println("Corridas positivas(arriba de la media): "+numsPos+"\n");
            System.out.println("Corridas negativas(debajo de la media): "+numsNeg+"\n");
            System.out.println("Valor esperado de R: "+mediaR+"\n");
            System.out.println("Desviación estandar de la media: "+ desvEstR+"\n");
            System.out.println("Valor de Z de acuerdo al nivel de significancia: "+valTablaZ+"\n");
            System.out.println("Valor de Z obtenido de los calculos con los numeros generados: "+Z+"\n");
            System.out.println("De acuerdo a la prueba de corridas los numeros generados son estadisticamente independientes \n");
            valido = true;
        }
        else
        {
            System.out.println("Nivel de significancia (alfa) considerado 5% \n");
            System.out.println("Total de corridas: "+R+"\n");
            System.out.println("Corridas positivas(arriba de la media): "+numsPos+"\n");
            System.out.println("Corridas negativas(debajo de la media): "+numsNeg+"\n");
            System.out.println("Valor esperado de R: "+mediaR+"\n");
            System.out.println("Desviación estandar de la media: "+ desvEstR+"\n");
            System.out.println("Valor de Z de acuerdo al nivel de significancia: "+valTablaZ+"\n");
            System.out.println("Valor de Z obtenido de los calculos con los numeros generados: "+Z+"\n");
            System.out.println("De acuerdo a la prueba de corridas los numeros generados NO son estadisticamente independientes \n");
            valido = false;
        }
    return valido;
    }
    public ArrayList<Double> getNumeros() {
        return numeros;
    }
}