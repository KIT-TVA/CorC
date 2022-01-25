
public class MaxElement {

	/*@
   	  @ requires list.size() > 0;
   	  @ ensures (\forall int n; list.contains(n); \result >= n) && list.contains(\result);
	  @*/
	static int maxElement(SinglyLinkedList list) {
		if (list.size() == 1) return accessElement(list);
		if (accessElement(list) >= maxElement(list.tail)) {
			return accessElement(list);
		} else {
			return maxTail(list);
		}
	}

	/*@
	  @ requires list.size() > 1;
	  @ ensures (\forall int n; list.tail.contains(n); \result >= n) && list.tail.contains(\result);
	  @*/
	static int maxTail(SinglyLinkedList list) {
		return maxElement(list.tail);
	}

	/*@
	  @ requires list.size() > 0;
	  @ ensures \result == list.data;
      @*/
	static int accessElement(SinglyLinkedList list) {
		return list.data;
	}
	
	/*@
	@ normal_behavior
	@ requires A.length > 0;
	@ ensures (\forall int q; q >= 0 & q < A.length; A[\result]>=A[q]);
	@*/
	public static int maxElement(int[] A) {
		int i = 0;
		int j = 1;
		//@ loop_invariant (\forall int q; q >= 0 && q < j; A[i]>=A[q]) && i>=0 && i<A.length && j>0 && j<=A.length;
		//@ decreases A.length - j;
		while (j != A.length) {
			if (A[j] > A[i]) {
				i = j;
			} else if (A[j] <= A[i]) {
				;
			}
			j = j + 1;
		}
		return i;
	}
	
	/*@
	@ normal_behavior
	@ requires a.length > 0 && index >=0 && index <= a.length && max >= 0 && max < index && (\forall int q; q >= 0 & q < index; a[max]>=a[q]);
	@ ensures (\forall int q; q >= 0 && q < a.length; a[\result]>=a[q]);
	@*/
	public static int maxElement1(int[] a, int index, int max) {
		if (index == a.length) return max;
		return checkNextIndex(a, index, max);
	}

	/*@
	@ normal_behavior
	@ requires a.length > 0 && index >=0 && index < a.length && max >= 0 && max < index && (\forall int q; q >= 0 & q < index; a[max]>=a[q]);
	@ ensures (\forall int q; q >= 0 && q < a.length; a[\result]>=a[q]);
	@*/
	private static int checkNextIndex(int[] a, int index, int max) {
		if (a[index] >= a[max]) {
			return maxElement1(a, index+1, index);
		} else {
			return maxElement1(a, index+1, max);
		}
	}
	
	public static void main(String[] args) {
		//int a[] = {800,543,34,653,25,3,724};
		//int b[] = {2};
		//System.out.println(maxElement1(a));
		SinglyLinkedList sList = new SinglyLinkedList(7);    
        
        //Add nodes to the list    
        //sList = sList.addNode(1);    
        //sList = sList.addNode(7);    
        //sList = sList.addNode(3);    
        //sList = sList.addNode(4);    
            
        System.out.println(maxElement(sList));
	}
	
	/*@
	@ normal_behavior
	@ requires a.length > 0;
	@ ensures (\forall int q; q >= 0 && q < a.length; a[\result]>=a[q]);
	@*/
	private static int maxElement1(int[] a) {
		return maxElement1(a, 1, 0);
	}
}
