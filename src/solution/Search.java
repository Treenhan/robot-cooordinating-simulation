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
    Search(PolarConfig startConfig, PolarConfig endConfig,int num){
        start = startConfig;
        end = endConfig;
        numASV=num;
    }

    private PolarConfig start;
    private PolarConfig end;
    private int numASV;

    public static Map<PolarConfig,PolarConfig> parents;//containing parents//TODO get the parents out

    private static HashSet<PolarConfig> visited;//false if not visited

    /**
     * main search using BFS
     */
    public void startBFSSearch()throws java.io.IOException{
        Flag foundSolution = new Flag(false);

        Writer writer = new Writer("solution.txt",start,end,numASV);
        parents = new HashMap<>(); //clean the parents
        visited = new HashSet<>();//clean the visited list

        Queue<PolarConfig> queue = new LinkedList<>();//queue to start search

        queue.add(start);//add the first element
        markVisited(start);//mark the first element as visited

        while(!queue.isEmpty()&& !foundSolution.getFlag()){
            PolarConfig current = queue.remove();

            markVisited(current);

            List<PolarConfig> successors;
            successors=getSuccessors(current);//get the children

            while(!successors.isEmpty() && !foundSolution.getFlag()){

                PolarConfig newChild = successors.get(0);

                if(newChild==end) {
                    parents.put(newChild,current);
                    System.out.println("WE ARE WINNERSSS!!!");//stop condition
                    foundSolution.setFlag(true);
                    writer.printPath();
                    visited.add(newChild);
                    break;
                }

                //debugging
                System.out.println("visiting: "+newChild);

                if(!visited.contains(newChild)){//if this guy is not visited yet
                    queue.add(newChild);
                    visited.add(newChild);
                    parents.put(newChild,current);
                }
                successors.remove(0);
            }
        }

        if(!visited.contains(end)) {
            System.out.println("WE ARE losersss");
            writer.closeWriter();
        }



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
