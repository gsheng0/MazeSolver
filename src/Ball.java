import util.Edge;
import util.Point;
import util.Util;

import java.util.ArrayList;

public class Ball {
    private Point location;
    private Point velocity;
    public static final int RADIUS = 10;
    private int framesUntilNextCollision = -1;
    private int orientation = Edge.DIAGONAL;
    private Point nextCollision = Point.NULL_LOCATION;
    public Ball(Point location, Point velocity){
        this.location = location;
        this.velocity = velocity;
    }
    public Point getLocation(){ return location; }
    public void move(){
        location = Point.add(location, velocity);
        framesUntilNextCollision--;
    }
    public Point getNextCollisionPoint() { return nextCollision; }
    public int getOrientationOfCollision() { return orientation; }
    public int getFramesUntilNextCollision() { return framesUntilNextCollision; }
    public void move(double x, double y){
        this.location = this.location.add(x, y);
    }
    public void hitHorizontalWall(){
        velocity = new Point(velocity.double_x * -1, velocity.double_y);
    }
    public void hitVerticalWall(){
        velocity = new Point(velocity.double_x, velocity.double_y * -1);
    }
    public Point getRightmostPoint(){
        return location.add(Ball.RADIUS, 0);
    }
    public Point getLeftmostPoint(){
        return location.add(-1 * Ball.RADIUS, 0);
    }
    public Point getHighestPoint(){
        return location.add(0, -1 * Ball.RADIUS);
    }
    public Point getLowestPoint(){
        return location.add(0, Ball.RADIUS);
    }
    public void calculateNextCollision(ArrayList<Edge> edges, int wall_width){
        ArrayList<Edge> inPath = new ArrayList<>();
        Point future = this.location.add(10000.0 * velocity.double_x, 10000.0 * velocity.double_y);
        for(Edge edge : edges){
            ArrayList<Edge> edgesToCheck = new ArrayList<>();
            if(velocity.double_x > 0){
                //need to check left side
                edgesToCheck.add(edge.getLeftSide(wall_width));
            }
            else if(velocity.double_x < 0){
                //need to check right side
                edgesToCheck.add(edge.getRightSide(wall_width));
            }

            if(velocity.double_y > 0){
                //need to check upper side
                edgesToCheck.add(edge.getHigherSide(wall_width));

            }
            else if(velocity.double_y < 0){
                //need to check lower side
                edgesToCheck.add(edge.getLowerSide(wall_width));
            }

            //finding edges that will be in the path of the ball
            for(Edge edge1 : edges){
                if(Util.intersects(location, future, edge1.p1, edge1.p2)){
                    inPath.add(edge1);
                }
            }
        }
        //need to find closest edge
        Point closest = Point.NULL_LOCATION;
        double minDistance = Double.MAX_VALUE;
        for(Edge edge : inPath){
            Point intersection = Util.getIntersectionPoint(location, future, edge.p1, edge.p2);
            double distance = intersection.distanceFrom(location);
            if(distance < minDistance){
                minDistance = distance;
                closest = intersection;
                orientation = edge.orientation;
            }
        }

        nextCollision = closest;

        if(velocity.x != 0) {
            framesUntilNextCollision = (closest.x - location.x) / velocity.x;
        }
        else if(velocity.y != 0){
            framesUntilNextCollision = (closest.y - location.y) / velocity.y;
        }
        else{
            framesUntilNextCollision = -1;
            nextCollision = Point.NULL_LOCATION;
        }
    }
}
