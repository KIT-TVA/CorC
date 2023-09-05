package de.tu_bs.cs.isf.cbc.mutation.feature;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

import org.json.*;

import de.tu_bs.cs.isf.cbc.mutation.interfaces.IDataLoader;
import de.tu_bs.cs.isf.cbc.mutation.interfaces.IEvaluator;
import de.tu_bs.cs.isf.cbc.mutation.interfaces.IMutationOperators;
import de.tu_bs.cs.isf.cbc.mutation.interfaces.IMutationReductionTechnique;
import de.tu_bs.cs.isf.cbc.mutation.interfaces.IMutator;
import de.tu_bs.cs.isf.cbc.mutation.interfaces.IReport;

public class Framework {

	public IDataLoader loader = null;
	public IEvaluator evaluator = null;
	public IMutationReductionTechnique mRT = null;
	public IMutationOperators mOps = null;
	public IMutator mutator = null;
	public IReport report = null;
	
	public static long preProcTime;
	public static long postProcTime;
	
	public static String config = "";
	
//	public static void main(String args[]) {
//		System.out.println("works");
//		System.out.println(args[0]);
//		configLocation = args[0];
//		//loadConfig();
//		//loader.loadData("test");
//	}
	
	public void run(IDataLoader loader, IEvaluator evaluator, IMutationReductionTechnique mRT, IMutationOperators mOps, IMutator mutator, IReport report, String configLocation) {
		this.loader = loader;
		this.evaluator = evaluator;
		this.mRT = mRT;
		this.mOps = mOps;
		this.mutator = mutator;
		this.report = report;
		config = configLocation;
		
		String target = getTargetConfigString("Source");
		
		System.out.println(target);
		File[] files = loader.loadData(target);
		
		//System.out.println(files.length + " : " + files[0].getName());
		
		files = mutator.generateMutants(files);
		files = mRT.reduceMutants(files);
		
		this.report = evaluator.evaluate(files);
//		System.out.println(this.report.print());
	}
	
//	public static void loadClasses(String className, String path) {
//		System.out.println(path.substring(0, path.lastIndexOf("\\") + 1));
//		try {
//			URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] {
//					//new URL(
//							new File(path).toURI().toURL()
//					//)
//			});
//			System.out.println(urlClassLoader.getURLs()[0].getPath());
//			loader = (IDataLoader)urlClassLoader.loadClass(path).newInstance();
//			System.out.println("nicht tot!");
//			switch(className) {
//			case "DataLoader":
//				Class<?> clazz = Class.forName(path, true, urlClassLoader);
//				loader = (IDataLoader) clazz.newInstance();
//			}
//			
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
	public static String getTargetConfigString(String key) {
		try {
			Scanner scanner = new Scanner(new File(config));
			String json = "";
			while (scanner.hasNext()) {
				json += scanner.nextLine();
			}
			scanner.close();
			
			JSONObject obj = new JSONObject(json);
			return obj.getString(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static JSONArray getTargetConfigArray(String key) {
		try {
			Scanner scanner = new Scanner(new File(config));
			String json = "";
			while (scanner.hasNext()) {
				json += scanner.nextLine();
			}
			scanner.close();
			
			JSONObject obj = new JSONObject(json);
			return obj.getJSONArray(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
