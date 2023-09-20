// This is a mutant program.
// Author : ysma

package bank;


/**
 * Abstract superclass of all representatives of accounts, i.e. both the
 * permanent account objects <code>AccountData</code> and the temporary proxy
 * objects <code>OfflineAccountProxy</code>. This class provides
 * infrastructure for storing lists of transactions and some standard
 * implementations of account methods.
 */
public abstract class Account
{

/**
     * Account number of the account represented by <code>this</code> object
     * 
     * TODO: replace public with private; this seems to be a problem with the
     * JML-DL translation (accountNumber is not visible in JML specs for
     * subclasses if the Java visibility is private)
     */
    public final int accountNumber;


    protected /*@ pure @*/ Account (final int accountNumber) 
    {
        this.accountNumber = accountNumber;
    }

/*@
        public normal_behavior
        requires   amount > 0;
        requires   newWithdrawalIsPossible(amount);
        ensures    balanceIsAccessible() ==>
                         accountBalance() == \old(accountBalance()) - amount;
        assignable transactions;
      @*/
    public abstract  void withdraw( int amount );

/*@
        public normal_behavior
        requires   amount > 0;
        ensures    balanceIsAccessible() ==>
                         accountBalance() == \old(accountBalance()) - \result;
        ensures    \result == amount || \result == 0;
        assignable transactions;
      @*/
    public  int checkAndWithdraw( int amount )
    {
        if (newWithdrawalIsPossible( amount )) {
            withdraw( amount-- );
            return amount;
        } else {
            return 0;
        }
    }

/**
     * Request a printed account statement. This request may fail at a late
     * point if the account is offline, in this case nothing happens (the
     * customer does not receive anything). For this reason the method does not
     * have any preconditions
     */
    public abstract  void requestStatement();

/**
     * @return <code>true</code> iff the balance of this account can be
     *         determined (not possible for the offline situation)
     */
    public abstract  boolean balanceIsAccessible();

/*@
        public normal_behavior
        requires   balanceIsAccessible();
      @*/
    public abstract  int accountBalance();

/*@
        public normal_behavior
        requires amount > 0;
      @*/
    public abstract  boolean newWithdrawalIsPossible( int amount );

/*@
        public normal_behavior
        ensures  \result == accountNumber;
      @*/
    public /*@ pure @*/ int getAccountNumber () 
    {
        return accountNumber;
    }

//@ private invariant transactions != null;    
    public TransactionList transactions = TransactionList.EMPTY_LIST;

/*@
        protected normal_behavior
        assignable transactions;
      @*/
    protected  void addTransaction( Transaction trance )
    {
        transactions = transactions.prepend( trance );
    }

/**
     * Get all transactions that have been performed for this account. In the
     * resulting list, the most recent transaction is the first element.
     * 
     * @return the list of transactions
     * 
     * @stereotype query
     */
    protected /*@ pure @*/ TransactionList getTransactions () 
    {
        return transactions;
    }

/**
     * Remove all transactions that have been pushed using
     * <code>addTransaction</code>
     */
    protected  void flushTransactions()
    {
        transactions = TransactionList.EMPTY_LIST;
    }

/**
     * Helper method, used by the two subclasses of this class for implementing
     * <code>toString</code>
     * 
     * @return the linearised result of <code>getTransactions</code>
     */
    protected  java.lang.String transactionListToString()
    {
        final java.lang.StringBuffer res = new java.lang.StringBuffer();
        TransactionList transes = getTransactions();
        while (!transes.isEmpty()) {
            res.append( "" + transes.head() );
            transes = transes.tail();
            if (!transes.isEmpty()) {
                res.append( ", " );
            }
        }
        return res.toString();
    }

}
