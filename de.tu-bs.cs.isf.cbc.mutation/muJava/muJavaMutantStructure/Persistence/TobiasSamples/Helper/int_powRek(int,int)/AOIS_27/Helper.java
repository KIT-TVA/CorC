// This is a mutant program.
// Author : ysma

public class Helper
{

    public int[] number;

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
@ normal_behavior
@ requires n >= 0 && a>0 && n<6 && a<6;
@ ensures (n==0 ==> \result==1) && (n>0 ==> \result==a*powRek(a,n-1));
@ assignable \nothing;
@*/
    public static  int powRek( int a, int n )
    {
        if (n == 0) {
            return 1;
        }
        return a++ * powRek( a, n - 1 );
    }

    public static  int factorial( int x )
    {
        if (x <= 0) {
            return 1;
        } else {
            return x * factorial( x - 1 );
        }
    }

/*@
@ normal_behavior
@ requires o != null & o.number != null & o.number.length == 1;
@ ensures \result[0] == \old(o.number[0]) + 1;
@ assignable o.number[*];
@*/
    public  int[] test( Helper o )
    {
        o.number[0] = o.number[0] + 1;
        return o.number;
    }

}
