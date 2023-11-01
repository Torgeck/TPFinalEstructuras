package estructuras.grafo;

import java.util.Objects;

public class NodoVert {

    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;

    public NodoVert(Object elem, NodoVert sigVertice, NodoAdy primerAdy) {
        this.elem = elem;
        this.sigVertice = sigVertice;
        this.primerAdy = primerAdy;
    }

    public NodoVert(Object elem, NodoVert sigVertice) {
        this.elem = elem;
        this.sigVertice = sigVertice;
        this.primerAdy = null;
    }

    public NodoVert(Object elem) {
        this.elem = elem;
        this.sigVertice = null;
        this.primerAdy = null;
    }

    public Object getElem() {
        return elem;
    }

    public void setElem(Object elem) {
        this.elem = elem;
    }

    public NodoVert getSigVertice() {
        return sigVertice;
    }

    public void setSigVertice(NodoVert sigVertice) {
        this.sigVertice = sigVertice;
    }

    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }

    public void setPrimerAdy(NodoAdy primerAdy) {
        this.primerAdy = primerAdy;
    }

    @Override
    public String toString() {
        return elem.toString();
    }

    @Override
    public boolean equals(Object objeto) {
        return objeto.equals(this.elem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elem);
    }
}
