package de.tu_bs.cs.isf.cbc.proofrepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.google.common.io.Files;

import de.tu_bs.cs.isf.cbc.util.Colors;
import de.tu_bs.cs.isf.cbc.util.Console;

/**
 * A implementation of a proof repository for testing purposes
 */
public final class FileSystemProofRepository implements IProofRepository {
	
	private final String repositoryPath;
	
	public FileSystemProofRepository(String repositoryPath) {
		this.repositoryPath = repositoryPath;
		File repo = new File(repositoryPath);
		if (!repo.exists()) {
			repo.mkdirs();
		}
	}

	@Override
	public void getPartialProofForId(List<String> featureNames, List<UUID> uuids, String location, String proofFileName) {
		File currentFolder = new File(repositoryPath);
		for (int i = 0; i < uuids.size(); i++) {
			File currentFile = new File(currentFolder.getPath() + "/" + uuids.get(i) + "(" + featureNames.get(i) + ")");
			if (!currentFile.exists()) {
				currentFile.mkdir();
			}
			currentFolder = currentFile;
		}
		Console.println("Loading: " + currentFolder.getPath());

		try {
			Files.copy(new File(currentFolder.getPath() + "/" + proofFileName), new File(location + proofFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean savePartialProofForId(List<String> featureNames, List<UUID> uuids, String proof) {
		File proofFolder = new File(proof);

		// Copy .proof file
		File currentFolder = new File(repositoryPath);

		for (int i = 0; i < uuids.size(); i++) {
			File currentFile = new File(currentFolder.getPath() + "/" + uuids.get(i) + "(" + featureNames.get(i) + ")");
			if (!currentFile.exists()) {
				currentFile.mkdir();
			}
			currentFolder = currentFile;
		}
		
		try {
			Files.copy(proofFolder, new File(currentFolder.getPath() + "/" + proofFolder.getName()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Console.println("Saving " + proof + " to :");
		Console.println("proofrepository/");
		for (int i = 0; i < uuids.size(); i++) {
			for (int j = 0; j < i+1; j++)
				Console.print("\t", Colors.BLACK);
			
			Console.println(uuids.get(i) + " (" + featureNames.get(i) + ")");
		}

		return false;
	}

}
