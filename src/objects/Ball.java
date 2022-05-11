package objects;

import util.Edge;
import util.Point;
import util.Util;

import java.util.ArrayList;

public class Ball {
    private Point location;
    private Point velocity;
    public static final int RADIUS = 10;
    private int framesUntilNextCollision = -1;
    private int collisionOrientation = Edge.DIAGONAL;
    private Point nextCollision = Point.NULL_LOCATION;
    public final Point startingVelocity;
    public Ball(Point location, Point velocity){
        this.location = location;
        this.velocity = velocity;
        this.startingVelocity = velocity;
    }
    public Point getLocation(){ return location; }
    public void move(ArrayList<Edge> edges, int wall_width){
        location = Point.add(location, velocity);
        if(framesUntilNextCollision > 0) {
            framesUntilNextCollision--;
            //System.out.println("Frame counter: " + framesUntilNextCollision);
        }
        else if(framesUntilNextCollision == 0){
            if(collisionOrientation == Edge.HORIZONTAL){
                hitHorizontalWall();
                calculateNextCollision(edges, wall_width);
            }
            else if(collisionOrientation == Edge.VERTICAL){
                hitVerticalWall();
                calculateNextCollision(edges, wall_width);
            }
            else if(collisionOrientation == Edge.DIAGONAL){
                hitVerticalWall();
                hitHorizontalWall();
                calculateNextCollision(edges, wall_width);
            }
        }
        else {
            calculateNextCollision(edges, wall_width);
        }


    }
    public Point getNextCollisionPoint() { return nextCollision; }
    public int getOrientationOfCollision() { return collisionOrientation; }
    public int getFramesUntilNextCollision() { return framesUntilNextCollision; }
    public void hitVerticalWall(){
        velocity = new Point(velocity.double_x * -1, velocity.double_y);
    }
    public void hitHorizontalWall(){
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
            for(Edge edge1 : edgesToCheck){
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
            if(distance < minDistance && distance > RADIUS){
                minDistance = distance;
                closest = intersection;
                collisionOrientation = edge.orientation;
            }
        }
        if(closest == Point.NULL_LOCATION){
            framesUntilNextCollision = -1;
            return;
        }
        //calculating number of frames to adjust for
        double velocityMagnitude = Math.sqrt(velocity.double_x * velocity.double_x + velocity.double_y * velocity.double_y);
        int adjusted = (int)(RADIUS / velocityMagnitude);

        nextCollision = closest;
        if(velocity.double_x != 0) {
            framesUntilNextCollision = (int)((closest.double_x - location.double_x) / velocity.double_x) - adjusted;
        }
        else if(velocity.double_y != 0){
            framesUntilNextCollision = (int)((closest.double_y - location.double_y) / velocity.double_y) - adjusted;
        }
        else{
            framesUntilNextCollision = -1;
            nextCollision = Point.NULL_LOCATION;
        }
    }
    public static Point generateVelocity(double magnitude){
        double x_comp = (Math.random() * 2.0 * magnitude) - magnitude;
        double y_comp = Math.sqrt(magnitude * magnitude - x_comp * x_comp);
        return new Point(x_comp, y_comp);
    }
}
