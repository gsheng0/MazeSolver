package util;

import java.awt.event.MouseEvent;

public class Point {
    public final int x, y;
    public static final Point NULL_LOCATION = new Point(-914323342, -1434346692);
    public static Point extract_point(MouseEvent e){
        return new Point(e.getX(), e.getY());
    }
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
    public Point add(int x, int y){
        return new Point(this.x + x, this.y + y);
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
