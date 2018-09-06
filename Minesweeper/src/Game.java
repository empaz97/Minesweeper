/*
 * Game.java includes the Game class which is the base JFrame for the application
 * Author: Emily Pazienza
 * Created: 08/23/2018
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame {

    // references to a bunch of components
    private Board board;
    private JButton smile;
    private HighScoreList highScores;
    private NumberSet mineCount, timeCount;

    // the ticking timer which counts the seconds since start
    private Timer timer;

    // the url of the current smile image to be used
    private String smileImg;
    // the image path suffix to be used to determine bw vs color
    private String imgSuff;

    /**
     * Creates a new Game
     * @param title - the title of the game to be sent to the super() call
     */
    public Game(String title) {
        // super call for the JFrame
        super(title);

        this.getContentPane().setBackground(Color.lightGray);

        // create a new menu bar to line the top of the game
        JMenuBar menuBar = new MenuBar(this);
        this.setJMenuBar(menuBar);

        // JPanel which contains the game part of the frame
        JPanel gamePanel = new JPanel(new BorderLayout(0,10));

        // create a wrapper for the header panel
        JPanel headerWrapper = new JPanel(new BorderLayout());
        headerWrapper.setBackground(Color.lightGray);
        // panel holds the header components
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.lightGray);

        // cbox is used for proper layout of header components
        Box box = Box.createHorizontalBox();

        this.mineCount = new NumberSet();
        box.add(this.mineCount);

        box.add(Box.createGlue());

        this.imgSuff = ""; // default, not black & white, so no suffix
        this.makeSmile();
        box.add(this.smile);

        box.add(Box.createGlue());

        this.timeCount = new NumberSet();
        box.add(this.timeCount);

        this.createTimer();

        headerPanel.add(box);

        // add some padding/spacing around the header components
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        headerWrapper.setBorder(BorderFactory.createLoweredBevelBorder());
        headerWrapper.add(headerPanel);

        gamePanel.add(headerWrapper, BorderLayout.NORTH);

        this.board = new Board(this);
        this.board.setBorder(BorderFactory.createLoweredBevelBorder());
        gamePanel.add(this.board, BorderLayout.CENTER);

        // add some padding around the game
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 9, 10));
        gamePanel.setBackground(Color.lightGray);

        // create the HighScoreList
        this.highScores = new HighScoreList();

        this.getContentPane().add(gamePanel, BorderLayout.CENTER);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
    }

    /**
     * Starts a new custom game with the given options
     * @param w - the width of the game
     * @param h - the height of the game
     * @param n - the number of mines in the game
     */
    public void newGame(int w, int h, int n) {
        this.setSmile("default");
        this.board.setDifficulty(w,h,n);
    }

    /**
     * Starts a new game with a preset difficulty
     * @param difficulty - String name of the preset
     */
    public void newGame(String difficulty) {
        this.setSmile("default");
        this.board.setDifficulty(difficulty);
    }

    /**
     * Starts a new game using the previous settings
     */
    public void newGame() {
        this.setSmile("default");
        this.board.newGame();
    }

    /**
     * Creates a new timer for the board which will increment the time counter every second
     */
    private void createTimer() {
        this.timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                timeCount.increment();
            }});
    }

    /**
     * Creates the JButton smile component for the game
     */
    private void makeSmile() {
        JButton sm = new JButton();

        // make sure it maintains 25x25 size
        sm.setPreferredSize(new Dimension(25,25));
        sm.setMaximumSize(new Dimension(25,25));
        sm.setMinimumSize(new Dimension(25,25));

        sm.setBackground(Color.lightGray);
        sm.setOpaque(true);
        sm.setBorder(BorderFactory.createRaisedBevelBorder());

        this.smile = sm;
        this.setSmile("default"); // starts at default
        this.smile.addActionListener((ActionEvent event) -> {
            this.newGame();
        });
    }

    /**
     * Toggles the color option for the board and adjusts the image path suffix
     * After toggling, resets the smile
     */
    public void toggleColor() {
        this.board.toggleColor();
        if (this.board.getIsColored()) {
            this.imgSuff = "";
        } else {
            this.imgSuff = "_bw";
        }
        this.setSmile(this.smileImg);
    }

    /**
     * @param name - the filename (discluding path) of the smile image
     */
    public void setSmile(String name) {
        String path = "resources/images/" + name + "_smiley" + this.imgSuff + ".png";
        this.smileImg = name;
        this.smile.setIcon(new ImageIcon(path));
    }

    /**
     * @return the Board for the game
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * @return the NumberSet mineCount for the game
     */
    public NumberSet getMineCount() {
        return this.mineCount;
    }

    /**
     * @return the NumberSet timeCount for the game
     */
    public NumberSet getTimeCount() {
        return this.timeCount;
    }

    /**
     * @return the Timer for the game
     */
    public Timer getTimer() {
        return this.timer;
    }

    /**
     * @return the HighScoreList of the game
     */
    public HighScoreList getHighScores() {
        return this.highScores;
    }
}
