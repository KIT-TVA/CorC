package de.tu_bs.cs.isf.cbc.parser.test;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;
import de.tu_bs.cs.isf.cbc.parser.javaparser.examples.ParserTest;

public class ParserTestClass extends ParserTest {

	@SecurityLevel("low")
	@MutationModifier(MDF.IMMUTABLE)
	private String testString = "bla", testString2 = "muh";

	@SecurityLevel("high")
	private String nichtBeachten;

	@SecurityLevel("high")
	@MutationModifier(MDF.CAPSULE)
	private Integer testInteger;

	@MethodReceiver(SL = "high", MDF = MDF.CAPSULE)
	public void voidTestMethod(	@SecurityLevel("low")  String testParam1,
							@SecurityLevel("high") @MutationModifier(MDF.CAPSULE) String testParam2) {
		return;
	}
	
	
	public void wrongTestMethod(@SecurityLevel("low") @MutationModifier(MDF.IMMUTABLE) String testParam1,
								@SecurityLevel("high") @MutationModifier(MDF.CAPSULE) String testParam2) {
		return;
	}
	
	@SecurityLevel("high")
	@MutationModifier(MDF.CAPSULE)
	static String staticMethod() {
		return "";
	}
	
	@SecurityLevel("high")
	@MutationModifier(MDF.CAPSULE)
	@MethodReceiver(SL = "high", MDF = MDF.CAPSULE)
	public String testMethod() {
		return "";
	}

	@SecurityLevel("high")
	@MutationModifier(MDF.CAPSULE)
	public void wrongParameterMethod(	@SecurityLevel("low") @MutationModifier(MDF.IMMUTABLE) String testParam1,
										@MutationModifier(MDF.CAPSULE) String testParam2) {
		return;
	}

	public class ParserTestInnerClass extends DateFormat implements Comparable<String> {

		@SecurityLevel("low")
		@MutationModifier(MDF.IMMUTABLE)
		private String testString = "bla", testString2 = "muh";
		
		
		@SecurityLevel("high")
		@MutationModifier(MDF.CAPSULE)
		public void innerTestMethod(	@SecurityLevel("low") @MutationModifier(MDF.IMMUTABLE) String testParam1,
								@SecurityLevel("high") @MutationModifier(MDF.CAPSULE) String testParam2) {
			return;
		}


		@Override
		public int compareTo(String o) {
			// TODO Auto-generated method stub
			return 0;
		}


		@Override
		public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Date parse(String source, ParsePosition pos) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
