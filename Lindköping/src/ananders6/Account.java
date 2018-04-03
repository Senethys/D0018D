package ananders6;

/**
 * Denna klass tillhör en kund. SvaingsAccount innehar all data om ett konto.
 * @author Anna Andersson, ananders-6
 */

public abstract class Account {
	
	protected double balance = 0.0;
	protected double interestRate = 1.0;
	protected String type = "Konto";
	protected int accountNumber;
	protected static int lastAccountNumber = 1001;

	
	public Account() {
		accountNumber = lastAccountNumber++;
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

	
}


