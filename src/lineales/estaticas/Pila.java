
package lineales.estaticas;

public class Pila {
    
    private Object [] arreglo;
    private int tope;
    private static final int TAMANIO = 10;
    
    public Pila(){
        this.arreglo = new Object[TAMANIO];
        this.tope = -1;
    }
    
    public boolean apilar(Object nuevoElemento){
        
        boolean exito = false;
        
        if(this.tope+1 < TAMANIO){
            //Pone el elemento en el lugar arriba del tope
            this.tope++;
            this.arreglo[tope] = nuevoElemento;
            exito = true;
        }
        
        return exito;
    }
    
    public boolean desapilar(){
        
        boolean exito = false;
        
        if(this.tope >= 0){
            this.arreglo[tope] = null;
            this.tope--;
            exito = true;
        }
        
        return exito;
    }
    
    public Object obtenerTope() {
        Object salida = null; 
        
        if(this.tope >= 0)
            salida = this.arreglo[this.tope];
        
        return salida;
    }
    
    public boolean esVacia(){
        boolean vacio = false;
        
        if (this.tope < 0)
            vacio = true;
        
        return vacio;
    }
    
    public void vaciar(){
        
        while(tope >= 0){
            this.arreglo[tope] = null;
            tope--;
        }
    }
    
    public Pila clone(){
        
        Pila clon = new Pila();
        
        clon.arreglo = this.arreglo.clone();
        clon.tope = this.tope;
        
        return clon;
    }
    
    public String toString(){
        
        String salida = "";
        int i = tope;
        
        while(i >= 0){
            if(i>0)
                salida = "," +this.arreglo[i] + salida;         //Muestra del primer elemento hasta el tope como pide el testing pila
            else
                salida =  this.arreglo[i] + salida;
            i--;
        }
        
        return salida;
    }
    
    
}
