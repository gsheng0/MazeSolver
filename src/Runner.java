import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import objects.Ball;
import objects.Maze;
import util.Edge;
import util.Point;
import util.Result;

public class Runner extends JPanel {
    private JFrame frame;
    private JMenuBar menuBar;
    private JButton saveButton, loadButton, startButton;
    private Listener listener;
    private ArrayList<Point> list = new ArrayList<>();
    private Point[][] intersections;
    static final int WINDOW_WIDTH = 1100;
    static final int WINDOW_HEIGHT = 800;
    static final int MARGIN_SIZE = 100;
    static final int NUM_HORIZONTAL_INTERSECTIONS = 10;
    static final int NUM_VERTICAL_INTERSECTIONS = 7;
    static int CELL_WIDTH = (WINDOW_WIDTH - 2 * MARGIN_SIZE) / NUM_HORIZONTAL_INTERSECTIONS;
    static int CELL_HEIGHT = (WINDOW_HEIGHT - 2 * MARGIN_SIZE) / NUM_VERTICAL_INTERSECTIONS;
    static final int SMALL_CIRCLE_HIT_BOX_SIZE = 40;
    static final int SMALL_CIRCLE_RADIUS = 5;
    static int WALL_WIDTH = 2 * SMALL_CIRCLE_RADIUS + 2;
    private Point first = Point.NULL_LOCATION;
    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<Ball> solutions = new ArrayList<>();
    static final Point startingPosition = new Point(125, 125);
    static final int NUMBER_OF_BALLS = 15;
    public static int FRAME_NUMBER = 0;
    int previousLength = 0;
    private boolean start = false;

    public Runner(){
        intersections = new Point[NUM_HORIZONTAL_INTERSECTIONS + 1][NUM_VERTICAL_INTERSECTIONS + 1];
        for(int x = 0; x <= NUM_HORIZONTAL_INTERSECTIONS; x++){
            for(int y = 0; y <= NUM_VERTICAL_INTERSECTIONS; y++){
                intersections[x][y] = new Point(MARGIN_SIZE + x * CELL_WIDTH, MARGIN_SIZE + y * CELL_HEIGHT);
            }
        }
        for(int i = 0; i < NUMBER_OF_BALLS; i++){
            balls.add(new Ball(startingPosition, Ball.generateVelocity(0.2)));
        }

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
        saveButton.addActionListener(this::saveCurrentMaze);
        saveButton.setText("Save");

        loadButton = new JButton();
        loadButton.addActionListener(this::loadMaze);
        loadButton.setText("Load");

        menuBar.add(getSpacer());
        menuBar.add(startButton);
        menuBar.add(getSpacer());
        menuBar.add(saveButton);
        menuBar.add(getSpacer());
        menuBar.add(loadButton);
        menuBar.add(getSpacer());
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D)graphics;

        //handling actions
        if(start) {
            for(Ball ball : balls) {
                ball.move(edges, WALL_WIDTH);
                if(ball.getFramesUntilNextCollision() < 0){
                    if(ball.getLocation().x > WINDOW_WIDTH || ball.getLocation().x < 0 || ball.getLocation().y > WINDOW_HEIGHT || ball.getLocation().y < 0){
                        System.out.println("A ball has solved the maze");
                        solutions.add(ball);
                    }
                }
            }

            if(previousLength != solutions.size()){
                for(int i = previousLength; i < solutions.size(); i++){
                    balls.remove(solutions.get(i));
                }
                previousLength = solutions.size();
            }
        }



        if(!listener.getLastClicked().equals(Point.NULL_LOCATION)){
            //if the previously released coordinates is not the null location
            if(listener.getButton() == Listener.LEFT_MOUSE_BUTTON) {
                updateIntersections(listener.getLastClicked());
            }
            else if(listener.getButton() == Listener.RIGHT_MOUSE_BUTTON){
                first = Point.NULL_LOCATION;
            }
            listener.clearLastClicked();
        }

