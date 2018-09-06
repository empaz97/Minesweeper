/*
 * Square.java includes the Square class which is a JButton for the squares on the board
 * Author: Emily Pazienza
 * Created: 08/24/2018
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Square extends JButton {

    // the board that the square is a part of
    private Board board;

    // booleans for square state
    private boolean hasMine, isPressed, isFlagged, isMarked;

    // the number of neighboring mines
    private int adjMines;
    // the location on the board
    private int x, y;

    // path to the image and the image suffix
    private String img, suffix;

    /**
     * Creates a new Square object
     * @param b - the board the Square belongs to
     * @param x, y - the location on the board's matrix
     */
    public Square(Board b, int x, int y) {
        super();
        this.board = b;

        // default states
        this.adjMines = 0;
        this.hasMine = false;
        this.isMarked = false;
        this.isFlagged = false;
        this.isPressed = false;

        // set the suffix based on colored vs bw
        if(this.board.getIsColored()) {
            this.suffix = "";
        } else {
            this.suffix = "_bw";
        }

        this.x = x;
        this.y = y;

        // setting visuals for the square
        this.setPreferredSize(new Dimension(25, 25));
        this.setBackground(Color.lightGray);
        this.setOpaque(true);
        this.setBorder(BorderFactory.createRaisedBevelBorder());

        this.addActionListener((ActionEvent event) -> {
            this.press();
            this.board.revalidate();
            this.board.repaint();
        });

        this.addMouseListener(new SquareMouseListener(this));
    }

    public void addAdjMine() {
        this.adjMines++;
    }

    public void setMine() {
        this.hasMine = true;
    }

    public void setImage() {
        String path = "resources/images/";
        switch (adjMines) {
            case 1:
                path += "one";
                break;
            case 2:
                path += "two";
                break;
            case 3:
                path += "three";
                break;
            case 4:
                path += "four";
                break;
            case 5:
                path += "five";
                break;
            case 6:
                path += "six";
                break;
            case 7:
                path += "seven";
                break;
            case 8:
                path += "eight";
                break;
            default:
                break;
        }
        path += this.suffix + ".png";

        if (this.hasMine) {
            path = "resources/images/mine.png";
        }

        this.img = path;
        this.setIcon(new ImageIcon(this.img));
    }

    public void press() {
        if (!this.board.isStarted())
            this.board.start();

        // if the square is press-able
        if (!this.isPressed && !this.isMarked && !this.isFlagged && !this.board.isFinished()) {
            // make it look pushed-in
            this.pushIn();

            if (this.hasMine) {
                this.board.loseGame();
                this.setBackground(Color.red);
            } else {
                this.board.countPress();
            }

            // if it has no neighboring mines, chain its neighbors
            if (this.adjMines == 0 && !this.hasMine) {
                this.board.chain("open", this.x, this.y);
            }
        }
    }

    // make the square look pushed-in
    public void pushIn() {
        if (!this.isPressed) {
            this.isPressed = true;
            this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.darkGray));
            this.setImage();
        }
    }

    public void removeMark() {
        this.isMarked = false;
        this.img = "";
        this.setIcon(new ImageIcon(this.img));
    }

    public void mark() {
        if (this.getBoard().getHasMarks() == true) {
            this.markWithQs();
        } else {
            this.markJustFlags();
        }
    }

    // flag or mark the square if marks are turned on
    private void markWithQs() {
        // is mark-able
        if (!this.isPressed && !this.board.isFinished()) {
            if (this.isFlagged) { // if flagged, then mark
                this.img = "resources/images/mark.png";
                this.isFlagged = false;
                this.board.getGame().getMineCount().increment();
                this.isMarked = true;
            } else if (this.isMarked) { // if marked, then unmark
                this.img = "";
                this.isMarked = false;
            } else { // if unmarked, then flag
                this.img = "resources/images/flag" + this.suffix + ".png";
                this.isFlagged = true;
                this.board.getGame().getMineCount().decrement();
            }
            this.setIcon(new ImageIcon(this.img));
        }
    }

    // flag the square if marks are turned off
    private void markJustFlags() {
        // is flag-able
        if (!this.isPressed && !this.board.isFinished()) {
            if (this.isFlagged) { // if flagged, then unflag
                this.img = "";
                this.isFlagged = false;
                this.board.getGame().getMineCount().increment();
            } else { // if unflagged, then flag
                this.img = "resources/images/flag" + this.suffix + ".png";
                this.isFlagged = true;
                this.board.getGame().getMineCount().decrement();
            }
            this.setIcon(new ImageIcon(this.img));
        }
    }

    public void setSuffix(String suff) {
        this.suffix = suff;
        if (this.isPressed) {
            this.setImage();
        }
    }

    // various getters
    public boolean getIsMarked() { return this.isMarked; }
    public boolean getIsFlagged() { return this.isFlagged; }
    public boolean getHasMine() { return this.hasMine; }

    public Board getBoard() { return this.board; }
}