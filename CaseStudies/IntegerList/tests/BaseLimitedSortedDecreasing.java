import org.testng.ITestContext;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.function.Supplier;

public class IntList {
public int[] data = new int[]{-2147483648, -1, 0, 1, 2147483647};
	public int LIMIT = 0;
	
	public IntList(int[] data, int LIMIT) {
		this.data = data;
		this.LIMIT = LIMIT;
	}
	
	public IntList(int[] data) {
		this.data = data;
	}
	
	public IntList() {
		
	}
	
	@Test
	public void dummyMethod(ITestContext context) {
		int i = 0;
	data = new int[]{-2147483648};
	int[] tmp = new int[]{-2147483648, -1};
		int newTop = 0;
		int[] old_data = data;
		//[checks]
		if(!(true)) context.setAttribute("skip", "true");
		//[end_checks]
		tmp = new int[data.length+1];
		tmp[tmp.length-1] = newTop;
		i = 0;
		while (i < data.length) {
			context.setAttribute("executed", "");
			tmp[i] = data[i];
i++;
			boolean predicate0 = false;
			{
				Assert.assertTrue(tmp.length == (data.length + 1));
				predicate0 = true;
			}
			boolean predicate1 = false;
			{
				boolean predicate2 = false;
				{
					Assert.assertTrue(tmp[tmp.length-1] == newTop);
					predicate2 = true;
				}
				boolean predicate3 = false;
				{
					for (int j = 0; j < i; j++) {
						Assert.assertTrue(data[j] == tmp[j]);
					}
					predicate3 = true;
				}
				Assert.assertTrue(predicate2 && predicate3);
				predicate1 = true;
			}
			Assert.assertTrue(predicate0 && predicate1);
		}
		context.setAttribute("path", "statement -> composition -> statement2 -> composition -> statement2 -> composition -> statement1 -> composition -> statement2 -> repetition -> statement1 -> tmp[i] = data[i];i++;");
		
	}
}