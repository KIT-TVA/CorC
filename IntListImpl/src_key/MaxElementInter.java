
public class MaxElementInter {

	/*@
   	  @ requires list.size() > 0;
   	  @ ensures (\forall int n; list.contains(n) == true; \result >= n) && list.contains(\result) == true;
	  @*/
	public   int maxElement(SinglyLinkedList list) {
		if (list.size() == 1) return accessElement(list);
		if (accessElement(list) >= maxElement(list.tail)) {
			return accessElement(list);
		} else {
			return maxTail(list);
		}
	}

	/*@
	  @ requires list.size() > 1;
	  @ ensures (\forall int n; list.tail.contains(n) == true; \result >= n) && list.tail.contains(\result) == true;
	  @*/
	public   int maxTail(SinglyLinkedList list) {
		return maxElement(list.tail);
	}

	/*@
	  @ requires list.size() > 0;
	  @ ensures \result == list.data;
      @*/
	public   int accessElement(SinglyLinkedList list) {
		return list.data;
	}
}
