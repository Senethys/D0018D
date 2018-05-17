package ananders6;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MoneyClass extends JFrame implements ActionListener {

  private static final long serialVersionUID = 1L;
  private JPanel            moneyPanel;
  private JTextField        moneyField;
  private JButton           withdrawButton   = new JButton("Withdraw");
  private JButton           depositButton   = new JButton("Deposit");
  private String            buttonText;
  protected double             withdrawAmount = 0;
  protected double             depositAmount = 0;

  public MoneyClass() {
    initiateVariables();

  }

  public void initiateVariables() {
    setTitle("Money to transfer");
    setSize(300, 300);
    setLayout(new GridLayout(1, 2));
    JPanel moneyPanel = new JPanel(new GridLayout());
    JTextField moneyField = new JTextField();
    moneyPanel.add(moneyField);
    moneyField.setBorder(BorderFactory.createTitledBorder("Amount to Transfer"));
    add(moneyPanel);
    add(withdrawButton);
    add(depositButton);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    String buttonText = event.getActionCommand();
    String amount;
    if (buttonText.equals("Withdraw")) {
      amount = moneyField.getText();
      withdrawAmount = Double.parseDouble(amount);
      super.dispose();
      
    }
    if (buttonText.equals("Deposit")) {
      amount = moneyField.getText();
      depositAmount = Double.parseDouble(amount);
     
    }
  }
}