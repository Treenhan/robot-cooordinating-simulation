package solution;

import problem.ASVConfig;
import problem.ProblemSpec;
import tester.Tester;

/**
 * Created by Treenhan on 9/13/17.
 */
public class EdgeCollision {
    public static final double BOOM_LENGTH = 0.05;
    public static final double MIN_DISTANCE = 0.01;

    private static Tester tester = new Tester();

    /**
     * check for collision between 2 points
     * @param ps
     * @param startConfig
     * @param endConfig
     *
     * @param numASV
     * @return true if there is no collision
     */
    public static void isEdgeCollisionFree(ProblemSpec ps, PolarConfig startConfig, PolarConfig endConfig, int numASV, Flag flag){
        if(!flag.getFlag()) return;
        else {
            PolarConfig midConfig = PolarConfig.getMidPolarConfig(startConfig, endConfig, numASV);

            if (PolarConfig.distance(endConfig, midConfig, numASV) <= MIN_DISTANCE) return;//no collision

            //check for collision
            ASVConfig asvConfig = midConfig.convertToRec();
            if (tester.hasCollision(asvConfig, ps.getObstacles()) || !tester.hasEnoughArea(asvConfig) || !tester.isConvex(asvConfig) || !tester.fitsBounds(asvConfig)) {
                flag.setFlag(false);
            }

            isEdgeCollisionFree(ps, startConfig, midConfig,numASV,flag);
            isEdgeCollisionFree(ps, midConfig, endConfig,numASV,flag);
            return;
        }
    }


}
