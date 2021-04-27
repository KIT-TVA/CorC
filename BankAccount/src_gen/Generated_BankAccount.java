public class Generated_BankAccount {

 public static boolean original; 


    /*@
    @ public normal_behavior
    @ requires true;
    @ ensures (\result == false ==> a.balance == old_a.balance) && (\result == true ==> a.balance == old_a.balance + x);
    @ assignable a.balance;
    @*/
    public static boolean generated_bankaccount(int x, Account a, Account old_a) {  }

    /*@
    @ normal_behavior
    @ requires true;
    @ ensures (\result == false ==> a.balance == old_a.balance) && (\result == true ==> a.balance == old_a.balance + x);
    @ assignable a.balance;
    @*/
    public static boolean bankaccount(int x, Account a, Account old_a) {
    }

    /*@
    @ normal_behavior
    @ requires true;
    @ ensures (result == false ==> (a.withdraw == old_a.withdraw &&  a.balance == old_a.balance)) && (result == true ==> (a.withdraw >= old_a.withdraw ) &&  a.balance == old_a.balance + x);
    @ assignable a.withdraw;
    @*/
    public static boolean dailylimit(int x, Account a, Account old_a) {
    }
}
