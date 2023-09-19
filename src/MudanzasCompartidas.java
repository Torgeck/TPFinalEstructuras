import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Scanner;

import estructuras.arbolAVL.ArbolAVL;
import estructuras.grafo.Grafo;
import estructuras.lineales.Lista;
import estructuras.lineales.Par;
import objetos.Ciudad;
import objetos.Cliente;

public class MudanzasCompartidas {

    final static int longitudCodigoPostal = 4;
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
    public static void verificarVIajes() {
        System.out.println("""

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
    public static void darAltaCiudad() {
        // Metodo que pide datos al usuario para dar de alta una ciudad
        Scanner inputUsuario = new Scanner(System.in);
        Ciudad ciudadUsuario;
        boolean seguir = true;
        int codigoPostal;

        while (seguir) {
            System.out.println("Ingrese el codigo postal de la ciudad");
            codigoPostal = convertirCodigoPostal(inputUsuario.nextLine(), inputUsuario);

            if (ciudades.obtenerElemento(codigoPostal) == null) {
                System.out.println(
                        "Ingrese nombre de la ciudad y provincia separadas por -. \nEj: Ciudad-Provincia");
                ciudadUsuario = crearCiudad(inputUsuario.nextLine(), codigoPostal, inputUsuario);

                ciudades.insertar(codigoPostal, ciudadUsuario);
                solicitudesViajes.insertar(codigoPostal, new Lista());
                mapaRutas.insertarVertice(codigoPostal);

            } else {
                System.out.println(errorExistencia);
            }
            seguir = !deseaSalir(inputUsuario);
        }
        inputUsuario.close();
    }

    public static Ciudad crearCiudad(String datosCiudad, int codigoPostal, Scanner inputUsuario) {
        // Metodo que verifica el input del usuario y crea una ciudad
        String[] arrInput = datosCiudad.split("-");

        while (arrInput.length != 2) {
            System.out.println("Ingrese nuevamente nombre de la ciudad y provincias separadas por -");
            arrInput = inputUsuario.nextLine().split("-");
        }

        return new Ciudad(codigoPostal, arrInput[0], arrInput[1]);
    }

    public static void darBajaCiudad() {
        // Metodo que da de baja una ciudad en el sistema si es que existe
        Scanner inputUsuario = new Scanner(System.in);
        int codigoPostal;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese el codigo postal de la ciudad que desea eliminar");
            codigoPostal = convertirCodigoPostal(inputUsuario.nextLine(), inputUsuario);

            if (ciudades.obtenerElemento(codigoPostal) != null) {

                ciudades.eliminar(codigoPostal);
                solicitudesViajes.eliminar(codigoPostal);
                mapaRutas.eliminarVertice(codigoPostal);
            } else {
                System.out.println(errorExistencia);
            }

            seguir = !deseaSalir(inputUsuario);
        }
        inputUsuario.close();
    }

    public static void modificarCiudad() {
        // Metodo que modifica una ciudad en el sistema si es que existe
        Scanner inputUsuario = new Scanner(System.in);
        Ciudad ciudad;
        boolean seguir = true;
        int codigoPostal, opcion;

        while (seguir) {
            // Pregunta que ciudad quiere modificar
            System.out.println("Ingrese el codigo postal de la ciudad a modificar");
            codigoPostal = convertirCodigoPostal(inputUsuario.nextLine(), inputUsuario);
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
                        ciudad.setNombre(inputUsuario.nextLine());
                        System.out.println("Se cambio el nombre de la ciudad a: " + ciudad.getNombre());
                        break;
                    case 2:
                        System.out.println("Ingrese la nueva provincia para la ciudad");
                        ciudad.setProvincia(inputUsuario.nextLine());
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
        inputUsuario.close();
    }

    // * CLIENTES

    public static void darAltaCliente() {
        // Metodo que da de alta un cliente si es que no existe en el sistema
        Scanner inputUsuario = new Scanner(System.in);
        Cliente cliente;
        String[] claveCliente;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese el tipo y numero de documento del cliente separada por -. Ej TIPO-11111111");
            claveCliente = verificarClaveCliente(inputUsuario.nextLine().split("-"), inputUsuario);
            // Validar clave

            if (clientes.get(claveCliente[0].concat(claveCliente[1])) == null) {
                System.out.println("Ingrese nombre, apellido, telefono y email del cliente separados por -");
                cliente = crearCliente(inputUsuario.nextLine().split("-"), claveCliente, inputUsuario);

                // Lo agrego al sistema
                clientes.put(cliente.getClave(), cliente);
            } else {
                System.out.println(errorExistencia);
            }

            seguir = !deseaSalir(inputUsuario);
        }
        inputUsuario.close();
    }

    public static String[] verificarClaveCliente(String[] clave, Scanner inputUsuario) {
        // Metodo que verifica si al clave ingresada es valida
        int num = -1;

        while (clave.length != 2) {
            System.out.println("Ingresar nuevamente tipo y numero de documento separado por -");
            clave = inputUsuario.nextLine().split("-");
        }

        try {
            num = Integer.parseInt(clave[1]);
        } catch (NumberFormatException e) {
            System.out.println(errorInput);
            System.out.println("Ingresar nuevamente numero de documento");
            clave[1] = inputUsuario.nextLine();
        }

        if (num == -1) {
            clave = verificarClaveCliente(clave, inputUsuario);
        }

        return clave;
    }

    public static Cliente crearCliente(String[] datosCliente, String[] clave, Scanner inputUsuario) {
        // Metodo que crea y retorna un cliente en base a los datos pasados por
        // parametro y pide al usuario que reingrese info si es que falta
        int telefono = -1;

        while (datosCliente.length != 4) {
            System.out.println("Ingrese nombre, apellido, telefono y email del cliente separados por -");
            datosCliente = inputUsuario.nextLine().split("-");
        }

        while (telefono == -1) {
            try {
                telefono = Integer.parseInt(datosCliente[2]);
            } catch (NumberFormatException e) {
                System.out.println("Formato de telefono erroneo. Ingresar nuevamente telefono");
                datosCliente[2] = inputUsuario.nextLine();
            }
        }

        return new Cliente(clave[0], Integer.parseInt(clave[1]), datosCliente[0], datosCliente[1],
                telefono, datosCliente[3]);
    }

    public static void modificarCliente() {
        // Metodo que pide al usuario que quiere modificar del cliente y lo modifica
        Scanner inputUsuario = new Scanner(System.in);
        Cliente cliente;
        int opcion;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese tipo y numero de documento del cliente a modificar");
            cliente = clientes.get(inputUsuario.nextLine());

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
                        cliente.setNombre(inputUsuario.nextLine());
                        System.out.println("Se cambio el nombre del cliente a: " + cliente.getNombre());
                        break;
                    case 2:
                        System.out.println("Ingrese nuevo apellido para el cliente");
                        cliente.setApellido(inputUsuario.nextLine());
                        System.out.println("Se cambio el apellido a: " + cliente.getApellido());
                        break;
                    case 3:
                        System.out.println("Ingrese nuevo telefono para el cliente");
                        cliente.setTelefono(inputUsuario.nextInt());
                        inputUsuario.nextLine();
                        System.out.println("Se cambio el telefono a: " + cliente.getTelefono());
                        break;
                    case 4:
                        System.out.println("Ingrese nuevo email para el cliente");
                        cliente.setEmail(inputUsuario.nextLine());
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

    public static void eliminarCliente() {
        // Metodo que pide al usuario que cliente desea eliminar del sistema
        Scanner inputUsuario = new Scanner(System.in);
        String clave;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese el tipo y numero de documento del cliente a eliminar. Ej: DNI11111111");
            clave = inputUsuario.nextLine();
            // Si el cliente esta en el sistema
            if (clientes.get(clave) != null) {
                clientes.remove(clave);
            } else {
                System.out.println(errorInput);
            }

            seguir = !deseaSalir(inputUsuario);
        }
    }

    // * Rutas
    public static void darAltaRuta() {
        // Metodo que da de alta una ruta
        Scanner inputUsuario = new Scanner(System.in);
        int[] ciudad;
        int cantCiudades = 2;
        double cantKms;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de las ciudades separados por -. Ej 1111-2222");
            ciudad = toIntArray(inputUsuario.nextLine().split("-"), cantCiudades, inputUsuario);
            System.out.println("Ingrese la cantidad de kilometros de la ruta");

            // TODO implementar un metodo para identificar numeros
            cantKms = inputUsuario.nextDouble();
            inputUsuario.nextLine();

            // TODO loggear todas las inserciones y eliminaciones en el sistema
            System.out.println("Se dio de alta la ruta: " + mapaRutas.insertarArco(ciudad[0], ciudad[1], cantKms));

            seguir = !deseaSalir(inputUsuario);
        }
    }

    public static void darBajaRuta() {
        // Metodo que da de baja una ruta si es que existe en el sistema
        Scanner inputUsuario = new Scanner(System.in);
        int[] ciudad;
        int cantCiudades = 2;
        double cantKms;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de las ciudades separados por -. Ej 1111-2222");
            ciudad = toIntArray(inputUsuario.nextLine().split("-"), cantCiudades, inputUsuario);
            System.out.println("Ingrese la cantidad de kilometros de la ruta");

            // TODO implementar un metodo para identificar numeros
            cantKms = inputUsuario.nextDouble();
            inputUsuario.nextLine();

            // TODO loggear todas las inserciones y eliminaciones en el sistema
            System.out.println("Se dio de baja la ruta: " + mapaRutas.eliminarArco(ciudad[0], ciudad[1], cantKms));
            seguir = !deseaSalir(inputUsuario);
        }
        inputUsuario.close();
    }

    public static void modificarRuta() {
        // Metodo que modifica una ruta especificada por el usuario si es que existe en
        // el sistema
        Scanner inputUsuario = new Scanner(System.in);
        int[] ciudad;
        int cantCiudades = 2;
        double cantKms, nuevoKms;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de las ciudades separados por -. Ej 1111-2222");
            ciudad = toIntArray(inputUsuario.nextLine().split("-"), cantCiudades, inputUsuario);
            System.out.println("Ingrese la cantidad de kilometros de la ruta");

            // TODO implementar un metodo para identificar numeros
            cantKms = inputUsuario.nextDouble();
            inputUsuario.nextLine();

            if (mapaRutas.eliminarArco(ciudad[0], ciudad[1], cantKms)) {
                System.out.println("Ingrese la nueva cantidad de kilometros de la ruta");
                nuevoKms = inputUsuario.nextDouble();
                inputUsuario.nextLine();
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

    public static int obtenerLongitudInt(int numero) {
        // Metodo que obtine la cantidad de digitos de un numero
        return (int) Math.ceil(Math.log10(numero + 1));
    }

    public static boolean verificarCodigo(int codigo) {
        return obtenerLongitudInt(codigo) == 4;
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

    public static boolean contineSoloNumeros(String string) {
        // Metodo que retorna si un string esta conformado solo por numeros
    }

    public static boolean contieneSoloLetras(String string) {
        // Metodo que retorna un boolean si un string esta conformado solo por letras

    }

    public static int convertirCodigoPostal(String codigoPostal, Scanner inputUsuario) {
        /*
         * Metodo que toma un string del usuario, verifica que sea un int y lo retorna
         * caso contrario se llama recursivamente hasta que el input ingresado por el
         * usuario sea de tipo int y contenga 4 digitos
         */
        int codigoInt = -1;

        try {
            codigoInt = Integer.parseInt(codigoPostal);
        } catch (NumberFormatException e) {
            System.out.println(errorInput);
        }

        if (!verificarCodigo(codigoInt)) {
            System.out.println("Ingrese nuevamente el codigo postal de 4 digitos numericos");
            codigoInt = convertirCodigoPostal(inputUsuario.nextLine(), inputUsuario);
        }

        return codigoInt;
    }

    public static int[] toIntArray(String[] arrString, int cantElementos, Scanner input) {
        // Metodo que convierte un arreglo de strings a ints
        int i = 0;
        int[] arrInts = new int[cantElementos];

        while (i < cantElementos) {

            try {
                arrInts[i] = convertirCodigoPostal(arrString[i], input);
            } catch (ArrayIndexOutOfBoundsException e) {
                arrInts[i] = convertirCodigoPostal("", input);
            }
            i++;
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
        int cantCiudades = 2;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de la ciudades separadas por un guion. Ej: XXXX-YYYY");
            codigoPostal = inputUsuario.nextLine().toString().split("-");

            // Se verifican que los codigos postales sean validos
            codigoPostalInt = toIntArray(codigoPostal, cantCiudades, inputUsuario);

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
        int cantCiudades = 3;
        boolean seguir = true;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de las 3 ciudades separadas por '-'. Ej XXXX-YYYY-ZZZZ");
            // Hago un split() para obtener un array con los codigos postales
            codigoPostal = inputUsuario.nextLine().split("-");
            // Procedo a verificar dichos codigos si es que son validos
            codigoPostalInt = toIntArray(codigoPostal, cantCiudades, inputUsuario);

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
        int cantCiudades = 2;
        Ciudad origen, destino;
        boolean seguir = true;
        int kilometros;

        while (seguir) {
            System.out.println("Ingrese los codigos postales de la ciudades separadas por un guion. Ej: XXXX-YYYY");
            codigoPostal = inputUsuario.nextLine().split("-");
            codigoPostalInt = toIntArray(codigoPostal, cantCiudades, inputUsuario);

            System.out.println("Ingrese la cantidad de kilometros");
            kilometros = inputUsuario.nextInt();

            // ! O simplemete usar los codigos postales y me evito los nulos y crear tantos
            // objetos
            origen = (Ciudad) ciudades.obtenerElemento(codigoPostalInt[0]);
            destino = (Ciudad) ciudades.obtenerElemento(codigoPostalInt[1]);

            if (origen != null && destino != null) {
                System.out.format("Es posible viajar de %s a %s en %d kms : %b %n",
                        origen.getNombre(),
                        destino.getNombre(),
                        kilometros,
                        mapaRutas.verificarCaminoMenorDistacia(codigoPostalInt[0], codigoPostalInt[1], kilometros));
            } else {
                System.out.println("No existe codigo postal ingresado en el sistema");
            }
            seguir = !deseaSalir(inputUsuario);
        }
        inputUsuario.close();
    }

    // * Verificar viaje

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
