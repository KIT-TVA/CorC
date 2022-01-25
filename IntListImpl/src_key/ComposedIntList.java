public class ComposedIntList {

	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0;
	 @ ensures data[data.length-1] == newTop
     @ && (\forall int k; 0 <= k && k < \old(data.length); data[k] == \old(data[k]));
     @ assignable \everything;
	 @*/
	public void push(int[]data,int newTop) {
		int[]tmp = new int[data.length + 1];
		tmp[tmp.length - 1] = newTop;
		for (int i = 0;i < data.length;i++) {
			tmp[i] = data[i];
		}
		data = tmp;
	}

	
	/*@
	@ normal_behavior
	@ requires (\forall int k; (0 <= k && k < data.length-1 ==> data[k] >= data[k+1]));
	@ ensures (\forall int k; (0 <= k && k < \old(data.length) ==> (\exists int z; (0 <= z && z < data.length && data[z] == \old(data[k])))))&& ((\forall int k; (0 <= k && k < data.length-1 ==> (data[k] >= data[k+1]))) || (\forall int k; (0 <= k && k < data.length-1 ==> (data[k] <= data[k+1]))));
	@ assignable \everything;
	@*/
	public void pushAndSort(int[]data,int newTop) {
		push(data,newTop);
		sortIncreasing(data);
	}

	
		/*@
		@ normal_behavior
		@ requires true;
		@ ensures ((\forall int k; (0 <= k && k < \old(data).length==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] >= data[k+1])));
		@ assignable \everything;
		@*/
		public void sortDecreasing(int[]data) {
		int i = 0;
		while (i < data.length) {
			int j = data.length - 2;
			while (j >= i) {
				if (data[j] < data[j + 1]) {
					int tmp = data[j];
					data[j] = data[j + 1];
					data[j + 1] = tmp;
				}else if (data[j] >= data[j + 1]) {
					;
				}
				j--;
			}
			i++;
		}
	}

		
		/*@
		@ normal_behavior
		@ requires true;
		@ ensures ((\forall int k; (0 <= k && k < \old(data.length)==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] <= data[k+1])));
		@ assignable \everything;
		@*/
		public void sortIncreasing(int[]data) {
		int i = 0;
		while (i < data.length) {
			int j = data.length - 2;
			while (j >= i) {
				if (data[j] > data[j + 1]) {
					int tmp = data[j];
					data[j] = data[j + 1];
					data[j + 1] = tmp;
				}else if (data[j] <= data[j + 1]) {
					;
				}
				j--;
			}
			i++;
		}
	}
}