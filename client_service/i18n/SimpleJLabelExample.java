//A quick application to show a simple JLabel.

import javax.swing.JFrame;
import javax.swing.JLabel;

public class SimpleJLabelExample {
  public static void main(String[] args) {
    JLabel label = new JLabel("A Very Simple Text Label");

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(label);
    frame.pack();
    frame.setVisible(true);
  }
}

