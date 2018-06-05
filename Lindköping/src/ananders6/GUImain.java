package ananders6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.List;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

  /**
   * Konstruktor for GUImain
   */
  public GUImain() {
    initiateInstanceVariables();
    buildFrame();
    buildMenu(this);
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
    nameField.setBounds(10, 10, 200, 35);

    lastnameField = new JTextField();
    lastnameField.setBounds(10, 50, 200, 35);

    pNrField = new JTextField();
    pNrField.setBounds(10, 90, 200, 35);

    nameField.setBorder(BorderFactory.createTitledBorder("Name"));
    lastnameField.setBorder(BorderFactory.createTitledBorder("Lastname"));
    pNrField.setBorder(BorderFactory.createTitledBorder("pNr"));

  }

  /**
   * Skapar ramar, aktiverar actionlisteners för listorna.
   */
  private void buildFrame() {
    setTitle("Bank");
    setLocation(100, 100);
    setLayout(null);
    setPreferredSize(new Dimension(5000, 5000));
    setMinimumSize(new Dimension(1200, 500));

    JPanel bankpanel = new JPanel();
    bankpanel.setLayout(null);
    bankpanel.setBounds(5, 5, 2000, 2000);

    JScrollPane customerListPane = new JScrollPane(customerList);
    customerList.getSelectionModel().addListSelectionListener(accountTable);
    customerListPane.setBounds(250, 90, 250, 1500);

    JScrollPane accountTablePane = new JScrollPane(accountTable);
    accountTablePane.setBounds(520, 90, 280, 1500);

    JScrollPane transactionTablePane = new JScrollPane(transactionTable);
    transactionTablePane.setBounds(820, 90, 270, 1500);

    bankpanel.add(customerListPane);
    bankpanel.add(accountTablePane);
    bankpanel.add(transactionTablePane);

    bankpanel.add(nameField);
    bankpanel.add(lastnameField);
    bankpanel.add(pNrField);

    bankpanel.add(addButton);
    bankpanel.add(editButton);

    bankpanel.add(createSavingsAccountButton);
    bankpanel.add(createCreditAccountButton);

    bankpanel.add(transferButton);
    bankpanel.add(deleteButton);

    addButton.addActionListener(this);
    addButton.setBounds(10, 130, 120, 30);

    editButton.addActionListener(this);
    editButton.setBounds(10, 170, 120, 30);

    createSavingsAccountButton.addActionListener(this);
    createSavingsAccountButton.setBounds(250, 10, 170, 30);

    createCreditAccountButton.addActionListener(this);
    createCreditAccountButton.setBounds(250, 50, 170, 30);

    deleteButton.addActionListener(this);
    deleteButton.setBounds(10, 210, 120, 30);

    transferButton.addActionListener(this);
    transferButton.setBounds(520, 50, 170, 30);

    accountModel.addTableModelListener(accountTable);
    ListSelectionModel select = accountTable.getSelectionModel();
    select.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    add(bankpanel);
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

  /**
   * Skapar en meny för programmet.
   * 
   * @param JFrame
   */

  public void buildMenu(JFrame frame) {

    JMenuBar menubar = new JMenuBar();
    JMenu menu = new JMenu("Menu");
    JMenuItem menuItem = new JMenuItem("New Bank Window");
    JMenuItem exportTransaction = new JMenuItem("Export Transaction Data");
    JMenuItem importBankData = new JMenuItem("Import Bank Data");
    JMenuItem exportBankData = new JMenuItem("Export Bank Data");

    JMenuItem menuItemExist = new JMenuItem("Exit");

    menuItemExist.setMnemonic(KeyEvent.VK_E);

    menuItemExist.addActionListener((ActionEvent event) -> {
      System.exit(0);
    });

    menuItem.addActionListener((ActionEvent event) -> {
      GUImain newBank = new GUImain();
    });

    exportTransaction.addActionListener((ActionEvent event) -> {

    });

    // EXPORTING DATA TO FILE _______________________________________
    exportBankData.addActionListener((ActionEvent event) -> {
      ArrayList<Customer> customers = new ArrayList<>();

      try {
        FileOutputStream file = new FileOutputStream("CustomersFile.dat");
        ObjectOutputStream BankOutFile = new ObjectOutputStream(file);

        for (int i = 0; i < customerList.getModel().getSize(); i++) {
          String selectedCustomerpNr = customerList.getModel().getElementAt(i).toString().split(" ")[2];
          System.out.println("EXPORTING CUSTOMER: " + selectedCustomerpNr);
          Customer customer = logic.matchCustomer(selectedCustomerpNr);
          customers.add(customer);
        }
        BankOutFile.writeObject(customers);
        BankOutFile.close();
        file.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // IMPORTING DATA _______________________________________
    importBankData.addActionListener((

        ActionEvent event) -> {

      try {
        FileInputStream file = new FileInputStream("CustomersFile.dat");
        ObjectInputStream allCustomers = new ObjectInputStream(file);
        ArrayList<Customer> customersInFile = new ArrayList<>();
        customersInFile = (ArrayList<Customer>) allCustomers.readObject();
        int customerAmount = logic.getAmountOfCustomers();
        // Import only those customers who are not in the bank.

        // if there are customers in the file
        if (!customersInFile.isEmpty() && customerAmount != 0) {

          // For every customers in the file
          for (int i = 0; i < customersInFile.size(); i++) {
            Customer customer = customersInFile.get(i);
            System.out.println(customerAmount);
            System.out.println(customersInFile.size());

            // if it is not in the bank then add it.
            if (logic.matchCustomer(customer.getCustomerpNr()) == null) {
              String customerData = customer.getCustomerInfo();
              System.out.println("IMPORTING CUSTOMER: " + customerData);
              customerModel.addElement(customerData);
              logic.addExistingCustomer(customersInFile.get(i));
            }
          }
        } else if (!customersInFile.isEmpty() && customerAmount == 0) {
          for (int i = 0; i < customersInFile.size(); i++) {
            Customer customer = customersInFile.get(i);
            if (logic.matchCustomer(customer.getCustomerpNr()) == null) {
              String customerData = customer.getCustomerInfo();
              System.out.println("IMPORTING CUSTOMER: " + customerData);
              customerModel.addElement(customerData);
              logic.addExistingCustomer(customersInFile.get(i));
            }
          }
        }
      } catch (IOException e1) {
        System.out.println("IOException!");
        System.out.println("There is no import file");
        e1.printStackTrace();

      } catch (ClassNotFoundException e2) {
        System.out.println("ClassNotFoundException");
        e2.printStackTrace();
      }

    });

    menu.add(menuItem);
    menu.add(exportTransaction);
    menu.add(importBankData);
    menu.add(exportBankData);
    menu.add(menuItemExist);
    menubar.add(menu);

    frame.setJMenuBar(menubar);
    frame.setSize(400, 400);
    frame.setVisible(true);

  }

  // UI LOGIC. This activated the functions below.
  public void actionPerformed(ActionEvent event) {
    String buttonText = event.getActionCommand();

    if (buttonText.equals("Add Customer")) {
      addCustomer();
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
    String selectedCustomerpNr;
    Customer customer;
    ArrayList<String> accounts;
    String accountAsString;
    String[] accountAsList;
    int accountNumber;
    String accountData;
    List<String> AccounItems;
    int selectedIndex = customerList.getSelectedIndex();
    if (selectedIndex != -1) {
      selectedCustomerpNr = customerList.getSelectedValue().toString().split(" ")[2];
      customer = logic.matchCustomer(selectedCustomerpNr);
      accounts = customer.getAllCustomerAccountInfo();
      for (int i = accounts.size(); 0 < i; i--) {
        accountAsString = accounts.get(i - 1);
        accountAsList = accountAsString.split(" ");
        accountNumber = Integer.parseInt(accountAsList[0]);
        accountData = logic.getAccount(selectedCustomerpNr, accountNumber);
        AccounItems = Arrays.asList(accountData.split(" "));
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

    // REgex kopierades från
    // https://stackoverflow.com/questions/18590901/check-if-a-string-contains-numbers-java?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
    if (nameF.matches(".*\\d+.*") || lastF.matches(".*\\d+.*")) {
      JOptionPane.showMessageDialog(null, "Names can't contain integers.");
      return;
    }

    if (nameF.isEmpty() || lastF.isEmpty() || pNrF.isEmpty()) {
      JOptionPane.showMessageDialog(null, "You can't have an empty field.");
      return;
    }

    if (!pNrF.matches("-?\\d+")) {
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
   * Slapar ett kredit-konto för en target kund och skriver in den i listan. Går
   * inte ta up mer än 5000kr.
   * 
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

    } else {
      JOptionPane.showMessageDialog(null, "Select a customer.");
    }
  }

  /**
   * Slapar ett kredit-konto för en target kund och skriver in den i listan.
   * 
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

    } else {
      JOptionPane.showMessageDialog(null, "Select a customer.");
    }
  }

  /**
   * Tar bort kunden eller bara kontot beroende på vad som har klickats i
   * listorna.
   * 
   * @return void
   */
  public void delete() {
    int selectedCustomerIndex = -1;
    String selectedCustomerItems = null;
    String selectedAccountItems;
    String pNr;
    int selectedAccountIndex = -1;
    int accountID;
    int deleteAccountConfirm;

    selectedAccountIndex = accountTable.getSelectedRow();
    selectedCustomerIndex = customerList.getSelectedIndex();

    // Om både kunden och ett av konton har valts tas endast kontot bort.
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

    // Om det är endast kunden som har ett giltigt select index så tas kunden bort.
    if (selectedAccountIndex == -1 && selectedCustomerIndex != -1) {
      deleteAccountConfirm = JOptionPane.showConfirmDialog((Component) null,
          "Are you sure you want to delete selected customer?", "Confirm", JOptionPane.YES_NO_OPTION);

      if (deleteAccountConfirm == JOptionPane.YES_OPTION) {
        selectedCustomerItems = customerModel.getElementAt(selectedCustomerIndex).toString();
        List<String> items = Arrays.asList(selectedCustomerItems.split(" "));
        pNr = items.get(2);
        System.out.println(pNr);
        logic.deleteCustomer(pNr);
        customerModel.removeElementAt(selectedCustomerIndex);
        // Tar bort all data från tables som är relevant för kontot.
        for (String pNrs : enteredpNrs) {
          if (pNrs == pNr) {
            enteredpNrs.remove(pNr);
          }
        }
        customerList.clearSelection();
        clearAccounts();
        clearTransactions();
      }
    } else {
      return;
    }

  }

  /**
   * Instantierar nytt fönster för att skicka över pengar.
   * 
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
    } catch (NullPointerException e) {
      JOptionPane.showMessageDialog(null, "Select an account.");
      return;
    }
    // Convert to list.
    List<String> items = Arrays.asList(selectedItems.split(" "));
    // Get personla nunmber in order to get account.
    pNr = items.get(2);
    // Get account index from JTable model.
    accountNumberIndex = accountTable.getSelectedRow();

    try {
      // Get account data from JTable model.
      accountTableData = accountModel.getValueAt(accountNumberIndex, 0);
      accountNumber = Integer.parseInt(accountTableData.toString());
      // Get customer object.
      customer = logic.matchCustomer(pNr);
      // Get account object.
      account = customer.matchAccount(accountNumber);
      // Create transaction options window.
      m = new MoneyClass(account, transactionModel, accountModel);
      // Set window to visible.
      m.setVisible(true);
      // Get account ID
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("You have to select an account first.");
    }
  }

  /**
   * Ändrar personens för ock efternamn.
   * 
   * @return void
   */
  private void editCustomerData() {
    int position = customerList.getSelectedIndex();
    if (position > -1) {
      String nameF = nameField.getText();
      String lastF = lastnameField.getText();
      String pNrF = logic.getpNrAt(position);
      String customerData = nameF + " " + lastF + " " + pNrF;

      if (nameF.matches(".*\\d+.*") || lastF.matches(".*\\d+.*")) {
        JOptionPane.showMessageDialog(null, "Names can't contain integers.");
        return;
      }

      if (nameF.isEmpty() || lastF.isEmpty() || pNrF.isEmpty()) {
        JOptionPane.showMessageDialog(null, "You can't have an empty field.");
        return;
      }

      logic.changeCustomerName(nameF, lastF, pNrF);
      customerModel.removeElementAt(position);
      customerModel.insertElementAt(customerData, position);
    } else {
      JOptionPane.showMessageDialog(null, "You need a person in the list!");
    }
  }

  /**
   * Visar överförings-knappen.
   * 
   * @return void
   */
  private void showTransactionButtons() {
    transferButton.setVisible(true);
  }

  /**
   * Rensar transaktionsListan.
   * 
   * @return void
   */
  private void clearTransactions() {
    transactionModel.setRowCount(0);
  }

  /**
   * Rensar kontolistan. Används vid uppdatering.
   * 
   * @return void
   */
  private void clearAccounts() {
    accountModel.setRowCount(0);
  }

  /**
   * Rensar inpufälten, transaktionerna och konton.
   * 
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
   * 
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
