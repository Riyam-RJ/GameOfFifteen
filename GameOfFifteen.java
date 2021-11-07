import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameOfFifteen extends JFrame implements ActionListener {

    public static void main(String[] args) {
        new GameOfFifteen();
    }

    private static final Random RANDOM = new Random();
    JButton start;
    JButton[] buttons = new JButton[16];

    public GameOfFifteen() {
        super("FemtonSpelet");

        start = new JButton("Start");

        start.addActionListener(e -> {
            newGame();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));
        //skapar brickorna (knappar), lägger i en array of buttons och i en panel
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(i + 1 + "");
            panel.add(buttons[i]);
            buttons[i].addActionListener(this);
        }

        buttons[15].setText("");

        JLabel lbl = new JLabel("Tryck på Start för att börja spelet!");

        setLayout(new BorderLayout(10, 10));

        add(BorderLayout.CENTER, panel);
        add(BorderLayout.NORTH, start);
        add(BorderLayout.SOUTH, lbl);

        // lägger label i mitten
        lbl.setHorizontalAlignment(JLabel.CENTER);

        setSize(400, 400);
        setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton source = (JButton) e.getSource();

        int index = 0;
        //Index på brickan där man har tryckt
        for (int i = 0; i < buttons.length; i++) {
            if (source.equals(buttons[i])) {
                index = i;
                break;
            }
        }

        // den här if statement är för att checka om den tomma brickan är åt vänster sidan om den nuvarande index
        /* vi checkar även om man har klickat i den vänstra hörnan, i detta fallet
         kan vi inte checka index -1*/
        if (index != 0 && index != 4 && index != 8 && index != 12 && buttons[index - 1].getText().equals("")) {
            buttons[index - 1].setText(source.getText());
            source.setText("");
        }

        /* här kontrollerar vi index + 1 om den är tomma brickan samt om den är i höger sidan då
         behöver vi inte titta på tomma sidan i index+1 då det finns ingen number i höger sida efter*/
        else if (index != 3 && index != 7 && index != 11 && index != 15 && buttons[index + 1].getText().equals("")) {
            buttons[index + 1].setText(source.getText());
            source.setText("");
        }

        /* här kontrollerar vi nummer under, samt kontrollerar vi om index är
         out of boundaries*/
        else if ((index+4 < buttons.length) && buttons[index + 4].getText().equals("")) {
            buttons[index + 4].setText(source.getText());
            source.setText("");

        /* här kontrollerar vi nummer ovanför, samt kontrollerar vi om index är
        out of boundaries*/
        } else if ((index-4 >= 0) && buttons[index - 4].getText().equals("")) {
            buttons[index - 4].setText(source.getText());
            source.setText("");
        }

        //Checkar vinnande condition
        winning();

    }

    private void winning() {
        //vinnande condition
        for (int i = 0; i <= 14; i++) {
            if (!buttons[i].getText().equals(i + 1 + "")) {
                return;
            }
        }
        if (buttons[15].getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Grattis du vann!\uD83D\uDE00");
        }

    }

    // Lägger brickorna efter varann 1-15 sedan shuffle
    private void newGame() {
        for (int i = 0; i < buttons.length - 1; i++) {
            buttons[i].setText(i + 1 + "");
        }
        buttons[15].setText("");
        shuffle();
    }

    private void shuffle() {
        int n = 15;

        //Shuffle numbers
        while (n > 1) {
            int r = RANDOM.nextInt(n--);
            String tmp = buttons[r].getText();
            buttons[r].setText(buttons[n].getText());
            buttons[n].setText(tmp);
        }
    }
}
