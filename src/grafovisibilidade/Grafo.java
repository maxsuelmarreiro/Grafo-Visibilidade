package grafovisibilidade;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author '-Maxsuel
 */
public class Grafo {

    private ArrayList<Vertice> vertices;
    private ArrayList<Aresta> arestas;

    private boolean orientado;
    private static int ID_AUTO_OBSTACULO = 0;

    public Grafo() {
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();
    }

    public void grafoVisibilidade() {
        ArrayList<Aresta> tempArestas = getArestas();
        ArrayList<Vertice> tempVertice = getVertices();
        ArrayList<Vertice> tempLista = new ArrayList<>();

        for (Vertice ver1 : tempVertice) {
            for (Vertice ver2 : tempVertice) {
                if (ver1.getId() != ver2.getId()) {
                    if (ver1.getIdObstaculo() != ver2.getIdObstaculo()) {
                        int haObstaculos = 0;
                        for (Aresta aresta : tempArestas) {
                            if ((interseccaoRetas(ver1.getX(), ver1.getY(), ver2.getX(), ver2.getY(), aresta.getV1().getX(), aresta.getV1().getY(), aresta.getV2().getX(), aresta.getV2().getY()))) {

                                //IF ( Vertice1(X,Y) == Aresta1(X,Y) || Vertice1(X,Y) == Aresta2(X,Y) || 
                                //     Vertice2(X,Y) == Aresta1(X,Y) || Vertice2(X,Y) == Aresta2(X,Y))
                                if (((ver1.getX() == aresta.getV1().getX() && ver1.getY() == aresta.getV1().getY()) || (ver1.getX() == aresta.getV2().getX() && ver1.getY() == aresta.getV2().getY()) || (ver2.getX() == aresta.getV1().getX() && ver2.getY() == aresta.getV1().getY()) || (ver2.getX() == aresta.getV2().getX() && ver2.getY() == aresta.getV2().getY()))) {
                                } else {
                                    haObstaculos++;
                                }
                            } else {

                            }
                        }
                        if (haObstaculos == 0) {
                            //inserirAresta(ver1.getId(), ver2.getId());
                            tempLista.add(ver1);
                            tempLista.add(ver2);
                        }
                    }
                }
            }
        }
        for (int m = 0; m < tempLista.size(); m += 2) {
            inserirAresta(tempLista.get(m).getId(), tempLista.get(m + 1).getId());
        }
    }

    public boolean interseccaoRetas(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        return Line2D.linesIntersect(x1, y1, x2, y2, x3, y3, x4, y4);
    }

    public boolean verticeExiste(int v) {
        return vertices.stream().anyMatch((vertice) -> (vertice.getId() == v));
    }

    public boolean arestaExiste(int v1, int v2) {
//      for (Aresta aresta : arestas) {
//      if ((aresta.getV1().getId() == v1 && aresta.getV2().getId() == v2) || (aresta.getV1().getId() == v2 && aresta.getV2().getId() == v1)) {

        return arestas.stream().anyMatch((aresta) -> ((aresta.getV1().getId() == v1 && aresta.getV2().getId() == v2) || (aresta.getV1().getId() == v2 && aresta.getV2().getId() == v1)));
    }

    public boolean inserirVertice(int idVertice, int idObstaculo, double xVertice, double yVertice) {
        if (!verticeExiste(idVertice)) {
            Vertice addVertice = new Vertice();
            addVertice.setId(idVertice);
            addVertice.setIdObstaculo(idObstaculo);
            addVertice.setX(xVertice);
            addVertice.setY(yVertice);
            //addVertice.setPeso(pesoVertice);
            vertices.add(addVertice);
            return true;
        }
        return false;
    }

    public boolean inserirVertice(int idVertice, double xVertice, double yVertice) {
        if (!verticeExiste(idVertice)) {
            Vertice addVertice = new Vertice();
            addVertice.setId(idVertice);
            addVertice.setIdObstaculo(0);
            addVertice.setX(xVertice);
            addVertice.setY(yVertice);
            //addVertice.setPeso(pesoVertice);
            vertices.add(addVertice);
            return true;
        }
        return false;
    }

    public boolean inserirAresta(int v1, int v2) {
        if (verticeExiste(v1) && verticeExiste(v2)) {
            if (!(arestaExiste(v1, v2))) {
                Vertice vrc1 = buscarVertice(v1);
                Vertice vrc2 = buscarVertice(v2);

                vrc1.getAdjacente().add(vrc2);
                vrc2.getAdjacente().add(vrc1);

                Aresta addAresta = new Aresta();
                addAresta.setV1(vrc1);
                addAresta.setV2(vrc2);
                //calcular tamanho da aresta
                double peso = (Math.sqrt(Math.pow(vrc2.getX() - vrc1.getX(), (2))) * (Math.pow(vrc2.getY() - vrc1.getY(), (2))));
                addAresta.setPeso(peso);
                arestas.add(addAresta);
                return true;
            }
        }
        return false;
    }

