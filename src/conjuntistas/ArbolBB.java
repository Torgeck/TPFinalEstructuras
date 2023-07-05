package conjuntistas;

import lineales.dinamicas.Lista;

public class ArbolBB {

    private NodoABB raiz;

    public ArbolBB() {
        this.raiz = null;
    }

    public boolean insertar(Comparable elemento){
        /*
        Inserta el elemento ingresado por parametro al arbol de manera ordenada retornando true.
        El metodo no inserta elementos repetidos en el arbol y en este caso retorna false.
         */
        boolean exito = true;

        //Checkeo si el arbol esta vacio
        if(this.raiz == null)
            this.raiz = new NodoABB(elemento);

        //Caso contrario checkeo si se encuentra en el arbol
        else
            exito = insertarAux(this.raiz, elemento);

        return exito;
    }

    private boolean insertarAux(NodoABB nodo, Comparable elemento) {
        // Precondicion nodo no es nulo
        boolean exito = true;

        //Si el elemento es encontrado en el arbol entonces retorna false
        if((elemento.compareTo(nodo.getElem())) == 0)
            exito = false;

        //Si el elemento es menor que el elemento del nodo actual
        else if (elemento.compareTo(nodo.getElem()) < 0){

            //Si el nodo actual tiene HI baja a la izq
            if(nodo.getIzquierdo() != null)
                exito = insertarAux(nodo.getIzquierdo(), elemento);

            //Caso contrario crea nuevo nodo y lo inserta como HI
            else
                nodo.setIzquierdo(new NodoABB(elemento));
        }
        //Si el elemnto es mayor que el elemento del nodo
        else {
            //Si tiene HD baja a la derecha
            if (nodo.getDerecho() != null)
                exito = insertarAux(nodo.getDerecho(), elemento);

            //Casp contrario Lo agrega como HD del nodo actual
            else
                nodo.setDerecho(new NodoABB(elemento));
        }

        return exito;
    }

    public boolean eliminar(Comparable elemento){
        /*
        Metodo que recibe un elemento por parametro y lo elimina del ABB retornando true
        Caso de no encontrarlo en el arbol retonra false
        */

        return eliminarAux(null,this.raiz,elemento);
    }

    private boolean eliminarAux(NodoABB padre, NodoABB nodo, Comparable elem) {
        //Metodo recursivo que busca el elemento en el arbol
        boolean exito = false;

        if(nodo != null){
            //Veo si el elemento es igual al elemento del nodo actual
            Comparable elemNodo = nodo.getElem();
            if(elemNodo.compareTo(elem) == 0) {
                eliminarNodo(padre, nodo);
                exito = true;
            }
            //Busco en el subarbol izq
            else if(elemNodo.compareTo(elem) < 0)
                eliminarAux(nodo,nodo.getIzquierdo(),elem);

            //Busco en el subarbol der
            else
                eliminarAux(nodo,nodo.getDerecho(),elem);
        }

        return exito;
    }

    private void eliminarNodo(NodoABB padre, NodoABB nodo) {
        //Metodo que analiza los casos y elimina el nodo deseado
        NodoABB hijoIzq = nodo.getIzquierdo(),hijoDer = nodo.getDerecho();

        //Si el nodo actual es hoja
        if(hijoIzq == null && hijoDer == null)
            eliminarHoja(padre, nodo);

        //Si el nodo actual tiene ambos hijos
        else if(hijoIzq != null && hijoDer != null)
            eliminarNodoConHijos(nodo);

        //Si el nodo actual tiene un solo hijo
        else
            eliminarNodoConUnHijo(padre,nodo);
    }

    //Caso 1
    private void eliminarHoja(NodoABB padre, NodoABB nodo) {
        //Metodo que elimina una hoja de un arbol

        //Caso especial donde se quiere eliminar la raiz
        if(padre == null)
            this.raiz = null;

        //Si el elemento a eliminar es el HI seteo al mismo como null
        else if (padre.getIzquierdo() == nodo)
            padre.setIzquierdo(null);

        //Caso contrario seteo el HD a null
        else
            padre.setDerecho(null);
    }

