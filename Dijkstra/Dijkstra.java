
package Dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author lmbaeza
 */

public class Dijkstra {

    public static HashMap<String, Vertex> vertexMap = new HashMap<>();;
    public static double MAX_VALUE = Double.POSITIVE_INFINITY;;

    public Dijkstra() {}

    public static boolean inalcanzable(double distancia) {
        return (distancia == MAX_VALUE);
    }

    public static void agregarArcoConPeso(String from, String to, double distance) {
        Vertex vertexFrom = vertexMap.get(from);
        Vertex VertexTo = vertexMap.get(to);

        Edge edgeFrom = new Edge (vertexFrom, distance);
        Edge edgeTo = new Edge (VertexTo, distance);

        vertexFrom.list.add(edgeTo);
        VertexTo.list.add(edgeFrom);
    }
    
    public static double minDistanceTo(String to) { 
        return vertexMap.get(to).distanceMinVertex;
    }
    
    static void minDistanceFrom(Vertex from) { 
        from.distanceMinVertex = 0.0;

        Queue<Vertex> queue = new PriorityQueue<>();
        queue.add(from);

        while (!queue.isEmpty()) {
            Vertex tmp = queue.poll();

            for (Edge edge : tmp.list){
                Vertex vertexTo = edge.to;
                double distancia = edge.distanceTo;
                double distanceVertex = (tmp.distanceMinVertex + distancia);
                if (distanceVertex < vertexTo.distanceMinVertex) {
                    queue.remove(vertexTo);
                    vertexTo.distanceMinVertex = (double)(distanceVertex);
                    vertexTo.previous = (Vertex)(tmp);
                    queue.add(vertexTo);
                }
            }
        }
    }

    static class Edge {

        public final Vertex to;
        public final double distanceTo;

        public Edge(Vertex to){
            this(to, 0.0);
        }
        public Edge(Vertex to, double distanceTo){
            this.to = to;
            this.distanceTo = distanceTo;
        }
    }

    static class Vertex implements Comparable<Vertex>{

        public final String iD;
        public ArrayList<Edge> list;
        public double distanceMinVertex = MAX_VALUE;
        public Vertex previous;

        public Vertex(String iD) {
            this(iD, new ArrayList<>(), null);
        }

        public Vertex(String iD, ArrayList<Edge> list, Vertex previous) {
            this.iD = iD;
            this.list = list;
            this.previous = previous;
        }

        @Override
        public int compareTo(Vertex other) {
            return Double.compare(distanceMinVertex, other.distanceMinVertex);
        }
    }
    
    public static void main(String... args) {
        double distancia;
        Vertex vertex;
        
        for (int j = 0 ; j < 10 ; j++){
            vertex = new Vertex(Integer.toString(j));
            Dijkstra.vertexMap.put(Integer.toString(j) , vertex);
        }
        
        Dijkstra.agregarArcoConPeso("1", "2", 20.0);
        Dijkstra.agregarArcoConPeso("1", "3", 3.0);
        Dijkstra.agregarArcoConPeso("2", "3", 5.0);
        
        Dijkstra.minDistanceFrom(vertexMap.get("1"));
        distancia = Dijkstra.minDistanceTo("2"); 
        if (!inalcanzable(distancia)){
            System.out.println((int)distancia);
        }else{
            System.out.println("Inalcanzable");
        }
    }
}