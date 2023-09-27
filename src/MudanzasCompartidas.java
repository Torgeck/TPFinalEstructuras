import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Scanner;

import estructuras.arbolAVL.ArbolAVL;
import estructuras.grafo.Grafo;
import estructuras.lineales.Lista;
import estructuras.lineales.Par;
import objetos.Ciudad;
import objetos.Cliente;
import objetos.Solicitud;

public class MudanzasCompartidas {

    private static ArbolAVL ciudades;
    private static ArbolAVL solicitudesViajes;
    private static Grafo mapaRutas;
    private static HashMap<String, Cliente> clientes;

    // Mensajes de error
    private static String errorInput = "ERROR clave ingresada erronea o no existente";
    private static String errorExistencia = "ERROR ya existe en el sistema";

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

    // Submenu de VerificarViajes
    public static void verificarViajesMenu() {
        System.out.println("""
                ======== Dadas 2 ciudades X e Y ========
                1 - Mostrar todos los pedidos y calcular el espacio total faltante en el camion
                2 - Verificar si sobra espacio en el camion y listar posibles solicitudes a ciudades
                    intermedias que se podrian aprovechar a cubrir (Considerando el camino mas corto en kms)
                ======= Dada una lista de ciudades =======
                3 - Verificar si es un camino perfecto
                    """);
    }

    // * Operaciones ABM
    // Ciudades, clientes, solicitudes
    public static void operacionABM(String objeto) {
        System.out.printf("1 - Dar alta un %s\n2 - Dar baja un %s\n3 - Modificar un %s\n4 - Salir\n%n", objeto, objeto,
                objeto);
    }

    // TODO Ver si es conveniente sacar el switch por un HM
    // * CIUDADES
    public static void darAltaCiudad(Scanner inputUsuario) {
        // Metodo que pide datos al usuario para dar de alta una ciudad
        Ciudad ciudadUsuario;
        boolean seguir = true;
        int codigoPostal;

        while (seguir) {
            System.out.println("Ingrese el codigo postal de la ciudad");
            codigoPostal = Utilidades.verificarCodigoPostal(inputUsuario.nextLine(), inputUsuario);

            if (ciudades.obtenerElemento(codigoPostal) == null) {
                System.out.println(
                        "Ingrese nombre de la ciudad y provincia separadas por -. \nEj: Ciudad-Provincia");
                ciudadUsuario = crearCiudad(inputUsuario.nextLine().split("-"), codigoPostal, inputUsuario);

                ciudades.insertar(codigoPostal, ciudadUsuario);
                solicitudesViajes.insertar(codigoPostal, new Lista());
                mapaRutas.insertarVertice(codigoPostal);

            } else {
                System.out.println(errorExistencia);
            }
            seguir = !deseaSalir(inputUsuario);
        }
    }

    public static Ciudad crearCiudad(String[] datosCiudad, int codigoPostal, Scanner inputUsuario) {
        // Metodo que verifica el input del usuario y crea una ciudad
        String nombre, provincia;

        while (datosCiudad.length != 2) {
            System.out.println("Ingrese nuevamente nombre de la ciudad y provincias separadas por -");
            datosCiudad = inputUsuario.nextLine().split("-");
        }

        nombre = Utilidades.verificarLetras(datosCiudad[0], inputUsuario);
        provincia = Utilidades.verificarLetras(datosCiudad[1], inputUsuario);

        return new Ciudad(codigoPostal, nombre, provincia);
    }

