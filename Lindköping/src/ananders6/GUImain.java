package ananders6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.List;
import java.awt.event.*;
import java.awt.*;

/**
 * GUI som ansvarar för både grafik och logik.
 * 
 * @author Anna Andersson, ananders-6
 */

public class GUImain extends JFrame implements ActionListener {

  private static final long serialVersionUID = 1L;
  private BankLogic         logic;
  private ArrayList<String> enteredpNrs      = new ArrayList<>();
  private JList<Object>     customerList;
  private JTable            accountTable;
  private JTable            transactionTable;
  private JTextField        nameField;
  private JTextField        lastnameField;
  private JTextField        pNrField;

  private DefaultTableModel        accountModel     = new DefaultTableModel(0, 0);
  private DefaultTableModel        transactionModel = new DefaultTableModel(0, 0);
  private DefaultListModel<Object> customerModel    = new DefaultListModel<>();

  private JButton addButton                  = new JButton("Add Customer");
  private JButton editButton                 = new JButton("Edit");
  private JButton createSavingsAccountButton = new JButton("Create Savings Account");
  private JButton createCreditAccountButton  = new JButton("Create Credit Account");
  private JButton deleteButton               = new JButton("Delete");
  private JButton transferButton             = new JButton("Transfer Money");

  String[] accountColumns     = { "ID", "Balance", "Account Type", "Interest" };
  String[] transactionColumns = { "Date", "Time", "Amount", "Balance" };

  public GUImain() {
    initiateInstanceVariables();
    buildFrame();
  }

  /**
   * Initinerar alla fält och modeller.
   * 
   * @author Anna Andersson, ananders-6
   */

