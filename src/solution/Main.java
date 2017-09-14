package solution;

import problem.ASVConfig;
import problem.ProblemSpec;
import tester.Tester;

import java.awt.*;
import java.awt.geom.Point2D;
import java.lang.Math;
import java.util.*;
import java.util.List;


/**
 * Created by Treenhan on 9/9/17.
 */
public class Main {
    public static void main(String [] args) throws java.io.IOException {
        ProblemSpec problemSpec = new ProblemSpec();
        problemSpec.loadProblem("testcases/3ASV-easy.txt");//all configurations are in here now

        Sampling.startSampling(problemSpec,problemSpec.getASVCount());//start sampling
        EdgeConnection.connectEdges(problemSpec,problemSpec.getASVCount());//connect edges
        System.out.println("DONE");

    /*
    then use hasCollision() to check your configuration with an obstacle
     */
    }

}
