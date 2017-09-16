package solution;

import problem.ProblemSpec;


/**
 * Created by Treenhan on 9/9/17.
 */
public class Main {
    public static void main(String [] args) throws java.io.IOException {
        ProblemSpec problemSpec = new ProblemSpec();
        problemSpec.loadProblem("testcases/7ASV-easy.txt");//all configurations are in here now

        Sampling.startSampling(problemSpec,problemSpec.getASVCount());//start sampling
        EdgeConnection.connectEdges(problemSpec,problemSpec.getASVCount());//connect edges between samples

        PolarConfig initialPolar = problemSpec.getInitialState().convertToPolar();
        PolarConfig goalPolar = problemSpec.getGoalState().convertToPolar();

        //add start and end point to the graph
        EdgeConnection.connectEdgesForTargets(problemSpec,problemSpec.getASVCount(),initialPolar,goalPolar);

        Search search = new Search(initialPolar,goalPolar);
        search.startBFSSearch();

        //start searching

        System.out.println("DONE");

    /*
    then use hasCollision() to check your configuration with an obstacle
     */
    }

}
