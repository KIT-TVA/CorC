
public class IntListInter {

	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0;
	 @ ensures data[data.length-1] == newTop
     @ && (\forall int k; 0 <= k && k < \old(data.length); data[k] == \old(data[k]));
     @ assignable \everything;
	 @*/
	public   void push(int[] data, int newTop) {
		int[] tmp = new int[data.length + 1];
		tmp[tmp.length - 1] = newTop;
		//@loop_invariant tmp.length == data.length + 1 && tmp[tmp.length-1] == newTop && (\forall int j; 0 <= j & j < i; data[j] == tmp[j]);
		//@decreases data.length-i;
		for (int i = 0; i < data.length; i++) {
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
	public   void pushAndSort(int[] data, int newTop) {
			push(data, newTop);
			sortIncreasing(data);

		}
	
		/*@
		@ normal_behavior
		@ requires true;
		@ ensures ((\forall int k; (0 <= k && k < \old(data).length==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] >= data[k+1])));
		@ assignable \everything;
		@*/
		public   void sortDecreasing(int[] data) {
				int i = 0;
				//@ loop_invariant (\forall int k; (0 <= k && k < i ==>(\forall int m; (k < m && m < data.length ==>data[k] >= data[m]))));
				//@ decreases data.length - i ;
				while (i < data.length) {
					int j = data.length-2;
					//@ loop_invariant (\forall int k; (0 <= k && k < i ==>(\forall int m; (k < m && m < data.length ==>data[k] >= data[m]))))&& (\forall int h; (j < h && h < data.length ==> data[j+1] >= data[h]));
					//@ decreases j+1;
					while (j>=i) {
						if (data[j] < data[j+1]) {
							int tmp = data[j];
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
		
		/*@
		@ normal_behavior
		@ requires true;
		@ ensures ((\forall int k; (0 <= k && k < \old(data.length)==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] <= data[k+1])));
		@ assignable \everything;
		@*/
		public   void sortIncreasing(int[] data) {
				int i = 0;
				//@ loop_invariant (\forall int k; (0 <= k && k < i ==>(\forall int m; (k < m && m < data.length ==>data[k] <= data[m]))));
				//@ decreases data.length - i ;
				while (i < data.length) {
					int j = data.length-2;
					//@ loop_invariant (\forall int k; (0 <= k && k < i ==>(\forall int m; (k < m && m < data.length ==>data[k] >= data[m]))))&& (\forall int h; (j < h && h < data.length ==> data[j+1] <= data[h]));
					//@ decreases j+1;
					while (j>=i) {
						if (data[j] > data[j+1]) {
							int tmp = data[j];
							data[j] = data[j+1];
							data[j+1] = tmp;
						} else if (data[j] <= data[j+1]) {
							;
						}
						j--;
					}
					i++;
				}

			}
}
