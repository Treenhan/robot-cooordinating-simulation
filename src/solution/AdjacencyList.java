package solution;

import java.util.*;

/**
 * Created by Treenhan on 9/13/17.
 */
public class AdjacencyList {
    public static Map<PolarConfig,List<PolarConfig>> adjacencyList = new HashMap<>();

    /**
     * adding vertices to the map
     * @param config
     */
   public static void addVertex(PolarConfig config){
       adjacencyList.put(config,new LinkedList<PolarConfig>());
   }

    /**
     * add an edge into the graph
     * @param source
     * @param destination
     */
    public static void addEdge(PolarConfig source, PolarConfig destination)
    {
        if (!adjacencyList.containsKey(source)) addVertex(source);
        if(!adjacencyList.containsKey(destination)) addVertex(destination);

        List<PolarConfig> slist = adjacencyList.get(source);
        slist.add(destination);
        List<PolarConfig> dlist = adjacencyList.get(destination);
        dlist.add(source);
    }

    /**
     * get all the children of a node
     */
    public static List<PolarConfig> getChildren(PolarConfig node){
        List<PolarConfig> children;

        children = adjacencyList.get(node);

        return children;
    }

}
