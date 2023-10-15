import java.util.Scanner;
import java.util.regex.Pattern;

import estructuras.lineales.Par;

public class Verificador {

    // * Clase que se encarga de verificar que un string cumpla con una expresion
    // * regular dada

    private static final Pattern FORMATO_FECHA = Pattern.compile("(0[1-9]|[12]\\d|30)/(1[012]|0[1-9])/[1-9]\\d{3}");
    private static final Pattern FORMATO_EMAIL = Pattern.compile("(\\w+@\\w+)([.]\\w+)+");
    private static final Pattern FORMATO_TELEFONO = Pattern.compile("[1-9]\\d{1,3}-\\d{8}");
    private static final Pattern FORMATO_DOCUMENTO = Pattern.compile("[1-9]\\d{8}+");
    private static final Pattern FORMATO_PREFIJO = Pattern.compile("0*\\d{0,4}");
    private static final Pattern FORMATO_CLAVE_CLIENTE = Pattern.compile("[a-zA-Z]{3-4}[1-9]\\\\d{8}+");
    private static final Pattern CODIGO_POSTAL = Pattern.compile("[1-9]\\d{3}");
    private static final Pattern DIRECCION = Pattern.compile("([a-zA-Z]+)(\s[a-zA-Z]*)*(\s[1-9]\\d*)");
    private static final Pattern SOLO_LETRAS = Pattern.compile("[a-zA-Z]+");
    private static final Pattern SOLO_NUMEROS = Pattern.compile("\\d+");
    private static final Pattern NUMERO_REAL = Pattern.compile("\\d+(\\.\\d*)");
    private static final Pattern BOOLEAN = Pattern.compile("T|F");

    // * Mensajes de error
    private static final String ERROR_CODIGO_INVALIDO = "ERROR, codigo postal ingresado invalido";

    // * Verificadores basicos
    public static boolean esPalabra(String palabra) {
        return SOLO_LETRAS.matcher(palabra).matches();
    }

    public static boolean esTelefono(String telefono) {
        return FORMATO_TELEFONO.matcher(telefono).matches();
    }

    public static boolean esDocumento(String documento) {
        return FORMATO_DOCUMENTO.matcher(documento).matches();
    }

    public static boolean esCodigoPostal(String codigoPostal) {
        return CODIGO_POSTAL.matcher(codigoPostal).matches();
    }

    public static boolean esEmail(String email) {
        return FORMATO_EMAIL.matcher(email).matches();
    }

    public static boolean esFecha(String fecha) {
        return FORMATO_FECHA.matcher(fecha).matches();
    }

    public static boolean esNumero(String num) {
        return SOLO_NUMEROS.matcher(num).matches();
    }

    public static boolean esPrefijo(String prefijo) {
        return FORMATO_PREFIJO.matcher(prefijo).matches();
    }

    public static boolean esDireccion(String direccion) {
        return DIRECCION.matcher(direccion).matches();
    }

    public static boolean esClaveCliente(String clave) {
        return FORMATO_CLAVE_CLIENTE.matcher(clave).matches();
    }

    public static boolean esNumeroReal(String num) {
        return NUMERO_REAL.matcher(num).matches();
    }

    public static boolean esBoolean(String bool) {
        return BOOLEAN.matcher(bool).matches();
    }

