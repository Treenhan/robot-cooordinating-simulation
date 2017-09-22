package solution;

import problem.ASVConfig;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Treenhan on 9/17/17.
 */
public class Writer {
    public static final double MAX_POLAR_STEP = 0.0005;
    PrintWriter writer;
    private PolarConfig end;
    private PolarConfig start;
    private int numASV;
    public static int lineCounter=0;
    public static double pathLength=0;

    public static ArrayList<PolarConfig> path = new ArrayList<>();

    /**
     * print the output
     * @param file
     */
    public Writer(String file,PolarConfig startConfig,PolarConfig endConfig, int numASV) throws java.io.FileNotFoundException, java.io.UnsupportedEncodingException{
        writer = new PrintWriter(file, "UTF-8");
        end = endConfig;
        start = startConfig;
        this.numASV=numASV;
    }

    /**
     * only use this function when you reach the goal
     */
    public void printPath(){
        System.out.println("Start printing the solution...");
        path.add(end);

        boolean found=false;

        PolarConfig vertex = end;

        while(!found){
            if(vertex==start){
                found=true;
                continue;
            }


            path.add(Search.parents.get(vertex));
            vertex=Search.parents.get(vertex);
        }

        path=extendPath(path);

        pathLength=calculateTotalCost();
        //writing things out
        writer.println(lineCounter+" "+pathLength);
        for(int j = 0; j < path.size(); j++){

            writer.println(path.get(j).convertToRec().toString());//print the Polar out

        }

        closeWriter();
    }

    public void printRoughPath(){
        path.add(end);

        boolean found=false;

        PolarConfig vertex = end;

        while(!found){
            if(vertex==start){
                found=true;
                continue;
            }

            //debug
            System.out.println("still adding path");

            path.add(Search.parents.get(vertex));
            lineCounter++;
            vertex=Search.parents.get(vertex);
        }


        pathLength=calculateTotalCost();
        //writing things out
        writer.println(lineCounter-1+" "+pathLength);
        for(int j = 0; j < path.size(); j++){
            //debugging
            System.out.println("still printing roughly");
            writer.println(path.get(j).convertToRec().toString());//print the Polar out

        }

        closeWriter();
    }

    public void closeWriter(){
        writer.close();
    }

    public ArrayList<PolarConfig> extendPath(ArrayList<PolarConfig> path){
        ArrayList<PolarConfig> extendedPath = new ArrayList<>();

        extendedPath.add(path.get(path.size()-1));//add the first element


        for(int i=path.size()-2;i>=0;i--){
            extendedPath.addAll(PolarConfig.connectPolar(extendedPath.get(extendedPath.size()-1),path.get(i),MAX_POLAR_STEP,numASV));
            extendedPath.add(path.get(i));
            lineCounter++;
        }

        return extendedPath;
    }

    /**
     * Returns the true total cost of the currently loaded solution.
     *
     * @return the true total cost of the currently loaded solution.
     */
    public double calculateTotalCost() {
        double cost = 0;
        ASVConfig c0 = path.get(0).convertToRec();
        for (int i = 1; i < path.size(); i++) {
            ASVConfig c1 = path.get(i).convertToRec();
            cost += c0.totalDistance(c1);
            c0 = c1;
        }
        return cost;
    }

}
