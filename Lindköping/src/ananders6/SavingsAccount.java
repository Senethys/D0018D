package ananders6;

/**
 * Denna klass tillhör en kund. SvaingsAccount innehar all data om ett konto.
 * @author Anna Andersson, ananders-6
 */

public class SavingsAccount extends Account {

  private double balance = 0.0;
  private double interestRate = 1.0;
  private String type = "Sparkonto";
  private int accountNumber;
  private static int lastAccountNumber = 1001;


  public SavingsAccount() {
    accountNumber = lastAccountNumber++;
  }

  /** Lägger till amount i kontots saldo.
  *
  * @param double amount
  * @return void
  */
  public void deposit(double amount) {
    this.balance += amount;

  }
  /** Tar bort amount från kontot.
  * Man kan inte ta bort mer än saldo.
  * returnerar true om uttaget gick igenom.
  * @param double amount
  * @return boolean.
  */
  public boolean withdraw(double amount) {
    double difference = balance - amount;
    boolean result = false;

    if (!(difference < 0.0)) {
      this.balance = balance - amount;
      result = true;
    }
    return result;
  }

  /** Returnerar kontots nummer
  *
  * @param void
  * @return String
  */
  public int getAccountNumber() {
    return accountNumber;

  }
  
  /** Returnerar all information om kontot förutom räntan.s
  *
  * @param void
  * @return String
  */
  public String getAccountInfo() {
    return accountNumber + " " + balance + ' ' + type + ' ' + interestRate;

  }

  /** Beräknar kontots ränta.
  *
  * @param void
  * @return double
  */
  public double calculateInterest() {
    return (this.balance * this.interestRate / 100.0);

  }
  
  /** Byter typen på kontot.
  * Har ingen funktionell funktion.
  * @param String newType
  * @return void
  */
  public void changeAccountType(String newType) {
    this.type = newType;

  }

}