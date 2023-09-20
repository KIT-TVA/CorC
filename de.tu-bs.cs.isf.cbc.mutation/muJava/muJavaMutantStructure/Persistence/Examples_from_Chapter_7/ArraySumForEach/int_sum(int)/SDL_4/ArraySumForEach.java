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
/*@ maintaining sum == (\sum int j; 
      @                          0 <= j && j < \index; array[j]);
      @ maintaining \index >= 0 && \index <= array.length;
      @ decreasing array.length - \index;
      @ assignable \strictly_nothing;
      @*/
        for (int value: array) {
            sum += value;
        }
        return 0;
    }

}
