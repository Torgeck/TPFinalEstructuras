import java.util.Scanner;
import java.util.regex.Pattern;

public class Utilidades {

    private static final Pattern FORMATO_FECHA = Pattern.compile("(0[1-9]|[12]\\d|30)/(1[012]|0[1-9])/[1-9]\\d{3}");
    private static final Pattern FORMATO_TELEFONO = Pattern.compile("\\d{2,3}\\d{8}");
    private static final Pattern CODIGO_POSTAL = Pattern.compile("[1-9]\\d{3}");

    public int verificarCodigoPostal(Scanner inputUsuario) {
        while (!inputUsuario.hasNext(CODIGO_POSTAL)) {
            System.out.println("Error, codigo postal invalido. Ingrese de codigo postal nuevamente");
            inputUsuario.next();
        }
        return inputUsuario.nextInt();
    }

    public int verificarTelefono(Scanner inputUsuario) {
        // Metodo que verifica que el nro ingresado sea correcto

        System.out.println("Ingrese numero de telefono");
        while (!inputUsuario.hasNext(FORMATO_TELEFONO)) {
            System.out.println("Numero de telefono incorrecto\n Ingrese numero de telefono nuevamente");
            inputUsuario.next();
        }

        return inputUsuario.nextInt();
    }

    public String verificarLetras(Scanner inputUsuario) {
        while (!inputUsuario.hasNext("[a-zA-Z]+")) {
            System.out.println("Error, Ingrese nuevamente ");
            inputUsuario.next();
        }

        return inputUsuario.next();
    }

    public int verificarInts(Scanner inputUsuario) {
        while (!inputUsuario.hasNext("\\d+")) {
            System.out.println("Error, Ingrese nuevamente un numero valido");
            inputUsuario.next();
        }

        return inputUsuario.nextInt();
    }

    public double verificarDouble(Scanner inputUsuario) {
        while (!inputUsuario.hasNextDouble()) {
            System.out.println("Error, Ingrese nuevamente un numero valido");
            inputUsuario.next();
        }

        return inputUsuario.nextDouble();
    }

    public String verificarFecha(Scanner inputUsuario) {
        while (!inputUsuario.hasNext(FORMATO_FECHA)) {
            System.out.println("Error, Ingrese nuevamente la fecha en el formato dd/mm/aaaa");
            inputUsuario.next();
        }

        return inputUsuario.next();
    }
}
