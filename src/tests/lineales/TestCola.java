package tests.lineales;

//import lineales.estaticas.Cola;
import lineales.dinamicas.Cola;

public class TestCola {

    private static void muestraOpciones() {
        //Imprime por pantalla opciones a elegir
        System.out.println("Elija una de las siguiente opciones: \n" +
                "1) Poner elemento en la cola\n" +
                "2) Sacar elemento\n" +
                "3) Obtener frente\n" +
                "4) Verificar si la cola esta vacia\n" +
                "5) Vaciar cola\n" +
                "6) Clonar cola\n" +
                "7) Mostrar cola\n" +
                "8) Salir\n");
    }

    private static void ponerElemento(Cola cola) {
        //Puedo hacer que se repita segun necesite el usuario
        Object elemento;

        System.out.println("Ingrese elemento");
        elemento = TecladoIn.readLine();
        System.out.println("Se pudo poner el elemento: " +cola.poner(elemento) +"\n");

    }

    private static void sacarElemento(Cola cola) {
        System.out.println("Se pudo sacar el elemtento: "+cola.sacar());
    }

    private static void mostrarCola(Cola cola, Cola clon, boolean flag) {

        System.out.println("Cola OG: " + cola.toString() +"\n");

        if(flag){
            System.out.println("Clon cola: "+ clon.toString() + "\n");
        }

    }

    private static void menu(Cola cola, Cola clon) {
        //HUB para que el usuario elija opciones para testear la clase cola
        boolean iterar = true, flag = false;
        int opcion;

        while(iterar) {

            muestraOpciones();
            opcion = TecladoIn.readLineInt();

            switch (opcion) {
                case 1:
                    ponerElemento(cola);
                    break;

                case 2:
                    sacarElemento(cola);
                    break;

                case 3:
                    System.out.println("El frente es: "+cola.obtenerFrente());
                    break;

                case 4:
                    System.out.println("La cola esta vacia: "+cola.esVacia());
                    break;

                case 5:
                    cola.vaciar();
                    break;

                case 6:
                    clon = cola.clone();
                    flag = true;
                    break;

                case 7:
                    mostrarCola(cola, clon, flag);
                    break;

                case 8:
                    iterar = false;
                    break;

                default:
                    System.out.println("Opcion no valida");

            }

        }

    }

    public static void main(String[] args) {
        //Algoritmo que testea la clase cola
        Cola cola = new Cola(), clon = new Cola();

        menu(cola, clon);

    }


}
