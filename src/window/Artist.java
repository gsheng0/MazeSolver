package window;

import objects.Ball;
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
        if(simulation.getMode() == Simulation.SIMULATION) {
            if (graphics == null) {
                return false;
            }
            graphics.setColor(Color.BLUE);
            Artist.drawBalls(simulation.getBalls());

            graphics.setColor(Color.BLACK);
            Artist.drawIntersectionGrid(simulation.intersections);
            graphics.setStroke(new BasicStroke(WALL_WIDTH));
            Artist.drawEdges(simulation.getEdges());
        }
        return true;

    }
}
