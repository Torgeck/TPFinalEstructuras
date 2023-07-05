package lineales.dinamicas;

public class Cola {

    private Nodo frente;
    private Nodo fin;

    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    public boolean poner(Object elemento) {
        Nodo nuevoNodo = new Nodo(elemento, null);

        //Caso especial cola vacia
        if (this.frente == null && this.fin == null) {
            this.frente = nuevoNodo;
        }
        else {
            //Enlazo ultimo nodo al nuevo Nodo
            this.fin.setEnlace(nuevoNodo);
        }
        //Seteo el nuevo fin de la cola
        this.fin = nuevoNodo;

        return true;
    }

    public boolean sacar() {
        boolean exito = false;

        //Si la cola no esta vacia procede
        if(this.frente != null){
            //Setea el frente al nodo enlazado del que sale
            this.frente = this.frente.getEnlace();
            exito = true;
            //En caso de que quede la cola vacia setea el fin
            if(this.frente == null)
                this.fin  = null;
        }

        return exito;
    }

    public Object obtenerFrente() {
        Object elemento = null;

        if(!esVacia())
            elemento = this.frente.getElemento();

        return elemento;
    }

    public boolean esVacia() {
        boolean vacio = false;

        if(this.frente == null && this.fin == null)
            vacio = true;

        return vacio;
    }

    public void vaciar(){
        //El garbage collector de java se lleva todos los nodos que no son apuntados
        this.frente = null;
        this.fin = null;
    }

    public Cola clone(){
        Cola clon = new Cola();
        Nodo aux1 = this.frente;

        //Creo y seteo el frente de Cola clon
        clon.frente = new Nodo(aux1.getElemento(),null);

        clonRecursivo(clon,aux1,clon.frente);

        return clon;
    }

    private void clonRecursivo(Cola clon, Nodo punteroOG, Nodo nodoClon) {

        //Condicion si llego al final de la cola
        if(punteroOG.getEnlace() == null) {
            //Pone el ultimo elemento y setea el fin
            clon.fin = nodoClon;
        }
        else {
            //Enlazo nodo del clon al nodo anterior
            nodoClon.setEnlace(new Nodo(punteroOG.getEnlace().getElemento(),null));
            //Llamo de nuevo al metodo recursivo aumentando las posiciones de los punteros
            clonRecursivo(clon,punteroOG.getEnlace(),nodoClon.getEnlace());
        }
    }

    public String toString (){
        String salida = "[";
        Nodo puntero = this.frente;

        if(!this.esVacia()){
            while(puntero != null){
                salida += puntero.getElemento();
                //Me muevo al siguiente nodo
                puntero = puntero.getEnlace();

                //Agrego comas entre elementos
                if(puntero != null)
                    salida += ",";
            }
        }
        return salida + "]";
    }
}
