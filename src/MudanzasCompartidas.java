import java.util.HashMap;
import java.util.Scanner;

import estructuras.arbolAVL.ArbolAVL;
import estructuras.grafo.Grafo;
import estructuras.grafo.NodoVert;
import estructuras.lineales.Cola;
import estructuras.lineales.Lista;
import estructuras.lineales.Par;
import objetos.Ciudad;
import objetos.Cliente;
import objetos.Solicitud;

public class MudanzasCompartidas {

    private ArbolAVL ciudades;
    private HashMap<String, Lista> solicitudesViajes;
    private Grafo mapaRutas;
    private HashMap<String, Cliente> clientes;

    // Mensajes de error
    private static String ERROR_INPUT = "ERROR clave ingresada erronea o no existente";
    private static String ERROR_EXISTENCIA = "ERROR ya existe en el sistema";
    private static String ERROR_OPCION = "ERROR opcion ingresada inexistente";

    public MudanzasCompartidas() {
        this.ciudades = new ArbolAVL();
        this.solicitudesViajes = new HashMap<String, Lista>();
        this.mapaRutas = new Grafo();
        this.clientes = new HashMap<String, Cliente>();
    }

    // Getters
    public ArbolAVL getCiudades() {
        return this.ciudades;
    }

    public HashMap<String, Lista> getSolicitudesViajes() {
        return this.solicitudesViajes;
    }

    public Grafo getMapaRutas() {
        return this.mapaRutas;
    }

    public HashMap<String, Cliente> getClientes() {
        return this.clientes;
    }