        g.setColor(Color.BLUE);
        for(Ball ball : balls){
            g.fillOval(ball.getLocation().x - Ball.RADIUS, ball.getLocation().y - Ball.RADIUS, Ball.RADIUS * 2, Ball.RADIUS * 2);
        }

        //actual graphics
        g.setColor(Color.BLACK);


        //g.drawRect(MARGIN_SIZE, MARGIN_SIZE, WINDOW_WIDTH - 2 * MARGIN_SIZE, WINDOW_HEIGHT - 2 * MARGIN_SIZE);
        for(Point[] row : intersections){
            for(Point intersection : row) {
                int x = intersection.x;
                int y = intersection.y;
                g.drawOval(x - SMALL_CIRCLE_RADIUS, y - SMALL_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS * 2, SMALL_CIRCLE_RADIUS * 2);
                //g.drawOval(x - SMALL_CIRCLE_HIT_BOX_SIZE/2, y - SMALL_CIRCLE_HIT_BOX_SIZE/2, SMALL_CIRCLE_HIT_BOX_SIZE, SMALL_CIRCLE_HIT_BOX_SIZE);
            }
        }
        g.setStroke(new BasicStroke(WALL_WIDTH));
        if(first != Point.NULL_LOCATION){
            Point current = listener.getCurrentLocation().add(0, -60);
            g.fillOval(first.x - SMALL_CIRCLE_RADIUS, first.y - SMALL_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS * 2, SMALL_CIRCLE_RADIUS * 2);
            g.drawLine(first.x, first.y, current.x, current.y);
        }
        for(Edge edge : edges){
            g.drawLine(edge.p1.x, edge.p1.y, edge.p2.x, edge.p2.y);
        }
        repaint();
    }
    public Result<Point> getClosestIntersection(Point location){
        int x = location.x;
        int y = location.y;

        x -= MARGIN_SIZE;
        y -= MARGIN_SIZE;
        double precise_x = ((double)x)/CELL_WIDTH;
        double precise_y = ((double)y)/CELL_HEIGHT;
        x = (int) Math.round(precise_x);
        y = (int) Math.round(precise_y);
        if(x < 0 || x > intersections.length - 1 || y < 0 || y > intersections[0].length - 1){
            return new Result<>();
        }
        return new Result<>(intersections[x][y]);
    }
    public void updateIntersections(Point location){
        //-30 pixels without menubar
        //-60 with menubar
        Point adjusted = location.add(0, -60);
        Result<Point> result = getClosestIntersection(adjusted);
        if(result.error()){
            first = Point.NULL_LOCATION;
            return;
        }
        Point closest = result.get();
        if(adjusted.distanceFrom(closest) < SMALL_CIRCLE_HIT_BOX_SIZE/2.0){
            //if the intersection clicked is the first one, then mark it
            //if the intersection clicked is the second one, clear both
            if(first == Point.NULL_LOCATION){
                //is first intersection
                first = closest;
            }
            else{
                //is second intersection
                //checks to make sure the other point would form a straight edge with first point
                if((first.x == closest.x || first.y == closest.y) && !(first.x == closest.x && first.y == closest.y)){
                    edges.add(new Edge(first, closest));
                }
                first = Point.NULL_LOCATION;
            }
        }
        else{
            //clear selection if the user clicks somewhere invalid
            first = Point.NULL_LOCATION;
        }
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
    public void saveCurrentMaze(ActionEvent e){
        Maze maze = new Maze(edges, NUM_HORIZONTAL_INTERSECTIONS, NUM_VERTICAL_INTERSECTIONS, CELL_WIDTH, CELL_HEIGHT, MARGIN_SIZE);
        maze.save();
    }
    public void loadMaze(ActionEvent e){
        if(Maze.load(edges, intersections)){
            System.out.println("Successfully loaded maze");
        }
        else{
            System.out.println("Failed to load maze");
        }
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

