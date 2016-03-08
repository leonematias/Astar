package astar;

/**
 * @author Matias Leone
 */
public class Edge {
    
    private int id;
    private Vertex from;
    private Vertex to;
    private double length;
    private double cost;

    public Edge(int id, Vertex from, Vertex to, double cost) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.length = Vertex.distance(from, to);
        this.cost = cost;
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    public double getLength() {
        return length;
    }

    public int getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    

    @Override
    public String toString() {
        return from.getId() + " -> " + to.getId();
    }
    
    public Vertex getOpposite(Vertex v) {
        return from.equals(v) ? to : from;
    }
    
    
}
