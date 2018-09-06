/*
 * SquareMouseListener.java includes the sml class which is a MouseAdapter for Squares on the board
 * Author: Emily Pazienza
 * Created: 08/26/2018
 */

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SquareMouseListener extends MouseAdapter {

    // the Square it is listening to
    private Square square;

    /**
     * Creates a new SquareMouseListener
     * @param s - the Square to listen to
     */
    public SquareMouseListener(Square s) {
        this.square = s;
    }

    public void mouseClicked(MouseEvent e) {
        // if its a right click, we mark it
        if (SwingUtilities.isRightMouseButton(e)) {
            this.square.mark();
        }
    }

    public void mousePressed(MouseEvent e) {
        // if a risky move is being done, then make the smiley scared
        if (!this.square.getBoard().isFinished()
                && !SwingUtilities.isRightMouseButton(e)
                && !this.square.getIsMarked()
                && !this.square.getIsFlagged())
            this.square.getBoard().getGame().setSmile("scared");
    }

    public void mouseReleased(MouseEvent e) {
        // reset to default smiley after possible scared moment
        if (!this.square.getBoard().isFinished()
                && !SwingUtilities.isRightMouseButton(e)
                && !this.square.getIsMarked()
                && !this.square.getIsFlagged())
            this.square.getBoard().getGame().setSmile("default");
    }
}
