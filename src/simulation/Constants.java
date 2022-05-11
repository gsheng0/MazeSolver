package simulation;

import util.Point;

public class Constants {
    public static final int WINDOW_WIDTH = 1100;
    public static final int WINDOW_HEIGHT = 800;
    public static final int MARGIN_SIZE = 100;
    public static final int NUM_HORIZONTAL_INTERSECTIONS = 10;
    public static final int NUM_VERTICAL_INTERSECTIONS = 7;
    public static int CELL_WIDTH = (WINDOW_WIDTH - 2 * MARGIN_SIZE) / NUM_HORIZONTAL_INTERSECTIONS;
    public static int CELL_HEIGHT = (WINDOW_HEIGHT - 2 * MARGIN_SIZE) / NUM_VERTICAL_INTERSECTIONS;
    public static final int SMALL_CIRCLE_HIT_BOX_DIAMETER = 40;
    public static final int SMALL_CIRCLE_RADIUS = 5;
    public static int WALL_WIDTH = 2 * SMALL_CIRCLE_RADIUS + 2;
    public static final Point STARTING_POSITION = new Point(125, 125);
    public static final double VELOCITY_MAGNITUDE = 0.2;
    public static final int NUMBER_OF_BALLS = 15;
}
