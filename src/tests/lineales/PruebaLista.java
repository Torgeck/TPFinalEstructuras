package tests.lineales;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Pila;

public class PruebaLista {

    public static Lista concatenar(Lista lista1, Lista lista2) {
        Lista listaConcatenada;

        listaConcatenada = lista1.clone();
        recorreYLlena(listaConcatenada,lista2);

        return listaConcatenada;
    }

    public static void recorreYLlena(Lista listaALlenar, Lista lista) {
        //Recorre y llena una lista con otra lista
        int tope = lista.longitud(), fin = listaALlenar.longitud() + 1, i = 1;

        while(i <= tope) {
            listaALlenar.insertar(lista.recuperar(i),fin);
            fin++;
            i++;
        }
    }

    public static boolean comprobar (Lista lista) {
        //Comprueba si los elementos de la lista divididos por ceros estan invertidos
        boolean exito = false;
        int i = 1, tope = lista.localizar(0);

        //Si la lista no esta vacia y contiene almenos 1 cero entra
        if (!lista.esVacia() && tope != -1) {
            exito = true;
            Cola cola = new Cola();
            Pila pila = new Pila();

            //Recorro la lista hasta el primer 0
            while(i < tope) {
                //Almaceno primera parte de la lista en una cola
                cola.poner(lista.recuperar(i));
                i++;
                if( (int) lista.recuperar(i) == 0)
                    i++;
            }

            //Recorro la segunda porcion de la lista hasta que la cola se vacie
            while( exito && !cola.esVacia() ) {
                pila.apilar(lista.recuperar(i));

                //Comparo para saber si son iguales las cadenas
                if(pila.obtenerTope() != cola.obtenerFrente())
                    exito = false;

                else{
                    cola.sacar();
                    i++;

                    if((int)lista.recuperar(i) == 0)
                        i++;
                }
            }

            //Recorro la ultima porcion de la lista hasta el final
            while(exito && !pila.esVacia()) {

                if(pila.obtenerTope() == lista.recuperar(i)) {
                    pila.desapilar();
                    i++;
                }

                else
                    exito = false;
            }

        }

        return exito;
    }

    public static Lista invertir(Lista lista) {
        //Toma una lista y va insertando elementos en la primera posicion para obtener la lista invertida
        Lista invertida = new Lista();
        int i = 1, tope = lista.longitud();

        while( i <= tope) {
            invertida.insertar(lista.recuperar(i),1);
            i++;
        }

        return invertida;
    }

    private static void mostrarOpciones() {
        System.out.println("Elija una de las siguientes opciones:\n" +
                "1) Concatenar listas\n" +
                "2) Comprobar condiciones de ambas listas\n" +
                "3) Invertir ambas listas\n" +
                "4) Salir\n");
    }

    private static void llenarListas(Lista l1, Lista l2) {
        int i = 1, num = 1;


        while(i <= 10 ) {
            l1.insertar(i,i);
            i++;
        }

        i=1;

        //Inserta 3 digitos luego 0 dos veces
        while(i < 8){
            l2.insertar(num,i);
            num++;
            i++;

            if(i == 4 || i == 8) {
                l2.insertar(0, i);
                i++;
                num = 1;
            }
        }

        num = 3;
        while(i <= 11){
            l2.insertar(num,i);
            num--;
            i++;
        }
    }

    public static void main(String[] args) {
        //Testea esta clase
        boolean repetir = true;
        int opcion;
        Lista l1 = new Lista(), l2 = new Lista();

        llenarListas(l1,l2);

        while(repetir){
            mostrarOpciones();
            opcion = TecladoIn.readInt();
            switch (opcion) {
                case 1:
                    System.out.println(concatenar(l1,l2).toString() + "\n");
                    break;
                case 2:
                    System.out.println( "La lista 1 cumple condiciones: " + comprobar(l1) + "\n" +
                                        "La lista 2 cumple condiciones: " + comprobar(l2) + "\n");
                    break;
                case 3:
                    System.out.println( "Lista 1 invertida: " + invertir(l1).toString() + "\n" +
                                        "Lista 2 invertida: " + invertir(l2).toString() + "\n");
                    break;
                case 4:
                    repetir = false;
                    break;
                default:
                    System.out.println("Opcion incorrecta");

            }
        }
    }
}
