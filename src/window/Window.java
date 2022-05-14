package window;

import javax.swing.*;

import java.awt.event.ActionListener;

import static util.Constants.*;
import static util.Util.getSpacer;

public class Window {
    public static JFrame createWindowsFrame(
            JPanel panel, Listener listener,
            ActionListener startSimulation,
            ActionListener saveMaze,
            ActionListener loadMaze,
            ActionListener traceSolutions) {
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
            if (startButton.getText().equals("Start")) {
                startButton.setText("Stop");
            } else {
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

    public static JFrame createAppleFrame(
            JPanel panel, Listener listener,
            ActionListener startSimulation,
            ActionListener saveMaze,
            ActionListener saveNewMaze,
            ActionListener loadMaze,
            ActionListener traceSolutions,
            ActionListener clearMaze) {
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

        JMenuItem restart = new JMenuItem("New");
        restart.addActionListener(clearMaze);

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(saveMaze);

        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(loadMaze);

        JMenuItem trace = new JMenuItem("Trace");
        trace.addActionListener(traceSolutions);

        JMenuItem saveNew = new JMenuItem("Save as");
        save.addActionListener(saveNewMaze);

        fileMenu.add(restart); //should clear maze class's "loadedMaze" variable
        fileMenu.add(save); //should be grayed out unless maze class as a "most recently loaded maze"
        fileMenu.add(saveNew);
        fileMenu.add(load);


        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
        return frame;
    }

    public static JFrame createFrame(JPanel panel, Listener listener, ActionListener startSimulation, ActionListener saveMaze, ActionListener loadMaze, ActionListener traceSolutions) {
        return createWindowsFrame(panel, listener, startSimulation, saveMaze, loadMaze, traceSolutions);
    }
}