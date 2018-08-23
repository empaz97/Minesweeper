import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
        newGame.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menu.add(newGame);

        menu.addSeparator();

        ButtonGroup group = new ButtonGroup();

        JCheckBoxMenuItem beginner = new JCheckBoxMenuItem("Beginner");
        beginner.setSelected(false);
        beginner.setMnemonic(KeyEvent.VK_B);
        group.add(beginner);
        menu.add(beginner);

        JCheckBoxMenuItem interm = new JCheckBoxMenuItem("Intermediate");
        interm.setSelected(true);
        interm.setMnemonic(KeyEvent.VK_I);
        group.add(interm);
        menu.add(interm);

        JCheckBoxMenuItem expert = new JCheckBoxMenuItem("Expert");
        expert.setSelected(false);
        expert.setMnemonic(KeyEvent.VK_E);
        group.add(expert);
        menu.add(expert);

        JCheckBoxMenuItem custom = new JCheckBoxMenuItem("Custom");
        custom.setSelected(false);
        custom.setMnemonic(KeyEvent.VK_C);
        group.add(custom);
        menu.add(custom);

        menu.addSeparator();

        JMenuItem scores = new JMenuItem("High Scores", KeyEvent.VK_S);
        scores.getAccessibleContext().setAccessibleDescription(
                "This brings up the high score screen");
        menu.add(scores);

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