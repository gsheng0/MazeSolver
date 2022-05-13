import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.*;

/** @see https://stackoverflow.com/questions/8955638 */
public class Tester {

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Name");

        JFrame frame = new JFrame("Gabby");
        JPanel dm = new JPanel();
        frame.setSize(400, 400);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(dm);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        fileMenu.add(new JMenuItem("Opotion 1"));
        fileMenu.add(new JMenuItem("Option 2"));
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);

    }
}