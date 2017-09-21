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
    public static final double SAMPLES_NUM = 2000;
    public static final double COLLISION_SAMPLES_NUM = 5 ;
    public static final double COLLISION_SAMPLING = 0.03;
    public static final double STRAIGHT_SAMPLES_NUM = 2000 ;

    public static List<PolarConfig> samples = new ArrayList<>();


    public static void startSampling(ProblemSpec ps, int asv) {
        Point2D randomInitPoint;
        PolarConfig polarConfig;
        ASVConfig asvConfig;
        Tester tester = new Tester();

        int counter=0;//debug

        for (int i = 0; i < SAMPLES_NUM; i++) {//sampling the configurations
            boolean flag = true;

            while (flag) {//sample the point until it has no collision

                randomInitPoint = new Point2D.Double(Math.random(), Math.random());//random a first ASV

                List<Double> angles = new ArrayList();//create a list of angles

                for (int j = 0; j < asv - 1; j++) {
                    double angle = Math.random() * 2 * Math.PI - Math.PI; //random an angle
                    angles.add(angle);//put it in the list
                }

                polarConfig = new PolarConfig(randomInitPoint, angles);//create a polar config
                asvConfig = polarConfig.convertToRec();//convert to rec config

                if (!tester.hasCollision(asvConfig, ps.getObstacles()) && tester.hasEnoughArea(asvConfig) && tester.isConvex(asvConfig) && tester.fitsBounds(asvConfig)) {
                    samples.add(polarConfig);
                    flag = false;

                    //debug
                    System.out.println("add sample number: "+counter++);
                }

            }
        }

    }

    public static void startStraightSampling(ProblemSpec ps, int asv) {
        PolarConfig polarConfig;
        ASVConfig asvConfig;
        Tester tester = new Tester();

        double additionalAngle = decideAdditionalAngle(asv);
        int counter=0;//debug

        for (int i = 0; i < STRAIGHT_SAMPLES_NUM; i++) {//sampling the configurations
            boolean flag = true;

            while (flag) {
                double angle = Math.random() * 2 * Math.PI - Math.PI; //random an angle

                polarConfig = straightConfigSampling(angle, asv, ps,additionalAngle);//create a polar config
                asvConfig = polarConfig.convertToRec();//convert to rec config

                if (!tester.hasCollision(asvConfig, ps.getObstacles()) && tester.hasEnoughArea(asvConfig) && tester.isConvex(asvConfig) && tester.fitsBounds(asvConfig)) {
                    samples.add(polarConfig);
                    flag = false;

                    //debug
                    System.out.println("add straight sample number: " + counter++);
                }

            }

        }

    }

    public static PolarConfig straightConfigSampling(double mainAngle,int asv,ProblemSpec ps,double addAngle){
        Point2D randomInitPoint;

        double additionalAngle=addAngle;

                randomInitPoint = new Point2D.Double(Math.random(), Math.random());//random a first ASV

                List<Double> angles = new ArrayList();//create a list of angles

                    for (int j = 0; j < asv - 1; j++) {
                        if (j == 0){
                            angles.add(PolarConfig.boundAngle(mainAngle - additionalAngle));
                        }
                        else if (j == asv - 2) {
                            angles.add(PolarConfig.boundAngle(mainAngle + additionalAngle));
                        }
                        else angles.add(mainAngle);
                    }

                return new PolarConfig(randomInitPoint, angles);//create a polar config

    }

    public static double decideAdditionalAngle(int asv){
        Tester tester = new Tester();
        double returnAngle=0.2;
        double mainAngle=0;
        Point2D randomInitPoint = new Point2D.Double(Math.random(), Math.random());//random a first ASV
        List<Double> angles;
        do {
            returnAngle+=0.01;
            angles = new ArrayList();//create a list of angles
            for (int j = 0; j < asv - 1; j++) {
                if (j == 0) {
                    angles.add(PolarConfig.boundAngle(mainAngle - returnAngle));
                } else if (j == asv - 2) {
                    angles.add(PolarConfig.boundAngle(mainAngle + returnAngle));
                } else angles.add(mainAngle);
            }
        }while(!tester.hasEnoughArea((new PolarConfig(randomInitPoint,angles)).convertToRec()));

        return returnAngle;
    }
//unused

    public static void startEdgeSampling(ProblemSpec ps, int asv) {//fix here to 8 direction sampling
        Point2D randomInitPoint;
        PolarConfig polarConfig;
        ASVConfig asvConfig;
        Tester tester = new Tester();

        //number of asv
        int asvNum = asv;

        for (int i = 0; i < COLLISION_SAMPLES_NUM; i++) {//sampling the configurations
            boolean flag = true;

            randomInitPoint = new Point2D.Double(Math.random(), Math.random());//random ASV

            asvConfig = new ASVConfig(randomInitPoint);//put it in an asvConfig so that we can check collision

            while (flag) {
                if (tester.hasCollision(asvConfig, ps.getObstacles())) {//if in collision, take the next point inside 0.03 radius
                    randomInitPoint = new Point2D.Double(randomInitPoint.getX() + Math.random() * COLLISION_SAMPLING * 2 - COLLISION_SAMPLING
                            , randomInitPoint.getY() + Math.random() * COLLISION_SAMPLING * 2 - COLLISION_SAMPLING);
                    asvConfig = new ASVConfig(randomInitPoint);
                } else flag = false;//get a point near the edge
            }

            flag = true;//reset the flag

            while (flag) {//constructing the good configuration no collision
                List<Double> angles = new ArrayList();//create a list of angles

                for (int j = 0; j < asvNum - 1; j++) {
                    double angle = Math.random() * 2 * Math.PI - Math.PI; //random an angle
                    angles.add(angle);//put it in the list
                }
                polarConfig = new PolarConfig(randomInitPoint, angles);//create a polar config

                //debug
                //System.out.println("test new config near the edge");

                asvConfig = polarConfig.convertToRec();//convert to rec config

                if (!tester.hasCollision(asvConfig, ps.getObstacles()) && tester.hasEnoughArea(asvConfig) && tester.isConvex(asvConfig)) {
                    samples.add(polarConfig);
                    flag = false;

                    //debugging
                    System.out.println("add one sample near the obstacle's edge");
                }


            }
        }
    }


}
