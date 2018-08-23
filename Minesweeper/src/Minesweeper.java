import javax.swing.*;

public class Minesweeper {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new Game("Minesweeper");
                Minesweeper app = new Minesweeper();
                frame.setVisible(true);
            }
        });
    }
}