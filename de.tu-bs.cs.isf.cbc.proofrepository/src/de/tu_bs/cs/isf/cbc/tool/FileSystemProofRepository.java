package de.tu_bs.cs.isf.cbc.tool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * A implementation of a proof repository for testing purposes
 */
public final class FileSystemProofRepository implements IProofRepository {

	@Override
	public String getPartialProofForId(UUID uuid) {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(uuid.toString() + ".proof"))) {
			return bufferedReader.lines().collect(Collectors.joining());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean savePartialProofForId(UUID uuid, String proof) {
		PrintWriter out;
		try {
			out = new PrintWriter(uuid.toString() + ".proof");
			out.print(proof);
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}

}
