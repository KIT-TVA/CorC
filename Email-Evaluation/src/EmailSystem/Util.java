package EmailSystem;

import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class Util {
	public static void prompt(@SecurityLevel("low") String msg) {
		System.out.println(msg);
	}

	public static void prompt(@SecurityLevel("low") int msg) {
		prompt(String.valueOf(msg));
	}	

	public static void prompt(@SecurityLevel("low") Email msg) {
		prompt(String.valueOf(msg));
	}

	public static void prompt(@SecurityLevel("low") Client client) {
		prompt(String.valueOf(client));
	}
}
