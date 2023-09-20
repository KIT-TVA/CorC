// This is a mutant program.
// Author : ysma

public class MutationTarget
{

/*@requires true;
	 @ensures \result == a + b; 
	 @*/
    public  int add( int a, int b )
    {
        return a-- + b;
    }

}
