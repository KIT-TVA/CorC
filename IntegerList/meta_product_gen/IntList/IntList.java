public class IntList {
	public int[] data;
	/*@ invariant data != null; @*/
	/*@ invariant this != null; @*/
	
	
	/*@
	@ normal_behavior
	@ requires (MetaVariables.FV_INCREASING == true ==> (this.data == \old(this.data))) && (MetaVariables.FV_INCREASING == false ==> ((MetaVariables.FV_DECREASING == true ==> (this.data == \old(this.data)))));
	@ ensures (MetaVariables.FV_INCREASING == true ==> (((\forall int k; (0 <= k && k < \old(this.data).length==> (\exists int z; (0 <= z && z < this.data.length&& this.data[z] == \old(this.data)[k]))))&& (\forall int k; (0 <= k && k < this.data.length-1 ==> this.data[k] <= this.data[k+1]))))) && (MetaVariables.FV_INCREASING == false ==> ((MetaVariables.FV_DECREASING == true ==> (((\forall int k; (0 <= k && k < \old(this.data).length==> (\exists int z; (0 <= z && z < this.data.length&& this.data[z] == \old(this.data)[k]))))&& (\forall int k; (0 <= k && k < this.data.length-1 ==> this.data[k] >= this.data[k+1])))))));
	@ assignable data;
	*/
	public void sort() {
		
	}
	
	/*@
	@ normal_behavior
	@ requires (MetaVariables.FV_SORTED == true ==> ((\forall int k; (0 <= k && k < this.data.length-1 ==> this.data[k] >= this.data[k+1])))) && (MetaVariables.FV_SORTED == false ==> ((MetaVariables.FV_CONS == true ==> (true)) && (MetaVariables.FV_CONS == false  ==> ((MetaVariables.FV_BASE == true ==> (true))))));
	@ ensures (MetaVariables.FV_SORTED == true ==> ((\forall int k; (0 <= k && k < \old(this.data).length ==> (\exists int z; (0 <= z && z < this.data.length && this.data[z] == \old(this.data)[k]))))&& ((\forall int k; (0 <= k && k < this.data.length-1 ==> (this.data[k] >= this.data[k+1]))) || (\forall int k; (0 <= k && k < this.data.length-1 ==> (this.data[k] <= this.data[k+1])))))) && (MetaVariables.FV_SORTED == false ==> ((MetaVariables.FV_CONS == true ==> ((MetaVariables.FV_BASE == true ==> ((\exists int z;(0 <= z && z < this.data.length&& this.data[z] == newTop))&& (\forall int k; (0 <= k && k < \old(this.data).length==> (\exists int z; (0 <= z && z < this.data.length&& this.data[z] == \old(this.data)[k])))) && this.data[this.data.length - 1] == newTop)) && this.data[this.data.length - 1] == newTop)) && (MetaVariables.FV_CONS == false  ==> ((MetaVariables.FV_BASE == true ==> ((\exists int z;(0 <= z && z < this.data.length&& this.data[z] == newTop))&& (\forall int k; (0 <= k && k < \old(this.data).length==> (\exists int z; (0 <= z && z < this.data.length&& this.data[z] == \old(this.data)[k])))) && this.data[this.data.length - 1] == newTop))))));
	@ assignable data;
	*/
	public void push(int newTop) {
		
	}
	
}