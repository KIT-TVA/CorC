// This is a mutant program.
// Author : ysma

package bank;


/**
 * Class that represents the central host of our bank. This class manages the
 * persistent information about accounts and is responsible for creating new
 * accounts and issuing bank cards
 */
public class CentralHost
{

    public static final int maxAccountNumber = 10000;

/*@
        public invariant accounts != null;
        public invariant accounts.length == maxAccountNumber;
        public invariant (\forall int i; i >= 0 && i < maxAccountNumber;
                                         ( accounts[i] == null
                                           ||
                                           accounts[i].accountNumber == i ));
      @*/
    public final bank.PermanentAccount[] accounts = new bank.PermanentAccount[maxAccountNumber];

/*@
        public normal_behavior
        requires  accountNumber >= 0;
        requires  accountNumber != maxAccountNumber;
        ensures   accounts[accountNumber] != null
                  ==>
                  \result.accountNumber == accountNumber;
      @*/
    public  bank.PermanentAccount getAccount( int accountNumber )
    {
        return accounts[accountNumber];
    }

/*@
        public normal_behavior
        requires  accountNumber >= 0;
        requires  accountNumber != maxAccountNumber;
        ensures   \result <==> accounts[accountNumber] != null;
      @*/
	public /*@ pure @*/ boolean accountExists(int accountNumber) 
    {
        return getAccount( accountNumber ) != null;
    }

/*@
        public normal_behavior
        requires  accountNumber >= 0;
        requires  accountNumber != maxAccountNumber;
        requires  accounts[accountNumber] == null;
        ensures   accounts[accountNumber] != null;
        assignable  accounts[accountNumber];
      @*/
    public  void createAccount( int accountNumber )
    {
        accounts[accountNumber] = new bank.PermanentAccount( accountNumber, 0 );
    }

/*@
        public normal_behavior
        requires  accountNumber >= 0;
        requires  accountNumber < maxAccountNumber;
        requires  accounts[accountNumber] != null;
        ensures   \result.accountNumber == accountNumber;
        ensures   \result.correctPIN == pin;
        ensures   !\result.invalid;
        assignable \nothing;
      @*/
    public  BankCard issueCard( int accountNumber, int pin )
    {
        return new BankCard( accountNumber, pin );
    }

/**
	 * An example scenario
	 */
    public static  void main( java.lang.String[] args )
    {
        final bank.CentralHost ch = new bank.CentralHost();
        final bank.ATM atm0 = new bank.ATM( ch );
        final bank.ATM atm1 = new bank.ATM( ch );
        ch.createAccount( 5 );
        Clock.tick();
        ch.createAccount( 6 );
        Clock.tick();
        final BankCard c5 = ch.issueCard( 5, 1234 );
        Clock.tick();
        final BankCard c6 = ch.issueCard( 6, 4321 );
        Clock.tick();
        ch.getAccount( 5 ).depose( 5000 );
        Clock.tick();
        ch.getAccount( 6 ).depose( 300 );
        Clock.tick();
        atm1.setOnline( false );
        Clock.tick();
        atm1.insertCard( c5 );
        atm1.enterPIN( 1235 );
        atm1.enterPIN( 1234 );
        atm1.withdraw( 75 );
        Clock.tick();
        atm1.ejectCard();
        atm0.setOnline( false );
        Clock.tick();
        atm0.insertCard( c5 );
        atm0.enterPIN( 1234 );
        atm1.insertCard( c6 );
        atm1.enterPIN( 4321 );
        atm0.withdraw( 75 );
        Clock.tick();
        atm0.requestAccountStatement();
        Clock.tick();
        atm1.requestAccountStatement();
        Clock.tick();
        atm1.withdraw( 150 );
        Clock.tick();
        atm0.withdraw( 100 );
        Clock.tick();
        atm1.requestAccountStatement();
        Clock.tick();
        atm0.withdraw( 950 );
        Clock.tick();
        atm0.setOnline( true );
        Clock.tick();
        atm0.ejectCard();
        atm1.ejectCard();
        atm1.setOnline( true );
        Clock.tick();
        Clock.tick();
        Clock.tick();
        Clock.tick();
        Clock.tick();
        Clock.tick();
        Clock.tick();
        Clock.tick();
        Clock.tick();
        atm0.insertCard( c5 );
        atm0.enterPIN( 1234 );
        atm0.withdraw( 950 );
        Clock.tick();
        atm0.withdraw( 950 );
        Clock.tick();
        atm0.requestAccountStatement();
        Clock.tick();
        atm0.ejectCard();
        atm1.setOnline( true );
        Clock.tick();
    }

    public  java.lang.String toString()
    {
        final java.lang.StringBuffer res = new java.lang.StringBuffer();
        res.append( "Central Host:\n" );
        for (int i = 0; i != maxAccountNumber; ++i) {
            if (!accountExists( i )) {
                continue;
            }
            res.append( getAccount( i ).toString() );
            res.append( '\n' );
        }
        return res.toString();
    }

}
