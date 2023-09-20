// This is a mutant program.
// Author : ysma

package bank;


/**
 * Class whose objects represent the automated teller machines of our bank.
 * These machines can operate both online and offline. In the first case, all
 * transactions are directly transfered to the central host of the bank, in the
 * latter case transactions are first buffered (using class
 * <code>OfflineAccountProxy</code> and later written back to the central
 * host.
 */
public class ATM
{

    public static final int maxAccountNumber = CentralHost.maxAccountNumber;

/*@
        public invariant accountProxies != null;
        public invariant accountProxies.length == maxAccountNumber;
        public invariant (\forall int i; i >= 0 && i < maxAccountNumber;
                                         ( accountProxies[i] == null
                                           ||
                                           accountProxies[i].accountNumber == i ));
      @*/
    public final OfflineAccountProxy[] accountProxies = new OfflineAccountProxy[maxAccountNumber];

//@ private invariant centralHost != null;
    public final CentralHost centralHost;

/**
     * <code>true</code> iff the ATM is online, i.e. if
     * <code>centralHost</code> may be accessed
     */
    public boolean online = true;

/**
     * A bank card that is possible inserted into the ATM at the time
     */
    public BankCard insertedCard = null;

/**
     * <code>true</code> iff there is a card inserted and the customer has
     * entered the right PIN
     */
    public boolean customerAuthenticated = false;

/**
     * Count the number of wrong PINs entered since a valid card was inserted
     */
    public int wrongPINCounter = 0;

/*@
        private invariant customerAuthenticated ==> insertedCard != null;
        private invariant insertedCard != null ==> !insertedCard.invalid;
        private invariant insertedCard != null ==>
                                 insertedCard.accountNumber >= 0;
        private invariant insertedCard != null ==>
                                 insertedCard.accountNumber < maxAccountNumber;
        private invariant ( online && insertedCard != null )
                          ==>
                          centralHost.accounts[insertedCard.accountNumber] != null;
      @*/

