import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;


public class Provisional
{

    ArrayList<Double> Ri; // ya esta declarado en la clase nreinas

    public double numAleatorio()
    {
        NumerosRi numerosRi = new NumerosRi();
        numerosRi.generarRi();
        Ri = numerosRi.getNumeros();

        Calendar calendario = Calendar.getInstance();

        //int hora = calendario.get(Calendar.HOUR_OF_DAY);
        //int minutos = calendario.get(Calendar.MINUTE);
        int segundos = calendario.get(Calendar.SECOND);
        int milisegundos = calendario.get(Calendar.MILLISECOND);
        int posicion, numero, totalRi = Ri.size();
        double numeroA;

        do
        {
            posicion = segundos * milisegundos;
        }
        while(posicion < totalRi);

        numeroA = (Ri.get(posicion))/100000;
        return numeroA;
    }

    private double[][] mutacion(double[][] matrizInicial)
    {

        int mutaciones=0;
        double gen, probaMutacion,numAleatorio;
        //String str,aux;
        double[][] matrizMutada = matrizInicial.clone();

        //usuario designa la probabilidad de mutacion
        do
        {
            probaMutacion = Double.parseDouble(JOptionPane.showInputDialog(null,
                    "Ingrese probabilidad de mutación(se recomienda valor muy pequeño)"));

            if(probaMutacion<0)
            {
                JOptionPane.showMessageDialog(null,"El valor de la probabilidad debe estar entre 0 y 100");
            }
        }
        while(probaMutacion<0);

        //obtener cada posicion del arreglo que contiene los genotipos
        for (int i=0; i < matrizMutada.length; i++)
        {
            for(int j=0; j < matrizMutada[i].length; j++)
            {
                //asignar valor al cromosoma
                gen = matrizMutada[i][j];
                //cromosoma = matrizMutada[i][j];
                //str = String.valueOf(cromosoma);

                //tomar cada gen de cada cromosoma para ver si muta
                //for(int x=0; x < str.length(); x++)
                //{
                //aux = str.substring(x,x+1);

                numAleatorio=numAleatorio();

                //cambiar valor del gen que muta
                if(numAleatorio<= probaMutacion)
                {
                    if(gen == 1)
                    {
                        gen = 0;
                    }
                    else
                    {
                        gen=0;
                    }

                    //remplazar gen mutado en el cromosoma correspondiente
                    matrizMutada[i][j] = gen;
                    mutaciones++;
                }
                //}
            }
        }
        return matrizMutada;
    }


    private void cruce(double[][] matrizInicial)
    {
        String padre1="",padre2="";
        int contador1=0,contador2=0;

        //obtener padre1
        for(int x = 0; x<(matrizInicial.length-2); x+=2)
        {
            for(int y=0; y < (matrizInicial[x].length-2); y+=2)
            {
                padre1=String.valueOf(matrizInicial[x][y]);
            }
            contador1++;
        }

        //obtener padre2
        for(int i = 0; i<(matrizInicial.length-2); i+=2)
        {
            for(int j=0; j < (matrizInicial[i].length-2); j+=2)
            {
                padre2=String.valueOf(matrizInicial[i][j]);
            }
            contador2++;
        }

        //si queda padre1 solo al final se asigna a padre2 el valor de la primer posicion del arreglo
        if(contador1 != contador2)
        {
            for(int x = 0; x < 1; x++)
            {
                for(int y=0; y < 1; y++)
                {
                    padre2 = String.valueOf(matrizInicial[x][y]);
                }
            }
        }
        else
        {
            //CODIGO PARA QUE SEPARE Y CRUCE :V
        }
    }

}
