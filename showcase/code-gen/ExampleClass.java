public class ExampleClass {

	/*@
	@ normal_behavior
	@ requires N > 0;
	@ ensures N >= n*Helper.pow(2,l) && N < (n+1)*Helper.pow(2,l) && n == 1;
	@ assignable l,n;
	@*/
	public void logarithm(int N, int n, int l) {
		n=N;
		l=0;
		while (n != 1) {
			n=n/2;
			l=l+1;
		}

	}

	/*@
	@ normal_behavior
	@ requires n >= 0;
	@ ensures f == Helper.factorial(n);
	@ assignable f;
	@*/
	public void factorial(int n, int f) {
		if (n == 0) {
			f = 1;
		} else if (n == 1) {
			f = 1;
		} else if (n >= 2) {
			f = n*Helper.factorial(n-1);
		}

	}

	/*@
	@ normal_behavior
	@ requires \dl_app(A, x, 0, A.length) & i >= 0 && i < A.length & A.length > 0 & A != null & A.length < 10;
	@ ensures A[i] == x;
	@ assignable i;
	@*/
	public void linearSearch(int[] A, int x, int i) {
		i = A.length;
		while (A[i] != x) {
			i = i - 1;
		}

	}
}