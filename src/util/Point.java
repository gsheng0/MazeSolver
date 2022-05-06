package util;

import java.awt.event.MouseEvent;

public class Point {
    public final int x, y;
    public final double double_x, double_y;
    public static final Point NULL_LOCATION = new Point(-914323342, -1434346692);
    public static Point extract_point(MouseEvent e){
        return new Point(e.getX(), e.getY());
    }
    public static Point add(Point p1, Point p2){ return new Point(p1.double_x + p2.double_x, p1.double_y + p2.double_y); }
    public Point(int x, int y){
        this.x = x;
        this.y = y;
        this.double_x = x;
        this.double_y = y;
    }
    public Point(double x, double y){
        this.x = (int)x;
        this.y = (int)y;
        this.double_x = x;
        this.double_y = y;
    }
    public String toString() { return "(" + x + ", " + y + ")"; }
    public double distanceFrom(Point other){
        int delta_x = other.x - this.x;
        int delta_y = other.y - this.y;

        return Math.sqrt(delta_x * delta_x + delta_y * delta_y);
    }
    public Point add(int x, int y){
        return new Point(this.x + x, this.y + y);
    }
    public Point add(double x, double y){ return new Point(this.double_x + x, this.double_y + y); }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Point){
            Point other = (Point)obj;
            if(other.double_x == this.double_x && other.double_y == this.double_y){
                return true;
            }
            return false;
        }
        return false;
    }
    public Point copy(){
        return new Point(double_x, double_y);
    }



}
