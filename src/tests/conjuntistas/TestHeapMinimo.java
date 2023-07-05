package tests.conjuntistas;

import conjuntistas.Heap;

public class TestHeapMinimo {

    public static void llenoHeap(Heap a){
        //Metodo que llena un heap con enteros predeterminados
        System.out.println("Se inserta 20, espera true: " + a.insertar(20));
        System.out.println("Se inserta 3: " + a.insertar(3));
        System.out.println("Se inserta 6: " + a.insertar(6));
        System.out.println("Se inserta 4: "+a.insertar(4));
        System.out.println("Se inserta 2: "+a.insertar(2));
        System.out.println("Se inserta 5: "+a.insertar(5));
        System.out.println("Se inserta 3: "+a.insertar(3));
        System.out.println("Se inserta 1: "+a.insertar(1));
        System.out.println("Se inserta 15: "+a.insertar(15));
        System.out.println("Se inserta 19: "+a.insertar(19));
        System.out.println("Se inserta 30, espera false: "+a.insertar(30));
        System.out.println("Se inserta 8, espera false: "+a.insertar(8));
    }

    public static void main(String[] args){
        //Metodo que testea clase arbolHeap minimo
        Heap a = new Heap(), b;

        llenoHeap(a);
        System.out.println(a.esVacio());
        System.out.println("Muestro heap minimo: \n" + a.toString());
        System.out.println("Recupero cima tiene que ser 1: " + a.recuperarCima());
        System.out.println("Elemino cima" + a.eliminarCima() + "y recupero nueva cima tien que ser 2:" + a.recuperarCima());
        System.out.println("Clono heap");
        b = a.clone();
        System.out.println("Vacio heap");
        a.vaciar();
        System.out.println(a.esVacio());
        System.out.println("Muestro arbol vacio: " + a.toString());
        System.out.println("Muestro clon " + b.toString());
    }
}
