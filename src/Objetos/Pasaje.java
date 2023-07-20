package Objetos;

public class Pasaje {
    private String vuelo;
    private int fecha;
    private String numeroAsiento;
    private String estado;

    public Pasaje(String vuelo, int fecha, String numeroAsiento, String estado) {
        this.vuelo = vuelo;
        this.fecha = fecha;
        this.numeroAsiento = numeroAsiento;
        this.estado = estado;
    }

    public String getVuelo() {
        return vuelo;
    }

    public void setVuelo(String vuelo) {
        this.vuelo = vuelo;
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public String getNumeroAsiento() {
        return numeroAsiento;
    }

    public void setNumeroAsiento(String numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
