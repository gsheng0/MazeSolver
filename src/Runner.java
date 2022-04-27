import util.Listener;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

import util.Point;

public class Runner extends JPanel {
    JFrame frame;
    Listener listener;
    ArrayList<Point> list = new ArrayList<>();
    static final int WINDOW_WIDTH = 1100;
    static final int WINDOW_HEIGHT = 800;
    static final int MARGIN_SIZE = 100;
    static final int NUM_HORIZONTAL_CELLS = 11;
    static final int NUM_VERTICAL_CELLS = 8;
    static int CELL_WIDTH = (WINDOW_WIDTH - 2 * MARGIN_SIZE) / NUM_HORIZONTAL_CELLS;
    static int CELL_HEIGHT = (WINDOW_HEIGHT - 2 * MARGIN_SIZE) / NUM_VERTICAL_CELLS;
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
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawRect(MARGIN_SIZE, MARGIN_SIZE, WINDOW_WIDTH - 2 * MARGIN_SIZE, WINDOW_HEIGHT - 2 * MARGIN_SIZE);
        for(int x = MARGIN_SIZE; x <= WINDOW_WIDTH - MARGIN_SIZE; x += CELL_WIDTH) {
            for (int y = MARGIN_SIZE; y <= WINDOW_HEIGHT - MARGIN_SIZE; y += CELL_HEIGHT) {
                g.drawOval(x - SMALL_CIRCLE_RADIUS, y - SMALL_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS * 2, SMALL_CIRCLE_RADIUS * 2);
            }
        }


        repaint();
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
