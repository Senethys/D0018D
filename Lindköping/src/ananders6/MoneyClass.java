package ananders6;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MoneyClass extends JFrame implements ActionListener {

  private static final long serialVersionUID = 1L;
  private Account           account;
  private JPanel            moneyPanel;
  private JTextField        moneyField;
  private JButton           withdrawButton   = new JButton("Withdraw");
  private JButton           depositButton    = new JButton("Deposit");
  protected double          withdrawAmount   = 0;
  protected double          depositAmount    = 0;
  DefaultTableModel         transactionModel;
  DefaultTableModel         accountModel;

  public MoneyClass(Account account, DefaultTableModel transactionModel, DefaultTableModel accountModel) {

    this.accountModel = accountModel;
    this.transactionModel = transactionModel;
    this.account = account;
    initiateVariables();
   
  }

  public void initiateVariables() {
    setTitle("Money to transfer");
    setSize(300, 300);
    setLayout(new GridLayout(1, 2));
    moneyPanel = new JPanel(new GridLayout());
    moneyField = new JTextField();
    moneyPanel.add(moneyField);
    moneyField.setBorder(BorderFactory.createTitledBorder("Amount to Transfer"));
    withdrawButton.addActionListener(this);
    depositButton.addActionListener(this);

    add(moneyPanel);
    add(withdrawButton);
    add(depositButton);
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    String buttonText = event.getActionCommand();
    String amount;

    if (buttonText.equals("Withdraw")) {
      transactionModel.setRowCount(0);
      amount = moneyField.getText();
      withdrawAmount = Double.parseDouble(amount);
      account.withdraw(withdrawAmount);
      this.setVisible(false);
      updateTransactionTables();
      dispose();
      
    }
    
    if (buttonText.equals("Deposit")) {
      transactionModel.setRowCount(0);
      amount = moneyField.getText();
      if(Integer.parseInt(amount) < 0) {
        JOptionPane.showMessageDialog(null, "You can't deposit a negative amount");
      } 
      else {
      depositAmount = Double.parseDouble(amount);
      account.deposit(depositAmount);
      this.setVisible(false);
      updateTransactionTables();
      dispose();
      }
    }

  }

  public void updateTransactionTables() {
  
    ArrayList<Transaction> transactions = account.getAccountTransactions();
    for (Transaction t : transactions) {
      String[] transactionDetails = t.getTransacionDetails().split(" ");
      System.out.println(transactionDetails);
      transactionModel.addRow(
          new String[] { transactionDetails[0], transactionDetails[1], transactionDetails[2], transactionDetails[3] });
      System.out.println(transactionDetails);
    }

  }
}