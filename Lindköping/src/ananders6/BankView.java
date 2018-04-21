package ananders6;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * This is the View, the View is responsible to present the data to the user
 * 
 * @author Susanne Fahlman
 *
 */
public class BankView extends JFrame implements Observer {
  private static final long serialVersionUID           = 7207001945354636294L;
  private JList<Object>     CustomerList;
  private JTable            AccountList;
  private JList<Object>     TransactionList;
  private JTextField        nameField;
  private JTextField        lastnameField;
  private JTextField        pNrField;
  private JButton           newButton                  = new JButton("Add Customer");
  private JButton           showButton                 = new JButton("Visa");
  private JButton           clearButton                = new JButton("Rensa");
  private JButton           createSavingsAccountButton = new JButton("Create Savings Account");
  private JButton           createCreditAccountButton  = new JButton("Create Credit Account");
  private JButton           deleteAccountButton        = new JButton("Delete Account");
  private JButton           withdrawButton             = new JButton("Withdraw");
  private JButton           depositButton              = new JButton("Deposit");
  private JButton           addAccount                 = new JButton("Add Account");

  public BankView() {
    initiateInstanceVariables();
    buildFrame();
    setVisible(true);
  }

  /**
   * Initiate the instance variables
   */
  private void initiateInstanceVariables() {
    nameField = new JTextField();
    nameField.setBorder(BorderFactory.createTitledBorder("Name"));

    lastnameField = new JTextField();
    lastnameField.setBorder(BorderFactory.createTitledBorder("Lastname"));

    pNrField = new JTextField();
    pNrField.setBorder(BorderFactory.createTitledBorder("pNr"));

    CustomerList = new JList<Object>();
    CustomerList.setBorder(BorderFactory.createTitledBorder("Customer List"));

    AccountList = new JTable();
    AccountList.setBorder(BorderFactory.createTitledBorder("Account List"));

    TransactionList = new JList<Object>();
    TransactionList.setBorder(BorderFactory.createTitledBorder("Transaction List"));
  }

  /**
   * Set up the frame
   */
  private void buildFrame() {
    setTitle("Online Bank");
    setSize(600, 400);
    setLayout(new GridLayout(4, 4));

    JPanel customerPanel = new JPanel(new GridLayout(2, 2));
    customerPanel.add(nameField);
    customerPanel.add(lastnameField);
    customerPanel.add(pNrField);
    customerPanel.add(newButton);
    customerPanel.add(showButton);
    customerPanel.add(clearButton);
    add(customerPanel);
    add(CustomerList);

    JPanel AccountPanel = new JPanel(new GridLayout(2, 2));
    AccountPanel.add(createCreditAccountButton);
    AccountPanel.add(createSavingsAccountButton);
    AccountPanel.add(deleteAccountButton);
    AccountPanel.add(withdrawButton);
    AccountPanel.add(depositButton);
    add(AccountPanel);
    add(AccountList);

    JTable TransactiontPanel = new JTable();
    add(TransactiontPanel);
    add(TransactionList);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  /**
   * Returns the text in the name text field
   * 
   * @return the name
   */
  public String getName() {
    return nameField.getText();
  }

  /**
   * Returns the text in the phone text field
   * 
   * @return the phone nr
   */
  public String getLastname() {
    return lastnameField.getText();
  }

  /**
   * Returns the text in the phone text field
   * 
   * @return the phone nr
   */
  public String getpNr() {
    return pNrField.getText();
  }

  /**
   * Returns the index that is selected in the JList
   * 
   * @return index
   */
  public int getIndex() {
    return CustomerList.getSelectedIndex();
  }

  public void showCustomer(BankLogic theModel) {
    // Get the index that is selected
    int position = getIndex();

    // Someone is selected
    if (position > -1) {
      // Retrieve the name and tells the view to show the name
      String text = theModel.getNameForPersonAt(position);
      nameField.setText(text);
      // Retrieve the phone number and tells the view to show the phone number
      text = theModel.getPhoneNrForPersonAt(position);
      pNrField.setText(text);
    } else {
      // Notify the view that no selection was made
      JOptionPane.showMessageDialog(null, "Du måste markera en person i listan!");
    }
  }

  /**
   * Clears the text fields
   */
  public void clearTextFields() {
    nameField.setText("");
    lastnameField.setText("");
    pNrField.setText("");
  }

  /**
   * Adds a listener for the newButton
   * 
   * @param listenForButton
   */
  public void addNewListeners(ActionListener listenForButton) {
    newButton.addActionListener(listenForButton);
  }
  
  public void creditListeners(ActionListener listenForButton) {
    createCreditAccountButton.addActionListener(listenForButton);
  }
  
  public void savingsListeners(ActionListener listenForButton) {
    createSavingsAccountButton.addActionListener(listenForButton);
  }

  /**
   * Adds a listener for the showButton
   * 
   * @param listenForButton
   */
  public void addShowListeners(ActionListener listenForButton) {
    showButton.addActionListener(listenForButton);
  }

  /**
   * Adds a listener for the clearButton
   * 
   * @param listenForButton
   */
  public void addClearListeners(ActionListener listenForButton) {
    clearButton.addActionListener(listenForButton);
  }

  @Override
  public void update(Observable o, Object arg) {
    // We get the Observable object (that is our Logic object) as the first argument
    // The second argument is not used, but could be anything that the Observable
    // object
    // want to sent to the Observer: notifyObservers(arg);
    Object[] newData = ((BankLogic) o).getAllCustomers().toArray();
    CustomerList.setListData(newData);
    clearTextFields();
  }
}