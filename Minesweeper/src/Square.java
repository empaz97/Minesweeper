import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Square extends JButton {

    private Board board;
    private boolean hasMine, isPressed, isMarked;
    private int adjMines, x, y;
    private String img;

    public Square(Board b, int x, int y) {
        super();
        this.board = b;
        this.adjMines = 0;
        this.hasMine = false;
        this.x = x;
        this.y = y;

        this.setPreferredSize(new Dimension(25, 25));
        this.setBackground(Color.lightGray);
        this.setOpaque(true);
        this.setBorder(BorderFactory.createRaisedBevelBorder());

        this.addActionListener((ActionEvent event) -> {
            this.press();
            this.board.revalidate();
            this.board.repaint();
        });

        Square button = this;
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    button.mark();
                }
            }
        });
    }

    public void addAdjMine() {
        this.adjMines++;
    }

    public void setMine() {
        this.hasMine = true;
    }

    public void setImage() {
        String path = "images/";
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
        path += ".png";

        if (this.hasMine) {
            path = "images/mine.png";
        }

        this.img = path;
        this.setIcon(new ImageIcon(this.img));
    }

    public void press() {
        if (!this.isPressed && !this.isMarked && !this.board.isFinished()) {
            this.pushIn();

            if (this.hasMine) {
                this.board.loseGame();
                this.setBackground(Color.red);
            }

            if (this.adjMines == 0 && !this.hasMine) {
                this.board.chain("open", this.x, this.y);
            }
        }
    }

    public void pushIn() {
        if (!this.isPressed) {
            this.isPressed = true;
            this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.darkGray));
            this.setImage();
            this.board.countPress();
        }
    }

    public void mark() {
        if (!this.isPressed && !this.board.isFinished()) {
            if (this.isMarked) {
                this.img = "";
                this.setIcon(null);
            } else {
                this.img = "images/flag.png";
                this.setIcon(new ImageIcon(this.img));
            }

            this.isMarked = !this.isMarked;
        }
    }

    public boolean getHasMine() {
        return this.hasMine;
    }
}