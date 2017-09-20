package solution;

import problem.ProblemSpec;

import java.util.ArrayList;


/**
 * Created by Treenhan on 9/9/17.
 */
public class Main {
    public static void main(String [] args) throws java.io.IOException {
        ProblemSpec problemSpec = new ProblemSpec();
        problemSpec.loadProblem("testcases/3ASV-x4.txt");//all configurations are in here now

        Sampling.startSampling(problemSpec,problemSpec.getASVCount());//start sampling
        Sampling.startStraightSampling(problemSpec,problemSpec.getASVCount());



        //Sampling.startEdgeSampling(problemSpec,problemSpec.getASVCount());

       // Sampling.startEdgeSampling(problemSpec,problemSpec.getASVCount());//start edge sampling

        EdgeConnection.connectEdges(problemSpec,problemSpec.getASVCount());//connect edges between samplesx4


//        PolarConfig initialPolar = problemSpec.getInitialState().convertToPolar();
 //       PolarConfig goalPolar = problemSpec.getGoalState().convertToPolar();

        ArrayList<Double> list = new ArrayList<>();
        list.add(0.8);
        list.add(0.8);
        list.add(2.8);
        list.add(-2.8);
        PolarConfig initialPolar = new PolarConfig(list,problemSpec.getASVCount());

        initialPolar.convertToRec();

        ArrayList<Double> list2 = new ArrayList<>();
        list2.add(0.8);
        list2.add(0.8);
        list2.add(2.5);
        list2.add(3.1);
        PolarConfig goalPolar = new PolarConfig(list2,problemSpec.getASVCount());


        //add start and end point to the graph
        EdgeConnection.connectEdgesForTargets(problemSpec,problemSpec.getASVCount(),initialPolar,goalPolar);

        Search search = new Search(initialPolar,goalPolar,problemSpec.getASVCount());
        search.startBFSSearch();

        //start searching

        System.out.println("DONE");


    /*
    then use hasCollision() to check your configuration with an obstacle
     */
    }

}
