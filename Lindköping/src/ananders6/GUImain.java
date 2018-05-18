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
  
  MouseListener mouseListener;
  
  private DefaultTableModel accountModel  = new DefaultTableModel(0, 0);
  private DefaultTableModel transactionModel = new DefaultTableModel(0, 0);
  private DefaultListModel<Object> CustomerModel = new DefaultListModel<>();

  
  private JButton addButton                  = new JButton("Add Customer");
  private JButton showButton                 = new JButton("Show");
  private JButton clearButton                = new JButton("Rensa");
  private JButton createSavingsAccountButton = new JButton("Create Savings Account");
  private JButton createCreditAccountButton  = new JButton("Create Credit Account");
  private JButton deleteAccountButton        = new JButton("Delete Account");
  private JButton transferButton             = new JButton("Transfer Money");


  String[] accountColumns     = { "ID", "Balance", "Account Type", "Interest" };
  String[] transactionColumns = { "Date", "Time", "Amount", "Balance" };
  String   testdata[][]       = { { "101", "Amit", "670000" }, { "102", "Jai", "780000" },
      { "101", "Sachin", "700000" } };

  public GUImain() {
    initiateInstanceVariables();
    buildFrame();
  }

  private void initiateInstanceVariables() {
    //BANK LOGIC
    logic = new BankLogic();

    //CUSTOMERS
    customerList = new JList<Object>();
    customerList.setModel(CustomerModel);

    
    //ACOUNTS
    accountTable = new JTable() {
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    accountModel.setColumnIdentifiers(accountColumns);
    accountTable.setModel(accountModel);

    //TRANSACTIONS
    transactionTable = new JTable() {
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
    setSize(300, 250);
    setLayout(new GridLayout(1, 2));

    JPanel bankpanel = new JPanel(new GridLayout(5, 1));
    bankpanel.add(nameField);
    bankpanel.add(lastnameField);
    bankpanel.add(pNrField);
    bankpanel.add(addButton);
    bankpanel.add(showButton);
    bankpanel.add(clearButton);
    bankpanel.add(createSavingsAccountButton);
    bankpanel.add(createCreditAccountButton);
    bankpanel.add(transferButton);
  

    createSavingsAccountButton.setVisible(false);
    createCreditAccountButton.setVisible(false);
    transferButton.setVisible(false);

    addButton.addActionListener(this);
    showButton.addActionListener(this);
    clearButton.addActionListener(this);
    createSavingsAccountButton.addActionListener(this);
    createCreditAccountButton.addActionListener(this);
    deleteAccountButton.addActionListener(this);
    transferButton.addActionListener(this);
    
    customerList.getSelectionModel().addListSelectionListener(accountTable);
   
    ListSelectionModel select = accountTable.getSelectionModel();
    select.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

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
  }
    
    public void showAccounts() {
      
      while(accountModel.getRowCount() != 0) {
        accountModel.removeRow(0);
      }
      
      String selectedCustomerpNr = customerList.getSelectedValue().toString().split(" ")[2];
      Customer customer = logic.matchCustomer(selectedCustomerpNr);
      
      
    ArrayList<String> accounts = customer.getAllCustomerAccountInfo();


    for(int i = accounts.size(); 0 < i; i--) {
      String testa = accounts.get(i - 1);
      String[] testb = testa.split(" "); 
      int accountNumber = Integer.parseInt(testb[0]);
      String accountData = logic.getAccount(selectedCustomerpNr, accountNumber);
      List<String> AccounItems = Arrays.asList(accountData.split(" "));
      accountModel.addRow(new String[] { AccounItems.get(0), AccounItems.get(1), AccounItems.get(2), AccounItems.get(3) });
    }
    }
   
          


  private void addCustomer() {
    logic.createCustomer(nameField.getText(), lastnameField.getText(), pNrField.getText());
    customerList.setListData(logic.getAllCustomers().toArray());
    clear();
  }

  // get targeted customer
  public void createCreditAccount() {
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
    accountModel.addRow(new String[] { AccounItems.get(0), AccounItems.get(1), AccounItems.get(2), AccounItems.get(3) });
  }

  public void createSavingsAccount() {
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
    accountModel.addRow(new String[] { AccounItems.get(0), AccounItems.get(1), AccounItems.get(2), AccounItems.get(3) });
  }


  public void transfer() {
    MoneyClass m = new MoneyClass();
    double moneyToTransfer;
    m.setVisible(true);
    
    if(m.withdrawAmount > 0) {
     moneyToTransfer =  m.withdrawAmount;
     //logic.getAccount(pNr, accountId);
    }
    
   
    
    int accountNumber;
    String AccountData;
    String selectedItems;
    String pNr;
    ArrayList<String> items = new ArrayList<String>();

    for (int count = 0; count < accountTable.getColumnCount(); count++) {
      selectedItems = accountTable.getValueAt(accountTable.getSelectedRow(), count).toString();
      System.out.println("Items selected: " + selectedItems);
      items.add(selectedItems);
      System.out.println("Items: " + items);

    }

    // accountNumber = logic.createSavingsAccount(pNr);
    // AccountData = logic.getAccount(pNr, accountNumber);
    // List<String> AccounItems = Arrays.asList(AccountData.split(" "));
    // model.addRow(new String[] { AccounItems.get(0), AccounItems.get(1),
    // AccounItems.get(2), AccounItems.get(3)});
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
  }
}