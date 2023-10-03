import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.Date;

public class Logger {
    // Todo terminar el logger, ver como resolver el loggear distinatas clases
    static Path log = Path.of("./src/logs/log.txt");
    static Charset charset = Charset.forName("US-ASCII");
    // - Logear una string pasada por parametro y que el logger se encargue de la
    // timestamp(opcional)

    public static void log(String unString) {
        // Metodo que loggea la creacion de un bojeto

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

    public static void loggearSistema(MudanzasCompartidas sistema){
        // Metodo que loggea el estado del sistema
        log("ArbolAVL con info de ciudades\n" + sistema.)
    }
}
