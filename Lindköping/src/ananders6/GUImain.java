package ananders6;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.List;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;

//YOU NEED TO ADD A FIELD in the GUI FOR NAME, LASTNAME AND PNR

public class GUImain extends JFrame implements ActionListener {

  private static final long serialVersionUID = 1L;
  private BankLogic         logic;
  private JList<Object>     customerList;
  private JTable            accountTable;
  private JTable            transactionTable;
  private JTextField        nameField;
  private JTextField        lastnameField;
  private JTextField        pNrField;

  private DefaultTableModel        accountModel     = new DefaultTableModel(0, 0);
  private DefaultTableModel        transactionModel = new DefaultTableModel(0, 0);
  private DefaultListModel<Object> CustomerModel    = new DefaultListModel<>();

  private JButton editCustomerButton         = new JButton("Edit Customer Details");
  private JButton addButton                  = new JButton("Add Customer");
  private JButton showButton                 = new JButton("Show");
  private JButton clearButton                = new JButton("Rensa");
  private JButton createSavingsAccountButton = new JButton("Create Savings Account");
  private JButton createCreditAccountButton  = new JButton("Create Credit Account");
  private JButton deleteCustomerButton       = new JButton("Delete Customer");
  private JButton deleteAccountButton        = new JButton("Delete Account");
  private JButton transferButton             = new JButton("Transfer Money");

  String[] accountColumns     = { "ID", "Balance", "Account Type", "Interest" };
  String[] transactionColumns = { "Date", "Time", "Amount", "Balance" };

  public GUImain() {
    initiateInstanceVariables();
    buildFrame();
  }

  private void initiateInstanceVariables() {
    // BANK LOGIC
    logic = new BankLogic();

    // CUSTOMERS
    customerList = new JList<Object>();
    customerList.setModel(CustomerModel);

    // ACOUNTS
    accountTable = new JTable() {
      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    accountModel.setColumnIdentifiers(accountColumns);
    accountTable.setModel(accountModel);

    // TRANSACTIONS
    transactionTable = new JTable() {
      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    transactionModel.setColumnIdentifiers(transactionColumns);
    transactionTable.setModel(transactionModel);

    nameField = new JTextField();
    lastnameField = new JTextField();
    pNrField = new JTextField();

    nameField.setBorder(BorderFactory.createTitledBorder("Name"));
    lastnameField.setBorder(BorderFactory.createTitledBorder("Lastname"));
    pNrField.setBorder(BorderFactory.createTitledBorder("pNr"));

  }

  private void buildFrame() {
    setTitle("Bank");
    setSize(1000, 500);
    setLocation(100, 100);
    setLayout(new GridLayout(1, 2));

    JPanel bankpanel = new JPanel(new GridLayout(5, 1));

    bankpanel.add(nameField);
    bankpanel.add(lastnameField);
    bankpanel.add(pNrField);
    bankpanel.add(addButton);
    bankpanel.add(showButton);
    bankpanel.add(editCustomerButton);
    bankpanel.add(clearButton);
    bankpanel.add(createSavingsAccountButton);
    bankpanel.add(createCreditAccountButton);
    bankpanel.add(transferButton);
    bankpanel.add(deleteAccountButton);
    bankpanel.add(deleteCustomerButton);

    createSavingsAccountButton.setVisible(false);
    createCreditAccountButton.setVisible(false);
    transferButton.setVisible(false);
    addButton.addActionListener(this);
    showButton.addActionListener(this);
    clearButton.addActionListener(this);
    createSavingsAccountButton.addActionListener(this);
    createCreditAccountButton.addActionListener(this);
    deleteAccountButton.addActionListener(this);
    deleteCustomerButton.addActionListener(this);
    transferButton.addActionListener(this);
    customerList.getSelectionModel().addListSelectionListener(accountTable);

    ListSelectionModel select = accountTable.getSelectionModel();
    select.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    
    accountTable.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        if (e.getClickCount() == 1){
          String selectedCustomerpNr = customerList.getSelectedValue().toString().split(" ")[2];
          Customer customer = logic.matchCustomer(selectedCustomerpNr);
          int accountID = accountTable.getSelectedRow();
          Account account = customer.matchAccount(accountID);
          showTransactions(account);
           }
        }
       });
    
