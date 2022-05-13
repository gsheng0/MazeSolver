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
    private boolean paused = true;


    public App(){
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Some name goes here");
        simulation = new Simulation();
        listener = Listener.getInstance();
        frame = Window.createFrame(this, listener,
                e -> paused = !paused,
                e -> simulation.saveCurrentMaze(),
                e -> simulation.loadMaze(),
                e -> simulation.setMode(Simulation.TRACEBACK));
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D)graphics;
        Artist.graphics = g;


        //handling actions
        if (!paused) {
            if(simulation.getMode() == Simulation.SIMULATION){
                simulation.simulate();
            }
            else if(simulation.getMode() == Simulation.TRACEBACK){
                simulation.traceback();
            }

        }
        else if(paused) {
            if (!listener.getLastClicked().equals(Point.NULL_LOCATION)) {
                simulation.handleClick(listener.getLastClicked(), listener.getButton());
                listener.clearLastClicked();
            }
            g.setStroke(new BasicStroke(WALL_WIDTH));
            Artist.drawSelectionPreview(simulation.getSelection(), listener.getCurrentLocation().add(0, -60));
        }
        Artist.drawSimulation(simulation);



        repaint();
    }
    public static void main(String[] args){
        new App();
    }
}

