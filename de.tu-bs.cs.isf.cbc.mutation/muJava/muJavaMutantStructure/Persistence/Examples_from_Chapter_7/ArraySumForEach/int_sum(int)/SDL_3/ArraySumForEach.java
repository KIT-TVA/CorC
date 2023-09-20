// This is a mutant program.
// Author : ysma

package Examples_from_Chapter_7;


public class ArraySumForEach
{

/*@ requires array != null;
  @ ensures \result == (\sum int i; 
  @                          0 <= i && i < array.length; array[i]);
  @*/
    public static  int sum( int[] array )
    {
        int sum = 0;

        for (;;) {
            sum += value;
        }
        return sum;
    }

}
