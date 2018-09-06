/*
 * Board.java includes the Board class which is a JPanel representing the game board
 * Author: Emily Pazienza
 * Created: 08/24/2018
 */

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Board extends JPanel {

    // options and settings for the current board
    private int width, height, nMines;
    private boolean marks, color, sound;
    private String difficulty;

    // variable attributes based on state of game
    private int remainingMines, squaresRemaining;
    private boolean isFinished, isStarted;

    // a matrix of Squares which make up the board
    private ArrayList<ArrayList<Square>> squares;
    // a list of references to the mines
    private ArrayList<Square> mines;

    // the Game this board belongs to
    private Game game;

    /**
     * Creates a new Board
     * @param game - the parent Game that this Board belongs to
     */
    public Board(Game game) {
        super();

        this.setBackground(Color.lightGray);
        this.game = game;

        // set up the default settings
        this.squares = new ArrayList<ArrayList<Square>>();
        this.mines = new ArrayList<Square>();
        this.marks = true;
        this.color = true;
        this.sound = false;
        this.setDifficulty("intermediate");

        // let the game know how many mines we start with
        this.game.getMineCount().setVal(nMines);

        // start a game!
        this.newGame();
    }

    /**
     * Takes a string with a difficulty and sets the options for that difficulty
     * @param diff - a String indicating the difficulty to be set to
     */
    public void setDifficulty (String diff) {
        this.difficulty = diff;
        switch (diff) {
            case "beginner":
                this.setOptions(8, 8, 10);
                break;
            case "intermediate":
                this.setOptions(16, 16, 40);
                break;
            case "expert":
                this.setOptions(24,24,99);
                break;
            default: // if it doesn't match a difficulty, leave the settings as-is
                break;
        }

        // start a new game once we change settings
        this.newGame();

        // resize the window based on the board dimensions
        this.game.pack();
    }

    /**
     * Sets the difficulty to custom options
     * @param w - width of the board
     * @param h - height of the board
     * @param n - number of mines on the board
     */
    public void setDifficulty (int w, int h, int n) {
        this.difficulty = "custom";
        this.setOptions(w,h,n);
        this.newGame();
        this.game.pack();
    }

    /**
     * Sets the game options to given values
     * @param w - width of the board
     * @param h - height of the board
     * @param n - number of mines on the board
     */
    private void setOptions(int w, int h, int n) {
        this.width = w;
        this.height = h;
        this.nMines = n;
    }

    /**
     * Toggles the color/bw option for the board
     */
    public void toggleColor() {
        this.color = !this.color;

        // go through all of the squares, and change the image path suffix
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.color)
                    this.squares.get(i).get(j).setSuffix("");
                else
                    this.squares.get(i).get(j).setSuffix("_bw");
            }
        }
    }

    /**
     * Toggles whether or not the game includes marks
     */
    public void toggleMarks() {
        this.marks = !this.marks;
        if (!this.marks) {
            // if we turned off marks, we go through and unmark all marked squares
            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.width; j++) {
                    if (this.squares.get(i).get(j).getIsMarked()) {
                        this.squares.get(i).get(j).removeMark();
                    }
                }
            }
        }
    }

    /**
     * Toggles whether sound effects are played
     */
    public void toggleSound() {
        this.sound = !this.sound;
    }

    /**
     * Begins a new game with the currently set options
     */
    public void newGame() {

        // stop the timer until the user starts playing and reset it to 0
        this.game.getTimer().stop();
        this.game.getTimeCount().setVal(0);

        // set the new layout based on the current height and width
        this.setLayout(new GridLayout(this.height, this.width));

        // remove and clear all components and settings
        this.removeAll();
        this.squares.clear();
        this.mines.clear();
        this.isFinished = false;
        this.isStarted = false;

        // reset mine and square counts
        this.remainingMines = this.nMines;
        this.game.getMineCount().setVal(this.remainingMines);
        this.squaresRemaining = (this.width * this.height) - this.nMines;

        // create the matrix of squares
        for (int h = 0; h < this.height; h++) {
            // create a new row
            ArrayList<Square> row = new ArrayList<Square>();
            for (int w = 0; w < this.width; w++) {
                // create a new square
                Square sq = new Square(this, w, h);
                this.add(sq);
                row.add(sq);
            }
            this.squares.add(row);
        }

        // populate the matrix with some mines
        this.addMines();

        // redraw everything!
        this.revalidate();
        this.repaint();
    }

    /**
     * Populates the current matrix with mines
     */
    private void addMines() {
        for (int i = 0; i < this.nMines; i++) {
            // get a random square in the matrix
            int h = ThreadLocalRandom.current().nextInt(0, this.height);
            int w = ThreadLocalRandom.current().nextInt(0, this.width);
            Square s = this.squares.get(h).get(w);

            // if there is already a mine, we can't add a second one
            if (s.getHasMine()) {
                i--;
            } else { // otherwise, add a mine and set it's adjacents
                s.setMine();
                this.mines.add(s);
                this.chain("add adj", w, h);
            }
        }
    }

    /**
     * Sets the game as started and begins the timer
     */
    public void start() {
        this.isStarted = true;
        this.game.getTimer().start();
    }

    /**
     * Chains a certain action to all of a given location's neighbors
     * @param action - the action to do
     * @param x, y - the location to chain around
     */
    public void chain(String action, int x, int y) {
        int north, south, east, west;
        north = y - 1;
        south = y + 1;
        east = x + 1;
        west = x - 1;

        // perform the action on the neighbors that share a side
        this.performAction(action, north, x);
        this.performAction(action, south, x);
        this.performAction(action, y, east);
        this.performAction(action, y, west);

        // perform the action on the corners
        this.performAction(action, north, west);
        this.performAction(action, north, east);
        this.performAction(action, south, west);
        this.performAction(action, south, east);
    }

    /**
     * Performs a given action on the given location
     * @param action - the action to be done
     * @param y, x - the matrix location to be acted upon
     */
    private void performAction(String action, int y, int x) {
        // first check that the location exists in the matrix
        if (y >= 0 && x >= 0 && y < this.height && x < this.width) {
            // perform the requested action
            if (action.equals("open")) {
                this.squares.get(y).get(x).press();
            } else if (action.equals("add adj")) {
                this.squares.get(y).get(x).addAdjMine();
            }
        }
    }

    /**
     * Log the fact that a press was made
     */
    public void countPress() {
        this.squaresRemaining--;
        if (this.squaresRemaining <= 0) {
            this.winGame();
        }
    }

    /**
     * Lose the game and perform various actions that go along with loss
     */
    public void loseGame() {
        this.game.getTimer().stop();

        // display all of the mines
        for (int i = 0; i < this.mines.size(); i++)
            this.mines.get(i).pushIn();

        this.game.setSmile("loss");

        this.playSound("resources/sounds/explosion.wav");

        // popup informing the user of their loss
        JOptionPane.showMessageDialog(this, "You lose! You stepped on a mine!",
                "Bummer!", JOptionPane.ERROR_MESSAGE, new ImageIcon("resources/images/loss_smiley.png"));

        this.isFinished = true;
    }

    /**
     * Win the game and perform various actions that go along with win
     */
    public void winGame() {
        this.game.getTimer().stop();

        this.game.setSmile("win");
        this.playSound("resources/sounds/cheering.wav");

        // popup informing the user of their win
        JOptionPane.showMessageDialog(this, "You win!", "Nice Job!",
                JOptionPane.INFORMATION_MESSAGE, new ImageIcon("resources/images/win_smiley.png"));
        this.isFinished = true;

        // check if the user set a new high score
        this.checkHighScore();
    }

    /**
     * Plays a sound if the game has sound option set to on
     * @param path - path to the sound file to be played
     */
    private void playSound(String path) {
        // if sound setting is false then don't play it
        if (!this.sound)
            return;

        // attempt to play the clip, and catch exceptions
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    new File(path));
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the user set a new high score
     * If so, give them option to save their score and save it
     */
    private void checkHighScore() {
        // we don't save high scores for custom board settings
        if (this.difficulty.equals("custom"))
            return;

        // the existing high score for this difficulty
        HighScore existing = this.game.getHighScores().get(this.difficulty);
        int currentTime = this.game.getTimeCount().getTotal();

        // if there is no existing high score, or if the user beat the old high score
        if (existing == null || currentTime < existing.getTime()) {

            JPanel panel = new JPanel(new BorderLayout());

            String message = "Congrats! You've set the new high score of " + currentTime + " seconds!";
            panel.add(new JLabel(message), BorderLayout.NORTH);

            message = "Please enter your name if you wish to save your high score";
            panel.add(new JLabel(message), BorderLayout.CENTER);

            JTextField name = new JTextField();
            name.setColumns(10);
            panel.add(name, BorderLayout.SOUTH);
            String winnerName;

            int option = JOptionPane.showConfirmDialog(
                    null, panel, "New High Score!",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                // if the user clicked ok, save their high score
                winnerName = name.getText();
                this.game.getHighScores().logBeginner(currentTime, winnerName);
            }
        }
    }

    // various getters
    public boolean isFinished() { return this.isFinished; }
    public boolean isStarted() { return this.isStarted; }

    public boolean getHasMarks() { return this.marks; }
    public boolean getIsColored() { return this.color; }

    public Game getGame() { return this.game; }
}