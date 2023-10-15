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

    public boolean eliminarElemento(Object objeto) {
        boolean exito = false;

        // Caso que se elimine la primer posicion
        if (this.cabecera.getElemento().equals(objeto)) {
            this.cabecera = this.cabecera.getEnlace();
            exito = true;
        } else {
            exito = eliminarElementoRecursivo(objeto, this.cabecera, this.cabecera.getEnlace());
        }

        return exito;
    }

    private boolean eliminarElementoRecursivo(Object objeto, Nodo nodoAnterior, Nodo nodoActual) {
        boolean exito = false;

        if (nodoActual != null) {
            if (nodoActual.getElemento().equals(objeto)) {
                nodoAnterior.setEnlace(nodoActual.getEnlace());
                exito = true;
            } else {
                exito = eliminarElementoRecursivo(objeto, nodoActual, nodoActual.getEnlace());
            }
        }
        return exito;
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

    public String toString() {
        StringBuilder salida = new StringBuilder("[");
        Nodo aux = this.cabecera;

        while (aux != null) {
            salida.append(aux.getElemento());
            // Muevo puntero
            aux = aux.getEnlace();
            // Agrego comas entre elementos
            if (aux != null)
                salida.append(",");
        }

        return salida.append("]").toString();
    }

    public String enumerar() {
        StringBuilder salida = new StringBuilder();
        Nodo aux = this.cabecera;
        int indice = 1;

        while (aux != null) {
            salida.append(indice + " - " + aux.getElemento() + "\n");

            aux = aux.getEnlace();
            indice++;
        }

        return salida.toString();
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

    public boolean insertarElementosLista(Lista listaElementos) {
        // Metodo que recibe una lista por parametro y agrega los elementos de ella a la
        // lista que llama el metodo
        boolean resultado = true;
        int i = 1;

        if (this.cabecera != null) {
            insertarElementosListaRecursivo(this.cabecera, listaElementos, i);
        } else if (!listaElementos.esVacia()) {
            this.cabecera = new Nodo(listaElementos.recuperar(i), null);
            this.longitud++;
            insertarElementosListaRecursivo(this.cabecera, listaElementos, i++);
        } else {
            resultado = false;
        }

        return resultado;
    }

    private void insertarElementosListaRecursivo(Nodo nodoActual, Lista elementos, int posicionElemento) {

        if (nodoActual.getEnlace() != null) {
            insertarElementosListaRecursivo(nodoActual.getEnlace(), elementos, posicionElemento);
        } else if (elementos.recuperar(posicionElemento) != null) {
            nodoActual.setEnlace(new Nodo(elementos.recuperar(posicionElemento), null));
            this.longitud++;
            insertarElementosListaRecursivo(nodoActual.getEnlace(), elementos, posicionElemento + 1);
        }
    }

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
