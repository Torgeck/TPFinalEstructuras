package tests.lineales;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Pila;

public class mixLineales {

    private static boolean checkeaMayus(Cola c1) {
        //Checkea si hay mayusculas y $ en la cola
        boolean todasMayus = true;
        char elemento;
        Cola clon = c1.clone();

        while(todasMayus && !clon.esVacia()){
            elemento = (char) clon.obtenerFrente();

            if(Character.isUpperCase(elemento) || elemento == '$')
                clon.sacar();
            else
                todasMayus = false;
        }
        return todasMayus;
    }

    public static Cola generarOtraCola(Cola c1) {
        Cola colaRara = new Cola();
        Pila porcionCola;
        boolean Mayus;


        //Checkea si hay solo hay mayusculas dentro de la cola
        Mayus = checkeaMayus(c1);

        if(Mayus) {
            while (!c1.esVacia()) {
                //Obtenemos un trozo de cola y vamos llenando colaRara
                porcionCola = RVLCola(c1,colaRara);

                //Llena colaRara con la pila inversa
                invierteYLlena(c1,colaRara,porcionCola);
            }
        }
        else
            System.out.println("La cola contiene caracteres que no estan en mayusculas");

        return colaRara;
    }

    private static Pila RVLCola(Cola c1,Cola colaRara) {
        //Recorre, vacia cola hasta $ y llena pila y colarara con caracteres de la cola original, devuelve pila
        Pila pila = new Pila();
        boolean sigue = true;
        char letra;

        while(!c1.esVacia() && sigue) {
            letra = (char) c1.obtenerFrente();

            //Corta un trozo de la cola hasta que encuentra el $ y lo saca
            if(letra == '$') {
                sigue = false;
            }

            else {
                //Apila la pila con caracteres de la cola y a esta la va vaciando, a su vez llena la colarara en orden
                pila.apilar(letra);
                colaRara.poner(letra);
            }
            c1.sacar();
        }

        return pila;
    }

    private static void invierteYLlena(Cola c1, Cola colaRara, Pila pila) {
        //Llena la colaRara con la pila y le agrega un signo peso al final dependiendo de la cola original

        //Desapila y llena colaRara
        while(!pila.esVacia()){
            colaRara.poner(pila.obtenerTope());
            pila.desapilar();
        }
        //Si la original tiene elemento agrega el signo $
        if(!c1.esVacia())
            colaRara.poner('$');
    }

    private static void llenaLote(Cola c1, Cola c2, Cola c3) {
        //Llena cola 1
        c1.poner('A');
        c1.poner('B');
        c1.poner('$');
        c1.poner('C');
        c1.poner('$');
        c1.poner('D');
        c1.poner('E');
        c1.poner('F');
        //Llena cola 2
        c2.poner('$');
        c2.poner('a');
        c2.poner('C');
        c2.poner('S');
        //LLena cola 3
        c3.poner('$');
        c3.poner('A');
        c3.poner('B');
        c3.poner('$');
        c3.poner('$');
        c3.poner('A');
        c3.poner('B');
        c3.poner('C');
        c3.poner('$');
    }

    private static void muestraLote(Cola c1, Cola c2, Cola c3) {
        System.out.println("Cola 1:"+c1.toString()+"\n" +
                "Cola 2:" +c2.toString()+ "\n" +
                "Cola 3:" +c3.toString()+ "\n");
    }

    public static void main (String[] args) {
        //Testea esta clase con lotes de prueba
        Cola c1 = new Cola(), c2 = new Cola() , c3 = new Cola(),CI1,CI2,CI3;

        llenaLote(c1,c2,c3);
        muestraLote(c1,c2,c3);
        CI1 = generarOtraCola(c1);
        CI2 = generarOtraCola(c2);
        CI3 = generarOtraCola(c3);
        muestraLote(CI1,CI2,CI3);
    }
}
