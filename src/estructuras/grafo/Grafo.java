package estructuras.grafo;

import java.util.HashMap;
import java.util.Map;

import estructuras.lineales.Cola;
import estructuras.lineales.ColaPrioridad;
import estructuras.lineales.Lista;
import estructuras.lineales.Par;

public class Grafo {

    private NodoVert inicio;

    public Grafo() {
        this.inicio = null;
    }

    public boolean insertarVertice(Object vertice) {
        // Metodo que inserta un vertice en el grafo controlando que no se repita en el
        // mismo
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

    public boolean eliminarVertice(Object vertice) {
        // Metodo que elimina un vertice de la estructura y todos los arcos que lo
        // contengan como vertice
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
            eliminarArcos(nodo);
            exito = true;
        }
        return exito;
    }

    private void eliminarArcos(NodoVert nodoAEliminar) {
        // Metodo que elimina arcos de un grafo direccional
        NodoAdy arco = nodoAEliminar.getPrimerAdy();

        // Mientras el nodo a eliminar tenga arcos
        while (arco != null) {
            // Como es un grafo entonces se que ambos nodos tienen los mismos arcos por ende
            // puedo eliminar arcos usando los arcos del nodo a eliminar
            eliminarArcoConNodos(arco.getVertice(), nodoAEliminar);
            arco = arco.getSigAdyacente();
        }
    }

