package ananders6;

import java.util.ArrayList;

/**
 * Denna klass tillhör en kund. SvaingsAccount innehar all data om ett konto.
 * 
 * @author Anna Andersson, ananders-6
 */

public abstract class Account {
	protected double balance = 0.0;
	protected ArrayList<Transaction> TransactionList = new ArrayList<Transaction>();
	protected Transaction transaction; 
	protected int accountNumber;
	protected static int lastAccountNumber = 1001;

	public Account() {
		this.accountNumber = lastAccountNumber++;
	}

	/**
	 * Lägger till amount i kontots saldo.
	 *
	 * @param double
	 *            amount
	 * @return void
	 */
	public abstract void deposit(double amount);

	/**
	 * Tar bort amount från kontot. Man kan inte ta bort mer än saldo. returnerar
	 * true om uttaget gick igenom.
	 * 
	 * @param double
	 *            amount
	 * @return boolean.
	 */
	public abstract boolean withdraw(double amount);

	/**
	 * Returnerar kontots nummer
	 *
	 * @param void
	 * @return String
	 */
	public int getAccountNumber() {
		return this.accountNumber;
	};

	/**
	 * Returnerar all information om kontot förutom räntan.
	 *
	 * @param void
	 * @return String
	 */
	public abstract String getAccountInfo();

	/**
	 * Returnerar all information om kontots transaktioner.
	 *
	 * @param void
	 * @return String
	 */
	public ArrayList<Transaction> getAccountTransactions() {
		return TransactionList;

	}

	/**
	 * Beräknar kontots ränta.
	 *
	 * @param void
	 * @return double
	 */
	public abstract double calculateInterest();

	/**
	 * Byter typen på kontot. Har ingen funktionell funktion.
	 * 
	 * @param String
	 *            newType
	 * @return void
	 */
	public abstract void changeAccountType(String newType);
}