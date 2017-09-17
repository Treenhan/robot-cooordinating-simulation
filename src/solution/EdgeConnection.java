package solution;

import problem.ASVConfig;
import problem.ProblemSpec;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Treenhan on 9/13/17.
 */
public class EdgeConnection {
    public static final double MAX_RADIUS = 0.1;

    /**
     * connect all the graph together
     */
    public static void connectEdges(ProblemSpec ps,int numASV) {
        int counter=0;//for debugging

        ArrayList<PolarConfig> samples = new ArrayList<PolarConfig>(Sampling.samples);

        Flag flag;


        PolarConfig currentVertex = samples.get(0);
        samples.remove(currentVertex);

        while (!samples.isEmpty()){

            for(PolarConfig each : samples){
                flag = new Flag(true);//reset the flag

                if(Point2D.distance(currentVertex.getPoint().getX(),currentVertex.getPoint().getY(),each.getPoint().getX(),each.getPoint().getY())<=MAX_RADIUS){//if others are inside this circle
                    EdgeCollision.isEdgeCollisionFree(ps,currentVertex,each,numASV,flag);//this is good

                    //debugging
                    System.out.println("OK"+counter);


                    counter++;

                    if(flag.getFlag()){//if the other point is collision free
                        AdjacencyList.addEdge(currentVertex,each);
                    }
                }
            }
            samples.remove(currentVertex);//remove this currentVertex
            currentVertex=samples.get(0);//take in the new current vertex
            samples.remove(currentVertex);
        }
    }

    /**
     * connect the init and the goal to the connected graph
     */
    public static void connectEdgesForTargets(ProblemSpec ps, int numASV, PolarConfig startPolar, PolarConfig endPolar) {
        int counter=0;//for debugging

        ArrayList<PolarConfig> samples = new ArrayList<PolarConfig>(Sampling.samples);

        Flag flag;

        PolarConfig currentVertex = startPolar;

        for(int i=0;i<2;i++){
            for(PolarConfig each : samples){
                flag = new Flag(true);//reset the flag

                if(Point2D.distance(currentVertex.getPoint().getX(),currentVertex.getPoint().getY(),each.getPoint().getX(),each.getPoint().getY())<=MAX_RADIUS){//if others are inside this circle
                    EdgeCollision.isEdgeCollisionFree(ps,currentVertex,each,numASV,flag);//this is good

                    //debugging
                    if(i==0)System.out.println("start-OK"+counter);
                    else System.out.println("end-OK"+counter);

                    counter++;

                    if(flag.getFlag()){//if the other point is collision free
                        AdjacencyList.addEdge(currentVertex,each);
                    }
                }
            }
            currentVertex=endPolar;
        }
    }


}
