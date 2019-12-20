package de.tubs.carsten.robot.monkey;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.key_project.monkey.product.ui.batch.MonKeYBatchMode;
import org.key_project.monkey.product.ui.batch.MonKeYBatchModeParameters;

import de.tubs.carsten.robot.util.FileHelper;
import de.tubs.carsten.robot.util.KeyResultHelper;
import de.tubs.carsten.robot.util.SimianResult;

public class RobotMonkey implements Simian {

	private static final String BASE_PATH_PREFIX = "temp" + File.separator + "RobotMonkey";
	private static final String RES_PATH_SUFFIX = File.separator + "keyresults" + File.separator;
	private static final String SRC_PATH_SUFFIX = File.separator + "src" + File.separator;
	
	private static final int MAX_RULES = (int) Math.pow(2, 30);

	private final String basePath;
	private final String resPath;
	private final String srcPath;
	private final MonKeYBatchMode monkey = new MonKeYBatchMode();
	private final String[] command;

	/**
	 * @param id
	 *            Aus der ID wird der Name f�r die von MonKey verwendenten
	 *            Dateien generiert. Er muss also eindeutig sein.
	 */
	public RobotMonkey(String id, boolean methodInlining) {
		basePath = BASE_PATH_PREFIX + id;
		resPath = basePath + RES_PATH_SUFFIX;
		srcPath = basePath + SRC_PATH_SUFFIX;
		if (methodInlining) {
			command = new String[] { MonKeYBatchModeParameters.PARAM_MAIN_WINDOW_OFF, MonKeYBatchModeParameters.PARAM_STOP_AT_UNCLOSABLE,
					MonKeYBatchModeParameters.PARAM_METHOD_TREATMENT_CONTRACT, MonKeYBatchModeParameters.PARAM_MAX_RULES, "" + MAX_RULES,
					MonKeYBatchModeParameters.PARAM_OUTPUT_PATH, resPath, srcPath };
		} else {
			command = new String[] { MonKeYBatchModeParameters.PARAM_MAIN_WINDOW_OFF, MonKeYBatchModeParameters.PARAM_STOP_AT_UNCLOSABLE,
					MonKeYBatchModeParameters.PARAM_MAX_RULES, "" + MAX_RULES, MonKeYBatchModeParameters.PARAM_OUTPUT_PATH, resPath, srcPath };
		}
	}

	@Override
	public SimianResult test(InputStream program, String name) throws IOException {
		FileHelper.deleteRecursevly(new File(basePath));
		File resPathFile = FileHelper.createDir(this.resPath);
		File srcPathFile = FileHelper.createDir(srcPath);


		File sourceFile = new File(srcPathFile, name + ".java");
		sourceFile.delete();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(sourceFile)); BufferedReader br = new BufferedReader(new InputStreamReader(program))) {
			String line;
			while ((line = br.readLine()) != null) {
				bw.write(line);
				bw.newLine();
			}
		} catch (IOException e) {
			throw e;
		}
		try {
			monkey.start(command);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		System.out.println("DONE!");
		
		File[] files = resPathFile.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.matches("MonKeY Batch Results.*");
			}
		});
		
		if(files.length != 1){
			throw new IllegalArgumentException("MonKey did not generate a test output. There must be something wrong with the input");
		}
		
		File batchResults = files[0];
		File[] rounds = batchResults.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.matches("Round \\d+");
			}
		});
		
		File round1 = rounds[0];
		File resultFile = new File(round1.getAbsolutePath()+File.separator+"src.csv");
		
		SimianResult sr = KeyResultHelper.readSimianResult(resultFile);
		
		File base = new File(basePath);
		FileHelper.deleteRecursevly(base);
		return sr;
	}

}
