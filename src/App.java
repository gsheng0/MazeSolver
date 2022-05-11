import javax.swing.*;
import java.awt.*;

import simulation.Simulation;
import util.Point;
import window.*;
import window.Window;

import static util.Constants.*;

public class App extends JPanel {
    public final JFrame frame;
    public final Listener listener;
    public final Simulation simulation;
    private boolean running = false;

    public App(){
        simulation = new Simulation();
        listener = Listener.getInstance();
        frame = Window.createFrame(this, listener,
                e -> running = !running,
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
        if(running) {
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
        Artist.drawSelectionPreview(simulation.getSelection(), listener.getCurrentLocation().add(0, -60));
        Artist.drawEdges(simulation.getEdges());
        repaint();
    }
    public static void main(String[] args){
        new App();
    }
}

