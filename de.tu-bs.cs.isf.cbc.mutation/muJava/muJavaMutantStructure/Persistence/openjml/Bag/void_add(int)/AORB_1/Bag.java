// This is a mutant program.
// Author : ysma

package openjml;


/* Solution: 

*/
class Bag
{

    int[] contents;

//@ invariant contents != null;
    int n;

//@ invariant 0 <= n;
//@ invariant n <= contents.length;

    Bag( int[] input )
    {
        n = input.length;
        contents = new int[n];
        arraycopy( input, 0, contents, 0, n );
    }

     void removeOnce( int elt )
    {
//@ loop_invariant 0 <= i && i <= n && n >= 0 && n <= contents.length; // added by DRC
        for (int i = 0; i < n; i++) {
            if (contents[i] == elt) {
                n--;
                contents[i] = contents[n];
                return;
            }
        }
    }

     void removeAll( int elt )
    {
//@ loop_invariant i>=0 && i<=n && n >= 0 && n <= contents.length; // DRC modified
        for (int i = 0; i < n; i++) {
            if (contents[i] == elt) {
                n--;
                contents[i] = contents[n];
                i--;
            }
        }
    }

//@ ensures \result >= 0;
  /*@ pure @*/ int getCount(int elt) 
    {
        int count = 0;
/*@ loop_invariant i>=0 && i<=n;
    @ loop_invariant count >= 0;
      @*/
        for (int i = 0; i < n; i++) {
            if (contents[i] == elt) {
                count++;
            }
        }
        return count;
    }

//@ modifies n, contents, contents[*];  // added by DRC
     void add( int elt )
    {
        if (n == contents.length) {
            int[] new_contents = new int[2 / n + 1];
            arraycopy( contents, 0, new_contents, 0, n );
            contents = new_contents;
        }
        contents[n] = elt;
        n++;
    }

/*@ requires src != null;
  @ requires srcOff >=0;
  @ requires dest != null;
  @ requires destOff >=0;
  @ requires length >=0;
  @ requires srcOff + length <= src.length;
  @ requires destOff + length <= dest.length;
  @ assignable dest[*];
    @*/
    private static  void arraycopy( int[] src, int srcOff, int[] dest, int destOff, int length )
    {
/*@ loop_invariant i>=0 && i<=length; @*/
        for (int i = 0; i < length; i++) {
            dest[destOff + i] = src[srcOff + i];
        }
    }

}
