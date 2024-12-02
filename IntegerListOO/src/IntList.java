

public class IntList {

	public /*@spec_public@*/ int[] data;

	/*@
	@ normal_behavior
	@ requires true & \old(data) == data & data != null & data.length >= 0;
	@ ensures (\exists int z;(0 <= z && z < data.length&& data[z] == newTop))&& (\forall int k; (0 <= k && k < \old(data).length==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data)[k])))) && data[data.length - 1] == newTop;
	@ assignable data[*];
	@*/
	public /*@helper@*/ void pushBase(int newTop) {
		int i;
		int[] tmp;
		tmp = new int[data.length+1];
		tmp[tmp.length-1] = newTop;
		i = 0;
		while (i < data.length) {
			tmp[i] = data[i];
			i++;
		}
		data = tmp;

	}
	
	/*@
	@ normal_behavior
	@ requires true & \old(data) == data & data != null & data.length >= 0;
	@ ensures (\exists int z;(0 <= z && z < data.length&& data[z] == newTop))&& (\forall int k; (0 <= k && k < \old(data).length==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data)[k])))) && data[data.length - 1] == newTop;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ void pushCons(int newTop) {
		pushBase(newTop);
	}
	
	/*@
	@ normal_behavior
	@ requires (\forall int k; (0 <= k && k < data.length-1 ==> data[k] >= data[k+1])) & \old(data).length >= 0 & \old(data) == data & data != null & \old(data) != null & data.length >= 0;
	@ ensures (\forall int k; (0 <= k && k < \old(data).length ==> (\exists int z; (0 <= z && z < data.length && data[z] == \old(data)[k]))))&& ((\forall int k; (0 <= k && k < data.length-1 ==> (data[k] >= data[k+1]))) || (\forall int k; (0 <= k && k < data.length-1 ==> (data[k] <= data[k+1]))));
	@ assignable \nothing;
	@*/
	public /*@helper@*/ void push(int newTop) {
		pushCons(newTop);
		sort();

	}
	
	/*@
	@ normal_behavior
	@ requires data == \old(data) & data != null;
	@ ensures ((\forall int k; (0 <= k && k < \old(data).length==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data)[k]))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] >= data[k+1])));
	@ assignable data[*];
	@*/
	public /*@helper@*/ void sort() {
		int tmp;
		int j;
		int i;
		i = 0;
		while (i < data.length) {
			j = data.length-2;
			while (j>=i) {
				if (data[j] < data[j+1]) {
					tmp = data[j];
					data[j] = data[j+1];
					data[j+1] = tmp;
				} else if (data[j] >= data[j+1]) {
					;
				}
				j--;
			}
			i++;
		}

	}
}
