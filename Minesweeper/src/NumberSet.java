import javax.swing.*;

public class NumberSet extends JPanel {

    private JLabel digHun, digTen, digOne;
    private int valHun, valTen, valOne;

    public NumberSet() {
        super();

        this.valHun = 0;
        this.valTen = 0;
        this.valOne = 0;

        this.makeLabels();

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(this.digHun);
        this.add(this.digTen);
        this.add(this.digOne);
    }

    private void makeLabels() {
        String fn = "resources/images/times/" + this.numToString(valHun) + ".png";
        ImageIcon hunIcon = new ImageIcon(fn, "The hundreds place");
        this.digHun = new JLabel(hunIcon);

        fn = "resources/images/times/" + this.numToString(valTen) + ".png";
        ImageIcon tenIcon = new ImageIcon(fn, "The hundreds place");
        this.digTen = new JLabel(tenIcon);

        fn = "resources/images/times/" + this.numToString(valOne) + ".png";
        ImageIcon oneIcon = new ImageIcon(fn, "The hundreds place");
        this.digOne = new JLabel(oneIcon);
    }

    private String numToString(int num) {
        switch(num) {
            case 1:
                return "one";
            case 2:
                return "two";
            case 3:
                return "three";
            case 4:
                return "four";
            case 5:
                return "five";
            case 6:
                return "six";
            case 7:
                return "seven";
            case 8:
                return "eight";
            case 9:
                return "nine";
            default:
                return "zero";
        }
    }

    private void repaintOne() {
        String fn = "resources/images/times/" + this.numToString(valOne) + ".png";
        ImageIcon icon = new ImageIcon(fn, "The ones place");
        this.digOne.setIcon(icon);
    }

    private void repaintTen() {
        String fn = "resources/images/times/" + this.numToString(valTen) + ".png";
        ImageIcon icon = new ImageIcon(fn, "The tens place");
        this.digTen.setIcon(icon);
    }

    private void repaintHun() {
        String fn = "resources/images/times/" + this.numToString(valHun) + ".png";
        ImageIcon icon = new ImageIcon(fn, "The hundreds place");
        this.digHun.setIcon(icon);
    }

    public void increment() {
        this.valOne++;
        if (this.valOne > 9 ) {
            this.valOne = 0;
            this.valTen++;
            if (this.valTen > 9) {
                this.valTen = 0;
                this.valHun++;
                this.repaintHun();
                if (this.valHun > 9) {
                    this.valOne = 9;
                    this.valTen = 9;
                    this.valHun = 9;
                    this.makeLabels();
                }
                this.repaintHun();
            }
            this.repaintTen();
        }
        this.repaintOne();
    }

    public void decrement() {
        this.valOne--;
        if (this.valOne == -1) {
            int temp = this.valOne;
            this.valOne = 9;
            this.valTen--;
            if (this.valTen < 0) {
                this.valTen = 9;
                this.valHun--;
                if (this.valHun < 0) {
                    this.valOne = temp;
                    this.valTen = 0;
                    this.valHun = 0;
                }
                this.repaintHun();
            }
            this.repaintTen();
        }
        this.repaintOne();
    }

    public void setVal(int n) {
        if (n > 999 || n < 0) {
            return;
        }

        int h, t, o;
        h = n / 100;
        n -= h * 100;
        t = n / 10;
        n -= t * 10;
        o = n / 1;

        this.valHun = h;
        this.repaintHun();
        this.valTen = t;
        this.repaintTen();
        this.valOne = o;
        this.repaintOne();
    }

    public int getTotal() {
        int n = 100 * this.valHun;
        n += 10 * this.valTen;
        n += this.valOne;
        return n;
    }
}
