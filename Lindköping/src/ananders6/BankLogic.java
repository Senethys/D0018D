package ananders6;
import java.util.ArrayList;
import java.util.Scanner;



public class BankLogic {

  public static Scanner userInput = new Scanner(System.in);
  String inputData;

  private ArrayList<Customer> CustomerList = new ArrayList();

  public BankLogic() {


  }



  public ArrayList<String> getAllCustomers() {
    return null;

  }

  public boolean createCustomer(String name, String surname, String pNo) {
    return true;
  }


  public ArrayList<String> getCustomer(String pNo){
    return null;

  }


  public boolean changeCustomerName(String name, String surname, String pNo) {
    return false;

  }


  public ArrayList<String> deleteCustomer(String pNo){
    return null;

  }

  public int createSavingsAccount(String pNo) {
    return 0;

  }


  public String getAccount(String pNo, int accountId) {
    return pNo;

  }


  public boolean deposit(String pNo, int accountId, double amount) {
    return false;

  }

  public boolean withdraw(String pNo, int accountId, double amount) {
    return false;

  }

  public String closeAccount(String pNr, int accountId) {
    return pNr;

  }







  public static void main(String[] args) {

    String welcomeMessage = "---Welcome to SeBanking Co.---\n";

    System.out.println("Update");
    System.out.println(welcomeMessage);
    Customer customer1 = new Customer("Anna", "Andersson", 9913991);
    customer1.addAccount();

  }
}
