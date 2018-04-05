package ananders6;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Denna klass tillhör en kund. SvaingsAccount innehar all data om ett konto.
 * @author Anna Andersson, ananders-6
 */

public class CreditAccount extends Account{

	private String type = "Kreditkonto";
  private double creditLimit = -5000.0;
  private double interestRate = 7.0;



  public CreditAccount() {}
  
  

  /** Efterssom det inte går att att lägga pengar i kredit
  * konton så tas funktionen bort. 
  *
  * @param double amount
  * @return void
  */
  @Override
  public void deposit(double amount) {
	  throw new UnsupportedOperationException();
  }
  
  
  /** Tar bort amount från kontot.
  * Man kan inte ta bort mer än saldo.
  * returnerar true om uttaget gick igenom.
  * @param double amount
  * @return boolean.
  */
  @Override
  public boolean withdraw(double amount) {
	  boolean result = false;
	  String transactionDate;
	  if (creditLimit < (this.balance - Math.abs(amount))) { 
		  this.balance = this.balance - Math.abs(amount);
		  result = true;
		    Date date = new Date();
		    transactionDate = date.toString();
		    TransactionList.add(String.valueOf(amount));
		    TransactionList.add(transactionDate);
	  }
	  
	  else if(balance < creditLimit) {
		  System.out.println("Credit card will overdrawn.");
	
	  }
	  return result;
  }


  /** Beräknar kontots ränta.
  *
  * @param void
  * @return double
  */
  public double calculateInterest() {
    return (this.balance * this.interestRate / 100.0);

  }

}
