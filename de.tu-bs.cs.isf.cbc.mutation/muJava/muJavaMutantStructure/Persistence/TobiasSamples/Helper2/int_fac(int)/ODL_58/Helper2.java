// This is a mutant program.
// Author : ysma

public class Helper2
{

    public static  boolean sorted( int[] a )
    {
        if (a != null) {
            if (a.length > 1) {
                for (int i = 1; i < a.length; i++) {
                    if (a[i - 1] >= a[i]) {
                        return false;
                    }
                }
                return true;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public static  boolean equal( char[] A, int begin, int end, char[] p )
    {
        if (A != null && p != null && A.length > 0 && begin < end && begin >= 0 && end <= A.length) {
            java.lang.String AAsString = "";
            java.lang.String pAsString = "";
            for (int i = begin; i < end; i++) {
                AAsString += A[i];
            }
            for (int i = 0; i < p.length; i++) {
                pAsString += p[i];
            }
            return AAsString.equals( pAsString );
        } else {
            return false;
        }
    }

    public static  int pow( int a, int b )
    {
        if (b == 0) {
            return 1;
        }
        int res = a;
        for (int i = 0; i < b - 1; i++) {
            res *= a;
        }
        return res;
    }

/*@
	  @ public normal_behavior
	  @ requires n==0;
	  @ ensures \result == 1;
	  @ also
	  @ public normal_behavior
	  @ requires n==1;
	  @ ensures \result == 1;
	  @ also
	  @ public normal_behavior
	  @ requires n>=2;
	  @ ensures \result == (\product int i; i>=1 && i<=n; i);
	  @*/
    public static  int fac( int n )
    {
        if (n == 0) {
            return 1;
        } else {
            if (n == 1) {
                return 1;
            } else {
                if (n >= 2) {
                    return n;
                } else {
                    return 1;
                }
            }
        }
    }

}
