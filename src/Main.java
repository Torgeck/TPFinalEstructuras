public class Main {

    public static void main(String[] args) {
        // Inicializa una instancia de la clase MudanzasCompartidas para poder realizar
        // operaciones sobre ella
        MudanzasCompartidas programa = new MudanzasCompartidas();
        boolean seguir = true;

        do {
            seguir = programa.iniciarMenu();
        } while (seguir);
    }
}