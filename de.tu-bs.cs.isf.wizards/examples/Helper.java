
public class Helper {
	public static int pow(int a, int b) {
		if (b == 0)
			return 1;
		int res = a;
		for (int i = 0; i < b - 1; i++) {
			res *= a;
		}
		return res;
	}

	public static int factorial(int x) {
		if (x <= 0)
			return 1;
		else
			return x * factorial(x - 1);
	}
}
