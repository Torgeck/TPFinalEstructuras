import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;

import objetos.Ciudad;

public class Main {
    static Charset charset = Charset.forName("US-ASCII");
    static Path cargaInicial = Path.of("./src/cargaInicial/carga_ini.txt");

    public static void main(String[] args) {
        // Corre el menu de la clase MudanzasCompartidas
        String line;
        StringTokenizer token;
        Ciudad ciudad;
        try (BufferedReader reader = Files.newBufferedReader(cargaInicial, charset)) {
            while ((line = reader.readLine()) != null) {

                token = new StringTokenizer(line, ";");
                // La mando a un swith o un hashmap, creo que conviene switch ya que hay 3
                // clases no mas

                switch (token.nextToken()) {
                    case "C":
                        // crear ciudad, aniadirla a las estructuras y logear
                        int cp = Integer.parseInt(token.nextToken());
                        String nombre = token.nextToken(), provincia = token.nextToken();
                        ciudad = new Ciudad(cp, nombre, provincia);
                        Utilidades.loggearCreacion(ciudad);
                        break;
                    case "S":
                        // crear, aniadirla a las estructuras y logear la solicitud
                        break;
                    case "P":
                        // crear, aniadirl a las estructuras y logear el cliente
                        break;
                    case "R":
                        // crear aniadirla a las estructuras y logear la ruta
                        break;

                    default:
                        System.out.println("No es objeto valido o se rompio algo");

                }

            }
        } catch (IOException e) {
            System.out.println("Salio algo mal");
            e.printStackTrace();
        }
    }
}