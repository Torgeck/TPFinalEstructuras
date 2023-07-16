package lineales.dinamicas;

public class Pila {

    private Nodo tope;

    public Pila() {
        this.tope = null;
    }

    public boolean apilar (Object nuevoElemento) {

        this.tope = new Nodo(nuevoElemento, this.tope);

        return true;        //La pila nunca se llena entonces siempre devuelve true
    }

    public boolean desapilar() {
        boolean exito = false;

        if(this.tope != null){
            this.tope = this.tope.getEnlace();
            exito = true;
        }

        return exito;
    }

    public Object obtenerTope() {
        Object salida = null;

        if(this.tope!=null)
            salida = this.tope.getElemento();

        return salida;
    }

    public boolean esVacia() {
        boolean vacio = false;

        if(this.tope == null)
            vacio = true;

        return vacio;
    }

    public void vaciar() {
        this.tope = null;           //Garbage collector de java se encarga de eliminar los nodos sueltos
    }

    public Pila clone() {
        Pila clon = new Pila();

        clon.tope = cloneRecursivo(this.tope);

        return clon;
    }

    private Nodo cloneRecursivo(Nodo tope) {
        Nodo nodoClon,aux;

        if(tope.getEnlace() == null){               //Llegamos hasta el final de la pila y seteamos nuestro nuevo tope
            nodoClon = new Nodo (tope.getElemento(),null);
        }
        else {
            aux = cloneRecursivo(tope.getEnlace());      //Recorremos la pila y linkeamos nodos
            nodoClon = new Nodo (tope.getElemento(),aux);
        }
        //Retornamos tope de la pila
        return nodoClon;
    }

    public String toString() {
        StringBuilder salida = new StringBuilder();
        Nodo aux;

        if(this.tope != null){
            aux = this.tope;

            while (aux != null) {
                    salida.insert(0, aux.getElemento().toString());         //Muestra del primer elemento hasta el tope como pide el testing pila
                    aux = aux.getEnlace();
                if(aux != null)
                    salida.insert(0, ",");
            }

        }
        return salida.toString();
    }






}
