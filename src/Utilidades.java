import java.util.Scanner;
import java.util.regex.Pattern;

import estructuras.lineales.Par;

public final class Utilidades {

    // * Patrones de expresiones regulares
    private static final Pattern FORMATO_FECHA = Pattern.compile("(0[1-9]|[12]\\d|30)/(1[012]|0[1-9])/[1-9]\\d{3}");
    private static final Pattern FORMATO_EMAIL = Pattern.compile("(\\w+@\\w+)([.]\\w+)+");
    private static final Pattern FORMATO_TELEFONO = Pattern.compile("\\d{2,3}\\d{8}");
    private static final Pattern CODIGO_POSTAL = Pattern.compile("[1-9]\\d{3}");
    private static final Pattern SOLO_LETRAS = Pattern.compile("[a-zA-Z]+");

    // * Mensajes de error
    private static final String ERROR_CODIGO_INVALIDO = "ERROR, codigo postal ingresado invalido";

    // * Constantes
    private static final int LONGITUD_CODIGO_POSTAL = 4;

    // TODO hacer el resto de mensajes de errores

    // * Verificadores de input
    public static int verificarCodigoPostal(String codigo, Scanner inputUsuario) {
        // Metodo que verifica que el codigo postal tenga el formato correcto
        int resultado;

        if (!CODIGO_POSTAL.matcher(codigo).matches()) {
            System.out.println(ERROR_CODIGO_INVALIDO + "Ingrese codigo postal de la ciudad");
            while (!inputUsuario.hasNext(CODIGO_POSTAL)) {
                System.out.println(
                        ERROR_CODIGO_INVALIDO + "\n Ingrese nuevamente un codigo postal de 4 digitos numericos");
                inputUsuario.next();
            }
            resultado = inputUsuario.nextInt();
        } else {
            resultado = Integer.parseInt(codigo);
        }
        return resultado;
    }

    public static int verificarTelefono(String telefono, Scanner inputUsuario) {
        // Metodo que verifica que el nro ingresado sea correcto
        int tel;

        if (!FORMATO_TELEFONO.matcher(telefono).matches()) {
            System.out.println("Ingrese numero de telefono");
            while (!inputUsuario.hasNext(FORMATO_TELEFONO)) {
                System.out.println("Error, numero de telefono incorrecto.\n Ingrese numero de telefono nuevamente");
                inputUsuario.next();
            }
            tel = inputUsuario.nextInt();
        } else {
            tel = Integer.parseInt(telefono);
        }

        return tel;
    }

    public static String verificarLetras(String letras, Scanner inputUsuario) {
        // Metodo que verifica que se hayan ingresado caracteres alfabeticos

        if (!SOLO_LETRAS.matcher(letras).matches()) {
            System.out.println("Ingrese una cadena que contenga solamente letras");
            while (!inputUsuario.hasNext(SOLO_LETRAS)) {
                System.out.println("Error, Ingrese nuevamente ");
                inputUsuario.next();
            }
            letras = inputUsuario.next();
        }

        return letras.toUpperCase();
    }

    public static String verificarEmail(String email, Scanner inputUsuario) {
        // Metodo que verifica que se haya ingresado el email en el formato correcto
        if (!FORMATO_EMAIL.matcher(email).matches()) {
            System.out.println("Ingrese un email");
            while (!inputUsuario.hasNext(FORMATO_EMAIL)) {
                System.out.println("Error, Ingrese email nuevamente ");
                inputUsuario.next();
            }
            email = inputUsuario.next();
        }

        return email.toUpperCase();
    }

    public static int verificarInts(String numeros, String objeto, Scanner inputUsuario) {
        // Metodo que verifica que se hayan ingresado numeros enteros
        int resultado;

        if (!numeros.matches("\\d+")) {
            System.out.println("Ingrese un " + objeto);
            while (!inputUsuario.hasNext("\\d+")) {
                System.out.println("Error, Ingrese nuevamente un " + objeto + " valido");
                inputUsuario.next();
            }
            resultado = inputUsuario.nextInt();
        } else {
            resultado = Integer.parseInt(numeros);
        }
        return resultado;
    }

