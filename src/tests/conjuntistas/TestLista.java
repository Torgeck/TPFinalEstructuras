package tests.conjuntistas;

import estructuras.lineales.Lista;

public class TestLista {

        public static void main(String[] args) {
                Lista a = new Lista(), b = new Lista(), c;

                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());
                System.out.println("Muestra lista B = " + b.toString() + " con longitud: " + b.longitud());

                // AGREGO ITEMS
                System.out.println("Agrego A a lista A" + a.insertar('A', 1));
                System.out.println("Agrego B a lista A" + a.insertar('B', 2));
                System.out.println("Agrego C a lista A" + a.insertar('C', 3));
                System.out.println("Agrego D a lista A" + a.insertar('D', 4));
                System.out.println("Agrego E a lista A" + a.insertar('E', 5));
                System.out.println("Agrego F a lista A" + a.insertar('F', 6));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());

                System.out.println("Agrego 1 a lista B" + b.insertar(1, 1));
                System.out.println("Agrego 2 a lista B" + b.insertar(2, 2));
                System.out.println("Agrego 3 a lista B" + b.insertar(3, 3));
                System.out.println("Agrego 4 a lista B" + b.insertar(4, 4));
                System.out.println("Agrego 5 a lista B" + b.insertar(5, 5));
                System.out.println("Agrego 6 a lista B" + b.insertar(6, 6));
                System.out.println("Muestra lista B = " + b.toString() + " con longitud: " + b.longitud());

                System.out.println("Agrego 100 a lista A en la posicion 3" + a.insertar(100, 3));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());
                System.out.println("Agrego 101 a lista A en la posicion 2" + a.insertar(101, 2));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());
                System.out.println("Agrego 102 a lista A en la posicion 5" + a.insertar(102, 5));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());
                System.out.println("Agrego 103 a lista A en la posicion 2" + a.insertar(103, 2));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());
                System.out.println("Agrego 104 a lista A en la posicion 7" + a.insertar(104, 7));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());
                System.out.println("Agrego 105 a lista A en la posicion 3" + a.insertar(105, 3));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());

                // Elimino elementos de la lista
                System.out.println("Elimino elemento en posicion 4 de la lista A " + a.eliminar(4) + a);
                System.out.println("Elimino elemento en posicion 5 de la lista A " + a.eliminar(5) + a);
                System.out.println("Elimino elemento en posicion 9 de la lista A " + a.eliminar(9) + a);
                System.out.println("Elimino elemento en posicion 1 de la lista A " + a.eliminar(1) + a);
                System.out.println("Elimino elemento en posicion 1 de la lista A " + a.eliminar(1) + a);
                System.out.println("Elimino elemento en posicion 7 de la lista A " + a.eliminar(7) + a);
                System.out.println("Elimino elemento en posicion 6 de la lista A " + a.eliminar(6) + a);
                System.out.println(
                                "Elimino elemento en posicion 5 de la lista A " + a.eliminar(5) + a + " con longitud: "
                                                + a.longitud());

                System.out.println("Ahora agrego los items de B a A: " + a.insertarElementosLista(b));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());
                System.out.println("Muestra lista B = " + b.toString() + " con longitud: " + b.longitud());

                System.out.println("Muestro el elemento con indice 0 en la lista " + a.recuperar(0));
                System.out.println("Muestro el elemento con indice 1 en la lista " + a.recuperar(1));
                System.out.println("Muestro el elemento con indice 4 en la lista " + a.recuperar(4));
                System.out.println("Muestro el elemento con indice 3 en la lista " + a.recuperar(3));

                System.out.println("Muestro el elemento B con indice " + a.localizar('B'));
                System.out.println("Muestro el elemento 1 con indice " + a.localizar(1));
                System.out.println("Muestro el elemento 6 con indice " + a.localizar(6));
                System.out.println("Muestro el elemento 105 con indice " + a.localizar(105));

                // Vacio y agrego al final y al inicio en ambas listas
                a.vaciar();
                b.vaciar();
                System.out.println("Se vacian ambas listas A: " + a.toString() + " y B: " + b.toString());

                System.out.println("Agrego 100 al final de la lista" + a.insertarFin(100));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());
                System.out.println("Agrego 101 al final de la lista" + a.insertarFin(101));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());
                System.out.println("Agrego 102 al final de la lista" + a.insertarFin(102));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());
                System.out.println("Agrego 103 al final de la lista" + a.insertarFin(103));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());
                System.out.println("Agrego 104 al final de la lista" + a.insertarFin(104));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());
                System.out.println("Agrego 105 al final de la lista" + a.insertarFin(105));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud() + "\n");

                System.out.println("Agrego 1 al inico de la lista" + b.insertarInicio(1));
                System.out.println("Muestra lista A = " + b.toString() + " con longitud: " + b.longitud());
                System.out.println("Agrego 2 al inicio de la lista" + b.insertarInicio(2));
                System.out.println("Muestra lista A = " + b.toString() + " con longitud: " + b.longitud());
                System.out.println("Agrego 3 al inicio de la lista" + b.insertarInicio(3));
                System.out.println("Muestra lista A = " + b.toString() + " con longitud: " + b.longitud());
                System.out.println("Agrego 4 al inicio de la lista" + b.insertarInicio(4));
                System.out.println("Muestra lista A = " + b.toString() + " con longitud: " + b.longitud());
                System.out.println("Agrego 5 al inicio de la lista" + b.insertarInicio(5));
                System.out.println("Muestra lista A = " + b.toString() + " con longitud: " + b.longitud());
                System.out.println("Agrego 6 al inicio de la lista" + b.insertarInicio(6));
                System.out.println("Muestra lista A = " + b.toString() + " con longitud: " + b.longitud() + "\n");

                // Clonar listas
                c = a.clone();
                System.out.println(
                                "Clono la lista A a C, Lista A: " + a.toString() + ", Lista Clon c: " + c.toString());
                System.out.println("Agrego 1 al inico de la lista" + a.insertarInicio(1));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());
                System.out.println("Agrego 2 al inicio de la lista" + a.insertarInicio(2));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());
                System.out.println("Agrego 3 al inicio de la lista" + a.insertarInicio(3));
                System.out.println("Muestra lista A = " + a.toString() + " con longitud: " + a.longitud());

                System.out.println(
                                "Clono la lista A a C, Lista A: " + a.toString() + ", Lista Clon c: " + c.toString()
                                                + c.longitud());

                c.insertar(1, 1);
                c.insertar(2, 1);
                c.insertar(3, 1);
                c.insertar(4, 1);
                c.insertar(5, 1);
                c.insertar(6, 1);
                c.insertar(7, 1);

                System.out.println(
                                "Clono la lista A a C, Lista A: " + a.toString() + ", Lista Clon c: " + c.toString()
                                                + c.longitud());
                c.vaciar();
                modificaInternamente(c);
                System.out.println("Muestro c: " + c.toString());
        }

        public static void modificaInternamente(Lista unaLista) {
                Lista otraLista = new Lista();
                otraLista.insertar(1, 1);
                otraLista.insertar(2, 1);
                otraLista.insertar(3, 1);
                otraLista.insertar(4, 1);
                otraLista.insertar(5, 1);
                otraLista.insertar(6, 1);
                otraLista.insertar(7, 1);

                unaLista = otraLista.clone();

                System.out.println("Muestro lista interna: " + otraLista.toString()
                                + "\nLuego muestro la lista pasada por parametro: " + unaLista.toString());
        }

}
