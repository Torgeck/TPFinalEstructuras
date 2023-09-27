package tests.conjuntistas;

import estructuras.grafo.Grafo;
import estructuras.lineales.Lista;

public class TestGrafo {

        public static void main(String[] args) {
                // Clase que testea la estructura de datos grafo
                Grafo a = new Grafo(), b = new Grafo();
                Lista nodosA = new Lista();
                final String barra = "\n==========================================================================================================\n";

                // Lleno una lista con nodos que almacenan valores numericos
                generarNodosA(nodosA);

                // Muestra los grafos y chekean si estan esVacios
                System.out.println(
                                barra + "Muestra el grafo a \n" + a.toString() + "\nEl grafo esta esVacio?: "
                                                + a.esVacio()
                                                + barra);

                // Lleno el grafo y luego lo muestro
                llenarGrafo(a, nodosA);
                System.out.println(
                                barra + "Muestra el grafo a \n" + a.toString() + "\nEl grafo esta esVacio?: "
                                                + a.esVacio()
                                                + barra);

                // Veo si en el grafo existe arco
                System.out.println(barra + "Existe arco de A a B tiene que ser false es: "
                                + a.existeArco(nodosA.recuperar(1), nodosA.recuperar(2)));
                // No existe nodo Z en grafo
                System.out.println("Existe arco de Z a B tiene que ser false es: "
                                + a.existeArco("Z", nodosA.recuperar(2)));
                System.out.println("Existe vertice A tiene que ser true: " + a.existeVertice('A'));
                System.out.println("Existe vertice Z tiene que ser false: " + a.existeVertice('Z') + barra);

                // Procedo a llenar el grafo con arcos
                System.out.println(barra + "Agrego arcos de A => [ B:5 , C:10 , D:10 ] tiene que dar todo true "
                                + a.insertarArco('A', 'B', 5)
                                + a.insertarArco('A', 'C', 10) + a.insertarArco('A', 'D', 10));
                System.out.println("Agrego arcos de B => [ E:50, G:20 ] tiene que dar todo true "
                                + a.insertarArco('B', 'E', 50)
                                + a.insertarArco('B', 'G', 20));
                System.out.println(
                                "Agrego arcos de C => [ H:5 ] tiene que dar todo true " + a.insertarArco('C', 'H', 5));
                System.out.println("Agrego arcos de D => [ E:20 ] tiene que dar todo true "
                                + a.insertarArco('D', 'E', 20));
                System.out.println("Agrego arcos de E => [ K:10 ] tiene que dar todo true "
                                + a.insertarArco('E', 'K', 10));
                System.out.println("Agrego arcos de F => [ J:6, K:3 ] tiene que dar todo true "
                                + a.insertarArco('F', 'J', 6)
                                + a.insertarArco('F', 'K', 3));
                System.out.println("Agrego arcos de G => [ J:10, I:30 ] tiene que dar todo true "
                                + a.insertarArco('G', 'J', 10)
                                + a.insertarArco('G', 'I', 30));
                System.out
                                .println("Agrego arcos de H => [ I:5 ] tiene que dar todo true "
                                                + a.insertarArco('H', 'I', 5) + barra);

                // Muestro el grafo con los arcos
                System.out.println("Existe arco de A a D tiene que ser true es: " + a.existeArco('A', 'D'));
                System.out.println("Existe vertice E tiene que ser true es: " + a.existeVertice('E'));
                System.out.println("Muestra el grafo a \n" + a.toString() + "\nEl grafo esta esVacio?: " + a.esVacio());

                // Encuentro el camino mas corto entre nodos
                System.out.println(barra + "El camino mas corto entre A y B tiene que dar [A, B] con 5u, y da: "
                                + a.caminoMasCorto('A', 'B').toString());
                System.out.println("El camino mas corto entre A y J tiene que dar [A, B, G, J] con 35u, y da: "
                                + a.caminoMasCorto('A', 'J').toString());
                System.out.println("El camino mas corto entre J y A tiene que dar [J, G, B, A] con 35u, y da: "
                                + a.caminoMasCorto('J', 'A').toString());
                System.out.println("El camino mas corto entre K y C tiene que dar [K, E, D, A, C] con 35u, y da: "
                                + a.caminoMasCorto('K', 'C').toString());
                System.out.println("El camino mas corto entre E y Z tiene que dar [] con 0u, y da: "
                                + a.caminoMasCorto('E', 'Z').toString() + barra);

                // Encuentro el camino con menos nodos entre dos nodos
                System.out.println(barra + "El camino mas corto entre A y B tiene que dar [A, B] con 0, y da: "
                                + a.menorCaminoCantidadNodos('A', 'B').toString());
                System.out.println("El camino mas corto entre A y J tiene que dar [A, B, G, J] con 2, y da: "
                                + a.menorCaminoCantidadNodos('A', 'J').toString());
                System.out.println("El camino mas corto entre A y I tiene que dar [A, C, H, I] con 2, y da: "
                                + a.menorCaminoCantidadNodos('A', 'I').toString());
                System.out.println("El camino mas corto entre E y Z tiene que dar [] con 0u, y da: "
                                + a.menorCaminoCantidadNodos('E', 'Z').toString() + barra);

                // Verifico si es posible llegar de nodoOrigen a nodoDestino en X unidades
                System.out.println(
                                barra + "Verificar si es posible el camino de A y B con 5u tiene que dar true , y da: "
                                                + a.verificarCaminoMenorDistacia('A', 'B', 5));
                System.out.println("Verificar si es posible el camino de A y J con 40u tiene que dar true , y da: "
                                + a.verificarCaminoMenorDistacia('A', 'J', 40));
                System.out.println("Verificar si es posible el camino de J y A con 20u tiene que dar false , y da: "
                                + a.verificarCaminoMenorDistacia('J', 'A', 20));
                System.out.println("Verificar si es posible el camino de K y C con 25u tiene que dar false , y da: "
                                + a.verificarCaminoMenorDistacia('K', 'C', 25));
                System.out.println("Verificar si es posible el camino de A y A con 0U tiene que dar true , y da: "
                                + a.verificarCaminoMenorDistacia('A', 'A', 0));
                System.out.println("Verificar si es posible el camino de A y Z con 5u tiene que dar false , y da: "
                                + a.verificarCaminoMenorDistacia('E', 'Z', 5) + barra);

                // Obtener todos los caminos posibles de A-B que pasen por C
                System.out.println(barra + "Todos los caminos posibles de A->E->H son: "
                                + a.listarCaminosPosibles('A', 'E', 'H'));

                System.out.println(barra + "Todos los caminos posibles de A->A->H son: "
                                + a.listarCaminosPosibles('A', 'A', 'E'));
                System.out.println("Todos los caminos posibles de A->E->A son: "
                                + a.listarCaminosPosibles('A', 'E', 'A'));
                System.out.println("Todos los caminos posibles de K->B->I son: "
                                + a.listarCaminosPosibles('K', 'B', 'I'));
                System.out.println("Todos los caminos posibles de B->D->G son: "
                                + a.listarCaminosPosibles('B', 'D', 'G') + barra);

                // Existe camino
                System.out.println(
                                barra + "Existe camino entre A y F? tiene que dar true: " + a.existeCamino('A', 'F'));
                System.out.println("Existe camino entre F y A? tiene que dar true: " + a.existeCamino('F', 'A'));
                System.out.println(
                                "Existe camino entre B y Z? tiene que dar false: " + a.existeCamino('B', 'Z') + barra);

                // Listar en profundidad
                System.out.println(barra + "Listado en profundidad " + a.listarEnProfundidad());
                System.out.println("Listado en anchura " + a.listarEnAnchura() + barra);

                // Elimino nodos vertices
                System.out.println(barra + "Elimino vertice B se tienen que eliminar tambien los arcos de [A,E,G] "
                                + a.eliminarVertice('B'));
                System.out.println("Muestra el grafo a \n" + a.toString() + "\nEl grafo esta esVacio?: " + a.esVacio());
                System.out.println("Elimino vertice D se tienen que eliminar tambien los arcos de [A,E] "
                                + a.eliminarVertice('D'));
                System.out.println("Muestra el grafo a \n" + a.toString() + "\nEl grafo esta esVacio?: " + a.esVacio()
                                + barra);

                // Elimino arcos
                System.out.println(barra + "Elimino arco A a C" + a.eliminarArco('A', 'C', 10));
                System.out.println("Muestra el grafo a \n" + a.toString() + "\nEl grafo esta esVacio?: " + a.esVacio());
                System.out.println("Elimino arco J a F" + a.eliminarArco('J', 'F', 6));
                System.out.println("Muestra el grafo a \n" + a.toString() + "\nEl grafo esta esVacio?: " + a.esVacio()
                                + barra);

                // esVacio el grafo
                System.out.println(barra + "esVacio el grafo");
                a.vaciar();
                System.out.println("Muestra el grafo a \n" + a.toString() + "\nEl grafo esta esVacio?: " + a.esVacio());

                // Existe camino pero con grafo esVacio
                System.out.println("Existe camino entre A y F? tiene que dar false: " + a.existeCamino('A', 'F'));
                System.out.println("Existe camino entre F y A? tiene que dar false: " + a.existeCamino('F', 'A'));
                System.out.println("Existe camino entre B y Z? tiene que dar false: " + a.existeCamino('B', 'Z'));

                // Camino mas corto pero con grafo esVacio
                System.out.println("El camino mas corto entre A y B tiene que dar [] con , y da: "
                                + a.caminoMasCorto('A', 'B').toString());
                System.out.println("El camino mas corto entre A y J tiene que dar [] con , y da: "
                                + a.caminoMasCorto('A', 'J').toString() + barra);
        }

        public static void generarNodosA(Lista lista) {
                // Metodo que genera valores numericos y los va insertando en la lista
                int i = 0, car = 65;

                while (i <= 10) {
                        lista.insertar((char) (car + i), i + 1);
                        i++;
                }
        }

        public static void llenarGrafo(Grafo g, Lista l) {
                int i = 1;

                while (i <= l.longitud()) {
                        System.out.println("Inserto " + l.recuperar(i) + " en grafo: "
                                        + g.insertarVertice(l.recuperar(i)));
                        i++;
                }
        }
}
