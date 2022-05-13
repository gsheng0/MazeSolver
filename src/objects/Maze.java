package objects;

import util.Edge;
import util.Point;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
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
    /*
    Formatting for .mz files:
    Line 1: Width of the maze, in cells
    Line 2: Height of the maze, in cells
    Line 3, 4, ....: Pairs of points representing the edges of the maze, based on the relative position of the points

     */
    //this method only needs the matrix of intersections to find the coordinates of the walls of the maze
    public static boolean load(ArrayList<Edge> edges, Point[][] intersections){
        File file = Util.promptUserForFile();
        if(file == null){
            return false;
        }
        String path = file.getAbsolutePath();
        ArrayList<String> data = new ArrayList<>();
        if(!Util.readFromFile(path, data)){
            return false;
        }

        try{
            edges.clear();
            for(int i = 2; i < data.size(); i++){
                String line = data.get(i);
                String[] stringOfPoints = new String[2];
                int x = 0;
                for(; x < line.length(); x++){
                    char c = line.charAt(x);
                    if(c == ')' && line.charAt(x + 1) == ' ' && line.charAt(x + 2) == '('){
                        break;
                    }
                }
                stringOfPoints[0] = line.substring(0, x + 1);
                stringOfPoints[1] = line.substring(x + 2);

                Point first = Point.parsePoint(stringOfPoints[0]);
                Point second = Point.parsePoint(stringOfPoints[1]);
                Point p1 = intersections[first.x][first.y];
                Point p2 = intersections[second.x][second.y];
                edges.add(new Edge(p1, p2));

            }
            return true;
        }
        catch(NumberFormatException e){
            System.out.println("Error: file " + path + " has invalid formatting");
            e.printStackTrace();
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //This method needs to know how large the cells are and the margin size to figure out the coordinates of the edges of the maze
    public static boolean load(ArrayList<Edge> edges, int cell_width, int cell_height, int margin_size){
        File file = Util.promptUserForFile();
        if(file == null){
            return false;
        }
        String path = file.getAbsolutePath();
        ArrayList<String> data = new ArrayList<>();
        if(!Util.readFromFile(path, data)){
            return false;
        }
        try{
            edges.clear();
            for(int i = 2; i < data.size(); i++){
                String line = data.get(i);
                String[] stringOfPoints = line.split(" ");
                Point first = Point.parsePoint(stringOfPoints[0]).multiply(cell_width, cell_height).add(margin_size, margin_size);
                Point second = Point.parsePoint(stringOfPoints[1]).multiply(cell_width, cell_height).add(margin_size, margin_size);
                edges.add(new Edge(first, second));
            }
            return true;
        }
        catch(NumberFormatException e){
            System.out.println("Error: file " + path + " has invalid formatting");
            e.printStackTrace();
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    private void writeMazeToFile(String filename){
        File file = Util.createFile("src/mazes/" + filename + ".mz");
        String path = file.getAbsolutePath();

        ArrayList<String> toBeWritten = new ArrayList<>();
        toBeWritten.add("" + width);
        toBeWritten.add("" + height);
        for(Point[] pair : pairsOfIntersections){
            toBeWritten.add(pair[0].toString() + " " + pair[1].toString());
        }
        Util.writeToFile(path, toBeWritten);
    }
    public void save(){
        JDialog dialogBox = new JDialog();
        JTextField textField = new JTextField("");
        JLabel label = new JLabel("Enter a filename:");
        Dimension buttonSize = new Dimension(95, 30);
        Dimension windowSize = new Dimension(100, 20);
        JButton cancelButton = new JButton("Cancel");
        JButton okButton = new JButton("Ok");

        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    String filename = textField.getText();
                    writeMazeToFile(filename);
                    dialogBox.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        dialogBox.setLayout(new GridLayout(2, 2));
        textField.setPreferredSize(windowSize);
        textField.setSize(windowSize);
        textField.setMaximumSize(windowSize);
        textField.setMinimumSize(windowSize);
        dialogBox.add(label);
        dialogBox.add(textField);

        okButton.setPreferredSize(buttonSize);
        okButton.setMinimumSize(buttonSize);
        okButton.setMaximumSize(buttonSize);
        okButton.addActionListener(e -> {
            String filename = textField.getText();
            writeMazeToFile(filename);
            dialogBox.dispose();
        });

        cancelButton.setPreferredSize(buttonSize);
        cancelButton.setMinimumSize(buttonSize);
        cancelButton.setMaximumSize(buttonSize);
        cancelButton.addActionListener(e -> dialogBox.dispose());

        dialogBox.add(cancelButton);
        dialogBox.add(okButton);

        dialogBox.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogBox.setSize(250,80);
        dialogBox.setVisible(true);
    }


}
