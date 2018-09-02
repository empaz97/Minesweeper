import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Game extends JFrame {

    private Board board;
    private JButton smile;
    private String smileImg;
    private String imgSuff;

    public Game(String title) {
        super(title);

        JMenuBar menuBar = new MenuBar(this);
        this.setJMenuBar(menuBar);

        this.makeButton();
        this.getContentPane().add(this.smile, BorderLayout.NORTH);

        this.board = new Board(this);
        this.getContentPane().add(this.board, BorderLayout.CENTER);
        this.imgSuff = "";


        //this.setSize(1000,1000);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
    }
    public void newGame(int w, int h, int n) {
        this.setSmile("default");
        this.board.setCustom(w,h,n);
    }

    public void newGame(String difficulty) {
        this.setSmile("default");
        this.board.setDifficulty(difficulty);
    }

    public void newGame() {
        this.setSmile("default");
        this.board.newGame();
    }

    private void makeButton() {
        JButton sm = new JButton();
        sm.setPreferredSize(new Dimension(30,30));
        sm.setBackground(Color.lightGray);
        sm.setOpaque(true);
        sm.setBorder(BorderFactory.createRaisedBevelBorder());
        this.smile = sm;

        this.setSmile("default");
        this.smile.addActionListener((ActionEvent event) -> {
            this.newGame();
        });
    }

    public void toggleColor() {
        this.board.toggleColor();
        if (this.board.getIsColored()) {
            this.imgSuff = "";
        } else {
            this.imgSuff = "_bw";
        }
        this.setSmile(this.smileImg);
    }

    public Board getBoard() {
        return this.board;
    }

    public void setSmile(String name) {
        String path = "images/" + name + "_smiley" + this.imgSuff + ".png";
        this.smileImg = name;
        this.smile.setIcon(new ImageIcon(path));
    }
}
