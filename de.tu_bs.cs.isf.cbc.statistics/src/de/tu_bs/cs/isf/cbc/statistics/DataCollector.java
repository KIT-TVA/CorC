package de.tu_bs.cs.isf.cbc.statistics;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.swing.JOptionPane;

import com.google.common.hash.Hashing;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.uka.ilkd.key.proof.Proof;
import de.uka.ilkd.key.proof.Statistics;

public class DataCollector {

	// TODO: data is constantly saved -> implement something to clean database
	// either:
	// automatically (e.g. after a certain time) or
	// manually (e.g. by clicking clean statistics within the context menu)

	public void collectCorcStatistics(Proof proof, CbCFormula formula, AbstractStatement statement, String problem,
			String uri) {
		// TODO: if formula is null -> only a KeY file is proven: no diagram in direct
		// relation
		if (formula != null) {
			Statistics keyStats = proof.getStatistics();
			String hashValue = getHashFromProblem(problem);

			StatisticsEntry corcStatsEntry = statisticsFactory.eINSTANCE.createStatisticsEntry();
			StatisticsData corcStatsData = statisticsFactory.eINSTANCE.createStatisticsData();
			CorcKeyMapping mapping = statisticsFactory.eINSTANCE.createCorcKeyMapping();

			// set statistics to this entry
			corcStatsEntry.setData(corcStatsData);
			corcStatsEntry.setMapping(mapping);
			

//			String path = getWorkspaceRelatedPath(proof.getProofFile().getAbsolutePath());

			// set KeY file path mapping to this entry
			mapping.setKeyFilePath(proof.getProofFile().getAbsolutePath());
			// look up diagram name and set to mapping
			mapping.setCorcDiagramName(getFolderName(proof.getProofFile().getAbsolutePath().toString()));
			// set CorC elements mapping to this entry
			mapping.setCorcElementFormula(formula);
			mapping.setCorcElementStatement(statement);
			// for old models because id for abstract statements in cbcmodel is new
			if (statement.getId() == null || statement.getId().isEmpty()) {
				throw new RuntimeException(
						"Error: Statistics Data Collector - Please right click on the Diagram within the project explorer to add IDs.");
			} else {
				mapping.setCorcElementId(statement.getId());
			}
			mapping.setCorcDiagramPath(uri);
			// set KeY file hash value
			mapping.setKeyProofProblemHashValue(hashValue);

			// collecting statistic data for this entry TODO: more
			corcStatsData.setNumberOfNodes(keyStats.nodes);
			corcStatsData.setAutoModeTimeInMillis(keyStats.autoModeTimeInMillis);
			corcStatsData.setTimeInMillis(keyStats.timeInMillis);
			corcStatsData.setTimePerStepInMillis(keyStats.timePerStepInMillis);
			corcStatsData.setNumberOfBranches(keyStats.branches);
			corcStatsData.setTotalRuleApps(keyStats.totalRuleApps);
			Date date = new Date();
			corcStatsData.setTimestamp(date);
			corcStatsData.setIsProven(proof.closed());

			StatisticsDatabase.instance.saveToDatabase(corcStatsEntry);
		} else {
			System.out.println("Directly proven a KeY file. Statistics not collected!");
		}
	}

	private String getFolderName(String keyFilePath) {

		int indexLastSeperatorEntry = keyFilePath.lastIndexOf(File.separator);
		keyFilePath = keyFilePath.substring(0, indexLastSeperatorEntry);
		
		if (keyFilePath.contains(File.separator + "features" + File.separator)) {
			// remove config folder for variational projects
			indexLastSeperatorEntry = keyFilePath.lastIndexOf(File.separator);
			keyFilePath = keyFilePath.substring(0, indexLastSeperatorEntry);
		}
		// adding 6 because of prove string
		indexLastSeperatorEntry = keyFilePath.lastIndexOf(File.separator) + 6;
		String diagramFolder = keyFilePath.substring(indexLastSeperatorEntry, keyFilePath.length());
		
		return diagramFolder;
	}

//	private String getWorkspaceRelatedPath(String absolutePath) {
//
//		// D:\Uni\Bachelorarbeit\Bachelorarbeit_Git\CorC\BankAccountCorC\src\Account\provebankAccountUndoUpdate\SelectionStatement1.key
//		
//		IPath workspacePath = Platform.getLocation();
//		
//		IPath path = new Path(absolutePath);
//
////		path.
//		// this will give workspace related path and not project related :(
//		try {
//			File currentDirFile = new File(".");
//			String helper = currentDirFile.getAbsolutePath();
//			String currentDir = helper.substring(0, helper.length() - currentDirFile.getCanonicalPath().length());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return null;
//	}

	private String getHashFromProblem(String problem) {

//		Path keyFilePath = Path.of(proof.getProofFile().getAbsolutePath());
//		String fileString = "";
//		try {
//			fileString = Files.readString(keyFilePath);
//		} catch (IOException e) {
//			System.out.println("read proof file failed - DataCollector Error: " + e.getMessage());
//			e.printStackTrace();
//			return "-1";
//		}

//		int startProblem = fileString.indexOf("\\problem");
//
//		// TODO: check if file string is empty
//		String problem = fileString.substring(startProblem, fileString.length() - 1);
//		int firstOpeningBracket = problem.indexOf("{");
//		int endOfProblem = problem.indexOf("\\proof");
//		problem = problem.substring(firstOpeningBracket + 1, endOfProblem);
//
//		problem = problem.substring(0, problem.lastIndexOf("}"));
//		problem = problem.trim();

		// TODO: throw exception if problem is null
		String hash = Hashing.sha256().hashString(problem, StandardCharsets.UTF_8).toString();

		return hash;
	}
	
	public static boolean checkForId(AbstractStatement statement) {
		if (statement.getId() == null || statement.getId().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error: Statistical data collection failed. Please add Ids by right click on diagram in project explorer. Proof not executed.");
			return false;
		}
		return true;
	}
}
