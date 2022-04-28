import util.Point;

public class Ball {
    private Point location;
    private Point velocity;
    public static final int RADIUS = 10;
    public Ball(Point location, Point velocity){
        this.location = location;
        this.velocity = velocity;
    }
    public Point getLocation(){ return location; }
    public void move(){
        location = Point.add(location, velocity);
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

}
