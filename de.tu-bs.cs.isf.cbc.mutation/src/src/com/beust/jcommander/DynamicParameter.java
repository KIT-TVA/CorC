package src.com.beust.jcommander;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import src.com.beust.jcommander.validators.NoValidator;
import src.com.beust.jcommander.validators.NoValueValidator;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({FIELD})
public @interface DynamicParameter {
	/**
	 * An array of allowed command line parameters (e.g. "-D", "--define", etc...).
	 */
	String[] names() default {};

	/**
	 * Whether this option is required.
	 */
	boolean required() default false;

	/**
	 * A description of this option.
	 */
	String description() default "";

	/**
	 * The key used to find the string in the message bundle.
	 */
	String descriptionKey() default "";

	/**
	 * If true, this parameter won't appear in the usage().
	 */
	boolean hidden() default false;

	/**
	 * The validation class to use.
	 */
	Class<? extends IParameterValidator> validateWith() default NoValidator.class;

	/**
	 * The character(s) used to assign the values.
	 */
	String assignment() default "=";

	Class<? extends IValueValidator> validateValueWith() default NoValueValidator.class;
}
