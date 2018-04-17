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

    theView.addNewListeners(new AddNewCustomerListener());
    //theView.addShowListeners(new ShowCustomerListener());
    theView.addClearListeners(new ClearFieldsListener());

  }

  private void addCustomer() {
    // Update the model
    theModel.createCustomer(theView.getName(), theView.getLastname(), theView.getpNr());
  }

  private class AddNewCustomerListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      addCustomer();
    }
  }

//  private class ShowPersonListener implements ActionListener {
//
//    public void actionPerformed(ActionEvent event) {
//      theView.showPerson(theModel);
//    }
//  }

  private class ClearFieldsListener implements ActionListener {

    public void actionPerformed(ActionEvent event) {
      theView.clearTextFields();
    }
  }
}
