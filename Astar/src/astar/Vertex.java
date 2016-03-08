
package astar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matias Leone
 */
public class Vertex {
    
    private int id;
    private List<Edge> edges;
    private double x;
    private double y;
    
    public Vertex(int id, double x, double y) {
        this.id = id;
        this.edges = new ArrayList<Edge>();
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    public static double distance(Vertex a, Vertex b) {
        return Math.sqrt(distanceSq(a, b));
    }
    
    public static double distanceSq(Vertex a, Vertex b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return dx * dx + dy * dy;
    }
    
    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertex other = (Vertex) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
}
