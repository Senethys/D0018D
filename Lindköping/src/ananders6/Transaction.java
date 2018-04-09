package ananders6;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
  private double balance = 0.0;
  private String pattern = "yyyy-MM-dd HH:mm:ss";
  private double amount;
  private String TransactionDetails;


  public Transaction(double amount, double balance) {
    this.balance = balance;
    this.amount = amount;
    
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String TransactionDetails = simpleDateFormat.format(new Date());
    this.TransactionDetails = TransactionDetails + " " + this.amount + " " + this.balance;

  }
  

  public String getTransacionDetails() {
    return this.TransactionDetails;
  }
 
}
