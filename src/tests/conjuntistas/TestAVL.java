package tests.conjuntistas;

import Estructuras.ArbolAVL;

public class TestAVL {

    public static void main(String []args){
        ArbolAVL a = new ArbolAVL(), b = new ArbolAVL();

        //Muestra los arboles y checkean si estan vacios
        System.out.println("Muestra el arbol a " + a.toString() + "\n El arbol esta vacio?: "+a.esVacio());
        System.out.println("Muestra el arbol b " + b.toString() + "\n El arbol esta vacio?: "+b.esVacio());

        //Trata de eliminar el maximo y el minimo de un arbol vacio
        System.out.println("Elimino minimo del arbol a, tiene que dar false: " + a.eliminarMinimo());
        System.out.println("Elimino maximo del arbol a, tiene que dar false: " + a.eliminarMaximo());

        //Trata de listar por rango al arbol vacio
        System.out.println("Lista del arbol a con min:3 y max:11" + "\n" + a.listarRango(3,11));

        //Llena los arboles
        llenarCaso0(a);
        llenarCaso1(b);

        //Muestra los arboles
        System.out.println("Muestra el arbol a " + a.toString());
        System.out.println("Muestra el arbol b " + b.toString());

        //Lista por rango
        System.out.println("Lista del arbol a con min:3 y max:11" + a.listarRango(3,11));
        System.out.println("Lista del arbol b con min:4 y max:10 " + b.listarRango(4,10));

        //Lista por rango que no se encuentra en el arbol
        System.out.println("Lista del arbol a con min:23 y max:50" + a.listarRango(23,50));
        System.out.println("Lista del arbol b con min:3 y max:11" + b.listarRango(24,50));

        //Elimina el minimo y el maximo mostrando entremedio los arboles
        System.out.println("Elimino el maximo del arbol que es " +a.maximoElem().toString() + ": " +a.eliminarMaximo());
        System.out.println(a.toString());
        System.out.println("Elimino el minimo del arbol que es " + a.minimoElem().toString() + ": " + a.eliminarMinimo());
        System.out.println(a.toString());

        //Elimino algunos elementos para causar un desbalance
        System.out.println("Elimino a 8 " + a.eliminar(8) + a.toString());
        System.out.println("Elimino a 2 " + a.eliminar(2) + a.toString());
        System.out.println("Elimino a 4 " + a.eliminar(4) + a.toString());


        //Vacio los arboles eliminado el menor y el mayor
        vaciarMayor(a);
        vaciarMenor(b);

        //Muestro arboles vacios
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("El arbol b es: " + b.toString());
    }

    private static void llenarCaso0(ArbolAVL a) {
        //Metodo que llena al arbol con numeros y testea si se estan insertando
       /* System.out.println("Se inserta 8" + a.insertar(8) + a.toString());
        System.out.println("Se inserta 4" + a.insertar(4) + a.toString());
        System.out.println("Se inserta 10" + a.insertar(10) + a.toString());
        System.out.println("Se inserta 2" + a.insertar(2) + a.toString());
        System.out.println("Se inserta 6" + a.insertar(6) + a.toString());
        System.out.println("Se inserta 7" + a.insertar(7) + a.toString());*/
        System.out.println("Se inserta 10 tiene que dar true: " + a.insertar(1,10));
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("Se inserta 6 tiene que dar true: " + a.insertar(1,6));
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("Se inserta 12 tiene que dar true: " + a.insertar(1,12));
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("Se inserta 10 tiene que dar false: " + a.insertar(1,10));
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("Se inserta 20 tiene que dar true: " + a.insertar(1,20));
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("Se inserta 3 tiene que dar true: " + a.insertar(1,3));
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("Se inserta 7 tiene que dar true: " + a.insertar(1,7));
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("Se inserta 9 tiene que dar true: " + a.insertar(1,9));
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("Se inserta 4 tiene que dar true: " + a.insertar(1,4));
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("Se inserta 1 tiene que dar true: " + a.insertar(1,1));
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("Se inserta 2 tiene que dar true: " + a.insertar(1,2));
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("Se inserta 22 tiene que dar true: " + a.insertar(1,22));
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("Se inserta 22 tiene que dar false: " + a.insertar(1,22));
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("Se inserta 3 tiene que dar false: " + a.insertar(1,3));
        System.out.println("El arbol a es: " + a.toString());
    }

    private static void llenarCaso1(ArbolAVL a) {
        //Metodo que llena al arbol con numeros y testea si se estan insertando

        System.out.println("Se inserta 5 tiene que dar true: " + a.insertar(1,5) + a.toString());
        System.out.println("Se inserta 4 tiene que dar true: " + a.insertar(1,4) + a.toString());
        System.out.println("Se inserta 3 tiene que dar true: " + a.insertar(1,3) + a.toString());
        System.out.println("Se inserta 2 tiene que dar true: " + a.insertar(1,2) + a.toString());
        System.out.println("Se inserta 1 tiene que dar true: " + a.insertar(1,1) + a.toString());
        System.out.println("Se inserta 10 tiene que dar true: " + a.insertar(1,10));
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("Se inserta 2 tiene que dar true: " + a.insertar(1,2));
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("Se inserta 3 tiene que dar true: " + a.insertar(1,3));
        System.out.println("El arbol a es: " + a.toString());
        System.out.println("Se inserta 4 tiene que dar true: " + a.insertar(1,4));
        System.out.println("Se inserta 5 tiene que dar true: " + a.insertar(1,5));
        System.out.println("Se inserta 3 tiene que dar false: " +a.insertar(1,3));
        System.out.println("Se inserta 7 tiene que dar true: " + a.insertar(1,7));
        System.out.println("Se inserta 9 tiene que dar true: " + a.insertar(1,9));
        System.out.println("Se inserta 4 tiene que dar false: " + a.insertar(1,4));
        System.out.println("Se inserta 15 tiene que dar true: " + a.insertar(1,15));
        System.out.println("Se inserta 12 tiene que dar true: " + a.insertar(1,12));
        System.out.println("Se inserta 20 tiene que dar true: " + a.insertar(1,20));
        System.out.println("Se inserta 11 tiene que dar true: " + a.insertar(1,11));
        System.out.println("Se inserta 13 tiene que dar true: " + a.insertar(1,13));
    }

    private static void vaciarMenor(ArbolAVL a){
        //Metodo que vacia y muestra el arbol

        System.out.println("Elimino al menor hasta vaciar el arbol");

        while(!a.esVacio()){
            System.out.println("El menor elemento del arbol es: " + a.minimoElem());
            System.out.println("Elimino al menor " + a.eliminarMinimo());
            System.out.println("El arbol a es: " + a.toString());
        }
    }

    private static void vaciarMayor(ArbolAVL a){
        //Metodo que vacia y muestra el arbol

        System.out.println("Elimino al mayor hasta vaciar el arbol");

        while(!a.esVacio()){
            System.out.println("El mayor elemento del arbol es: " + a.maximoElem());
            System.out.println("Elimino al mayor " + a.eliminarMaximo());
            System.out.println("El arbol a es: " + a.toString());
        }
    }
}
