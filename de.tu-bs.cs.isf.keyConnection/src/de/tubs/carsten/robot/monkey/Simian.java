package de.tubs.carsten.robot.monkey;

import java.io.IOException;
import java.io.InputStream;

import de.tubs.carsten.robot.util.SimianResult;

public interface Simian {
	
	SimianResult test(InputStream program, String name) throws IOException;

}
