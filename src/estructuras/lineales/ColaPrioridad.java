package estructuras.lineales;

public class ColaPrioridad {

    private NodoPrioridad frente;
    private NodoPrioridad fin;

    public ColaPrioridad() {
        this.frente = null;
        this.fin = null;
    }

    public boolean poner(Object elemento, double prioridad) {
        NodoPrioridad nuevoNodo = new NodoPrioridad(prioridad, elemento);

        // Caso especial cola vacia
        if (this.frente == null) {
            this.frente = nuevoNodo;
            this.fin = nuevoNodo;
        } else {
            // Caso especial que el elemento a insertar tenga menor prioridad que el frente
            if (this.frente.compareTo(nuevoNodo) > 0) {
                nuevoNodo.setEnlace(this.frente);
                this.frente = nuevoNodo;
            } else {
                // Busco el lugar donde insertar el nuevo nodo
                ponerAux(nuevoNodo, this.frente.getEnlace(), this.frente);
            }
        }
        return true;
    }

    private void ponerAux(NodoPrioridad nuevoNodo, NodoPrioridad nodoActual, NodoPrioridad nodoAnterior) {
        // Metodo que analiza si el nodo tiene la prioridad mas baja que el nodo actual
        // y lo inserta en su lugar

        // Si se llego al final de la cola lo enlazo al nodo anterior y seteo el nuevo
        // fin
        if (nodoActual == null) {
            nodoAnterior.setEnlace(nuevoNodo);
            this.fin = nuevoNodo;
        }
        // Si el nodo actual tiene menor prioridad entonces lo inserto
        else if (nuevoNodo.compareTo(nodoActual) < 0) {
            nodoAnterior.setEnlace(nuevoNodo);
            nuevoNodo.setEnlace(nodoActual);
        } else {
            // Si no recorro recursivamente hasta encontrar nodo con mayor prioridad o final
            // de cola
            ponerAux(nuevoNodo, nodoActual.getEnlace(), nodoActual);
        }
    }

    public boolean sacar() {
        boolean exito = false;

        // Si la cola no esta vacia procede
        if (this.frente != null) {
            // Setea el frente al nodo enlazado del que sale
            this.frente = this.frente.getEnlace();
            exito = true;
            // En caso de que quede la cola vacia setea el fin
            if (this.frente == null)
                this.fin = null;
        }

        return exito;
    }

    public Object obtenerFrente() {
        Object elemento = null;

        if (!esVacia())
            elemento = this.frente.getElemento();

        return elemento;
    }

    public boolean esVacia() {

        return this.frente == null && this.fin == null;
    }

    public void vaciar() {
        // El garbage collector de java se lleva todos los nodos que no son apuntados
        this.frente = null;
        this.fin = null;
    }

    public ColaPrioridad clone() {
        ColaPrioridad clon = new ColaPrioridad();
        NodoPrioridad aux1 = this.frente;

        // Creo y seteo el frente de Cola clon
        clon.frente = new NodoPrioridad(aux1.getPrioridad(), aux1.getElemento());

        clonRecursivo(clon, aux1, clon.frente);

        return clon;
    }

    private void clonRecursivo(ColaPrioridad clon, NodoPrioridad punteroOG, NodoPrioridad nodoClon) {

        // Condicion si llego al final de la cola
        if (punteroOG.getEnlace() == null) {
            // Pone el ultimo elemento y setea el fin
            clon.fin = nodoClon;
        } else {
            // Enlazo nodo del clon al nodo anterior
            nodoClon.setEnlace(
                    new NodoPrioridad(punteroOG.getEnlace().getPrioridad(), punteroOG.getEnlace().getElemento()));
            // Llamo de nuevo al metodo recursivo aumentando las posiciones de los punteros
            clonRecursivo(clon, punteroOG.getEnlace(), nodoClon.getEnlace());
        }
    }

    public String toString() {
        StringBuilder salida = new StringBuilder("[");
        NodoPrioridad puntero = this.frente;

        while (puntero != null) {
            salida.append(puntero.getElemento());
            // Me muevo al siguiente nodo
            puntero = puntero.getEnlace();

            // Agrego comas entre elementos
            if (puntero != null)
                salida.append(",");
        }

        return salida.append("]").toString();
    }

}
