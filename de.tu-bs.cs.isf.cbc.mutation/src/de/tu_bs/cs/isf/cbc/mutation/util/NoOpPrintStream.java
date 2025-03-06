package de.tu_bs.cs.isf.cbc.mutation.util;

import java.io.OutputStream;
import java.io.PrintStream;

public class NoOpPrintStream extends PrintStream {
	public NoOpPrintStream(OutputStream out) {
		super(out);
	}

	@Override
	public void println(String x) {
		// no op
	}

	@Override
	public void print(String s) {
		// no op
	}
}
