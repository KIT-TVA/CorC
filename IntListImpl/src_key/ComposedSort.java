public class ComposedSort {

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
				}
				j--;
			}
			i++;
		}
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
	public void sortIncStart(int[]data) {
		int i = 0;
		sortFirstLoop(data,i);
	}

	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures ((\forall int k; (0 <= k && k < \old(data).length==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] >= data[k+1])));
	@ assignable \everything;
	@*/
	public void sortDecStart(int[]data) {
		int i = 0;
		sortFirstLoop(data,i);
	}

	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures ((\forall int k; (0 <= k && k < \old(data.length)==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] <= data[k+1])));
	@ assignable \everything;
	@*/
	public void sortFirstLoop(int[]data,int i) {
		while (i < data.length) {
			int j = data.length - 2;
			sortSecondLoop(data,j,i);
			i++;
		}
	}

	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures ((\forall int k; (0 <= k && k < \old(data.length)==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] <= data[k+1])));
	@ assignable \everything;
	@*/
	public void sortSecondLoop(int[]data,int j,int i) {
		while (j >= i) {
			sortIncCheck(data,j);
			j--;
		}
	}

	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures ((\forall int k; (0 <= k && k < \old(data.length)==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] <= data[k+1])));
	@ assignable \everything;
	@*/
	public void sortIncCheck(int[]data,int j) {
		if (data[j] > data[j + 1]) {
			sortSwap(data,j);
		}
	}

	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures ((\forall int k; (0 <= k && k < \old(data.length)==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] <= data[k+1])));
	@ assignable \everything;
	@*/
	public void sortDecCheck(int[]data,int j) {
		if (data[j] < data[j + 1]) {
			sortSwap(data,j);
		}
	}

	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures ((\forall int k; (0 <= k && k < \old(data.length)==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] <= data[k+1])));
	@ assignable \everything;
	@*/
	public void sortSwap(int[]data,int j) {
		int tmp = data[j];
		data[j] = data[j + 1];
		data[j + 1] = tmp;
	}
}