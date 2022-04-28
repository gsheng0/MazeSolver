import javafx.scene.input.KeyCode;
import util.Point;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Listener implements MouseListener, KeyListener, MouseMotionListener {
    static Listener instance;
    static boolean created = false;
    private boolean pressed = false;
    private Point lastClicked = Point.NULL_LOCATION;
    private Point currentLocation = Point.NULL_LOCATION;
    private int button = -1;
    public static final int LEFT_MOUSE_BUTTON = 1;
    public static final int RIGHT_MOUSE_BUTTON = 2;
    public boolean leftArrow, rightArrow, downArrow, upArrow;
    public Ball ball;
    private ArrayList<Point> list;

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
    public Point getLastClicked(){
        return lastClicked;
    }
    public Point getCurrentLocation() { return currentLocation; }
    public int getButton() { return button; }
    public void clearLastClicked(){
        lastClicked = Point.NULL_LOCATION;
        button = 0;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_LEFT){
            leftArrow = true;
        }
        else if(code == KeyEvent.VK_RIGHT){
            rightArrow = true;
        }
        else if(code == KeyEvent.VK_DOWN){
            downArrow = true;
        }
        else if(code == KeyEvent.VK_UP){
            upArrow = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_LEFT){
            leftArrow = false;
        }
        else if(code == KeyEvent.VK_RIGHT){
            rightArrow = false;
        }
        else if(code == KeyEvent.VK_DOWN){
            downArrow = false;
        }
        else if(code == KeyEvent.VK_UP){
            upArrow = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        lastClicked = Point.extract_point(e);
        if(SwingUtilities.isRightMouseButton(e)){
            button = RIGHT_MOUSE_BUTTON;
        }
        else if(SwingUtilities.isLeftMouseButton(e)){
            button = LEFT_MOUSE_BUTTON;
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
        currentLocation = Point.extract_point(e);
//        int x = e.getX();
//        int y = e.getY() - 30;
//        if(pressed) {
//            list.add(new Point(x, y));
//        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        currentLocation = Point.extract_point(e);
    }
}
