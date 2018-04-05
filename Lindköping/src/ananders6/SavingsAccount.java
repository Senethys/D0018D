package ananders6;
import java.util.Date;
import java.util.ArrayList;

/**
 * Denna klass tillhör en kund. SvaingsAccount innehar all data om ett konto.
 * @author Anna Andersson, ananders-6
 */

public class SavingsAccount extends Account {

	private String type = "Sparkonto";



  public SavingsAccount() {
  }

  /** Lägger till amount i kontots saldo.
  *
  * @param double amount
  * @return void
  */
  @Override
  public void deposit(double amount) {
    this.balance += amount;
    Date date = new Date();
    System.out.println(date.toString());
    String transactionDate = date.toString();
    TransactionList.add(String.valueOf(amount));
    TransactionList.add(transactionDate);
  }
  
  
  /** Tar bort amount från kontot.
  * Man kan inte ta bort mer än saldo.
  * returnerar true om uttaget gick igenom.
  * @param double amount
  * @return boolean.
  */
  @Override
  public boolean withdraw(double amount) {
    double difference = balance - amount;
    boolean result = false;

    if (!(difference < 0.0)) {
      this.balance = balance - amount;
      Date date = new Date();
      System.out.println(date.toString());
      result = true;
    }
    return result;
  }
}