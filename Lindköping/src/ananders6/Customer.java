package ananders6;

import java.util.ArrayList;
import java.util.Scanner;

public class Customer {
  
  public static Scanner userInput = new Scanner(System.in);
  String inputData;
  
  private String name;
  private String lastname;
  private long socialSecurityNumber;
  private ArrayList<SavingsAccount> Accounts = new ArrayList();
  
  public Customer(String fname, String lname, long sNumber) {
    name = fname;
    lastname = lname;
    socialSecurityNumber = sNumber;
  }

  public void changeName() {
    System.out.print("Current name: ");
    System.out.print(name + " " + lastname);
    System.out.println("What would you like to change the name to? >: ");
    inputData = userInput.next();
    name = inputData;
  }
  
  public void addAccount() {
    System.out.println("Adding new accout...");
    SavingsAccount account = new SavingsAccount();
    Accounts.add(account);
  
  }
  
  public void getCustomerInfo() {
    System.out.println("Customer information: ");
    System.out.print("Current name: ");
    System.out.println(name + " " + lastname);
    System.out.print("Social Number: ");
    System.out.println(socialSecurityNumber);

    
  }
  
  public void getCustomerAccountInfo() {
    System.out.println("Account Number: ");
  }

}