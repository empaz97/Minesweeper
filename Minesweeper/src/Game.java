import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame {

    private Board board;
    private JButton smile;
    private String smileImg;
    private String imgSuff;
    private NumberSet mineCount, timeCount;
    private Timer timer;
    private HighScoreList highScores;

    public Game(String title) {
        super(title);
        this.getContentPane().setBackground(Color.lightGray);

        JMenuBar menuBar = new MenuBar(this);
        this.setJMenuBar(menuBar);

        JPanel gamePanel = new JPanel(new BorderLayout(0,10));

        JPanel headerWrapper = new JPanel();
        headerWrapper.setBackground(Color.lightGray);
        JPanel headerPanel = new JPanel(new BorderLayout(135,0));
        headerPanel.setBackground(Color.lightGray);

        this.mineCount = new NumberSet();
        headerPanel.add(this.mineCount, BorderLayout.WEST);

        this.imgSuff = "";
        this.makeSmile();
        this.smile.setPreferredSize(new Dimension(25, 27));
        headerPanel.add(this.smile, BorderLayout.CENTER);

        this.timeCount = new NumberSet();
        headerPanel.add(this.timeCount, BorderLayout.EAST);

        this.createTimer();

        headerPanel.setBorder(BorderFactory.createEmptyBorder(1,0,1,0));
        headerWrapper.setBorder(BorderFactory.createLoweredBevelBorder());
        headerWrapper.add(headerPanel);
        gamePanel.add(headerWrapper, BorderLayout.NORTH);

        this.board = new Board(this);
        this.board.setBackground(Color.lightGray);
        this.board.setBorder(BorderFactory.createLoweredBevelBorder());
        gamePanel.add(this.board, BorderLayout.CENTER);

        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gamePanel.setBackground(Color.lightGray);
        this.getContentPane().add(gamePanel, BorderLayout.CENTER);

        this.highScores = new HighScoreList();
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

    private void createTimer() {
        this.timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                timeCount.increment();
            }});
    }

    private void makeSmile() {
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

    public NumberSet getMineCount() {
        return this.mineCount;
    }

    public NumberSet getTimeCount() {
        return this.timeCount;
    }

    public void setSmile(String name) {
        String path = "resources/images/" + name + "_smiley" + this.imgSuff + ".png";
        this.smileImg = name;
        this.smile.setIcon(new ImageIcon(path));
    }

    public Timer getTimer() {
        return this.timer;
    }

    public HighScoreList getHighScores() {
        return this.highScores;
    }
}
