package ananders6;

import java.util.ArrayList;

public class main {

  public static void main(String[] args) {
    BankLogic bank = new BankLogic();
    boolean r = bank.createCustomer("Anna", "Andersson", "9913991234");
    ArrayList<String> result = bank.getAllCustomers();
    System.out.println("  " + result);
  }
}
