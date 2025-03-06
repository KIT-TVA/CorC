package de.tu_bs.cs.isf.cbc.util;

import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;

/**
 * Class to open a console in the runtime instance
 * 
 * @author Tobias
 *
 */
public class Console {

	/**
	 * name of the console
	 */
	public static final String CONSOLE = "cbc_tool_output_console";

	/**
	 * finds and if necessary creates a console
	 * 
	 * @param name
	 *            the name of the console
	 * @return the found console
	 */
	private static MessageConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (MessageConsole) existing[i];
		// no console found, so create a new one
		MessageConsole myConsole = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[]{myConsole});
		return myConsole;
	}

	/**
	 * finds a console with a default name
	 * 
	 * @return the found console
	 */
	public static MessageConsole findConsole() {
		return findConsole(CONSOLE);
	}

	/**
	 * prints a line on the console
	 * 
	 * @param message
	 *            the line which should be printed
	 */
	public static void println(Object message) {
		if (message == null) {
			message = new String("null");
		} else {
			MessageConsoleStream out = findConsole().newMessageStream();
			out.println("" + message);
		}
	}

	/**
	 * prints an empty line on the console
	 */
	public static void println() {
		MessageConsoleStream out = findConsole().newMessageStream();
		out.println();
	}

	/**
	 * prints a colored line on the console
	 * 
	 * @param message
	 *            the line which should be printed
	 * @param color
	 *            the color the text should have
	 */
	public static void println(Object message, Color color) {
		if (message == null) {
			message = new String("null");
		} else {
			MessageConsoleStream out = findConsole().newMessageStream();
			out.setColor(color);
			out.println("" + message);
		}
	}

	/**
	 * prints a colored text on the console
	 * 
	 * @param message
	 *            the line which should be printed
	 * @param color
	 *            the color the text should have
	 */
	public static void print(Object message, Color color) {
		if (message == null) {
			message = new String("null");
		} else {
			MessageConsoleStream out = findConsole().newMessageStream();
			out.setColor(color);
			out.print("" + message);
		}
	}

	public static void printWarn(Object message) {
		try {
			if (!Settings.get().testWarningsEnabled()) {
				return;
			}
		} catch (SettingsException e) {
			e.printStackTrace();
			return;
		}
		if (message == null) {
			message = new String("null");
		} else {
			MessageConsoleStream out = findConsole().newMessageStream();
			out.setColor(Colors.WARNING);
			out.println("" + message);
		}
	}

	/**
	 * clears the content of the console
	 */
	public static void clear() {
		findConsole().clearConsole();
	}
}
