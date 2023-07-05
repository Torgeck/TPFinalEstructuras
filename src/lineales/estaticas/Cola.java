package lineales.estaticas;

public class Cola {

    private static final int TAMANIO = 10;
    private Object[] arreglo;
    private int frente;
    private int fin;

    public Cola() {
        this.arreglo = new Object[this.TAMANIO];
        this.frente = 0;
        this.fin = 0;
    }

    public boolean poner(Object elemento) {
        boolean exito = false;
        int finMasUno = (this.fin+1) % this.TAMANIO;

        //Verifica si esta llena
        if( finMasUno != this.frente) {
            this.arreglo[this.fin] = elemento;
            this.fin = (this.fin + 1) % this.TAMANIO;
            exito = true;
        }

        return exito;
    }

    public boolean sacar() {
        boolean exito = false;

        if(this.frente != this.fin) {
            saleElemento();
            exito = true;
        }

        return exito;
    }

    public Object obtenerFrente() {
        return this.arreglo[this.frente];
    }

    public boolean esVacia() {
        boolean vacio = false;

        if(this.frente == this.fin)
            vacio = true;

        return vacio;
    }

    public void vaciar() {

        while(this.fin != this.frente){
            saleElemento();
        }

        this.frente = 0;
        this.fin = 0;
    }

    private void saleElemento() {
        this.arreglo[this.frente] = null;
        this.frente = (this.frente + 1) % TAMANIO;
    }

    public Cola clone() {
        Cola clon = new Cola();
        int aux = this.frente, fin = this.fin;

        //Recorro la cola y voy copiado los elementos al clon
        while(aux != fin) {
            clon.arreglo[aux] = this.arreglo[aux];
            aux = (aux + 1) % TAMANIO;
        }

        //Luego copio el frente y fin para que coincidan
        clon.frente = this.frente;
        clon.fin = fin;

        return clon;
    }

    public String toString() {
        int aux = this.frente,fin = this.fin;
        String salida = "[";

        if(!this.esVacia()) {
            while (aux != fin) {
                salida += this.arreglo[aux];
                aux = (aux + 1) % TAMANIO;
                if(aux != fin)
                    salida += ",";
            }
        }

        return salida + "]";
    }
}
