package simulation;

import objects.Ball;
import objects.Maze;
import objects.SolutionBall;
import util.Edge;
import util.Point;
import window.Listener;

import java.util.ArrayList;

import static util.Constants.*;

public class Simulation {
    public final Point[][] intersections;
    private ArrayList<Ball> balls;
    private ArrayList<Edge> edges;
    private ArrayList<SolutionBall> solutions;
    private ArrayList<SolutionBall> finishedSolutions;
    private Point selection = Point.NULL_LOCATION;
    private int previousLength = 0;
    private int mode = 0;
    public static int SIMULATION = 0;
    public static int TRACEBACK = 1;
    public Simulation(){
        edges = new ArrayList<>();
        solutions = new ArrayList<>();
        intersections = new Point[NUM_HORIZONTAL_INTERSECTIONS + 1][NUM_VERTICAL_INTERSECTIONS + 1];
        for(int x = 0; x <= NUM_HORIZONTAL_INTERSECTIONS; x++){
            for(int y = 0; y <= NUM_VERTICAL_INTERSECTIONS; y++){
                intersections[x][y] = new Point(MARGIN_SIZE + x * CELL_WIDTH, MARGIN_SIZE + y * CELL_HEIGHT);
            }
        }

        balls = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_BALLS; i++){
            balls.add(new Ball(STARTING_POSITION, Ball.generateVelocity(VELOCITY_MAGNITUDE)));
        }
    }

    public void simulate(){
        for(Ball ball : balls){
            ball.move(edges, WALL_WIDTH);
            if(ball.getFramesUntilNextCollision() >= 0){
                continue;
            }
            if(ball.getLocation().x > WINDOW_WIDTH || ball.getLocation().x < 0 || ball.getLocation().y > WINDOW_HEIGHT || ball.getLocation().y < 0){
                System.out.println("A ball has solved the maze");
                solutions.add(new SolutionBall(ball));
            }
        }

        //removing balls from active ball array
        if(previousLength != solutions.size()){
            for(int i = previousLength; i < solutions.size(); i++){
                balls.remove(solutions.get(i));
            }
            previousLength = solutions.size();
        }
    }
    public void traceback(){
        if(solutions.size() > 0) {
            //if there are still solutions in the process of solving the maze
            for (SolutionBall solution : solutions) {
                solution.move(edges, WALL_WIDTH);
                if (solution.getFramesUntilNextCollision() >= 0) {
                    continue;
                }
                if (solution.getLocation().x > WINDOW_WIDTH || solution.getLocation().x < 0 || solution.getLocation().y > WINDOW_HEIGHT || solution.getLocation().y < 0) {
                    solution.getTravelPoints().add(solution.getLocation());
                    finishedSolutions.add(solution);
                }
            }
            if (previousLength != finishedSolutions.size()) {
                for (int i = previousLength; i < finishedSolutions.size(); i++) {
                    solutions.remove(finishedSolutions.get(i));
                }
                previousLength = finishedSolutions.size();
            }
        }


    }
    public int getMode() { return mode; }
    public Point getSelection() { return selection; }
    public ArrayList<Ball> getBalls() { return balls; }
    public ArrayList<Edge> getEdges(){ return edges; }
    public ArrayList<SolutionBall> getSolutions(){ return solutions; }
    public ArrayList<SolutionBall> getFinishedSolutions() { return finishedSolutions; }
    public void handleClick(Point location, int button){
        if(button == Listener.LEFT_MOUSE_BUTTON){
            updateIntersectionGrid(location);
        }
        else if(button == Listener.RIGHT_MOUSE_BUTTON){
            selection = Point.NULL_LOCATION;
        }
    }
    public Point getClosestIntersection(Point location){
        int x = location.x;
        int y = location.y;

        x -= MARGIN_SIZE;
        y -= MARGIN_SIZE;
        double precise_x = ((double)x)/CELL_WIDTH;
        double precise_y = ((double)y)/CELL_HEIGHT;
        x = (int) Math.round(precise_x);
        y = (int) Math.round(precise_y);
        if(x < 0 || x > intersections.length - 1 || y < 0 || y > intersections[0].length - 1){
            return Point.NULL_LOCATION;
        }
        return intersections[x][y];
    }
    public void updateIntersectionGrid(Point location){
        Point adjusted = location.add(0, -60);
        Point closest = getClosestIntersection(adjusted);
        if(closest == Point.NULL_LOCATION){
            selection = Point.NULL_LOCATION;
        }
        if(adjusted.distanceFrom(closest) < SMALL_CIRCLE_HIT_BOX_DIAMETER /2.0){
            //if the intersection clicked is the first one, then mark it
            //if the intersection clicked is the second one, clear both
            if(selection == Point.NULL_LOCATION){
                selection = closest;
            }
            else{
                //is second intersection
                //checks to make sure the other point would form a straight edge with first point
                if((selection.x == closest.x || selection.y == closest.y) && !(selection.x == closest.x && selection.y == closest.y)){
                    edges.add(new Edge(selection, closest));
                }
                selection = Point.NULL_LOCATION;

            }
        }
        else{
            selection = Point.NULL_LOCATION;
        }
    }
    public void saveCurrentMaze(){
        Maze maze = new Maze(edges, NUM_HORIZONTAL_INTERSECTIONS, NUM_VERTICAL_INTERSECTIONS, CELL_WIDTH, CELL_HEIGHT, MARGIN_SIZE);
        maze.save();
    }
    public void loadMaze(){
        if(Maze.load(edges, intersections)){
            System.out.println("Successfully loaded maze");
        }
        else{
            System.out.println("Failed to load maze");
        }
    }
    public void setMode(int mode){
        if(mode != this.mode){
            previousLength = 0;
        }
        if(mode == SIMULATION){

        }
        else if(mode == TRACEBACK){
            for(Ball solution : solutions){
                solution.setLocation(STARTING_POSITION);
            }
        }
    }

}
