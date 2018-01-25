package ananders6;
import java.util.ArrayList;


public class Customer {
  
  
  
  private String name;
  private String lastname;
  private String ssn;
  private ArrayList<SavingsAccount> Accounts = new ArrayList();
  
  
  
  
  public Customer(String fname, String lname, String sNumber) {
    this.name = fname;
    this.lastname = lname;
    this.ssn = sNumber;
  }
  
  
  

  public void changeName(String newName, String newLastname) {
    System.out.print("Current name: ");
    System.out.print(this.name + " " + this.lastname);
    this.name = newName;
    this.lastname = newLastname;
    System.out.print("New name: ");
    System.out.print(this.name + " " + this.lastname);
  }
  
  
  
  
  public int addAccount() {
    int result;
    System.out.println("Adding new accout...");
    SavingsAccount account = new SavingsAccount();
    Accounts.add(account);
    return account.getAccountNumber();
    
  }
  
  
  
  
  public ArrayList<String> getAllCustomerAccountInfo() {
    
    ArrayList<String> results = new ArrayList();
    String AccountInfo;
    
    for(int i = 0; Accounts.size() > i; i++) {
      AccountInfo = Accounts.get(i).getAccountInfo();
      results.add(AccountInfo);
    }
    
   return results;
  }
  
  
  
  
  
  public String getCustomerAccountInfo(int accountID) {
    
    String AccountInfo = null;
    
    for(int i = 0; Accounts.size() > i; i++) {
      if(accountID == Accounts.get(i).getAccountNumber()) {
        AccountInfo = Accounts.get(i).getAccountInfo();
      }
    }
   return AccountInfo;
  }
  
  
  
  
  public String getpNo() {
    return ssn;
  }
  
  public String getCustomerName() {
    return name;
  }
  
  public String getCustomerLastname() {
    return lastname;
  }
  
  public String getCustomerInfo() {
    String result;
    result = name + ' ' + lastname + ' ' + ssn;
    return result;
  }

}