    // * Verificadores que toman input
    public static int verificarCodigoPostal(String codigo, Scanner inputUsuario) {
        // Metodo que verifica que el codigo postal tenga el formato correcto
        int resultado;

        if (!esCodigoPostal(codigo)) {
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

        if (!esTelefono(telefono)) {
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

        if (!esPalabra(letras)) {
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
        if (!esEmail(email)) {
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

        if (!esNumero(numeros)) {
            System.out.println("Ingrese un " + objeto);
            while (!inputUsuario.hasNextInt()) {
                System.out.println("Error, Ingrese nuevamente un " + objeto + " valido");
                inputUsuario.next();
            }
            resultado = inputUsuario.nextInt();
        } else {
            resultado = Integer.parseInt(numeros);
        }
        return resultado;
    }

    public static double verificarDouble(String numero, String objeto, Scanner inputUsuario) {
        // Metodo que verifica que se hayan ingresado numeros reales
        double resultado;

        if (!esNumeroReal(numero)) {
            while (!inputUsuario.hasNextDouble()) {
                System.out.println("Error, Ingrese nuevamente " + objeto + " valido");
                inputUsuario.next();
            }
            resultado = inputUsuario.nextDouble();
        } else {
            resultado = Double.parseDouble(numero);
        }

        return resultado;
    }

    public static String verificarFecha(String fecha, Scanner inputUsuario) {
        // Metodo que verifica que se haya ingresado una fecha en el formato correcto

        if (!esFecha(fecha)) {
            System.out.println("Ingrese fecha en formato dd/mm/aaaa");
            while (!inputUsuario.hasNext(FORMATO_FECHA)) {
                System.out.println("Error, Ingrese nuevamente la fecha en el formato dd/mm/aaaa");
                inputUsuario.next();
            }
            fecha = inputUsuario.next();
        }
        return fecha;
    }

    public static String verificarDireccion(String direccion, Scanner inputUsuario) {
        // Metodo que verifica que se haya ingresado una direccion en el formato
        // correcto

        if (!esDireccion(direccion)) {
            System.out.println(
                    "Ingrese nuevamente una direccion en formato NOMBRE_CALLE NUMERO_CASA\nEj: calle falsa 123");
            while (!inputUsuario.hasNext(DIRECCION)) {
                System.out.println("Error, Ingrese nuevamente la direccion");
                inputUsuario.next();
            }
            direccion = inputUsuario.next();
        }
        return direccion;
    }

    public static boolean verificarEstadoPago(String estado, Scanner inputUsuario) {
        // Metodo que verifica que se haya ingresado una direccion en el formato
        // correcto

        if (!esBoolean(estado)) {
            System.out.println("Ingrese fecha en formato dd/mm/aaaa");
            while (!inputUsuario.hasNext(BOOLEAN)) {
                System.out.println("Error, Ingrese nuevamente el estado, T/F");
                inputUsuario.next();
            }
            estado = inputUsuario.next();
        }
        return estado.equals("T");
    }

    public static int verificarPrefijo(String prefijo, Scanner inputUsuario) {
        // Metodo que verifica si el prefijo ingresado es correcto, si no pide al
        // usuario ingresarlo de nuevo
        int resultado;

        if (!esPrefijo(prefijo)) {
            System.out.println("Prefijo invalido, ingrese un prefijo valido");
            while (!inputUsuario.hasNext(FORMATO_PREFIJO)) {
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

    // * Verificadores de atributos de clases
    public static boolean verificarSolicitud(String[] arr) {
        // Metodo que verifica los atributos de una solicitud
        return esCodigoPostal(arr[0]) && esCodigoPostal(arr[1]) && esFecha(arr[2]) && esClaveCliente(arr[3])
                && esNumeroReal(arr[4]) && esNumero(arr[5]) && esDireccion(arr[6]) && esDireccion(arr[7])
                && esBoolean(arr[8]);
    }

    public static boolean verificarCliente(String[] arr) {
        // Metodo que verifica los atribudos de un cliente, retorna true si estan todos
        // correctos
        return esPalabra(arr[0]) && esDocumento(arr[1]) && esPalabra(arr[2]) && esPalabra(arr[3]) && esTelefono(arr[4])
                && esEmail(arr[5]);
    }

    public static boolean verificarRuta(String[] arr) {
        // Metodo que verifica los atributos de una ruta, retorna true si estan todos
        // correctos
        return esCodigoPostal(arr[0]) && esCodigoPostal(arr[1]) && esNumeroReal(arr[2]);
    }

}
