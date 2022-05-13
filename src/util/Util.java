package util;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
    public static boolean readFromFile(String filename, ArrayList<String> lines){
        //lines read from file do not contain new line escape character
        try{
            File file = new File(filename);
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                lines.add(reader.nextLine());
            }
            reader.close();
            return true;
        }
        catch(FileNotFoundException e){
            System.out.println("Error: File not found");
            e.printStackTrace();
            return false;
        }
        catch(Exception e){
            return false;
        }

    }
    public static boolean writeToFile(String filename, ArrayList<String> lines){
        //assuming that file already exists
        try{
            FileWriter writer = new FileWriter(filename);
            for(String line : lines){
                writer.write(line);
                writer.write("\n");  //filewriter does not automatically write new lines
            }
            writer.close();
        }
        catch(IOException exception){
            exception.printStackTrace();
            return false;
        }
        catch(Exception e){
            return false;
        }
        return true;
    }
    public static File createFile(String filename){
        File file = new File(filename);
        try{
            file.createNewFile();
            return file;
        }
        catch(Exception e){
            e.printStackTrace();
            return file;
        }
    }
    public static File promptUserForFile(){
        JFileChooser jfc = new JFileChooser("src/mazes");
        jfc.showDialog(null, "Select");
        jfc.setVisible(true);
        return jfc.getSelectedFile();
    }
    public static String filterNonNumericCharacters(String string){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < string.length(); i++){
            char c = string.charAt(i);
            if(c == ' '){
                builder.append(" ");
            }
            else if(c >= '0' && c <= '9'){
                builder.append(c);
            }
            else if(c == '.'){
                builder.append(c);
            }
        }
        return builder.toString();
    }
    public static String removeLeadingWhiteSpace(String string){
        int i = 0;
        for(; i < string.length(); i++){
            char c = string.charAt(i);
            if(!(c == ' ' || c == '\t' || c == '\n')){
                break;
            }
        }
        return string.substring(i);
    }
    public static String removeTrailingWhiteSpace(String string){
        int index = 0;
        for(int i = 0; i < string.length(); i++){
            char c = string.charAt(i);
            if(!(c == ' ' || c == '\t' || c == '\n')){
                index = i;
            }
        }
        return string.substring(0, index);
    }
    public static String removeLeadingAndTrailingWhiteSpace(String string){
        return removeTrailingWhiteSpace(removeLeadingWhiteSpace(string));
    }
    public static String condenseSpaces(String string){
        StringBuilder builder = new StringBuilder();
        boolean seenSpace = false;
        for(int i = 0; i < string.length(); i++){
            char c = string.charAt(i);
            if(c == ' '){
                if(!seenSpace){
                    builder.append(" ");
                    seenSpace = true;
                }
            }
            else{
                builder.append(c);
                seenSpace = false;
            }
        }
        return builder.toString();
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
    public static JMenu getSpacer() {
        return getSpacer(20);
    }
    public static int getOperatingSystem(){
        String os = System.getProperty("os.name");
        if(os.startsWith("Mac")){
            return Constants.MAC;
        }
        else{
            return Constants.OTHER;
        }
    }

}
