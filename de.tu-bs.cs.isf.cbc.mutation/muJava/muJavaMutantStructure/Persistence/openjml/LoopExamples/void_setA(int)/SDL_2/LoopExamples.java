// This is a mutant program.
// Author : ysma

package openjml;


public class LoopExamples
{

    private int[] a;


    LoopExamples()
    {
        a = new int[5];
    }

//@ requires a != null;
    public  void setA( int[] a )
    {
    }

/*@ pure*/
     boolean contains( int x )
    {
        boolean found = false;
/*@ loop_invariant 0 <= i && i <= a.length;
        @ loop_invariant found == (\exists int j; 0 <= j && j < i; a[j] == x);
          @*/
        for (int i = 0; i < a.length; i++) {
            if (a[i] == x) {
                found = true;
            }
        }
        return found;
    }

}
