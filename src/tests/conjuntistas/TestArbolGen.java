package tests.conjuntistas;

import estructuras.arbolGenerico.ArbolGen;
import estructuras.lineales.Lista;

public class TestArbolGen {

    public static void main(String[] args) {
        ArbolGen a = new ArbolGen(), b = new ArbolGen(), clon;
        Lista patron1 = new Lista(), patron2 = new Lista(), patron3 = new Lista();
        patron1.insertarFin("A");
        patron1.insertarFin("B");
        patron1.insertarFin("E");

        patron2.insertarFin("A");
        patron2.insertarFin("C");
        patron2.insertarFin("F");
        patron2.insertarFin("J");
        patron2.insertarFin("K");
        patron2.insertarFin("M");

        patron3.insertarFin("A");
        patron3.insertarFin("C");
        patron3.insertarFin("X");
        patron3.insertarFin("Y");
        // Se prueban las operaciones con el arbol vacio
        pruebaTotal(a, b, patron1, patron2, patron3);

        // Lleno el arbol con un patron dado
        llenarArbolA(a);

        // Se prueban las operaciones con arbol lleno
        pruebaTotal(a, b, patron1, patron2, patron3);

        a.vaciar();
        // Lleno el arbol con posiciones
        llenarArbolAPos(a);
        pruebaTotal(a, b, patron1, patron2, patron3);

        // Vacio los arboles
        a.vaciar();
        b.vaciar();

        llenarArbolB(b);

        pruebaTotal(b, a, patron1, patron2, patron3);

    }

    private static void pruebaTotal(ArbolGen a, ArbolGen b, Lista p1, Lista p2, Lista p3) {
        ArbolGen clon;
        System.out.println("Muestro el arbol A: " + a.toString() + "\nEs vacio?: " + a.esVacio());
        System.out.println("Muestro el arbol b: " + b.toString() + "\nEs vacio?: " + b.esVacio());
        System.out.println("Trato de insertar un elemento nuevo con un padre inexistente en el arbol [X con padre U]: "
                + a.insertar("X", "U"));
        System.out.println("Muestro el arbol A: " + a.toString() + "\nEs vacio?: " + a.esVacio());
        System.out.println("Muestro ancestros de J en a: " + a.ancestros("J"));
        System.out.println("Pertenece I a A?: " + a.pertenece("I"));
        System.out.println("La altura del arbol es: " + a.altura());
        System.out.println("El nivel del elemento C es: " + a.nivel("C"));
        System.out.println("El padre del elemento J es: " + a.padre("J"));
        System.out.println("Listo a en preorden " + a.listarPreorden());
        System.out.println("Listo a en inorden " + a.listarInorden());
        System.out.println("Listo a en posorden " + a.listarPosorden());
        System.out.println("Listo niveles " + a.listarNiveles());
        // Metodos adicionales
        System.out.println("La frontera del arbol es: " + a.frontera());
        System.out.println("Verifica si existe el patron " + p1.toString() + ": " + a.verificarPatron(p1));
        System.out.println("Verifica si existe el patron " + p2.toString() + ": " + a.verificarPatron(p2));
        System.out.println("Verifica si existe el patron " + p3.toString() + ": " + a.verificarPatron(p3));
        System.out.println("Lista que justifica altura: " + a.listaQueJustificaLaAltura());
        // Clono y luego vacio el arbol
        clon = a.clone();
        System.out.println("Clono y muestro los dos arboles " + a.toString() + "\n| " + clon.toString());
        a.vaciar();
        System.out.println("Vacio el arbol y muestro original y clon: " + a.toString() + "\n|" + clon.toString());
    }

    public static void llenarArbolA(ArbolGen a) {
        a.insertar("A", "x");
        a.insertar("B", "A");
        a.insertar("C", "A");
        a.insertar("D", "A");
        a.insertar("E", "B");
        a.insertar("F", "B");
        a.insertar("G", "D");
        a.insertar("H", "D");
        a.insertar("I", "H");
        a.insertar("J", "H");
    }

    public static void llenarArbolAPos(ArbolGen a) {
        a.insertarPosPadre("A", 0);
        a.insertarPosPadre("B", 1);
        a.insertarPosPadre("C", 1);
        a.insertarPosPadre("D", 1);
        a.insertarPosPadre("E", 2);
        a.insertarPosPadre("F", 2);
        a.insertarPosPadre("G", 6);
        a.insertarPosPadre("C", 1);
        a.insertarPosPadre("X", 8);
        a.insertarPosPadre("X", 5);
        a.insertarPosPadre("Y", 10);
        a.insertarPosPadre("H", 7);
        a.insertarPosPadre("I", 9);
        a.insertarPosPadre("J", 9);
    }

    public static void llenarArbolB(ArbolGen b) {
        b.insertar("A", "x");
        b.insertar("B", "A");
        b.insertar("C", "A");
        b.insertar("D", "B");
        b.insertar("E", "C");
        b.insertar("F", "C");
        b.insertar("G", "E");
        b.insertar("H", "G");
        b.insertar("I", "H");
        b.insertar("J", "F");
        b.insertar("K", "J");
        b.insertar("M", "K");
        b.insertar("N", "M");
        b.insertar("O", "A");
        b.insertar("P", "O");
        b.insertar("Q", "P");
        b.insertar("R", "P");
        b.insertar("S", "P");
        b.insertar("T", "S");
    }
}
