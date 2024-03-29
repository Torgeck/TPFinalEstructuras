package estructuras.grafo;

public class NodoAdy {

    // Se uso double y no Object por temas de evitar convercion y casteo a la hora
    // de usar la etiqueta para operaciones relacionadas a buscar camino mas corto
    private double etiqueta;
    private NodoVert vertice;
    private NodoAdy sigAdyacente;

    public NodoAdy(NodoVert vertice, NodoAdy sigAdyacente, double etiqueta) {
        this.vertice = vertice;
        this.sigAdyacente = sigAdyacente;
        this.etiqueta = etiqueta;
    }

    public NodoAdy(NodoVert vertice, double etiqueta) {
        this.vertice = vertice;
        this.etiqueta = etiqueta;
        this.sigAdyacente = null;
    }

    public NodoVert getVertice() {
        return vertice;
    }

    public void setVertice(NodoVert vertice) {
        this.vertice = vertice;
    }

    public NodoAdy getSigAdyacente() {
        return sigAdyacente;
    }

    public void setSigAdyacente(NodoAdy sigAdyacente) {
        this.sigAdyacente = sigAdyacente;
    }

    public double getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(double etiqueta) {
        this.etiqueta = etiqueta;
    }
}
