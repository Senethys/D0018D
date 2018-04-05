package ananders6;
import java.util.ArrayList;

/**
 * Denna klass tillhör en kund. SvaingsAccount innehar all data om ett konto.
 * @author Anna Andersson, ananders-6
 */

public abstract class Account {
	
	protected double balance = 0.0;
	protected double interestRate = 1.0;	
	protected String type = "Konto";
	protected ArrayList<String> TransactionList = new ArrayList<String>();
	protected int accountNumber;
	protected static int lastAccountNumber = 1001;


	
	public Account() {
	    this.accountNumber = lastAccountNumber++;
	}
	
	
	  /** Lägger till amount i kontots saldo.
	   *
	   * @param double amount
	   * @return void
	   */
	  public abstract void deposit(double amount);
	  
	  
	  /** Tar bort amount från kontot.
	   * Man kan inte ta bort mer än saldo.
	   * returnerar true om uttaget gick igenom.
	   * @param double amount
	   * @return boolean.
	   */
	   public abstract boolean withdraw(double amount);

	   
	  /** Returnerar kontots nummer
	  *
	  * @param void
	  * @return String
	  */
	  public int getAccountNumber() {
		  return this.accountNumber;
	  };
	  
	  
	  /** Returnerar all information om kontot förutom räntan.
	  *
	  * @param void
	  * @return String
	  */
	  public String getAccountInfo() {
		    return accountNumber + " " + balance + ' ' + type + ' ' + interestRate;
	  };
	  
	  /** Returnerar all information om kontots transaktioner.
	  *
	  * @param void
	  * @return String
	  */
	  public ArrayList<String> getAccountTransactions() {
			return TransactionList;
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