    public static void darBajaCiudad(Scanner inputUsuario) {
        // Metodo que da de baja una ciudad en el sistema si es que existe
        int codigoPostal;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese el codigo postal de la ciudad que desea eliminar");
            codigoPostal = Utilidades.verificarCodigoPostal(inputUsuario.nextLine(), inputUsuario);

            if (ciudades.obtenerElemento(codigoPostal) != null) {

                ciudades.eliminar(codigoPostal);
                solicitudesViajes.eliminar(codigoPostal);
                mapaRutas.eliminarVertice(codigoPostal);
            } else {
                System.out.println(errorExistencia);
            }

            seguir = !deseaSalir(inputUsuario);
        }
    }

    public static void modificarCiudad(Scanner inputUsuario) {
        // Metodo que modifica una ciudad en el sistema si es que existe
        Ciudad ciudad;
        boolean seguir = true;
        int codigoPostal, opcion;

        while (seguir) {
            // Pregunta que ciudad quiere modificar
            System.out.println("Ingrese el codigo postal de la ciudad a modificar");
            codigoPostal = Utilidades.verificarCodigoPostal(inputUsuario.nextLine(), inputUsuario);
            ciudad = (Ciudad) ciudades.obtenerElemento(codigoPostal);

            if (ciudad != null) {
                System.out.println("""
                            Que desea modificar?\n
                        1 - Nombre de la ciudad\n
                        2 - Provincia de la ciudad""");

                opcion = inputUsuario.nextInt();
                inputUsuario.nextLine();
                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese nuevo nombre para la ciudad");
                        ciudad.setNombre(Utilidades.verificarLetras(inputUsuario.nextLine(), inputUsuario));
                        System.out.println("Se cambio el nombre de la ciudad a: " + ciudad.getNombre());
                        break;
                    case 2:
                        System.out.println("Ingrese la nueva provincia para la ciudad");
                        ciudad.setProvincia(Utilidades.verificarLetras(inputUsuario.nextLine(), inputUsuario));
                        System.out.println("Se cambio la provincia a: " + ciudad.getProvincia());
                        break;
                    default:
                        System.out.println("Opcion ingresada incorrecta");
                }

            } else {
                System.out.println(errorExistencia);
            }

            seguir = !deseaSalir(inputUsuario);
        }
    }

    // * CLIENTES

    public static void darAltaCliente(Scanner inputUsuario) {
        // Metodo que da de alta un cliente si es que no existe en el sistema
        Cliente cliente;
        Par claveCliente;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese el tipo y numero de documento del cliente separada por -. Ej TIPO-11111111");
            claveCliente = Utilidades.verificarClaveCliente(inputUsuario.nextLine().split("-"), inputUsuario);
            // Validar clave

            if (clientes.get(claveCliente.toConcatString()) == null) {
                System.out.println("Ingrese nombre, apellido, telefono y email del cliente separados por -");
                cliente = crearCliente(inputUsuario.nextLine().split("-"), claveCliente, inputUsuario);

                // Lo agrego al sistema
                clientes.put(cliente.getClave(), cliente);
            } else {
                System.out.println(errorExistencia);
            }

            seguir = !deseaSalir(inputUsuario);
        }
    }

    public static Cliente crearCliente(String[] datosCliente, Par clave, Scanner inputUsuario) {
        // Metodo que crea y retorna un cliente en base a los datos pasados por
        // parametro y pide al usuario que reingrese info si es que falta
        String nombre, apellido, email;
        int telefono = -1;

        while (datosCliente.length != 4) {
            System.out.println("Ingrese nombre, apellido, telefono y email del cliente separados por -");
            datosCliente = inputUsuario.nextLine().split("-");
        }

        nombre = Utilidades.verificarLetras(datosCliente[0], inputUsuario);
        apellido = Utilidades.verificarLetras(datosCliente[1], inputUsuario);
        telefono = Utilidades.verificarTelefono(datosCliente[2], inputUsuario);
        email = Utilidades.verificarEmail(datosCliente[3], inputUsuario);

        return new Cliente(clave.getA().toString(), (int) clave.getB(), nombre, apellido, telefono, email);
    }

    public static void modificarCliente(Scanner inputUsuario) {
        // Metodo que pide al usuario que quiere modificar del cliente y lo modifica
        Cliente cliente;
        int opcion;
        boolean seguir = true;

        while (seguir) {
            System.out.println(
                    "Ingrese el tipo y numero de documento del cliente a modificar separada por -. Ej TIPO-11111111");
            cliente = clientes.get(Utilidades.verificarClaveCliente(inputUsuario.nextLine().split("-"), inputUsuario)
                    .toConcatString());

            if (cliente != null) {
                System.out.println("""
                            Ingrese la opcion correspondiente al atributo a modificar
                            1 - Nombre
                            2 - Apellido
                            3 - Telefono
                            4 - Email
                        """);

                opcion = inputUsuario.nextInt();
                inputUsuario.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese nuevo nombre para el cliente");
                        cliente.setNombre(Utilidades.verificarLetras(inputUsuario.nextLine(), inputUsuario));
                        System.out.println("Se cambio el nombre del cliente a: " + cliente.getNombre());
                        break;
                    case 2:
                        System.out.println("Ingrese nuevo apellido para el cliente");
                        cliente.setApellido(Utilidades.verificarLetras(inputUsuario.nextLine(), inputUsuario));
                        System.out.println("Se cambio el apellido a: " + cliente.getApellido());
                        break;
                    case 3:
                        System.out.println("Ingrese nuevo telefono para el cliente");
                        cliente.setTelefono(Utilidades.verificarTelefono(inputUsuario.nextLine(), inputUsuario));
                        System.out.println("Se cambio el telefono a: " + cliente.getTelefono());
                        break;
                    case 4:
                        System.out.println("Ingrese nuevo email para el cliente");
                        cliente.setEmail(Utilidades.verificarEmail(inputUsuario.nextLine(), inputUsuario));
                        System.out.println("Se cambio el email a: " + cliente.getEmail());
                    default:
                        System.out.println("Opcion ingresada incorrecta");
                }

            } else {
                System.out.println("No existe cliente con la clave ingresada");
            }

            seguir = !deseaSalir(inputUsuario);
        }
    }

    public static void eliminarCliente(Scanner inputUsuario) {
        // Metodo que pide al usuario que cliente desea eliminar del sistema
        String clave;
        boolean seguir = true;

        while (seguir) {
            System.out.println(
                    "Ingrese el tipo y numero de documento del cliente a eliminar separada por -. Ej TIPO-11111111");
            clave = Utilidades.verificarClaveCliente(inputUsuario.nextLine().split("-"), inputUsuario).toConcatString();
            // Si el cliente esta en el sistema
            if (clientes.get(clave) != null) {
                clientes.remove(clave);
                System.out.println("Se elimino el cliente ");
            } else {
                System.out.println(errorInput);
            }

            seguir = !deseaSalir(inputUsuario);
        }
    }

    // * Rutas
    public static void darAltaRuta(Scanner inputUsuario) {
        // Metodo que da de alta una ruta
        int[] ciudad;
        int cantCiudades = 2;
        double cantKms;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de las ciudades separados por -. Ej 1111-2222");
            ciudad = Utilidades.toIntArray(inputUsuario.nextLine().split("-"), cantCiudades, inputUsuario);
            System.out.println("Ingrese la cantidad de kilometros de la ruta");

            cantKms = Utilidades.verificarDouble(inputUsuario.nextLine(), "cantidad de kms", inputUsuario);

            // TODO loggear todas las inserciones y eliminaciones en el sistema
            System.out.println("Se dio de alta la ruta: " + mapaRutas.insertarArco(ciudad[0], ciudad[1], cantKms));

            seguir = !deseaSalir(inputUsuario);
        }
    }

    public static void darBajaRuta(Scanner inputUsuario) {
        // Metodo que da de baja una ruta si es que existe en el sistema
        int[] ciudad;
        int cantCiudades = 2;
        double cantKms;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de las ciudades separados por -. Ej 1111-2222");
            ciudad = Utilidades.toIntArray(inputUsuario.nextLine().split("-"), cantCiudades, inputUsuario);
            System.out.println("Ingrese la cantidad de kilometros de la ruta");

            cantKms = Utilidades.verificarDouble(inputUsuario.nextLine(), "cantidad de kms", inputUsuario);

            // TODO loggear todas las inserciones y eliminaciones en el sistema
            System.out.println("Se dio de baja la ruta: " + mapaRutas.eliminarArco(ciudad[0], ciudad[1], cantKms));
            seguir = !deseaSalir(inputUsuario);
        }
    }

    public static void modificarRuta(Scanner inputUsuario) {
        // Metodo que modifica una ruta especificada por el usuario si es que existe en
        // el sistema
        int[] ciudad;
        int cantCiudades = 2;
        double cantKms, nuevoKms;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de las ciudades separados por -. Ej 1111-2222");
            ciudad = Utilidades.toIntArray(inputUsuario.nextLine().split("-"), cantCiudades, inputUsuario);
            System.out.println("Ingrese la cantidad de kilometros de la ruta");

            cantKms = Utilidades.verificarDouble(inputUsuario.nextLine(), "cantidad de kms", inputUsuario);

            if (mapaRutas.eliminarArco(ciudad[0], ciudad[1], cantKms)) {
                System.out.println("Ingrese la nueva cantidad de kilometros de la ruta");
                nuevoKms = Utilidades.verificarDouble(inputUsuario.nextLine(), "cantidad de kms", inputUsuario);
                System.out.println("Se modifico la distancia de la ruta: "
                        + mapaRutas.insertarArco(ciudad[0], ciudad[1], nuevoKms));
            }

            // TODO loggear todas las inserciones y eliminaciones en el sistema
            seguir = !deseaSalir(inputUsuario);
        }
    }

    // * PEDIDOS
    public static void darAltaPedido(Scanner inputUsuario) {
        // Metodo que da de alta un pedido de una ciudad a otra
        boolean seguir = true;

        while (seguir) {
            // Pedir ciudades origen y destino
            // ver si existen
            // Pedir cliente
            // ver si existe
            // pedir demas datos y crear solicitud
            // agregarla al avl con la ciudad origen

            seguir = !deseaSalir(inputUsuario);
        }
    }

    // * Consultas sobre clientes
    public static void consultasCliente(Scanner inputUsuario) {
        // Muestra toda la info de los clientes
        Cliente clienteObtenido;
        boolean seguir = true;

        while (seguir) {
            System.out.println(
                    "Ingrese tipo y numero de documento del cliente separados por - , en formato TIPO-NUMEROS");

            clienteObtenido = (Cliente) clientes.get(Utilidades
                    .verificarClaveCliente(inputUsuario.nextLine().split("-"), inputUsuario).toConcatString());

            System.out.println(((clienteObtenido == null) ? "No se existe cliente con clave\n"
                    : "El cliente es: \n" + clienteObtenido.toString()));

            seguir = !deseaSalir(inputUsuario);
        }
    }

    // * Consultas sobre ciudades
    public static void consultaCiudad(Scanner inputUsuario) {
        // Muestra toda la info de una ciudad dada la clave
        Ciudad ciudadObtenida;
        int codigoPostal;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese el codigo postal de la ciudad");
            codigoPostal = Utilidades.verificarCodigoPostal(inputUsuario.nextLine(), inputUsuario);
            ciudadObtenida = (Ciudad) ciudades.obtenerElemento(codigoPostal);

            System.out.println(((ciudadObtenida == null) ? "No se existe cliente con clave\n"
                    : "El cliente es: \n" + ciudadObtenida.toString()));
            seguir = !deseaSalir(inputUsuario);
        }
    }

    public static void listarCiudades(Scanner inputUsuario) {
        // Muestra un listado de las ciudades con un prefijo dado por usuario
        Lista ciudadesObtenidas;
        Par rango;
        int prefijo;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese un prefijo numerico que no exceda 4 caracteres");
            prefijo = Utilidades.verificarPrefijo(inputUsuario.nextLine(), inputUsuario);

            rango = Utilidades.obtenerRango(prefijo);
            ciudadesObtenidas = ciudades.listarRango((int) rango.getA(), (int) rango.getB());

            // Muestro la lista de las ciudades obtenidas
            System.out.println("Las ciudades dentro del rango [" + rango.getA() + " - " + rango.getB() + "] son:\n"
                    + ciudadesObtenidas.toString());

            seguir = !deseaSalir(inputUsuario);
        }
    }

    public static boolean deseaSalir(Scanner input) {
        // Metodo que pregunta al usuario si desea salir y retorna true si la respuesta
        // es Si
        System.out.println("Desea salir? S/N");
        return input.nextLine().toUpperCase().equals("S");
    }

    // * Consultas viajes
    public static void caminoMenosCiudades(Scanner inputUsuario) {
        // Metodo que pide al usuario que ingrese 2 codigos postales de ciudades y
        // muestra el camino que pase por menos ciudades
        Ciudad ciudadOrigen, ciudadDestino;
        int[] codigoPostal;
        boolean seguir = true;

        while (seguir) {

            System.out.println(
                    "Ingrese el codigo postal de la ciudad de partida y la ciudad de destino separadas por un -. Ej XXXX-YYYY");
            codigoPostal = Utilidades.toIntArray(inputUsuario.nextLine().split("-"), 2, inputUsuario);

            ciudadOrigen = (Ciudad) ciudades.obtenerElemento(codigoPostal[0]);
            ciudadDestino = (Ciudad) ciudades.obtenerElemento(codigoPostal[1]);

            // Si las ciudades existen muestro el camino
            if (ciudadOrigen != null && ciudadDestino != null) {
                System.out.println("El camino que pasa por menos ciudades de "
                        + ciudadOrigen.getNombre() + " a " + ciudadDestino.getNombre()
                        + " es: " + mapaRutas.menorCaminoCantidadNodos(ciudadOrigen.getCodigoPostal(),
                                ciudadDestino.getCodigoPostal()).toString());
            } else {
                System.out.println(errorInput);
            }
            seguir = !deseaSalir(inputUsuario);
        }
    }

    public static void caminoMenosKilometros(Scanner inputUsuario) {
        // Metodo que pide al usuario que ingrese 2 codigos postales de ciudades y
        // muestra el camino con menos kilometros si es que existe
        int[] codigoPostalInt;
        int cantCiudades = 2;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de la ciudades separadas por un guion. Ej: XXXX-YYYY");
            // Se verifican que los codigos postales sean validos
            codigoPostalInt = Utilidades.toIntArray(inputUsuario.nextLine().toString().split("-"), cantCiudades,
                    inputUsuario);

            System.out
                    .println("El camino mas corto es:\n"
                            + mapaRutas.caminoMasCorto(codigoPostalInt[0], codigoPostalInt[1]));

            // Se pregunta al usuario si desea salir o no
            seguir = !deseaSalir(inputUsuario);
        }
    }

    public static void caminoTresCiudades(Scanner inputUsuario) {
        // Metodo que retorna un camino que pasa por 3 ciudades si es que existe
        int[] codigoPostalInt;
        int cantCiudades = 3;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de las 3 ciudades separadas por '-'. Ej XXXX-YYYY-ZZZZ");
            codigoPostalInt = Utilidades.toIntArray(inputUsuario.nextLine().split("-"), cantCiudades, inputUsuario);

            System.out.println("Todos los caminos posibles "
                    + mapaRutas.listarCaminosPosibles(codigoPostalInt[0], codigoPostalInt[1], codigoPostalInt[2]));

            seguir = !deseaSalir(inputUsuario);
        }
    }

    public static void esPosibleConKilometros(Scanner inputUsuario) {
        // Metodo que verifica si es posible llegar de ciudad A a ciudad B con X kms o
        // menos
        int[] codigoPostalInt;
        Ciudad origen, destino;
        int cantCiudades = 2;
        double kilometros;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de la ciudades separadas por un guion. Ej: XXXX-YYYY");
            codigoPostalInt = Utilidades.toIntArray(inputUsuario.nextLine().split("-"), cantCiudades, inputUsuario);

            System.out.println("Ingrese la cantidad de kilometros");
            kilometros = Utilidades.verificarDouble(inputUsuario.nextLine(), "cantidad de kms", inputUsuario);

            origen = (Ciudad) ciudades.obtenerElemento(codigoPostalInt[0]);
            destino = (Ciudad) ciudades.obtenerElemento(codigoPostalInt[1]);

            if (origen != null && destino != null) {
                System.out.format("Es posible viajar de %s a %s en %d kms : %b %n",
                        origen.getNombre(),
                        destino.getNombre(),
                        kilometros,
                        mapaRutas.verificarCaminoMenorDistacia(origen.getCodigoPostal(), destino.getCodigoPostal(),
                                kilometros));
            } else {
                System.out.println("No existe codigo postal ingresado en el sistema");
            }
            seguir = !deseaSalir(inputUsuario);
        }
    }

    // * Verificar viaje
    public static void obtenerEspacioFaltante(Scanner inputUsuario) {
        Par pedidosYEspacio;
        int[] codigoPostal;
        int cantCiudades = 2;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese codigos postales de la ciudades separadas por un -. Ej XXXX-YYYY");
            codigoPostal = Utilidades.toIntArray(inputUsuario.nextLine().split("-"), cantCiudades, inputUsuario);

            if (mapaRutas.existeCamino(codigoPostal[0], codigoPostal[1])) {

                pedidosYEspacio = obtenerParListaEspacio(codigoPostal[0], codigoPostal[1], 0);
                System.out.println("Todos los pedidos de: " + codigoPostal[0] + " a " + codigoPostal[1] + "son: "
                        + pedidosYEspacio.getA().toString());

                System.out.println("El espacio faltante es de: " + pedidosYEspacio.getB() + " metros cubicos");
            } else {
                System.out.println("No existe camino entre las ciudades ingresadas");
            }
            seguir = !deseaSalir(inputUsuario);
        }
    }

    private static Par obtenerParListaEspacio(int origen, int destino, double espacioCamion) {
        // Metodo que obtiene la lista de todos los pedidos de origen a destino y
        // calcula el espacio faltante en el camion
        // Retornando un par con los resultados obtenidos
        Par resultado = new Par();
        Lista solicitudes = (Lista) solicitudesViajes.obtenerElemento(origen);

        // Filtro las solicitudes con destino
        resultado.setA(crearYFiltrar(solicitudes, destino));
        resultado.setB(calcularEspacioFaltante((Lista) resultado.getA(), espacioCamion));

        return resultado;
    }

    private static Lista crearYFiltrar(Lista lista, int destino) {
        int i = 0, longitud = lista.longitud();
        Lista resultado = new Lista();
        Solicitud aux;

        while (i < longitud) {
            aux = (Solicitud) lista.recuperar(i);
            if (aux.getCiudadDestino() == destino) {
                resultado.insertar(aux, 0);
            }
            i++;
        }
        return resultado;
    }

    private static double calcularEspacioFaltante(Lista lista, double espacioCamion) {
        int i = 0, longitud = lista.longitud();
        double resultado = -espacioCamion;
        Solicitud aux;

        while (i < longitud) {
            aux = (Solicitud) lista.recuperar(i);
            resultado += aux.getMetrosCubicos();
        }

        return resultado;
    }

    public static void verificarEspacioListarSolicitudes(Scanner inputUsuario) {
        Lista camino, solicitudesDestino, posiblesSolicitudes;
        int[] codigoPostal;
        int cantCiudades = 2;
        boolean seguir = true;
        double espacioCamion;

        while (seguir) {
            System.out.println("Ingrese codigos postales de la ciudades separadas por un -. Ej XXXX-YYYY");
            codigoPostal = Utilidades.toIntArray(inputUsuario.nextLine().split("-"), cantCiudades, inputUsuario);

            camino = (Lista) mapaRutas.caminoMasCorto(codigoPostal[0], codigoPostal[1]).getB();

            if (camino != null) {
                System.out.println("Ingrese cantidad de espacio en el camion en metros cubicos");
                espacioCamion = Utilidades.verificarDouble(inputUsuario.nextLine(), "cantidad de metros cubicos",
                        inputUsuario);
                // llamo al metodo que verifica si hay espacio para los pedidos de origen a
                // destino
                solicitudesDestino = crearYFiltrar((Lista) solicitudesViajes.obtenerElemento(codigoPostal[0]),codigoPostal[1]);
                espacioCamion = calcularEspacioFaltante(solicitudesDestino, espacioCamion);
                if( espacioCamion < 0);{
                    // Hay espacio
                    posiblesSolicitudes = obtenerSolicitudesSatisfacibles(camino, codigoPostal[1], espacioCamion);
                    System.out.println(posiblesSolicitudes );
                } else{
                    // No hay espacio
                    System.out.println("No hay espacio por ende no se pueden listar posibles pedidos a satisfacer");
                };
                // si hay espacio > 0 entonces recorro la lista en busca de pedidos de ciudad
                // actual a ciudad destino
                // los agrego a una lista y los muestro
                // si no hay espacio le digo al usuario que no hay espacio y no listo ningun
                // pedido ya que no se puede aprovechar nada
            }
            seguir = !deseaSalir(inputUsuario);
        }
    }

    private Lista obtenerPosiblesSolcitudes(Lista camino, int destino, double espacioDisponible) {
        // Metodo que obtiene las posibles solicitudes que se pueden satisfacer de
        // origen a destino
        int i = 0, longitud, ciudadActual = (int) camino.recuperar(i);
        Lista posiblesSolicitudes = crearYFiltrar(solicitudesViajes.obtenerElemento(ciudadActual), destino),
                solicitudesFinales = new Lista();
        Solicitud aux;

        while (i > longitud) {
            aux = (Solicitud) posiblesSolicitudes.recuperar(i);

            if (aux.getMetrosCubicos() >= espacioDisponible) {
                resultado.insertar(aux, 1);
            }
            i++;
        }

        return solicitudesFinales;
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
