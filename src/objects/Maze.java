package objects;

import util.Edge;
import util.Point;

import java.util.ArrayList;

public class Maze {
    private ArrayList<Edge> edges;
    private int height, width; //in cells
    private ArrayList<Point[]> pairsOfIntersections = new ArrayList<>();
    public Maze(ArrayList<Edge> edges, int height, int width, int cell_width, int cell_height, int margin_size){
        this.edges = edges;
        this.height = height;
        this.width = width;

        //Generate pairs list
        for(Edge edge : edges){
            Point p1 = edge.p1.add(-1 * margin_size, -1 * margin_size);
            Point p2 = edge.p2.add(-1 * margin_size, -1 * margin_size);
            Point first = new Point(p1.x/cell_width, p1.y/cell_height);
            Point second = new Point(p2.x/cell_width, p2.y/cell_height);
            pairsOfIntersections.add(new Point[]{first, second});
        }
    }


}