  private void initiateInstanceVariables() {

    // BANK LOGIC
    logic = new BankLogic();

    // CUSTOMERS
    customerList = new JList<Object>();
    customerList.setModel(customerModel);

    /**
     * Stänger av cell-editing.
     */

    // ACOUNTS
    accountTable = new JTable() {

      private static final long serialVersionUID = 1L;

      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    accountModel.setColumnIdentifiers(accountColumns);
    accountTable.setModel(accountModel);

    // TRANSACTIONS
    transactionTable = new JTable() {

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

  /**
   * Skapar ramar, aktiverar actionlisteners för listorna.
   */
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
    bankpanel.add(editButton);

    bankpanel.add(createSavingsAccountButton);
    bankpanel.add(createCreditAccountButton);
    bankpanel.add(transferButton);
    bankpanel.add(deleteButton);

    createSavingsAccountButton.setVisible(false);
    createCreditAccountButton.setVisible(false);
    transferButton.setVisible(false);
    addButton.addActionListener(this);
    editButton.addActionListener(this);
    createSavingsAccountButton.addActionListener(this);
    createCreditAccountButton.addActionListener(this);
    deleteButton.addActionListener(this);
    transferButton.addActionListener(this);
    customerList.getSelectionModel().addListSelectionListener(accountTable);
    accountModel.addTableModelListener(accountTable);

    ListSelectionModel select = accountTable.getSelectionModel();
    select.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    add(bankpanel);
    add(customerList);
    add(new JScrollPane(accountTable), BorderLayout.CENTER);
    add(new JScrollPane(transactionTable), BorderLayout.CENTER);

    setDefaultCloseOperation(EXIT_ON_CLOSE);

    /**
     * Gör så att när man klickar på konton, uppdateras transaktionerna.
     */
    accountTable.addMouseListener(new MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        if (e.getClickCount() == 1) {
          int accountID = Integer.parseInt((String) (accountTable.getValueAt(accountTable.getSelectedRow(), 0)));
          String value = customerList.getSelectedValue().toString();
          String[] data2 = value.split(" ");
          Customer customer = logic.matchCustomer(data2[2]);
          Account account = customer.matchAccount(accountID);
          clearTransactions();
          showTransactions(account);
        }
      }
    });

    /**
     * Gör så att när man klickar på en lista med kunder så uppdateras konton.
     */
    customerList.addMouseListener(new MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        if (e.getClickCount() == 1) {
          clearAccounts();
          showAccounts();
        }
      }
    });
  }

  // UI LOGIC. This activated the functions below.
  public void actionPerformed(ActionEvent event) {
    String buttonText = event.getActionCommand();

    if (buttonText.equals("Add Customer")) {
      addCustomer();
      showAccountButtons();
    }

    if (buttonText.equals("Edit")) {
      editCustomerData();
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

    if (buttonText.equals("Delete")) {
      delete();
    }
  }

  /**
   * Skriver in alla kunder i listan på GUIn. Används för att uppdatera listan
   * efter ändring.
   * 
   * @return void
   */
  public void showCustomers() {
    ArrayList<String> customerData = new ArrayList<>();
    customerData = logic.getAllCustomers();

    for (int i = 0; customerData.size() < i; i++) {
      customerModel.add(i, customerData.get(i));
    }
  }

  /**
   * Skriver in alla kunder i listan på GUIn. Används för att uppdatera listan
   * efter ändring.
   * 
   * @return void
   */
  public void showAccounts() {
    int selectedIndex = customerList.getSelectedIndex();
    if (selectedIndex != -1) {
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
  }

  /**
   * Skriver in alla transaktioner från avgivna kontot på GUIn. Används för att
   * uppdatera listan efter ändring.
   * 
   * @param Account
   * @return void
   */
  public void showTransactions(Account account) {
    try {
      ArrayList<Transaction> transactions = account.getAccountTransactions();
      for (Transaction t : transactions) {
        String[] transactionDetails = t.getTransacionDetails().split(" ");
        transactionModel.addRow(new String[] { transactionDetails[0], transactionDetails[1], transactionDetails[2],
            transactionDetails[3] });
      }
    } catch (NullPointerException e) {
      System.out.println(e);
      System.out.println("You want to transactions to be shown.");
    }
  }

  /**
   * Används för att lägga till en kund. Har flera kontroll mekanismer för att
   * korrekt data ska matas in. Tar inte emot dubbla personnummer.
   * 
   * @return void
   */
  private void addCustomer() {
    String nameF = nameField.getText();
    String lastF = lastnameField.getText();
    String pNrF = pNrField.getText();
    String customerData;

    if (nameF.isEmpty() || lastF.isEmpty() || pNrF.isEmpty()) {
      JOptionPane.showMessageDialog(null, "You can't have an empty field.");
      return;
    }

    if (!isInteger(pNrF)) {
      JOptionPane.showMessageDialog(null, "Personal number must be an integer.");
      return;
    }

    // Ta bort för att få svensk standard för personnummer.
    
    // if(pNrF.length() != 10) {
    // JOptionPane.showMessageDialog(null, "Personal number must be 10 number
    // long.");
    // return;
    // }

    else {

      if (enteredpNrs.contains(pNrF) == true) {
        JOptionPane.showMessageDialog(null, "This personal number already exists.");
        return;
      } else {
        enteredpNrs.add(pNrF);
        logic.createCustomer(nameF, lastF, pNrF);
        customerData = nameField.getText() + " " + lastnameField.getText() + " " + pNrField.getText();
        customerModel.addElement(customerData);
        clearAndUnselect();
      }
    }
  }
  
  /**
   * Slapar ett kredit-konto för en target kund och skriver in den i listan.
   * Går inte ta up mer än 5000kr. 
   * @return void
   */
  public void createCreditAccount() {
    int accountNumber;
    String AccountData;
    String selectedItems;
    String pNr;
    if (customerList.getSelectedValue() != null) {
      selectedItems = customerList.getSelectedValue().toString();
      List<String> items = Arrays.asList(selectedItems.split(" "));
      pNr = items.get(2);
      accountNumber = logic.createCreditAccount(pNr);
      AccountData = logic.getAccount(pNr, accountNumber);
      List<String> AccounItems = Arrays.asList(AccountData.split(" "));
      accountModel
          .addRow(new String[] { AccounItems.get(0), AccounItems.get(1), AccounItems.get(2), AccounItems.get(3) });

    }
  }
  
  /**
   * Slapar ett kredit-konto för en target kund och skriver in den i listan.
   * @return void
   */
  public void createSavingsAccount() {
    int accountNumber;
    String AccountData;
    String selectedItems;
    String pNr;
    if (customerList.getSelectedValue() != null) {
      selectedItems = customerList.getSelectedValue().toString();
      List<String> items = Arrays.asList(selectedItems.split(" "));
      pNr = items.get(2);
      accountNumber = logic.createSavingsAccount(pNr);
      AccountData = logic.getAccount(pNr, accountNumber);
      List<String> AccounItems = Arrays.asList(AccountData.split(" "));
      accountModel
          .addRow(new String[] { AccounItems.get(0), AccounItems.get(1), AccounItems.get(2), AccounItems.get(3) });

    }
  }
  
  /**
   * Tar bort kunden eller bara kontot beroende på vad som har klickats i listorna.
   * @return void
   */
  public void delete() {
    int selectedCustomerIndex = -1;
    String selectedCustomerItems;
    String selectedAccountItems;
    int selectedAccountIndex = -1;
    int accountID;
    String pNr;
    
    selectedAccountIndex = accountTable.getSelectedRow();
    selectedCustomerIndex = customerList.getSelectedIndex();

    //Om både kunden och ett av konton har valts tas endast kontot bort.
    if (selectedAccountIndex != -1 && selectedCustomerIndex != -1) {
      selectedAccountItems = accountModel.getValueAt(selectedAccountIndex, 0).toString();
      List<String> accountItems = Arrays.asList(selectedAccountItems.split(" "));
      accountID = Integer.parseInt(accountItems.get(0));
      selectedCustomerItems = customerModel.getElementAt(selectedCustomerIndex).toString();
      List<String> cutomeritems = Arrays.asList(selectedCustomerItems.split(" "));
      pNr = cutomeritems.get(2);
      Customer c = logic.matchCustomer(pNr);
      c.closeAccount(accountID);
      this.enteredpNrs.remove(String.valueOf(pNr));
      accountModel.removeRow(selectedAccountIndex);
      clearTransactions();
    }
    
    //Om det är endast kunden som har ett giltigt select index så tas kunden bort.
    if (selectedAccountIndex == -1 && selectedCustomerIndex != -1) {
      selectedCustomerItems = customerModel.getElementAt(selectedCustomerIndex).toString();
      List<String> items = Arrays.asList(selectedCustomerItems.split(" "));
      pNr = items.get(2);
      System.out.println(pNr);
      logic.deleteCustomer(pNr);
      customerModel.removeElementAt(selectedCustomerIndex);
      //Tar bort all data från tables som är relevant för kontot.
      customerList.clearSelection();
      clearAccounts();
      clearTransactions();
    }
  }
  
  
  
  /**
   * Instantierar nytt fönster för att skicka över pengar.
   * @return void
   */
  public void transfer() {

    int accountNumber = 0;
    int accountNumberIndex;
    String selectedItems;
    String pNr;
    MoneyClass m;
    Customer customer;
    Account account;
    Object accountTableData;
    try {
    // Get Customer data from the table
    selectedItems = customerList.getSelectedValue().toString();
    } catch(NullPointerException e) {
      JOptionPane.showMessageDialog(null, "Select an account.");
      return;
    }
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
    // Get customer object.
    customer = logic.matchCustomer(pNr);
    // Get account object.
    account = customer.matchAccount(accountNumber);
    // Create transaction options window.
    m = new MoneyClass(account, transactionModel, accountModel);
    // Set window to visible.
    m.setVisible(true);

  }
  
  /**
   * Instantierar nytt fönster för att skicka över pengar.
   * @return void
   */
  private void editCustomerData() {
    int position = customerList.getSelectedIndex();
    if (position > -1) {
      nameField.setText(logic.getNameForPersonAt(position));
      lastnameField.setText(logic.getLastNameForPersonAt(position));
      pNrField.setText(logic.getpNrAt(position));
    } else {
      JOptionPane.showMessageDialog(null, "You need a person in the list!");
    }
  }

  /**
   * Visar konto-tillägsknapparna.
   * @return void
   */
  private void showAccountButtons() {
    createSavingsAccountButton.setVisible(true);
    createCreditAccountButton.setVisible(true);
  }
  
  /**
   * Visar överförings-knappen.
   * @return void
   */
  private void showTransactionButtons() {
    transferButton.setVisible(true);
  }

  /**
   * Rensar transaktionsListan.
   * @return void
   */
  private void clearTransactions() {
    transactionModel.setRowCount(0);
  }

  /**
   * Rensar kontolistan.
   * Används vid uppdatering.
   * @return void
   */
  private void clearAccounts() {
    accountModel.setRowCount(0);
  }

  /**
   * Rensar inpufälten, transaktionerna och konton. 
   * @return void
   */
  private void clearAndUnselect() {
    nameField.setText("");
    lastnameField.setText("");
    pNrField.setText("");
    transactionModel.setRowCount(0);
    accountModel.setRowCount(0);
  }

  
  /**
   * Aänvänds för att kontrollera om personnumret (Sträng) är numeriskt.
   * @param String
   * @return boolean
   */
  public boolean isInteger(String pNr) {
    try {
      Integer.parseInt(pNr);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}