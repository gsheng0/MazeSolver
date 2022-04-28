package util;

import java.awt.event.MouseEvent;

public class Point {
    public int x, y;
    public static final Point NULL_LOCATION = new Point(-14342, -14342);
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    public String toString() { return "( " + x + ", " + y + ")"; }
    public double distanceFrom(Point other){
        int delta_x = other.x - this.x;
        int delta_y = other.y - this.y;

        return Math.sqrt(delta_x * delta_x + delta_y * delta_y);
    }
    public static Point extract_point(MouseEvent e){
        return new Point(e.getX(), e.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Point){
            Point other = (Point)obj;
            if(other.x == this.x && other.y == this.y){
                return true;
            }
            return false;
        }
        return false;
    }
}
