package grafovisibilidade;

import java.util.ArrayList;

/**
 *
 * @author '-Maxsuel
 */
public class Vertice {
    
    private int id;
    private int idObstaculo;
    private double x;
    private double y;
    private double peso;
    private ArrayList<Vertice> adjacente = new ArrayList<Vertice>();

    
    public ArrayList<Vertice> getAdjacente() {
        return adjacente;
    }

    public void setAdjacente(ArrayList<Vertice> adjacente) {
        this.adjacente = adjacente;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    } 

    public int getIdObstaculo() {
        return idObstaculo;
    }

    public void setIdObstaculo(int idObstaculo) {
        this.idObstaculo = idObstaculo;
    }
}