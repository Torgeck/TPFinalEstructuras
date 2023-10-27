package objetos;

public class Cliente {

    // Atributos
    private String tipoDoc; // CLAVE
    private int numeroDoc; // CLAVE
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;

    // Constructures
    public Cliente(String tipoDoc, int numeroDoc, String apellido, String nombre, String telefono, String email) {
        this.tipoDoc = tipoDoc;
        this.numeroDoc = numeroDoc;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }

    public Cliente() {
        this("", -1, "", "", "", "");
    }

    // Getters/Setters
    public String getClave() {
        return this.tipoDoc + this.numeroDoc;
    }

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

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
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
        return "Tipo documento: " + this.tipoDoc +
                ", Numero documento: " + this.numeroDoc +
                ", Nombre: " + this.nombre +
                ", Apellido:  " + this.apellido +
                ", Telefono: " + this.telefono +
                ", Email: " + this.email + "\n";
    }

}
