import util.Point;

public class Ball {
    private Point location;
    private Point velocity;
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

}