    //Caso 2
    private void eliminarNodoConUnHijo(NodoABB padre, NodoABB nodo) {
        //Metodo que elimina un nodo que tiene un solo hijo
        NodoABB hijoIzq = nodo.getIzquierdo(), hijoDer = nodo.getDerecho();

        //Caso especial donde se quiere eliminar la raiz
        if(padre == null)
            this.raiz = (hijoDer == null) ? hijoIzq : hijoDer;

        //Veo si nodo es HI del padre
        else if(padre.getIzquierdo() == nodo){

            //Si el HI del nodo no es nulo entonces seteo al mismo con su padre
            if(hijoIzq != null)
                padre.setIzquierdo(hijoIzq);

            //Caso contrario seteo a su HD
            else
                padre.setIzquierdo(hijoDer);
        }
        //Caso contrario el nodo es HD del padre
        else{

            //Si el HI del nodo no es nulo entonces seteo al mismo con su padre
            if(hijoIzq != null)
                padre.setDerecho(hijoIzq);

                //Caso contrario seteo a su HD
            else
                padre.setDerecho(hijoDer);
        }
    }

    //Caso 3
    private void eliminarNodoConHijos(NodoABB nodo) {
        //Metodo que elimina un nodo que tiene ambos hijos
        NodoABB nodoCandidato = nodo.getDerecho();

        //Encuentro el nodo candidato y su padre
        NodoABB padre = nodo;
        while(nodoCandidato.getIzquierdo() != null){
            padre = nodoCandidato;
            nodoCandidato = nodoCandidato.getIzquierdo();
        }

        //Ahora intercambio el valor del candidato con el valor del nodo a eliminar
        nodo.setElem(nodoCandidato.getElem());

        //Determino que caso usar para eliminar a nodo candidato del arbol
        eliminarNodo(padre,nodoCandidato);
    }

    public boolean eliminarMinimo(){
        //Metodo que elimina el minimo de un arbolBB haciendo uso de metodos anteriores
        boolean exito = false;
        //Checkeo que el arbol no este vacio
        if(this.raiz != null){
            NodoABB min = this.raiz,padre = null;

            //Busco al minimo y a su padre
            while(min.getIzquierdo() != null){
                padre = min;
                min = min.getIzquierdo();
            }

            //Una vez que los tengo los elimino segun sea el caso
            eliminarNodo(padre,min);
            exito = true;
        }

        return exito;
    }

    public boolean eliminarMaximo(){
        //Metodo que elimina el maximo de un arbolBB haciendo uso de metodos anteriores
        boolean exito = false;
        //Checkeo que el arbol no este vacio
        if(this.raiz != null){
            NodoABB max = this.raiz,padre = null;

            //Busco al minimo y a su padre
            while(max.getDerecho() != null){
                padre = max;
                max = max.getDerecho();
            }

            //Una vez que los tengo los elimino segun sea el caso
            eliminarNodo(padre,max);
            exito = true;
        }

        return exito;
    }

    public boolean pertenece(Comparable elemento){
        //Metodo que devuelve un boolean si encuentra el elemento ingresado por parametro en el arbolBB
        boolean exito = false;

        //Checkeo que el arbol no este vacio
        if(this.raiz != null)
            exito = perteneceAux(this.raiz, elemento) != null;

        return exito;
    }

    private NodoABB perteneceAux(NodoABB nodo, Comparable elemento) {
        NodoABB nodoEncontrado = null;

        //Si encontramos el nodo con igual elemento lo devolvemos
        if(nodo.getElem().compareTo(elemento) == 0)
            nodoEncontrado = nodo;

        //Si el elemento es menor que el elemento del nodo actual busco en HI
        else if(nodo.getElem().compareTo(elemento) < 0){

            if(nodo.getIzquierdo() != null)
                nodoEncontrado = perteneceAux(nodo.getIzquierdo(),elemento);
        }
        //Caso contrario busco en HD
        else{
            if(nodo.getDerecho() != null)
                nodoEncontrado = perteneceAux(nodo.getDerecho(), elemento);
        }

        return nodoEncontrado;
    }

    public boolean esVacio(){
        //Metodo que devuelve un boolean dependiendo si hay o no elementos dentro del arbol
        return this.raiz == null;
    }

    public void vaciar(){
        //Metodo que vacia el ABB con el garbage colector de java
        this.raiz = null;
    }

    public Lista listar(){
        //Metodo que lista en orden al arbol
        Lista lista = new Lista();

        //Checkeo si el arbol esta vacio
        if(this.raiz != null)
            listarAux(this.raiz,lista);

        return lista;
    }

