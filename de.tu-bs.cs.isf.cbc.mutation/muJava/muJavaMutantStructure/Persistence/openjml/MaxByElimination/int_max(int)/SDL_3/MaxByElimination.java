// This is a mutant program.
// Author : ysma

package openjml;


public class MaxByElimination
{

/*@ requires a != null && a.length > 0;
	@ ensures (\forall int i; 0<=i && i<a.length; a[i] <= a[\result]);
	  @*/
    public static  int max( int[] a )
    {
        int x = 0;
        int y = a.length - 1;

        while (true) {
            if (a[x] <= a[y]) {
                x++;
            } else {
                y--;
            }
        }
        return x;
    }

}
