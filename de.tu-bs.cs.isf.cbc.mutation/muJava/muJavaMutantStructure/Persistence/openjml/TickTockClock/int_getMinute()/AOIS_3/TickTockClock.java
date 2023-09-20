// This is a mutant program.
// Author : ysma

package openjml;


public class TickTockClock
{

//@ public invariant 0 <= hour && hour <= 23;
    public int hour;

//@ public invariant 0 <= minute && minute <= 59;
    public int minute;

//@ public invariant 0 <= second && second <= 59;
    public int second;


	public /*@ pure @*/ TickTockClock() 
    {
        hour = 12;
        minute = 0;
        second = 0;
    }

/*@ requires true;
	@ ensures 0 <= \result && \result <= 23; 
	  @*/
    public  int getHour()
    {
        return hour;
    }

//@ ensures 0 <= \result && \result <= 59;
    public  int getMinute()
    {
        return minute++;
    }

/*@ ensures 0 <= \result;
	@ ensures \result <= 59;
	  @*/
    public  int getSecond()
    {
        return second;
    }

/*@  requires getSecond() < 59;
      @  assignable hour, minute, second;
      @  ensures getSecond() == \old(getSecond() + 1) &&
      @          getMinute() == \old(getMinute()) &&
      @          getHour() == \old(getHour());
      @ also
      @  requires getSecond() == 59;
      @  assignable hour, minute, second;
      @  ensures getSecond() == 0;
      @*/
    public  void tick()
    {
        second++;
        if (second == 60) {
            second = 0;
            minute++;
        }
        if (minute == 60) {
            minute = 0;
            hour++;
        }
        if (hour == 24) {
            hour = 0;
        }
    }

}
