package util;

public class Util {

    public static boolean counterClockwise(Point a, Point b, Point c){
        return (c.double_y - a.double_y) * (b.double_x - a.double_x) > (b.double_y - a.double_y) * (c.double_x - a.double_x);
    }
    //one line segment is AB, other is CD
    public static boolean intersects(Point a, Point b, Point c, Point d){
        return (counterClockwise(a, c, d) != counterClockwise(b, c, d)) && (counterClockwise(a, b, c) != counterClockwise(a, b, d));
    }
}
