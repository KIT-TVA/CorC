
public class ArrayList {
	
	private /*@spec_public@*/ boolean changeable = true;
	private /*@spec_public@*/ boolean supportsNull = false;
	private /*@spec_public@*/ boolean supportsDuplicates = false;
	private /*@spec_public@*/ int collectionSize = 0;
	private /*@spec_public@*/ int currentStart = 0;
	public /*@spec_public@*/ int[] elements = new int[10];
		
	/*@
	  @ public exceptional_behavior
	  @ requires !supportsNull;
	  @ requires e == null;
	  @ signals (NullPointerException e) (true);
	  @ 
	  @ also
	  @ public normal_behavior
	  @ requires e != null || supportsNull;
	  @ ensures contains(e);
	  @ assignable collectionSize, elements[*];
	  @ 
	  @ also
	  @ public normal_behavior
	  @ requires e != null || supportsNull;
	  @ requires (contains(e) && supportsDuplicates) || !contains(e);
	  @ ensures collectionSize == \old(collectionSize) + 1;
	  @ ensures \result;
	  @ assignable collectionSize,elements[*];
	  @ also
	  @ public normal_behavior
	  @ requires e != null || supportsNull;
	  @ requires contains(e) && !supportsDuplicates;
	  @ ensures collectionSize == \old(collectionSize);
	  @ ensures !\result;
	  @ assignable collectionSize,elements[*];
	  @ 
	  @ 
	  @ 
	  @*/
	public boolean /*@helper@*/ add(int e) {
		this.elements[collectionSize] = e;
		this.collectionSize++;
		return true;
	}

	
	/*@
	@ public normal_behavior
	@ requires elements != null;
	@ ensures \result <==> (\exists int i;
	@ 0 <= i && i < elements.length && elements[i] != null; elements[i]== o);
	@*/
	public boolean /*@pure helper@*/ contains(int o) {
		return true;
	}
	
	/*@ normal_behavior
	@ requires index < collectionSize;
	@ requires index >= 0;
	@ requires collectionSize > 0;
	@ ensures collectionSize == \old(collectionSize)-1;
	@ ensures elements == \old(elements);
	@ ensures (\forall int i; 0 <= i && i < index;
	elements[i] == \old(elements[i]));
	@ ensures (\forall int i; index <= i && i < collectionSize;
	elements[i] == \old(elements[i+1]));
	@ assignable collectionSize, elements[*];
	@*/
	public void fastRemove(int index) {
		
	}
	
	/*@ normal_behavior
	@ requires collectionSize > 0 && collectionSize < 10;
	@ ensures collectionSize == \old(collectionSize)-1;
	@ ensures elements == \old(elements);
	@ ensures (\forall int i; 0 <= i && i < collectionSize;
	elements[i] == \old(elements[i+1]));
	@ assignable collectionSize, elements[*];
	@*/
	public ArrayList tail() {
		fastRemove(0);
		return this;
	}
	
	/*@ normal_behavior
	@ requires collectionSize > 0 && collectionSize < 10;
	@ requires currentStart >= 0 && currentStart < collectionSize;
	@ ensures elements == \old(elements);
	@ ensures (\forall int i; 0 <= i && i < collectionSize;
	elements[i] == \old(elements[i+1]));
	@ assignable collectionSize, elements[*];
	@*/
	public ArrayList getTail() {
		fastRemove(0);
		return this;
	}
	
	/*@
	  @ normal_behavior
	  @ requires true;
	  @ ensures contains(p) == false;
	  @ ensures collectionSize == \old(collectionSize) - 1;
	  @ assignable collectionSize, elements[*];
	  @*/
	public /*@helper@*/ void remove(int p) {
		
	}
	
	/*@
	  @ normal_behavior
	  @ ensures \result == (collectionSize == 0) && \result != null;
	  @*/
	public /*@pure helper@*/ boolean isEmpty() {
		return true;
	}
	
	/*@ public normal_behavior
	@ ensures isEmpty();
	@ assignable \nothing;
	@*/
	public void clear() {
		
	}
	
	/*@ public normal_behavior
	@ requires true;
	@ ensures (collectionSize <= Integer.MAX_VALUE && collectionSize >= 0) ==> \result == collectionSize;
	@ also public normal_behavior
	@ requires true;
	@ ensures (collectionSize > Integer.MAX_VALUE) ==> \result == Integer.MAX_VALUE;
	@ assignable \nothing;
	@*/
	public int /*@pure helper@*/ size() {
		return collectionSize;
	}
	
	/*@
	  @ public normal_behavior
	  @ requires this.elements != null;
	  @ ensures (this.elements != null & index >= 0 && index < this.collectionSize) ==> \result == this.elements[index];
	  @ also 
	  @ public normal_behavior
	  @ requires this.elements != null;
	  @ ensures (this.elements == null || index < 0 || index > this.collectionSize) ==> \result == 0;
	  @ assignable \nothing;
	  @*/
	public int /*@pure helper@*/ get(int index) {
		int result = 0;
		if (this.elements != null && index < this.elements.length && index < collectionSize && index >= 0)
			result = elements[index];
		else if (this.elements == null || index >= this.elements.length || index >= collectionSize || index < 0) {
			;
		}
		return result;
	}
}
