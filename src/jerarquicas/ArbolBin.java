package jerarquicas;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;

public class ArbolBin {

    private NodoArbol raiz;

    public ArbolBin(){
        raiz = null;
    }

    public boolean insertar(Object elementoNuevo, Object elementoPadre, char posicion) {
        /*
        Inserta elementoNuevo como hijo del primer nodo encontrado en preorden
        igual a elementoPadre, como hijo izquierdo(I) o derecho(D), segun lo
        indique el parametro posicion
         */

        boolean exito = true;

        if(this.raiz == null)
            //si el arbol esta vacio se le asigna el nuevo elemento a la raiz
            this.raiz = new NodoArbol(elementoNuevo);

        else{
            NodoArbol nodoPadre = obtenerNodo(this.raiz, elementoPadre);

            //Si el nodo padre existe entonces veo si tiene lugar en los hijos
            if (nodoPadre != null) {

                if (posicion == 'I' && nodoPadre.getIzquierdo() == null)
                    // Si el padre existe y no tiene HI se lo asigna
                    nodoPadre.setIzquierdo(new NodoArbol(elementoNuevo));

                else{

                    if(posicion == 'D' && nodoPadre.getDerecho() == null)
                        // Si el padre existe y no tiene HD se lo asigna
                        nodoPadre.setDerecho(new NodoArbol(elementoNuevo));

                    else
                        // si no se cumple ninguna de las condiciones, da error
                        exito = false;
                }
            }
            //Si el padre no existe asigno exito = falso
            else
                exito = false;
        }
        return exito;
    }

    private NodoArbol obtenerNodo(NodoArbol nodo, Object buscado) {
        /*
        Metodo que busca un elemento y devuelve el nodo que lo contiene.
        Si no lo encuentra devuelve null.
        */
        NodoArbol resultado = null;

        if (nodo != null) {

            if(nodo.getElem().equals(buscado))
                //Si encuentro el nodo buscado lo devuelve
                resultado = nodo;

            else {
                //no es el buscado, procede a buscarlo en HI
                resultado = obtenerNodo(nodo.getIzquierdo(),buscado);

                // si no lo encuentra en el HI, lo busca en HD
                if(resultado == null)
                    resultado = obtenerNodo(nodo.getDerecho(),buscado);
            }
        }
        return resultado;
    }

    public boolean esVacio() {
        //Metodo que retorna true o false si la raiz esta vacia o no
        return this.raiz == null;
    }

    public Object padre(Object hijo) {
        //Busca el hijo en el arbol y retorna el elemento del padre en caso de no encontrarlo retorna null
        Object padre = null;

        if (this.raiz != null)
            padre = obtengoPadre(null, this.raiz, hijo);

        return padre;
    }

    private Object obtengoPadre(NodoArbol nodoPadre, NodoArbol nodoActual, Object hijo) {
        /*
        Metodo que verifica si el elemento hijo es igual al elemento del nodo actual
        en caso de ser asi asigna padre al nodo padre del nodo actual caso contrario
        retorna null
        */
        Object padre = null;

        if(nodoActual != null) {

            //Si se encuentra elemento retorna padre
            if (nodoActual.getElem().equals(hijo))
                padre = (nodoPadre != null) ? nodoPadre.getElem() : null;

            //Tiene prioridad el HI
            else {
                padre = obtengoPadre(nodoActual, nodoActual.getIzquierdo(), hijo);

                //Si el HI no lo encontro busca en HD
                if (padre == null)
                    padre = obtengoPadre(nodoActual, nodoActual.getDerecho(), hijo);
            }

        }

        return padre;
    }

    public int altura() {
        //Metodo que verifica la altura de un arbol, retorna -1 en caso de estar vacio
        int altura = -1;

        if(this.raiz != null) {
            //recorro y llevo un contador
            altura = obtenerAltura(this.raiz,altura);
        }


        return altura;
    }

    private int obtenerAltura(NodoArbol nodo,int altura) {
        //Metodo que compara las alturas de los hijos de un nodo y retorna la maxima de ellas

        if (nodo != null){
            //Recorro los distintos hijos del arbol
            int alturaIzq = obtenerAltura(nodo.getIzquierdo(),altura);
            int alturaDer = obtenerAltura(nodo.getDerecho(),altura);

            //Me quedo con el maximo de ambos y la incremento en 1
            altura = Math.max(alturaIzq, alturaDer) + 1;
        }

        return altura;
    }

    public int nivel(Object elemento) {
        //Devuelve el nivel de un elemento, si no es encontrado en el arbol o es vacio retorna -1
        int nivel = -1;

        if(this.raiz != null) {
            nivel = obtenerNivel(this.raiz,elemento,nivel);
        }

        return nivel;
    }

    private int obtenerNivel(NodoArbol nodo, Object elemento, int nivel) {
        //Metodo recursivo que recorre el arbol hasta encontrar un elemento dado y retorna el nivel del mismo

        if (nodo != null) {

            if (nodo.getElem().equals(elemento))
                nivel++;

            else {
                //Recorro los distintos hijos del arbol
                int nivelIzq = obtenerNivel(nodo.getIzquierdo(),elemento,nivel);
                int nivelDer = obtenerNivel(nodo.getDerecho(),elemento,nivel);

                //Si son diferentes entonces se encontro elemento
                if(nivelDer != nivelIzq)
                    nivel = Math.max(nivelDer,nivelIzq) + 1;
            }
        }

        return nivel;
    }

