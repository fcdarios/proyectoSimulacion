/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author chayi
 */
public class KolmogorovExponencial {
    
    double Sn, Sn2,  Fx, lamda, media, expo;
    double mas,menos,sumatoriaMas=0,sumatoriaMenos=0,D;


        ArrayList<Double> Xi = new ArrayList<>();
    
    public void calcular(ArrayList<Double> Xi){
        
        Scanner sc= new Scanner(System.in);
        
        // usar la clase variablesAletorias
//        VariablesAleatorias variablesAleatorias = new VariablesAleatorias();
//
//        ArrayList<Double> Xi = new ArrayList<>();

        System.out.println(Xi);
        
        int n=0, i=1;
        double val;
        ArrayList<Double>nums = new ArrayList<>();
        ArrayList<Double>Dmas = new ArrayList<>();
        ArrayList<Double>Dmenos = new ArrayList<>();
        
        //añade los valores decimales a otro arraylist
        for (int x=0; x<Xi.size(); x++) 
        {
            val = Xi.get(x) / 10000;
            nums.add(val);
            n++;
        } 
        
        //ordena los valores (primera columna)
        Collections.sort(nums);
                
        //funcion de distribucion Sn (segunda columna)
        int a = 0;
        for (int x=0; x<nums.size(); x++)
        {
            Sn = ++a/nums.size();
            System.out.println("Sn: " + Sn);
        }
        
        //Pedirle al usiario lamda
        System.out.println("Ingresar el valor de lamda: ");
        lamda = sc.nextInt();
        
        
        //Zi (tercera columna)
        for (int x=0; x<nums.size(); x++)
        {
            Fx = 1-Math.exp(-lamda*Xi.get(x)); //xi son los numeros aleatorios
            System.out.println("Fx: " + Fx);
        }
        
        //saca los valores de D+ y D-
        for (int x=0; x<nums.size(); x++)
        {
            mas = Sn - Fx;//(cuarta columna)
            menos = Fx - Sn2;//(quinta columna)
            
            Dmas.add(mas);
            Dmenos.add(menos);    
            
            i++;
        }
        
        //saca la sumatoria de D+ y D-
        for (int x=0; x<nums.size(); x++)
        {
            sumatoriaMas += Dmas.get(x);
            sumatoriaMenos += Dmenos.get(x);
        }
        
        D =1.36/ Math.sqrt(n);
        
        if(sumatoriaMas > sumatoriaMenos)
        {
            if(sumatoriaMas<=D)
            {
                System.out.println("Nivel de significancia (alfa) considerado 5% \n");
                System.out.println("Valor de D+: "+sumatoriaMas+"\n");
                System.out.println("Valor de D-: "+sumatoriaMenos+"\n");
                System.out.println("Valor de D: "+D+"\n");
                System.out.println("Total de numeros de la muestra: "+ n+"\n");
                System.out.println("De acuerdo a la prueba de Kolmogorov Smirnov los numeros generados provienen de una distribución unifirme \n");       
            }
            else
            {
                System.out.println("Nivel de significancia (alfa) considerado 5% \n");
                System.out.println("Valor de D+: "+sumatoriaMas+"\n");
                System.out.println("Valor de D-: "+sumatoriaMenos+"\n");
                System.out.println("Valor de D: "+D+"\n");
                System.out.println("Total de numeros de la muestra: "+ n+"\n");
                System.out.println("De acuerdo a la prueba de Kolmogorov Smirnov los numeros generados NO provienen de una distribución unifirme \n");
            }
            
        }
        else
        {
            if(sumatoriaMenos<=D)
            {
                System.out.println("Nivel de significancia (alfa) considerado 5% \n");
                System.out.println("Valor de D+: "+sumatoriaMas+"\n");
                System.out.println("Valor de D-: "+sumatoriaMenos+"\n");
                System.out.println("Valor de D: "+D+"\n");
                System.out.println("Total de numeros de la muestra: "+ n+"\n");
                System.out.println("De acuerdo a la prueba de Kolmogorov Smirnov los numeros generados provienen de una distribución unifirme \n");       
            }
            else
            {
                System.out.println("Nivel de significancia (alfa) considerado 5% \n");
                System.out.println("Valor de D+: "+sumatoriaMas+"\n");
                System.out.println("Valor de D-: "+sumatoriaMenos+"\n");
                System.out.println("Valor de D: "+D+"\n");
                System.out.println("Total de numeros de la muestra: "+ n+"\n");
                System.out.println("De acuerdo a la prueba de Kolmogorov Smirnov los numeros generados NO provienen de una distribución unifirme \n");
            }
            
        }
        
        expo = (mas - 0.2/nums.size()) * ( Math.sqrt(nums.size()) + 0.26 + 0.5/Math.sqrt(nums.size()));
        System.out.println("Expo: " + expo);
        
    }
}
