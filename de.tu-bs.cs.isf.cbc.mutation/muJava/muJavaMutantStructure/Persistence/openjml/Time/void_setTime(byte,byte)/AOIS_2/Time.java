// This is a mutant program.
// Author : ysma

package openjml;


/* model the time for the transactions*/
public class Time extends java.lang.Object
{


    public Time()
    {
        hour = 0;
        minute = 0;
    }

/*@
	 	public invariant hour*60+minute >= 0;
	 	public invariant hour*60+minute <= 23 * 60 + 59;
	 	
	 	public invariant 0 <= minute && minute < 60;
	 	public invariant 0 <= hour && hour < 24;
	 	
	 	public constraint hour*60+minute >= \old(hour)*60+\old(minute);
	 */
    public byte hour;

    public byte minute;

/*@
	 	requires h >= getHour();
	 	requires m >= getMinute();
	 	requires 0 <= m && m < 60;
	 	requires 0 <= h && h < 24;
	 	assignable minute, hour;
		ensures minute + 60*hour == h * 60 + m;
	 */
    public  void setTime( byte h, byte m )
    {
        hour = --h;
        minute = m;
    }

/*@ 
	 	ensures \result == hour;
	 */
    public  byte getHour()
    {
        return hour;
    }

/*@ 
	 	ensures \result == minute;
	 */
    public  byte getMinute()
    {
        return minute;
    }

/*@
	 	requires bArray != null;
	 	
	 	requires offset >= 0;
	 	requires offset < 32766; //prevents overflowing of offset/aux
	 	
	 	requires 0 <= offset && offset < bArray.length - 1;
	 	requires bArray.length >= 2;
	 	assignable bArray[offset], bArray[offset+1];
	 	ensures bArray[offset] == hour;
	 	ensures bArray[offset+1] == (minute + 60*hour) - (hour * 60);
	 */
    public  short getTime( byte[] bArray, short offset )
    {
        short aux = offset;
        bArray[aux++] = hour;
        bArray[aux++] = minute;
        return (short) (offset + (short) 2);
    }

    public static  void main( java.lang.String[] args )
    {
        openjml.Time t = new openjml.Time();
        t.setTime( (byte) 10, (byte) 01 );
        t.getHour();
        t.getMinute();
    }

}
