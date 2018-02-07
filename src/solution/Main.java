package solution;

import problem.ASVConfig;
import problem.ProblemSpec;


/**
 * Created by Treenhan on 9/9/17.
 */
public class Main {
    public static void main(String [] args) throws java.io.IOException {
        System.out.println("---PROGRAM STARTED---\n");
        ProblemSpec problemSpec = new ProblemSpec();
        problemSpec.loadProblem(args[0]);

        Sampling.startSampling(problemSpec,problemSpec.getASVCount());//start sampling
        if(problemSpec.getASVCount()<10) Sampling.startStraightSampling(problemSpec,problemSpec.getASVCount());

        EdgeConnection.connectEdges(problemSpec,problemSpec.getASVCount());//connect edges between samples

       PolarConfig initialPolar = problemSpec.getInitialState().convertToPolar();
       PolarConfig goalPolar = problemSpec.getGoalState().convertToPolar();
//-------------------
        //FOR DEBUGGING
        //POLAR
//        ArrayList<Double> list = new ArrayList<>();
//        list.add(0.49776);
//        list.add(0.67999);
//        list.add(3.13999);
//        list.add(0.9999);
//        PolarConfig initialPolar = new PolarConfig(list,problemSpec.getASVCount());
//
//        initialPolar.convertToRec();
//
//        ArrayList<Double> list2 = new ArrayList<>();
//        list2.add(0.49778);
//        list2.add(0.68);
//        list2.add(-3.1399);
//        list2.add(1.001);
//        PolarConfig goalPolar = new PolarConfig(list2,problemSpec.getASVCount());
//        Flag flag = new Flag(false);
//        EdgeCollision.isEdgeCollisionFree(problemSpec,initialPolar,goalPolar,problemSpec.getASVCount(),flag);
//
//        PolarConfig.connectPolar(initialPolar,goalPolar,0.001,problemSpec.getASVCount());

        //ASV
//        double [] coor1 = {0.10966004631561066,0.08297332301512254,0.14784000613897133,0.05068879625869377,0.09784000613897133,0.050688796258693776};
//        ASVConfig asvStart = new ASVConfig(coor1);
//        double [] coor2 = {0.12223063448072702,0.08878862533224119,0.16653439297332379,0.06561154649495754,0.12243057687195147,0.04205622002096587};
//        ASVConfig asvEnd = new ASVConfig(coor2);
//
//        PolarConfig.connectPolar(asvStart.convertToPolar(),asvEnd.convertToPolar(),0.0005,problemSpec.getASVCount());
//
//        Flag flag = new Flag(true);

//        EdgeCollision.isEdgeCollisionFree(problemSpec,asvStart.convertToPolar(),asvEnd.convertToPolar(),problemSpec.getASVCount(),flag);

        //-----------------------------------

        //add start and end point to the graph
        EdgeConnection.connectEdgesForTargets(problemSpec,problemSpec.getASVCount(),initialPolar,goalPolar);

        Search search = new Search(initialPolar,goalPolar,problemSpec.getASVCount());

        Flag hardcoreFlag = new Flag(false);

        search.startBFSSearch(args[1],hardcoreFlag);

        if(hardcoreFlag.getFlag()){
            System.out.println("HARDCORE MODE ACTIVATED");
            Sampling.SAMPLES_NUM=1000;
            Sampling.STRAIGHT_SAMPLES_NUM=2000;
            Sampling.startSampling2(problemSpec,problemSpec.getASVCount());//start sampling
            if(problemSpec.getASVCount()<10) Sampling.startStraightSampling2(problemSpec,problemSpec.getASVCount());
            EdgeConnection.connectEdges2(problemSpec,problemSpec.getASVCount());
            EdgeConnection.connectEdgesForTargets2(problemSpec,problemSpec.getASVCount(),initialPolar,goalPolar);
            search.startBFSSearch(args[1],hardcoreFlag);
        }

        //start searching

        System.out.println("\n---PROGRAM TERMINATED---");


    /*
    then use hasCollision() to check your configuration with an obstacle
     */
    }

}
