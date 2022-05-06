package util;

public class Edge {
    public final Point p1, p2;
    public final int orientation;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int DIAGONAL = 2;
    public Edge(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        if(p1.x == p2.x){
            orientation = VERTICAL;
        }
        else if(p1.y == p2.y){
            orientation = HORIZONTAL;
        }
        else{
            orientation = DIAGONAL;
        }
    }
    public Point getRightmostPoint(){ return p1.x > p2.x ? p1 : p2; }
    public Point getLeftmostPoint(){ return p1.x < p2.x ? p1 : p2; }
    public Point getHigherPoint(){ return p1.y < p2.y ? p1 : p2; }
    public Point getLowerPoint(){ return p1.y > p2.y ? p1 : p2; }
    public Edge copy(){
        return new Edge(p1.copy(), p2.copy());
    }
    //lines are drawn with the center at the specified coordinates
    public Edge getRightSide(int wall_width){
        //add half of wall width to x coordinates
        if(orientation == VERTICAL) {
            return new Edge(p1.add(wall_width / 2.0, 0.0), p2.add(wall_width / 2.0, 0.0));
        }
        else if(orientation == HORIZONTAL){
            //finding the rightmost point
            Point rightmostPoint = this.getRightmostPoint();
            return new Edge(rightmostPoint.add(0, wall_width/2.0), p2.add(0, wall_width/-2.0));
        }
        else{
            return this.copy();
        }
    }
    public Edge getLeftSide(int wall_width){
        if(orientation == VERTICAL) {
            return new Edge(p1.add(wall_width / -2.0, 0.0), p2.add(wall_width / -2.0, 0.0));
        }
        else if(orientation == HORIZONTAL){
            Point leftmostPoint = this.getLeftmostPoint();
            return new Edge(leftmostPoint.add(0, wall_width/2.0), p2.add(0, wall_width/-2.0));
        }
        else{
            return this.copy();
        }
    }
    public Edge getHigherSide(int wall_width){
        if(orientation == HORIZONTAL){
            return new Edge(p1.add(0.0, wall_width/-2.0), p2.add(0.0, wall_width/-2.0));
        }
        else if(orientation == VERTICAL){
            Point higherPoint = getHigherPoint();
            return new Edge(higherPoint.add(wall_width/-2.0, 0.0), higherPoint.add(wall_width/2.0, 00));
        }
        else{
            return this.copy();
        }
    }
    public Edge getLowerSide(int wall_width){
        if(orientation == HORIZONTAL){
            return new Edge(p1.add(0.0, wall_width/2.0), p2.add(0.0, wall_width/2.0));
        }
        else if(orientation == VERTICAL){
            Point lowerPoint = getLowerPoint();
            return new Edge(lowerPoint.add(wall_width/-2.0, 0.0), lowerPoint.add(wall_width/2.0, 0.0));
        }
        else{
            return this.copy();
        }
    }

}
