import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tester {

    public static void main(String[] args){
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
                    System.out.println(textField.getText());
                    textField.setText("");
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

        cancelButton.setPreferredSize(buttonSize);
        cancelButton.setMinimumSize(buttonSize);
        cancelButton.setMaximumSize(buttonSize);

        dialogBox.add(cancelButton);
        dialogBox.add(okButton);

        dialogBox.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogBox.setSize(250,80);
        dialogBox.setVisible(true);

    }
}
