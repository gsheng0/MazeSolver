import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.*;

/** @see https://stackoverflow.com/questions/8955638 */
public class Tester {
    public static String getOperatingSystem() {
        String os = System.getProperty("os.name");
        return os;
    }
    public static void main(String[] args) {
        System.out.println(getOperatingSystem());

    }
}