package tests.jerarquicas;

import jerarquicas.ArbolBin;
import tests.lineales.TecladoIn;

public class TestArbolBin {

    public static void muestraOpciones() {
        //Muestra las opciones en un menu
        System.out.println("Elija una de las siguientes opciones:\n" +
                "1) Insertar un elemento en el arbol\n" +
                "2) Verificar si el arbol esta vacio\n" +
                "3) Obtener la altura del arbol\n" +
                "4) Obtener el nivel de un elemento del arbol\n" +
                "5) Obtener el nodo padre de un elemento\n" +
                "6) Listar el arbol en todos los metodos\n" +
                "7) Clonar el arbol\n" +
                "8) Vaciar el arbol\n" +
                "9) Salir\n");
    }

    public static void menu(ArbolBin arbol, ArbolBin clon) {
        //HUB para que el usuario elija opciones para testear la clase ArbolBin
        boolean iterar = true, flag = false;
        int opcion;

        while(iterar) {

            muestraOpciones();
            opcion = TecladoIn.readLineInt();

            switch (opcion) {
                case 1:
                    ponerElemento(arbol);
                    break;

                case 2:
                    System.out.println("El arbol esta vacio: " + arbol.esVacio());
                    break;

                case 3:
                    System.out.println("La altura del arbol es "+arbol.altura());
                    break;

                case 4:
                    obtenerNivel(arbol);
                    break;

                case 5:
                    obtenerPadre(arbol);
                    break;

                case 6:
                    listarArbol(arbol,clon,flag);
                    break;

                case 7:
                    clon = arbol.clone();
                    flag = true;
                    break;

                case 8:
                    arbol.vaciar();
                    break;

                case 9:
                    iterar = false;
                    break;

                default:
                    System.out.println("Opcion no valida");

            }

        }

    }

    public static void listarArbol(ArbolBin arbol, ArbolBin clon, boolean flag) {

        if(arbol.esVacio())
            System.out.println("Arbol esta vacio");
        else
            System.out.println("Arbol:" + arbol.toString() + "\n" +
                    "Arbol en preorden: " + arbol.listarPreorden().toString() + "\n" +
                    "Arbol en inorden:" + arbol.listarInorden().toString() + "\n" +
                    "Arbol en posorden:" + arbol.listarPosorden().toString() + "\n" +
                    "Arbol en niveles:" + arbol.listarNiveles().toString() + "\n");

        if(flag) {
            System.out.println("Clon:" + clon.toString() + "\n" +
                    "Clon en preorden: " + clon.listarPreorden().toString() + "\n" +
                    "Clon en inorden:" + clon.listarInorden().toString() + "\n" +
                    "Clon en posorden:" + clon.listarPosorden().toString() + "\n" +
                    "Clon en niveles:" + clon.listarNiveles().toString() + "\n");
        }
    }

    public static void obtenerPadre(ArbolBin arbol) {
        Object elemento;

        System.out.println("Ingrese elemento hijo");
        elemento = TecladoIn.readLine();
        System.out.println("El elemento padre es: " + arbol.padre(elemento));
    }

    public static void obtenerNivel(ArbolBin arbol) {
        Object elemento;

        System.out.println("Ingresar elemento a obtener nivel");
        elemento = TecladoIn.readLine();
        System.out.println("El nivel del elemento es: " + arbol.nivel(elemento));
    }

    public static void ponerElemento(ArbolBin arbol) {
        Object elemento,elementoPadre = null;
        char posicion = 'I';

        System.out.println("Ingrese el elemento a insertar");
        elemento = TecladoIn.readLine();

        //Excepcion para que el usuario no ingrese parametros de mas
        if(!arbol.esVacio()){
            System.out.println("Ingrese el elemento padre");
            elementoPadre = TecladoIn.readLine();
            System.out.println("Ingrese la posicion [D]erecha - [I]zquierda");
            posicion = Character.toUpperCase(TecladoIn.readLineNonwhiteChar());
        }

        System.out.println("Se pudo insertar le elemento en el arbol: " + arbol.insertar(elemento,elementoPadre,posicion));
    }

    public static void main(String[] args) {
        //Algoritmo que testea la clase cola
        ArbolBin arbol = new ArbolBin(), clon = new ArbolBin();

        menu(arbol, clon);

    }



}
