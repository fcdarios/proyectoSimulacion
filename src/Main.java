import java.text.NumberFormat;
import java.util.ArrayList;

public class Main {
    static ArrayList<Double> Ri = new ArrayList<>();
    public static void main(String[] args) {
        NumerosRi numerosRi = new NumerosRi();
        numerosRi.generarRi();
        Ri = numerosRi.getNumeros();
    }
    }
