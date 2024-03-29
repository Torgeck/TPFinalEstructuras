import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;

import objetos.Ciudad;
import objetos.Cliente;
import objetos.Solicitud;

public class Loader {

    // * Clase encargada de la carga de datos al sistema */

    static Path datos = Path.of("./src/cargaInicial/carga_ini.txt");
    static Charset charset = Charset.forName("ISO-8859-1");

    public static void cargarDatos(MudanzasCompartidas sistema) {
        String line;
        StringTokenizer token;

        try (BufferedReader reader = Files.newBufferedReader(datos, charset)) {
            while ((line = reader.readLine()) != null) {

                token = new StringTokenizer(line, ";");
                // La mando a un swith o un hashmap, creo que conviene switch ya que hay 3
                // clases no mas
                if (token.hasMoreTokens()) {
                    switch (token.nextToken()) {
                        case "C":
                            // crear ciudad, aniadirla a las estructuras y logear
                            cargarCiudad(token, sistema);
                            break;
                        case "S":
                            // crear, aniadirla a las estructuras y logear la solicitud
                            cargarSolicitud(token, sistema);
                            break;
                        case "P":
                            // crear, aniadirl a las estructuras y logear el cliente
                            cargarCliente(token, sistema);
                            break;
                        case "R":
                            // crear aniadirla a las estructuras y logear la ruta
                            cargarRuta(token, sistema);
                            break;

                        default:
                            System.out.println("No es objeto valido o se rompio algo");
                    }
                }
            }
            // Loggea el sistema despues de hacer la carga inicial (Lo podria hacer el mismo
            // sistema idk)
            Logger.loggearSistema(sistema);
        } catch (IOException e) {
            System.out.println("Salio algo mal");
            e.printStackTrace();
        }
    }

    public static void cargarCiudad(StringTokenizer token, MudanzasCompartidas sistema) {
        // Metodo que carga una ciudad al sistema y retorna la ciudad cargada
        Ciudad ciudad;
        String cp, nombre, provincia;

        // Asigno cada token a una variable y las verifico
        cp = token.nextToken();
        nombre = token.nextToken();
        provincia = token.nextToken();

        if (Verificador.esCodigoPostal(cp) && Verificador.sonPalabras(nombre)
                && Verificador.sonPalabras(provincia)) {
            // Creo la ciudad y la agrego a las estructuras
            ciudad = new Ciudad(Integer.parseInt(cp), nombre, provincia);
            sistema.agregarCiudad(ciudad);
        } else {
            System.out.println("ERROR CIUDAD " + nombre + " Algo salio mal");
            // Error no se pudo loggear ciudad
        }
    }

    public static void cargarSolicitud(StringTokenizer token, MudanzasCompartidas sistema) {
        Solicitud solicitud;
        String[] arr = new String[9];

        // Asigno cada token a una variable y las verifico [Podria ser un array]
        llenarArraySolicitud(arr, token);

        if (Verificador.verificarSolicitud(arr)) {
            // Transformar los atributos que no son string
            solicitud = new Solicitud(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), arr[2], arr[3],
                    Double.parseDouble(arr[4]),
                    Integer.parseInt(arr[5]), arr[6], arr[7], (arr[8].equals("T")));
            // Agrego la solicitud a las estructuras
            sistema.agregarSolicitud(solicitud);
        } else {
            System.out.println("ERROR SOLICITUD " + arr[0] + "|" + arr[1] + "|" + arr[3] + "Algo salio mal");
            // Error en el sistema
        }
    }

    private static void llenarArraySolicitud(String[] arr, StringTokenizer token) {
        // Metodo que llena un array para una solicitud con tokens
        int i = 0, longitud = arr.length;

        while (i < longitud) {
            if (i == 3) {
                arr[i] = token.nextToken() + token.nextToken();
            } else {
                arr[i] = token.nextToken();
            }
            i++;
        }
    }

    public static void cargarCliente(StringTokenizer token, MudanzasCompartidas sistema) {
        Cliente cliente;
        String[] arr = new String[6];

        llenarArray(arr, token);

        if (Verificador.verificarCliente(arr)) {
            cliente = new Cliente(arr[0], Integer.parseInt(arr[1]), arr[2], arr[3], arr[4], arr[5]);
            sistema.agregarCliente(cliente);
        } else {
            System.out.println("ERROR CLIENTE Algo salio mal");
            // error en el sistema
        }
    }

    private static void llenarArray(String[] arr, StringTokenizer token) {
        int i = 0;

        while (token.hasMoreTokens()) {
            arr[i] = token.nextToken();
            i++;
        }
    }

    public static void cargarRuta(StringTokenizer token, MudanzasCompartidas sistema) {
        String[] arr = new String[3];

        llenarArray(arr, token);

        if (Verificador.verificarRuta(arr)) {
            sistema.agregarRuta(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Double.parseDouble(arr[2]));
        } else {
            System.out.println("ERROR RUTA " + arr[0] + "|" + arr[1] + "Algo salio mal");
            // error en el sistema
        }
    }

}
