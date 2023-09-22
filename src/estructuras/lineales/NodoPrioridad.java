package estructuras.lineales;

public class NodoPrioridad implements Comparable {

    private double prioridad;
    private NodoPrioridad enlace;
    private Object elemento;

    public NodoPrioridad(double prioridad, NodoPrioridad enlace, Object elemento) {
        this.prioridad = prioridad;
        this.enlace = enlace;
        this.elemento = elemento;
    }

    public NodoPrioridad(double prioridad, Object elemento) {
        this.prioridad = prioridad;
        this.elemento = elemento;
        this.enlace = null;
    }

    public NodoPrioridad getEnlace() {
        return enlace;
    }

    public void setEnlace(NodoPrioridad enlace) {
        this.enlace = enlace;
    }

    public double getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(double prioridad) {
        this.prioridad = prioridad;
    }

    public Object getElemento() {
        return elemento;
    }

    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    public int compareTo(Object nodo) {
        int resultado;
        NodoPrioridad otro = (NodoPrioridad) nodo;

        resultado = Double.compare(this.prioridad, otro.getPrioridad());
        return resultado;
    }
}
