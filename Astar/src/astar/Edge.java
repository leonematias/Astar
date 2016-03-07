package astar;

/**
 * @author Matias Leone
 */
public class Edge {
    
    private Vertex from;
    private Vertex to;

    public Edge(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    @Override
    public String toString() {
        return from.getId() + " -> " + to.getId();
    }
    
    
    
}
