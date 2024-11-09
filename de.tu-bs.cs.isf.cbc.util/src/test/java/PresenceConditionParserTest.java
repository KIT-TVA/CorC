package test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.tu_bs.cs.isf.cbc.util.presenceconditionparser.PresenceConditionNode;
import de.tu_bs.cs.isf.cbc.util.presenceconditionparser.PresenceConditionParser;
import de.tu_bs.cs.isf.cbc.util.presenceconditionparser.PresenceConditionParser.SelectionInfo;

public class PresenceConditionParserTest {
	
	@Test
	public void testAnd() {
		final String condition = "FeatureA & FeatureB";
		PresenceConditionParser parser = new PresenceConditionParser(condition, null);
		PresenceConditionNode node = parser.parse();
		
		assertEquals(node.print(), "FeatureA & FeatureB");
	}
	
	@Test
	public void testOr() {
		final String condition = "FeatureA | FeatureB";
		PresenceConditionParser parser = new PresenceConditionParser(condition, null);
		PresenceConditionNode node = parser.parse();
		
		assertEquals(node.print(), "FeatureA | FeatureB");
	}

	@Test
	public void testNot() {
		final String condition = "!FeatureA";
		PresenceConditionParser parser = new PresenceConditionParser(condition, null);
		PresenceConditionNode node = parser.parse();
		
		assertEquals(node.print(), "!FeatureA");
	}

	@Test
	public void testBracket() {
		final String condition = "(FeatureA)";
		PresenceConditionParser parser = new PresenceConditionParser(condition, null);
		PresenceConditionNode node = parser.parse();
		
		assertEquals(node.print(), "(FeatureA)");
	}

	@Test
	public void testImpl() {
		final String condition = "FeatureA -> FeatureB";
		PresenceConditionParser parser = new PresenceConditionParser(condition, null);
		PresenceConditionNode node = parser.parse();
		
		assertEquals(node.print(), "!FeatureA | FeatureB");
	}

	@Test
	public void testComplex() {
		final String condition = "(FeatureA | FeatureB) & !FeatureC";
		PresenceConditionParser parser = new PresenceConditionParser(condition, null);
		PresenceConditionNode node = parser.parse();
		
		assertEquals(node.print(), "(FeatureA | FeatureB) & !FeatureC");
	}

	@Test
	public void testEvaluation() {
		final String condition = "FeatureA | FeatureB";
		PresenceConditionParser parser = new PresenceConditionParser(condition, null);

		List<Set<SelectionInfo>> info = parser.parseCondition();
		System.out.println(info.size());
		info.forEach(list -> {
			System.out.print("[");
			list.forEach(elem -> {
				System.out.print(elem);
			});
			System.out.print("]");
		});

	}

	@Test
	public void testImplication() {
		final String condition = "FeatureA -> FeatureB";
		PresenceConditionParser parser = new PresenceConditionParser(condition, null);

		List<Set<SelectionInfo>> info = parser.parseCondition();
		System.out.println(info.size());
		info.forEach(list -> {
			System.out.print("[");
			list.forEach(elem -> {
				System.out.print(elem);
			});
			System.out.print("]");
		});

	}

}
