package estructuras.arbolGenerico;

import estructuras.lineales.Cola;
import estructuras.lineales.Lista;

public class ArbolGen {

    private NodoGen raiz;

    public ArbolGen() {
        this.raiz = null;
    }

    public boolean insertar(Object elementoNuevo, Object elementoPadre) {
        // Metodo que inserta elementoNuevo como hijo de la primera aparicion de
        // elementoPadre
        boolean respuesta = true;

        // Checkeo si el arbol esta vacio en caso de que lo este inserto elemento como
        // raiz
        if (this.esVacio())
            this.raiz = new NodoGen(elementoNuevo);

        // En caso de que no este vacio recorro el arbol en busca del padre
        else {
            NodoGen nodoPadre = obtenerNodo(this.raiz, elementoPadre);

            // Si el nodo padre es encontrado
            if (nodoPadre != null) {
                insertarElemento(elementoNuevo, nodoPadre);
            } else
                respuesta = false;
        }
        return respuesta;
    }

    private void insertarElemento(Object elementoNuevo, NodoGen nodoPadre) {
        // Metodo que inserta elemento con el nodo padre pasado por parametro
        NodoGen nodoHijo = nodoPadre.getHijoIzquierdo();

        // Si nodo no tiene hijo izquierdo creo y seteo un nuevo nodo con elemento nuevo
        if (nodoHijo == null)
            nodoPadre.setHijoIzquierdo(new NodoGen(elementoNuevo));

        // Caso contrario busco al ultimo hermano derecho para crear y setear el nuevo
        // nodo
        else {
            // Recorro sus hermanos derechos hasta encontrar el que no tiene hermano derecho
            while (nodoHijo.getHermanoDerecho() != null) {
                nodoHijo = nodoHijo.getHermanoDerecho();
            }
            nodoHijo.setHermanoDerecho(new NodoGen(elementoNuevo));
        }
    }

    // Metodo alternativo para insertar
    public boolean insertarPosPadre(Object elemento, int posicionPadre) {
        // Metodo que inserta el elemento pasado por parametro segun la posicion del
        // padre en preorden
        boolean exito = true;

        if (this.esVacio() && posicionPadre == 0) {
            this.raiz = new NodoGen(elemento);

        } else {
            int posicionActual = 0;
            posicionActual = insertarPosPadreAux(this.raiz, elemento, posicionPadre, ++posicionActual);

            // Comparo si las posiciones son iguales entonces se inserto
            exito = posicionActual == -1;
        }

        return exito;
    }

    private int insertarPosPadreAux(NodoGen nodo, Object elemento, int posicionPadre, int posicionActual) {
        // Metodo auxiliar que inserta un elemento segun la posiicon del padre en
        // preoden

        // Si se encuentra al padre
        if (posicionActual == posicionPadre) {
            // Inserto el elemento y seteo posicion actual como -1
            insertarElemento(elemento, nodo);
            posicionActual = -1;
        } else {
            // Caso contrario busco en su hijo izquierdo
            if (nodo.getHijoIzquierdo() != null) {
                posicionActual = insertarPosPadreAux(nodo.getHijoIzquierdo(), elemento, posicionPadre,
                        ++posicionActual);
            }
            // Busco en su hijo derecho si todavia no se encontro
            if (nodo.getHermanoDerecho() != null && posicionActual > 0) {
                posicionActual = insertarPosPadreAux(nodo.getHermanoDerecho(), elemento, posicionPadre,
                        ++posicionActual);
            }
        }

        return posicionActual;
    }

