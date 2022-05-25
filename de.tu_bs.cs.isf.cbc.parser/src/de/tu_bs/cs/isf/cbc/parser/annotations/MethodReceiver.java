package de.tu_bs.cs.isf.cbc.parser.annotations;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;

@Target({ METHOD })
public @interface MethodReceiver {
	String SL();
	MDF MDF();
}
