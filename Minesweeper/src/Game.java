import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    private Board board;

    public Game(String title) {
        super(title);

        JMenuBar menuBar = new MenuBar(this);
        this.setJMenuBar(menuBar);

        this.board = new Board(this);
        this.getContentPane().add(board, BorderLayout.CENTER);

        //JButton button = new JButton("CLICK!");
        //this.getContentPane().add(button, BorderLayout.SOUTH);

        //this.setSize(1000,1000);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
    }

    public Board getBoard() {
        return this.board;
    }
}
