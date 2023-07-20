package Objetos;

public class Aeropuerto {
    private String nombreAeronautico;
    private String ciudad;
    private int telefono;

    public Aeropuerto(String nombreAeronautico, String ciudad, int telefono) {
        this.nombreAeronautico = nombreAeronautico;
        this.ciudad = ciudad;
        this.telefono = telefono;
    }

    public String getNombreAeronautico() {
        return nombreAeronautico;
    }

    public void setNombreAeronautico(String nombreAeronautico) {
        this.nombreAeronautico = nombreAeronautico;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
