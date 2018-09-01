import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SquareMouseListener extends MouseAdapter {
    private Square square;

    public SquareMouseListener(Square s) {
        this.square = s;
    }
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            this.square.mark();
        }
    }

    public void mousePressed(MouseEvent e) {
        if (!this.square.getBoard().isFinished()
                && !SwingUtilities.isRightMouseButton(e)
                && !this.square.getIsMarked()
                && !this.square.getIsFlagged())
            this.square.getBoard().getGame().setSmile("scared");
    }

    public void mouseReleased(MouseEvent e) {
        if (!this.square.getBoard().isFinished()
                && !SwingUtilities.isRightMouseButton(e)
                && !this.square.getIsMarked()
                && !this.square.getIsFlagged())
            this.square.getBoard().getGame().setSmile("default");
    }
}