    public void inserirObstaculo(Grafo grafo) {
        Vertice verticePrimeiro = null;
        Vertice verticeUltimo = null;
        Vertice verticeAnterior = null;
        Vertice verticeAtual = null;

        Scanner ler = new Scanner(System.in);
        System.out.print("Quantidade de lados:");
        int qtdLados = ler.nextInt();
        int idObstaculo = ID_AUTO_OBSTACULO;
        ID_AUTO_OBSTACULO++;
        for (int i = 1; i <= qtdLados; i++) {
            System.out.println("VERTICE " + i + " de " + qtdLados);
            System.out.print("ID do Vertice: ");
            int idVertice = ler.nextInt();
            System.out.print("X: ");
            double xVertice = ler.nextDouble();
            System.out.print("Y: ");
            double yVertice = ler.nextDouble();

            if (inserirVertice(idVertice, idObstaculo, xVertice, yVertice)) {
            } else {
                System.out.println("Ops!. Algo errado. Parece que esse ID já existe.");
                i--;
            }

            if (i == 1) {
                verticeAtual = buscarVertice(idVertice);
                verticePrimeiro = buscarVertice(idVertice);
            } else if (i == qtdLados) {
                verticeUltimo = buscarVertice(idVertice);
                inserirAresta(verticeAtual.getId(), verticeUltimo.getId());
                inserirAresta(verticePrimeiro.getId(), verticeUltimo.getId());
            } else {
                verticeAnterior = verticeAtual;
                verticeAtual = buscarVertice(idVertice);
                inserirAresta(verticeAtual.getId(), verticeAnterior.getId());
            }
            System.out.println("");
        }
    }

    public void inserirObstaculo(Scanner in) {
        Vertice verticePrimeiro = null;
        Vertice verticeUltimo = null;
        Vertice verticeAnterior = null;
        Vertice verticeAtual = null;

        Scanner ler = in;
        int qtdLados = ler.nextInt();
        int idObstaculo = ID_AUTO_OBSTACULO;
        ID_AUTO_OBSTACULO++;
        for (int i = 1; i <= qtdLados; i++) {
            int idVertice = ler.nextInt();
            double xVertice = ler.nextDouble();
            double yVertice = ler.nextDouble();

            if (inserirVertice(idVertice, idObstaculo, xVertice, yVertice)) {
            } else {
                System.out.println("Ops!. Algo errado. Parece que esse ID já existe. (" + (idVertice) + ")");
                i--;
            }

            if (i == 1) {
                verticeAtual = buscarVertice(idVertice);
                verticePrimeiro = buscarVertice(idVertice);
            } else if (i == qtdLados) {
                verticeUltimo = buscarVertice(idVertice);
                inserirAresta(verticeAtual.getId(), verticeUltimo.getId());
                inserirAresta(verticePrimeiro.getId(), verticeUltimo.getId());
            } else {
                verticeAnterior = verticeAtual;
                verticeAtual = buscarVertice(idVertice);
                inserirAresta(verticeAtual.getId(), verticeAnterior.getId());
            }
        }
    }

    public Vertice buscarVertice(int idVertice) {
        for (Vertice vertice : vertices) {
            if (vertice.getId() == idVertice) {
                return vertice;
            }
        }
        return null;
    }

    public void imprimirGrafo() {
        vertices.stream().map((vertice) -> {
            Vertice v = vertice;
            System.out.print(vertice.getId() + "(" + vertice.getX() + "," + vertice.getY() + ")" + "->");

            for (int i = 0; i < v.getAdjacente().size(); i++) {
                System.out.print(v.getAdjacente().get(i).getId() + "(" + v.getAdjacente().get(i).getX() + "," + v.getAdjacente().get(i).getY() + ") - ");
            }
            return vertice;
        }).forEach((_item) -> {
            System.out.println("");
        });
    }

    public String[][] imprimeMatrizdeIncidencia(Grafo g) {
        String[][] matriz = new String[getVertices().size() + 1][getArestas().size() + 1];
        if (orientado) {
            matriz[0][0] = "#";
            for (int i = 0; i < getVertices().size(); i++) {
                matriz[i + 1][0] = Integer.toString(getVertices().get(i).getId());
            }
            for (int i = 0; i < getArestas().size(); i++) {
                matriz[0][i + 1] = getArestas().get(i).getV1().getId() + "-" + getArestas().get(i).getV2().getId();
            }
            for (int i = 0; i < getVertices().size(); i++) {
                for (int j = 0; j < getArestas().size(); j++) {
                    if (matriz[i + 1][0].equals(Integer.toString(getArestas().get(j).getV1().getId())) || matriz[i + 1][0].equals(Integer.toString(getArestas().get(j).getV2().getId()))) {
                        matriz[i + 1][j + 1] = "  1";
                    } else {
                        matriz[i + 1][j + 1] = "  0";
                    }
                }
            }
            for (int i = 0; i < getVertices().size() + 1; i++) {
                for (int j = 0; j < getArestas().size() + 1; j++) {
                    System.out.print("  " + matriz[i][j] + "   ");
                }
                System.out.println("");
            }
        }
        return matriz;
    }

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertice> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Aresta> getArestas() {
        return arestas;
    }

    public void setArestas(ArrayList<Aresta> arestas) {
        this.arestas = arestas;
    }

    public boolean isOrientado() {
        return orientado;
    }

    public void setOrientado(boolean orientado) {
        this.orientado = orientado;
    }

}