    public static double verificarDouble(String objeto, Scanner inputUsuario) {
        // Metodo que verifica que se hayan ingresado numeros reales
        double resultado;

        if (!objeto.matches("\\d+.\\d*")) {
            while (!inputUsuario.hasNextDouble()) {
                System.out.println("Error, Ingrese nuevamente un " + objeto + " valido");
                inputUsuario.next();
            }
            resultado = inputUsuario.nextDouble();
        } else {
            resultado = Double.parseDouble(objeto);
        }

        return resultado;
    }

    public static String verificarFecha(String fecha, Scanner inputUsuario) {
        // Metodo que verifica que se haya ingresado una fecha en el formato correcto

        if (!FORMATO_FECHA.matcher(fecha).matches()) {
            System.out.println("Ingrese fecha en formato dd/mm/aaaa");
            while (!inputUsuario.hasNext(FORMATO_FECHA)) {
                System.out.println("Error, Ingrese nuevamente la fecha en el formato dd/mm/aaaa");
                inputUsuario.next();
            }
            fecha = inputUsuario.next();
        }
        return fecha;
    }

    public static int verificarPrefijo(String prefijo, Scanner inputUsuario) {
        // Metodo que verifica si el prefijo ingresado es correcto, si no pide al
        // usuario ingresarlo de nuevo
        int resultado;
        String formatoPrefijo = "0*\\d{0,4}";

        if (!prefijo.matches(formatoPrefijo)) {
            System.out.println("Prefijo invalido, ingrese un prefijo valido");
            while (!inputUsuario.hasNext(formatoPrefijo)) {
                System.out.println("ERROR, prefijo invalido");
                inputUsuario.next();
            }
            resultado = inputUsuario.nextInt();
        } else {
            resultado = Integer.parseInt(prefijo);
        }
        return resultado;
    }

    public static Par verificarClaveCliente(String[] clave, Scanner inputUsuario) {
        // Metodo que verifica si al clave ingresada es valida y retorna un par con la
        // misma
        Par parClave = new Par();

        while (clave.length != 2) {
            System.out.println("Ingresar nuevamente tipo y numero de documento separado por -");
            clave = inputUsuario.nextLine().split("-");
        }

        parClave.setA(verificarLetras(clave[0], inputUsuario).toUpperCase());
        parClave.setB(verificarInts(clave[1], "numero de documento", inputUsuario));

        return parClave;
    }

    public static int[] toIntArray(String[] arrString, int cantElementos, Scanner input) {
        // Metodo que convierte un arreglo(con codigos postales) de strings a ints
        int i = 0;
        int[] arrInts = new int[cantElementos];

        while (i < cantElementos) {

            try {
                arrInts[i] = verificarCodigoPostal(arrString[i], input);
            } catch (ArrayIndexOutOfBoundsException e) {
                arrInts[i] = verificarCodigoPostal("", input);
            }
            i++;
        }

        return arrInts;
    }

    public static Par obtenerRango(int prefijo) {
        // Metodo que analiza si es un string y devuelve un rango en forma de par o tira
        // una excepcion en caso de no serlo
        Par rango = new Par();
        int n, limiteInferior;

        n = LONGITUD_CODIGO_POSTAL - obtenerLongitudInt(prefijo);
        limiteInferior = (int) (prefijo * Math.pow(10, n));

        rango.setA(limiteInferior);
        rango.setB(obtenerLimiteSuperior(limiteInferior, n));

        return rango;
    }

    public static int obtenerLongitudInt(int numero) {
        // Metodo que obtine la cantidad de digitos de un numero
        return (int) Math.ceil(Math.log10(numero + 1));
    }

    public static int obtenerLimiteSuperior(int numero, int digitosFaltantes) {
        return numero + (9 * obtenerMax(digitosFaltantes));
    }

    public static int obtenerMax(int n) {
        int i;
        // Caso base
        if (n <= 1) {
            i = (n == 0) ? 0 : 1;
        } else {
            i = obtenerMax(n - 1) + (int) Math.pow(10, n - 1);
        }
        return i;
    }

}
