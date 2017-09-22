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
    public static  double SAMPLES_NUM = 3000;
    public static  double STRAIGHT_SAMPLES_NUM = 2000 ;
    public static  double SQUARES_NUM = 1500 ;

    public static List<PolarConfig> samples = new ArrayList<>();


    public static void startSampling(ProblemSpec ps, int asv) {
        Point2D randomInitPoint;
        PolarConfig polarConfig;
        ASVConfig asvConfig;
        Tester tester = new Tester();

        if (asv < 10) {
            System.out.println("Start URS Sampling...");
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

                    }

                }
            }
        } else {
            System.out.println("Start SCS Sampling...");

            int edgePerSquare = 2;
            while (asv >= edgePerSquare * 4) edgePerSquare++;//decide number of edges per a square
            for (int i = 0; i < SQUARES_NUM; i++) {//sampling the configurations
                boolean flag = true;

                while (flag) {//sample the point until it has no collision

                    randomInitPoint = new Point2D.Double(Math.random(), Math.random());//random a first ASV

                    List<Double> angles = new ArrayList();//create a list of angles

                    double tempAngle = Math.random() * 2 * Math.PI - Math.PI;



                    if(i%2==0) {
                        for (int j = 0; j < asv - 1; j++) {
                            if (j % edgePerSquare == 0 && j != 0) {
                                if (Math.abs(tempAngle + Math.PI / 2) > Math.PI) {
                                    tempAngle = -Math.PI * 2 + (tempAngle + Math.PI / 2);
                                } else
                                    tempAngle += Math.PI / 2;

                            }
                            angles.add(tempAngle);
                        }
                    }else{
                        for (int j = 0; j < asv - 1; j++) {
                            if (j % edgePerSquare == 0 && j != 0) {
                                if (Math.abs(tempAngle - Math.PI / 2) > Math.PI) {
                                    tempAngle = Math.PI * 2 + (tempAngle - Math.PI / 2);
                                } else
                                    tempAngle -= Math.PI / 2;

                            }
                            angles.add(tempAngle);
                        }
                    }

                        polarConfig = new PolarConfig(randomInitPoint, angles);//create a polar config
                        asvConfig = polarConfig.convertToRec();//convert to rec config

                        if (!tester.hasCollision(asvConfig, ps.getObstacles()) && tester.hasEnoughArea(asvConfig) && tester.isConvex(asvConfig) && tester.fitsBounds(asvConfig)) {
                            samples.add(polarConfig);
                            flag = false;
                        }

                    }
                }
            }

        }




    public static void startStraightSampling(ProblemSpec ps, int asv) {
        PolarConfig polarConfig;
        ASVConfig asvConfig;
        Tester tester = new Tester();

        double additionalAngle = decideAdditionalAngle(asv);

        System.out.println("Start NCS Sampling...");

        for (int i = 0; i < STRAIGHT_SAMPLES_NUM; i++) {//sampling the configurations
            boolean flag = true;

            while (flag) {
                double angle = Math.random() * 2 * Math.PI - Math.PI; //random an angle

                polarConfig = straightConfigSampling(angle, asv, ps,additionalAngle);//create a polar config
                asvConfig = polarConfig.convertToRec();//convert to rec config

                if (!tester.hasCollision(asvConfig, ps.getObstacles()) && tester.hasEnoughArea(asvConfig) && tester.isConvex(asvConfig) && tester.fitsBounds(asvConfig)) {
                    samples.add(polarConfig);
                    flag = false;
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



}
