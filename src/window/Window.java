package window;

import javax.swing.*;

import java.awt.event.ActionListener;

import static util.Constants.*;
import static util.Util.getSpacer;

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

    public static JFrame createAppleFrame(JPanel panel, Listener listener, ActionListener startSimulation, ActionListener saveMaze, ActionListener loadMaze, ActionListener traceSolutions){
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocation(WINDOW_LOCATION.x, WINDOW_LOCATION.y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addMouseMotionListener(listener);
        frame.addKeyListener(listener);
        frame.addMouseMotionListener(listener);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(saveMaze);

        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(loadMaze);

        JMenuItem trace = new JMenuItem("Trace");
        trace.addActionListener(traceSolutions);

        fileMenu.add(save);
        fileMenu.add(load);

        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
        return frame;
    }


}
