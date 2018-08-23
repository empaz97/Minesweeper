import javax.swing.*;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        super();
        this.makeGameMenu();
        this.makeOptMenu();
        this.makeHelpMenu();
    }

    private void makeGameMenu() {
        JMenu menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_G);

        menu.getAccessibleContext().setAccessibleDescription(
                "The menu which allows you to modify your game"
        );
        this.add(menu);

        JMenuItem newGame = new JMenuItem("New Game", KeyEvent.VK_N);
        newGame.getAccessibleContext().setAccessibleDescription(
                "This starts a new game");
        menu.add(newGame);

        /*
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        */
    }

    private void makeOptMenu() {
        JMenu menu = new JMenu("Options");
        menu.setMnemonic(KeyEvent.VK_O);

        menu.getAccessibleContext().setAccessibleDescription(
                "The menu which allows you to modify your options"
        );
        this.add(menu);


        /*
        menuItem = new JMenuItem("A text-only menu item",
                KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(menuItem);
        */

    }

    private void makeHelpMenu() {
        JMenu menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);

        menu.getAccessibleContext().setAccessibleDescription(
                "The menu which gives you help"
        );
        this.add(menu);


        /*
        menuItem = new JMenuItem("A text-only menu item",
                KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(menuItem);
        */

    }
}