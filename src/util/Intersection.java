package util;

public class Intersection {
    private Point point;
    private boolean pressed = false;
    public Intersection(Point point){
        this.point = point;
    }
    public void setPressed(boolean in){ this.pressed = in; }
    public Point getLocation() { return point; }
    public boolean isPressed() { return pressed; }
}
