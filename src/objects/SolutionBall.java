package objects;

import util.Constants;
import util.Edge;
import util.Point;

import java.util.ArrayList;

public class SolutionBall extends Ball{
    private ArrayList<Point> travelPoints;
    public SolutionBall(Ball ball){
        super(Constants.STARTING_POSITION, ball.startingVelocity);
        travelPoints = new ArrayList<>();
        travelPoints.add(Constants.STARTING_POSITION);
    }

    public ArrayList<Point> getTravelPoints() { return travelPoints; }

    @Override
    public void calculateNextCollision(ArrayList<Edge> edges, int wall_width) {
        super.calculateNextCollision(edges, wall_width);
        if(getFramesUntilNextCollision() > -1){
            travelPoints.add(getNextCollisionPoint());
        }
    }
}
