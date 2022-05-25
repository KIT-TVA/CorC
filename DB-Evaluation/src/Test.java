import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class Test {

	static void interferringSecurityLevels() {
		@SecurityLevel("low") GUI gui = new GUI();
		gui.databaseOne.setEntry("person", "hans", -1);
		gui.databaseTwo.setEntry("person", "peter", -1);
		@SecurityLevel("highOne") String name = gui.databaseOne.getEntry("person", 0);
		gui.databaseTwo.setEntry("person", name, 0); //not typable as name is highOne and databaseTwo is highTwo
													 //The entries of both databases can not interfere
		gui.printEntry(declassify(name));
	}

	private static @SecurityLevel("low") String declassify(@SecurityLevel("high") String value) {
		return value;
	}
}
