import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuBar extends JMenuBar implements ActionListener {

    private Game game;

    public MenuBar(Game game) {
        super();
        this.game = game;
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
        newGame.addActionListener((ActionEvent event) -> {
            this.game.getBoard().newGame();
        });
        menu.add(newGame);

        menu.addSeparator();

        ButtonGroup group = new ButtonGroup();

        JRadioButtonMenuItem beginner = new JRadioButtonMenuItem("Beginner");
        beginner.setSelected(false);
        beginner.setMnemonic(KeyEvent.VK_B);
        group.add(beginner);
        beginner.addActionListener((ActionEvent event) -> {
            this.game.newGame("Beginner");
        });
        menu.add(beginner);

        JRadioButtonMenuItem interm = new JRadioButtonMenuItem("Intermediate");
        interm.setSelected(true);
        interm.setMnemonic(KeyEvent.VK_I);
        group.add(interm);
        interm.addActionListener((ActionEvent event) -> {
            this.game.newGame("Intermediate");
        });
        menu.add(interm);

        JRadioButtonMenuItem expert = new JRadioButtonMenuItem("Expert");
        expert.setSelected(false);
        expert.setMnemonic(KeyEvent.VK_E);
        group.add(expert);
        expert.addActionListener((ActionEvent event) -> {
            this.game.newGame("Expert");
        });
        menu.add(expert);

        JRadioButtonMenuItem custom = new JRadioButtonMenuItem("Custom");
        custom.setSelected(false);
        custom.setMnemonic(KeyEvent.VK_C);
        group.add(custom);
        custom.addActionListener((ActionEvent event) -> {
            // ask for size
            // make new board
            this.game.newGame(7,7,3);
        });
        menu.add(custom);

        /*
        menu.addSeparator();

        JMenuItem scores = new JMenuItem("High Scores", KeyEvent.VK_S);
        scores.getAccessibleContext().setAccessibleDescription(
                "This brings up the high score screen");
        menu.add(scores);
        */
    }

    private void makeOptMenu() {
        JMenu menu = new JMenu("Options");
        menu.setMnemonic(KeyEvent.VK_O);

        menu.getAccessibleContext().setAccessibleDescription(
                "The menu which allows you to modify your options"
        );
        this.add(menu);

        JCheckBoxMenuItem marks = new JCheckBoxMenuItem("Marks (?)");
        marks.setSelected(true);
        marks.setMnemonic(KeyEvent.VK_M);
        marks.addActionListener((ActionEvent event) -> {
            this.game.getBoard().toggleMarks();
        });
        menu.add(marks);

        JCheckBoxMenuItem color = new JCheckBoxMenuItem("Color");
        color.setSelected(true);
        color.setMnemonic(KeyEvent.VK_C);
        color.addActionListener((ActionEvent event) -> {
            this.game.toggleColor();
        });
        menu.add(color);

        JCheckBoxMenuItem sound = new JCheckBoxMenuItem("Sound");
        sound.setSelected(false);
        sound.setMnemonic(KeyEvent.VK_S);
        menu.add(sound);

    }

    private void makeHelpMenu() {
        JMenu menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);

        menu.getAccessibleContext().setAccessibleDescription(
                "The menu which gives you help"
        );
        this.add(menu);

        JMenuItem instr = new JMenuItem("Instructions", KeyEvent.VK_I);
        instr.getAccessibleContext().setAccessibleDescription(
                "This brings up the information screen");
        instr.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_I, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        String message = "Here's how to play:\n";
        message += "Click squares to open them. If it's a mine, you lose!\n";
        message += "If you open all non-mine squares, you win!\n\n";
        message += "Non-mine squares will display a number indicating the number of adjacent mines," +
                "or nothing if there are no adjacent mines.\n";
        message += "Right click to flag a square as possibly having a mine. Right click again to mark" +
                " it as questionable. One more right click clears the mark and flag.";

        final String instrMess = message;
        instr.addActionListener((ActionEvent event) -> {
            JOptionPane.showMessageDialog(this, instrMess,
                    "Instructions", JOptionPane.INFORMATION_MESSAGE);
        });

        menu.add(instr);


        JMenuItem about = new JMenuItem("About", KeyEvent.VK_A);
        about.getAccessibleContext().setAccessibleDescription(
                "This brings up the about screen");

        message = "About:\n";
        message += "Created by Emily Pazienza\n";
        message += "2018";
        final String abtMess = message;
        about.addActionListener((ActionEvent event) -> {
            JOptionPane.showMessageDialog(this, abtMess,
                    "About", JOptionPane.INFORMATION_MESSAGE);
        });
        menu.add(about);
    }

    public void actionPerformed(ActionEvent e) {

    }
}