// This is a mutant program.
// Author : ysma

package bank;


/**
 * Singleton class for representing an assumed system-wide clock (for simplicity
 * we suppose that both the central host and all ATMs are running on the same
 * clock).
 * @stereotype singleton
 */
public class Clock
{

//@ static public invariant clockInstance != null;
    public static final bank.Clock clockInstance = new bank.Clock();

/**
     * The current date/time. We don't care about details of this representation
     * ...
     */
    public int currentDate = 0;


    private Clock()
    {
    }

/*@
        public normal_behavior
        ensures \result > null;
      @*/
    public static  bank.Clock getInstance()
    {
        return clockInstance;
    }

/*@
        public normal_behavior
        ensures clockInstance.currentDate > \old(clockInstance.currentDate);
        assignable clockInstance.currentDate;
      @*/
    public static  void tick()
    {
        getInstance().currentDate++;
    }

/**
     * @return the current date/time
     */
    public /*@ pure @*/ static int getCurrentDate () 
    {
        return getInstance().currentDate;
    }

/**
     * @return some date/time in a very distant past (used to initialise certain
     *         attributes in other classes)
     */
    public /*@ pure @*/ static int getBigBangsDate() 
    {
        return -1000;
    }

/**
     * @return <code>true</code> iff the two given dates refer to the same day
     */
    public /*@ pure @*/ static boolean isSameDay (int dateA, int dateB) 
    {
        return isSameInterval( dateA, dateB, 10 );
    }

/**
     * @return <code>true</code> iff <code>dateA</code> is an earlier date
     *         than <code>dateB</code>
     */
    public /*@ pure @*/ static boolean isEarlier (int dateA, int dateB) 
    {
        return dateA < dateB;
    }

/*@
        public normal_behavior
        ensures  isSameDay(dateA, dateB) ==> \result;
      @*/
    public /*@ pure @*/ static boolean isSameMonth (int dateA, int dateB) 
    {
        return isSameInterval( dateA, dateB, 100 );
    }

    private static  boolean isSameInterval( int dateA, int dateB, int intervalLength )
    {
        return dateA / intervalLength == dateB / intervalLength;
    }

}
