package solution;

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
    public void startBFSSearch(String outputFile, Flag hardcoreFlag)throws java.io.IOException{
        System.out.println("Start BFS Searching...");

        Flag foundSolution = new Flag(false);

        Writer writer = new Writer(outputFile,start,end,numASV);
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
                    System.out.println("SOLUTION FOUND!");//stop condition
                    foundSolution.setFlag(true);
                    writer.printPath();
                    visited.add(newChild);
                    break;
                }


                if(!visited.contains(newChild)){//if this guy is not visited yet
                    queue.add(newChild);
                    visited.add(newChild);
                    parents.put(newChild,current);
                }
                successors.remove(0);
            }
        }

        if(!visited.contains(end)) {

            System.out.print("SOLUTION NOT FOUND. ");
            writer.closeWriter();
            hardcoreFlag.setFlag(true);


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
