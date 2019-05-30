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
public class KolmogorovExponencial2 {
    
    double Sn, Sn2,  Fx, lamda = 0.5555 , media, expo;
    double mas,menos,sumatoriaMas=0,sumatoriaMenos=0,D;
    boolean pasa;
    ArrayList<Double> numAle = new ArrayList<>();
    ArrayList<Double> numSn = new ArrayList<>();
    ArrayList<Double> numFx = new ArrayList<>();
    
    
    ArrayList<Double>nums = new ArrayList<>();
    ArrayList<Double>Dmas = new ArrayList<>();
    ArrayList<Double>Dmenos = new ArrayList<>();
    
    public void calcular(ArrayList<Double>numx){
        numAle = numx;
    }
    
    public void calcular2(){
        
        int n=0, i=1;
         //ordena los valores (primera columna)
        System.out.println("_________________________________");
        System.out.println("ordenar");
        Collections.sort(numAle);
        System.out.println(""+ numAle);
        
        //funcion de distribucion Sn (segunda columna)
        System.out.println("_________________________________");
        int a = 0;
        for (int x=0; x<numAle.size(); x++)
        {
            Sn = (double)++a/(double)numAle.size();
            System.out.println("Sn: " + Sn);
            numSn.add(Sn);
            System.out.println("numeros guardados en Fx: " + numSn);
        }
        
        //Pedirle al usiario lamda
//        Scanner sc= new Scanner(System.in);
//        System.out.println("Ingresar el valor de lamda: ");
//        lamda = sc.nextInt();
        
        //fv (tercera columna)
        System.out.println("_________________________________");
        for (int x=0; x<numAle.size(); x++)
        {
            Fx = 1-Math.exp(-lamda*numAle.get(x)); //numAle son los numeros aleatorios
            System.out.println("Fx: " + Fx);
            numFx.add(Fx);
            
            
//            fx2 = x - 1;
//            System.out.println("fx-1: "+ fx2);
        }
        
        //saca los valores de D+ y D-
        System.out.println("_________________________________");
        int z;
        for (int x=0; x<numAle.size(); x++)
        {
            mas = numSn.get(x) - numFx.get(x);//(cuarta columna)
            System.out.println("Sn(xi)-Fx(xi): "+ mas);
            
            z=x;
            if(z == 0)
            {
                Sn2 = numSn.get(x);                
            }
            else
            {
                Sn2 = numSn.get(x-1);                
            }
            
            System.out.println("Sn-1:" + Sn2);
            
            menos = numFx.get(x) - Sn2;//(quinta columna)
            System.out.println("Fx(xi) - Sn(xi-1): " + menos);
            
            Dmas.add(mas);
            Dmenos.add(menos);    
            
            i++;
            System.out.println("D+: " + Dmas);
            System.out.println("D-: " + Dmenos);
        }
        
        double alto=0,aux;
        for(int y=0;y<Dmas.size();y++)
        {
            aux = Dmas.get(y);
            
            if(aux>alto)
            {
                alto = aux;
                
            }
        }System.out.println("mas: " + alto);
        
        double bajo=0,aux2;
        for(int y=0;y<Dmenos.size();y++)
        {
            aux2 = Dmenos.get(y);
            
            if(aux2>bajo)
            {
                bajo = aux2;
               
            }
        } System.out.println("menos:" + bajo);
        
        //saca la sumatoria de D+ y D-
        System.out.println("_________________________________");
        for (int x=0; x<numAle.size(); x++)
        {
            sumatoriaMas += Dmas.get(x);
            sumatoriaMenos += Dmenos.get(x);
        }System.out.println("sumatoria D+: " +sumatoriaMas );
        System.out.println("sumatoria D-: " + sumatoriaMenos);
        
        D = alto;
            if(D<=1.094)
            {
//                System.out.println("Nivel de significancia (alfa) considerado 5% \n");
//                System.out.println("Valor de D+: "+sumatoriaMas+"\n");
//                System.out.println("Valor de D-: "+sumatoriaMenos+"\n");
                System.out.println("Valor de Dn: "+D+"\n");
                System.out.println("Total de numeros de la muestra: "+ numAle +"\n");
                System.out.println("De acuerdo a la prueba de Kolmogorov Smirnov los numeros generados provienen de una distribución exponencial \n");
                pasa = true;
            }
            else
            {
//                System.out.println("Nivel de significancia (alfa) considerado 5% \n");
//                System.out.println("Valor de D+: "+sumatoriaMas+"\n");
//                System.out.println("Valor de D-: "+sumatoriaMenos+"\n");
                System.out.println("Valor de Dn: "+D+"\n");
                System.out.println("Total de numeros de la muestra: "+ numAle +"\n");
                System.out.println("De acuerdo a la prueba de Kolmogorov Smirnov los numeros generados No provienen de una distribución exponencial \n");
                pasa = true;
            }
            
        
        
        expo = (D - 0.2/numAle.size()) * ( Math.sqrt(numAle.size()) + 0.26 + 0.5/Math.sqrt(numAle.size()));
        System.out.println("Expo: " + expo);
    
    }
}
