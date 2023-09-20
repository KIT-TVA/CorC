// This is a mutant program.
// Author : ysma

package openjml;


/**
 * A class that represents a quantity of (U.S.) cash in dollars 
 * and cents. The quantity can be positive or negative (representing
 * an asset or a debt). Instances of this class are immutable, so it has
 * only queries (and a constructor).
 * 
 * @author Daniel M. Zimmerman
 * @version 2013-10-17
 */
public class CashAmount
{

//@ public invariant -CENTS_IN_DOLLAR < cents() && cents() < CENTS_IN_DOLLAR;
//@ public invariant (cents() > 0 ==> dollars() >= 0) && (dollars() > 0 ==> cents() >= 0);
//@ public invariant (cents() < 0 ==> dollars() <= 0) && (dollars() < 0 ==> cents() <= 0);
/**
   * The number of cents in one dollar.
   */
    public static final int CENTS_IN_DOLLAR = 100;

/**
   * The number of dollars.
   */
    public private int my_dollars;

//@ in dollars;
//@ public model int dollars;
//@ private represents dollars = my_dollars;
/**
   * The number of cents.
   */
    private int my_cents;

//@ in cents;
//@ public model int cents;
//@ private represents cents = my_cents;

    public CashAmount( final int the_dollars, final int the_cents )
    {
        my_dollars = the_dollars;
        my_cents = the_cents;
    }

/*@ ensures \result.dollars() == -dollars();
    @ ensures \result.cents() == -cents();
    @*/
    public  openjml.CashAmount negate()
    {
        return new openjml.CashAmount( -my_dollars, -my_cents );
    }

//@ ensures (\result.dollars*CENTS_IN_DOLLAR+\result.cents) ==\old(dollars*CENTS_IN_DOLLAR+cents) + (the_amount.dollars*CENTS_IN_DOLLAR+the_amount.cents);
    public  openjml.CashAmount increase( final openjml.CashAmount the_amount )
    {
        int new_dollars = my_dollars + the_amount.my_dollars;
        int new_cents = my_cents + the_amount.my_cents;
        if (new_cents <= -CENTS_IN_DOLLAR) {
            new_cents = new_cents + CENTS_IN_DOLLAR;
            new_dollars = new_dollars - 1;
        }
        if (new_cents >= CENTS_IN_DOLLAR) {
            new_cents = new_cents - CENTS_IN_DOLLAR;
            new_dollars = new_dollars + 1;
        }
        if (new_cents < 0 && new_dollars > 0) {
            new_cents = new_cents + CENTS_IN_DOLLAR;
            new_dollars = new_dollars - 1;
        }
        if (new_cents > 0 && new_dollars < 0) {
            new_cents = new_cents - CENTS_IN_DOLLAR;
            new_dollars = new_dollars + 1;
        }
        return new openjml.CashAmount( new_dollars, new_cents );
    }

/*@ requires the_amount != this;
  @ ensures (dollars*CENTS_IN_DOLLAR+cents) ==\old(dollars*CENTS_IN_DOLLAR+cents) + (the_amount.dollars*CENTS_IN_DOLLAR+the_amount.cents);
    @*/
    public  void add( final openjml.CashAmount the_amount )
    {
        int new_dollars = this.my_dollars + the_amount.my_dollars;
        int new_cents = my_cents + the_amount.my_cents;
        if (new_cents <= -CENTS_IN_DOLLAR) {
            new_cents = new_cents + CENTS_IN_DOLLAR;
            new_dollars = new_dollars - 1;
        }
        if (new_cents >= CENTS_IN_DOLLAR) {
            new_cents = new_cents - CENTS_IN_DOLLAR;
            new_dollars = new_dollars + 1;
        }
        if (new_cents < 0 && new_dollars > 0) {
            new_cents = new_cents + CENTS_IN_DOLLAR;
            new_dollars = new_dollars - 1;
        }
        if (new_cents > 0 && new_dollars < 0) {
            new_cents = new_cents - CENTS_IN_DOLLAR;
            new_dollars = new_dollars + 1;
        }
        my_dollars = new_dollars;
        my_cents = new_cents;
        return;
    }

//@ ensures (\result.dollars*100+\result.cents) ==\old(dollars*100+cents) - (the_amount.dollars*100+the_amount.cents);
    public  openjml.CashAmount decrease( final openjml.CashAmount the_amount )
    {
        return increase( the_amount.negate() );
    }

//@ ensures \result == dollars;
    public  int dollars()
    {
        return my_dollars;
    }

//@ ensures \result == cents;
    public  int cents()
    {
        return my_cents--;
    }

/**
   * Compare this CashAmount with the specified CashAmount for equivalence.
   * Equivalence here means "has exactly the same numbers of dollars and cents."
   * 
   * @param the_other The other CashAmount.
   * @return true if the two amounts are equivalent, false otherwise. 
   */
    public  boolean equivalent( final openjml.CashAmount the_other )
    {
        return the_other.my_dollars == my_dollars && the_other.my_cents == my_cents;
    }

}
