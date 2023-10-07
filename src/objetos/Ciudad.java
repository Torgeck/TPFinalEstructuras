package objetos;

public class Ciudad {

    // Atributos
    private int codigoPostal; // CLAVE
    private String nombreCiudad;
    private String provincia;

    // Constructores
    public Ciudad(int codigoPostal, String nombreCiudad, String provincia) {
        this.codigoPostal = codigoPostal;
        this.nombreCiudad = nombreCiudad;
        this.provincia = provincia;
    }

    public Ciudad() {
        this(0, "", "");
    }

    // Metodos
    public int getCodigoPostal() {
        return this.codigoPostal;
    }

    public String getNombre() {
        return this.nombreCiudad;
    }

    public void setNombre(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return "Codigo Postal = " + this.codigoPostal +
                ", Nombre de ciudad = " + this.nombreCiudad +
                ", Provincia = " + this.provincia;
    }

}
