package de.tu_bs.cs.isf.cbc.statistics;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;

import com.google.common.hash.Hashing;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;

public class FileNameManager {

	private int selectionCounter;
	private int abstractCounter;
	private int skipCounter;
	private int compositionCounter;
	private int returnCounter;
	private int repetitionCounter;
	private int strengthWeakCounter;

	public String getFileName(String problem, String location, AbstractStatement statement, String subProofName)  {
		EObject root = getRoot(statement);
		// TODO: delete old key files
		if (!location.contains("/features/")) { // TODO cleanUp diabled for SPLs, as up to date files are deleted in some configs
			cleanKeyFiles(location, root);
		}
		// if there exists a KeY file with same problem hash -> get the existing name
		File keyFile = null;
		if (problem != null) {
			keyFile = getAlreadyProvenKeyFile(problem, statement, location);
		}

		String statementKind = statement.eClass().getName();


		selectionCounter = 0;
		abstractCounter = 0;
		skipCounter = 0;
		compositionCounter = 0;
		returnCounter = 0;
		repetitionCounter = 0;
		strengthWeakCounter = 0;

		getKindNumber(root, statement);

		int counter;
		if (statementKind.equals("SelectionStatement")) {
			counter = selectionCounter;
		} else if (statementKind.equals("SkipStatement")) {
			counter = skipCounter;
		} else if (statementKind.equals("CompositionStatement")) {
			counter = compositionCounter;
		} else if (statementKind.equals("ReturnStatement")) {
			counter = returnCounter;
		} else if (statementKind.equals("SmallRepetitionStatement")) {
			counter = repetitionCounter;
			statementKind = "RepetitionStatement";
		} else if (statementKind.equals("StrengthWeakStatement")) {
			counter = strengthWeakCounter;
		} else {
			counter = abstractCounter;
			statementKind = "Statement";
		}

		String foundName = "";
		if (statement instanceof SmallRepetitionStatement) {
			foundName= "/" + statementKind + counter + subProofName;
		}
		else
			foundName = "/" + statementKind + counter;
		if (keyFile != null) {
			// rename existing files
			String existingName = keyFile.getName();
			existingName = existingName.substring(0, existingName.length() - 4);
//			return "/" + existingName;
			if (!("/" + existingName).equals(foundName)) {
				File file = new File(location + File.separator + foundName + ".key");
				if (file.exists()) {
//				in this case the already existing file will be overwritten
					System.out.println("not renaming");
					return foundName;
				}
				keyFile.renameTo(file);
				System.out.println("renaming");
			}
		}
		
		return foundName;
	}

	private void cleanKeyFiles(String location, EObject root) {
		
		//TODO this method only cleans redundant files -> also clean outdated files (if they do not hold an id existing within diagram)

		List<File> keyFilesInFolder = getKeYFilesFromFolder(location);
		List<File> redundantFiles = new LinkedList<File>();
		List<String> foundHashValues = new LinkedList<String>();
		for (int i = 0; keyFilesInFolder.size() > i; i++) {
			File outterFile = keyFilesInFolder.get(i);
			if (outterFile.getName().equals("helper.key")) {
				continue;
			}
			String outterHash = StatisticsDatabase.instance.getHashForKeyFile(outterFile);
			if (outterHash == null) {
				redundantFiles.add(outterFile);
				continue;
			}
			if (!outterHash.isEmpty()) {
				foundHashValues.add(outterHash);
				for (int j = i + 1; keyFilesInFolder.size() > j; j++) {
					File innerFile = keyFilesInFolder.get(j);
					String innerHash = StatisticsDatabase.instance.getHashForKeyFile(innerFile);
					if (innerHash == null) {
						if (!innerFile.getName().equals("helper.key"))
							redundantFiles.add(innerFile);
						continue;
					}
					if (outterHash.equals(innerHash))
						if (outterFile.lastModified() > innerFile.lastModified())
							redundantFiles.add(innerFile);
						else
							redundantFiles.add(outterFile);
				}
			}
		}

		for (File file : redundantFiles) {
			// TODO: Deleting key files leads to worse error tracing. This should be handled differently.
			//file.delete();
		}

	}
	
