package util;

public class Util {

    public static boolean counterClockwise(Point a, Point b, Point c){
        return (c.double_y - a.double_y) * (b.double_x - a.double_x) > (b.double_y - a.double_y) * (c.double_x - a.double_x);
    }
    //one line segment is AB, other is CD
    public static boolean intersects(Point a, Point b, Point c, Point d){
        return (counterClockwise(a, c, d) != counterClockwise(b, c, d)) && (counterClockwise(a, b, c) != counterClockwise(a, b, d));
    }
    public static double determinant(Point a, Point b){
        return a.double_x * b.double_y - a.double_y * b.double_x;
    }
    public static Point getIntersectionPoint(Point a, Point b, Point c, Point d){
        Point xDiff = new Point(a.double_x - b.double_x, c.double_x - d.double_x);
        Point yDiff = new Point(a.double_y - b.double_y, c.double_y - d.double_y);

        double determinant = determinant(xDiff, yDiff);
        if(determinant == 0){
            return Point.NULL_LOCATION;
        }
        Point det = new Point(determinant(a, b), determinant(c, d));
        return new Point(
                determinant(det, xDiff) / determinant,
                determinant(det, yDiff) / determinant);
    }
}
