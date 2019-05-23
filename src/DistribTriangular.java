import javax.swing.*;
import java.util.ArrayList;

public class DistribTriangular
{
    ArrayList<Double> Xi;
    double a = 0, b, c, p, x;
    String func="";

    public void DatosTriangular()
    {
        JOptionPane.showMessageDialog(null, "Ingrese datos  paara la ddistribucion triangular");
        JOptionPane.showMessageDialog(null, "El valor de A es igual a 0.0",
                                        "GENERADOR DE VARIABLES ALEATORIAS", JOptionPane.QUESTION_MESSAGE);
        b = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese el valor de B: ",
                                        "GENERADOR DE VARIABLES ALEATORIAS", JOptionPane.QUESTION_MESSAGE));
        c = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese el valor de C: ",
                                        "GENERADOR DE VARIABLES ALEATORIAS", JOptionPane.QUESTION_MESSAGE));
        p = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese el valor de la probabilidad (altura): ",
                                        "GENERADOR DE VARIABLES ALEATORIAS", JOptionPane.QUESTION_MESSAGE));

        if(b<=a || b>=c)
        {
            System.out.println("Valor de B incorrecto\nB no puede ser menor a A ni mayor C");
            DatosTriangular();
        }
        else if(c<=b)
        {
            System.out.println("Valor de C incorrecto\nC no puede ser menor a B");
            DatosTriangular();
        }
        else if(p<0 || p>1)
        {
            System.out.println("Valor de probabilidad incorrecto\nDebe ser un valor entre 0 y 1");
            DatosTriangular();
        }
    }

    public void TriangularInversa(ArrayList<Double> Ri)
    {
        Xi = new ArrayList<>();
        rango r1 = new rango();
        rango r2 = new rango();

        x = a;
        r1.setrIzq((p * Math.pow(x, 2)) / 2*b );
        x = b;
        r1.setrDer( (p * Math.pow(x, 2)) / 2*b );
        r2.setrIzq((p * ((2 * x * c) - Math.pow(x, 2))) / (c-b));
        x = c;
        r2.setrDer((p * ((2 * x * c) - Math.pow(x, 2))) / (c-b));

        for (Double R: Ri){
            if(R >= r1.getrIzq() && R < r1.getrDer())
            {
                Xi.add(Math.sqrt((2 * b * R) / p));
                func = "Funcion 1";
            }
            else if(R >= r2.getrIzq() && R < r2.getrDer())
            {
                Xi.add(((c * p) + Math.sqrt(p * ((p * Math.pow(c, 2)) - (2 * c * R) + (2 * b * R)))) / p);
                func = "Funcion 2";
            }
            else
            {
                System.out.println("valor fuera de rango " + R);
            }
        }
        imprimir(Xi);
    }

    public void imprimir(ArrayList<Double> Xs)
    {
        int n = 1;
        for(Double d : Xs)
        {
            System.out.println("El valor Xi numero "+n+" es: "+d);
            n++;
        }
    }

    public ArrayList<Double> getXi()
    {
        return Xi;
    }

    class rango
    {
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
}
