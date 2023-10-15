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

    public String mostrarSistema() {
        // Metodo que retorna en un string las estructuras del sistema tal cual esta en
        // el momento de ser llamado
        return "ArbolAVL con info de ciudades\n" + this.ciudades.toString()
                + "\nArbolAVL con solicitudes originadas de una ciudad\n" + this.solicitudesViajes.toString()
                + "\nGrafo, representando el mapa de rutas\n" + this.mapaRutas.toString()
                + "\nHashMap con info de clientes\n" + this.clientes.toString();
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
    public static void menuVerificarViajes() {
        System.out.println("""
                ======== Dadas 2 ciudades X e Y ========
                    1 - Mostrar todos los pedidos y calcular el espacio total faltante en el camion
                    2 - Verificar si sobra espacio en el camion y listar posibles solicitudes a ciudades
                        intermedias que se podrian aprovechar a cubrir (Considerando el camino mas corto en kms)
                    ======= Dada una lista de ciudades =======
                    3 - Verificar si es un camino perfecto
                        """);
    }

    private static void menuModificacionCiudad() {
        System.out.println("""
                    Que desea modificar?\n
                1 - Nombre de la ciudad\n
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
    public static void operacionABM(String objeto) {
        System.out.printf("1 - Dar alta %s\n2 - Dar baja %s\n3 - Modificar %s\n0 - Salir\n", objeto, objeto,
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
                menuModificacionCiudad();

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
                menuModificacionCliente();

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

    public void darBajaCliente(Scanner inputUsuario) {
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
                agregarRuta(ciudad[0], ciudad[1], nuevoKms);

                Logger.log("Se modifico la distancia de la ruta que unia " + ciudad[0] + " y " + ciudad[1] + " con "
                        + cantKms + " kilometros a " + nuevoKms + " kilometros");
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
            ciudad = solicitarOrigenDestino(inputUsuario);

            // Si existe un camino y por ende las ciudades en el sistema
            if (mapaRutas.existeCamino(ciudad[0], ciudad[1])) {
                // Pedir clave del cliente, y verificar que exista en el sistema
                clave = solicitarClaveCliente(inputUsuario);
                // Podria tomar que exista el cliente o saltarlo y directamente agregar una
                // solicitud sin que exista el cliente seria muy raro
                if (clientes.get(clave.toConcatString()) != null) {
                    agregarSolicitud(ciudad[0], crearSolicitud(ciudad[1], clave.toConcatString(), inputUsuario));
                } else {
                    // Error no existe cliente
                }
            }
            seguir = !deseaSalir(inputUsuario);
        }
    }

    private int[] solicitarOrigenDestino(Scanner inputUsuario) {
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

    public Solicitud crearSolicitud(int destino, String claveCliente, Scanner inputUsuario) {
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

        return new Solicitud(destino, fecha, claveCliente, cantM, cantBultos, domRetiro, domEntrega, estado);
    }

    public void agregarSolicitud(int origen, Solicitud solicitud) {
        // Agrega solicitud al sistema y loggea la misma
        ciudades.insertar(origen, solicitud);
        Logger.log("Se agrego solicitud: " + solicitud + " a ciudad: " + origen);
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
            ciudad = solicitarOrigenDestino(inputUsuario);

            if (mapaRutas.existeCamino(ciudad[0], ciudad[1])) {
                clave = solicitarClaveCliente(inputUsuario);
                // Filtro las solicitudes para ver si existe alguna que el usuario quiera
                // modificar
                solicitudesFiltradas = Utilidades.filtrarConCiudadYCliente((Lista) ciudades.obtenerElemento(ciudad[0]),
                        ciudad[1], clave.toConcatString());

                if (!solicitudesFiltradas.esVacia()) {
                    mostrarOpcionesSolicitudes(ciudad, clave, solicitudesFiltradas);
                    numeroSolicitud = inputUsuario.nextInt();
                    inputUsuario.next();

                    if (numeroSolicitud > 0 && numeroSolicitud <= solicitudesFiltradas.longitud()) {
                        solicitudElegida = (Solicitud) solicitudesFiltradas.recuperar(numeroSolicitud);
                        // TODO hacer esto un metodo separado, y usar clase verificador
                        menuModificacionSolicitud();
                        operacionElegida = inputUsuario.nextInt();
                        inputUsuario.next();

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
                }
            }
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
            ciudad = solicitarOrigenDestino(inputUsuario);

            if (mapaRutas.existeCamino(ciudad[0], ciudad[1])) {
                claveCliente = solicitarClaveCliente(inputUsuario);

                if (clientes.get(claveCliente.toConcatString()) != null) {
                    // Listo todas las solicitudes del la ciudad origen a destino con el cliente tal
                    solicitudesCiudad = (Lista) ciudades.obtenerElemento(ciudad[0]);
                    solicitudesFiltradas = Utilidades.filtrarConCiudadYCliente(solicitudesCiudad,
                            ciudad[1], claveCliente.toConcatString());

                    mostrarOpcionesSolicitudes(ciudad, claveCliente, solicitudesFiltradas);

                    opcion = inputUsuario.nextInt();
                    solElegida = (Solicitud) solicitudesFiltradas.recuperar(opcion);

                    if (solicitudesCiudad.eliminarElemento(solElegida)) {
                        Logger.log("Se elimino la solicitud: " + solElegida + " con origen: " + ciudad[0]);
                    }
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

    public void ciudadABM(Scanner inputUsuario) {
        // Metodo que presenta las operaciones de ABM de las ciudades
        boolean seguir = true;
        int opcion;

        do {
            operacionABM("ciudad");
            opcion = inputUsuario.nextInt();
            inputUsuario.nextLine();

            switch (opcion) {
                case 0:
                    seguir = true;
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
            }
        } while (seguir);
    }

    public void redRutasABM(Scanner inputUsuario) {
        // Metodo que presenta las operaciones de ABM de la red de rutas
        boolean seguir = true;
        int opcion;

        do {
            operacionABM("ruta");
            opcion = inputUsuario.nextInt();
            inputUsuario.nextLine();

            switch (opcion) {
                case 0:
                    seguir = true;
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
            }
        } while (seguir);
    }

    public void clientesABM(Scanner inputUsuario) {
        // Metodo que presenta las operaciones de ABM de los clientes
        boolean seguir = true;
        int opcion;

        do {
            operacionABM("cliente");
            opcion = inputUsuario.nextInt();
            inputUsuario.nextLine();

            switch (opcion) {
                case 0:
                    seguir = true;
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
            }
        } while (seguir);
    }

    public void solicitudesABM(Scanner inputUsuario) {
        // Metodo que presenta las operaciones de ABM de las solicitudes/pedidos
        boolean seguir = true;
        int opcion;

        do {
            operacionABM("pedidos");
            opcion = inputUsuario.nextInt();
            inputUsuario.nextLine();

            switch (opcion) {
                case 0:
                    seguir = true;
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
            }
        } while (seguir);
    }

    public void operacionesABM(Scanner inputUsuario) {
        boolean seguir = true;
        int opcion;

        do {
            menuABM();
            opcion = inputUsuario.nextInt();
            inputUsuario.nextLine();

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
                    System.out.println(errorInput);

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
            opcion = inputUsuario.nextInt();
            inputUsuario.nextLine();

            switch (opcion) {

            }

        } while (seguir);

    }

    public void iniciarMenu() {
        boolean seguir = true;
        int opcion;
        Scanner inputUsuario = new Scanner(System.in);

        // Mostrar menu
        do {
            // Muestro las opciones
            menuPrincipal();
            opcion = inputUsuario.nextInt();
            inputUsuario.nextLine();
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
                    // Hacer cosas
                    break;
                default:
                    System.out.println("\nNO EXISTE OPCION INGRESADA\n");
            }
        } while (seguir);
        // loggea el estado final del sistema al terminar
        Logger.loggearSistema(this);
    }
}
