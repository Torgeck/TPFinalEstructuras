package grafos;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;

import java.util.Map;

public class Grafo {

    private NodoVert inicio;

    public Grafo() {
        this.inicio = null;
    }

    public boolean insertarVertice(Object vertice) {
        // Metodo que inserta un vertice en el grafo controlando que no se repita en el mismo
        boolean exito = false;

        // Busco si existe el vertice en el grafo
        NodoVert aux = ubicarVertice(vertice);

        // Si no existe lo inserto en el grafo
        if (aux == null) {
            this.inicio = new NodoVert(vertice, this.inicio);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object buscado) {
        // Metodo que busca en la estructura si lo encuentra lo retorna
        NodoVert aux = this.inicio;

        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    private boolean eliminarVertice(Object vertice) {
        // Metodo que elimina un vertice de la estructura y todos los arcos que lo contengan como vertice
        boolean exito;

        // Caso que el grafo este vacio
        if (this.inicio == null)
            exito = false;
        else
            exito = eliminarVerticeAux(this.inicio, vertice);

        return exito;
    }

    private boolean eliminarVerticeAux(NodoVert nodo, Object buscado) {
        // Metodo que controla y realiza la eliminacion de un vertice existente
        boolean exito = false;
        NodoVert anterior = null;

        // Busco si existe el nodo buscado
        while (nodo != null && !nodo.getElem().equals(buscado)) {
            anterior = nodo;
            nodo = nodo.getSigVertice();
        }

        // Si el nodo es encontrado
        if (nodo != null) {
            // Enlazo al nodo anterior con el siguiente del nodo asi eliminandolo
            if (anterior == null)
                this.inicio = nodo.getSigVertice();
            else
                anterior.setSigVertice(nodo.getSigVertice());

            // Borro los arcos de los nodos que apuntan al nodo a eliminar
            eliminarArcos(nodo.getPrimerAdy(),nodo);
            nodo.setPrimerAdy(null);
            exito = true;
        }
        return exito;
    }

    private void eliminarArcos(NodoAdy arco, NodoVert nodoAEliminar) {
        // Metodo que elimina arcos de un grafo unidireccional con su lista de adyacencia

        // Mientras tenga arcos
        while(arco != null){
            eliminarArcoConNodos(arco.getVertice(), nodoAEliminar);
            arco = arco.getSigAdyacente();
        }

    }

    private boolean eliminarArcoConNodos(NodoVert origen, NodoVert destino) {
        // Metodo que analiza los diferentes casos de eliminar un arco y devuelve un boolean
        boolean exito = true;
        NodoAdy arcoBuscado = origen.getPrimerAdy(), anterior;

        // Si el arco es el primero se setea al siguiente como primero
        if (arcoBuscado.getVertice() == destino)
            origen.setPrimerAdy(arcoBuscado.getSigAdyacente());

        else {
            anterior = null;
            // Caso contrario recorro todos los arcos de origen hasta encontrarlo
            while (arcoBuscado != null && arcoBuscado.getVertice() != destino) {
                anterior = arcoBuscado;
                arcoBuscado = arcoBuscado.getSigAdyacente();
            }

            // Si es encontrado seteo al nodoAdy anterior con el posterior del nodoAdy a eliminar
            if (arcoBuscado != null) {
                anterior.setSigAdyacente(arcoBuscado.getSigAdyacente());
            }
            // Caso contrario no se encontró y, por lo tanto, no se pudo eliminar
            else
                exito = false;
        }
        return exito;
    }

    public boolean insertarArco(Object origen, Object destino, Object etiqueta) {
        // Metodo que agrega un arco en la estructura solo si ambos nodos existen en la misma
        boolean exito = false;

        // Si el grafo no está vacio busco en la estructura ambos nodos
        if (this.inicio != null) {
            NodoVert nodoOrigen = ubicarVertice(origen), nodoDestino = ubicarVertice(destino);

            // Si ambos existen inserto un arco
            if (nodoOrigen != null && nodoDestino != null) {
                // Setea el nuevo arco haciedo uso del constructor de la clase NodoAdy
                nodoOrigen.setPrimerAdy(new NodoAdy(nodoDestino, nodoOrigen.getPrimerAdy(), etiqueta));
                nodoDestino.setPrimerAdy(new NodoAdy(nodoOrigen, nodoDestino.getPrimerAdy(), etiqueta));
                exito = true;
            }
        }
        return exito;
    }

    public boolean eliminarArco(Object origen, Object destino) {
        // Metodo que elimina solo si este existe entre origen y destino pasados por parametros
        boolean exito = false;

        // Si el grafo no está vacio busco en la estructura ambos nodos
        if (this.inicio != null) {
            NodoVert nodoOrigen = ubicarVertice(origen), nodoDestino = ubicarVertice(destino);

            // Si ambos exiten busco si existe el arco
            if (nodoOrigen != null && nodoDestino != null) {
                // Busco el arco en el nodo origen si existe lo elimino
                exito = eliminarArcoConNodos(nodoOrigen, nodoDestino);

                // Si existe el arco elimino el otro
                if(exito)
                    eliminarArcoConNodos(nodoDestino, nodoOrigen);
            }
        }
        return exito;
    }

    public boolean existeVertice(Object buscado) {
        //Metodo que recorre los vertices para ver si existe en el grafo
        return ubicarVertice(buscado) != null;
    }

    public boolean existeArco(Object origen, Object destino) {
        // Metodo que recorre la estructura para verificar si existe un arco entre los vertices pasados por parametro
        boolean exito = false;
        NodoVert nodoOrigen = ubicarVertice(origen), nodoDestino = ubicarVertice(destino);
        // Sí existen amnbos nodos en el grafo
        if (nodoOrigen != null && nodoDestino != null) {
            //Analizo cada arco del nodo origen
            NodoAdy aux = nodoOrigen.getPrimerAdy();

            while (aux != null) {
                // Verifica si el arco actual tiene como destino a destino
                if (aux.getVertice() == nodoDestino) {
                    exito = true;
                    aux = null;
                }
                // Si no se fija en el siguiente arco
                else
                    aux = aux.getSigAdyacente();
            }
        }
        return exito;
    }

    public boolean existeCamino(Object origen, Object destino) {
        //Metodo que recorre la estructura y verifica que exista un camino entre los nodos pasados por parametro
        boolean exito = false;
        NodoVert nodoOrigen = ubicarVertice(origen), nodoDestino = ubicarVertice(destino);

        //Si existen ambos vertices busca si existe camino entre ambos 
        if (nodoOrigen != null && nodoDestino != null) {
            Lista visitados = new Lista();
            exito = existeCaminoAux(nodoOrigen, destino, visitados);
        }
        return exito;
    }

    private boolean existeCaminoAux(NodoVert nodo, Object destino, Lista visitados) {
        boolean exito = false;

        if (nodo != null) {
            //Si vertice nodo es el destino entonces hay camino
            if (nodo.getElem().equals(destino))
                exito = true;
            else {
                //Si no es el destino verifica si hay camino entre nodo y destino
                visitados.insertar(nodo.getElem(), visitados.longitud() + 1);
                NodoAdy ady = nodo.getPrimerAdy();

                while (!exito && ady != null) {

                    if (visitados.localizar(ady.getVertice().getElem()) < 0)
                        exito = existeCaminoAux(ady.getVertice(), destino, visitados);

                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public Lista caminoMasCorto(Object origen, Object destino) {
        //Metodo que devuelve una lista con el camino mas corto si es que existen los nodos en el grafo
        Lista camino = new Lista();
        NodoVert nodoOrigen = ubicarVertice(origen), nodoDestino = ubicarVertice(destino);

        //Si los vertices existen en el grafo
        if (nodoOrigen != null && nodoDestino != null) {
            Lista vis = new Lista();
            caminoMasCortoAux(nodoOrigen, nodoDestino, camino, vis);
        }
        return camino;
    }
    // TODO terminar la funcion y hacerla con el algoritmo de dijkstra 
    private void caminoMasCortoAux(NodoVert nodo, NodoVert destino, Lista camino, Lista vis) {
        //Metodo que encuentra el camino mas corto y llena una lista con este recursivamente
        boolean flag = false;
        NodoAdy arco = nodo.getPrimerAdy();
        vis.insertar(nodo.getElem(), vis.longitud() + 1);
        //Para cada arco del vertice nodo
        while(arco != null){
            
            arco = arco.getSigAdyacente();
        }

    }

    public boolean vacio() {
        //Metodo que retorna true si el grafo esta vacio y false caso contrario
        return this.inicio == null;
    }

    public void vaciar() {
        //Metodo que vacia la estructura de grafo usando el garbage collector de java
        this.inicio = null;
    }

    public Lista listarEnProfundidad() {
        //Devuelve una lista con los vertices del grafo visitados segun el recorrido en profundidad
        Lista visitados = new Lista();

        //Comienza a recorrer del vertice que esta en el inicio
        NodoVert aux = this.inicio;
        while (aux != null) {
            //Si el vertice no esta en la lista avanza en profundidad
            if (visitados.localizar(aux.getElem()) < 0)
                listarEnProfundidadAux(aux, visitados);

            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert nodo, Lista visitados) {
        //Metodo que recorre recursivamente el grafo en profundidad

        if (nodo != null) {
            //Marca al vertice nodo como visitado
            visitados.insertar(nodo.getElem(), visitados.longitud() + 1);
            NodoAdy ady = nodo.getPrimerAdy();

            //Visita en profundidad los adyacentes de nodo aun no visitados
            while (ady != null) {
                if (visitados.localizar(ady.getVertice().getElem()) < 0)
                    listarEnProfundidadAux(ady.getVertice(), visitados);
                ady = ady.getSigAdyacente();
            }
        }
    }

    public Lista listarEnAnchura() {
        //Metodo que devuelve una lista con los vertices del grafo visitados segun el recorrido de anchura
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;

        while (aux != null) {
            //Si no esta en visitados reccorro en anchura
            if (visitados.localizar(aux.getElem()) < 0)
                listarEnAnchuraAux(aux, visitados);

            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnAnchuraAux(NodoVert nodo, Lista visitados) {
        //Metodo que recorre recursivamente el grafo en anchura
        Cola cola = new Cola();
        NodoVert aux;
        NodoAdy ady;
        cola.poner(nodo);

        //Mientras la cola no este vacia
        while (!cola.esVacia()) {
            aux = (NodoVert) cola.obtenerFrente();
            cola.sacar();
            visitados.insertar(aux.getElem(), visitados.longitud() + 1);
            ady = aux.getPrimerAdy();
            //Para cada vertice adyacente de aux
            while (ady != null) {
                //Si no esta en la lista lo añado a la cola
                if (visitados.localizar(ady.getVertice().getElem()) < 0)
                    cola.poner(ady.getVertice());
                //Avanzo al proximo adyacente
                ady = ady.getSigAdyacente();
            }
        }
    }

    public Grafo clone() {
        //Metodo que clona la estructura de grafo y la retorna
        Grafo clon = new Grafo();

        if (this.inicio != null) {
            clon.inicio = new NodoVert(this.inicio.getElem());
            cloneAux(clon.inicio, this.inicio);
        }
        return clon;
    }

    private void cloneAux(NodoVert nodoClon, NodoVert nodoOG) {
        //Metodo que crea y asigna los valores de nodos a los nodos de clon
        Lista vis = new Lista();
        NodoVert aux;
        NodoAdy ady;

        vis.insertar(nodoOG.getElem(), 1);

        //Mientras la cola no este vacia
        while (nodoOG != null) {
            //Veo cada arco del nodoOG
            ady = nodoOG.getPrimerAdy();
            aux = nodoOG;
            while (ady != null) {
                //Si no se encuentra en el clon lo creo
                if (vis.localizar(ady.getVertice().getElem()) < 0) {
                    vis.insertar(ady.getVertice().getElem(), 1);
                    nodoClon.setSigVertice(new NodoVert(ady.getVertice().getElem()));
                    nodoClon = nodoClon.getSigVertice();
                }
            }
        }
    }
}