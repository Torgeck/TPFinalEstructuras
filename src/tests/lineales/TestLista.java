package tests.lineales;

import lineales.dinamicas.Lista;

public class TestLista {

    private static void muestraOpciones() {
        //Muestra opciones al usuario

        System.out.println("Elija una de las siguientes opciones: \n" +
                "0) Llenar cadena con numeros del 1 hasta 10\n" +
                "1) Insertar un elemento\n" +
                "2) Eliminar un elemento\n" +
                "3) Recuperar la posicion de un elemento\n" +
                "4) Localizar un elemento\n" +
                "5) Obtener longitud de la lista\n" +
                "6) Verificar si la lista esta vacia\n" +
                "7) Vaciar lista\n" +
                "8) Clonar lista\n" +
                "9) Imprimir lista\n" +
                "10) Eliminar todas las apariciones de x elemento\n" +
                "11) Invertir lista\n" +
                "12) Salir\n");
    }

    private static void insertarElemento(Lista lista) {
        int pos;
        Object elemento;

        System.out.println("Ingrese elemento a insertar");
        elemento = TecladoIn.readInt();
        System.out.println("Ingrese posicion a insertar");
        pos = TecladoIn.readInt();

        System.out.println("Se pudo insertar elemento: " + lista.insertar(elemento,pos));

    }

    private static void eliminarElemento(Lista lista) {
        int pos;
        System.out.println("Ingrese posicion del elemento a eliminar");
        pos = TecladoIn.readInt();

        System.out.println("Se pudo eliminar elemento de la posicion " + pos + " : " + lista.eliminar(pos));
    }

    private static void recuperarElemento(Lista lista) {
        int pos;

        System.out.println("Ingrese la posicion");
        pos = TecladoIn.readInt();
        System.out.println("El elemento de la posision " + pos + " es: " + lista.recuperar(pos));

    }



    private static void localizarElemento(Lista lista) {
        Object elemento;

        System.out.println("Ingrese elemento a localizar");
        elemento = TecladoIn.readInt();
        System.out.println("El elemento " + elemento + " se encuentra en la posicion: " + lista.localizar(elemento));

    }



    private static void mostrarLista(Lista lista, Lista clon, boolean flag) {

        System.out.println(lista.toString());
        //Muestra clon si es que hay uno
        if(flag)
            System.out.println(clon.toString());

    }

    private static void llenarLista10(Lista lista) {
        int i=1;

        while (i <= 10){
            lista.insertar(i,i);
            i++;
        }
    }

    private static void eliminarAparicionesElemento(Lista lista) {
        int elemento;

        System.out.println("Ingrese elemento a eliminar");
        elemento = TecladoIn.readInt();
        lista.eliminarApariciones(elemento);
        System.out.println("Lista que quedo es: " + lista.toString());

    }

    private static void menu(Lista lista, Lista clon) {
        //HUB donde el usuario puede elejir opciones
        boolean iterar = true, flag = false;
        int opcion;

        while(iterar) {

            muestraOpciones();
            opcion = TecladoIn.readLineInt();

            switch (opcion) {
                case 0:
                    llenarLista10(lista);
                    break;

                case 1:
                    insertarElemento(lista);
                    break;

                case 2:
                    eliminarElemento(lista);
                    break;

                case 3:
                    recuperarElemento(lista);
                    break;

                case 4:
                    localizarElemento(lista);
                    break;

                case 5:
                    System.out.println("La longitud de la lista es: " + lista.longitud());
                    break;

                case 6:
                    System.out.println("La lista esta vacia: " + lista.esVacia());
                    break;

                case 7:
                    lista.vaciar();
                    break;

                case 8:
                    clon = lista.clone();
                    flag = true;
                    break;

                case 9:
                    mostrarLista(lista, clon, flag);
                    break;

                case 10:
                    eliminarAparicionesElemento(lista);
                    break;

                case 11:
                    System.out.println("La lista invertida es: " + lista.invertir().toString());
                    break;

                case 12:
                    iterar = false;
                    break;

                default:
                    System.out.println("Opcion no valida");

            }

        }
    }

    public static void main(String[] args){
        //Algoritmo que testea clase lista
        Lista lista = new Lista(), clon = new Lista();

        menu(lista,clon);

   }
}
