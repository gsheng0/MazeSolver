import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import simulation.Simulation;
import util.Point;
import window.Artist;
import window.Listener;

import static simulation.Constants.*;

public class Runner extends JPanel {
    private JFrame frame;
    private JMenuBar menuBar;
    private JButton saveButton, loadButton, startButton, traceButton;
    private Listener listener;
    private ArrayList<Point> list = new ArrayList<>();

    private Point first = Point.NULL_LOCATION;
    private Simulation simulation;

    int previousLength = 0;
    private boolean start = false;

    public Runner(){
        simulation = new Simulation();

        listener = Listener.getInstance();
        listener.setPointList(list);
        frame = new JFrame();
        frame.add(this);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        frame.setLocation(250, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addMouseListener(listener);
        frame.addKeyListener(listener);
        frame.addMouseMotionListener(listener);

        menuBar = new JMenuBar();
        menuBar.add(getSpacer());

        startButton = new JButton();
        startButton.addActionListener(this::startSimulation);
        startButton.setText("Start");

        saveButton = new JButton();
        saveButton.addActionListener(e -> simulation.saveCurrentMaze());
        saveButton.setText("Save");

        loadButton = new JButton();
        loadButton.addActionListener(e -> simulation.loadMaze());
        loadButton.setText("Load");

        traceButton = new JButton();
        traceButton.addActionListener(e -> simulation.traceSolutions());
        traceButton.setText("Trace");

        menuBar.add(getSpacer());
        menuBar.add(startButton);
        menuBar.add(getSpacer());
        menuBar.add(saveButton);
        menuBar.add(getSpacer());
        menuBar.add(loadButton);
        menuBar.add(getSpacer());
        menuBar.add(traceButton);
        menuBar.add(getSpacer());
        frame.setJMenuBar(menuBar);
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

    public static JMenu getSpacer(int x) { //returns empty menu that provides spacing between non empty options on menu
        JMenu output = new JMenu();
        output.setEnabled(false);
        Dimension dim = new Dimension(x, 1);
        output.setMinimumSize(dim);
        output.setPreferredSize(dim);
        output.setMaximumSize(dim);
        return output;
    }

    public void startSimulation(ActionEvent e){
        start = !start;
        if(start){
            startButton.setText("Stop");
        }
        else{
            startButton.setText("Start");
        }
    }

    public static JMenu getSpacer() {
        return getSpacer(20);
    }
    public static void main(String[] args){
        new Runner();
    }
}

