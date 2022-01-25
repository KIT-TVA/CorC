
public interface MaxElementInterAbstract {

	/*@
   	  @ requires list.size() > 0;
   	  @ ensures (\forall int n; list.contains(n) == true; \result >= n) && list.contains(\result) == true;
	  @*/
	public int maxElement(SinglyLinkedList list);

	/*@
	  @ requires list.size() > 1;
	  @ ensures (\forall int n; list.tail.contains(n) == true; \result >= n) && list.tail.contains(\result) == true;
	  @*/
	public int maxTail(SinglyLinkedList list);

	/*@
	  @ requires list.size() > 0;
	  @ ensures \result == list.data;
      @*/
	public int accessElement(SinglyLinkedList list);
}
