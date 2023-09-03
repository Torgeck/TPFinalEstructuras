import java.util.HashMap;
import java.util.Scanner;

import estructura.arbolAVL.ArbolAVL;
import estructura.grafo.Grafo;
import estructura.lineales.Lista;
import estructura.lineales.Par;
import objetos.Ciudad;
import objetos.Cliente;

public class MudanzasCompartidas {

    final static int longitudCodigoPostal = 4;
    private static ArbolAVL ciudades;
    private ArbolAVL solicitudesViajes;
    private static Grafo mapaRutas;
    private HashMap<Integer, Cliente> clientes;

    // Menu principal
    public static void menu() {
        System.out.println("""
                Ingrese un numero correspondiente a la operacion que quiere realizar
                1 - Carga inicial
                2 - ABM
                3 - Consultas
                4 - Verificar viaje
                5 - Mostrar sistema
                0 - Salir
                """);
    }

    // Submenu ABM
    public static void abmMenu() {

        System.out.println("""
                1 - ABM Ciudades
                2 - ABM Red de rutas
                3 - ABM Clientes
                4 - ABM Pedidos
                0 - Salir
                """);
    }

    // Submenu Consultas
    public static void consultasMenu() {
        System.out.println("""
                ===== CLIENTE =====
                1 - Mostrar toda la informacion de un cliente dada la clave
                ===== CIUDAD =====
                2 - Mostrar informacion de una ciudad dada la clave
                3 - Listar ciudades dado un prefijo
                ===== VIAJE =====
                [ Siendo A,B,C ciudades ]
                4 - Obtener camino de A a B que pase por menos ciudades
                5 - Obtener camino de A a B con menor distancia en Kms
                6 - Obtener todos los caminos posibles de A a B que pasen por C
                7 - Verificar si es posible llegar de A a B en como maximo 'X' Kms
                ==========
                0 - Salir
                """);
    }

    // Operaciones ABM
    // Aeropuertos, clientes, vuelos, pasajes
    public static void operacionABM(String objeto) {
        System.out.printf("1 - Dar alta un %s\n2 - Dar baja un %s\n3 - Modificar un %s\n4 - Salir\n%n", objeto, objeto,
                objeto);
    }

    // * Consultas sobre clientes
    public static void consultasCliente(HashMap<Comparable, Object> clientes) {
        // Muestra toda la info de los clientes
        Scanner inputUsuario = new Scanner(System.in);
        Cliente clienteObtenido;
        boolean seguir = true;

        while (seguir) {
            System.out.println(
                    "Ingrese tipo y numero de documento del cliente separados por un guion, en formato TIPO-NUMEROS");

            clienteObtenido = (Cliente) clientes.get(inputUsuario.nextLine());

            System.out.println(((clienteObtenido == null) ? "No se existe cliente con clave\n"
                    : "El cliente es: \n" + clienteObtenido.toString()) + "\nDesea salir? S/N");

            if (inputUsuario.nextLine().toUpperCase().equals("S"))
                seguir = false;
        }
        inputUsuario.close();
    }

    // * Consultas sobre ciudades
    public static void consultaCiudad() {
        // Muestra toda la info de una ciudad dada la clave
        Scanner inputUsuario = new Scanner(System.in);
        Ciudad ciudadObtenida;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese el codigo postal de la ciudad");

            ciudadObtenida = (Ciudad) ciudades.obtenerElemento(inputUsuario.nextInt());

            System.out.println(((ciudadObtenida == null) ? "No se existe cliente con clave\n"
                    : "El cliente es: \n" + ciudadObtenida.toString()) + "\nDesea salir? S/N");
        }
        inputUsuario.close();
    }

    public static void listarCiudades() {
        // Muestra un listado de las ciudades con un prefijo dado por usuario
        Scanner inputUsuario = new Scanner(System.in);
        Lista ciudadesObtenidas;
        Par rangos;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese un prefijo numerico que no exceda 4 caracteres");

            if (inputUsuario.nextLine().length() < 4) {
                System.out.println("ERROR mas de 4 caracteres");
            } else {
                rangos = obtenerRango(inputUsuario.toString());
                ciudadesObtenidas = ciudades.listarRango(rangos.getA(), rangos.getB());

                // Muestro la lista de las ciudades obtenidas
                System.out.println(ciudadesObtenidas.toString());
            }
            // Pregunta si desea continuar o no
            System.out.println("Desea salir? S/N");
            if (inputUsuario.nextLine().toUpperCase().toString().equals("S")) {
                seguir = false;
            }
        }
        inputUsuario.close();
    }

    public static Par obtenerRango(String unString) {
        // Metodo que analiza si es un string y devuelve un rango en forma de par o tira
        // una excepcion en caso de no serlo
        Par rango = new Par();
        int prefijo, n, limiteInferior;

        // ! Optimizar en caso de ser necesario
        try {
            prefijo = Integer.parseInt(unString);
            n = longitudCodigoPostal - unString.length();
            limiteInferior = (int) (prefijo * Math.pow(10, n));

            rango.setA(limiteInferior);
            rango.setB(obtenerLimiteMax(limiteInferior, n));

        } catch (NumberFormatException exception) {
            System.out.println("Se ingreso un prefijo invalido");
        }

        return rango;
    }

    // * Utilidades, mover a otra clase para que sea mas limpio
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

    public static int obtenerLimiteMax(int numero, int digitosFaltantes) {
        return numero + (9 * obtenerMax(digitosFaltantes));
    }

    // ! COMPLETAR
    // * Consultas viajes
    public static void caminoMenosCiudades() {

    }

    public static void caminoMenosKilometros() {
        // Metodo que pide al usuario que ingrese 2 codigos postales de ciudades y
        // muestra el camino con menos kilometros si es que existe
        Scanner inputUsuario = new Scanner(System.in);
        String[] codigoPostal;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de la ciudades separadas por un guion [ 1111-2222 ] ");
            codigoPostal = inputUsuario.nextLine().toString().split("-");

            // Se verifican que los codigos postales sean validos
            if()

            System.out.println("El camino mas corto es:\n" + mapaRutas.caminoMasCorto(codigoPostal[0], codigoPostal[1]));

            // Se pregunta al usuario si desea salir o no
            // codigo reusable
        }
        inputUsuario.close();
    }

    public static void caminoTresCiudades() {

    }

    public static void esPosibleConKilometros() {

    }

    public void iniciarMenu() {
        boolean seguir = true;
        Scanner opcion = new Scanner(System.in);

        // Mostrar menu
        do {
            // Muestro las opciones
            menu();
            // Lectura de la variable ingresada por usuario
            switch (opcion.nextInt()) {
                case 0:
                    seguir = false;
                    break;
                case 1:
                    // cargaInicial(); mas mensaje de que se realizo una carga inicial de datos
                    // mostrar el otro menu
                    break;
                case 2:
                    abmMenu();
                    break;
                case 3:
                    operacionABM("Cliente");
                    // mostrar el otro menu
                    break;
                case 4:
                    operacionABM("Pedido");
                    // mostrar el otro menu
                    break;
                case 5:
                    consultasCliente();
                    // Hacer cosas
                    break;
                default:
                    System.out.println("\nNO EXISTE OPCION INGRESADA\n");
            }
        } while (seguir);
        // Al salir del sistema guardar un archivo LOG con el estado final del sistema
    }

    // Listar clientes con mas pasajes comprados (Ver que estructura es la mas
    // optima para ir ordenando mientras agrego
    // o hacer una lista y despues un quick sort) TESTEAR AMBOS POR TIEMPO

    // Mostrar sistema (TODAS las estructuras usadas con su contenido)
}
