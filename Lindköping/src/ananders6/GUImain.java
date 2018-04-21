package ananders6;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


//YOU NEED TO ADD A FIELD in the GUI FOR NAME, LASTNAME AND PNR
public class GUImain extends JFrame implements ActionListener {
  private BankLogic  logic;
  private JList      personList;
  private JTextField nameField;
  private JTextField phoneNrField;

  public static void main(String[] args) {
    GUImain frame = new GUImain();
    frame.setVisible(true);
  }

  public GUImain() {
    initiateInstanceVariables();
    buildFrame();
  }

  private void initiateInstanceVariables() {
    logic = new BankLogic();

    nameField = new JTextField();
    nameField.setBorder(BorderFactory.createTitledBorder("Namn"));
    phoneNrField = new JTextField();
    phoneNrField.setBorder(BorderFactory.createTitledBorder("Telefonnummer"));
    personList = new JList();
    personList.setBorder(BorderFactory.createTitledBorder("Registrerade personer"));
  }

  private void buildFrame() {
    setTitle("Person register");
    setSize(300, 250);
    setLayout(new GridLayout(1, 2));

    JPanel leftPanel = new JPanel(new GridLayout(5, 1));
    leftPanel.add(nameField);
    leftPanel.add(phoneNrField);
    JButton addButton = new JButton("Lägg till");
    addButton.addActionListener(this);
    leftPanel.add(addButton);
    JButton showButton = new JButton("Visa");
    showButton.addActionListener(this);
    leftPanel.add(showButton);
    JButton clearButton = new JButton("Rensa");
    clearButton.addActionListener(this);
    leftPanel.add(clearButton);
    add(leftPanel);

    add(personList);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void actionPerformed(ActionEvent event) {
    String buttonText = event.getActionCommand();
    if (buttonText.equals("Lägg till")) {
      add();
    }
    if (buttonText.equals("Visa")) {
      showSelected();
    }
    if (buttonText.equals("Rensa")) {
      clear();
    }
  }

  private void add() {
    logic.createCustomer(nameField.getText(), phoneNrField.getText());
    personList.setListData(logic.getAllCustomers().toArray());
    clear();
  }

  private void showSelected() {
    int position = personList.getSelectedIndex();
    if (position > -1) {
      nameField.setText(logic.getNameForPersonAt(position));
      phoneNrField.setText(logic.getPhoneNrForPersonAt(position));
    } else {
      JOptionPane.showMessageDialog(null, "Du måste markera en person i listan!");
    }
  }

  private void clear() {
    nameField.setText("");
    phoneNrField.setText("");
  }
}