package window;

import objects.Ball;
import objects.SolutionBall;
import simulation.Simulation;
import util.Edge;
import util.Point;

import java.awt.*;
import java.util.ArrayList;

import static util.Constants.SMALL_CIRCLE_RADIUS;
import static util.Constants.WALL_WIDTH;

public class Artist {
    public static Graphics2D graphics;
    public static boolean drawEdge(Edge edge){
        if(graphics == null){
            return false;
        }
        graphics.drawLine(edge.p1.x, edge.p1.y, edge.p2.x, edge.p2.y);
        return true;
    }
    public static boolean drawEdges(ArrayList<Edge> edges){
        if(graphics == null){
            return false;
        }
        for(Edge edge : edges){
            graphics.drawLine(edge.p1.x, edge.p1.y, edge.p2.x, edge.p2.y);
        }
        return true;
    }
    public static boolean drawBall(Ball ball){
        if(graphics == null){
            return false;
        }
        graphics.fillOval(ball.getLocation().x - Ball.RADIUS, ball.getLocation().y - Ball.RADIUS, Ball.RADIUS * 2, Ball.RADIUS * 2);
        return true;
    }
    public static boolean drawBalls(ArrayList<Ball> balls){
        if(graphics == null){
            return false;
        }
        for(Ball ball : balls){
            graphics.fillOval(ball.getLocation().x - Ball.RADIUS, ball.getLocation().y - Ball.RADIUS, Ball.RADIUS * 2, Ball.RADIUS * 2);
        }
        return true;
    }
    public static boolean drawSolutionBall(SolutionBall ball){
        if(graphics == null){
            return false;
        }
        if(!drawFinishedSolutionBall(ball)){
            return false;
        }
        ArrayList<Point> travelPoints = ball.getTravelPoints();
        Point last = travelPoints.get(travelPoints.size() - 1);
        graphics.drawLine(last.x, last.y, ball.getLocation().x, ball.getLocation().y);
        return true;
    }
    public static boolean drawFinishedSolutionBall(SolutionBall ball){
        if(graphics == null){
            return false;
        }
        if(!drawBall(ball)){
            return false;
        }
        ArrayList<Point> travelPoints = ball.getTravelPoints();
        for(int i = 0; i < travelPoints.size() - 1; i++){
            graphics.drawLine(travelPoints.get(i).x, travelPoints.get(i).y, travelPoints.get(i + 1).x, travelPoints.get(i + 1).y);
        }
        return true;
    }
    public static boolean drawSolutionBalls(ArrayList<SolutionBall> balls){
        if(graphics == null){
            return false;
        }
        for(SolutionBall ball : balls){
            ArrayList<Point> travelPoints = ball.getTravelPoints();
            for(int i = 0; i < travelPoints.size() - 1; i++){
                graphics.drawLine(travelPoints.get(i).x, travelPoints.get(i).y, travelPoints.get(i + 1).x, travelPoints.get(i + 1).y);
            }
            Point last = travelPoints.get(travelPoints.size() - 1);
            graphics.drawLine(last.x, last.y, ball.getLocation().x, ball.getLocation().y);
        }
        return true;
    }
    public static boolean drawFinishedSolutionBalls(ArrayList<SolutionBall> balls){
        if(graphics == null){
            return false;
        }
        for(SolutionBall ball : balls){
            ArrayList<Point> travelPoints = ball.getTravelPoints();
            for(int i = 0; i < travelPoints.size() - 1; i++){
                graphics.drawLine(travelPoints.get(i).x, travelPoints.get(i).y, travelPoints.get(i + 1).x, travelPoints.get(i + 1).y);
            }
        }
        return true;
    }
    public static boolean drawIntersectionGrid(Point[][] intersections){
        if(graphics == null){
            return false;
        }
        for(Point[] row : intersections){
            for(Point intersection : row) {
                int x = intersection.x;
                int y = intersection.y;
                graphics.drawOval(x - SMALL_CIRCLE_RADIUS, y - SMALL_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS * 2, SMALL_CIRCLE_RADIUS * 2);
            }
        }
        return true;
    }
    public static boolean drawSelectionPreview(Point initial, Point current){
        if(graphics == null){
            return false;
        }
        if(initial == Point.NULL_LOCATION){
            return false;
        }
        if(current == Point.NULL_LOCATION){
            return false;
        }
        graphics.fillOval(initial.x - SMALL_CIRCLE_RADIUS, initial.y - SMALL_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS * 2, SMALL_CIRCLE_RADIUS * 2);
        graphics.drawLine(initial.x, initial.y, current.x, current.y);
        return true;
    }
    public static boolean drawSimulation(Simulation simulation){
        if (graphics == null) {
            return false;
        }
        graphics.setStroke(new BasicStroke(WALL_WIDTH));
        Artist.drawEdges(simulation.getEdges());
        graphics.setStroke(new BasicStroke(1));
        graphics.setColor(Color.BLACK);
        Artist.drawIntersectionGrid(simulation.intersections);

        if(simulation.getMode() == Simulation.SIMULATION) {
            graphics.setColor(Color.BLUE);
            Artist.drawBalls(simulation.getBalls());
        }
        else if(simulation.getMode() == Simulation.TRACEBACK){
            graphics.setColor(Color.BLUE);
            Artist.drawSolutionBalls(simulation.getSolutions());
            Artist.drawFinishedSolutionBalls(simulation.getFinishedSolutions());


        }
        return true;

    }
}
