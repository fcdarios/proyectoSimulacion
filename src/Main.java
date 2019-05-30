import java.text.NumberFormat;
import java.util.ArrayList;

public class Main {
    static ArrayList<Double> Ri = new ArrayList<>();
    public static void main(String[] args) {
        NumerosRi numerosRi = new NumerosRi();
        numerosRi.generarRi();
        Ri = numerosRi.getNumeros();
        //NReinas nr = new NReinas();
        System.out.println("T: "+Ri.size());
        for (Double d : Ri) {
            double x = (d/100000.0);
            System.out.println(x);
        }

    }
    }
