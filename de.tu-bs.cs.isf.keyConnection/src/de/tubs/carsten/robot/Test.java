package de.tubs.carsten.robot;

public class Test {
	
	/*@
	  @ normal_behavior
	  @ ensures \result = x + y;
	  @ assignable strictly_nothing;
	  @*/
	public static int add2(int x, int y) {
		return x + y;
	}
	
	/*@
	  @ normal_behavior
	  @ ensures \result == x + y;
	  @ assignable strictly_nothing;
	  @*/
		public static int add(int x, int y) {
		return x + y;
	}

}
