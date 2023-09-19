package estructuras.lineales;

public class Lista {

    private Nodo cabecera;
    private int longitud;

    public Lista() {
        this.cabecera = null;
        longitud = 0;
    }

    public int longitud() {
        return this.longitud;
    }

    public boolean insertar(Object elemento, int posicion) {
        boolean exito = false;

        // Caso donde se quiera insertar nodo en una posiicon que no exista
        if (posicion >= 1 && posicion <= this.longitud + 1) {
            exito = true;

            // Caso donde se quiera meter nuevo nodo al principio de la lista
            if (posicion == 1)
                this.cabecera = new Nodo(elemento, this.cabecera);

            else {

                Nodo aux = this.cabecera;
                int i = 1;

                // Avanza hasta el nodo de la posicion -1
                while (i < posicion - 1) {
                    aux = aux.getEnlace();
                    i++;
                }

                Nodo nuevoNodo = new Nodo(elemento, aux.getEnlace());
                aux.setEnlace(nuevoNodo);
            }

            this.longitud++;
        }
        return exito;
    }

    public boolean eliminar(int posicion) {
        boolean exito = false;

        if (posicion >= 1 && posicion <= this.longitud) {
            // Si se elimina el primer elemento
            if (posicion == 1) {
                cabecera = this.cabecera.getEnlace();
            } else {
                int i = 1;
                Nodo auxDelantero = this.cabecera;

                // Recorro lista
                while (i < posicion - 1) {
                    auxDelantero = auxDelantero.getEnlace();
                    i++;
                }
                // Setea el enlace del nodo en la posicion anterior a la posicion +1
                auxDelantero.setEnlace(auxDelantero.getEnlace().getEnlace());

            }
            // Resto un elemento a la longitud total
            this.longitud--;
            exito = true;
        }
        return exito;
    }

    public Object recuperar(int posicion) {
        Object elemento = null;

        // Excepcion por si la posicion ingresada por parametro no exite en la lista
        if (posicion >= 1 && posicion <= this.longitud) {
            int i = 1;
            Nodo aux = this.cabecera;

            // Recorro la lista
            while (i < posicion) {
                aux = aux.getEnlace();
                i++;
            }
            elemento = aux.getElemento();
        }
        return elemento;
    }

    public int localizar(Object elemento) {
        int posicion = -1, i = 1;
        Nodo aux = this.cabecera;

        while (aux != null) {
            if (aux.getElemento().equals(elemento)) {
                posicion = i;
                aux = null;
            } else {
                aux = aux.getEnlace();
                i++;
            }
        }
        return posicion;
    }

    public boolean esVacia() {
        return this.cabecera == null;
    }

    public void vaciar() {
        this.cabecera = null;
        this.longitud = 0;
    }

    @Override
    public Lista clone() {
        Lista clon = new Lista();

        if (this.cabecera != null) {

            // Creo un nuevo nodo identico al nodo de la cabecera del OG
            clon.cabecera = new Nodo(this.cabecera.getElemento(), null);

            // Seteo la misma longitud
            clon.longitud = this.longitud;

            // Paso nodos ya avanzados
            cloneRecursivo(clon.cabecera, this.cabecera);
        }

        return clon;
    }

    private void cloneRecursivo(Nodo nodoClon, Nodo nodoOG) {

        if (nodoOG.getEnlace() == null)
            nodoClon.setEnlace(null);

        else {
            // creo y asigno nuevo nodo al nodo clon
            nodoClon.setEnlace(new Nodo(nodoOG.getEnlace().getElemento(), null));
            // avanzo punteros y hago llamado recursivo
            cloneRecursivo(nodoClon.getEnlace(), nodoOG.getEnlace());
        }
    }

    // TODO usar StringBuffer
    public String toString() {
        String salida = "[";
        Nodo aux = this.cabecera;

        if (!this.esVacia()) {
            while (aux != null) {
                salida += aux.getElemento();
                // Muevo puntero
                aux = aux.getEnlace();
                // Agrego comas entre elementos
                if (aux != null)
                    salida += ",";
            }
        }
        return salida + "]";
    }

    public Lista invertir() {
        Lista invertida = new Lista();

        if (this.cabecera != null) {

            invertirRecursivo(invertida, this.cabecera);

            // Seteo la misma longitud en ambas listas
            invertida.longitud = this.longitud;
        }

        return invertida;
    }

    private void invertirRecursivo(Lista invertida, Nodo nodoOG) {

        // Va desplazando la cabezera a medida que itera
        invertida.cabecera = new Nodo(nodoOG.getElemento(), invertida.cabecera);

        // Hace llamada recursiva hasta llegar al final de la lista original
        if (nodoOG.getEnlace() != null) {
            // Avanzo el puntero
            invertirRecursivo(invertida, nodoOG.getEnlace());
        }
    }
    /*
     * 
     * public Lista eliminarApariciones(Object elemento) {
     * Lista lista = new Lista();
     * Nodo puntero = this.cabecera, auxLista = lista.cabecera;
     * 
     * while(puntero != null) {
     * 
     * if(elemento != puntero.getElemento()) {
     * 
     * //Caso en que sea el primer elemento insertado en la lista
     * if (lista.cabecera == null) {
     * lista.cabecera = new Nodo(puntero.getElemento(), null);
     * auxLista = lista.cabecera;
     * }
     * 
     * else{
     * auxLista.setEnlace(new Nodo(puntero.getElemento(), null));
     * auxLista = auxLista.getEnlace();
     * }
     * //Aumento longitud de la cadena
     * lista.longitud++;
     * }
     * //Se saltea el elemento que hay que eliminar
     * puntero = puntero.getEnlace();
     * }
     * return lista;
     * }
     */

    public void eliminarApariciones(Object elemento) {

        if (this.cabecera != null) {
            Nodo delantero = this.cabecera.getEnlace();

            if (this.cabecera.getElemento().equals(elemento)) {

                while (delantero.getElemento().equals(elemento))
                    delantero = delantero.getEnlace();

                this.cabecera = delantero;
                delantero = delantero.getEnlace();
            }

            eliminarAparicionesRec(this.cabecera, delantero, elemento);
        }
    }

    private void eliminarAparicionesRec(Nodo anterior, Nodo actual, Object elemento) {

        if (actual != null) {

            if (actual.getElemento().equals(elemento)) {
                anterior.setEnlace(actual.getEnlace());
                eliminarAparicionesRec(anterior, actual.getEnlace(), elemento);
            } else
                eliminarAparicionesRec(anterior.getEnlace(), actual.getEnlace(), elemento);
        }
    }
}