    private void listarAux(NodoABB nodo,Lista lista) {
        //Metodo que lista al ABB en inorden

        if(nodo != null) {

            //Recorro el HI
            listarAux(nodo.getIzquierdo(),lista);

            lista.insertar(nodo.getElem(),lista.longitud()+1);

            //Recorro el HD
            listarAux(nodo.getDerecho(),lista);

        }

    }

    public Lista listarRango(Comparable elemMinimo, Comparable elemMaximo){
        /*
        Recorre parte del arbol y devuelve una lista ordenada con los elementos que
        se encuentran en el intervalo de los parametros ingresados
        */
        Lista lista = new Lista();

        //Checkeo si el arbol esta vacio
        if(this.raiz != null)
            //Checkeo que los parametros esten ingresados bien
            if(elemMinimo.compareTo(elemMaximo) < 0)
                listarRangoAux(elemMinimo,elemMaximo,lista, this.raiz);

        return lista;
    }

    private void listarRangoAux(Comparable min, Comparable max, Lista lista, NodoABB nodo) {
        //Metodo recursivo que devuelve una lista ordenada entre min y max

        //Checkeo que le nodo no sea null
        if(nodo != null) {
            Comparable elem = nodo.getElem();

            //Si el elemento actual es mayor que el minimo bajo a la izquierda
            if(elem.compareTo(min) > 0)
                listarRangoAux(min,max,lista,nodo.getIzquierdo());

            //Si el elemento actual se encuentra entremedio de los valores pedidos lo agrego a la lista
            if(elem.compareTo(min) >= 0 && elem.compareTo(max) <= 0)
                lista.insertar(elem,lista.longitud()+1);

            //Si el elemento actual es menor que el maximo entonces bajo por derecha
            if(elem.compareTo(max) < 0)
                listarRangoAux(min,max,lista,nodo.getDerecho());

        }

    }

    public Comparable minimoElem(){
        //Metodo que devuelve el elemento minimo del arbolBB
        return minimoAux(this.raiz, null);
    }

    private Comparable minimoAux(NodoABB nodo,Comparable elem) {
        //Metodo recursivo que retorna el elemento minimo de un ABB

        if(nodo != null){
            //Si no tiene HI entonces es la hoja por lo tanto el minimo elemento
            if(nodo.getIzquierdo() == null)
                elem = nodo.getElem();

            //Si no avanza a su HI
            else
                elem = minimoAux(nodo.getIzquierdo(),elem);
        }

        return elem;
    }

    public Comparable maximoElem(){
        //Metodo que devuelve el elemento mazimo del arbolBB
        return maximoAux(this.raiz,null);
    }

    private Comparable maximoAux(NodoABB nodo, Comparable elem) {
        //Metodo recursivo que retorna el elemento maximo de ABB

        if(nodo != null){
            //Si no tiene HD entonces es la hoja por lo tanto es el maximo elemento
            if(nodo.getDerecho() == null)
                elem = nodo.getElem();

            //Si no avanza a su HD
            else
                elem = maximoAux(nodo.getDerecho(), elem);
        }

        return elem;
    }

    public String toString() {
        //Metodo que retorna un string de la clase arbol
        String arbol;

        arbol = toStringRecursivo(this.raiz,"\n");

        return arbol;
    }

    private String toStringRecursivo(NodoABB nodo, String cadena) {
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

    public ArbolBB clone() {
        //Metodo que clona un arbol y retorna el clon del mismo
        ArbolBB clon = new ArbolBB();

        if(this.raiz != null) {
            //Crea un nuevo nodo y se lo asigna a la raiz clon
            clon.raiz = new NodoABB(this.raiz.getElem());

            //Llamada a un metodo privado
            clonRecursivo(clon.raiz, this.raiz);
        }

        return clon;
    }

    private void clonRecursivo(NodoABB nodoClon, NodoABB nodoOG) {

        if(nodoOG != null) {

            //Setea HI si es que tiene
            if(nodoOG.getIzquierdo() != null) {
                nodoClon.setIzquierdo(new NodoABB(nodoOG.getIzquierdo().getElem()));
                //Recorre y setea los HI
                clonRecursivo(nodoClon.getIzquierdo(),nodoOG.getIzquierdo());
            }

            //Setea HD si es que tiene
            if(nodoOG.getDerecho() != null) {
                nodoClon.setDerecho(new NodoABB((nodoOG.getDerecho().getElem())));
                //Recorre y setea los HD
                clonRecursivo(nodoClon.getDerecho(),nodoOG.getDerecho());
            }
        }
    }



}
