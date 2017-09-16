package solution;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import javax.xml.bind.SchemaOutputResolver;
import java.util.*;

/**
 * Created by Treenhan on 9/14/17.
 */
public class Search {

    /**
     * constructor to initialize variables
     */
    Search(PolarConfig startConfig, PolarConfig endConfig){
        start = startConfig;
        end = endConfig;
    }

    private PolarConfig start;
    private PolarConfig end;

    public static Map<PolarConfig,PolarConfig> parents;//containing parents//TODO get the parents out

    private static HashSet<PolarConfig> visited;//false if not visited

    /**
     * main search using BFS
     */
    public void startBFSSearch(){
        parents = new HashMap<>(); //clean the parents
        visited = new HashSet<>();//clean the visited list

        Queue<PolarConfig> queue = new LinkedList<>();//queue to start search

        queue.add(start);//add the first element
        markVisited(start);//mark the first element as visited

        while(!queue.isEmpty()){
            PolarConfig current = queue.remove();

            if(current==end) System.out.println("WE ARE WINNERSSS!!!");//stop condition

            markVisited(current);

            List<PolarConfig> successors;
            successors=getSuccessors(current);//get the children

            while(!successors.isEmpty()){

                PolarConfig newChild = successors.get(0);

                //debugging
                System.out.println("visiting: "+newChild);
                if(!visited.contains(newChild)){//if this guy is not visited yet
                    queue.add(newChild);
                    visited.add(newChild);
                }
                successors.remove(0);
            }
        }

        if(visited.contains(end)) System.out.println("WE ARE WINNERSSS!!!");
        else System.out.println("WE ARE losersss");


    }

    /**
     * change the state of a node
     * @param polarConfig
     */
    public void markVisited(PolarConfig polarConfig){
        visited.add(polarConfig);
    }

    /**
     * get the successors of a node
     */
    public List<PolarConfig> getSuccessors(PolarConfig node){
        return AdjacencyList.getChildren(node);

    }
}
