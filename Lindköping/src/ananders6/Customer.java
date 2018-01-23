package ananders6;
import java.util.ArrayList;


public class Customer {
  
  private String name;
  private String lastname;
  private String ssn;
  private ArrayList<SavingsAccount> Accounts = new ArrayList();
  
  public Customer(String fname, String lname, String sNumber) {
    name = fname;
    lastname = lname;
    ssn = sNumber;
  }

  public void changeName() {
    System.out.print("Current name: ");
    System.out.print(name + " " + lastname);
    System.out.println("What would you like to change the name to? >: ");

  }
  
  public void addAccount() {
    System.out.println("Adding new accout...");
    SavingsAccount account = new SavingsAccount();
    Accounts.add(account);
  
  }
  
  public void getCustomerAccountInfo() {

  }
  
  public String getpNo() {
    return ssn;
  }
  
  public String getCustomerName() {
    return name;
  }
  
  public String getCustLastane() {
    return lastname;
  }
  
  public String getCustomerInfo() {
    String result;
    result = name + ' ' + lastname + ' ' + ssn;
    return result;
  }

}