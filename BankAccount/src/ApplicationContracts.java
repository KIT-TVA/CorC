public class ApplicationContracts {

    /*@
      @ public normal_behavior
      @ requires true;
      @ ensures \result == false;
      @ assignable \nothing;
      @*/
    public static boolean nextDay_BankAccount(Account a) {
    }

    /*@
      @ public normal_behavior
      @ ensures a.withdraw == 0 && \result == true;
      @*/
    public static boolean nextDay_Daily(Account a) {
    }

    /*@
      @ requires true;
      @ ensures true;
      @ assignable \nothing;
      @*/
    public static void nextYear_BankAccount(Account a) {
    }
}