    private NodoGen obtenerNodo(NodoGen nodoActual, Object elemento) {
        // Metodo que encuentra y retorna un nodo que contiene al elemento buscado en
        // Preorden
        NodoGen resultado = null;

        if (nodoActual != null) {

            // Si encontramos al padre
            if (nodoActual.getElemento().equals(elemento))
                resultado = nodoActual;

            // Si no se encuentra nos movemos a sus hijos
            else {
                // Nos movemos al hijo del nodo, en caso de no tener no entra en el loop y
                // retorna null
                NodoGen hijo = nodoActual.getHijoIzquierdo();

                // Hacemos las llamadas recursiva de este metodo para cada hijo del nodo actual;
                // si lo encuentra se corta
                while (hijo != null && resultado == null) {
                    resultado = obtenerNodo(hijo, elemento);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return resultado;
    }

    public boolean pertenece(Object elemento) {
        // Metodo que busca si el elemento ingresado por parametro pertenece al arbol
        return obtenerNodo(this.raiz, elemento) != null;
    }

    public Lista ancestros(Object elemento) {
        // Metodo que devuelve una lista con el camino desde la raiz hasta dicho
        // elemento si es que ese elemento se encuentra en el AG
        Lista lista = new Lista();

        // Checkeo si el arbol no esta vacio y si el elemento que se busca es la raiz
        if (this.raiz != null) {
            ancestrosAux(this.raiz, elemento, lista);
        }

        return lista;
    }

    private boolean ancestrosAux(NodoGen nodoActual, Object elemento, Lista lista) {
        // Metodo que busca el nodo que contiene al elemento y retorna un boolean si es
        // que lo encuentra
        boolean encontrado = false;

        if (nodoActual != null) {
            // Si encontramos el elemento buscado actualizo el valor de la bandera
            if (nodoActual.getElemento().equals(elemento)) {
                encontrado = true;
            } else {
                // Busco en sus hijos si es que tiene
                NodoGen aux = nodoActual.getHijoIzquierdo();

                while (aux != null && !encontrado) {
                    encontrado = ancestrosAux(aux, elemento, lista);
                    aux = aux.getHermanoDerecho();
                }
                // Si se encontro en los hijos añado nodoActual a la lista
                if (encontrado)
                    lista.insertar(nodoActual.getElemento(), lista.longitud() + 1);
            }
        }
        return encontrado;
    }

    public boolean esVacio() {
        // Metodo que retorna true en caso de que el arbol este vacio y false en caso de
        // no estarlo
        return this.raiz == null;
    }

    public int altura() {
        // Metodo que retorna la altura del arbol
        return obtenerAltura(this.raiz, -1);
    }

    private int obtenerAltura(NodoGen nodoActual, int altura) {
        // Metodo que recorre el arbol y devuelve la altura

        if (nodoActual != null) {
            // Busco altura en los hijos del nodoActual, como bajo sumo 1 a la altura
            int alturaIzq = obtenerAltura(nodoActual.getHijoIzquierdo(), altura + 1);

            // Busco altura en los hermanos de nodoActual, como estoy en el mismo nivel paso
            // la misma altura
            int alturaDer = obtenerAltura(nodoActual.getHermanoDerecho(), altura);

            // Ahora comparo la altura maxima y me quedo con esa
            altura = Math.max(alturaIzq, alturaDer);
        }
        return altura;
    }

    public int nivel(Object elemento) {
        // Retorna el nivel del elemento ingresado por parametro
        return buscarNivel(this.raiz, elemento, -1);
    }

    private int buscarNivel(NodoGen nodo, Object elemento, int nivel) {
        // Retorna el nivel del elemento buscado en el arbol

        if (nodo != null) {

            // Si encuentro el elemento aumento en 1 el nivel
            if (nodo.getElemento().equals(elemento)) {
                nivel++;
            } else {
                // Si no es encontrado recorro su hijoIzq
                nivel = buscarNivel(nodo.getHijoIzquierdo(), elemento, nivel);

                // Si nivel cambio entonces se encontro en el nivel inferior por lo tanto le
                // sumo 1
                if (nivel != -1)
                    nivel++;

                // Sino busco en los hermanos de nodo
                else
                    nivel = buscarNivel(nodo.getHermanoDerecho(), elemento, nivel);
            }
        }
        return nivel;
    }

    public Object padre(Object elemento) {
        // Metodo que retorna el elemento padre del elemento ingresado por parametro
        Object resultado = null;

        // Si el arbol esta vacio o si la raiz es elemento a la que se le busca el padre
        // retorna null; caso contrario busca al padre
        if (this.raiz != null && !this.raiz.getElemento().equals(elemento)) {
            resultado = buscaElementoPadre(this.raiz, elemento);
        }
        return resultado;
    }

    private Object buscaElementoPadre(NodoGen nodoActual, Object elemento) {
        // Metodo que busca un nodo en y retorna el nodo padre del mismo
        Object padre = null;

        if (nodoActual != null) {
            NodoGen hijo = nodoActual.getHijoIzquierdo();

            // Chekeamos si alguno de los hijos del nodo actual es el buscado si es que
            // tiene
            while (hijo != null && padre == null) {

                if (hijo.getElemento().equals(elemento))
                    padre = nodoActual.getElemento();

                else
                    hijo = hijo.getHermanoDerecho();
            }

            hijo = nodoActual.getHijoIzquierdo();

            // Ahora checkeamos si los hijos del nodoActual son padre del elemento buscado
            while (hijo != null && padre == null) {

                padre = buscaElementoPadre(hijo, elemento);
                hijo = hijo.getHermanoDerecho();
            }
        }
        return padre;
    }

    public Lista listarPreorden() {
        // Metodo que devuelve una lista con los elementos del AG en preorden
        Lista lista = new Lista();

        if (this.raiz != null)
            listarPreordenRecursivo(this.raiz, lista);

        return lista;
    }

    private void listarPreordenRecursivo(NodoGen nodoActual, Lista lista) {

        if (nodoActual != null) {
            // Añado el nodo actual a la lista
            lista.insertar(nodoActual.getElemento(), lista.longitud() + 1);

            // Recorro los hijos del nodoActual si es que tiene
            NodoGen hijo = nodoActual.getHijoIzquierdo();

            while (hijo != null) {
                listarPreordenRecursivo(hijo, lista);
                hijo = hijo.getHermanoDerecho();
            }
        }
    }

    public Lista listarInorden() {
        // Metodo que devuelve una lista con los elemento del AG en inorden
        Lista lista = new Lista();

        if (this.raiz != null)
            listarInordenRecursivo(this.raiz, lista);

        return lista;
    }

    private void listarInordenRecursivo(NodoGen nodoActual, Lista lista) {

        if (nodoActual != null) {

            // Recorro el subarbol izquierdo
            NodoGen hijo = nodoActual.getHijoIzquierdo();

            listarInordenRecursivo(hijo, lista);

            // Añado el nodoActual a la lista
            lista.insertar(nodoActual.getElemento(), lista.longitud() + 1);

            // Recorro los hijos de nodoActual (Avanzo hijo primero ya que se inserto
            // previamente)
            while (hijo != null) {
                hijo = hijo.getHermanoDerecho();
                listarInordenRecursivo(hijo, lista);
            }
        }
    }

    public Lista listarPosorden() {
        // Metodo que devuelve una lista con los elementos del AG en posorden
        Lista lista = new Lista();

        if (this.raiz != null)
            listarPosordenRecursivo(this.raiz, lista);

        return lista;
    }

    private void listarPosordenRecursivo(NodoGen nodoActual, Lista lista) {

        if (nodoActual != null) {

            // Recorro el subarbol izquierdo
            NodoGen hijo = nodoActual.getHijoIzquierdo();

            // Recorro los hijos de nodoAtual
            while (hijo != null) {
                listarPosordenRecursivo(hijo, lista);
                hijo = hijo.getHermanoDerecho();
            }

            // Añado el nodoActual a la lista
            lista.insertar(nodoActual.getElemento(), lista.longitud() + 1);
        }
    }

    public Lista listarNiveles() {
        // Metoco que retorna una lista con los elementos del AG en el recorrido por
        // niveles
        Lista lista = new Lista();

        if (this.raiz != null) {
            Cola cola = new Cola();
            NodoGen nodoActual, hijo;

            cola.poner(this.raiz);

            while (!cola.esVacia()) {

                nodoActual = (NodoGen) cola.obtenerFrente();
                cola.sacar();

                lista.insertar(nodoActual.getElemento(), lista.longitud() + 1);

                hijo = nodoActual.getHijoIzquierdo();

                // Visito a los hijos del nodoActual y los pongo en la cola
                while (hijo != null) {
                    cola.poner(hijo);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return lista;
    }

    public ArbolGen clone() {
        // Genera y devuelve un arbol generico que es equivalente que el arbol original
        ArbolGen clon = new ArbolGen();

        if (this.raiz != null) {
            // Creo un nodo nuevo y se lo asigno con el valor de la raiz al clon
            clon.raiz = new NodoGen(this.raiz.getElemento());

            // Hago el llamado recursivo
            cloneRecursivo(this.raiz, clon.raiz);
        }

        return clon;
    }

    private void cloneRecursivo(NodoGen nodoActual, NodoGen nodoClon) {

        if (nodoActual != null) {
            // Si nodoActual posee hijos creo y seteo los hijos del nodo clon
            NodoGen hijoActual = nodoActual.getHijoIzquierdo(), hijoClon;
            if (hijoActual != null) {
                nodoClon.setHijoIzquierdo(new NodoGen(hijoActual.getElemento()));

                hijoActual = hijoActual.getHermanoDerecho();
                hijoClon = nodoClon.getHijoIzquierdo();

                // Creo y seteo hermanos del hijoActual si es que tiene
                while (hijoActual != null) {
                    hijoClon.setHermanoDerecho(new NodoGen(hijoActual.getElemento()));
                    // Me muevo a los hermanos
                    hijoActual = hijoActual.getHermanoDerecho();
                    hijoClon = hijoClon.getHermanoDerecho();
                }

                hijoActual = nodoActual.getHijoIzquierdo();
                hijoClon = nodoClon.getHijoIzquierdo();

                // Ahora hago el llamado recursivo para todos los hijos del nodoActual
                while (hijoActual != null) {
                    cloneRecursivo(hijoActual, hijoClon);
                    hijoActual = hijoActual.getHermanoDerecho();
                    hijoClon = hijoClon.getHermanoDerecho();
                }
            }
        }
    }

    public void vaciar() {
        // Metodo que vacia el arbol teniendo en cuenta el garbage collector de java
        this.raiz = null;
    }

    public String toString() {
        // Metodo que devuelve un string con la estructura del arbol
        return toStringRecursivo(this.raiz);
    }

    private String toStringRecursivo(NodoGen nodo) {
        String cadena = "";

        if (nodo != null) {
            // Visito al nodo
            cadena += nodo.getElemento().toString() + "->";
            NodoGen hijo = nodo.getHijoIzquierdo();

            // recorro todos los hijos del nodo actual
            while (hijo != null) {
                cadena += hijo.getElemento().toString() + ", ";
                hijo = hijo.getHermanoDerecho();
            }

            // Comienza recorrido de los hijos del nodo llamando recursivamente
            // para qe cada hijo agregue su subcadena a la general
            hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                cadena += "\n" + toStringRecursivo(hijo);
                hijo = hijo.getHermanoDerecho();
            }
        }
        return cadena;
    }

    public boolean verificarPatron(Lista lisPatron) {
        // Metodo que retorna un t/f si se encuentra potron en el AG
        boolean encontrado = false;
        int pos = 1;

        if (!this.esVacio()) {
            Object elemento = lisPatron.recuperar(pos);
            NodoGen nodoAux = obtenerNodo(this.raiz, elemento);

            // Veo si existe el primer nodo de la lista caso contrario retorna false
            if (nodoAux != null)
                encontrado = verificarPatronAux(nodoAux.getHijoIzquierdo(), lisPatron, pos + 1);
        }
        return encontrado;
    }

    private boolean verificarPatronAux(NodoGen nodoActual, Lista lista, int pos) {
        boolean encontrado = false;

        if (nodoActual != null) {
            Object elemento = lista.recuperar(pos);

            // Si el nodoActual es igual al elemento en la posicion pos de la lista
            if (nodoActual.getElemento().equals(elemento)) {
                // Si la posicion es igual a la longitud entonces termino y se encontro patron
                if (pos == lista.longitud())
                    encontrado = true;
                // Sino sigo buscando en los hijos del nodoActual
                else {
                    encontrado = verificarPatronAux(nodoActual.getHijoIzquierdo(), lista, pos + 1);
                }
            }

            // Si no se encontro busco en los hermanos del nodoActual
            if (encontrado == false)
                encontrado = verificarPatronAux(nodoActual.getHermanoDerecho(), lista, pos);
        }
        return encontrado;
    }

    public Lista frontera() {
        // Metodo que devuelve una lista con la frontera del arbol de izq a derecha
        Lista lista = new Lista();

        if (!this.esVacio())
            fronteraAux(this.raiz, lista);

        return lista;
    }

    private void fronteraAux(NodoGen nodoActual, Lista lista) {

        if (nodoActual != null) {
            // Si el nodo no tiene hijo izq entonces es hoja
            NodoGen hijo = nodoActual.getHijoIzquierdo();

            if (hijo == null)
                lista.insertar(nodoActual.getElemento(), lista.longitud() + 1);

            // Sino busco en preorden las hojas
            else {
                while (hijo != null) {
                    fronteraAux(hijo, lista);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
    }

    public Lista listaQueJustificaLaAltura() {
        // Devuelve una lista de elementos que es el camino que comienza en la raiz y
        // termina en la hoja mas lejana
        Lista listaAltura = new Lista(), caminoActual = new Lista();

        if (this.raiz != null) {
            caminoActual.insertar(this.raiz.getElemento(), 1);
            listaAltura = listaQueJustificaLaAlturaAux(listaAltura, caminoActual, this.raiz.getHijoIzquierdo());
        }

        return listaAltura;
    }

    private Lista listaQueJustificaLaAlturaAux(Lista listaAltura, Lista caminoActual, NodoGen nodoActual) {
        NodoGen hijo, hermano;
        if (nodoActual != null) {
            // Recorro HI aumentando la altura
            caminoActual.insertar(nodoActual.getElemento(), caminoActual.longitud() + 1);

            hijo = nodoActual.getHijoIzquierdo();
            // Si no tiene hijo entonces checkeo la altura
            if (hijo == null) {
                if (caminoActual.longitud() > listaAltura.longitud()) {
                    listaAltura = caminoActual.clone();
                }
            } else {
                listaAltura = listaQueJustificaLaAlturaAux(listaAltura, caminoActual, hijo);
            }
            // Saco de la lista al nodo actual
            caminoActual.eliminar(caminoActual.localizar(nodoActual.getElemento()));
            // Recorro a los hermanos
            hermano = nodoActual.getHermanoDerecho();

            while (hermano != null) {
                listaAltura = listaQueJustificaLaAlturaAux(listaAltura, caminoActual, hermano);
                // saco al hermano del camino y actualizo hermano
                caminoActual.eliminar(caminoActual.longitud());
                hermano = hermano.getHermanoDerecho();
            }

        }
        return listaAltura;
    }

}
