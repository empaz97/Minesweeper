import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Board extends JPanel {

    private int width, height, nMines;
    private int remainingMines, squaresRemaining;
    private ArrayList<ArrayList<Square>> squares;
    private ArrayList<Square> mines;
    private Game game;
    private boolean isFinished, isStarted;
    private boolean marks, color;

    public Board(Game game) {
        super();

        this.game = game;
        this.squares = new ArrayList<ArrayList<Square>>();
        this.mines = new ArrayList<Square>();
        this.marks = true;
        this.color = true;
        this.setDifficulty("Intermediate");
        this.game.getMineCount().setVal(nMines);

        this.newGame();
    }

    public void setDifficulty (String difficulty) {
        switch (difficulty) {
            case "Beginner":
                this.setOptions(8, 8, 10);
                break;
            case "Intermediate":
                this.setOptions(16, 16, 40);
                break;
            case "Expert":
                this.setOptions(24,24,99);
                break;
            default:
                break;
        }

        this.newGame();
    }

    private void setOptions(int w, int h, int nM) {
        this.width = w;
        this.height = h;
        this.nMines = nM;
    }

    public void setCustom (int w, int h, int n) {
        this.setOptions(w,h,n);
        this.newGame();
    }

    public void toggleColor() {
        this.color = !this.color;
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.color)
                    this.squares.get(i).get(j).setSuffix("");
                else
                    this.squares.get(i).get(j).setSuffix("_bw");
            }
        }
    }

    public void toggleMarks() {
        this.marks = !this.marks;
        if (!this.marks) {
            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.width; j++) {
                    if (this.squares.get(i).get(j).getIsMarked()) {
                        this.squares.get(i).get(j).removeMark();
                    }
                }
            }
        }
    }

    public boolean getHasMarks() {
        return this.marks;
    }

    public boolean getIsColored() {
        return this.color;
    }

    public void newGame() {
        this.game.getTimeCount().setVal(0);
        this.setLayout(new GridLayout(this.height, this.width));
        this.removeAll();
        this.squares.clear();
        this.mines.clear();
        this.isFinished = false;
        this.isStarted = false;

        this.remainingMines = this.nMines;
        this.game.getMineCount().setVal(this.remainingMines);
        this.squaresRemaining = (this.width * this.height) - this.nMines;

        for (int h = 0; h < this.height; h++) {
            ArrayList<Square> row = new ArrayList<Square>();
            for (int w = 0; w < this.width; w++) {
                //add a square
                Square sq = new Square(this, w, h);
                this.add(sq);
                row.add(sq);
            }
            this.squares.add(row);
        }

        this.addMines();
        this.revalidate();
        this.repaint();
    }

    private void addMines() {
        for (int i = 0; i < this.nMines; i++) {
            int h = ThreadLocalRandom.current().nextInt(0, this.height);
            int w = ThreadLocalRandom.current().nextInt(0, this.width);
            Square s = this.squares.get(h).get(w);

            if (s.getHasMine()) {
                i--;
            } else {
                s.setMine();
                this.mines.add(s);
                this.chain("add adj", w, h);
            }
        }
    }

    public void chain(String action, int x, int y) {
        int north, south, east, west;
        north = y - 1;
        south = y + 1;
        east = x + 1;
        west = x - 1;

        this.performAction(action, north, x);
        this.performAction(action, south, x);
        this.performAction(action, y, east);
        this.performAction(action, y, west);

        this.performAction(action, north, west);
        this.performAction(action, north, east);
        this.performAction(action, south, west);
        this.performAction(action, south, east);
    }

    private void performAction(String action, int y, int x) {
        if (y >= 0 && x >= 0 && y < this.height && x < this.width) {
            if (action.equals("open"))
                this.squares.get(y).get(x).press();
            if (action.equals("add adj"))
                this.squares.get(y).get(x).addAdjMine();
        }
    }
    public void countPress() {
        this.squaresRemaining--;
        if (this.squaresRemaining == 0) {
            this.winGame();
        }
    }

    public void loseGame() {
        this.game.getTimer().stop();
        for (int i = 0; i < this.mines.size(); i++) {
            this.mines.get(i).pushIn();
        }

        this.game.setSmile("loss");

        JOptionPane.showMessageDialog(this, "You lose! You stepped on a mine!",
                "Bummer!", JOptionPane.ERROR_MESSAGE);
        this.isFinished = true;
    }

    public void winGame() {
        this.game.getTimer().stop();
        this.game.setSmile("win");
        JOptionPane.showMessageDialog(this, "You win!",
                "Nice Job!", JOptionPane.INFORMATION_MESSAGE);
        this.isFinished = true;
    }

    public boolean isFinished() {
        return this.isFinished;
    }
    public boolean isStarted() { return this.isStarted; }

    public void start() {
        this.isStarted = true;
        this.game.getTimer().start();
    }

    public Game getGame() {
        return this.game;
    }
}