    private boolean eliminarArcoConNodos(NodoVert origen, NodoVert destino) {
        // Metodo que analiza los diferentes casos de eliminar un arco y devuelve un
        // boolean
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

            // Si es encontrado seteo al nodoAdy anterior con el posterior del nodoAdy a
            // eliminar
            if (arcoBuscado != null) {
                anterior.setSigAdyacente(arcoBuscado.getSigAdyacente());
            }
            // Caso contrario no se encontró y, por lo tanto, no se pudo eliminar
            else
                exito = false;
        }
        return exito;
    }

    public boolean insertarArco(Object origen, Object destino, double etiqueta) {
        // Metodo que agrega un arco en la estructura solo si ambos nodos existen en la
        // misma
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

    public boolean eliminarArco(Object origen, Object destino, double etiqueta) {
        // Metodo que elimina solo si este existe entre origen y destino pasados por
        // parametros
        boolean exito = false;

        // Si el grafo no está vacio busco en la estructura ambos nodos
        if (this.inicio != null) {
            NodoVert nodoOrigen = ubicarVertice(origen), nodoDestino = ubicarVertice(destino);

            // Si ambos exiten busco si existe el arco
            if (nodoOrigen != null && nodoDestino != null) {
                // Busco el arco en el nodo origen si existe lo elimino
                exito = eliminarArcoConEtiqueta(nodoOrigen, nodoDestino, etiqueta);
                eliminarArcoConEtiqueta(nodoDestino, nodoOrigen, etiqueta);
            }
        }
        return exito;
    }

    private boolean eliminarArcoConEtiqueta(NodoVert origen, NodoVert destino, double etiqueta) {
        // Metodo que analiza los diferentes casos de eliminar un arco y devuelve un
        // boolean
        boolean exito = false;
        NodoAdy arcoBuscado = origen.getPrimerAdy(), anterior;

        // Si el no posee arcos
        if (arcoBuscado != null) {
            // Si el arco es el primero se setea al siguiente como primero
            if (arcoBuscado.getVertice() == destino && arcoBuscado.getEtiqueta() == etiqueta) {
                origen.setPrimerAdy(arcoBuscado.getSigAdyacente());
                exito = true;
            } else {
                anterior = null;
                // Caso contrario recorro todos los arcos de origen hasta encontrarlo
                while (arcoBuscado != null && arcoBuscado.getVertice() != destino
                        && !(arcoBuscado.getEtiqueta() == etiqueta)) {
                    anterior = arcoBuscado;
                    arcoBuscado = arcoBuscado.getSigAdyacente();
                }

                // Si es encontrado seteo al nodoAdy anterior con el posterior del nodoAdy a
                // eliminar
                if (arcoBuscado.getEtiqueta() == etiqueta) {
                    if (anterior != null) {
                        anterior.setSigAdyacente(arcoBuscado.getSigAdyacente());
                    } else {
                        origen.setPrimerAdy(arcoBuscado.getSigAdyacente());
                    }
                    exito = true;
                }
            }
        }
        return exito;
    }

    public boolean existeVertice(Object buscado) {
        // Metodo que recorre los vertices para ver si existe en el grafo
        return ubicarVertice(buscado) != null;
    }

    public boolean existeArco(Object origen, Object destino) {
        // Metodo que recorre la estructura para verificar si existe un arco entre los
        // vertices pasados por parametro
        boolean exito = false;
        NodoVert nodoOrigen = ubicarVertice(origen), nodoDestino = ubicarVertice(destino);
        // Sí existen amnbos nodos en el grafo
        if (nodoOrigen != null && nodoDestino != null) {
            // Analizo cada arco del nodo origen
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
        // Metodo que recorre la estructura y verifica que exista un camino entre los
        // nodos pasados por parametro
        boolean exito = false;
        NodoVert nodoOrigen = ubicarVertice(origen), nodoDestino = ubicarVertice(destino);

        // Si existen ambos vertices busca si existe camino entre ambos
        if (nodoOrigen != null && nodoDestino != null) {
            Lista visitados = new Lista();
            exito = existeCaminoAux(nodoOrigen, destino, visitados);
        }
        return exito;
    }

    private boolean existeCaminoAux(NodoVert nodo, Object destino, Lista visitados) {
        boolean exito = false;

        if (nodo != null) {
            // Si vertice nodo es el destino entonces hay camino
            if (nodo.getElem().equals(destino))
                exito = true;
            else {
                // Si no es el destino verifica si hay camino entre nodo y destino
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

    // * ====================== NO SE USAN ====================== */
    public Par caminoMasCortoDijkstra(Object origen, Object destino) {
        // Metodo que devuelve un par con la distancia y el camino mas corto si es que
        // existen los nodos en el grafo, utilizando el algoritmo de Dijkstra

        // Estructuras que usa el algoritmo
        Map<NodoVert, Double> distancia = new HashMap<>();
        Map<NodoVert, NodoVert> padre = new HashMap<>();
        ColaPrioridad colaNodos = new ColaPrioridad();
        Lista visitados = new Lista();

        // Variables que usa el algoritmo
        Par distanciaYCamino = new Par("", "");
        NodoVert nodoActual = this.inicio, nodoOrigen = this.ubicarVertice(origen),
                nodoDestino = this.ubicarVertice(destino), vecino;
        NodoAdy arcoActual;
        boolean encontrado = false;
        double distanciaTentativa;

        // Si existen los nodos empiezo a buscar caminos caso contrario retorno un par
        // vacio
        if (nodoOrigen != null && nodoDestino != null) {
            // Inicializo las distancias de todos los nodos
            inicializarEstructuras(distancia, padre, colaNodos, nodoActual, nodoOrigen);

            // Mientras no este vacia la cola de nodos
            while (!colaNodos.esVacia() || !encontrado) {
                // Saco el elemento con la prioridad mas alta de la cola
                nodoActual = (NodoVert) colaNodos.obtenerFrente();
                colaNodos.sacar();
                // Visito los nodos vecinos y los pongo en la lista de visitados
                visitados.insertar(nodoActual, visitados.longitud() + 1);

                // Se encotro el nodo destino
                if (nodoActual.equals(nodoDestino)) {
                    encontrado = true;
                } else {
                    // Se recorren todos los nodos adyacentes del nodo actual
                    arcoActual = nodoActual.getPrimerAdy();
                    while (arcoActual != null) {

                        // Variable para no hacer tantos llamados
                        vecino = arcoActual.getVertice();
                        // Si el nodo no se visito
                        if (visitados.localizar(vecino) < 0) {
                            distanciaTentativa = distancia.get(nodoActual) + (double) arcoActual.getEtiqueta();

                            // Si la distancia tentativa es menor que la que tiene el vecino entonces la
                            // actualizo
                            if (distanciaTentativa < distancia.get(vecino)) {
                                distancia.put(vecino, distanciaTentativa);
                                padre.put(vecino, nodoActual);
                                colaNodos.poner(vecino, distanciaTentativa);
                            }
                        }
                        arcoActual = arcoActual.getSigAdyacente();
                    }
                }
            }
            // Si se encontro entonces armo y devuelvo un par con lista del camino y
            // distancia del mismo
            if (encontrado) {
                setDistanciaYCamino(distancia, padre, distanciaYCamino, nodoDestino);
            }
        }
        return distanciaYCamino;
    }

    private void inicializarEstructuras(Map<NodoVert, Double> distancia, Map<NodoVert, NodoVert> padre,
            ColaPrioridad colaNodos,
            NodoVert nodoActual, NodoVert nodoOrigen) {
        // Metodo que inicializa las estructuras para que sea posible usar el algoritmo
        // de dijkstra

        while (nodoActual != null) {
            distancia.put(nodoActual, Double.MAX_VALUE);
            padre.put(nodoActual, null);
            nodoActual = nodoActual.getSigVertice();
        }

        // Actualizo la distancia del nodo origen a 0 en el hashmap distancias y lo
        // agrego a la cola de prioridad
        distancia.replace(nodoOrigen, 0.0);
        colaNodos.poner(nodoOrigen, 0);
    }

    private void setDistanciaYCamino(Map<NodoVert, Double> distancia, Map<NodoVert, NodoVert> padre,
            Par distanciaYCamino,
            NodoVert nodoDestino) {
        // Metodo que setea el par distaciaYCamino con una lista de nodos vertices y la
        // distancia del mismo

        NodoVert nodoActual;
        Lista camino = new Lista();
        nodoActual = nodoDestino;

        while (nodoActual != null) {
            camino.insertar(nodoActual, 1);
            nodoActual = padre.get(nodoActual);
        }

        // Seteo el par (int, Lista)
        distanciaYCamino.setA(distancia.get(nodoDestino));
        distanciaYCamino.setB(camino);
    }

    public Par menorCaminoCantidadNodosAnchura(Object origen, Object destino) {
        // Metodo que devuelve una lista con el camino de origen a destino que pase por
        // la menor cantidad de nodos posibles utilizando BFS (Busqueda en anchura)
        HashMap<NodoVert, NodoVert> padre = new HashMap<>();
        Lista visitados = new Lista();
        Cola cola = new Cola();
        Par cantNodosYCamino = new Par("", "");
        NodoVert nodoOrigen = ubicarVertice(origen), nodoDestino = ubicarVertice(destino), nodoActual, vecino;
        NodoAdy nodoAdy;
        boolean encontrado = false;

        if (nodoOrigen != null && nodoDestino != null) {

            cola.poner(nodoOrigen);
            visitados.insertar(nodoOrigen, 1);

            // Analiza si el nodo se encontro sino busca en sus nodos vecinos
            while (!cola.esVacia() && !encontrado) {

                nodoActual = (NodoVert) cola.obtenerFrente();
                cola.sacar();

                // Si el nodo se encontro
                if (nodoActual.equals(nodoDestino)) {
                    encontrado = true;
                } else {
                    nodoAdy = nodoActual.getPrimerAdy();

                    // Para cada vecino del nodo actual
                    while (nodoAdy != null) {
                        vecino = nodoAdy.getVertice();
                        // Si no fue visitado lo agrega a la cola, a la lista de visitados y actualiza
                        // el HM de padre
                        if (visitados.localizar(vecino) < 0) {
                            cola.poner(vecino);
                            visitados.insertar(vecino, 1);
                            padre.put(vecino, nodoActual);
                        }

                        nodoAdy = nodoAdy.getSigAdyacente();
                    }
                }
            }

            // Si se encontro rearmo el camino y seteo los valores del Par a retornar
            if (encontrado) {
                visitados.vaciar();
                nodoActual = nodoDestino;

                while (nodoActual != null) {
                    visitados.insertar(nodoActual, 1);
                    nodoActual = padre.get(nodoActual);
                }

                // Seteo el par (int, Lista) le resto los nodos inicial y final ya que no
                // estarian "en medio"
                cantNodosYCamino.setA(visitados.longitud() - 2);
                cantNodosYCamino.setB(visitados);
            }
        }

        return cantNodosYCamino;
    }

    public boolean verificarCaminoMenorDistaciaDijsktra(Object origen, Object destino, double distancia) {
        // Metodo que verifica si se puede llegar de A - B en distancia o menos
        ColaPrioridad cola = new ColaPrioridad();
        Lista visitados = new Lista();
        HashMap<NodoVert, Double> distanciaNodo = new HashMap<>();
        NodoVert nodoOrigen = ubicarVertice(origen), nodoDestino = ubicarVertice(destino), nodoActual = this.inicio,
                vecino;
        NodoAdy arcoActual;
        double distanciaTentativa;
        boolean exito = false, encontrado = false;

        // Verifico que existan ambos nodos
        if (nodoOrigen != null && nodoDestino != null) {

            // Se inicilizan las estructuras [NO ES NECESARIO TENER PADRES ya que no se
            // devuelve una lista]
            while (nodoActual != null) {
                distanciaNodo.put(nodoActual, Double.MAX_VALUE);
                nodoActual = nodoActual.getSigVertice();
            }

            distanciaNodo.put(nodoOrigen, 0.0);
            cola.poner(nodoOrigen, 0);

            // Mientras la cola no este vacia, el nodo destino no se encontro
            while (!cola.esVacia() && !encontrado) {

                nodoActual = (NodoVert) cola.obtenerFrente();
                cola.sacar();
                visitados.insertar(nodoActual, 1);

                // Si la distancia actual es menor o igual a la ingresada por parametro
                if (distanciaNodo.get(nodoActual) <= distancia) {

                    // Si se encontro el nodoDestino
                    if (nodoActual.equals(nodoDestino)) {
                        encontrado = true;
                        exito = true;
                    } else {
                        // Empiezo a recorrer a los vecinos de nodoActual
                        arcoActual = nodoActual.getPrimerAdy();

                        while (arcoActual != null) {
                            vecino = arcoActual.getVertice();

                            // Si el nodo vecino no se visito lo anthis.inicio, alizo
                            if (visitados.localizar(vecino) < 0) {
                                distanciaTentativa = distanciaNodo.get(nodoActual) + (double) arcoActual.getEtiqueta();

                                // Si la distanciaTentativa es menor que la distancia que ya tiene asignada el
                                // vecino previamente
                                // y ademas es menor que la distancia ingresada por parametro entonces agrego el
                                // vecino a la cola
                                // y actualizo su distancia
                                if (distanciaTentativa < distanciaNodo.get(vecino)
                                        && distanciaTentativa <= distancia) {
                                    cola.poner(vecino, distanciaTentativa);
                                    distanciaNodo.put(vecino, distanciaTentativa);
                                }
                            }
                            arcoActual = arcoActual.getSigAdyacente();
                        }
                    }
                }
            }
        }
        return exito;
    }

    // * ====================================================================== */

    public Lista caminoMasCorto(Object origen, Object destino) {
        double distanciaActual = 0.0;
        Lista caminoActual = new Lista(), caminoMasCorto = new Lista();
        NodoVert nodoOrigen = this.ubicarVertice(origen), nodoDestino = this.ubicarVertice(destino);
        HashMap<NodoVert, Boolean> estaSiendoVisitado = new HashMap<>();
        Par menorCaminoDistacia;

        // Si ambos nodos existen en el grafo
        if (nodoOrigen != null && nodoDestino != null) {
            // Inicializo el par con una lista vacia y una distancia minima
            menorCaminoDistacia = new Par(caminoMasCorto, Double.MAX_VALUE);

            // Lleno un HM con los Nodos y false como key,value
            llenarHashVisitados(estaSiendoVisitado);

            caminoActual.insertar(nodoOrigen, 1);
            obtenerCaminoMenorDistancia(nodoOrigen, caminoActual, nodoDestino, distanciaActual, menorCaminoDistacia,
                    estaSiendoVisitado);
            caminoMasCorto = (Lista) menorCaminoDistacia.getA();
        }

        return caminoMasCorto;
    }

    private void obtenerCaminoMenorDistancia(NodoVert nodoActual, Lista caminoActual, NodoVert nodoDestino,
            double distanciaActual, Par menorCaminoDistancia, HashMap<NodoVert, Boolean> estaSiedoVisitado) {
        // Metodo que obtiene el camino con menor distancia del nodoOrigen al
        // nodoDestino
        NodoVert vecino;
        NodoAdy arcoActual;
        double distanciaArco;

        if (distanciaActual < (double) menorCaminoDistancia.getB()) {
            // Si se encuentra el nodo destino se setea la nueva menor distancia y el nuevo
            // camino mas corto
            if (nodoActual.equals(nodoDestino)) {
                menorCaminoDistancia.setA(caminoActual.clone());
                menorCaminoDistancia.setB(distanciaActual);
            } else {
                // Caso contratio se procede a recorrer a los vecinos
                estaSiedoVisitado.put(nodoActual, true);
                arcoActual = nodoActual.getPrimerAdy();

                // Mientras tenga vecinos
                while (arcoActual != null) {
                    vecino = arcoActual.getVertice();
                    distanciaArco = (double) (arcoActual.getEtiqueta());
                    distanciaActual += distanciaArco;

                    // Si no esta siendo visitado
                    if (!estaSiedoVisitado.get(vecino) && distanciaActual < (double) menorCaminoDistancia.getB()) {
                        caminoActual.insertarFin(vecino);
                        obtenerCaminoMenorDistancia(vecino, caminoActual, nodoDestino, distanciaActual,
                                menorCaminoDistancia, estaSiedoVisitado);
                        caminoActual.eliminar(caminoActual.localizar(vecino));
                    }
                    // Resto la cantidad de kms de arcoActual a distanciaActual
                    distanciaActual -= distanciaArco;
                    arcoActual = arcoActual.getSigAdyacente();
                }
                // Saco al nodo actual como visitado
                estaSiedoVisitado.replace(nodoActual, false);
            }
        }
    }

    // Busqueda profundidad menor cantidad de nodos
    public Lista menorCaminoCantidadNodos(Object origen, Object destino) {
        // Metodo que busca por profundidad el camino que pasa por la menor cantidad de
        // nodos
        int cantNodos = 0;
        Lista caminoActual = new Lista(), caminoMenosNodos = new Lista();
        NodoVert nodoOrigen = this.ubicarVertice(origen), nodoDestino = this.ubicarVertice(destino);
        HashMap<NodoVert, Boolean> estaSiendoVisitado = new HashMap<>();
        Par caminoCantNodos;

        // Si ambos nodos existen en el grafo
        if (nodoOrigen != null && nodoDestino != null) {
            // Inicializo el par con una lista vacia y una distancia minima
            caminoCantNodos = new Par(caminoMenosNodos, Integer.MAX_VALUE);

            // Lleno un HM con los Nodos y false como key,value
            llenarHashVisitados(estaSiendoVisitado);

            caminoActual.insertar(nodoOrigen, 1);
            obtenerCaminoMenosNodos(nodoOrigen, nodoDestino, caminoActual, cantNodos, caminoCantNodos,
                    estaSiendoVisitado);
            caminoMenosNodos = (Lista) caminoCantNodos.getA();
        }

        return caminoMenosNodos;
    }

    private void obtenerCaminoMenosNodos(NodoVert nodoActual, NodoVert nodoDestino, Lista caminoActual,
            int cantNodosActuales, Par caminoCantNodos, HashMap<NodoVert, Boolean> estaSiendoVisitado) {
        NodoVert vecino;
        NodoAdy arcoActual;
        // Si la cantidad de nodos actual es menor a la seteada en el par
        if (cantNodosActuales < (int) caminoCantNodos.getB()) {
            // Si se encontro el nodo
            if (nodoActual.equals(nodoDestino)) {
                caminoCantNodos.setA(caminoActual.clone());
                caminoCantNodos.setB(cantNodosActuales);
            } else {
                // Seteo el nodo actual como visitado
                estaSiendoVisitado.put(nodoActual, true);
                arcoActual = nodoActual.getPrimerAdy();

                // Mientras tenga vecinos
                while (arcoActual != null) {
                    vecino = arcoActual.getVertice();
                    cantNodosActuales++;

                    if (!estaSiendoVisitado.get(vecino) && cantNodosActuales < (int) caminoCantNodos.getB()) {
                        caminoActual.insertarFin(vecino);
                        obtenerCaminoMenosNodos(vecino, nodoDestino, caminoActual, cantNodosActuales,
                                caminoCantNodos, estaSiendoVisitado);
                        caminoActual.eliminar(caminoActual.localizar(vecino));
                    }

                    cantNodosActuales--;
                    arcoActual = arcoActual.getSigAdyacente();
                }
                // Pongo al nodo como no visitado
                estaSiendoVisitado.replace(nodoActual, false);
            }
        }
    }

    public boolean verificarCaminoMenorDistacia(Object origen, Object destino, double distanciaLimite) {
        // Metodo que verifica si es posible llegar de un nodo origen al nodo destino en
        // menos de la distanciaLimite pasada por el usuario
        double distanciaActual = 0;
        boolean esPosible = false;
        NodoVert nodoOrigen = this.ubicarVertice(origen), nodoDestino = this.ubicarVertice(destino);
        HashMap<NodoVert, Boolean> estaSiendoVisitado = new HashMap<>();

        // Si ambos nodos existen en el grafo
        if (nodoOrigen != null && nodoDestino != null) {
            // Lleno un HM con los Nodos y false como key,value
            llenarHashVisitados(estaSiendoVisitado);

            esPosible = esPosibleCaminoMenorDistacia(nodoOrigen, nodoDestino, distanciaLimite,
                    distanciaActual,
                    estaSiendoVisitado);
        }

        return esPosible;
    }

    private boolean esPosibleCaminoMenorDistacia(NodoVert nodoActual, NodoVert nodoDestino, double distanciaLimite,
            double distanciaActual, HashMap<NodoVert, Boolean> estaSiendoVisitado) {
        boolean esPosible = false;
        double distanciaArco;
        NodoVert vecino;
        NodoAdy arcoActual;

        if (distanciaActual <= distanciaLimite) {
            if (nodoActual.equals(nodoDestino)) {
                esPosible = true;
            } else {
                // Setea al nodo actual como visitado
                estaSiendoVisitado.put(nodoActual, true);
                arcoActual = nodoActual.getPrimerAdy();

                // Mientras tenga vecinos
                while (!esPosible && arcoActual != null) {
                    distanciaArco = arcoActual.getEtiqueta();
                    distanciaActual += distanciaArco;
                    vecino = arcoActual.getVertice();

                    if (!estaSiendoVisitado.get(vecino) && distanciaActual <= distanciaLimite) {
                        esPosible = esPosibleCaminoMenorDistacia(vecino, nodoDestino, distanciaLimite,
                                distanciaActual, estaSiendoVisitado);
                    }

                    distanciaActual -= distanciaArco;
                    arcoActual = arcoActual.getSigAdyacente();
                }

                // Marco al nodo actual como no visitado
                estaSiendoVisitado.replace(nodoActual, false);
            }
        }
        return esPosible;
    }

    public boolean esVacio() {
        // Metodo que retorna true si el grafo esta vacio y false caso contrario
        return this.inicio == null;
    }

    public void vaciar() {
        // Metodo que vacia la estructura de grafo usando el garbage collector de java
        this.inicio = null;
    }

    public Lista listarCaminosPosibles(Object origen, Object destino) {
        // Metodo que obtiene todos los caminos posibles de origen a destino
        NodoVert nodoOrigen = ubicarVertice(origen), nodoDestino = ubicarVertice(destino);
        Lista caminosPosibles = new Lista(), caminoActual = new Lista();
        HashMap<NodoVert, Boolean> estaSiendoVisitado = new HashMap<>();

        // Verifica si existen los nodos
        if (nodoOrigen != null && nodoDestino != null) {

            llenarHashVisitados(estaSiendoVisitado);

            // Inserto el nodo origen en la primer posicion de la lista para empezar de el a
            // recorrer el grafo
            caminoActual.insertar(nodoOrigen, 1);
            obtenerCaminosProfundidad(nodoOrigen, nodoDestino, caminoActual, estaSiendoVisitado, caminosPosibles);

        }
        return caminosPosibles;
    }

    public Lista listarCaminosPosibles(Object origen, Object intermedio, Object destino) {
        // Metodo que obtiene todos los caminos posibles de origen a destino que pasen
        // por intermedio, el HM estaSiendoVisitado sirve para no entrar en bucles
        NodoVert nodoOrigen = ubicarVertice(origen), nodoDestino = ubicarVertice(destino),
                nodoIntermedio = ubicarVertice(intermedio);
        Lista caminoActual = new Lista(), caminosPosibles = new Lista();
        HashMap<NodoVert, Boolean> estaSiendoVisitado = new HashMap<>();

        // Verifica que todos los nodos por parametros existan en el grafo
        if (nodoOrigen != null && nodoDestino != null && nodoIntermedio != null) {

            llenarHashVisitados(estaSiendoVisitado);

            caminoActual.insertarInicio(nodoOrigen);
            obtenerCaminosProfundidad(nodoOrigen, nodoIntermedio, nodoDestino, caminoActual, estaSiendoVisitado,
                    caminosPosibles);
        }

        return caminosPosibles;
    }

    private void llenarHashVisitados(HashMap<NodoVert, Boolean> estaSiendoVisitado) {
        // Metodo que llena un HM con los nodos pertenecientes al grafo como keys, y el
        // boolean false como values
        NodoVert nodoActual = this.inicio;

        while (nodoActual != null) {
            estaSiendoVisitado.put(nodoActual, false);
            nodoActual = nodoActual.getSigVertice();
        }
    }

    private void obtenerCaminosProfundidad(NodoVert nodoActual, NodoVert nodoDestino, Lista caminoActual,
            HashMap<NodoVert, Boolean> estaSiendoVisitado, Lista caminosPosibles) {
        // Metodo que obtiene todos los caminos posibles hacia nodoDestino y los agrega
        // a la lista de caminos posibles
        NodoVert vecino;
        NodoAdy arcoActual;
        // Si el nodoActual es igual al destino entonces se guarda el camino actual en
        // caminos
        if (nodoActual.equals(nodoDestino)) {
            caminosPosibles.insertar(caminoActual.clone(), 1);
        } else {
            // Caso contrario se procede a recorrer a los vecinos
            estaSiendoVisitado.put(nodoActual, true);
            arcoActual = nodoActual.getPrimerAdy();

            // Mientras tenga vecinos
            while (arcoActual != null) {
                vecino = arcoActual.getVertice();

                // Si no esta siendo visitado
                if (!estaSiendoVisitado.get(vecino)) {
                    caminoActual.insertarFin(vecino);
                    obtenerCaminosProfundidad(vecino, nodoDestino, caminoActual, estaSiendoVisitado, caminosPosibles);
                    caminoActual.eliminar(caminoActual.localizar(vecino));
                }
                arcoActual = arcoActual.getSigAdyacente();
            }
            // Saco al nodo actual como visitado
            estaSiendoVisitado.replace(nodoActual, false);
        }
    }

    private void obtenerCaminosProfundidad(NodoVert nodoActual, NodoVert nodoIntermedio, NodoVert nodoDestino,
            Lista caminoActual, HashMap<NodoVert, Boolean> estaSiendoVisitado, Lista caminos) {
        // Metodo que obtiene un camino en entre dos nodos a traves del DFS [nodoActual
        // nodoIntermedio] sin pasar por el nodoDestino
        NodoVert vecino;
        NodoAdy arcoActual;

        // Si el nodoActual no es el nodoDestino
        if (!nodoActual.equals(nodoDestino)) {
            // Si el nodoActual es el nodoIntermedio entonces procedo a buscar el camino a
            // nodoDestino
            if (nodoActual.equals(nodoIntermedio)) {
                obtenerCaminosProfundidad(nodoActual, nodoDestino, caminoActual, estaSiendoVisitado, caminos);
            } else {
                // Pongo el nodoActual como visitado
                estaSiendoVisitado.put(nodoActual, true);
                arcoActual = nodoActual.getPrimerAdy();

                // Recorro los nodos vecinos
                while (arcoActual != null) {
                    vecino = arcoActual.getVertice();

                    // Si no esta siendo visitado
                    if (!estaSiendoVisitado.get(vecino)) {
                        caminoActual.insertarFin(vecino);
                        obtenerCaminosProfundidad(vecino, nodoIntermedio, nodoDestino, caminoActual, estaSiendoVisitado,
                                caminos);
                        caminoActual.eliminar(caminoActual.localizar(vecino));
                    }
                    arcoActual = arcoActual.getSigAdyacente();
                }
                // Saco al nodoActual como visitado
                estaSiendoVisitado.replace(nodoActual, false);
            }
        }
    }

    public Lista listarEnProfundidad() {
        // Devuelve una lista con los vertices del grafo visitados segun el recorrido en
        // profundidad
        Lista visitados = new Lista();

        // Comienza a recorrer del vertice que esta en el inicio
        NodoVert aux = this.inicio;
        while (aux != null) {
            // Si el vertice no esta en la lista avanza en profundidad
            if (visitados.localizar(aux.getElem()) < 0)
                listarEnProfundidadAux(aux, visitados);

            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert nodo, Lista visitados) {
        // Metodo que recorre recursivamente el grafo en profundidad

        if (nodo != null) {
            // Marca al vertice nodo como visitado
            visitados.insertar(nodo.getElem(), visitados.longitud() + 1);
            NodoAdy ady = nodo.getPrimerAdy();

            // Visita en profundidad los adyacentes de nodo aun no visitados
            while (ady != null) {
                if (visitados.localizar(ady.getVertice().getElem()) < 0)
                    listarEnProfundidadAux(ady.getVertice(), visitados);
                ady = ady.getSigAdyacente();
            }
        }
    }

    public Lista listarEnAnchura() {
        // Metodo que devuelve una lista con los vertices del grafo visitados segun el
        // recorrido de anchura
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;

        while (aux != null) {
            // Si no esta en visitados reccorro en anchura
            if (visitados.localizar(aux.getElem()) < 0)
                listarEnAnchuraAux(aux, visitados);

            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnAnchuraAux(NodoVert nodo, Lista visitados) {
        // Metodo que recorre recursivamente el grafo en anchura
        Cola cola = new Cola();
        NodoVert aux;
        NodoAdy ady;

        cola.poner(nodo);

        // Mientras la cola no este vacia
        while (!cola.esVacia()) {
            aux = (NodoVert) cola.obtenerFrente();
            cola.sacar();
            // Si ya se visito el nodo entonces no lo aniado a la lista
            if (visitados.localizar(aux.getElem()) < 0) {
                visitados.insertar(aux.getElem(), visitados.longitud() + 1);
            }
            ady = aux.getPrimerAdy();
            // Para cada vertice adyacente de aux
            while (ady != null) {
                // Si no esta en la lista lo añado a la cola
                if (visitados.localizar(ady.getVertice().getElem()) < 0)
                    cola.poner(ady.getVertice());
                // Avanzo al proximo adyacente
                ady = ady.getSigAdyacente();
            }
        }
    }

    public Grafo clone() {
        // Metodo que clona la estructura de grafo y la retorna
        Grafo clon = new Grafo();

        if (this.inicio != null) {
            clon.inicio = new NodoVert(this.inicio.getElem());
            cloneAux(clon.inicio, this.inicio);
        }
        return clon;
    }

    private void cloneAux(NodoVert nodoClon, NodoVert nodoOG) {
        // Metodo que crea y asigna los valores de nodos a los nodos de clon
        Lista vis = new Lista();
        NodoVert aux;
        NodoAdy ady;

        vis.insertar(nodoOG.getElem(), 1);

        // Mientras la cola no este vacia
        while (nodoOG != null) {
            // Veo cada arco del nodoOG
            ady = nodoOG.getPrimerAdy();
            aux = nodoOG;
            while (ady != null) {
                // Si no se encuentra en el clon lo creo
                if (vis.localizar(ady.getVertice().getElem()) < 0) {
                    vis.insertar(ady.getVertice().getElem(), 1);
                    nodoClon.setSigVertice(new NodoVert(ady.getVertice().getElem()));
                    nodoClon = nodoClon.getSigVertice();
                }
            }
        }
    }

    public String toString() {
        StringBuilder salida = new StringBuilder();
        NodoVert aux = this.inicio;
        NodoAdy arc;

        // Mientras el siguiente nodo no sea vacio
        while (aux != null) {
            salida.append("[ ");
            salida.append(aux.getElem().toString());
            salida.append(" ] => {");

            arc = aux.getPrimerAdy();
            // Mientras tenga arcos
            while (arc != null) {
                salida.append(
                        " ( " + arc.getVertice().getElem().toString() + " : " + arc.getEtiqueta() + " )");
                arc = arc.getSigAdyacente();
            }

            salida.append(" }\n");
            aux = aux.getSigVertice();
        }

        return salida.toString();
    }
}