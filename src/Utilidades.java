import java.util.Scanner;
import estructuras.lineales.Par;

public final class Utilidades {

    // * Constantes
    private static final int LONGITUD_CODIGO_POSTAL = 4;

    public static int[] toIntArray(String[] arrString, int cantElementos, Scanner input) {
        // Metodo que convierte un arreglo(con codigos postales) de strings a ints
        int i = 0;
        int[] arrInts = new int[cantElementos];

        while (i < cantElementos) {

            try {
                arrInts[i] = Verificador.verificarCodigoPostal(arrString[i], input);
            } catch (ArrayIndexOutOfBoundsException e) {
                arrInts[i] = Verificador.verificarCodigoPostal("", input);
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