    public /*@ pure @*/ ATM (final CentralHost centralHost) 
    {
        this.centralHost = centralHost;
    }

/**
     * @return <code>true</code> iff the ATM is online
     */
    private /*@ pure @*/ boolean isOnline () 
    {
        return online;
    }

/*@
        private normal_behavior
        requires  accountNumber >= 0;
        requires  accountNumber < maxAccountNumber;
        requires  centralHost.accounts[accountNumber] != null;
        ensures   \result.accountNumber == accountNumber;
        assignable accountProxies[accountNumber];
      @*/
    private  Account getAccount( int accountNumber )
    {
        if (isOnline()) {
            return centralHost.getAccount( accountNumber );
        }
        if (!proxyExists( accountNumber )) {
            setProxy( accountNumber, new OfflineAccountProxy( accountNumber ) );
        }
        return getProxy( accountNumber );
    }

/*@
        public normal_behavior
        ensures  online == newOnline;
        assignable \everything;
      @*/
    public  void setOnline( boolean newOnline )
    {
        if (this.online == newOnline) {
            return;
        }
        if (newOnline) {
// synchronize with central host
            for (int i = 0; i != CentralHost.maxAccountNumber; ++i) {
                if (proxyExists( i )) {
                    getProxy( i ).replay( centralHost.getAccount( i ) );
                    setProxy( i, null );
                }
            }
        }
        if (cardForNonexistingAccountInserted()) {
            confiscateCard();
        }
        this.online = newOnline;
    }

/*@
        public normal_behavior
        requires  insertedCard == null;
        requires  card != null;
        requires  card.accountNumber >= 0;
        requires  card.accountNumber < maxAccountNumber;
        ensures   card.invalid <==> insertedCard == null;
        ensures   !customerAuthenticated;
        ensures   wrongPINCounter == 0;
        assignable insertedCard, customerAuthenticated, wrongPINCounter, card.invalid;
      @*/
    public  void insertCard( BankCard card )
    {
        if (!(!cardIsInserted() && card != null && card.getAccountNumber() >= 0 && card.getAccountNumber() <= maxAccountNumber)) {
            throw new java.lang.RuntimeException();
        }
        insertedCard = card;
        customerAuthenticated = false;
        wrongPINCounter = 0;
        if (insertedCard.cardIsInvalid() || cardForNonexistingAccountInserted()) {
            confiscateCard();
        }
    }

/*@
        public normal_behavior
        requires  insertedCard != null;
        ensures   insertedCard == null;
        assignable insertedCard, customerAuthenticated;
      @*/
    public  BankCard ejectCard()
    {
        if (!cardIsInserted()) {
            throw new java.lang.RuntimeException();
        }
        final BankCard res = insertedCard;
        insertedCard = null;
        customerAuthenticated = false;
        return res;
    }

/*@
        public normal_behavior
        requires  insertedCard != null;
        ensures   insertedCard == null;
        ensures   \old(insertedCard).invalid;
        assignable insertedCard, customerAuthenticated, insertedCard.invalid;
      @*/
    public  void confiscateCard()
    {
        if (!cardIsInserted()) {
            throw new java.lang.RuntimeException();
        }
        insertedCard.makeCardInvalid();
        insertedCard = null;
        customerAuthenticated = false;
    }

/*@ public normal_behavior
        requires  insertedCard != null;
        requires  !customerAuthenticated;
        requires  pin == insertedCard.correctPIN;
        assignable customerAuthenticated;      
        ensures   customerAuthenticated;
      
        also
      
        public normal_behavior
        requires  insertedCard != null;
        requires  !customerAuthenticated;
        requires  pin != insertedCard.correctPIN;
        requires  wrongPINCounter < 2;
        assignable wrongPINCounter;
        ensures   wrongPINCounter 
                        == \old(wrongPINCounter) + 1;
        ensures   !customerAuthenticated;
      
        also
        
        public normal_behavior
        requires  insertedCard != null;
        requires  !customerAuthenticated;
        requires  pin != insertedCard.correctPIN;
        requires  wrongPINCounter >= 2;
        assignable insertedCard, wrongPINCounter, 
                   insertedCard.invalid;    
        ensures   insertedCard == null;
        ensures   \old(insertedCard).invalid;
        ensures   !customerAuthenticated;
      @*/
    public  void enterPIN( int pin )
    {
        if (!(cardIsInserted() && !customerIsAuthenticated())) {
            throw new java.lang.RuntimeException();
        }
        if (insertedCard.pinIsCorrect( pin )) {
            customerAuthenticated = true;
        } else {
            ++wrongPINCounter;
            if (wrongPINCounter >= 3) {
                confiscateCard();
            }
        }
    }

/**
     * @return <code>true</code> iff a card is inserted in the ATM
     */
    private /*@ pure @*/ boolean cardIsInserted () 
    {
        return insertedCard != null;
    }

/**
     * @return <code>true</code> iff a customer is regarded as authenticated,
     *         i.e. if a valid card is inserted and the correct PIN has be
     *         entered
     */
    private /*@ pure @*/ boolean customerIsAuthenticated () 
    {
        return customerAuthenticated;
    }

/*@
        private normal_behavior
        ensures  \result
                 <==>
                 ( insertedCard != null
                   &&
                   online
                   &&
                   centralHost.accounts[insertedCard.accountNumber] == null );
      @*/
    private /*@ pure @*/ boolean cardForNonexistingAccountInserted () 
    {
        return cardIsInserted() && isOnline() && !centralHost.accountExists( getAccountNumber() );
    }

/*@
        private normal_behavior
        requires  customerAuthenticated;
      @*/
    private /*@ pure @*/ int getAccountNumber () 
    {
        return insertedCard.getAccountNumber();
    }

/*@
        private normal_behavior
        requires  customerAuthenticated;
        assignable accountProxies[insertedCard.accountNumber];
      @*/
    private  Account getAccount()
    {
        return getAccount( getAccountNumber() );
    }

/*@
        public normal_behavior
        requires  customerAuthenticated;
        ensures   online
                  ==>
                  \result ==
                     centralHost.accounts[insertedCard.accountNumber].balance;
        assignable accountProxies[insertedCard.accountNumber];
      @*/
    public  int accountBalance()
    {
        if (!customerIsAuthenticated()) {
            throw new java.lang.RuntimeException();
        }
        if (!getAccount().balanceIsAccessible()) {
            return 0;
        }
        return getAccount().accountBalance();
    }

/*@
        public normal_behavior
        requires  customerAuthenticated;
        requires  amount > 0;
      @*/
    public  int withdraw( int amount )
    {
        if (!(customerIsAuthenticated() && amount > 0)) {
            throw new java.lang.RuntimeException();
        }
        return getAccount().checkAndWithdraw( amount );
    }

/*@
        public normal_behavior
        requires  customerAuthenticated;
      @*/
    public  void requestAccountStatement()
    {
        if (!customerIsAuthenticated()) {
            throw new java.lang.RuntimeException();
        }
        getAccount().requestStatement();
    }

/*@
        private normal_behavior
        requires  accountNumber >= 0;
        requires  accountNumber < maxAccountNumber;
        ensures   accountProxies[accountNumber] == proxy;
        assignable accountProxies[accountNumber];
      @*/
    private  void setProxy( int accountNumber, OfflineAccountProxy proxy )
    {
        accountProxies[accountNumber] = proxy;
    }

/*@
        private normal_behavior
        requires  accountNumber >= 0;
        requires  accountNumber < maxAccountNumber;
        ensures   \result == accountProxies[accountNumber];
      @*/
    private /*@ pure @*/ OfflineAccountProxy getProxy (int accountNumber) 
    {
        return accountProxies[accountNumber];
    }

/*@
        private normal_behavior
        requires  accountNumber >= 0;
        requires  accountNumber < maxAccountNumber;
        ensures   \result <==> accountProxies[accountNumber] != null;
      @*/
    private /*@ pure @*/ boolean proxyExists (int accountNumber) 
    {
        return getProxy( accountNumber ) != null;
    }

}
