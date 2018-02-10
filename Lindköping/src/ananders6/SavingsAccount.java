package ananders6;

/**
 * Kort beskrivning av klassen, vad den gör. 
 * @author Anna Andersson, ananders-6
 */

public class SavingsAccount {

  private double balance = 0.0;
  private double interestRate = 1.0;
  private String type = "Sparkonto";
  private int accountNumber;
  private static int lastAccountNumber = 1001;


  public SavingsAccount() {
    accountNumber = lastAccountNumber++;
  }


  public void deposit(double amount) {
    this.balance += amount;

  }

  public boolean withdraw(double amount) {
    double difference = balance - amount;
    boolean result = false;

    if (!(difference < 0.0)) {
      this.balance = balance - amount;
      result = true;
    }
    return result;
  }


  public int getAccountNumber() {
    return accountNumber;

  }

  public String getAccountInfo() {
    return accountNumber + " " + balance + ' ' + type + ' ' + interestRate;

  }


  public double calculateInterest() {
    return (this.balance * this.interestRate / 100.0);

  }

  public void changeAccountType(String newType) {
    this.type = newType;

  }

}