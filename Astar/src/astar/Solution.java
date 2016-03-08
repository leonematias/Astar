package astar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matias Leone
 */
public class Solution implements Comparable<Solution>{
    
    private Vertex lastVertex;
    private List<Edge> edges;
    private double edgeCost;
    private double cost;
    
    public Solution(Vertex lastVertex) {
        this.lastVertex = lastVertex;
        this.edges = new ArrayList<Edge>();
        this.cost = 0;
        this.edgeCost = 0;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Vertex getLastVertex() {
        return lastVertex;
    }

    public double getEdgeCost() {
        return edgeCost;
    }

    public void setEdgeCost(double edgeCost) {
        this.edgeCost = edgeCost;
    }

    @Override
    public int compareTo(Solution o) {
        return Double.compare(this.cost, o.cost);
    }
    
    
    
}
