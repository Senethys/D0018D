package ananders6;
import java.util.ArrayList;

/**
 * Huvudclassen som innehåller alla klasser, Customer och SavingsAccount. 
 * @author Anna Andersson, ananders-6
 */


public class BankLogic {

  private ArrayList<Customer> CustomerList = new ArrayList();

  public BankLogic() {}


  /**
   * Letar bland kunder där p
   * @param Personnummer.
   * @return Customer object.
   */
  private Customer matchCustomer(String pNr) {

    String matchedpNr;
    Customer MatchedCustomerObject = null;

    for(int i = 0; i < CustomerList.size(); i++) {
      matchedpNr = CustomerList.get(i).getpNr(); {
        if(matchedpNr.equals(pNr)) {
          MatchedCustomerObject = CustomerList.get(i);
        }
      }
    }
    return MatchedCustomerObject;
  }


  //Returnerar en ArrayList<String> som innehåller en presentation av bankens alla kunder på följande sätt: 
  //[Karl Karlsson 8505221898, Pelle Persson 6911258876, Lotta Larsson 7505121231]

  /**
   * 
   * @param 
   * @return void
   */
  public ArrayList<String> getAllCustomers() {

    ArrayList<String> results = new ArrayList();
    String CustomerInfo;

    for(int i = 0; CustomerList.size() > i; i++) {
      CustomerInfo = CustomerList.get(i).getCustomerInfo();
      results.add(CustomerInfo);
    }
    return results;
  }



  /**
   * 
   * @param 
   * @return void
   */
  public boolean createCustomer(String name, String surname, String pNr) {

    boolean result = false;
    boolean exists = false;
    //If customers exists
    if(CustomerList.size() != 0) {
      //Scan after matching pNr
      for(int i = 0; i < CustomerList.size(); i++) {
        if(pNr.equals(CustomerList.get(i).getpNr())) {
          exists = true;
          break;
        } 
      }

      if(!exists) {
        Customer NewCustomer = new Customer(name, surname, pNr);
        CustomerList.add(NewCustomer);
        result = true;
      }
    } else {
      Customer NewCustomer = new Customer(name, surname, pNr);
      CustomerList.add(NewCustomer);
      result = true;
    }
    return result;
  }


  /**
   * Returnerar en ArrayList<String> som innehåller informationen om kunden inklusive dennes konton.
  Returnerar null om kunden inte fanns
  Första platsen i listan är reserverad för kundens namn och personnummer sedan följer informationen om kundens konton (kontonr saldo kontotyp räntesats)
  Exempel på hur det som returneras ska se ut:
  [Lotta Larsson 7505121231, 1004 0.0 Sparkonto 1.0, 1005 0.0 Sparkonto 1.0]

   */
  /**
   * 
   * @param 
   * @return void
   */
  public ArrayList<String> getCustomer(String pNr){

    ArrayList<String> results = new ArrayList();
    String CustomerInfo;
    Customer MatchedCustomerObject;

    try {
      MatchedCustomerObject = matchCustomer(pNr);
      CustomerInfo = MatchedCustomerObject.getCustomerInfo();
      results.add(CustomerInfo);
      results.addAll(MatchedCustomerObject.getAllCustomerAccountInfo());
    }
    catch(NullPointerException e) {
      results = null;
    }

    return results;
  }


  /**
  Byter namn på vald kund, inparametern pNr anger vilken kund som ska få nytt namn.
  Returnerar true om namnet ändrades annars returnerar false (alltså om kunden inte fanns).

   */

  /**
   * 
   * @param 
   * @return void
   */
  public boolean changeCustomerName(String name, String surname, String pNr) {
    Customer customer;
    boolean result = false;

    try {
      customer = matchCustomer(pNr);
      customer.changeName(name, surname);
      result = true;
    }
    catch(NullPointerException e) {
      System.out.println("This person does not exist.");
      result = false;
    }
    return result;

  }

  /**
   * Tar bort kund ur banken, alla kundens eventuella konton tas också bort och resultatet returneras.
  Returnerar null om ingen kund togs bort
  Listan som returneras ska innehålla information om kund på första platsen i ArrayListen (förnamn efternamn personnummer) sedan följer de konton som togs bort (kontonr saldo kontotyp räntesats ränta). Ränta beräknas som saldo*räntesats/100 (ränta behöver enbart beräknas vid borttagning av konton då banken i denna version inte stödjer årsskiften).
  Det som returneras ska se ut som följer:
  [Lotta Larsson 7505121231, 1004 0.0 Sparkonto 1.0 0.0, 1005 700.0 Sparkonto 1.0 7.0]

   */


