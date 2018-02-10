package ananders6;
import java.util.ArrayList;

/**
 * Kort beskrivning av klassen, vad den gör. 
 * @author Anna Andersson, ananders-6
 */


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


  public SavingsAccount matchAccount(int accountId) {
    SavingsAccount matchedAccount = null;

    for(int i = 0; Accounts.size() > i; i++) {
      if(Accounts.get(i).getAccountNumber() == accountId) {
        matchedAccount = Accounts.get(i);
      }
    }

    return matchedAccount;
  }



  public void changeName(String newName, String newLastname) {
    this.name = newName;
    this.lastname = newLastname;
    System.out.println(this.name + " " + this.lastname);
  }




  public int addAccount() {
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


  public ArrayList<String> getFullCustomerAccountInfo() {

    ArrayList<String> results = new ArrayList();
    String AccountInfo;

    for(int i = 0; Accounts.size() > i; i++) {
      AccountInfo = Accounts.get(i).getAccountInfo() + " " + Accounts.get(i).calculateInterest();
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



  public boolean closeAccount(int accountId) {
    boolean result;
    try {
      SavingsAccount Acc = matchAccount(accountId);
      Accounts.remove(Acc);
      result = true;
    } catch(IndexOutOfBoundsException e) {
      System.err.println("ERROR!");
      System.err.println(e);
      result = false;
    }

    return result;
  }


  public String getpNr() {
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