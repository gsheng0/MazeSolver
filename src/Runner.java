import util.Intersection;
import util.Listener;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

import util.Point;

public class Runner extends JPanel {
    private JFrame frame;
    private Listener listener;
    private ArrayList<Point> list = new ArrayList<>();
    private Intersection[][] intersections;
    static final int WINDOW_WIDTH = 1100;
    static final int WINDOW_HEIGHT = 800;
    static final int MARGIN_SIZE = 100;
    static final int NUM_HORIZONTAL_INTERSECTIONS = 10;
    static final int NUM_VERTICAL_INTERSECTIONS = 7;
    static int CELL_WIDTH = (WINDOW_WIDTH - 2 * MARGIN_SIZE) / NUM_HORIZONTAL_INTERSECTIONS;
    static int CELL_HEIGHT = (WINDOW_HEIGHT - 2 * MARGIN_SIZE) / NUM_VERTICAL_INTERSECTIONS;
    static final int SMALL_CIRCLE_HIT_BOX_SIZE = 40;
    static final int SMALL_CIRCLE_RADIUS = 5;

    public Runner(){
        listener = Listener.getInstance();
        listener.setPointList(list);
        frame = new JFrame();
        frame.add(this);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setVisible(true);
        frame.setLocation(250, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addMouseListener(listener);
        frame.addKeyListener(listener);
        frame.addMouseMotionListener(listener);

        intersections = new Intersection[NUM_HORIZONTAL_INTERSECTIONS + 1][NUM_VERTICAL_INTERSECTIONS + 1];
        for(int x = 0; x <= NUM_HORIZONTAL_INTERSECTIONS; x++){
            for(int y = 0; y <= NUM_VERTICAL_INTERSECTIONS; y++){
                intersections[x][y] = new Intersection(new Point(MARGIN_SIZE + x * CELL_WIDTH, MARGIN_SIZE + y * CELL_HEIGHT));
            }
        }

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //handling actions
        if(!listener.getLocation().equals(Point.NULL_LOCATION)){
            //if the previously released coordinates is not the null location
            updateIntersections(listener.getLocation());
            listener.setNullLocation();

        }





        //actual graphics
        g.setColor(Color.BLACK);
        g.drawRect(MARGIN_SIZE, MARGIN_SIZE, WINDOW_WIDTH - 2 * MARGIN_SIZE, WINDOW_HEIGHT - 2 * MARGIN_SIZE);
        for(Intersection[] row : intersections){
            for(Intersection intersection : row) {
                int x = intersection.getLocation().x;
                int y = intersection.getLocation().y;
                if (intersection.isPressed()) {
                    g.fillOval(x - SMALL_CIRCLE_RADIUS, y - SMALL_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS * 2, SMALL_CIRCLE_RADIUS * 2);
                }
                else{
                    g.drawOval(x - SMALL_CIRCLE_RADIUS, y - SMALL_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS * 2, SMALL_CIRCLE_RADIUS * 2);
                }
                g.drawOval(x - SMALL_CIRCLE_HIT_BOX_SIZE/2, y - SMALL_CIRCLE_HIT_BOX_SIZE/2, SMALL_CIRCLE_HIT_BOX_SIZE, SMALL_CIRCLE_HIT_BOX_SIZE);
            }
        }



        repaint();
    }
    public void updateIntersections(Point location){
        location.y -= 30;
        int x = location.x;
        int y = location.y;

        x -= MARGIN_SIZE;
        y -= MARGIN_SIZE;
        double precise_x = ((double)x)/CELL_WIDTH;
        double precise_y = ((double)y)/CELL_HEIGHT;
        x = (int) Math.round(precise_x);
        y = (int) Math.round(precise_y);
        if(x < 0 || x > intersections.length - 1 || y < 0 || y > intersections[0].length - 1){
            return;
        }
        Intersection closest = intersections[x][y];
        System.out.println("Closest: " + closest.getLocation());
        System.out.println("Location: " + location);
        System.out.println(location.distanceFrom(closest.getLocation()));
        if(location.distanceFrom(closest.getLocation()) < SMALL_CIRCLE_HIT_BOX_SIZE/2.0){
            //if the intersection clicked is the first one, then mark it
            //if the intersection clicked is the second one, clear both

            if(listener.getFirstSelection() == null){
                //is first selection
                listener.addSelection(closest);
                closest.setPressed(true);
            }
            else{
                //is second selection
                listener.getFirstSelection().setPressed(false);
                listener.clearSelections();
            }

        }
        else{
            //if user clicked somewhere invalid, clear the first selection
            if(listener.getFirstSelection() != null){
                listener.getFirstSelection().setPressed(false);
            }
        }
    }

    public static void main(String[] args){
        Runner runner = new Runner();
    }
}

//drawing lines
//        for(int i = 0; i < list.size() - 1; i++){
//            Point p1 = list.get(i);
//            Point p2 = list.get(i + 1);
//            if((p2.x == -1 && p2.y == -1) || (p1.x == -1 && p1.y == -1)) {
//                continue;
//            }
//            g.drawLine(p1.x, p1.y, p2.x, p2.y);
//        }
