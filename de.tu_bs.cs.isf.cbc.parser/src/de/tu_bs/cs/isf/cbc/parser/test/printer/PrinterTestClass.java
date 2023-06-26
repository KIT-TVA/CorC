package de.tu_bs.cs.isf.cbc.parser.test.printer;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;

public class PrinterTestClass {
	

	@MethodReceiver(SL = "high", MDF = MDF.CAPSULE)
	public void testMethod() {
		//a[0] =2;
	}
	
	private class C  {
		public String a;
	}
}
