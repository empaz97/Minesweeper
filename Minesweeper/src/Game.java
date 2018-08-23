import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    public Game(String title) {
        super(title);

        JMenuBar menuBar = new MenuBar();
        this.setJMenuBar(menuBar);
        JButton button = new JButton("CLICK!");
        this.getContentPane().add(button, BorderLayout.CENTER);

        this.setSize(1000,1000);
        this.pack();
    }
}
