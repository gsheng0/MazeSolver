package util;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Listener implements MouseListener, KeyListener, MouseMotionListener {
    static Listener instance;
    static boolean created = false;
    private boolean pressed = false;
    private Point location = Point.NULL_LOCATION;
    private ArrayList<Point> list;
    private Intersection first;
    private Intersection second;

    private Listener(){}
    public static Listener getInstance(){
        if(!created){
            instance = new Listener();
            created = true;
        }
        return instance;
    }
    public void setPointList(ArrayList<Point> list){
        this.list = list;
    }

    public boolean isPressed(){
        return pressed;
    }
    public Point getLocation(){
        return location;
    }
    public void setNullLocation(){
        location = Point.NULL_LOCATION;
    }
    public void clearSelections(){
        first = null;
        second = null;
    }
    public void addSelection(Intersection intersection){
        if(first == null){
            first = intersection;
        }
        else{
            second = intersection;
        }
    }
    public Intersection getFirstSelection(){ return first;}
    public Intersection getSecondSelection(){ return second; }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse Pressed!");
        pressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            first.setPressed(false);
            clearSelections();
        }
        else if(SwingUtilities.isLeftMouseButton(e)){
            location = Point.extract_point(e);
        }
        pressed = false;

//        list.add(new Point(-1, -1));

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY() - 20;
        System.out.println(pressed);
        if(pressed) {
            System.out.println("Dragged");
            list.add(new Point(x, y));
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
