// This is a mutant program.
// Author : ysma

package Examples_from_Chapter_16;


public class PostInc
{

    public Examples_from_Chapter_16.PostInc rec;

    public int x;

    public int y;

/*@  public invariant 
          @   rec.x>=0 && rec.y>=0;
          @*/
/*@ public normal_behavior
      @ requires true;
      @ ensures rec.x == \old(rec.y) && rec.y == \old(rec.y)+1;
      @*/
    public  void postinc()
    {
        rec.x = rec.y--;
    }

}
