package de.tu_bs.cs.isf.cbc.parser.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Target;

@Target({ FIELD, METHOD, PARAMETER, LOCAL_VARIABLE })
public @interface MutationModifier {
	MDF value();
}
