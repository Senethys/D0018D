package ananders6;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class moneyClass extends JFrame implements ActionListener {

  private static final long serialVersionUID = 1L;
  private JPanel            moneyPanel;
  private JTextField        moneyField;
  private JButton           transferButton   = new JButton("Transfer Money");
  private String            buttonText;
  protected int             amountInt;

  public moneyClass() {
    initiateVariables();

  }

  public void initiateVariables() {
    setTitle("Money to transfer");
    setSize(300, 300);
    setLayout(new GridLayout(1, 2));
    JPanel moneyPanel = new JPanel(new GridLayout(5, 1));
    JTextField moneyField = new JTextField();
    moneyPanel.add(moneyField);
    moneyField.setBorder(BorderFactory.createTitledBorder("Amount to deposit"));
    add(moneyPanel);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    String buttonText = event.getActionCommand();
    String amount;
    if (buttonText.equals("Transfer Money")) {
      amount = moneyField.getText();
      amountInt = Integer.parseInt(amount);
    }
  }
}