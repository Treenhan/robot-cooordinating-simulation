package solution;

import problem.ASVConfig;
import problem.ProblemSpec;
import tester.Tester;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Treenhan on 9/13/17.
 */
public class Sampling {
    private Tester tester = new Tester();
    public static final double BOOM_LENGTH = 0.05;
    public static final double SAMPLES_NUM = 1000;

    public static List<PolarConfig> samples = new ArrayList<>();


    public static List<PolarConfig> startSampling(ProblemSpec ps, int asv){
        Point2D randomInitPoint;
        PolarConfig polarConfig;
        ASVConfig asvConfig;
        Tester tester = new Tester();

        //number of asv
        int asvNum = asv;

        for (int i=0;i<SAMPLES_NUM;i++){//sampling the configurations
            boolean flag=true;

            while(flag) {//sample the point until it has no collision

                randomInitPoint = new Point2D.Double(Math.random(), Math.random());//random a first ASV

                List<Double> angles = new ArrayList();//create a list of angles

                for (int j = 0; j < asvNum - 1; j++) {
                    double angle = Math.random() * 2 * Math.PI - Math.PI; //random an angle
                    angles.add(angle);//put it in the list
                }

                polarConfig = new PolarConfig(randomInitPoint,angles);//create a polar config

                asvConfig = polarConfig.convertToRec();//convert to rec config

                //TODO check the area or convex
                if(!tester.hasCollision(asvConfig,ps.getObstacles()) && tester.hasEnoughArea(asvConfig) && tester.isConvex(asvConfig)){
                    samples.add(polarConfig);
                    flag = false;
                }

            }
        }
        return samples;
    }
}