    public String mostrarSistema() {
        // Metodo que retorna en un string las estructuras del sistema tal cual esta en
        // el momento de ser llamado
        return "\nArbolAVL con info de ciudades\n" + this.ciudades.toKeyValueString()
                + "\nHashMap con solicitudes de viajes\n"
                + Utilidades.mostrarElementosHashmap(this.solicitudesViajes.toString())
                + "\nGrafo, representando el mapa de rutas\n" + this.mapaRutas.toString()
                + "\nHashMap con info de clientes\n"
                + Utilidades.mostrarElementosHashmap(this.clientes.toString() + "\n");
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
    public static void menuABM() {

        System.out.println("""
                1 - ABM Ciudades
                2 - ABM Red de rutas
                3 - ABM Clientes
                4 - ABM Pedidos
                0 - Salir
                """);
    }

    // Submenu Consultas
    public static void menuConsultas() {
        System.out.println("""
                ===== CLIENTE =====
                1 - Mostrar toda la informacion de un cliente dada la clave
                ===== CIUDAD =====
                2 - Mostrar informacion de una ciudad dada la clave
                3 - Listar ciudades dado un prefijo
                ===== VIAJE =====
                [ Siendo A, B, C ciudades ]
                4 - Obtener camino de A a B que pase por menos ciudades
                5 - Obtener camino de A a B con menor distancia en Kms
                6 - Obtener todos los caminos posibles de A a B que pasen por C
                7 - Verificar si es posible llegar de A a B en como maximo 'X' Kms
                ==========
                0 - Salir
                """);
    }

    // Submenu de VerificarViajes
    public static void menuVerificarViajes() {
        System.out.println("""
                ======== Dadas 2 ciudades X e Y ========
                1 - Mostrar todos los pedidos y calcular el espacio total faltante en el camion
                2 - Verificar si sobra espacio en el camion y listar posibles solicitudes a ciudades
                    intermedias que se podrian aprovechar a cubrir (Considerando el camino mas corto en kms)
                ======= Dada una lista de ciudades =======
                3 - Verificar si es un camino perfecto
                ==========
                0 - Salir
                """);
    }

    private static void menuModificacionCiudad() {
        System.out.println("""
                ======== Que desea modificar? ========
                1 - Nombre de la ciudad
                2 - Provincia de la ciudad""");
    }

    private static void menuModificacionCliente() {
        System.out.println("""
                    Ingrese la opcion correspondiente al atributo a modificar
                    1 - Nombre
                    2 - Apellido
                    3 - Telefono
                    4 - Email
                """);
    }

    private static void menuModificacionSolicitud() {
        System.out.println("""
                Que desea modificar?
                1 - Fecha de solicitud
                2 - Cantidad de metros cubicos
                3 - Cantidad de bultos
                4 - Domicilio de retiro
                5 - Domicilio de entrega
                6 - Estado de pago
                """);
    }

    // * Operaciones ABM
    // Ciudades, clientes, solicitudes
    private static void operacionABM(String objeto) {
        System.out.printf("1 - Dar alta %s\n2 - Dar baja %s\n3 - Modificar %s\n0 - Salir\n", objeto, objeto,
                objeto);
    }

    private static void solicitarInput(String atributo, String objeto) {
        System.out.println("Ingrese el " + atributo + " de " + objeto);
    }

    private static int solicitarCodigoPostal(Scanner inputUsuario) {
        System.out.println("Ingrese el codigo postal de la ciudad");
        return Verificador.verificarCodigoPostal(inputUsuario.nextLine(), inputUsuario);
    }

    private int[] solicitarCodigosPostales(Scanner inputUsuario) {
        // Metodo que solicita al usuario ciudad de origen y destino
        int cantCiudades = 2;

        System.out.println(
                "Ingrese los codigos postales de las ciudades de origen y destino separadas por - . EJ 1111-4444");
        return Utilidades.toIntArray(inputUsuario.nextLine().split("-"), cantCiudades, inputUsuario);
    }

    private Par solicitarClaveCliente(Scanner inputUsuario) {
        // Metodo que solicita al usuario que ingrese la clave de un cliente
        System.out.println("Ingrese tipo y numero de documento separados por -");
        return Verificador.verificarClaveCliente(inputUsuario.nextLine().split("-"), inputUsuario);
    }

    // * CIUDADES
    public void darAltaCiudad(Scanner inputUsuario) {
        // Metodo que pide datos al usuario para dar de alta una ciudad
        Ciudad ciudadUsuario;
        boolean seguir = true;
        int codigoPostal;

        while (seguir) {
            codigoPostal = solicitarCodigoPostal(inputUsuario);

            if (this.ciudades.obtenerElemento(codigoPostal) == null) {
                System.out.println(
                        "Ingrese nombre de la ciudad y provincia separadas por -. \nEj: Ciudad-Provincia");
                ciudadUsuario = crearCiudad(inputUsuario.nextLine().split("-"), codigoPostal, inputUsuario);

                agregarCiudad(ciudadUsuario);
                System.out.println("Creacion exitosa");
                Logger.log("Se creo ciudad: " + ciudadUsuario);
            } else {
                System.out.println(ERROR_EXISTENCIA);
            }
            seguir = !deseaSalir(inputUsuario);
        }
    }

    public void agregarCiudad(Ciudad ciudadUsuario) {
        // Agrega una ciudad a las estructuras del sistema
        int codigoPostal = ciudadUsuario.getCodigoPostal();

        this.ciudades.insertar(codigoPostal, ciudadUsuario);
        this.mapaRutas.insertarVertice(codigoPostal);
    }

    public Ciudad crearCiudad(String[] datosCiudad, int codigoPostal, Scanner inputUsuario) {
        // Metodo que verifica el input del usuario y crea una ciudad
        String nombre, provincia;

        while (datosCiudad.length != 2) {
            System.out.println("Ingrese nuevamente nombre de la ciudad y provincias separadas por -");
            datosCiudad = inputUsuario.nextLine().split("-");
        }

        nombre = Verificador.verificarPalabras(datosCiudad[0], "nombre", inputUsuario);
        provincia = Verificador.verificarPalabras(datosCiudad[1], "provincia", inputUsuario);

        return new Ciudad(codigoPostal, nombre, provincia);
    }

    public void darBajaCiudad(Scanner inputUsuario) {
        // Metodo que da de baja una ciudad en el sistema si es que existe
        int codigoPostal;
        boolean seguir = true;
        Ciudad ciudadAEliminar;

        while (seguir) {
            codigoPostal = solicitarCodigoPostal(inputUsuario);
            ciudadAEliminar = (Ciudad) ciudades.obtenerElemento(codigoPostal);

            // Se opto por obtener primero la ciudad y despues eliminarla para poder loggear
            // el objeto con sus datos (En vez de solo eliminarla de las estructuras y
            // loggear solo el codigo postal)
            if (ciudadAEliminar != null) {

                ciudades.eliminar(codigoPostal);
                mapaRutas.eliminarVertice(codigoPostal);
                // Si tiene solicitudes se eliminan del HM
                // Verifico que no queden solicitudes que no se puedan satisfacer debido a la
                // inexistencia del camino
                eliminarSolicitudesSinCamino();

                System.out.println("Eliminacion exitosa");
                Logger.log("Se elimino la ciudad: " + ciudadAEliminar);
            } else {
                System.out.println("No existe en el sistema");
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
            codigoPostal = solicitarCodigoPostal(inputUsuario);
            ciudad = (Ciudad) ciudades.obtenerElemento(codigoPostal);

            if (ciudad != null) {
                menuModificacionCiudad();

                opcion = Verificador.verificarInts(inputUsuario.nextLine(), "opcion", inputUsuario);
                switch (opcion) {
                    case 1:
                        datoModificado = ciudad.getNombre();
                        System.out.println("Ingrese nuevo nombre para la ciudad");
                        ciudad.setNombre(Verificador.verificarLetras(inputUsuario.nextLine(), "nombre", inputUsuario));
                        Logger.log("Se cambio el nombre de la ciudad" + datoModificado + " a: " + ciudad.getNombre());
                        break;
                    case 2:
                        datoModificado = ciudad.getProvincia();
                        System.out.println("Ingrese la nueva provincia para la ciudad");
                        ciudad.setProvincia(
                                Verificador.verificarLetras(inputUsuario.nextLine(), "provincia", inputUsuario));
                        Logger.log("Se cambio el nombre de la provincia" + datoModificado + " a: "
                                + ciudad.getProvincia());
                        break;
                    default:
                        System.out.println("Opcion ingresada incorrecta");
                }

            } else {
                System.out.println("No existe en el sistema");
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
            claveCliente = solicitarClaveCliente(inputUsuario);
            // Validar clave

            if (clientes.get(claveCliente.toConcatString()) == null) {
                System.out.println("Ingrese nombre, apellido, telefono y email del cliente separados por -");
                cliente = crearCliente(inputUsuario.nextLine().split("-"), claveCliente, inputUsuario);

                // Lo agrego al sistema
                agregarCliente(cliente);
                Logger.log("Se creo el cliente: " + cliente);
                System.out.println("Creacion exitosa");
            } else {
                System.out.println(ERROR_EXISTENCIA);
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
        String nombre, apellido, email, telefono;

        while (datosCliente.length != 4) {
            System.out.println("Ingrese nombre, apellido, telefono y email del cliente separados por -");
            datosCliente = inputUsuario.nextLine().split("-");
        }

        nombre = Verificador.verificarLetras(datosCliente[0], "nombre", inputUsuario);
        apellido = Verificador.verificarLetras(datosCliente[1], "apellido", inputUsuario);
        telefono = Verificador.verificarTelefono(datosCliente[2], inputUsuario);
        email = Verificador.verificarEmail(datosCliente[3], inputUsuario);

        return new Cliente(clave.getA().toString(), (int) clave.getB(), apellido, nombre, telefono, email);
    }

    public void modificarCliente(Scanner inputUsuario) {
        // Metodo que pide al usuario que quiere modificar del cliente y lo modifica
        Cliente cliente;
        String datoModificado, claveCliente, objeto = "cliente";
        int opcion;
        boolean seguir = true;

        while (seguir) {
            claveCliente = solicitarClaveCliente(inputUsuario).toConcatString();
            cliente = clientes.get(claveCliente);

            if (cliente != null) {
                menuModificacionCliente();
                opcion = Verificador.verificarInts(inputUsuario.nextLine(), "opcion", inputUsuario);

                switch (opcion) {
                    case 1:
                        datoModificado = cliente.getNombre();
                        solicitarInput("nombre", objeto);
                        cliente.setNombre(
                                Verificador.verificarPalabras(inputUsuario.nextLine(), "nombre", inputUsuario));
                        Logger.log(
                                "Se cambio el nombre del cliente de: " + datoModificado + " a: " + cliente.getNombre());
                        break;
                    case 2:
                        datoModificado = cliente.getApellido();
                        solicitarInput("apellido", objeto);
                        cliente.setApellido(
                                Verificador.verificarPalabras(inputUsuario.nextLine(), "apellido", inputUsuario));
                        Logger.log("Se cambio el apellido de: " + datoModificado + " a: " + cliente.getApellido());
                        break;
                    case 3:
                        datoModificado = cliente.getTelefono();
                        solicitarInput("telefono", objeto);
                        cliente.setTelefono(Verificador.verificarTelefono(inputUsuario.nextLine(), inputUsuario));
                        Logger.log("Se cambio el telefono de: " + datoModificado + "a: " + cliente.getTelefono());
                        break;
                    case 4:
                        datoModificado = cliente.getEmail();
                        solicitarInput("email", objeto);
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

    public void darBajaCliente(Scanner inputUsuario) {
        // Metodo que pide al usuario que cliente desea eliminar del sistema
        String clave;
        Cliente clienteAEliminar;
        boolean seguir = true;

        while (seguir) {
            clave = solicitarClaveCliente(inputUsuario).toConcatString();
            clienteAEliminar = clientes.get(clave);
            // Si el cliente esta en el sistema
            if (clienteAEliminar != null) {
                clientes.remove(clave);
                Logger.log("Se elimino el cliente con clave" + clienteAEliminar.getClave() + " con datos: "
                        + clienteAEliminar);
                System.out.println("Eliminacion exitosa");
            } else {
                System.out.println(ERROR_INPUT);
            }

            seguir = !deseaSalir(inputUsuario);
        }
    }

    // * Rutas
    public void darAltaRuta(Scanner inputUsuario) {
        // Metodo que da de alta una ruta
        int[] ciudad;
        double cantKms;
        boolean seguir = true;

        while (seguir) {
            ciudad = solicitarCodigosPostales(inputUsuario);

            // Si existen las dos ciudades en el sistema
            if (ciudades.pertenece(ciudad[0]) && ciudades.pertenece(ciudad[1])) {
                System.out.println("Ingrese la cantidad de kilometros de la ruta");

                cantKms = Verificador.verificarDouble(inputUsuario.nextLine(), "cantidad de kms", inputUsuario);
                agregarRuta(ciudad[0], ciudad[1], cantKms);

                System.out.println("Creacion exitosa");
                Logger.log("Se dio de alta la ruta que une " + ciudad[0] + " y " + ciudad[1] + " con " + cantKms
                        + " kilometros");
            } else {
                System.out.println(ERROR_EXISTENCIA);
            }
            seguir = !deseaSalir(inputUsuario);
        }
    }

    public void agregarRuta(int origen, int destino, double cantKms) {
        mapaRutas.insertarArco(origen, destino, cantKms);
    }

    public void darBajaRuta(Scanner inputUsuario) {
        // Metodo que da de baja una ruta si es que existe en el sistema
        int[] ciudad;
        double cantKms;
        boolean seguir = true;

        while (seguir) {
            ciudad = solicitarCodigosPostales(inputUsuario);

            // Si existen las ciudades en el sistema
            if (ciudades.pertenece(ciudad[0]) && ciudades.pertenece(ciudad[1])) {
                System.out.println("Ingrese la cantidad de kilometros de la ruta");
                cantKms = Verificador.verificarDouble(inputUsuario.nextLine(), "cantidad de kms", inputUsuario);

                if (mapaRutas.eliminarArco(ciudad[0], ciudad[1], cantKms)) {
                    // Elimino Pedidos que vayan de origen a destino si es que no existe otro camino
                    if (!mapaRutas.existeCamino(ciudad[0], ciudad[1])) {
                        eliminarSolicitudesSinCamino();
                    }
                    System.out.println("Eliminacion exitosa");
                    Logger.log("Se elimino la ruta que unia " + ciudad[0] + " y " + ciudad[1] + " con " + cantKms
                            + " kilometros");
                } else {
                    System.out.println("No existe ruta que una " + ciudad[0] + " y " + ciudad[1] + " con " + cantKms
                            + " cantidad de kms");
                }
            } else {
                System.out.println("No existen ciudades ingresadas");
            }

            seguir = !deseaSalir(inputUsuario);
        }
    }

    private void eliminarSolicitudesSinCamino() {
        // Metodo que recorre el HM de solicitudes buscando keys con el codigoPostal
        // para eliminarlo
        String ciudad[];
        Cola keysABorrar = new Cola();

        for (String key : this.solicitudesViajes.keySet()) {
            ciudad = key.split("\\|");

            if (!mapaRutas.existeCamino(Integer.parseInt(ciudad[0]), Integer.parseInt(ciudad[1]))) {
                keysABorrar.poner(key);
            }
        }

        while (!keysABorrar.esVacia()) {
            this.solicitudesViajes.remove(keysABorrar.obtenerFrente());
            Logger.log("Se eliminaron las solicitudes con origen|destino: " + keysABorrar.obtenerFrente());
            keysABorrar.sacar();
        }
    }

    public void modificarRuta(Scanner inputUsuario) {
        // Metodo que modifica una ruta especificada por el usuario si es que existe en
        // el sistema
        int[] ciudad;
        double cantKms, nuevoKms;
        boolean seguir = true;

        while (seguir) {
            ciudad = solicitarCodigosPostales(inputUsuario);

            System.out.println("Ingrese la cantidad de kilometros de la ruta");
            cantKms = Verificador.verificarDouble(inputUsuario.nextLine(), "cantidad de kms", inputUsuario);

            if (mapaRutas.eliminarArco(ciudad[0], ciudad[1], cantKms)) {
                System.out.println("Ingrese la nueva cantidad de kilometros de la ruta");
                nuevoKms = Verificador.verificarDouble(inputUsuario.nextLine(), "cantidad de kms", inputUsuario);
                agregarRuta(ciudad[0], ciudad[1], nuevoKms);

                System.out.println("Modificacion exitosa");
                Logger.log("Se modifico la distancia de la ruta que unia " + ciudad[0] + " y " + ciudad[1] + " con "
                        + cantKms + " kilometros a " + nuevoKms + " kilometros");
            } else {
                System.out.println("No existe ruta o ciudades en el sistema");
            }
            seguir = !deseaSalir(inputUsuario);
        }
    }

    // * PEDIDOS
    public void darAltaPedido(Scanner inputUsuario) {
        // Metodo que da de alta un pedido de una ciudad a otra
        Par clave;
        int[] ciudad;
        boolean seguir = true;

        while (seguir) {
            ciudad = solicitarCodigosPostales(inputUsuario);

            // Si existe un camino y por ende las ciudades en el sistema
            if (mapaRutas.existeCamino(ciudad[0], ciudad[1])) {
                // Pedir clave del cliente, y verificar que exista en el sistema
                clave = solicitarClaveCliente(inputUsuario);

                if (clientes.get(clave.toConcatString()) != null) {
                    agregarSolicitud(crearSolicitud(ciudad[0], ciudad[1], clave.toConcatString(), inputUsuario));
                    System.out.println("Creacion exitosa");

                } else {
                    System.out.println("ERROR no existe cliente");
                }
            } else {
                System.out.println("ERROR no existe camino entre ciudades");
            }
            seguir = !deseaSalir(inputUsuario);
        }
    }

    public Solicitud crearSolicitud(int origen, int destino, String claveCliente, Scanner inputUsuario) {
        // Metodo que pide datos al usuario, crea y devuelve una solicitud
        String fecha, domRetiro, domEntrega;
        boolean estado;
        double cantM;
        int cantBultos;

        System.out.println("Ingrese fecha en formato dd/mm/aaaa");
        fecha = Verificador.verificarFecha(inputUsuario.nextLine(), inputUsuario);
        System.out.println("Ingrese cantidad de metros cubicos de la solicitud");
        cantM = Verificador.verificarDouble(inputUsuario.nextLine(), "cantidad de metros cubicos", inputUsuario);
        System.out.println("Ingrese cantidad de bultos");
        cantBultos = Verificador.verificarInts(inputUsuario.nextLine(), "cantidad de bultos", inputUsuario);
        System.out.println("Ingrese el domicilio de retiro");
        domRetiro = Verificador.verificarDireccion(inputUsuario.nextLine(), inputUsuario);
        System.out.println("Ingrese el domicilio de entrega");
        domEntrega = Verificador.verificarDireccion(inputUsuario.nextLine(), inputUsuario);
        System.out.println("Ingrese si la solicitud esta paga o no, T/F");
        estado = Verificador.verificarEstadoPago(inputUsuario.nextLine(), inputUsuario);

        return new Solicitud(origen, destino, fecha, claveCliente, cantM, cantBultos, domRetiro, domEntrega, estado);
    }

    public void agregarSolicitud(Solicitud solicitud) {
        // Agrega solicitud al sistema y loggea la misma
        Lista solicitudes;
        String claveSolicitud = solicitud.getCiudadOrigen() + "|" + solicitud.getCiudadDestino();

        // Si no existe en el HM se crea
        if (!solicitudesViajes.containsKey(claveSolicitud)) {
            solicitudesViajes.put(claveSolicitud, new Lista());
        }

        solicitudes = solicitudesViajes.get(claveSolicitud);
        solicitudes.insertar(solicitud, 1);
        Logger.log("Se agrego solicitud: " + solicitud + " a ciudad: " + claveSolicitud);
    }

    public void modificarPedido(Scanner inputUsuario) {
        // Pedir ciudad de origen, destino y cliente; mostrar las solicitudes a
        // modificar, y que elija que modificar de cada solicitud
        Par clave;
        int[] ciudad;
        Lista solicitudesFiltradas;
        Solicitud solicitudElegida;
        int numeroSolicitud, operacionElegida;
        boolean seguir = true;

        while (seguir) {
            ciudad = solicitarCodigosPostales(inputUsuario);

            if (mapaRutas.existeCamino(ciudad[0], ciudad[1])) {
                clave = solicitarClaveCliente(inputUsuario);
                // Filtro las solicitudes para ver si existe alguna que el usuario quiera
                // modificar
                solicitudesFiltradas = Utilidades.filtrarConCliente(
                        (Lista) solicitudesViajes.get(ciudad[0] + "|" + ciudad[1]), clave.toConcatString());

                if (!solicitudesFiltradas.esVacia()) {
                    mostrarOpcionesSolicitudes(ciudad, clave, solicitudesFiltradas);
                    numeroSolicitud = Verificador.verificarInts(inputUsuario.nextLine(), "numero", inputUsuario);

                    if (numeroSolicitud > 0 && numeroSolicitud <= solicitudesFiltradas.longitud()) {
                        solicitudElegida = (Solicitud) solicitudesFiltradas.recuperar(numeroSolicitud);

                        menuModificacionSolicitud();
                        operacionElegida = Verificador.verificarInts(inputUsuario.nextLine(), "numero", inputUsuario);

                        switch (operacionElegida) {
                            case 1:
                                System.out.println("Ingrese nueva fecha");
                                solicitudElegida
                                        .setFechaSol(Verificador.verificarFecha(inputUsuario.nextLine(), inputUsuario));
                                break;
                            case 2:
                                System.out.println("Ingrese nueva cantidad de metros cubicos");
                                solicitudElegida.setMetrosCubicos(Verificador.verificarDouble(inputUsuario.nextLine(),
                                        "cantidad de metros cubicos", inputUsuario));
                                break;
                            case 3:
                                System.out.println("Ingrese nueva cantidad de bultos");
                                solicitudElegida.setCantBultos(Verificador.verificarInts(inputUsuario.nextLine(),
                                        "cantidad de bultos", inputUsuario));
                                break;
                            case 4:
                                System.out.println("Ingrese nuevo domicilio de retiro");
                                solicitudElegida.setDomicilioRetiro(
                                        Verificador.verificarDireccion(inputUsuario.nextLine(), inputUsuario));
                                break;
                            case 5:
                                System.out.println("Ingrese nuevo domicilio de entrega");
                                solicitudElegida.setDomicilioEntrega(
                                        Verificador.verificarDireccion(inputUsuario.nextLine(), inputUsuario));
                                break;
                            case 6:
                                System.out.println("Ingrese nuevo estado de pago (T/F)");
                                solicitudElegida.setEstaPago(
                                        Verificador.verificarEstadoPago(inputUsuario.nextLine(), inputUsuario));
                                break;
                            default:
                                System.out.println("ERROR");
                        }
                        Logger.log("Se modifico la solicitud: " + solicitudElegida);

                    } else {
                        // ERROR
                        System.out.println("Opcion invalida");
                    }
                } else {
                    System.out.println("No hay solicitudes para la ciudades ingresadas");
                }
            } else {
                System.out.println("No existe camino entre ciudades ingresadas");
            }

            seguir = !deseaSalir(inputUsuario);
        }
    }

    public void darBajaPedido(Scanner inputUsuario) {
        // Pedir ciudad de origen, destino y cliente; mostrar las solicitudes y que
        // elija cual eliminar
        int[] ciudad;
        Par claveCliente;
        boolean seguir = true;
        int opcion;
        Solicitud solElegida;
        Lista solicitudesCiudad, solicitudesFiltradas;

        while (seguir) {
            ciudad = solicitarCodigosPostales(inputUsuario);

            if (mapaRutas.existeCamino(ciudad[0], ciudad[1])) {
                claveCliente = solicitarClaveCliente(inputUsuario);

                solicitudesCiudad = (Lista) solicitudesViajes.get(ciudad[0] + "|" + ciudad[1]);
                solicitudesFiltradas = Utilidades.filtrarConCliente(solicitudesCiudad, claveCliente.toConcatString());

                if (!solicitudesFiltradas.esVacia()) {
                    // Listo todas las solicitudes del la ciudad origen a destino con el cliente tal
                    mostrarOpcionesSolicitudes(ciudad, claveCliente, solicitudesFiltradas);

                    opcion = Verificador.verificarInts(inputUsuario.nextLine(), "numero", inputUsuario);
                    solElegida = (Solicitud) solicitudesFiltradas.recuperar(opcion);

                    if (solicitudesCiudad.eliminarElemento(solElegida)) {
                        System.out.println("Eliminacion exitosa");
                        Logger.log("Se elimino la solicitud: " + solElegida + " con origen: " + ciudad[0]
                                + " y destino " + ciudad[1]);

                    } else {
                        System.out.println("ERROR opcion ingresada invalida");
                    }
                } else {
                    System.out.println("ERROR no hay solicitudes con las ciudades y clave ingresada");
                }
            }
            seguir = !deseaSalir(inputUsuario);
        }

    }

    private void mostrarOpcionesSolicitudes(int[] ciudad, Par claveCliente, Lista solicitudes) {
        System.out
                .println("Las solicitudes de " + ciudad[0] + " a " + ciudad[1] + " con el cliente con clave"
                        + claveCliente.toConcatString() + " son:\n" + solicitudes.enumerar()
                        + "\nIngrese el numero de la solicitud");
    }

    // * Consultas sobre clientes
    public void consultasCliente(Scanner inputUsuario) {
        // Muestra toda la info de los clientes
        Cliente clienteObtenido;
        String claveCliente;
        boolean seguir = true;

        while (seguir) {
            claveCliente = solicitarClaveCliente(inputUsuario).toConcatString();
            clienteObtenido = (Cliente) clientes.get(claveCliente);

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

            System.out.println(((ciudadObtenida == null) ? "No se existe ciudad con codigo postal ingresado\n"
                    : "La ciudad es: \n" + ciudadObtenida.toString()));
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

            codigoPostal = solicitarCodigosPostales(inputUsuario);

            ciudadOrigen = (Ciudad) ciudades.obtenerElemento(codigoPostal[0]);
            ciudadDestino = (Ciudad) ciudades.obtenerElemento(codigoPostal[1]);

            // Si las ciudades existen muestro el camino
            if (ciudadOrigen != null && ciudadDestino != null) {
                System.out.println("El camino que pasa por menos ciudades de "
                        + ciudadOrigen.getNombre() + " a " + ciudadDestino.getNombre()
                        + " es: " + mapaRutas.menorCaminoCantidadNodos(ciudadOrigen.getCodigoPostal(),
                                ciudadDestino.getCodigoPostal()).toString());
            } else {
                System.out.println(ERROR_INPUT);
            }
            seguir = !deseaSalir(inputUsuario);
        }
    }

    public void caminoMenosKilometros(Scanner inputUsuario) {
        // Metodo que pide al usuario que ingrese 2 codigos postales de ciudades y
        // muestra el camino con menos kilometros si es que existe
        int[] codigoPostal;
        boolean seguir = true;

        while (seguir) {
            codigoPostal = solicitarCodigosPostales(inputUsuario);

            System.out
                    .println("El camino mas corto es:\n"
                            + mapaRutas.caminoMasCorto(codigoPostal[0], codigoPostal[1]));

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

            System.out.println("Todos los caminos posibles:\n"
                    + mapaRutas.listarCaminosPosibles(codigoPostalInt[0], codigoPostalInt[1], codigoPostalInt[2])
                            .enumerar());

            seguir = !deseaSalir(inputUsuario);
        }
    }

    public void esPosibleConKilometros(Scanner inputUsuario) {
        // Metodo que verifica si es posible llegar de ciudad A a ciudad B con X kms o
        // menos
        int[] codigoPostal;
        Ciudad origen, destino;
        double kilometros;
        boolean seguir = true;

        while (seguir) {
            codigoPostal = solicitarCodigosPostales(inputUsuario);

            origen = (Ciudad) ciudades.obtenerElemento(codigoPostal[0]);
            destino = (Ciudad) ciudades.obtenerElemento(codigoPostal[1]);

            if (origen != null && destino != null) {
                System.out.println("Ingrese la cantidad de kilometros");
                kilometros = Verificador.verificarDouble(inputUsuario.nextLine(), "cantidad de kms", inputUsuario);

                System.out.format("Es posible viajar de %s a %s en %.2f kms : %b %n",
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
        boolean seguir = true;

        while (seguir) {

            codigoPostal = solicitarCodigosPostales(inputUsuario);

            if (mapaRutas.existeCamino(codigoPostal[0], codigoPostal[1])) {

                pedidosYEspacio = obtenerParListaEspacio(codigoPostal[0] + "|" + codigoPostal[1], 0);
                System.out.println("Todos los pedidos de: " + codigoPostal[0] + " a " + codigoPostal[1] + " son:\n"
                        + ((Lista) pedidosYEspacio.getA()).enumerar());

                System.out.println("El espacio faltante es de: " + pedidosYEspacio.getB() + " metros cubicos");
            } else {
                System.out.println("No existe camino entre las ciudades ingresadas");
            }
            seguir = !deseaSalir(inputUsuario);
        }
    }

    private Par obtenerParListaEspacio(String origenDestino, double espacioCamion) {
        // Metodo que obtiene la lista de todos los pedidos de origen a destino y
        // calcula el espacio faltante en el camion
        // Retornando un par con los resultados obtenidos
        Par resultado = new Par();
        Lista solicitudes = (Lista) solicitudesViajes.get(origenDestino);

        // Si no existen solicitudes en el HM
        if (solicitudes == null) {
            solicitudes = new Lista();
        }
        // Filtro las solicitudes con destino
        resultado.setA(solicitudes);
        resultado.setB(calcularEspacioFaltante((Lista) resultado.getA(), espacioCamion));

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
        int i = 1, longitud;
        double resultado = -espacioCamion;
        Solicitud aux;

        if (lista != null) {
            longitud = lista.longitud();

            while (i <= longitud) {
                aux = (Solicitud) lista.recuperar(i);
                if (aux != null) {
                    resultado += aux.getMetrosCubicos();
                }
                i++;
            }
        }

        return resultado;
    }

    public void verificarEspacioListarSolicitudes(Scanner inputUsuario) {
        Lista camino, posiblesSolicitudes;
        Par solicitudesEspacio;
        int[] codigoPostal;
        boolean seguir = true;
        double espacioCamion;

        while (seguir) {
            codigoPostal = solicitarCodigosPostales(inputUsuario);
            camino = (Lista) mapaRutas.caminoMasCorto(codigoPostal[0], codigoPostal[1]);

            if (!camino.esVacia()) {
                System.out.println("Ingrese cantidad de espacio en el camion en metros cubicos");
                espacioCamion = Verificador.verificarDouble(inputUsuario.nextLine(), "cantidad de metros cubicos",
                        inputUsuario);
                // Obtengo un par con las solicitudes y el espacio del camion
                solicitudesEspacio = obtenerParListaEspacio(codigoPostal[0] + "|" + codigoPostal[1],
                        espacioCamion);
                // TODO MENSAJE DE TEST
                System.out.println(">>> Los pedidos son:\n" + ((Lista) solicitudesEspacio.getA()).enumerar()
                        + " el espacio del camion es de: " + Math.abs((double) solicitudesEspacio.getB())
                        + "\nEl camino es : " + camino);

                if ((double) solicitudesEspacio.getB() < 0) {
                    // Hay espacio
                    posiblesSolicitudes = obtenerPosiblesSolicitudes(camino, codigoPostal[0],
                            Math.abs((double) solicitudesEspacio.getB()));
                    if (!posiblesSolicitudes.esVacia()) {
                        System.out.println("Los pedidos que se pueden satisfacer a lo largo de todo el tramo son:\n"
                                + posiblesSolicitudes.enumerar() + "\n");
                    } else {
                        System.out.println("No existen pedidos a satisfacer");
                    }
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
        String origenDestino;
        Lista solicitudesOrigenDestino, posiblesSolicitudes = new Lista();

        while (indiceActual < longitud) {
            // Obtengo CP ciudad origen
            ciudadActual = (int) ((NodoVert) camino.recuperar(indiceActual)).getElem();

            while (indiceDestino <= longitud) {
                // Obtengo CP de ciudad destino
                destinoActual = (int) ((NodoVert) camino.recuperar(indiceDestino)).getElem();
                origenDestino = ciudadActual + "|" + destinoActual;

                // No tiene en cuenta origen inicial y el destino final del camino
                if (!(indiceActual == 1 && indiceDestino == longitud)) {
                    solicitudesOrigenDestino = this.solicitudesViajes.get(origenDestino);
                    // Si existen solicitudes en el hashmap filtro
                    if (solicitudesOrigenDestino != null)
                        filtrarSolicitudesEspacio(solicitudesOrigenDestino, posiblesSolicitudes, espacioDisponible);
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
        boolean seguir = true, esPerfecto;
        int caminoElegido;
        double espacioCamion;
        Lista caminoCiudades;
        int[] codigoPostal;

        while (seguir) {
            // Solicita los codigos postales al usuario y obtiene los caminos
            codigoPostal = solicitarCodigosPostales(inputUsuario);
            caminoCiudades = mapaRutas.listarCaminosPosibles(codigoPostal[0], codigoPostal[1]);

            // Si existen caminos entre las dos ciudades
            if (!caminoCiudades.esVacia()) {
                System.out.println("Ingrese cantidad de espacio en el camion en metros cubicos");
                espacioCamion = Verificador.verificarDouble(inputUsuario.nextLine(), "cantidad de metros cubicos",
                        inputUsuario);

                System.out.println("Los caminos posibles son:\n" + caminoCiudades.enumerar()
                        + "\nIngrese un numero para elegir el camino a verificar");

                // Solicita al usuario elegir uno de los caminos
                caminoElegido = Verificador.verificarInts(inputUsuario.nextLine(), "entero", inputUsuario);

                if (caminoElegido > 0 && caminoElegido <= caminoCiudades.longitud()) {
                    // Se verifica si es perfecto
                    esPerfecto = esCaminoPerfecto((Lista) caminoCiudades.recuperar(caminoElegido), espacioCamion);
                    System.out.println("El camino " + caminoCiudades.recuperar(caminoElegido).toString()
                            + " con un espacio de: " + espacioCamion + " metros cubicos\nEs perfecto? : "
                            + esPerfecto);
                } else {
                    System.out.println("Opcion ingresada invalida");
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

        while (i < longitud && esPerfecto) {
            ciudadActual = (int) ((NodoVert) camino.recuperar(i)).getElem();

            solicitudesCiudadActual = obtenerPosiblesSolicitudes(camino, ciudadActual, espacioActual);
            solicitudMenorEspacio = obtenerSolicitudMenorEspacio(solicitudesCiudadActual);
            // Podria obtener solo el espacio de la solicitud y no toda la solicitud
            if (solicitudMenorEspacio != null && solicitudMenorEspacio.getMetrosCubicos() <= espacioActual) {
                espacioActual -= solicitudMenorEspacio.getMetrosCubicos();
            } else {
                esPerfecto = false;
            }
            i++;
        }
        return esPerfecto;
    }

    public void ciudadABM(Scanner inputUsuario) {
        // Metodo que presenta las operaciones de ABM de las ciudades
        boolean seguir = true;
        int opcion;

        do {
            operacionABM("ciudad");
            opcion = Verificador.verificarInts(inputUsuario.nextLine(), "entero", inputUsuario);

            switch (opcion) {
                case 0:
                    seguir = false;
                    break;
                case 1:
                    darAltaCiudad(inputUsuario);
                    break;
                case 2:
                    darBajaCiudad(inputUsuario);
                    break;
                case 3:
                    modificarCiudad(inputUsuario);
                    break;
                default:
                    System.out.println(ERROR_OPCION);
            }
        } while (seguir);
    }

    public void redRutasABM(Scanner inputUsuario) {
        // Metodo que presenta las operaciones de ABM de la red de rutas
        boolean seguir = true;
        int opcion;

        do {
            operacionABM("ruta");
            opcion = Verificador.verificarInts(inputUsuario.nextLine(), "entero", inputUsuario);

            switch (opcion) {
                case 0:
                    seguir = false;
                    break;
                case 1:
                    darAltaRuta(inputUsuario);
                    break;
                case 2:
                    darBajaRuta(inputUsuario);
                    break;
                case 3:
                    modificarRuta(inputUsuario);
                    break;
                default:
                    System.out.println(ERROR_OPCION);
            }
        } while (seguir);
    }

    public void clientesABM(Scanner inputUsuario) {
        // Metodo que presenta las operaciones de ABM de los clientes
        boolean seguir = true;
        int opcion;

        do {
            operacionABM("cliente");
            opcion = Verificador.verificarInts(inputUsuario.nextLine(), "entero", inputUsuario);

            switch (opcion) {
                case 0:
                    seguir = false;
                    break;
                case 1:
                    darAltaCliente(inputUsuario);
                    break;
                case 2:
                    darBajaCliente(inputUsuario);
                    break;
                case 3:
                    modificarCliente(inputUsuario);
                    break;
                default:
                    System.out.println(ERROR_OPCION);
            }
        } while (seguir);
    }

    public void solicitudesABM(Scanner inputUsuario) {
        // Metodo que presenta las operaciones de ABM de las solicitudes/pedidos
        boolean seguir = true;
        int opcion;

        do {
            operacionABM("pedidos");
            opcion = Verificador.verificarInts(inputUsuario.nextLine(), "entero", inputUsuario);

            switch (opcion) {
                case 0:
                    seguir = false;
                    break;
                case 1:
                    darAltaPedido(inputUsuario);
                    break;
                case 2:
                    darBajaPedido(inputUsuario);
                    break;
                case 3:
                    modificarPedido(inputUsuario);
                    break;
                default:
                    System.out.println(ERROR_OPCION);
            }
        } while (seguir);
    }

    public void operacionesABM(Scanner inputUsuario) {
        boolean seguir = true;
        int opcion;

        do {
            menuABM();
            opcion = Verificador.verificarInts(inputUsuario.nextLine(), "entero", inputUsuario);

            switch (opcion) {
                case 0:
                    seguir = false;
                    break;
                case 1:
                    ciudadABM(inputUsuario);
                    break;
                case 2:
                    redRutasABM(inputUsuario);
                    break;
                case 3:
                    clientesABM(inputUsuario);
                    break;
                case 4:
                    solicitudesABM(inputUsuario);
                    break;
                default:
                    System.out.println(ERROR_OPCION);

            }
        } while (seguir);
    }

    public void consultas(Scanner inputUsuario) {
        // Metodo que muestra el menu de consultas y llama a los metodos
        // correspondientes
        boolean seguir = true;
        int opcion;

        do {
            menuConsultas();
            opcion = Verificador.verificarInts(inputUsuario.nextLine(), "numero", inputUsuario);

            switch (opcion) {
                case 0:
                    seguir = false;
                    break;
                case 1:
                    consultasCliente(inputUsuario);
                    break;
                case 2:
                    consultaCiudad(inputUsuario);
                    break;
                case 3:
                    listarCiudades(inputUsuario);
                    break;
                case 4:
                    caminoMenosCiudades(inputUsuario);
                    break;
                case 5:
                    caminoMenosKilometros(inputUsuario);
                    break;
                case 6:
                    caminoTresCiudades(inputUsuario);
                    break;
                case 7:
                    esPosibleConKilometros(inputUsuario);
                    break;
                default:
                    System.out.println(ERROR_OPCION);
            }
        } while (seguir);
    }

    public void verificarViajes(Scanner inputUsuario) {
        // Metodo que muestra el menu de verificar viajes y llama a los metodos
        // correspondientes
        boolean seguir = true;
        int opcion;

        do {
            menuVerificarViajes();
            opcion = Verificador.verificarInts(inputUsuario.nextLine(), "numero", inputUsuario);

            switch (opcion) {
                case 0:
                    seguir = false;
                    break;
                case 1:
                    obtenerEspacioFaltante(inputUsuario);
                    break;
                case 2:
                    verificarEspacioListarSolicitudes(inputUsuario);
                    break;
                case 3:
                    verificarCaminoPerfecto(inputUsuario);
                    break;
                default:
                    System.out.println(ERROR_OPCION);
            }
        } while (seguir);
    }

    public boolean iniciarMenu() {
        boolean seguir = true;
        int opcion;
        Scanner inputUsuario = new Scanner(System.in).useDelimiter(System.lineSeparator());

        // Mostrar menu
        do {
            // Muestro las opciones
            menuPrincipal();
            opcion = Verificador.verificarInts(inputUsuario.nextLine(), "numero", inputUsuario);
            // Lectura de la variable ingresada por usuario
            switch (opcion) {
                case 0:
                    seguir = false;
                    break;
                case 1:
                    System.out.println("Comienzo de la carga inicial");
                    Loader.cargarDatos(this);
                    System.out.println("Finalizacion de la carga inicial");
                    break;
                case 2:
                    operacionesABM(inputUsuario);
                    break;
                case 3:
                    consultas(inputUsuario);
                    break;
                case 4:
                    verificarViajes(inputUsuario);
                    break;
                case 5:
                    System.out.println(mostrarSistema());
                    break;
                default:
                    System.out.println(ERROR_OPCION);
            }
        } while (seguir);
        // loggea el estado final del sistema al terminar
        inputUsuario.close();
        Logger.loggearSistema(this);
        return seguir;
    }
}
