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
        menu.add(marks);

        JCheckBoxMenuItem color = new JCheckBoxMenuItem("Color");
        color.setSelected(true);
        color.setMnemonic(KeyEvent.VK_C);
        menu.add(color);

        JCheckBoxMenuItem sound = new JCheckBoxMenuItem("Sound");
        marks.setSelected(false);
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

        instr.addActionListener((ActionEvent event) -> {
            JOptionPane.showMessageDialog(this, "Here's how to play:",
                    "Instructions", JOptionPane.INFORMATION_MESSAGE);
        });

        menu.add(instr);


        JMenuItem about = new JMenuItem("About", KeyEvent.VK_A);
        about.getAccessibleContext().setAccessibleDescription(
                "This brings up the about screen");
        about.addActionListener((ActionEvent event) -> {
            JOptionPane.showMessageDialog(this, "About:",
                    "About", JOptionPane.INFORMATION_MESSAGE);
        });
        menu.add(about);
    }

    public void actionPerformed(ActionEvent e) {

    }
}