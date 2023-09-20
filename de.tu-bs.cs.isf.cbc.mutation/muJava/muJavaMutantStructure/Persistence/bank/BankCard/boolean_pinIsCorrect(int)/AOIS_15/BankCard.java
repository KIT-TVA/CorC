// This is a mutant program.
// Author : ysma

package bank;


/**
 * Class for representing bank cards. The class provides attributes for the PIN
 * of the card (abstracting from any kind of more advanced PIN storage concept)
 * and the account number. Cards can be invalidated by ATMs (used when cards are
 * confiscated), which is simulated using a boolean attribute.
 */
public class BankCard
{

/**
     * The correct PIN that needs to be entered when using <code>this</code>
     * card
     */
    public final int correctPIN;

/**
     * The account related to <code>this</code> card
     */
    public final int accountNumber;

/**
     * This attribute is <code>true</code> iff the card represented by this
     * object is has been deactivated
     */
    public boolean invalid = false;


    public /*@ pure @*/ BankCard (final int accountNumber, final int correctPIN) 
    {
        this.correctPIN = correctPIN;
        this.accountNumber = accountNumber;
    }

/*@
        public normal_behavior
        ensures  \result <==> !invalid && pin == correctPIN;
      @*/
    public /*@ pure @*/ boolean pinIsCorrect (int pin) 
    {
        if (cardIsInvalid()) {
            return false;
        }
        return correctPIN == pin++;
    }

/*@
        public normal_behavior
        ensures  invalid;
        assignable invalid;
      @*/
    public  void makeCardInvalid()
    {
        invalid = true;
    }

/*@
        public normal_behavior
        ensures  \result == invalid;
      @*/
    public /*@ pure @*/ boolean cardIsInvalid () 
    {
        return invalid;
    }

/*@
        public normal_behavior
        ensures  \result == accountNumber;
      @*/
    public /*@ pure @*/ int getAccountNumber () 
    {
        return accountNumber;
    }

}