  /**
   * 
   * @param 
   * @return void
   */
  public ArrayList<String> deleteCustomer(String pNr){
    Customer MatchedCustomerObject;
    ArrayList<String> results = new ArrayList();
    String customerInfo;

    try {
      MatchedCustomerObject = matchCustomer(pNr);
      customerInfo = MatchedCustomerObject.getCustomerInfo();
      results.add(customerInfo);

      results.addAll(MatchedCustomerObject.getFullCustomerAccountInfo());
      CustomerList.remove(MatchedCustomerObject);
    }
    catch(NullPointerException e) {
      results = null;
    }

    return results;

  }


  /**Skapar ett konto till kund med personnummer pNr
  Kontonummer ska vara unika för hela banken, inte bara för en enskild kund (se Big Java Late Objects på s. 400Preview the documentView in a new window för tips på lösning).
  Returnerar kontonumret som det skapade kontot fick
  Alternativt returneras –1 om inget konto skapades
   */
  /**
   * 
   * @param 
   * @return void
   */
  public int createSavingsAccount(String pNr) {
    Customer MatchedCustomerObject;
    int accountNumber;

    try {
      MatchedCustomerObject = matchCustomer(pNr);
      accountNumber = MatchedCustomerObject.addAccount();
    }
    catch (NullPointerException e) { 
      return -1;
    }
    return accountNumber;  
  }


  /**
   * 
  Returnerar en String som innehåller presentation av kontot med kontonnummer accountId som tillhör kunden pNr (kontonummer saldo, kontotyp räntesats).
  Returnerar null om konto inte finns eller om det inte tillhör kunden
   */ 
   /**
   * 
   * @param 
   * @return void
   */
  public String getAccount(String pNr, int accountId) {
    String result;
    Customer customer;

    try {
      customer = matchCustomer(pNr);
      result = customer.getCustomerAccountInfo(accountId);
    }
    catch(NullPointerException e) {
      result = null;
    }
    return result;

  }
  /**Gör en insättning på konto med kontonnummer accountId som tillhör kunden pNr.
  Returnerar true om det gick bra annars false
   */
  /**
   * 
   * @param 
   * @return void
   */
  public boolean deposit(String pNr, int accountId, double amount) {
    SavingsAccount account;
    Customer customer;
    boolean result = false;

    try {
      customer = matchCustomer(pNr);
      account = customer.matchAccount(accountId);
      account.deposit(amount);
      result = true;
    }
    catch(NullPointerException e) {
      result = false;
    }

    return result;

  }

  /**Gör ett uttag på konto med kontonnummer accountId som tillhör kunden pNr.
  Uttaget genomförs endast om saldot täcker uttaget (saldot får inte bli mindre än 0)
  Returnerar true om det gick bra annars false
   */
  /**
   * 
   * @param 
   * @return void
   */
  public boolean withdraw(String pNr, int accountId, double amount) {
    SavingsAccount account;
    Customer customer;
    boolean result;

    try {
      customer = matchCustomer(pNr);
      account = customer.matchAccount(accountId);
      result = account.withdraw(amount);
    }
    catch(NullPointerException e) {
      result = false;
    }
    return result;

  }


  /**Avslutar ett konto med kontonnummer accountId som tillhör kunden pNr. När man avslutar ett konto skall räntan beräknas som saldo*ränta/100.
  OBS! Enda gången ränta läggs på är när kontot tas bort eftersom årsskiften inte hanteras i denna version av systemet.
  Presentation av kontot ska returneras inklusive räntan man får på pengarna (kontonummer saldo, kontotyp räntesats, ränta)
  Returnerar null om inget konto togs bort
   */
  /**
   * 
   * @param 
   * @return void
   */
  public String closeAccount(String pNr, int accountId) {
    Customer customer;
    SavingsAccount account;
    String result;

    try {
      customer = matchCustomer(pNr);
      account = customer.matchAccount(accountId);
      result = customer.getCustomerAccountInfo(accountId);
      result += " " + account.calculateInterest();
      customer.closeAccount(accountId);
    } catch (NullPointerException e) {
      result = null;
    }
    return result;
  }

  /**
   * 
   * @param 
   * @return void
   */
  public static void main(String[] args) {

    String welcomeMessage = "---Welcome to Banking Co.---\n";

    System.out.println(welcomeMessage);

  }
}
