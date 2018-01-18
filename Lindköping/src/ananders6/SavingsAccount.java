package ananders6;

public class SavingsAccount {
  
  private double balance = 0.0;
  private double interestRate;
  private String type;
  private int accountNumber;
  private static int lastAccountNumber = 1000;
  
  public SavingsAccount() {
  
    accountNumber = lastAccountNumber++;
  }
  
  
  public void deposit() {
    
  }
  
  public void withdraw() {
    
  }
  
  
 public void getAccountNumber() {
    
    System.out.print("Account Number: ");
    System.out.println(accountNumber);

  }
  
  public void getAccountInfo() {
    
    System.out.print("Account Number: ");
    System.out.println(accountNumber);
    System.out.print("Type: ");
    System.out.println(type);
    System.out.print("Balance: ");
    System.out.println(balance);
    System.out.print("Rate: ");
    System.out.println(interestRate);
  
  }
  

  public void calculateInterest() {
   
  }
  
  
  
  public void deleteAccount() {
    
  }
}
  


/* 1. Create new customer
 *  1.1 Add account
 *   1.1.1 deposit
 *   1.1.2 withdraw
 *   1.1.3 get accout number
 *   1.1.4 get account info
 *   1.1.5 calculate interest
 *   1.1.6 delete account
 *  1.2 Change name
 *  1.3 Get customer info
 *  1.4 Get customer account info
 * 2. Get all customers
 * 3. Get Customer
 * 4. Change Customer name
 * 5. delete Customer 
 * 6. Create Savings account
 * 7. Get Account
 * 8. deposit
 * 9. withdraw
 * 10. close account
 * 
 * 
 * 
 * 
 */


