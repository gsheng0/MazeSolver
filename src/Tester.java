import util.Point;
import util.Util;

import javax.swing.*;
import java.io.File;

public class Tester {

    public static void main(String[] args){

        JFileChooser jfc = new JFileChooser();
        jfc.showDialog(null,"Please Select the File");
        jfc.setVisible(true);
        File file = jfc.getSelectedFile();
        System.out.println("File name " + file.getAbsolutePath());
    }
}
