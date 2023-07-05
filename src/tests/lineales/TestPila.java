package tests.lineales;

import lineales.dinamicas.Pila;         //Importante para usar clases de otros paquetes
import java.util.Random;

public class TestPila {

    public static void muestraOpciones(){

        System.out.println("Seleccione una de las siguientes opciones:\n" +
                "0) LLenar la pila de enteros aleatorios (solo usar con estaticas)\n" +
                "1) Apilar un elemento\n" +
                "2) Desapilar un elemento\n" +
                "3) Obtener tope de la pila\n" +
                "4) Verificar si la pila esta vacia\n" +
                "5) Vaciar la pila\n" +
                "6) Clonar la pila\n" +
                "7) Mostrar pila\n" +
                "8) Mostrar clon pila\n" +
                "9) Capicua\n" +
                "10) Salir\n");
    }

    public static void llenarPila(Pila pila){
        //Llena la pila con numeros aleatorios
        int i=1;
        Random enteroAleatorio = new Random();

        while(i<=20) {
            pila.apilar(enteroAleatorio.nextInt(9));
            i++;
        }
    }

    public static boolean esCapicua(Pila pila){
        //Metodo que checkea si una pila es capicua

        Pila aux = pila.clone(), pilaReves = new Pila();
        boolean capicua = false;

        if(!pila.esVacia()){            //Si la pila esta vacia retorna false

            while(!aux.esVacia()){
                pilaReves.apilar(aux.obtenerTope());        //Obtengo una pila que esta al reves de la original
                aux.desapilar();
            }

            System.out.println(pilaReves.toString());
            aux = pila.clone();         //Clono denuevo a la pila original en aux

            capicua = checkCapicua(aux, pilaReves);        //Compara el tope de las dos pilas para ver si difieren o no
        }

        return capicua;
    }

    public static boolean checkCapicua(Pila aux, Pila pilaReves) {
        boolean coincidencia = true;

        while(!aux.esVacia() && coincidencia) {

            if(!aux.obtenerTope().equals(pilaReves.obtenerTope()) )
                coincidencia = false;

            else{
                aux.desapilar();
                pilaReves.desapilar();
            }
        }
        return coincidencia;
    }

    public static void menu(Pila pila) {
        //HUB con las diferentes opciones para que elija el usuario
        boolean iterar = true,flag = false;
        int opcion;
        Pila clonPila = new Pila();


        while(iterar) {
            muestraOpciones();
            opcion = TecladoIn.readLineInt();
            switch (opcion) {

                case 0:
                    llenarPila(pila);
                    break;

                case 1:
                    apilar(pila);
                    break;

                case 2:
                    desapilar(pila);
                    break;

                case 3:
                    System.out.println("El tope es: " + pila.obtenerTope());
                    break;

                case 4:
                    System.out.println("La pila es vacia: " + pila.esVacia());
                    break;

                case 5:
                    pila.vaciar();
                    break;

                case 6:
                    clonPila = pila.clone();
                    flag = true;
                    break;

                case 7:
                    System.out.println(pila.toString());
                    break;

                case 8:
                    if (flag)
                        System.out.println(clonPila.toString());
                    else
                        System.out.println("No hay clon de pila");
                    break;

                case 9:
                    System.out.println("La pila es capicua: "+ esCapicua(pila));
                    break;

                case 10:
                    iterar = false;
                    break;

                default:
                    System.out.println("Opcion incorrecta");
            }
            System.out.println();
        }
    }

    public static void desapilar(Pila pila) {
        boolean exito;
        char respuesta;
        boolean repetir = true;

        while(repetir) {
            exito = pila.desapilar();
            System.out.println("Se pudo desapilar: " + exito +"\nDesea deapilar otro elemento? [S]i -[N]o");
            respuesta = TecladoIn.readLineNonwhiteChar();

            if(!(respuesta == 's' || respuesta == 'S') || !exito)
                repetir = false;
        }
    }

    public static void apilar(Pila pila) {
        Object elemento;
        char respuesta;
        boolean repetir = true;

        while(repetir) {
            System.out.println("Ingrese elemento a apilar");
            elemento = TecladoIn.readLine();
            System.out.print("Se pudo apilar: " + pila.apilar(elemento)+"\nDesea apilar otro elemento? [S]i - [N]o\n");
            respuesta = TecladoIn.readLineNonwhiteChar();

            if(!(respuesta == 's' || respuesta == 'S') )
                repetir = false;
        }
    }

    public static void main (String[] args){
        //Algoritmo que testea la clase Pila

        Pila pilaTest = new Pila();

        menu(pilaTest);

    }
}
