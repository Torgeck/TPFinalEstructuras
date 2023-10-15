import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.Date;

public class Logger {

    static Path log = Path.of("./src/logs/log.txt");
    static Charset charset = Charset.forName("US-ASCII");

    public static void log(String unString) {
        // Metodo que loggea la creacion de un bojeto

        // Try with resources cierra automaticamente el writer
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(log.toString(), true))) {
            writer.write(agregarTimeStamp(unString));
            writer.newLine();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private static String agregarTimeStamp(String unString) {
        Date fecha = new Date();
        Timestamp ts = new Timestamp(fecha.getTime());
        return "[ " + ts + " ] " + unString;
    }

    public static void loggearSistema(MudanzasCompartidas sistema) {
        // Metodo que loggea el estado del sistema

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(log.toString(), true))) {
            escritor.write(sistema.mostrarSistema());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
