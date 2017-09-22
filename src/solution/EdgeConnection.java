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
    public static  double MAX_RADIUS = 0.1;//original 0.1
    public static  double MAX_RADIUS_FOR_START_END = 0.1;//original 0.1  -- 10 for SQUARE

    /**
     * connect all the graph together
     */
    public static void connectEdges(ProblemSpec ps,int numASV) {
        ArrayList<PolarConfig> samples = new ArrayList<PolarConfig>(Sampling.samples);

        Flag flag;

        PolarConfig currentVertex = samples.get(0);
        samples.remove(currentVertex);

        System.out.println("Start connecting edges...");

        while (!samples.isEmpty()){

            for(PolarConfig each : samples){
                flag = new Flag(true);//reset the flag

                if(Point2D.distance(currentVertex.getPoint().getX(),currentVertex.getPoint().getY(),each.getPoint().getX(),each.getPoint().getY())<=MAX_RADIUS){//if others are inside this circle
                    EdgeCollision.isEdgeCollisionFree(ps,currentVertex,each,numASV,flag);
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
        if(numASV>10) MAX_RADIUS_FOR_START_END=3;
        ArrayList<PolarConfig> samples = new ArrayList<PolarConfig>(Sampling.samples);

        Flag flag;

        PolarConfig currentVertex = startPolar;

        for(int i=0;i<2;i++){
            for(PolarConfig each : samples){
                flag = new Flag(true);//reset the flag

                if(Point2D.distance(currentVertex.getPoint().getX(),currentVertex.getPoint().getY(),each.getPoint().getX(),each.getPoint().getY())<=MAX_RADIUS_FOR_START_END){//if others are inside this circle
                    EdgeCollision.isEdgeCollisionFree(ps,currentVertex,each,numASV,flag);//this is good





                    if(flag.getFlag()){//if the other point is collision free
                        AdjacencyList.addEdge(currentVertex,each);
                    }
                }
            }
            currentVertex=endPolar;
        }
    }


}
