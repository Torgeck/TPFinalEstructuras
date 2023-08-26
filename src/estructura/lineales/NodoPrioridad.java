package estructura.lineales;

public class NodoPrioridad implements Comparable{

    private int prioridad;
    private NodoPrioridad enlace;
    private Object elemento;

    public NodoPrioridad(int prioridad, NodoPrioridad enlace, Object elemento) {
        this.prioridad = prioridad;
        this.enlace = enlace;
        this.elemento = elemento;
    }

    public NodoPrioridad(int prioridad, Object elemento) {
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

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public Object getElemento() {
        return elemento;
    }

    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    public int compareTo(Object nodo){
        int resultado;
        NodoPrioridad otro = (NodoPrioridad) nodo;

        resultado = Integer.compare(this.prioridad, otro.getPrioridad());
        return resultado;
    }
}
