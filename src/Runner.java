import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import simulation.Simulation;
import util.Point;
import window.*;
import window.Window;

import static util.Constants.*;

public class Runner extends JPanel {
    private JFrame frame;
    private Listener listener;
    private ArrayList<Point> list = new ArrayList<>();
    private Point first = Point.NULL_LOCATION;
    private Simulation simulation;
    private boolean start = false;

    public Runner(){
        simulation = new Simulation();

        listener = Listener.getInstance();
        listener.setPointList(list);
        frame = Window.createFrame(this, listener,
                this::startSimulation,
                e -> simulation.saveCurrentMaze(),
                e -> simulation.loadMaze(),
                e -> simulation.traceSolutions());
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D)graphics;
        Artist.graphics = g;

        //handling actions
        if(start) {
            simulation.simulate();
        }

        if(!listener.getLastClicked().equals(Point.NULL_LOCATION)){
            simulation.handleClick(listener.getLastClicked(), listener.getButton());
        }

        g.setColor(Color.BLUE);
        Artist.drawBalls(simulation.getBalls());

        g.setColor(Color.BLACK);
        Artist.drawIntersectionGrid(simulation.intersections);
        g.setStroke(new BasicStroke(WALL_WIDTH));
        Artist.drawSelectionPreview(first, listener.getCurrentLocation().add(0, -60));
        Artist.drawEdges(simulation.getEdges());
        repaint();
    }
    public void startSimulation(ActionEvent e){
        start = !start;
    }
    public static void main(String[] args){
        new Runner();
    }
}

