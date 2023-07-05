package Estructuras;

public class NodoAVL {

    private Object value;
    private Comparable key;
    private int altura;
    private NodoAVL izquierdo;
    private NodoAVL derecho;

    // Constructor
    public NodoAVL(Object value, Comparable key, NodoAVL izquierdo, NodoAVL derecho){
        this.value = value;
        this.key = key;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.altura = 0;
    }

    public NodoAVL(Object value, Comparable key){
        this(value, key, null, null);
    }

    // Getters
    public Object getValue() {
        return value;
    }

    public Comparable getKey() {
        return key;
    }

    public int getAltura() {
        return altura;
    }

    public NodoAVL getIzquierdo() {
        return izquierdo;
    }

    public NodoAVL getDerecho() {
        return derecho;
    }

    // Setters
    public void setValue(Object value) {
        this.value = value;
    }

    public void setKey(Comparable key) {
        this.key = key;
    }

    public void setIzquierdo(NodoAVL izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(NodoAVL derecho) {
        this.derecho = derecho;
    }

    public void recalcularAltura(){
        // Metodo que obtiene y setea la altura actual de un nodo
        this.altura = obtenerAltura(this, -1);
    }

    private int obtenerAltura(NodoAVL nodo, int altura){
        // Metodo recursivo que obtiene y retorna la altura de un nodo
        int alturaIzquierdo, alturaDerecho;
        // Recorre sus hijos si no es nulo
        if(nodo != null){
            alturaIzquierdo = obtenerAltura(nodo.getIzquierdo(), altura );
            alturaDerecho = obtenerAltura(nodo.getDerecho(), altura );

            // Me quedo con la altura maxima y la incremento en 1
            altura = Math.max(alturaIzquierdo, alturaDerecho) + 1;
        }

        return altura;
    }
}
