package conjuntistas;

public class Heap {

    private static final int TAMANIO = 20;
    private Comparable [] heap;
    private int ultimo;

    public Heap() {
        this.heap = new Comparable[TAMANIO];
        this.ultimo = 0;
    }

    public boolean insertar(Comparable elemento) {
        //Metodo que recibe un elemento por parametro y lo inserta en el arbol (Segun sea heapMax o heapMin)
        boolean exito = false;

        //Verifico que el heap no este lleno
        if(ultimo <= TAMANIO){

            //Si es el primer elemento que se inserta en el heap
            if(ultimo == 0){
                this.heap[++ultimo] = elemento;
            }
            else {
                //Inserto nuevo elemento como hoja
                this.heap[++ultimo] = elemento;

                //Ordeno heap
                hacerSubir(ultimo);

            }
            exito = true;
        }

        return exito;
    }

    private void hacerSubir(int posHijo) {
        //Metodo oara ordenar a arbolHeap minimo para elementos tipo int
        int posPadre;
        Comparable temp = this.heap[posHijo];
        boolean salir = false;

        while(!salir){
            posPadre = posHijo / 2;

            //Si tiene padre
            if(posPadre > 0){

                //Si el padre es mayor que el hijo los intercambio
                if(this.heap[posPadre].compareTo(temp) > 0){
                    this.heap[posHijo] = this.heap[posPadre];
                    this.heap[posPadre] = temp;
                    posHijo = posPadre;
                }
                //Si el padre es menor entonces esta bien ubicado
                else{
                    salir = true;
                }
            }
            //El temp se encuentra en la cima
            else{
                salir = true;
            }
        }
    }

    public boolean eliminarCima() {
        //Metodo que elimina el elemento de la raiz y ordena el arbol
        boolean exito = false;

        if(this.ultimo != 0){

            //Saca la raiz y pone la ultima hoja en su lugar
            this.heap[1] = this.heap[ultimo];
            this.heap[ultimo] = null;
            this.ultimo--;

            //Restablece la propiedad de heap minimo
            hacerBajar(1);
            exito = true;
        }

        return exito;
    }

    private void hacerBajar(int posPadre){
        //Metodo para ordenar a arbolHeap minimo para elementos tipo int
        int posH;
        Comparable temp = this.heap[posPadre];
        boolean salir = false;

        while(!salir){
            posH = posPadre * 2;

            //temp tiene al menos un hijo (izq) y lo considera menor
            if(posH <= this.ultimo) {
                
                //hijoMeno tiene hermano derecho
                if(posH < this.ultimo) {
                    
                    if(this.heap[posH + 1].compareTo(this.heap[posH]) < 0)
                        posH++;

                }

                //Compara al hijo menor con el padre
                if(this.heap[posH].compareTo(temp) < 0){
                    //El hijo es menor que el padre, los intercambia
                    this.heap[posPadre] = this.heap[posH];
                    this.heap[posH] = temp;
                    posPadre = posH;
                }
                else
                    salir = true;
            }
            //El temp es hoja, esta bien ubicado
            else
                salir = true;
        }


    }

    public Comparable recuperarCima() {
        //Metodo que retorna el elemento que se encuentra en la cima del arbol
        return this.heap[1];
    }

    public boolean esVacio() {
        //Metodo que retorna false si hay al menos un elemento cargado en el arreglo y true en caso contrario
        return this.ultimo == 0;
    }

    public void vaciar() {
        //Metodo que vacia un arbolHeap

        while(this.ultimo != 0){
            this.heap[ultimo] = null;
            ultimo--;
        }
    }

    public Heap clone(){
        //Metodo que hace un clon del arbolHeap utilizando el clone de java para arreglos
        Heap clon = new Heap();

        clon.heap = this.heap.clone();
        clon.ultimo = this.ultimo;

        return clon;
    }

    public String toString(){
        //Metodo que retorna un string con el contenido del arbol heap
        String cadena = "";

        if(!esVacio()){
            cadena = toStringRecursivo(1, cadena);
        }

        return cadena;
    }

    private String toStringRecursivo(int puntero, String cadena) {

        if(puntero <= this.ultimo){
            int aux = puntero * 2;

            //Armo la cadena
            cadena += this.heap[puntero] + "\t" +
                    "HI: " + ((this.heap[aux] != null) ? this.heap[aux] : "-") + "\t" +
                    "HD: " + ((this.heap[aux+1] != null) ? this.heap[aux+1] : "-") + "\n";

            //Hago el llamado recursivo
            cadena = toStringRecursivo(aux,cadena);
            cadena = toStringRecursivo(aux+1, cadena);
        }

        return cadena;
    }
}
