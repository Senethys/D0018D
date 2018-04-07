package ananders6;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Denna klass tillhör en kund. SvaingsAccount innehar all data om ett konto.
 * 
 * @author Anna Andersson, ananders-6
 */

public class SavingsAccount extends Account {

	private String type = "Sparkonto";
	private boolean usedFreeWithdraw = false;
	private double interestRate = 1.0;

	public SavingsAccount() {
	}

	/**
	 * Lägger till amount i kontots saldo.
	 *
	 * @param double  amount
	 * @return void
	 */
	@Override
	public void deposit(double amount) {
		
		this.balance += amount;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		date = date + " " + amount + " " + this.balance;
		TransactionList.add(date);


	}

	/**
	 * Tar bort amount från kontot. Man kan inte ta bort mer än saldo. returnerar
	 * true om uttaget gick igenom.
	 * 
	 * @param double
	 *            amount
	 * @return boolean.
	 */
	@Override
	public boolean withdraw(double amount) {
		
		boolean result = false;
		double difference = 0.0;
		double withdrawFee = 0.0;
		double withdrawAmount = 0.0;
		
		if (!this.usedFreeWithdraw) {
			difference = balance - amount;
		}
		
		else if(this.usedFreeWithdraw) {
			withdrawFee = amount * 0.02;
			difference = balance - (amount + withdrawFee);
		}
		
		withdrawAmount = amount + withdrawFee;

		if (!(difference < 0.0)) {
			this.balance = balance - withdrawAmount;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			date = date + " " + withdrawAmount *-1 + " " + this.balance;
			TransactionList.add(date);
			result = true;
			if (this.usedFreeWithdraw == false) {
				this.usedFreeWithdraw = true;
			}
		}
		return result;
	}

	@Override
	public String getAccountInfo() {
		return accountNumber + " " + balance + ' ' + type + ' ' + interestRate;
	}

	@Override
	public void changeAccountType(String newType) {
		this.type = newType;
	}

	@Override
	public double calculateInterest() {
		return (this.balance * this.interestRate / 100.0);
	}
}