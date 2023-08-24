import java.util.Scanner;

public class MudanzasCompartidas {

    // Menu principal
    public static void menu(){
        System.out.println("""
                Ingrese un numero correspondiente a la operacion que quiere realizar
                0 - Carga inicial
                1 - ABM
                2 - Consultas
                3 - Verificar viaje
                4 - Mostrar sistema
                5 - Salir
                """);
    }

    // Submenu ABM
    public static void abmMenu(){
        System.out.println(""" 
                1 - ABM Ciudades
                2 - ABM Red de rutas
                3 - ABM Clientes
                4 - ABM Pedidos
                0 - Salir
                """); 
    }

    // Submenu Consultas
    public static void consultasMenu(){
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
                """);
    }

    // Operaciones ABM
    // Aeropuertos, clientes, vuelos, pasajes
    public static void operacionABM(String objeto){
        System.out.printf("1 - Dar alta un %s\n2 - Dar baja un %s\n3 - Modificar un %s\n4 - Salir\n%n", objeto, objeto, objeto);
    }

    // Consultas sobre clientes
    public static void consultasCliente(){
        // Muestra toda la info de los clientes
    }

    public static void consultaCiudad(){
        // Muestra toda la info de una ciudad dada la clave
        Scanner codigoPostal = new Scanner(System.in);

        System.out.println("Ingrese el codigo postal de la ciudad");
        // ciudades.obtenerInfo(codigoPostal.nextInt());

    }

    public static void listarCiudades(){
        // Muestra un listado de las ciudades con un prefijo dado por usuario
        Scanner prefijo = new Scanner(System.in);

        System.out.println("Ingrese un prefijo numerico que no exceda 4 caracteres");
        // ciudades.listarRango(prefijo.nextInt())
    }

    public void iniciarMenu() {
        boolean seguir = true;
        Scanner opcion = new Scanner(System.in);

        // Mensaje de carga inicial de datos
        // cargaInicial();

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
                    operacionABM("Aeropuerto");
                    // mostrar el otro menu
                    break;
                case 2:
                    operacionABM("Cliente");
                    // mostrar el otro menu
                    break;
                case 3:
                    operacionABM("Vuelo");
                    // mostrar el otro menu
                    break;
                case 4:
                    operacionABM("Pasaje");
                    // mostrar el otro menu
                    break;
                case 5:
                    consultasClientes();
                    // Hacer cosas
                    break;
                case 6:
                    consultaVuelos();
                    // Hacer cosas
                    break;
                case 7:
                    consultaTiempoViaje();
                    // Hacer cosas
                    break;
                case 8:
                    // Promo clientes fieles
                    break;
                case 9:
                    // Mostrar sistema
                    break;
                default:
                    System.out.println("\nNO EXISTE OPCION INGRESADA\n");
            }
        } while (seguir);
        // Al salir del sistema guardar un archivo LOG con el estado final del sistema
    }

    // Listar clientes con mas pasajes comprados (Ver que estructura es la mas optima para ir ordenando mientras agrego
    // o hacer una lista y despues un quick sort)   TESTEAR AMBOS POR TIEMPO

    // Mostrar sistema (TODAS las estructuras usadas con su contenido)
}
