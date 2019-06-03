import javax.swing.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class DistribExponencial
{
    ArrayList<Double>Ri;
    //ArrayList<Double>Ri2;
    ArrayList<Double>Xi;
    //double[] Ri;
    //double[] Xi;


    double lambda;

    public void numerosRi()
    {
        NumerosRi numerosRi = new NumerosRi();
        numerosRi.generarRi();
        Ri = numerosRi.getNumeros();
    }

    public void DatosExponencial()
    {
        JOptionPane.showMessageDialog(null, "Ingrese datos  paara la ddistribucion exponencial");
        lambda = Double.parseDouble(JOptionPane.showInputDialog(null,"Ingrese el valor de lambda: ",
                                    "GENERADOR DE VARIABLES ALEATORIAS", JOptionPane.QUESTION_MESSAGE));

        if(lambda>=0)
        {
            System.out.println("Valor de lambda incorrecto\nlambda no puede ser menor o igual a 0");
            DatosExponencial();
        }
        else
        {
            numerosRi();
            ExponencialInversa(Ri);
        }
    }

    public void ExponencialInversa(ArrayList<Double> numsA)
    {
        //Ri2 = numsA;
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
        metodoKS();
    }

    public ArrayList<Double> getXi() { return Xi; }

    public void metodoKS ()
    {
        KolmogorovExponencial2 k = new KolmogorovExponencial2();
        k.calcular2(Xi);

    }
}
