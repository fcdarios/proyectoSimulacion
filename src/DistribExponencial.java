import javax.swing.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class DistribExponencial
{
    ArrayList<Double>Ri;
    ArrayList<Double>Xi;
    //double[] Ri;
    //double[] Xi;
    double lambda;

    public void DatosExponencial()
    {
        JOptionPane.showMessageDialog(null, "Ingrese datos  paara la ddistribucion exponencial");
        lambda = Double.parseDouble(JOptionPane.showInputDialog(null,"Ingrese el valor de lambda: ",
                                    "GENERADOR DE VARIABLES ALEATORIAS", JOptionPane.QUESTION_MESSAGE));

        if(lambda<=0)
        {
            System.out.println("Valor de lambda incorrecto\nlambda no puede ser menor o igual a 0");
            DatosExponencial();
        }
    }

    public void ExponencialInversa(ArrayList<Double> numsA)
    {
        Ri = numsA;
        Xi = new ArrayList<Double>(numsA.size());

        NumberFormat nf = new DecimalFormat("##.####");

        for (int i = 0; i < Xi.size(); i++)
        {
            //Xi[i] = (1/lambda) * Math.log(Ri[i]) * (-1.0) ;
            //Xi[i] = Double.parseDouble(nf.format(Xi[i]));
            double valorXi = (1/lambda) * Math.log(Ri.get(i)) * (-1.0);

            Xi.set(i, valorXi);
            Xi.set(i, Double.parseDouble(nf.format(Xi.get(i))));
        }
        imprimir(Xi);
    }

    public ArrayList<Double> getXi() { return Xi; }

    public void imprimir(ArrayList<Double> Xs)
    {
        int n=1;
        for(Double d : Xs)
        {
            System.out.println("El valor Xi numero "+n+" es: "+d);
            n++;
        }
    }
}