package grafovisibilidade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author '-Maxsuel
 */
public class GrafoVisibilidade {

    private static final Scanner ler = new Scanner(System.in);
    private static final Grafo grafo = new Grafo();

    private static void menu() {
        System.out.println(" ---------- GRAFO DE VISIBILIDADE ----------");
        System.out.println("| OPÇÃO | OPERAÇÃO");
        System.out.println("|   0   | Povoar grafo (povoar.txt)");
        System.out.println("|   1   | Vertice Inicial/Final");
        System.out.println("|   2   | Inserir obstáculo");
        System.out.println("|   3   | Gerar grafoVisibilidade()");
        System.out.println("|   4   | Imprimir Lista de adjacência");
        System.out.println("|   5   | Imprimir Matriz de Incidencia");
        System.out.println("|   10  | SAIR");
        System.out.println("");
        System.out.print("OPÇÃO: ");
    }

    public static void main(String[] args) {
        grafo.setOrientado(true); //grafo orientado?
        int opcao;
        do {
            menu();
            opcao = ler.nextInt();
            switch (opcao) {
                case 0:
                    povoarGrafo();
                    break;
                case 1:
                    inserirVertice();
                    break;
                case 2:
                    grafo.inserirObstaculo(grafo);
                    break;
                case 3:
                    grafo.grafoVisibilidade();
                    break;
                case 4:
                    grafo.imprimirGrafo();
                    break;
                case 5:
                    grafo.imprimeMatrizdeIncidencia(grafo);
                    break;
                case 6:
                    //grafo.ImprimeMatrizdeAdjacencia(grafo);
                    break;
                default:
            }
        } while (opcao != 10);
    }

    private static void inserirVertice() {
        System.out.print("ID: ");
        int idVertice = ler.nextInt();
        System.out.print("X: ");
        double xVertice = ler.nextDouble();
        System.out.print("Y: ");
        double yVertice = ler.nextDouble();
        //System.out.print("PESO: ");
        //double pesoVertice = ler.nextDouble();

        grafo.inserirObstaculo(new Scanner("1 +" + idVertice + " " + xVertice + " " + yVertice));
    }

    private static void inserirAresta() {
        System.out.print("A1: ");
        int a1 = ler.nextInt();
        System.out.print("A2: ");
        int a2 = ler.nextInt();

        if (grafo.inserirAresta(a1, a2)) {
            System.out.println("Aresta adicionada.");
        } else {
            System.out.println("Erro.");
        }
    }

    private static void povoarGrafo() {
        ArrayList<String> dados = new ArrayList<>();
        String linha;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("povoar.txt"));
            StringTokenizer dado = null;
            String m = "";
            while ((linha = reader.readLine()) != null) {
                dado = new StringTokenizer(linha, "-> :(),");
                while (dado.hasMoreTokens()) {
                    m = m + dado.nextToken() + " ";
                }
                grafo.inserirObstaculo(new Scanner(m));
                m = "";
            }
            reader.close();

        } catch (IOException | NumberFormatException e) {
        }
    }
}
