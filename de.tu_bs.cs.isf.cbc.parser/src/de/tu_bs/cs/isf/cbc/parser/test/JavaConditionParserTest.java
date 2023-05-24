package de.tu_bs.cs.isf.cbc.parser.test;

import de.tu_bs.cs.isf.cbc.parser.JavaConditionParser;

public class JavaConditionParserTest {
	public static void main(String[] args) {
		JavaConditionParser.parseJavaCondition("test", "x > f.a || 5 < y", null, null, null);
	}
}
