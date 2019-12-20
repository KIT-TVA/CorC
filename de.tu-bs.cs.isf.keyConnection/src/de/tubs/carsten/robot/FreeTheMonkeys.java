package de.tubs.carsten.robot;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import de.tubs.carsten.robot.monkey.RobotMonkey;
import de.tubs.carsten.robot.util.SimianResult;

public class FreeTheMonkeys {

	public static void main(String[] args) {
		RobotMonkey monkey = new RobotMonkey("test", true);
		String content = "public class Test {\n"
	    +"/*@\n"
	    +"@ normal_behavior\n"
	    +"@ ensures \\result == (x + y);\n"
	    +"@*/\n"
	    +"public static int add(int x, int y) {\n"
		+"return x + y;\n"
		+"}}";
		InputStream program = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
		try {
			SimianResult res =  monkey.test(program, "Test");
			System.out.println(res.isClosed());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
