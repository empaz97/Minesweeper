import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.ParseException;

public class MenuBar extends JMenuBar implements ActionListener {

    // the game that the menubar belongs to
    private Game game;
    private JRadioButtonMenuItem selected;

    public MenuBar(Game game) {
        super();
        this.game = game;
        this.makeGameMenu();
        this.makeOptMenu();
        this.makeHelpMenu();
    }

    // creates the game menu and adds it to the menu bar
    private void makeGameMenu() {
        JMenu menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_G);

        menu.getAccessibleContext().setAccessibleDescription(
                "The menu which allows you to modify your game"
        );
        this.add(menu);

        // starts a new game with the current settings
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

        // group of the difficulty options
        ButtonGroup group = new ButtonGroup();

        // beginner games are 8x8 with 10 mines
        // sets these settings and starts a new game
        JRadioButtonMenuItem beginner = new JRadioButtonMenuItem("Beginner");
        beginner.setSelected(false);
        beginner.setMnemonic(KeyEvent.VK_B);
        group.add(beginner);
        beginner.addActionListener((ActionEvent event) -> {
            this.game.newGame("beginner");
        });
        menu.add(beginner);

        // intermediate games are 16x16 with 40 mines
        // sets these settings and starts a new game
        JRadioButtonMenuItem interm = new JRadioButtonMenuItem("Intermediate");
        interm.setSelected(true);
        this.selected = interm;
        interm.setMnemonic(KeyEvent.VK_I);
        group.add(interm);
        interm.addActionListener((ActionEvent event) -> {
            this.game.newGame("intermediate");
            this.selected = interm;
        });
        menu.add(interm);

        // expert games are 24x24 with 99 mines
        // sets these settings and starts a new game
        JRadioButtonMenuItem expert = new JRadioButtonMenuItem("Expert");
        expert.setSelected(false);
        expert.setMnemonic(KeyEvent.VK_E);
        group.add(expert);
        expert.addActionListener((ActionEvent event) -> {
            this.game.newGame("expert");
            this.selected = expert;
        });
        menu.add(expert);

        // lets user customize game settings and starts a new game
        JRadioButtonMenuItem custom = new JRadioButtonMenuItem("Custom");
        custom.setSelected(false);
        custom.setMnemonic(KeyEvent.VK_C);
        group.add(custom);
        custom.addActionListener((ActionEvent event) -> {
            if (!this.showCustomDialog()) {
                custom.setSelected(false);
                this.selected.setSelected(true);
            } else {
                this.selected = custom;
            }
        });
        menu.add(custom);

        menu.addSeparator();

        // shows the high scores across all sessions of the game
        JMenuItem scores = new JMenuItem("High Scores", KeyEvent.VK_S);
        scores.getAccessibleContext().setAccessibleDescription(
                "This brings up the high score screen");
        scores.addActionListener((ActionEvent event) -> {
            Object[] options = { "Reset Scores", "Close"};
            int n = JOptionPane.showOptionDialog(this, game.getHighScores(),
                    "Fastest Mine Sweepers", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
            if (n == 0) {
                game.getHighScores().resetScores();
            }
        });
        menu.add(scores);
    }

    // creates the option menu and adds it to the menu bar
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
        sound.addActionListener((ActionEvent event) -> {
            this.game.getBoard().toggleSound();
        });
        menu.add(sound);
    }

    // creates the help menu and adds it to the menu bar
    private void makeHelpMenu() {
        JMenu menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);

        menu.getAccessibleContext().setAccessibleDescription(
                "The menu which gives you help"
        );
        this.add(menu);

        // some instructions on how to play!
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
            JOptionPane.showMessageDialog(this, instrMess, "Instructions",
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon("resources/images/mark.png"));
        });

        menu.add(instr);


        // some info about the creator
        JMenuItem about = new JMenuItem("About", KeyEvent.VK_A);
        about.getAccessibleContext().setAccessibleDescription(
                "This brings up the about screen");

        message = "About:\n";
        message += "Created by Emily Pazienza\n";
        message += "2018";
        final String abtMess = message;
        about.addActionListener((ActionEvent event) -> {
            JOptionPane.showMessageDialog(this, abtMess, "About",
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon("resources/images/mark.png"));
        });
        menu.add(about);
    }

    // shows the dialog for the user to enter custom options for the board
    private boolean showCustomDialog() {
        JPanel panel = new JPanel(new GridLayout(0, 2));

        NumberFormatter format = new NumberFormatter(NumberFormat.getInstance()) {
            public Object stringToValue(String text) throws ParseException {
                if ("".equals(text) || text.length() == 0) {
                    return null;
                }
                return super.stringToValue(text);
            }
        };
        format.setValueClass(Integer.class);
        format.setMaximum(50);
        format.setAllowsInvalid(false);

        JFormattedTextField height = new JFormattedTextField(format);
        height.setColumns(10);
        JFormattedTextField width = new JFormattedTextField(format);
        width.setColumns(10);
        format.setMaximum(500);
        JFormattedTextField mines = new JFormattedTextField(format);
        mines.setColumns(10);

        panel.add(new JLabel("Height: (1-50)"));
        panel.add(height);
        panel.add(new JLabel("Width: (1-50)"));
        panel.add(width);
        panel.add(new JLabel("Mines: (1-500)"));
        panel.add(mines);

        int option = JOptionPane.showConfirmDialog(
                null, panel, "Custom",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            if (height.getText().equals("")|| width.getText().equals("") || mines.getText().equals("")) {
                return false;
            }

            int h = Integer.parseInt(height.getText());
            int w = Integer.parseInt(width.getText());
            int m = Integer.parseInt(mines.getText());

            if (m > h * w) {
                JOptionPane.showMessageDialog(this,
                        "Number of mines must be less than number of squares.",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                this.game.newGame(w, h, m);
                return true;
            }
        }
        return false;
    }

    public void actionPerformed(ActionEvent e) {

    }
}