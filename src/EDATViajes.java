import java.util.Scanner;

public class EDATViajes {

    
    public static void menu(){
        System.out.println("""
                Ingrese un numero correspondiente a la operacion que quiere realizar
                1 - ABM de aeropuertos
                2 - ABM de clientes
                3 - ABM de vuelos
                4 - ABM de pasajes
                5 - Consultas sobre clientes
                6 - Consultas sobre vuelos
                7 - Consultas sobre tiempos de viaje
                8 - Candidatos promocion clientes fieles
                9 - Mostrar sistema
                0 - Salir
                """);
    }

    // Operaciones ABM
    // Aeropuertos, clientes, vuelos, pasajes
    public static void operacionABM(String objeto){
        System.out.printf("1 - Dar alta un %s\n2 - Dar baja un %s\n3 - Modificar un %s\n4 - Salir\n%n", objeto, objeto, objeto);
    }

    // Consultas sobre clientes
    public static void consultasClientes(){
        System.out.println("""
                1 - Verificar y mostrar info de contacto de un cliente
                2 - Mostrar las ciudades que ha visitado un cliente
                3 - Salir
                """);
    }

    // Consulta sobre vuelos
    public static void consultaVuelos(){
        System.out.println("""
                1 - Mostrar toda la informacion de un vuelo
                2 - Mostrar rango de codigos existentes segun 2 codigos ingresados
                3 - Salir""");
    }

    // Consultas sobre tiempos de viaje
    public static void consultaTiempoViaje(){
        System.out.println("""
                == Segun 2 aeropuertos dados A y B:
                1 - Mostrar si es posible que el cliente que parte del origen A llegue en como maximo X vuelos al destino B
                2 - Obtener el menor camino que llegue de A a B en menor tiempo de vuelos
                3 - Obtener el camino que llegue de A a B pasando por la minima cantidad de vuelos
                4 - Obtener el camino mas rapido de A a B y que pase por el aeropuerto C
                5 - Salir""");
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
