package ananders6;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the Controller, the Controller is listening after interactions from
 * the user. The Controller updates the data in the model and updates the view
 * with the new data.
 */
public class BankController {

  private BankLogic theModel;
  private BankView  theView;

  public BankController() {
    theView = new BankView();
    theModel = new BankLogic();
    theModel.addObserver(theView); // Registers the model with the view

    //Binds invidivdual buttons to functions
    theView.addNewListeners(new addNewCustomerListener());
    theView.creditListeners(new addNewCreditAccountListener());
    theView.savingsListeners(new addNewSavingsAccountListener());
    theView.addClearListeners(new ClearFieldsListener());
    theView.addShowListeners(new ShowCustomerListener());

  }

  private void addCustomer() {
    // Update the model
    theModel.createCustomer(theView.getName(), theView.getLastname(), theView.getpNr());
  }
  
  
  private void createCreditAccount() {

    theModel.createCreditAccount(theView.getpNr());
  }
  
  private void createSavingsAccount() {
    
    theModel.createSavingsAccount(theView.getpNr());
  }

  private class addNewCustomerListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      addCustomer();
    }
  }
  
  private class addNewCreditAccountListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      createCreditAccount();
    }
  }
  
  private class addNewSavingsAccountListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      createSavingsAccount();
    }
  }

  private class ShowCustomerListener implements ActionListener {

    public void actionPerformed(ActionEvent event) {
      theView.showCustomer(theModel);
    }
  }

  private class ClearFieldsListener implements ActionListener {

    public void actionPerformed(ActionEvent event) {
      theView.clearTextFields();
    }
  }
}
