package de.tu_bs.cs.isf.cbc.mutation.feature;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.json.JSONArray;

import de.tu_bs.cs.isf.cbc.mutation.interfaces.IMutator;
import de.tu_bs.cs.isf.cbc.mutation.util.DirectoryCreator;

import org.apache.commons.io.FileUtils;
import mujava.AllMutantsGenerator;
import mujava.MutationSystem;
import mujava.OpenJavaException;
import mujava.makeMuJavaStructure;

public class JavaCodeMutator implements IMutator {

	@Override
	public File[] generateMutants(File[] files) {

		// Get Important Information from Config File
		// First MuJava_Home = where do you want the mutants?
		// Secong Mutation Operators, which do you want applied?
		String home = Framework.getTargetConfigString("MuJava_Home");

		JSONArray obj = Framework.getTargetConfigArray("Traditional");
		String[] traditional = new String[obj.length()];
		int i = 0;
		for (Object jo : obj) {
			traditional[i++] = (String) jo;
		}

		// Syso Block1
		System.out.println(home);
		for (String s : traditional) {
			System.out.println(s);
		}

		// Setup correct MuJava Structure
		MutationSystem.SYSTEM_HOME = home;
		makeMuJavaStructure.main(null);
		MutationSystem.MUTANT_PATH = MutationSystem.MUTANT_HOME;
		MutationSystem.TRADITIONAL_MUTANT_PATH = MutationSystem.MUTANT_PATH + "/Traditional";
		MutationSystem.CLASS_MUTANT_PATH = MutationSystem.MUTANT_PATH + "/Class";
		MutationSystem.EXCEPTION_MUTANT_PATH = MutationSystem.MUTANT_PATH + "/Exceptional";
		DirectoryCreator.makeDir(new File(MutationSystem.TRADITIONAL_MUTANT_PATH));
		DirectoryCreator.makeDir(new File(MutationSystem.CLASS_MUTANT_PATH));
		DirectoryCreator.makeDir(new File(MutationSystem.EXCEPTION_MUTANT_PATH));

		
		
		// TODO:Make it independent from OS!
		// Copy Files to MuJava Src
		String src = home + File.separator + "src";
		for (File f : files) {
			try {
				FileUtils.copyFile(f, new File(src + File.separator + f.getName()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//PreProcess to get standalone comments paired up with dummy methods
		//Do this after copy to src, should make sure that their is no interference later
		//Since their exists a copy in src and that copy is written back through KeYEvaluator it is fine!
		for (File f: files) {
			f = FileProcessing.preProcess(f);
		}
		
		// Copy Compiled Versions of Src to classes dir
		if (Framework.getTargetConfigString("bin") != null) {
			String classesDir = Framework.getTargetConfigString("bin");
			
			JavaDirectoryLoader classLoader = new JavaDirectoryLoader();
			classLoader.fileEnding = ".class";
			File[] classFiles = classLoader.loadData(classesDir);
			
			for(File f: classFiles) {
				try {
					FileUtils.copyFile(f, new File(home + File.separator + "classes" + File.separator + f.getName()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		// It may be needed to get class files for src in the class directory?

		try {
			MutationSystem.recordInheritanceRelation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Generate Mutants
		// get Filenames as String Array
		String[] filenames = new String[files.length];
		i = 0;
		for (File f : files) {
			System.out.println(f.getName());
			String[] test = f.getName().split("\\.");
			for (int j = 0; j < test.length; j++) {
				System.out.println(test[j]);
			}
			filenames[i++] = f.getName().split("\\.")[0];
		}

		// Set Class-Name for File to be mutated
		MutationSystem.CLASS_NAME = filenames[0];
		System.out.println(filenames[0]);

		AllMutantsGenerator allgen = new AllMutantsGenerator(files[0], null, traditional);
		try {
			allgen.makeMutants();
		} catch (OpenJavaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Get Files als Array and return
		JavaDirectoryLoader loader = new JavaDirectoryLoader();
		File[] MutantFiles = loader.loadData(MutationSystem.MUTANT_HOME);

		//Get Dummy Methods out of the files
		for (File f : MutantFiles) {
			System.out.println(f.getAbsolutePath());
			f = FileProcessing.postProcess(f);
		}
		for (File f: files) {
			f = FileProcessing.postProcess(f);
		}

		//Save MutantFiles Persistent in muJavaStructure
		String projectpath = Framework.getTargetConfigString("ProjectPath");
		String projectName = new File(projectpath).getName();
		String source = Framework.getTargetConfigString("Source");
		String sourceName = new File(source).getName();
		sourceName = sourceName.split("\\.")[0];
		DirectoryCreator.makeDir(new File(home + File.separator + projectName));
		DirectoryCreator.makeDir(new File(home + File.separator + projectName + File.separator + sourceName));
		//Basically: MuJavaStructure absolute path + project + class name
		String persistentDirStr = home + File.separator + projectName + File.separator + sourceName;
		System.out.println("New Dir: " + persistentDirStr);
		for (File f: MutantFiles) {
			String[] pathParts = f.getAbsolutePath().split(Pattern.quote(File.separator));
			String signature = pathParts[pathParts.length - 3];
			String muOpNr = pathParts[pathParts.length - 2];
			String name = pathParts[pathParts.length - 1];
			try {
				FileUtils.copyFile(f, new File(persistentDirStr + File.separator + signature + File.separator + muOpNr + File.separator + name));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return MutantFiles;
	}

}
