public class Generated_BankAccount {

    /*@
    @ public normal_behavior
    @ requires true;
    @ ensures true;
    @ assignable \everything;
    @*/
    public static void generated_bankaccount() {  }

    /*@
    @ normal_behavior
    @ requires true;
    @ ensures true;
    @ assignable \everything;
    @*/
    public static void bankaccount() {
    }

    /*@
    @ normal_behavior
    @ requires true;
    @ ensures a.interest == 0 && a.balance == old_a.balance +old_a.interest;
    @ assignable \everything;
    @*/
    public static void interest(Account a, Account old_a) {
    }
}
