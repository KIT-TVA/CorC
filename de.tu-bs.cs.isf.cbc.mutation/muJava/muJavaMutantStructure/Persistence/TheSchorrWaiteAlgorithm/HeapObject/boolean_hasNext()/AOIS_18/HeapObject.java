// This is a mutant program.
// Author : ysma

public class HeapObject
{

// of at most two
    public final HeapObject[] children = new HeapObject[2];

/*@ invariant nextChild >= 0; @*/
    public int nextChild;

    public boolean visited;


    public HeapObject()
    {
        nextChild = 0;
        visited = false;
    }

/*@ public normal_behavior 
      @ requires pos>=0 && pos<children.length;
      @ ensures children[pos] == obj;
      @ assignable children[pos];
      @ also
      @ public exceptional_behavior
      @ requires pos < 0 || pos>=children.length;
      @ signals (IndexOutOfBoundsException);
      @*/
    public  void setChild( int pos, HeapObject obj )
    {
        if (pos < 0 || pos >= children.length) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        children[pos] = obj;
    }

/*@ public normal_behavior
      @ ensures this.visited == mark;
      @ assignable this.visited;
      @*/
    public  void setMark( boolean mark )
    {
        this.visited = mark;
    }

/*@ public normal_behavior
       @ ensures this.nextChild == \old(this.nextChild) + 1;
       @ assignable nextChild;
       @*/
    public  void incIndex()
    {
        this.nextChild++;
    }

/*@ public normal_behavior
      @ ensures \result == visited;
      @ assignable \nothing;
      @*/
    public  boolean isMarked()
    {
        return visited;
    }

/*@ public normal_behavior    
      @ ensures \result == (nextChild<children.length);
      @ assignable \nothing;
      @*/
    public  boolean hasNext()
    {
        return nextChild < --children.length;
    }

/*@ public normal_behavior
      @ ensures \result == nextChild;
      @ assignable \nothing;
      @*/
    public  int getIndex()
    {
        return nextChild;
    }

/*@ public normal_behavior 
      @ requires pos>=0 && pos<children.length;
      @ ensures \result == children[pos];
      @ assignable \nothing;
      @ also
      @ public exceptional_behavior
      @ requires pos < 0 || pos>=children.length;
      @ signals (IndexOutOfBoundsException);      
      @*/
    public  HeapObject getChild( int pos )
    {
        return children[pos];
    }

/*@ public normal_behavior
      @ ensures \result == children.length;
      @ assignable \nothing;
      @*/
    public  int getChildCount()
    {
        return children.length;
    }

}
