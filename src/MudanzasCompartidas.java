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

    private ArbolAVL ciudades;
    private ArbolAVL solicitudesViajes;
    private Grafo mapaRutas;
    private HashMap<String, Cliente> clientes;

    // Mensajes de error
    private static String errorInput = "ERROR clave ingresada erronea o no existente";
    private static String errorExistencia = "ERROR ya existe en el sistema";

    public MudanzasCompartidas() {
        this.ciudades = new ArbolAVL();
        this.solicitudesViajes = new ArbolAVL();
        this.mapaRutas = new Grafo();
        this.clientes = new HashMap<String, Cliente>();
    }

    // Getters
    public ArbolAVL getCiudades() {
        return this.ciudades;
    }

    public ArbolAVL getSolicitudesViajes() {
        return this.solicitudesViajes;
    }

    public Grafo getMapaRutas() {
        return this.mapaRutas;
    }

    public HashMap<String, Cliente> getClientes() {
        return this.clientes;
    }

    // * Se podria tener un HM con los strings para los menus
    // Menu principal
    public static void menuPrincipal() {
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
    public void darAltaCiudad(Scanner inputUsuario) {
        // Metodo que pide datos al usuario para dar de alta una ciudad
        Ciudad ciudadUsuario;
        boolean seguir = true;
        int codigoPostal;

        while (seguir) {
            System.out.println("Ingrese el codigo postal de la ciudad");
            codigoPostal = Verificador.verificarCodigoPostal(inputUsuario.nextLine(), inputUsuario);

            if (this.ciudades.obtenerElemento(codigoPostal) == null) {
                System.out.println(
                        "Ingrese nombre de la ciudad y provincia separadas por -. \nEj: Ciudad-Provincia");
                ciudadUsuario = crearCiudad(inputUsuario.nextLine().split("-"), codigoPostal, inputUsuario);

                agregarCiudad(ciudadUsuario);

                Logger.log("Se creo ciudad: " + ciudadUsuario);
            } else {
                System.out.println(errorExistencia);
            }
            seguir = !deseaSalir(inputUsuario);
        }
    }

    public void agregarCiudad(Ciudad ciudadUsuario) {
        // Agrega una ciudad a las estructuras del sistema
        int codigoPostal = ciudadUsuario.getCodigoPostal();

        this.ciudades.insertar(codigoPostal, ciudadUsuario);
        this.solicitudesViajes.insertar(codigoPostal, new Lista());
        this.mapaRutas.insertarVertice(codigoPostal);
    }

    public Ciudad crearCiudad(String[] datosCiudad, int codigoPostal, Scanner inputUsuario) {
        // Metodo que verifica el input del usuario y crea una ciudad
        String nombre, provincia;

        while (datosCiudad.length != 2) {
            System.out.println("Ingrese nuevamente nombre de la ciudad y provincias separadas por -");
            datosCiudad = inputUsuario.nextLine().split("-");
        }

        nombre = Verificador.verificarLetras(datosCiudad[0], inputUsuario);
        provincia = Verificador.verificarLetras(datosCiudad[1], inputUsuario);

        return new Ciudad(codigoPostal, nombre, provincia);
    }

    public void darBajaCiudad(Scanner inputUsuario) {
        // Metodo que da de baja una ciudad en el sistema si es que existe
        int codigoPostal;
        boolean seguir = true;
        Ciudad ciudadAEliminar;

        while (seguir) {
            System.out.println("Ingrese el codigo postal de la ciudad que desea eliminar");
            codigoPostal = Verificador.verificarCodigoPostal(inputUsuario.nextLine(), inputUsuario);
            ciudadAEliminar = (Ciudad) ciudades.obtenerElemento(codigoPostal);

            if (ciudadAEliminar != null) {

                ciudades.eliminar(codigoPostal);
                solicitudesViajes.eliminar(codigoPostal);
                mapaRutas.eliminarVertice(codigoPostal);

                Logger.log("Se elimino la ciudad: " + ciudadAEliminar);
            } else {
                System.out.println(errorExistencia);
            }

            seguir = !deseaSalir(inputUsuario);
        }
    }

    public void modificarCiudad(Scanner inputUsuario) {
        // Metodo que modifica una ciudad en el sistema si es que existe
        Ciudad ciudad;
        String datoModificado;
        boolean seguir = true;
        int codigoPostal, opcion;

        while (seguir) {
            // Pregunta que ciudad quiere modificar
            System.out.println("Ingrese el codigo postal de la ciudad a modificar");
            codigoPostal = Verificador.verificarCodigoPostal(inputUsuario.nextLine(), inputUsuario);
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
                        datoModificado = ciudad.getNombre();
                        System.out.println("Ingrese nuevo nombre para la ciudad");
                        ciudad.setNombre(Verificador.verificarLetras(inputUsuario.nextLine(), inputUsuario));
                        Logger.log("Se cambio el nombre de la ciudad" + datoModificado + " a: " + ciudad.getNombre());
                        break;
                    case 2:
                        datoModificado = ciudad.getProvincia();
                        System.out.println("Ingrese la nueva provincia para la ciudad");
                        ciudad.setProvincia(Verificador.verificarLetras(inputUsuario.nextLine(), inputUsuario));
                        Logger.log("Se cambio la provincia" + datoModificado + " a: " + ciudad.getProvincia());
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

    public void darAltaCliente(Scanner inputUsuario) {
        // Metodo que da de alta un cliente si es que no existe en el sistema
        Cliente cliente;
        Par claveCliente;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese el tipo y numero de documento del cliente separada por -. Ej TIPO-11111111");
            claveCliente = Verificador.verificarClaveCliente(inputUsuario.nextLine().split("-"), inputUsuario);
            // Validar clave

            if (clientes.get(claveCliente.toConcatString()) == null) {
                System.out.println("Ingrese nombre, apellido, telefono y email del cliente separados por -");
                cliente = crearCliente(inputUsuario.nextLine().split("-"), claveCliente, inputUsuario);

                // Lo agrego al sistema
                agregarCliente(cliente);
                Logger.log("Se creo el cliente: " + cliente);
            } else {
                System.out.println(errorExistencia);
            }

            seguir = !deseaSalir(inputUsuario);
        }
    }

    public void agregarCliente(Cliente cliente) {
        clientes.put(cliente.getClave(), cliente);
    }

    public Cliente crearCliente(String[] datosCliente, Par clave, Scanner inputUsuario) {
        // Metodo que crea y retorna un cliente en base a los datos pasados por
        // parametro y pide al usuario que reingrese info si es que falta
        String nombre, apellido, email;
        int telefono;

        while (datosCliente.length != 4) {
            System.out.println("Ingrese nombre, apellido, telefono y email del cliente separados por -");
            datosCliente = inputUsuario.nextLine().split("-");
        }

        nombre = Verificador.verificarLetras(datosCliente[0], inputUsuario);
        apellido = Verificador.verificarLetras(datosCliente[1], inputUsuario);
        telefono = Verificador.verificarTelefono(datosCliente[2], inputUsuario);
        email = Verificador.verificarEmail(datosCliente[3], inputUsuario);

        return new Cliente(clave.getA().toString(), (int) clave.getB(), nombre, apellido, telefono, email);
    }

    public void modificarCliente(Scanner inputUsuario) {
        // Metodo que pide al usuario que quiere modificar del cliente y lo modifica
        Cliente cliente;
        String datoModificado;
        int opcion;
        boolean seguir = true;

        while (seguir) {
            System.out.println(
                    "Ingrese el tipo y numero de documento del cliente a modificar separada por -. Ej TIPO-11111111");
            cliente = clientes.get(Verificador.verificarClaveCliente(inputUsuario.nextLine().split("-"), inputUsuario)
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

                // TODO refactorizar codigo; Metodo que imprima por pantalla con objecto y dado
                // un objeto se pase al switch
                switch (opcion) {
                    case 1:
                        datoModificado = cliente.getNombre();
                        System.out.println("Ingrese nuevo nombre para el cliente");
                        cliente.setNombre(Verificador.verificarLetras(inputUsuario.nextLine(), inputUsuario));
                        Logger.log(
                                "Se cambio el nombre del cliente de: " + datoModificado + " a: " + cliente.getNombre());
                        break;
                    case 2:
                        datoModificado = cliente.getApellido();
                        System.out.println("Ingrese nuevo apellido para el cliente");
                        cliente.setApellido(Verificador.verificarLetras(inputUsuario.nextLine(), inputUsuario));
                        Logger.log("Se cambio el apellido de: " + datoModificado + " a: " + cliente.getApellido());
                        break;
                    case 3:
                        datoModificado = Integer.toString(cliente.getTelefono());
                        System.out.println("Ingrese nuevo telefono para el cliente");
                        cliente.setTelefono(Verificador.verificarTelefono(inputUsuario.nextLine(), inputUsuario));
                        Logger.log("Se cambio el telefono de: " + datoModificado + "a: " + cliente.getTelefono());
                        break;
                    case 4:
                        datoModificado = cliente.getEmail();
                        System.out.println("Ingrese nuevo email para el cliente");
                        cliente.setEmail(Verificador.verificarEmail(inputUsuario.nextLine(), inputUsuario));
                        Logger.log("Se cambio el email" + datoModificado + " a: " + cliente.getEmail());
                        break;
                    default:
                        System.out.println("Opcion ingresada incorrecta");
                }
            } else {
                System.out.println("No existe cliente con la clave ingresada");
            }

            seguir = !deseaSalir(inputUsuario);
        }
    }

    public void eliminarCliente(Scanner inputUsuario) {
        // Metodo que pide al usuario que cliente desea eliminar del sistema
        String clave;
        Cliente clienteAEliminar;
        boolean seguir = true;

        while (seguir) {
            System.out.println(
                    "Ingrese el tipo y numero de documento del cliente a eliminar separada por -. Ej TIPO-11111111");
            clave = Verificador.verificarClaveCliente(inputUsuario.nextLine().split("-"), inputUsuario)
                    .toConcatString();
            clienteAEliminar = clientes.get(clave);
            // Si el cliente esta en el sistema
            if (clienteAEliminar != null) {
                clientes.remove(clave);
                System.out.println("Se elimino el cliente " + clienteAEliminar);
            } else {
                System.out.println(errorInput);
            }

            seguir = !deseaSalir(inputUsuario);
        }
    }

    // * Rutas
    public void darAltaRuta(Scanner inputUsuario) {
        // Metodo que da de alta una ruta
        int[] ciudad;
        int cantCiudades = 2;
        double cantKms;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de las ciudades separados por -. Ej 1111-2222");
            ciudad = Utilidades.toIntArray(inputUsuario.nextLine().split("-"), cantCiudades, inputUsuario);
            System.out.println("Ingrese la cantidad de kilometros de la ruta");

            cantKms = Verificador.verificarDouble(inputUsuario.nextLine(), "cantidad de kms", inputUsuario);
            agregarRuta(ciudad[0], ciudad[1], cantKms);

            Logger.log("Se dio de alta la ruta que une " + ciudad[0] + " y " + ciudad[1] + " con " + cantKms
                    + " kilometros");

            seguir = !deseaSalir(inputUsuario);
        }
    }

    public void agregarRuta(int origen, int destino, double cantKms) {
        mapaRutas.insertarArco(origen, destino, cantKms);
    }

    public void darBajaRuta(Scanner inputUsuario) {
        // Metodo que da de baja una ruta si es que existe en el sistema
        int[] ciudad;
        int cantCiudades = 2;
        double cantKms;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de las ciudades separados por -. Ej 1111-2222");
            ciudad = Utilidades.toIntArray(inputUsuario.nextLine().split("-"), cantCiudades, inputUsuario);
            System.out.println("Ingrese la cantidad de kilometros de la ruta");

            cantKms = Verificador.verificarDouble(inputUsuario.nextLine(), "cantidad de kms", inputUsuario);
            if (mapaRutas.eliminarArco(ciudad[0], ciudad[1], cantKms)) {

                Logger.log("Se elimino la ruta que unia " + ciudad[0] + " y " + ciudad[1] + " con " + cantKms
                        + " kilometros");
            }

            seguir = !deseaSalir(inputUsuario);
        }
    }

    public void modificarRuta(Scanner inputUsuario) {
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

            cantKms = Verificador.verificarDouble(inputUsuario.nextLine(), "cantidad de kms", inputUsuario);

            if (mapaRutas.eliminarArco(ciudad[0], ciudad[1], cantKms)) {
                System.out.println("Ingrese la nueva cantidad de kilometros de la ruta");
                nuevoKms = Verificador.verificarDouble(inputUsuario.nextLine(), "cantidad de kms", inputUsuario);
                extracted(ciudad, nuevoKms);

                Logger.log("Se modifico la distancia de la ruta que unia " + ciudad[0] + " y " + ciudad[1] + " con "
                        + cantKms + " kilometros a " + nuevoKms + " kilometros");
            }
            seguir = !deseaSalir(inputUsuario);
        }
    }

    // * PEDIDOS
    public void darAltaPedido(Scanner inputUsuario) {
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
    public void consultasCliente(Scanner inputUsuario) {
        // Muestra toda la info de los clientes
        Cliente clienteObtenido;
        boolean seguir = true;

        while (seguir) {
            System.out.println(
                    "Ingrese tipo y numero de documento del cliente separados por - , en formato TIPO-NUMEROS");

            clienteObtenido = (Cliente) clientes.get(Verificador
                    .verificarClaveCliente(inputUsuario.nextLine().split("-"), inputUsuario).toConcatString());

            System.out.println(((clienteObtenido == null) ? "No se existe cliente con clave\n"
                    : "El cliente es: \n" + clienteObtenido.toString()));

            seguir = !deseaSalir(inputUsuario);
        }
    }

    // * Consultas sobre ciudades
    public void consultaCiudad(Scanner inputUsuario) {
        // Muestra toda la info de una ciudad dada la clave
        Ciudad ciudadObtenida;
        int codigoPostal;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese el codigo postal de la ciudad");
            codigoPostal = Verificador.verificarCodigoPostal(inputUsuario.nextLine(), inputUsuario);
            ciudadObtenida = (Ciudad) ciudades.obtenerElemento(codigoPostal);

            System.out.println(((ciudadObtenida == null) ? "No se existe cliente con clave\n"
                    : "El cliente es: \n" + ciudadObtenida.toString()));
            seguir = !deseaSalir(inputUsuario);
        }
    }

    public void listarCiudades(Scanner inputUsuario) {
        // Muestra un listado de las ciudades con un prefijo dado por usuario
        Lista ciudadesObtenidas;
        Par rango;
        int prefijo;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese un prefijo numerico que no exceda 4 caracteres");
            prefijo = Verificador.verificarPrefijo(inputUsuario.nextLine(), inputUsuario);

            rango = Utilidades.obtenerRango(prefijo);
            ciudadesObtenidas = ciudades.listarRango((int) rango.getA(), (int) rango.getB());

            // Muestro la lista de las ciudades obtenidas
            System.out.println("Las ciudades dentro del rango [" + rango.getA() + " - " + rango.getB() + "] son:\n"
                    + ciudadesObtenidas.toString());

            seguir = !deseaSalir(inputUsuario);
        }
    }

    public boolean deseaSalir(Scanner input) {
        // Metodo que pregunta al usuario si desea salir y retorna true si la respuesta
        // es Si
        System.out.println("Desea salir? S/N");
        return input.nextLine().toUpperCase().equals("S");
    }

    // * Consultas viajes
    public void caminoMenosCiudades(Scanner inputUsuario) {
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

    public void caminoMenosKilometros(Scanner inputUsuario) {
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

    public void caminoTresCiudades(Scanner inputUsuario) {
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

    public void esPosibleConKilometros(Scanner inputUsuario) {
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
            kilometros = Verificador.verificarDouble(inputUsuario.nextLine(), "cantidad de kms", inputUsuario);

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
    public void obtenerEspacioFaltante(Scanner inputUsuario) {
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

    private Par obtenerParListaEspacio(int origen, int destino, double espacioCamion) {
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

    private Lista crearYFiltrar(Lista lista, int destino) {
        int i = 1, longitud = lista.longitud();
        Lista resultado = new Lista();
        Solicitud aux;

        while (i <= longitud) {
            aux = (Solicitud) lista.recuperar(i);
            if (aux.getCiudadDestino() == destino) {
                resultado.insertar(aux, 1);
            }
            i++;
        }
        return resultado;
    }

    private void filtrarSolicitudesEspacio(Lista aFiltrar, Lista listaFinal, double espacio) {
        // Metodo que filtra una l ista de solicitudes por ciudad y espacio
        int i = 1, longitud = aFiltrar.longitud();
        Solicitud aux;

        while (i <= longitud) {
            aux = (Solicitud) aFiltrar.recuperar(i);
            if (aux.getMetrosCubicos() <= espacio) {
                listaFinal.insertar(aux, 1);
            }
            i++;
        }
    }

    private double calcularEspacioFaltante(Lista lista, double espacioCamion) {
        // Metodo que retorna el espacio faltante(positivo) o sobrante(negativo) de un
        // camion
        int i = 0, longitud = lista.longitud();
        double resultado = -espacioCamion;
        Solicitud aux;

        while (i < longitud) {
            aux = (Solicitud) lista.recuperar(i);
            resultado += aux.getMetrosCubicos();
        }

        return resultado;
    }

    public void verificarEspacioListarSolicitudes(Scanner inputUsuario) {
        Lista camino, posiblesSolicitudes;
        Par solicitudesEspacio;
        int[] codigoPostal;
        int cantCiudades = 2;
        boolean seguir = true;
        double espacioCamion;

        while (seguir) {
            System.out.println("Ingrese codigos postales de la ciudades separadas por un -. Ej XXXX-YYYY");
            codigoPostal = Utilidades.toIntArray(inputUsuario.nextLine().split("-"), cantCiudades, inputUsuario);

            camino = (Lista) mapaRutas.caminoMasCorto(codigoPostal[0], codigoPostal[1]).getB();

            if (!camino.esVacia()) {
                System.out.println("Ingrese cantidad de espacio en el camion en metros cubicos");
                espacioCamion = Verificador.verificarDouble(inputUsuario.nextLine(), "cantidad de metros cubicos",
                        inputUsuario);
                // Obtengo un par con las solicitudes y el espacio del camion
                solicitudesEspacio = obtenerParListaEspacio(codigoPostal[0], codigoPostal[1], espacioCamion);
                // MENSAJE DE TEST
                System.out.println(">>> Los pedidos son: " + solicitudesEspacio.getA().toString()
                        + " el espacio del camion es de " + solicitudesEspacio.getB().toString());

                if ((double) solicitudesEspacio.getB() < 0) {
                    // Hay espacio
                    posiblesSolicitudes = obtenerPosiblesSolicitudes(camino, codigoPostal[0],
                            Math.abs((double) solicitudesEspacio.getB()));
                    System.out.println("Los pedidos que se pueden satisfacer a lo largo de todo el tramo son: "
                            + posiblesSolicitudes);
                } else {
                    // No hay espacio
                    System.out.println("No hay espacio por ende no se pueden listar posibles pedidos a satisfacer");
                }
            }
            seguir = !deseaSalir(inputUsuario);
        }
    }

    private Lista obtenerPosiblesSolicitudes(Lista camino, int origen,
            double espacioDisponible) {
        // Metodo que obtiene todas las posibles solicitudes que se pueden satisfacer de
        // origen a las ciudades que le siguen en el camino dado
        int indiceActual = camino.localizar(origen), indiceDestino = indiceActual + 1;
        int ciudadActual, destinoActual, longitud = camino.longitud();
        Lista solicitudesCiudadActual, posiblesSolicitudes = new Lista();

        while (indiceActual < indiceDestino) {
            ciudadActual = (int) camino.recuperar(indiceActual);
            solicitudesCiudadActual = (Lista) solicitudesViajes.obtenerElemento(ciudadActual);

            while (indiceDestino <= longitud) {
                destinoActual = (int) camino.recuperar(indiceDestino);

                if (!(indiceActual == 1 && indiceDestino == longitud)) {
                    filtrarSolicitudesEspacio(crearYFiltrar(solicitudesCiudadActual, destinoActual),
                            posiblesSolicitudes, espacioDisponible);
                }
                indiceDestino++;
            }
            indiceActual++;
        }
        return posiblesSolicitudes;
    }

    private Solicitud obtenerSolicitudMenorEspacio(Lista solicitudes) {
        // Metodo que retorna la solicitud con menor espacio entre la lista de
        // solicitudes
        double menorEspacio = Double.MAX_VALUE;
        int i = 1, longitud = solicitudes.longitud();
        Solicitud solicitudMenorEspacio = null, solicitudActual;

        while (i <= longitud) {
            solicitudActual = (Solicitud) solicitudes.recuperar(i);
            if (solicitudActual.getMetrosCubicos() < menorEspacio) {
                solicitudMenorEspacio = solicitudActual;
            }
            i++;
        }

        return solicitudMenorEspacio;
    }

    public void verificarCaminoPerfecto(Scanner inputUsuario) {
        // Metodo que verifica si existe un camino perfecto con una lista de ciudades,
        // dadas las ciudades y el espacio del camion por el usuario
        boolean seguir = true, esPerfecto = true;
        int cantCiudades = 2, caminoElegido;
        double espacioCamion;
        Lista caminoCiudades;
        int[] codigoPostal;

        while (seguir) {
            System.out.println("Ingrese codigos postales de la ciudades separadas por un -. Ej XXXX-YYYY");
            codigoPostal = Utilidades.toIntArray(inputUsuario.nextLine().split("-"), cantCiudades, inputUsuario);

            // TODO HACER UN METODO QUE LISTE LOS CAMINOS POSIBLES ENTRE 2 CIUDADES
            caminoCiudades = mapaRutas.listarCaminosPosibles(codigoPostal[0], codigoPostal[0], codigoPostal[1]);
            if (!caminoCiudades.esVacia()) {
                System.out.println("Ingrese cantidad de espacio en el camion en metros cubicos");
                espacioCamion = Verificador.verificarDouble(inputUsuario.nextLine(), "cantidad de metros cubicos",
                        inputUsuario);

                while (esPerfecto) {
                    System.out.println("Los caminos posibles son: " + caminoCiudades.toString()
                            + "\nIngrese un numero para elegir el camino a verificar");
                    caminoElegido = Verificador.verificarInts(inputUsuario.nextLine(), "entero", inputUsuario);

                    if (caminoElegido > 0 && caminoElegido <= caminoCiudades.longitud()) {
                        // Se verifica si es perfecto
                        esPerfecto = esCaminoPerfecto((Lista) caminoCiudades.recuperar(caminoElegido), espacioCamion);
                        System.out.println("El camino " + caminoCiudades.recuperar(caminoElegido).toString()
                                + " con un espacio de: " + espacioCamion + "metros cubicos\nEs perfecto? : "
                                + esPerfecto);
                    } else {
                        System.out.println("Opcion ingresada invalida");
                    }

                    seguir = !deseaSalir(inputUsuario);
                }
            }
            seguir = !deseaSalir(inputUsuario);
        }
    }

    private boolean esCaminoPerfecto(Lista camino, double espacio) {
        /*
         * Metodo que segun un camino retorna un boolean para saber si es perfecto
         * Un camino es un camino que existe en el grafo y que hay por lo menos una
         * solicitud que se pueda transportar entre las ciudades por las cuales pasará
         * el camión
         */
        boolean esPerfecto = true;
        double espacioActual = espacio;
        int i = 1, longitud = camino.longitud(), ciudadActual;
        Lista solicitudesCiudadActual;
        Solicitud solicitudMenorEspacio;

        while (i < longitud || esPerfecto) {
            ciudadActual = (int) camino.recuperar(i);

            solicitudesCiudadActual = obtenerPosiblesSolicitudes(camino, ciudadActual, espacioActual);
            solicitudMenorEspacio = obtenerSolicitudMenorEspacio(solicitudesCiudadActual);
            // Podria obtener solo el espacio de la solicitud y no toda la solicitud
            if (solicitudMenorEspacio.getMetrosCubicos() <= espacioActual) {
                espacioActual -= solicitudMenorEspacio.getMetrosCubicos();
            } else {
                esPerfecto = false;
            }
            i++;
        }
        return esPerfecto;
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
