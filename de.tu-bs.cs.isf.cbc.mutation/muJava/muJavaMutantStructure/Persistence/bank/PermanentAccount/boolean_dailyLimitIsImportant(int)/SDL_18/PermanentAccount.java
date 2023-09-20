// This is a mutant program.
// Author : ysma

package bank;


/**
 * Permanent representation of information about an account. Non-essential
 * information like the customer to which an account belongs is not modelled
 * here. The class implements a daily limit of 1000 somethings (which can only
 * be enforced if employed ATMs are online, however) and one free account
 * statement a month. It is possible that the <code>balance</code> of
 * represented accounts is negative, which cannot be sidestepped because the
 * possibility of offline withdrawals is mandatory
 */
public class PermanentAccount extends bank.Account
{

    public static final int dailyLimit = 1000;

/**
     * Balace of this account
     */
    public int balance;

/**
     * The time/date of the latest withdrawal. This information is used to
     * ensure that not more than 1000 units are withdrawn per day
     */
    public int dateOfLatestWithdrawal = Clock.getBigBangsDate();

//@ private invariant amountForLatestWithdrawalDay >= 0;
    public int amountForLatestWithdrawalDay = 0;

/**
     * The latest date at which an account statement was sent to the customer 
     */
    private int dateOfLatestAccountStatement = Clock.getBigBangsDate();


    public /*@ pure @*/ PermanentAccount (int accountNumber, int balance) 
    {
        super( accountNumber );
        this.balance = balance;
    }

/*@
        also
       
        public normal_behavior
        requires  amount > 0;
        requires  newWithdrawalIsPossible(amount);
        ensures   balance == \old(balance) - amount;
        assignable balance, dateOfLatestWithdrawal, amountForLatestWithdrawalDay;

        also
        
        public normal_behavior
        requires  amount > 0;
        requires  newWithdrawalIsPossible(amount);
        requires  !Clock.isEarlier(Clock.clockInstance.currentDate,
                                   dateOfLatestWithdrawal);
        requires  !Clock.isSameDay(Clock.clockInstance.currentDate,
                                   dateOfLatestWithdrawal);
        ensures   amountForLatestWithdrawalDay == amount;
        assignable balance, dateOfLatestWithdrawal, amountForLatestWithdrawalDay;
                        
        also
        
        public normal_behavior
        requires  amount > 0;
        requires  newWithdrawalIsPossible(amount);
        requires  !Clock.isEarlier(Clock.clockInstance.currentDate,
                                   dateOfLatestWithdrawal);
        requires  Clock.isSameDay(Clock.clockInstance.currentDate,
                                  dateOfLatestWithdrawal);
        ensures   amountForLatestWithdrawalDay ==
                        \old(amountForLatestWithdrawalDay) + amount;        
        assignable balance, dateOfLatestWithdrawal, amountForLatestWithdrawalDay;
      @*/
    public  void withdraw( int amount )
    {
        withdraw( Clock.getCurrentDate(), amount );
    }

/**
     * Request a printed account statement. This request may fail at a late
     * point if the account is offline, in this case nothing happens (the
     * customer does not receive anything). For this reason the method does not
     * have any preconditions
     */
    public  void requestStatement()
    {
        requestStatement( Clock.getCurrentDate() );
    }

/**
     * @return <code>true</code> iff the balance of this account can be
     *         determined (not possible for the offline situation)
     */
    public /*@ pure @*/ boolean balanceIsAccessible () 
    {
        return true;
    }

/*@
        also
       
        public normal_behavior
        requires  balanceIsAccessible();
      @*/
    public /*@ pure @*/ int accountBalance () 
    {
        return balance;
    }

/*@
        also
      
        public normal_behavior
        requires amount > 0;
      @*/
    public /*@ pure @*/ boolean newWithdrawalIsPossible (int amount) 
    {
        return amount <= accountBalance() && amount <= dailyLimit - getWithdrawnAmountForToday();
    }

/*@
        normal_behavior
        requires  amount > 0;
        ensures   balance == \old(balance) - amount;
        assignable balance, dateOfLatestWithdrawal, amountForLatestWithdrawalDay;
      @*/
     void withdraw( int date, int amount )
    {
        addTransaction( new Withdrawal( date, amount ) );
        balance -= amount;
        if (dailyLimitIsImportant( date )) {
            amountForLatestWithdrawalDay += amount;
        }
    }

/*@
        private normal_behavior
        assignable dateOfLatestWithdrawal, amountForLatestWithdrawalDay;
      @*/
    private  boolean dailyLimitIsImportant( int date )
    {
        if (Clock.isSameDay( date, dateOfLatestWithdrawal )) {
            return false;
        }
        if (Clock.isEarlier( date, dateOfLatestWithdrawal )) {
            return false;
        }
        dateOfLatestWithdrawal = date;
        amountForLatestWithdrawalDay = 0;
        return true;
    }

/**
     * Request a printed account statement and store the transaction for the
     * date <code>date</code>. This request may fail, in this case nothing
     * happens (the customer does not receive anything). For this reason the
     * method does not have any preconditions
     * 
     * TODO: here a nice specification seems possible ...
     */
     void requestStatement( int date )
    {
        final int now = Clock.getCurrentDate();
        addTransaction( new AccountStatementRequest( date ) );
        if (Clock.isSameMonth( dateOfLatestAccountStatement, now )) {
            if (!newWithdrawalIsPossible( 1 )) {
                return;
            }
            withdraw( 1 );
        }
        sendAccountStatement();
        dateOfLatestAccountStatement = now;
    }

/**
     * @return the amount of money that has been withdrawn so far on the day
     *         <code>Clock.getCurrentDate()</code>
     * 
     * TODO: here a nice specification seems possible ...
     */
    private /*@ pure @*/ int getWithdrawnAmountForToday () 
    {
        if (Clock.isSameDay( Clock.getCurrentDate(), dateOfLatestWithdrawal )) {
            return amountForLatestWithdrawalDay;
        }
        return 0;
    }

/**
     * Simulate the sending of an account statement ...
     */
    public  void sendAccountStatement()
    {
    }

////////////////////////////////////////////////////////////////////////////
    public  java.lang.String toString()
    {
        return "(Account: " + getAccountNumber() + ", Balance: " + accountBalance() + ", Recent withdrawals: (" + dateOfLatestWithdrawal + ", " + amountForLatestWithdrawalDay + ")" + ", Transactions: (" + transactionListToString() + "))";
    }

     void depose( int amount )
    {
        balance += amount;
    }

}
