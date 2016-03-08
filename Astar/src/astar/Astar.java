package astar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author Matias Leone
 */
public class Astar {

    private final static double HEURISTIC_WEIGHT = 1.0;
    private final static double COST_WEIGHT = 1.0;
    
    public Solution findPath(Vertex origin, Vertex dest) {
        Set<Vertex> closedList = new HashSet<Vertex>();
        PriorityQueue<Solution> openList = new PriorityQueue<Solution>();
        
        Solution sol = new Solution(origin);
        openList.add(sol);
        closedList.add(origin);
        
        while((sol = openList.poll()) != null) {
            if(dest.equals(sol.getLastVertex()))
                return sol;
            
            expand(sol, dest, closedList, openList);
        }
        
        
        
        return null;
    }
    
    private void expand(Solution prevSolution, Vertex dest, Set<Vertex> closedList, PriorityQueue<Solution> openList) {
        Vertex current = prevSolution.getLastVertex();
        for (Edge edge : current.getEdges()) {
            Vertex next = edge.getOpposite(current);
            if(!closedList.contains(next)) {
                
                double heuristic = Vertex.distance(next, dest);
                double edgeCost = prevSolution.getEdgeCost() + edge.getLength() * edge.getCost();
                double totalCost = HEURISTIC_WEIGHT * heuristic + COST_WEIGHT * edgeCost;
                
                Solution sol = new Solution(next);
                sol.setEdgeCost(edgeCost);
                sol.setCost(totalCost);
                sol.getEdges().addAll(prevSolution.getEdges());
                sol.getEdges().add(edge);
                
                openList.add(sol);
                closedList.add(next);
            }
        }
        
    }
    
}
