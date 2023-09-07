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

    // Mensajes de error
    private static String errorInput = "ERROR clave ingresada erronea o no existente";

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

    //* Operaciones ABM
    // Ciudades, clientes, solicitudes
    public static void operacionABM(String objeto) {
        System.out.printf("1 - Dar alta un %s\n2 - Dar baja un %s\n3 - Modificar un %s\n4 - Salir\n%n", objeto, objeto,
                objeto);
    }

    public static void darAltaCiudad(){
        
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

    // ! ARREGLAR
    public static void listarCiudades() {
        // Muestra un listado de las ciudades con un prefijo dado por usuario
        Scanner inputUsuario = new Scanner(System.in);
        Lista ciudadesObtenidas;
        Par rango;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese un prefijo numerico que no exceda 4 caracteres");

            if (inputUsuario.nextLine().length() > 4) {
                System.out.println("ERROR mas de 4 caracteres");
            } else {
                rango = obtenerRango(inputUsuario.toString());
                ciudadesObtenidas = ciudades.listarRango((int) rango.getA(), (int) rango.getB());

                // Muestro la lista de las ciudades obtenidas
                System.out.println(ciudadesObtenidas.toString());
            }
            // Pregunta si desea continuar o no
            seguir = !deseaSalir(inputUsuario);
        }
        inputUsuario.close();
    }

    public static Par obtenerRango(int prefijo) {
        // Metodo que analiza si es un string y devuelve un rango en forma de par o tira
        // una excepcion en caso de no serlo
        Par rango = new Par();
        int n, limiteInferior;

        n = longitudCodigoPostal - obtenerLongitudInt(prefijo);
        limiteInferior = (int) (prefijo * Math.pow(10, n));

        rango.setA(limiteInferior);
        rango.setB(obtenerLimiteMax(limiteInferior, n));

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

    // * Utilidades
    public static int obtenerLongitudInt(int numero) {
        // Metodo que obtine la cantidad de digitos de un numero
        return (int) Math.ceil(Math.log10(numero + 1));
    }

    public static int obtenerLimiteMax(int numero, int digitosFaltantes) {
        return numero + (9 * obtenerMax(digitosFaltantes));
    }

    public static boolean deseaSalir(Scanner input) {
        // Metodo que pregunta al usuario si desea salir y retorna true si la respuesta
        // es Si
        System.out.println("Desea salir? S/N");
        return input.nextLine().toUpperCase().equals("S");
    }

    public static boolean verificarCodigosPostales(int[] codigos) {

        boolean respuesta = true;
        int i, longitud = codigos.length;

        for (i = 0; i < longitud && respuesta; i++) {
            respuesta = verificarCodigo(codigos[i]);
        }
        return respuesta;
    }

    public static boolean verificarCodigo(int codigo) {
        return obtenerLongitudInt(codigo) == 4;
    }

    public static int[] toIntArray(String[] arrString, Scanner input) {
        // Metodo que convierte un arreglo de strings a ints
        int i = 0, longitud = arrString.length, codigo = 0;
        int[] arrInts = new int[longitud];

        while (i < longitud) {

            try {
                codigo = Integer.parseInt(arrString[i]);
            } catch (NumberFormatException e) {
                System.out.println(errorInput);
            }

            if (verificarCodigo(codigo)) {
                arrInts[i] = codigo;
            } else {
                System.out.println(errorInput);
                System.out.println("Ingrese un codigo postal valido");
                codigo = input.nextInt();
            }
        }

        return arrInts;
    }

    // ! COMPLETAR | VER SI CONVIENE REFACTORIZAR EL INPUT EN UN METODO
    // * Consultas viajes
    public static void caminoMenosCiudades() {
        // Metodo que pide al usuario que ingrese 2 codigos postales de ciudades y
        // muestra el camino que pase por menos ciudades
        boolean seguir = true;
        Scanner inputUsuario = new Scanner(System.in);
        int codigoOrigen = 0, codigoDestino = 0;
        Ciudad ciudadOrigen, ciudadDestino;

        while (seguir) {
            try {
                System.out.println("Ingrese el codigo postal de la ciudad de partida");
                codigoOrigen = inputUsuario.nextInt();
                System.out.println("Ingrese el codigo postal de la ciudad destino");
                codigoDestino = inputUsuario.nextInt();

            } catch (java.util.InputMismatchException e) {
                System.out.println(errorInput);
                inputUsuario.next();
            }
            // Asigno las ciudades
            ciudadOrigen = (Ciudad) ciudades.obtenerElemento(codigoOrigen);
            ciudadDestino = (Ciudad) ciudades.obtenerElemento(codigoDestino);

            // Si las ciudades existen muestro el camino
            if (ciudadOrigen != null && ciudadDestino != null) {
                // Muestra el camino si es que hay
                System.out.println("El camino que pasa por menos ciudades de "
                        + ciudadOrigen.getNombre() + " a " + ciudadDestino.getNombre()
                        + " es: " + mapaRutas.menorCaminoCantidadNodos(codigoOrigen, codigoDestino));
                // ! Faltaria ver si es conveniente o no listar solo los nombres
            } else {
                System.out.println(errorInput);
            }

            // Se le pregunta al usuario si desea continuar
            seguir = !deseaSalir(inputUsuario);
        }

        inputUsuario.close();
    }

    public static void caminoMenosKilometros() {
        // Metodo que pide al usuario que ingrese 2 codigos postales de ciudades y
        // muestra el camino con menos kilometros si es que existe
        Scanner inputUsuario = new Scanner(System.in);
        String[] codigoPostal;
        int[] codigoPostalInt;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de la ciudades separadas por un guion. Ej: XXXX-YYYY");
            codigoPostal = inputUsuario.nextLine().toString().split("-");

            // Se verifican que los codigos postales sean validos
            codigoPostalInt = toIntArray(codigoPostal, inputUsuario);
            verificarCodigosPostales(codigoPostalInt);

            System.out
                    .println("El camino mas corto es:\n"
                            + mapaRutas.caminoMasCorto(codigoPostalInt[0], codigoPostalInt[1]));

            // Se pregunta al usuario si desea salir o no
            seguir = !deseaSalir(inputUsuario);
        }
        inputUsuario.close();
    }

    public static void caminoTresCiudades() {
        // Metodo que retorna un camino que pasa por 3 ciudades si es que existe
        Scanner inputUsuario = new Scanner(System.in);
        String[] codigoPostal;
        int[] codigoPostalInt;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de las 3 ciudades separadas por '-'. Ej XXXX-YYYY-ZZZZ");
            // Hago un split() para obtener un array con los codigos postales
            codigoPostal = inputUsuario.nextLine().split("-");
            // Procedo a verificar dichos codigos si es que son validos
            codigoPostalInt = toIntArray(codigoPostal, inputUsuario);

            // Finalmente muestro la respuesta si son validos, caso contrario muestro msj
            // error
            System.out.println("Todos los caminos posibles "
                    + mapaRutas.listarCaminosPosibles(codigoPostalInt[0], codigoPostalInt[1], codigoPostalInt[2]));

            // Salir
            seguir = !deseaSalir(inputUsuario);
        }
        inputUsuario.close();
    }

    public static void esPosibleConKilometros() {
        // Metodo que verifica si es posible llegar de ciudad A a ciudad B con X kms o
        // menos
        Scanner inputUsuario = new Scanner(System.in);
        String[] codigoPostal;
        int[] codigoPostalInt;
        Ciudad origen, destino;
        boolean seguir = true;
        int kilometros;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de la ciudades separadas por un guion. Ej: XXXX-YYYY");
            codigoPostal = inputUsuario.nextLine().split("-");
            codigoPostalInt = toIntArray(codigoPostal, inputUsuario);

            System.out.println("Ingrese la cantidad de kilometros");
            kilometros = inputUsuario.nextInt();

            //! O simplemete usar los codigos postales y me evito los nulos y crear tantos objetos
            origen = (Ciudad) ciudades.obtenerElemento(codigoPostalInt[0]);
            destino = (Ciudad) ciudades.obtenerElemento(codigoPostalInt[1]);

            if(origen != null && destino != null){
                System.out.format("Es posible viajar de %s a %s en %d kms : %b %n",
                    origen.getNombre(),
                    destino.getNombre(),
                    kilometros,
                    mapaRutas.verificarCaminoMenorDistacia(codigoPostalInt[0], codigoPostalInt[1], kilometros));
            }
            else{ 
                System.out.println("No existe codigo postal ingresado en el sistema");
            }
            seguir = !deseaSalir(inputUsuario);
        }
        inputUsuario.close();
    }

    //* Verificar viaje 

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
