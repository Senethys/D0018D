package ananders6;

import javax.swing.SwingUtilities;

public class GUImain
{
  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable() {
          public void run() {
          new BankController();
          }
      });
  }
}