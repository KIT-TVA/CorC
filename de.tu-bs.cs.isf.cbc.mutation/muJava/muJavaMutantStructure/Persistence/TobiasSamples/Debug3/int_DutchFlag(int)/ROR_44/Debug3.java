// This is a mutant program.
// Author : ysma

public class Debug3
{

/*@
@ normal_behavior
@ requires true; 
@ ensures \result>=0 ==> a[\result]==x;
@*/
    public static  int linearSearch( int[] a, int x )
    {
        int i = a.length - 1;
/*@ loop_invariant !(\exists int q; q >= i+1 && q < a.length; a[q]==x) && i>=-1 && i<a.length;
		  @ decreases i+1;
		  @*/
        while (i >= 0 && a[i] != x) {
            i = i - 1;
        }
        return i;
    }

/*@
@ normal_behavior
@ requires A.length > 0;
@ ensures (\forall int q; q >= 0 & q < A.length; A[\result]>=A[q]);
@*/
    public static  int maxElement( int[] A )
    {
        int i = 0;
        int j = 1;
/*@ loop_invariant (\forall int q; q >= 0 && q < j; A[i]>=A[q]) && i>=0 && i<A.length && j>0 && j<=A.length && j>i;
		  @ decreases A.length - j;
		  @ assignable i,j;
		  @*/
        while (j != A.length) {
/*@
			@ normal_behavior
			@ requires (\forall int q; q >= 0 && q < j; A[i]>=A[q])&& i>=0 && i<A.length && j>0 && j<=A.length && j>i;
			@ ensures (\forall int q; q >= 0 && q < j+1; A[i]>=A[q])&& i>=0 && i<A.length && j>0 && j<=A.length && j>=i;
			@ assignable i;
			@*/
            {
                if (A[j] > A[i]) {
                    i = j;
                } else {
                    if (A[j] <= A[i]) {
                        ;
                    }
                }
            }
/*@
			@ normal_behavior
			@ requires (\forall int q; q >= 0 && q < j+1; A[i]>=A[q])&& i>=0 && i<A.length && j>0 && j<=A.length && j>=i;
			@ ensures (\forall int q; q >= 0 && q < j; A[i]>=A[q])&& i>=0 && i<A.length && j>0 && j<=A.length && j>i && j==\old(j)+1;
			@ assignable j;
			@*/
            {
                j = j + 1;
            }
        }
        return i;
    }

    static int wb;

    static int wt;

    static int bb;

/*@
	@ normal_behavior
	@ requires A.length > 0 && (\forall int i; i>=0 & i<A.length; A[i] == 0 || A[i] == 1 || A[i] == 2);
	@ ensures (\forall int q; q >= 0 && q < wb; \result[q]==0) && (\forall int q; q >= wb && q < wt; \result[q]==1) && (\forall int q; q >= bb && q < \result.length; \result[q]==2) && 0<=wb && wb<=wt && wt==bb && bb<=A.length;
	@*/
    public static  int[] DutchFlag( int[] A )
    {
        wb = 0;
        wt = 0;
        bb = A.length;
/*@ loop_invariant (\forall int i; i>=0 & i<A.length; A[i] == 0 || A[i] == 1 || A[i] == 2) && (\forall int q; q >= 0 && q < wb; A[q]==0) && (\forall int q; q >= wb && q < wt; A[q]==1) && (\forall int q; q >= bb && q < A.length; A[q]==2) && 0<=wb && wb<=wt && wt<=bb && bb<=A.length;
		  @ decreases bb-wt;
		  @*/
        while (wt != bb) {
            if (A[wt] >= 0) {
                int t = A[wt];
                A[wt] = A[wb];
                A[wb] = t;
                wt = wt + 1;
                wb = wb + 1;
            } else {
                if (A[wt] == 1) {
                    wt = wt + 1;
                } else {
                    if (A[wt] == 2) {
                        int t = A[wt];
                        A[wt] = A[bb - 1];
                        A[bb - 1] = t;
                        bb = bb - 1;
                    }
                }
            }
        }
        return A;
    }

}