    customerList.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        if (e.getClickCount() == 1){
          showAccounts();
           }
        }
       });
  
    add(bankpanel);
    add(customerList);
    add(new JScrollPane(accountTable), BorderLayout.CENTER);
    add(new JScrollPane(transactionTable), BorderLayout.CENTER);

    setDefaultCloseOperation(EXIT_ON_CLOSE);

  }

  // UI LOGIC. This activated the functions below.
  public void actionPerformed(ActionEvent event) {
    String buttonText = event.getActionCommand();
    if (buttonText.equals("Add Customer")) {

      addCustomer();
      showAccountButtons();
    }
    if (buttonText.equals("Show")) {
      showSelected();
    }

    if (buttonText.equals("Show") && customerList.getSelectedValue() != null) {
      showAccounts();
    }

    if (buttonText.equals("Clear")) {
      clear();
    }
    
    if (buttonText.equals("Create Savings Account")) {
      createSavingsAccount();
      showTransactionButtons();

    }
    
    if (buttonText.equals("Create Credit Account")) {
      createCreditAccount();
      showTransactionButtons();
    }
    
    if (buttonText.equals("Transfer Money")) {
      transfer();
    }
    
    if (buttonText.equals("Delete Account")) {
      transfer();
    }
    
    if (buttonText.equals("Transfer Money")) {
      transfer();
    }
  }


  public void showAccounts() {

    while (accountModel.getRowCount() != 0) {
      accountModel.removeRow(0);
    }

    String selectedCustomerpNr = customerList.getSelectedValue().toString().split(" ")[2];
    Customer customer = logic.matchCustomer(selectedCustomerpNr);

    ArrayList<String> accounts = customer.getAllCustomerAccountInfo();

    for (int i = accounts.size(); 0 < i; i--) {
      String testa = accounts.get(i - 1);
      String[] testb = testa.split(" ");
      int accountNumber = Integer.parseInt(testb[0]);
      String accountData = logic.getAccount(selectedCustomerpNr, accountNumber);
      List<String> AccounItems = Arrays.asList(accountData.split(" "));
      accountModel
          .addRow(new String[] { AccounItems.get(0), AccounItems.get(1), AccounItems.get(2), AccounItems.get(3) });
    }
  }

  public void showTransactions(Account account) {

    ArrayList<Transaction> transactions = account.getAccountTransactions();
    for (Transaction t : transactions) {
      String[] transactionDetails = t.getTransacionDetails().split(" ");
      System.out.println(transactionDetails);
      transactionModel.addRow(
          new String[] { transactionDetails[0], transactionDetails[1], transactionDetails[2], transactionDetails[3] });
      System.out.println(transactionDetails);
    }
  }

  private void addCustomer() {
    logic.createCustomer(nameField.getText(), lastnameField.getText(), pNrField.getText());
    customerList.setListData(logic.getAllCustomers().toArray());
    transactionModel.setRowCount(0);
    clear();
  }

  // get targeted customer
  public void createCreditAccount() {
    showAccounts();
    int accountNumber;
    String AccountData;
    String selectedItems;
    String pNr;

    selectedItems = customerList.getSelectedValue().toString();
    List<String> items = Arrays.asList(selectedItems.split(" "));
    pNr = items.get(2);
    accountNumber = logic.createCreditAccount(pNr);
    AccountData = logic.getAccount(pNr, accountNumber);
    List<String> AccounItems = Arrays.asList(AccountData.split(" "));
    accountModel
        .addRow(new String[] { AccounItems.get(0), AccounItems.get(1), AccounItems.get(2), AccounItems.get(3) });
    transactionModel.setRowCount(0);
  }

  public void createSavingsAccount() {
    showAccounts();
    int accountNumber;
    String AccountData;
    String selectedItems;
    String pNr;

    selectedItems = customerList.getSelectedValue().toString();
    List<String> items = Arrays.asList(selectedItems.split(" "));
    pNr = items.get(2);
    accountNumber = logic.createSavingsAccount(pNr);
    AccountData = logic.getAccount(pNr, accountNumber);
    List<String> AccounItems = Arrays.asList(AccountData.split(" "));
    accountModel
        .addRow(new String[] { AccounItems.get(0), AccounItems.get(1), AccounItems.get(2), AccounItems.get(3) });
    transactionModel.setRowCount(0);
  }
  
  public void deleteCustomer() {
    showAccounts();
    int accountNumber;
    String AccountData;
    String selectedItems;
    String pNr;

    selectedItems = customerList.getSelectedValue().toString();
    List<String> items = Arrays.asList(selectedItems.split(" "));
    pNr = items.get(2);
    logic.deleteCustomer(pNr);
  
  }
  
  public void deleteAccount() {
    showAccounts();
    int accountNumber;
    String AccountData;
    String selectedItems;
    String pNr;

    selectedItems = customerList.getSelectedValue().toString();
    List<String> items = Arrays.asList(selectedItems.split(" "));
    pNr = items.get(2);
    Customer customer = logic.matchCustomer(pNr);
    Account account = customer.matchAccount(accountId)
  }
  

  public void transfer() {
    transactionModel.setRowCount(0);
    int accountNumber = 0;
    int accountNumberIndex;
    String AccountData;
    String selectedItems;
    String pNr;
    MoneyClass m;
    Customer customer;
    Account account;
    Object accountTableData;

    // Get Customer data from the table
    selectedItems = customerList.getSelectedValue().toString();
    // Convert to list.
    List<String> items = Arrays.asList(selectedItems.split(" "));
    // Get personla nunmber in order to get account.
    pNr = items.get(2);
    // Get account index from JTable model.
    accountNumberIndex = accountTable.getSelectedRow();
    // Get account data from JTable model.
    accountTableData = accountModel.getValueAt(accountNumberIndex, 0);
    // Get account ID
    accountNumber = Integer.parseInt(accountTableData.toString());
    // Get all info about account in the database.
    AccountData = logic.getAccount(pNr, accountNumber);
    // Get customer object.
    customer = logic.matchCustomer(pNr);
    // Get account object.
    account = customer.matchAccount(accountNumber);
    // Create transaction options window.
    m = new MoneyClass(account, transactionModel);
    // Set window to visible.
    m.setVisible(true);

  }

  private void showSelected() {

    int position = customerList.getSelectedIndex();
    if (position > -1) {
      nameField.setText(logic.getNameForPersonAt(position));
      lastnameField.setText(logic.getLastNameForPersonAt(position));
      pNrField.setText(logic.getpNrAt(position));
    } else {
      JOptionPane.showMessageDialog(null, "You need a person in the list!");
    }
  }

  private void showAccountButtons() {
    createSavingsAccountButton.setVisible(true);
    createCreditAccountButton.setVisible(true);
  }

  private void showTransactionButtons() {
    transferButton.setVisible(true);
  }

  private void clear() {
    nameField.setText("");
    lastnameField.setText("");
    pNrField.setText("");
    transactionModel.setRowCount(0);
    accountModel.setRowCount(0);
  }
}