    public void vaciar() {
        //Vacia el arbol aprovechando el garbage collector
        this.raiz = null;
    }

    public ArbolBin clone() {
        //Metodo que clona un arbol y retorna el clon del mismo
        ArbolBin clon = new ArbolBin();

        if(this.raiz != null) {
            //Crea un nuevo nodo y se lo asigna a la raiz clon
            clon.raiz = new NodoArbol(this.raiz.getElem());

            //Llamada a un metodo privado
            clonRecursivo(clon.raiz, this.raiz);
        }

        return clon;
    }

    private void clonRecursivo(NodoArbol nodoClon, NodoArbol nodoOG) {

        if(nodoOG != null) {

            //Setea HI si es que tiene
            if(nodoOG.getIzquierdo() != null) {
                nodoClon.setIzquierdo(new NodoArbol(nodoOG.getIzquierdo().getElem()));
                //Recorre y setea los HI
                clonRecursivo(nodoClon.getIzquierdo(),nodoOG.getIzquierdo());
            }

            //Setea HD si es que tiene
            if(nodoOG.getDerecho() != null) {
                nodoClon.setDerecho(new NodoArbol((nodoOG.getDerecho().getElem())));
                //Recorre y setea los HD
                clonRecursivo(nodoClon.getDerecho(),nodoOG.getDerecho());
            }
        }
    }

    public Lista listarPreorden() {
        //Retorna una lista del arbol en preorden
        Lista lista = new Lista();

        if(!this.esVacio())
            preodenRecursivo(lista, this.raiz);

        return lista;
    }

    private void preodenRecursivo(Lista lista, NodoArbol nodo) {

        if(nodo != null) {

            insertarNodo(lista, nodo);

            //Llamado recursivo y aumento valor de posicion
            preodenRecursivo(lista,nodo.getIzquierdo());
            preodenRecursivo(lista,nodo.getDerecho());
        }
    }

    public Lista listarInorden() {
        //Retorna una lista del arbol en inorden
        Lista lista = new Lista();

        inordenRecursivo(lista,this.raiz);
        
        return lista;
    }

    private void inordenRecursivo(Lista lista, NodoArbol nodo) {

        if(nodo != null) {

            //Recorro el HI
            inordenRecursivo(lista,nodo.getIzquierdo());

            insertarNodo(lista, nodo);

            //Recorro el HD
            inordenRecursivo(lista,nodo.getDerecho());

        }

    }

    public Lista  listarPosorden() {
        //Retorna una lista del arbol en posorden
        Lista lista = new Lista();

        posordenRecursivo(lista,this.raiz);

        return lista;
    }

    private void posordenRecursivo(Lista lista, NodoArbol nodo) {

        if(nodo != null) {

            //Recorro el HI
            posordenRecursivo(lista,nodo.getIzquierdo());
            //Recorro el HD
            posordenRecursivo(lista,nodo.getDerecho());

            insertarNodo(lista, nodo);
        }
    }

    private void insertarNodo(Lista lista, NodoArbol nodo) {
        //Inserta un nodo en una lista
        if (lista.esVacia())
            lista.insertar(nodo.getElem(), 1);

        else
            lista.insertar(nodo.getElem(), lista.longitud() + 1);
    }

    public Lista listarNiveles() {
        //Retorna una lista con los elementos del arbol binario en el recorrido por niveles (No es recursivo)
        Lista lista = new Lista();

        if(!this.esVacio()) {
            //Creo una cola y un nodo auxiliar
            Cola cola = new Cola();
            NodoArbol nodoActual;

            cola.poner(this.raiz);

            //Mientras la cola este llena
            while(!cola.esVacia()){

                //Asigno al nodo actual el nodo guardado en la cola
                nodoActual = (NodoArbol) cola.obtenerFrente();

                //Saco al nodo previamente asignado de la cola
                cola.sacar();

                //Inserto en la lista el elemento del nodo frente de la cola
                lista.insertar(nodoActual.getElem(),lista.longitud()+1);

                //Visito los hijos izquierdo y derecho; si existen los pongo en la cola
                if(nodoActual.getIzquierdo() != null)
                    cola.poner(nodoActual.getIzquierdo());

                if(nodoActual.getDerecho() != null)
                    cola.poner(nodoActual.getDerecho());
            }
        }

        return lista;
    }

    public String toString() {
        //Metodo que retorna un string de la clase arbol
        String arbol;

        arbol = toStringRecursivo(this.raiz,"\n");

        return arbol;
    }

    private String toStringRecursivo(NodoArbol nodo, String cadena) {
        //Metodo que concatena un arbol en preorden

        if(nodo != null) {
            //Armo una cadena con todos los datos del nodo
            cadena += nodo.getElem() + "\t" +
                    "HI:" + ((nodo.getIzquierdo() != null) ? nodo.getIzquierdo().getElem() : "-" ) + "\t" +
                    "HD:" + ((nodo.getDerecho() != null ) ? nodo.getDerecho().getElem() : "-" ) + "\n";

            //Llamado recursivo
            cadena = toStringRecursivo(nodo.getIzquierdo(), cadena);
            cadena = toStringRecursivo(nodo.getDerecho(), cadena);
        }

        return cadena;
    }


}
