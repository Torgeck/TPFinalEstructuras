package objetos;

public class Cliente {

    // Atributos
    private String tipoDoc; // CLAVE
    private int numeroDoc; // CLAVE
    private String nombre;
    private String apellido;
    private int telefono;
    private String email;

    // Constructures
    public Cliente() {
        this.tipoDoc = "";
        this.numeroDoc = -1;
        this.nombre = "";
        this.apellido = "";
        this.telefono = -1;
        this.email = "";
    }

    public Cliente(String tipoDoc, int numeroDoc, String nombre, String apellido, int telefono, String email) {
        this.tipoDoc = tipoDoc;
        this.numeroDoc = numeroDoc;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters/Setters
    public String getTipoDoc() {
        return this.tipoDoc;
    }

    public int getNumeroDoc() {
        return this.numeroDoc;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTelefono() {
        return this.telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Tipo documento = " + this.tipoDoc +
                ", Numero documento = " + this.numeroDoc +
                ", Nombre = " + this.nombre +
                ", Apellido = " + this.apellido +
                ", Telefono = " + this.telefono +
                ", Email = " + this.email;
    }

}