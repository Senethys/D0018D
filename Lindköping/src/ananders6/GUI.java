package ananders6;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

  private static final long serialVersionUID = 1L;
  private static final int  FRAME_WIDTH      = 300;
  private static final int  FRAME_HEIGHT     = 100;
  private static final int  TEXT_WIDTH       = 20;
  private JButton           button           = new JButton("Add Customer");
  private JLabel            label            = new JLabel("Hello World");
  private JLabel            nameLabel        = new JLabel("Name: ");
  private JLabel            lastnameLabel    = new JLabel("Lastname: ");
  private JLabel            pNrLabel         = new JLabel("pNr: ");
  private JTextField        nameField        = new JTextField(TEXT_WIDTH);

  public GUI() {
    createComponents();
    setSize(FRAME_WIDTH, FRAME_HEIGHT);
    setLocationRelativeTo(null);

  }

  private void createComponents() {

    JPanel panel = new JPanel();
    panel.add(button);
    panel.add(label);
    panel.add(nameField);
    this.add(panel);
   

    ActionListener listener = new ClickListener();
    button.addActionListener(listener);

  }

  public class ClickListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      System.out.println("Du har klickat på knappen!");
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  private void addCustomerText() {
    return;
  }
}
