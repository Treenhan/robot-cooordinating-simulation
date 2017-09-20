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
    public static final double SAMPLES_NUM = 6000;
    public static final double COLLISION_SAMPLES_NUM = 5 ;
    public static final double COLLISION_SAMPLING = 0.03;
    public static final double STRAIGHT_SAMPLES_NUM = 500 ;

    public static List<PolarConfig> samples = new ArrayList<>();


    public static void startSampling(ProblemSpec ps, int asv) {
        Point2D randomInitPoint;
        PolarConfig polarConfig;
        ASVConfig asvConfig;
        Tester tester = new Tester();

        int counter=0;//debug

        //number of asv
        int asvNum = asv;

        for (int i = 0; i < SAMPLES_NUM; i++) {//sampling the configurations
            boolean flag = true;

            while (flag) {//sample the point until it has no collision

                randomInitPoint = new Point2D.Double(Math.random(), Math.random());//random a first ASV

                List<Double> angles = new ArrayList();//create a list of angles

                for (int j = 0; j < asvNum - 1; j++) {
                    double angle = Math.random() * 2 * Math.PI - Math.PI; //random an angle
                    angles.add(angle);//put it in the list
                }

                polarConfig = new PolarConfig(randomInitPoint, angles);//create a polar config

                asvConfig = polarConfig.convertToRec();//convert to rec config

                //TODO check the area or convex
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

        int counter=0;//debug

        //number of asv
        int asvNum = asv;


        for (int i = 0; i < 1000; i++) {//sampling the configurations
            boolean flag = true;

            while (flag) {//sample the point until it has no collision


                polarConfig = straightConfigSamplingSpecial2(Math.PI,asv,ps);//create a polar config


                asvConfig = polarConfig.convertToRec();//convert to rec config

                //TODO check the area or convex
                if (!tester.hasCollision(asvConfig, ps.getObstacles()) && tester.hasEnoughArea(asvConfig) && tester.isConvex(asvConfig) && tester.fitsBounds(asvConfig)) {
                    samples.add(polarConfig);
                    flag = false;

                    //debug
                    System.out.println("add straight ample number: "+counter++);
                }

            }
        }

        for (int i = 0; i < 1000; i++) {//sampling the configurations
            boolean flag = true;

            while (flag) {//sample the point until it has no collision


                polarConfig = straightConfigSamplingSpecial4(0,asv,ps);//create a polar config


                asvConfig = polarConfig.convertToRec();//convert to rec config

                //TODO check the area or convex
                if (!tester.hasCollision(asvConfig, ps.getObstacles()) && tester.hasEnoughArea(asvConfig) && tester.isConvex(asvConfig) && tester.fitsBounds(asvConfig)) {
                    samples.add(polarConfig);
                    flag = false;

                    //debug
                    System.out.println("add straight ample number: "+counter++);
                }

            }
        }
        for (int i = 0; i < STRAIGHT_SAMPLES_NUM; i++) {//sampling the configurations
            for(int j=0;j<=10;j++) {
            boolean flag = true;

                while (flag) {//sample the point until it has no collision

                    double angle = 0.1*j * 2 * Math.PI - Math.PI; //random an angle
                    polarConfig = straightConfigSampling(angle, asv, ps);//create a polar config


                    asvConfig = polarConfig.convertToRec();//convert to rec config

                    //TODO check the area or convex
                    if (!tester.hasCollision(asvConfig, ps.getObstacles()) && tester.hasEnoughArea(asvConfig) && tester.isConvex(asvConfig) && tester.fitsBounds(asvConfig)) {
                        samples.add(polarConfig);
                        flag = false;

                        //debug
                        System.out.println("add straight sample number: " + counter++);
                    }

                }
            }
        }

    }


    public static PolarConfig straightConfigSamplingSpecial2(double mainAngle,int asv,ProblemSpec ps){
        Point2D randomInitPoint;

        double additinalAngle=0.3;

        randomInitPoint = new Point2D.Double(Math.random()*(0.9-0.1)+0.1, 0.481);//exact point in the hall

        List<Double> angles = new ArrayList();//create a list of angles

        for (int j = 0; j < asv - 1; j++) {
            if (j == 0) angles.add(mainAngle - additinalAngle);
            else if (j == asv - 2) angles.add(-mainAngle + additinalAngle);
            else angles.add(mainAngle);
        }

        return new PolarConfig(randomInitPoint, angles);//create a polar config

    }

    public static PolarConfig straightConfigSamplingSpecial4(double mainAngle,int asv,ProblemSpec ps){
        Point2D randomInitPoint;

        double additinalAngle=0.3;

        randomInitPoint = new Point2D.Double(Math.random()*(0.9-0.1)+0.1, 0.518);//exact point in the hall

        List<Double> angles = new ArrayList();//create a list of angles

        for (int j = 0; j < asv - 1; j++) {
            if (j == 0) angles.add(-mainAngle - additinalAngle);
            else if (j == asv - 2) angles.add(mainAngle + additinalAngle);
            else angles.add(mainAngle);
        }

        return new PolarConfig(randomInitPoint, angles);//create a polar config

    }

    public static PolarConfig straightConfigSampling(double mainAngle,int asv,ProblemSpec ps){
        Point2D randomInitPoint;

        double additinalAngle=0.3;

                randomInitPoint = new Point2D.Double(Math.random(), Math.random());//random a first ASV

                List<Double> angles = new ArrayList();//create a list of angles

                    for (int j = 0; j < asv - 1; j++) {
                        if (j == 0){
                            angles.add(PolarConfig.boundAngle(mainAngle - additinalAngle));
                        }
                        else if (j == asv - 2) {
                            angles.add(PolarConfig.boundAngle(mainAngle + additinalAngle));
                        }
                        else angles.add(mainAngle);
                    }

                return new PolarConfig(randomInitPoint, angles);//create a polar config

    }



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

    /**
     * this function construct a sample step by step
     */
    public static void stepConfigConstruction(ProblemSpec ps, Point2D initPoint, int numASV){//TODO
        Point2D point=initPoint;
        PolarConfig polarConfig;
        ASVConfig asvConfig;

        Tester tester = new Tester();
        List<Double> angles = new ArrayList();//create a list of angles

        Point2D tempPoint=initPoint;
        PolarConfig tempPolarConfig;
        ASVConfig tempASVConfig;
        List<Double> tempAngles = new ArrayList();

        //number of asv
        int asvNum = numASV;

        for(int i=0;i<asvNum-1;i++){
            double angle = singleStepConfigConstruction(ps,tempPoint);

            angles.add(angle);

            tempAngles.add(angle);
            tempPolarConfig = new PolarConfig(tempPoint,tempAngles);
            tempASVConfig=tempPolarConfig.convertToRec();
            tempPoint=tempASVConfig.getPosition(1);

        }

        polarConfig = new PolarConfig(point,angles);
        asvConfig = polarConfig.convertToRec();

        if (!tester.hasCollision(asvConfig, ps.getObstacles()) && tester.hasEnoughArea(asvConfig) && tester.isConvex(asvConfig)) {
            samples.add(polarConfig);
        }

    }

    public static double singleStepConfigConstruction(ProblemSpec ps, Point2D initPoint){//TODO
        PolarConfig polarConfig;
        ASVConfig asvConfig;
        Tester tester = new Tester();
        List<Double> angles = new ArrayList();//create a list of angles

        double angle = Math.random() * 2 * Math.PI - Math.PI; //random an angle

        angles.add(angle);

        polarConfig = new PolarConfig(initPoint,angles);

        asvConfig = polarConfig.convertToRec();

        while(tester.hasCollision(asvConfig, ps.getObstacles())){
            angle = Math.random() * 2 * Math.PI - Math.PI; //random an angle
            angles.clear();
            angles.add(angle);
            polarConfig = new PolarConfig(initPoint,angles);
            asvConfig = polarConfig.convertToRec();
        }

        return angle;
    }
}
