package window;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

import static util.Constants.*;

public class Window {
    public static JFrame createFrame(JPanel panel, Listener listener, ActionListener startSimulation, ActionListener saveMaze, ActionListener loadMaze, ActionListener traceSolutions){
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocation(WINDOW_LOCATION.x, WINDOW_LOCATION.y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addMouseListener(listener);
        frame.addKeyListener(listener);
        frame.addMouseMotionListener(listener);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(getSpacer());

        JButton startButton = new JButton();
        startButton.addActionListener(e -> {
            startSimulation.actionPerformed(e);
            if(startButton.getText().equals("Start")){
                startButton.setText("Stop");
            }
            else{
                startButton.setText("Start");
            }
        });
        startButton.setText("Start");

        JButton saveButton = new JButton();
        saveButton.addActionListener(saveMaze);
        saveButton.setText("Save");

        JButton loadButton = new JButton();
        loadButton.addActionListener(loadMaze);
        loadButton.setText("Load");

        JButton traceButton = new JButton();
        traceButton.addActionListener(traceSolutions);
        traceButton.setText("Trace");

        menuBar.add(getSpacer());
        menuBar.add(startButton);
        menuBar.add(getSpacer());
        menuBar.add(saveButton);
        menuBar.add(getSpacer());
        menuBar.add(loadButton);
        menuBar.add(getSpacer());
        menuBar.add(traceButton);
        menuBar.add(getSpacer());
        frame.setJMenuBar(menuBar);
        return frame;
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

}
