
public class IntListInterAbstract {

	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0;
	 @ ensures data[data.length-1] == newTop
     @ && (\forall int k; 0 <= k && k < \old(data.length); data[k] == \old(data[k]));
     @ assignable \everything;
	 @*/
	public void push(int[] data, int newTop);
	
	/*@
	@ normal_behavior
	@ requires (\forall int k; (0 <= k && k < data.length-1 ==> data[k] >= data[k+1]));
	@ ensures (\forall int k; (0 <= k && k < \old(data.length) ==> (\exists int z; (0 <= z && z < data.length && data[z] == \old(data[k])))))&& ((\forall int k; (0 <= k && k < data.length-1 ==> (data[k] >= data[k+1]))) || (\forall int k; (0 <= k && k < data.length-1 ==> (data[k] <= data[k+1]))));
	@ assignable \everything;
	@*/
	public  void pushAndSort(int[] data, int newTop);
	
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
		public void sortIncreasing(int[] data);
}