	public boolean isKeYFileWithHashProven (String hash, String proveFolderLocation) {
		List<File> keyFiles = getKeYFilesFromFolder(proveFolderLocation);
		
		for (File file : keyFiles) {
			String hashKeyFile = StatisticsDatabase.instance.getHashForKeyFile(file);
			if (hashKeyFile != null && hashKeyFile.equals(hash)) {
				if (StatisticsDatabase.instance.isKeyFileProven(file)) {
					return true;
				}
			}
		}
		
		return false;
	}

	private List<File> getKeYFilesFromFolder(String location) {
		List<File> keyFilesInFolder = new LinkedList<File>();

		try (Stream<Path> paths = Files.walk(Paths.get(location))) {
			paths.filter(Files::isRegularFile).forEach(guas -> keyFilesInFolder.add(guas.toFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return keyFilesInFolder;
	}

	private File getAlreadyProvenKeyFile(String problem, AbstractStatement statement, String location) {

		String hash = Hashing.sha256().hashString(problem, StandardCharsets.UTF_8).toString();

		List<File> keyFilesInFolder = getKeYFilesFromFolder(location);
		for (File file : keyFilesInFolder) {
			String existingHash = StatisticsDatabase.instance.getHashForKeyFile(file);
//			if (file.getName().equals("helper.key"))
//				continue;
//			String existingHash = getHashFromKeyFile(file);
			if (existingHash != null) {
				if (existingHash.equals(hash)) {
					return file;
				}
			}
		}

		return null;
	}

//	private String getHashFromKeyFile(File keyFile) {
//
//		Path keyFilePath = Path.of(keyFile.getAbsolutePath());
//		String fileString = "";
//		try {
//			fileString = Files.readString(keyFilePath);
//		} catch (IOException e) {
//			System.out.println("read proof file failed - FileNameManager Error: " + e.getMessage());
//			e.printStackTrace();
//			return "-1";
//		}
//
//		int startProblem = fileString.indexOf("\\javaSource");
//
//		// todo: check if file string is empty
//		String problem = fileString.substring(startProblem, fileString.length() - 1);
////		int firstOpeningBracket = problem.indexOf("{");
//		// todo: proof may not be the next part in every case. use a counter to
//		// recognize every new opening bracket and decrement if a closing bracket
//		// appears. then cut string if counter is 0 and a closing bracket appears
////		int endOfProblem = problem.indexOf("\\proof");
//		
//		// Error: it is not possible to get the exact problem string from a key file 
//		
//		char[] charactersInProblem = problem.toCharArray();
//		int indexEndOfProblem = 0;
//		char lastProblemChar = ' ';
//		for (char character : charactersInProblem) {
//			if (character == '{') 
//				indexEndOfProblem ++;
//			if (character == '}')
//				indexEndOfProblem --;
//			if (indexEndOfProblem == -1) 
//				lastProblemChar = character;
//		}
//		problem = problem.substring(0, problem.indexOf(lastProblemChar));
//
////		problem = problem.substring(0, problem.lastIndexOf("}"));
//		problem = problem.trim();
//
//		// TODO: throw exception if problem is null
//		String hash = Hashing.sha256().hashString(problem, StandardCharsets.UTF_8).toString();
//
//		return hash;
//	}

	private boolean getKindNumber(EObject root, AbstractStatement statement) {

		// depth-first search for selection statement
		for (int i = 0; i < root.eContents().size(); i++) {
			EObject content = root.eContents().get(i);
			if (content instanceof Condition) {
				continue;
			}
			if (content instanceof SelectionStatement)
				selectionCounter = selectionCounter + 1;
			else if (content instanceof SkipStatement)
				skipCounter++;
			else if (content instanceof CompositionStatement)
				compositionCounter++;
			else if (content instanceof ReturnStatement)
				returnCounter++;
			else if (content instanceof SmallRepetitionStatement)
				repetitionCounter++;
			else if (content instanceof StrengthWeakStatement)
				strengthWeakCounter++;
			else if (content instanceof AbstractStatement && ((AbstractStatement) content).getRefinement() == null)
				abstractCounter++;
			if (content.equals(statement))
				return true;

			// if there are no children: counter for abstract statement can be increased by
			// 1
			if (content.eContents().size() > 0)
				if (getKindNumber(content, statement))
					return true;
		}
		return false;
	}

	private EObject getRoot(AbstractStatement statement) {

		EObject currentObject = statement.eContainer();
		while (currentObject.eClass().getName() != "CbCFormula") {
			currentObject = currentObject.eContainer();
		}
		return currentObject;
	}

}
