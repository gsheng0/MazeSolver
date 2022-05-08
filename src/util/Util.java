package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util {

    public static boolean counterClockwise(Point a, Point b, Point c){
        return (c.double_y - a.double_y) * (b.double_x - a.double_x) > (b.double_y - a.double_y) * (c.double_x - a.double_x);
    }
    //one line segment is AB, other is CD
    public static boolean intersects(Point a, Point b, Point c, Point d){
        return (counterClockwise(a, c, d) != counterClockwise(b, c, d)) && (counterClockwise(a, b, c) != counterClockwise(a, b, d));
    }
    public static double determinant(Point a, Point b){
        return a.double_x * b.double_y - a.double_y * b.double_x;
    }
    public static Point getIntersectionPoint(Point a, Point b, Point c, Point d){
        Point xDiff = new Point(a.double_x - b.double_x, c.double_x - d.double_x);
        Point yDiff = new Point(a.double_y - b.double_y, c.double_y - d.double_y);

        double determinant = determinant(xDiff, yDiff);
        if(determinant == 0){
            return Point.NULL_LOCATION;
        }
        Point det = new Point(determinant(a, b), determinant(c, d));
        return new Point(
                determinant(det, xDiff) / determinant,
                determinant(det, yDiff) / determinant);
    }
    public ArrayList<String> readFromFile(String filename){
        try{
            File file = new File(filename);
            Scanner reader = new Scanner(file);
            ArrayList<String> lines = new ArrayList<>();
            while(reader.hasNextLine()){
                lines.add(reader.nextLine());
            }
            reader.close();
            return lines;
        }
        catch(FileNotFoundException e){
            System.out.println("Error: File not found");
            e.printStackTrace();
        }
        catch(Exception e){
        }
        return new ArrayList<>();
    }

}
