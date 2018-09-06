/*
 * Minesweeper.java includes the Minesweeper app class and the project main method
 * Author: Emily Pazienza
 * Created: 08/23/2018
 */

import javax.swing.*;

public class Minesweeper {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // create a new game
                JFrame frame = new Game("Minesweeper");
                Minesweeper app = new Minesweeper();

                // show the frame
                frame.setVisible(true);
            }
        });
    }
}