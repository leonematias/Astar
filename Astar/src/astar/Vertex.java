
package astar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matias Leone
 */
public class Vertex {
    
    private int id;
    private List<Edge> edges;
    
    public Vertex(int id) {
        this.id = id;
        this.edges = new ArrayList<Edge>();
    }

    public int getId() {
        return id;
    }

    public List<Edge> getEdges() {
        return edges;
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
