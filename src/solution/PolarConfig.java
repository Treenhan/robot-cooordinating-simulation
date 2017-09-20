package solution;

import problem.ASVConfig;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Treenhan on 9/12/17.
 */
public class PolarConfig {
    private static final double BOOM_LENGTH = 0.05;

    private Point2D point ;
    private List<Double> angles = new ArrayList();

    /**
     * add 2 polarConfig together
     * @param b
     * @param numASV
     */
    public PolarConfig add(PolarConfig b,int numASV){
        Point2D bPoint = b.getPoint();

        List<Double> bAngles = b.getAngles();

        List<Double> newAngles = new ArrayList<Double>();

        Point2D newPoint = new Point2D.Double(bPoint.getX()+point.getX(),bPoint.getY()+point.getY());

        for(int i=0;i<numASV-1;i++){
            angles.add(bAngles.get(i)+angles.get(i));//plus and divide by 2
        }

        return new PolarConfig(newPoint,newAngles);
    }

    /**
     * return all polar information in form of an array
     */
    public ArrayList<Double> getArray(){
        ArrayList<Double> list = new ArrayList<>();

        list.add(point.getX());
        list.add(point.getY());

        for(Double each : angles){
            list.add(each);
        }

        return list;
    }

    public static double boundAngle(double angle){
        if(angle>Math.PI) {
            angle -= 2 * Math.PI;
            return angle;
        }
        else if(angle<-Math.PI) {
            angle+=2*Math.PI;
            return angle;
        }else return angle;

    }

    /**
     * constructor to creat a polar config based on an array
     */
    public PolarConfig(ArrayList<Double> list,int numASV){
        this.point = new Point2D.Double(list.get(0),list.get(1));

        for(int i=2;i<numASV+1;i++){
            this.angles.add(list.get(i));
        }
    }

    /**
     * get the middle point between 2 configs
     */
    public static PolarConfig getMidPolarConfig(PolarConfig a, PolarConfig b,int numASV){
        Point2D aPoint = a.getPoint();
        Point2D bPoint = b.getPoint();

        List<Double> aAngles = a.getAngles();
        List<Double> bAngles = b.getAngles();

        List<Double> angles = new ArrayList<Double>();

        Point2D point = new Point2D.Double((bPoint.getX()+aPoint.getX())/2,(bPoint.getY()+aPoint.getY())/2);

        for(int i=0;i<numASV-1;i++){
            angles.add((bAngles.get(i)+aAngles.get(i))/2);//plus and divide by 2
        }

        return new PolarConfig(point,angles);
    }

    /**
     * return the distance between 2 polar config
     */
    public static double distance(PolarConfig a, PolarConfig b,int numASV){
        Point2D aPoint = a.getPoint();
        Point2D bPoint = b.getPoint();

        List<Double> aAngles = a.getAngles();
        List<Double> bAngles = b.getAngles();

        double xSquared = Math.pow(bPoint.getX() - aPoint.getX(),2);
        double ySquared = Math.pow(bPoint.getY() - aPoint.getY(),2);

        double angleSquared=0;
        for(int i=0;i<numASV-1;i++){
            angleSquared += Math.pow(bAngles.get(i)-aAngles.get(i),2);
        }

        double distance = Math.sqrt(xSquared+ySquared+angleSquared);
        return distance;
    }

    /**
     * constructor for this polar config includes the first point and the list of angles
     * @param point
     * @param angles
     */
    public PolarConfig(Point2D point, List<Double> angles){
        this.point = point;
        this.angles = angles;
    }

    /**
     * get the initial point out
     * @return initial 2d point
     */
    Point2D getPoint() {return point;}

    /**
     * get the angles list
     * @return angles list
     */
    List<Double> getAngles() {return angles;}

    /**
     * convert polar coordinates to cartesian coordinates
     * @return list of points
     */
    ASVConfig convertToRec(){
        List<Point2D> asvPositions = new ArrayList<Point2D>();
        Point2D initPoint = new Point2D.Double(0,0);

        for(int i=0;i< angles.size();i++){
            double xOffset;
            double yOffset;


            Point2D newPoint;

            if(i==0) {
                initPoint = point;//initialize it for the first time
                asvPositions.add(point);//add the first point in
            }


            //go through each polar point:

            //get the x offset
            xOffset = Math.cos(angles.get(i))*BOOM_LENGTH;

            //get the y offset
            yOffset = Math.sin(angles.get(i))*BOOM_LENGTH;

            //update the new point
            newPoint = new Point2D.Double(initPoint.getX()+xOffset,initPoint.getY()+yOffset);
            asvPositions.add(newPoint);

            //update the point for the next iteration
            initPoint = newPoint;


        }

        return new ASVConfig(asvPositions);
    }

    /**
     * this function connect to point in the Polar Coordinates to print out for the result
     * @param startPolar
     * @param endPolar
     * @param maxAngleStep
     * @param numASV
     * @return
     */
    public static ArrayList<PolarConfig> connectPolar(PolarConfig startPolar, PolarConfig endPolar,double maxAngleStep,int numASV){

        ArrayList<Double> start = new ArrayList<>();
        ArrayList<Double> end = new ArrayList<>();
        ArrayList<Double> delta = new ArrayList<>();
        ArrayList<Double> temp = new ArrayList<>();

        ArrayList<PolarConfig> conncectingPoints = new ArrayList<>();//this will be returned

        //add initial & goal coordinates
        start=startPolar.getArray();
        end=endPolar.getArray();

        //fill the delta array
        for(int i=0;i<start.size();i++){
            delta.add(end.get(i)-start.get(i));
        }

        temp=incrementArrays(start,delta,maxAngleStep);
        while(distance(new PolarConfig(temp,numASV),endPolar,numASV)>maxAngleStep){
            conncectingPoints.add(new PolarConfig(temp,numASV));
            Writer.lineCounter++;
            temp=incrementArrays(temp,delta,maxAngleStep);
        }

        return conncectingPoints;
    }


    /**
     * this function will increment an array using a portion of delta
     * @param start
     * @param delta
     * @param step
     * @return
     */
    public static ArrayList<Double> incrementArrays(ArrayList<Double>start,ArrayList<Double>delta,double step) {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < start.size(); i++) {
            list.add(start.get(i) + delta.get(i) * step);

        }
        return list;
    }

}





