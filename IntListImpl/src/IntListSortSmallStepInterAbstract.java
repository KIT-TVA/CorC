
public interface IntListSortSmallStepInterAbstract {

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures ((\forall int k; (0 <= k && k < \old(data.length)==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] <= data[k+1])));
	@ assignable \everything;
	@*/
	public void sortIncreasing(int[] data);
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures ((\forall int k; (0 <= k && k < \old(data).length==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] >= data[k+1])));
	@ assignable \everything;
	@*/
	public void sortDecreasing(int[] data);
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures ((\forall int k; (0 <= k && k < \old(data.length)==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] <= data[k+1])));
	@ assignable \everything;
	@*/
	public void sortIncStart(int[] data);
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures ((\forall int k; (0 <= k && k < \old(data).length==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] >= data[k+1])));
	@ assignable \everything;
	@*/
	public void sortDecStart(int[] data);
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures ((\forall int k; (0 <= k && k < \old(data.length)==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] <= data[k+1])));
	@ assignable \everything;
	@*/
	public void sortFirstLoop(int[] data, int i);
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures ((\forall int k; (0 <= k && k < \old(data.length)==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] <= data[k+1])));
	@ assignable \everything;
	@*/
	public void sortSecondLoop(int[] data, int j, int i);
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures ((\forall int k; (0 <= k && k < \old(data.length)==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] <= data[k+1])));
	@ assignable \everything;
	@*/
	public void sortIncCheck(int[] data, int j);
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures ((\forall int k; (0 <= k && k < \old(data.length)==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] <= data[k+1])));
	@ assignable \everything;
	@*/
	public void sortDecCheck(int[] data, int j);
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures ((\forall int k; (0 <= k && k < \old(data.length)==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data[k])))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] <= data[k+1])));
	@ assignable \everything;
	@*/
	public void sortSwap(int[] data, int j);
}
