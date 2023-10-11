package de.tu_bs.cs.isf.cbc.mutation.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class FileProcessing {
	
	public static Map<String, String> pureMapping = new TreeMap<String, String>();
	public static Map<String, String> strictlyPureMapping = new TreeMap<String, String>();
	public static Map<String, String> specPubNonNullMapping = new TreeMap<String, String>();
	public static Map<String, String> nullableMapping = new TreeMap<String, String>();
	public static Map<String, String> pureHelperMapping = new TreeMap<String, String>();

	public static File preProcess(File f) {
		List<String> lines = new LinkedList<String>();
		List<String> procLines = new LinkedList<String>();
		List<String> tempLines = new LinkedList<String>();
		String line;
		int changesCounter = 0;

		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			fr.close();
			br.close();

			for (int i = 0; i < lines.size(); i++) {
				//Spec_Public Filtering
				if (lines.get(i).contains("/*@spec_public@*/")) {
					String temp = lines.get(i).trim();
					if (temp.startsWith("public")) {
						lines.set(i, lines.get(i).replace("/*@spec_public@*/", ""));
					} else if (temp.startsWith("protected")) {
						lines.set(i, lines.get(i).replace("/*@spec_public@*/", "").replace("protected", "public"));
					} else if (temp.startsWith("private")) {
						lines.set(i, lines.get(i).replace("/*@spec_public@*/", "").replace("private", "public"));
					} else {
						lines.set(i, "public" + lines.get(i).replace("/*@spec_public@*/", ""));
					}
				}
				
				if (lines.get(i).contains("/*@ spec_public @*/")) {
					String temp = lines.get(i).trim();
					if (temp.startsWith("public")) {
						lines.set(i, lines.get(i).replace("/*@ spec_public @*/", ""));
					} else if (temp.startsWith("protected")) {
						lines.set(i, lines.get(i).replace("/*@ spec_public @*/", "").replace("protected", "public"));
					} else if (temp.startsWith("private")) {
						lines.set(i, lines.get(i).replace("/*@ spec_public @*/", "").replace("private", "public"));
					} else {
						lines.set(i, "public" + lines.get(i).replace("/*@ spec_public @*/", ""));
					}
				}
				
				// Filtering pure, save original in a map with old to be replaced with contains in postProcessing
				//Assumption: Method Signature does not get changed through Mutation!
				if (lines.get(i).contains("/*@pure@*/")) {
					String newLine = lines.get(i).replace("/*@pure@*/", "");
					pureMapping.put(newLine.replaceAll("[ \t\b\f\r\n]", ""), lines.get(i));
					lines.set(i, newLine);
				}
				
				if (lines.get(i).contains("/*@ pure @*/")) {
					String newLine = lines.get(i).replace("/*@ pure @*/", "");
					pureMapping.put(newLine.replaceAll("[ \t\b\f\r\n]", ""), lines.get(i));
					lines.set(i, newLine);
				}
				
				// Filtering pure, save original in a map with old to be replaced with contains in postProcessing
				//Assumption: Method Signature does not get changed through Mutation!
				if (lines.get(i).contains("/*@pure helper@*/")) {
					String newLine = lines.get(i).replace("/*@pure helper@*/", "");
					pureHelperMapping.put(newLine.replaceAll("[ \t\b\f\r\n]", ""), lines.get(i));
					lines.set(i, newLine);
				}
				
				if (lines.get(i).contains("/*@ pure helper @*/")) {
					String newLine = lines.get(i).replace("/*@ pure helper @*/", "");
					pureHelperMapping.put(newLine.replaceAll("[ \t\b\f\r\n]", ""), lines.get(i));
					lines.set(i, newLine);
				}
				
				// Filtering strictly_pure, save original in a map with old to be replaced with contains in postProcessing
				//Assumption: Method Signature does not get changed through Mutation!
				if (lines.get(i).contains("/*@strictly_pure@*/")) {
					String newLine = lines.get(i).replace("/*@strictly_pure@*/", "");
					strictlyPureMapping.put(newLine.replaceAll("[ \t\b\f\r\n]", ""), lines.get(i));
					lines.set(i, newLine);
				}
				
				if (lines.get(i).contains("/*@ strictly_pure @*/")) {
					String newLine = lines.get(i).replace("/*@ strictly_pure @*/", "");
					strictlyPureMapping.put(newLine.replaceAll("[ \t\b\f\r\n]", ""), lines.get(i));
					lines.set(i, newLine);
				}
				
				// Filtering spec_public non_null, save original in a map with old to be replaced with contains in postProcessing
				//Assumption: Method Signature does not get changed through Mutation!
				if (lines.get(i).contains("/*@spec_public non_null@*/")) {
					String newLine = lines.get(i).replace("/*@spec_public non_null@*/", "");
					specPubNonNullMapping.put(newLine.replaceAll("[ \t\b\f\r\n]", ""), lines.get(i));
					lines.set(i, newLine);
				}
				
				if (lines.get(i).contains("/*@ spec_public non_null @*/")) {
					String newLine = lines.get(i).replace("/*@ spec_public non_null @*/", "");
					specPubNonNullMapping.put(newLine.replaceAll("[ \t\b\f\r\n]", ""), lines.get(i));
					lines.set(i, newLine);
				}
				
				// Filtering nullable, save original in a map with old to be replaced with contains in postProcessing
				//Assumption: Method Signature does not get changed through Mutation!
				if (lines.get(i).contains("/*@nullable@*/")) {
					String newLine = lines.get(i).replace("/*@nullable@*/", "");
					nullableMapping.put(newLine.replaceAll("[ \t\b\f\r\n]", ""), lines.get(i));
					lines.set(i, newLine);
				}
				
				if (lines.get(i).contains("/*@ nullable @*/")) {
					String newLine = lines.get(i).replace("/*@ nullable @*/", "");
					nullableMapping.put(newLine.replaceAll("[ \t\b\f\r\n]", ""), lines.get(i));
					lines.set(i, newLine);
				}
				
				// Filter Single Line Comments, only white spaces then //@
				if (lines.get(i).trim().startsWith("//@")) {
					//System.out.println("Reached");
					// Filter next lines that do only contain whitespace characters
					if (lines.get(i + 1).trim().equals("")) {
						//System.out.println(lines.get(i));
						procLines.add(lines.get(i));
						procLines.add("public static void abcdefghijklmnopqrstuvwxyz" + changesCounter + "() {}");
						changesCounter++;
					} else {
						procLines.add(lines.get(i));
					}
					//Filter Multi Line Comments, only white spaces then /*@
				} else if (lines.get(i).trim().startsWith("/*@")) {
					//System.err.println(lines.get(i));
					procLines.add(lines.get(i));
					//Loop over following lines to find comment end
					for (int j = i + 1; j < lines.size(); j++) {
						if (lines.get(i).contains("@*/"))
							break;
						tempLines.add(lines.get(j));
						//Detect Comment end through @*/
						if (lines.get(j).contains("@*/")) {
							//System.err.println(lines.get(j));
							// Filter next lines that do only contain whitespace characters
							if (lines.get(j + 1).trim().equals("")) {
								for(String x: tempLines) {
									System.out.println(x);
								}
								procLines.addAll(tempLines);
								procLines.add(
										"public static void abcdefghijklmnopqrstuvwxyz" + changesCounter + "() {}");
								changesCounter++;
								i += tempLines.size();
								break;
							} else {
								break;
							}
						}
					}
					tempLines.clear();
				} else {
					procLines.add(lines.get(i));
				}

			}

			FileWriter fw = new FileWriter(f);
			BufferedWriter out = new BufferedWriter(fw);
			for (String s : procLines)
				out.write(s + System.lineSeparator());
			out.flush();
			out.close();
			
			return f;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return f;
	}
	
	public static File postProcess(File f) {
		List<String> lines = new LinkedList<String>();
		List<String> procLines = new LinkedList<String>();
		List<String> tempLines = new LinkedList<String>();
		String line;
		int changesCounter = 0;

		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			fr.close();
			br.close();
			
			for(Entry<String, String> e :FileProcessing.pureMapping.entrySet()) {
				System.out.println(e.getKey().replaceAll("[ \t\b\f\r\n]", "") + " : " + e.getValue());
			}
			
			for (int i = 0; i < lines.size(); i++) {
				System.out.println(lines.get(i).replaceAll("[ \t\b\f\r\n]", "") + "{");
				// Filter Lines containing dummy method signature
				if (lines.get(i).contains("public static  void abcdefghijklmnopqrstuvwxyz")) {
//					System.err.println("here");
					if (!lines.get(i).contains("{")) {
						i += 3; //i = Method, i+1 = {, i+2 = }, i+3 = \n; Daher 3 Zeilen Skippen
					}
				//Replace marked pure, strictly_pure, spec_public non null, or nullable methods with the signature part containing pure comment
					//{ is added because MuJava makes a newLine
				} else if (pureMapping.containsKey(lines.get(i).replaceAll("[ \t\b\f\r\n]", "") + "{")) {
					procLines.add(pureMapping.get(lines.get(i).replaceAll("[ \t\b\f\r\n]", "") + "{").replace("{", ""));
				} else if (pureHelperMapping.containsKey(lines.get(i).replaceAll("[ \t\b\f\r\n]", "") + "{")) {
					procLines.add(pureHelperMapping.get(lines.get(i).replaceAll("[ \t\b\f\r\n]", "") + "{").replace("{", ""));
				} else if (strictlyPureMapping.containsKey(lines.get(i).replaceAll("[ \t\b\f\r\n]", "") + "{")) {
					procLines.add(strictlyPureMapping.get(lines.get(i).replaceAll("[ \t\b\f\r\n]", "") + "{").replace("{", ""));
				} else if (specPubNonNullMapping.containsKey(lines.get(i).replaceAll("[ \t\b\f\r\n]", "") + "{")) {
					procLines.add(specPubNonNullMapping.get(lines.get(i).replaceAll("[ \t\b\f\r\n]", "") + "{").replace("{", ""));
				} else if (nullableMapping.containsKey(lines.get(i).replaceAll("[ \t\b\f\r\n]", "") + "{")) {
					procLines.add(nullableMapping.get(lines.get(i).replaceAll("[ \t\b\f\r\n]", "") + "{").replace("{", ""));
				} else {
					procLines.add(lines.get(i));
				}
			}

			FileWriter fw = new FileWriter(f);
			BufferedWriter out = new BufferedWriter(fw);
			for (String s : procLines)
				out.write(s + System.lineSeparator());
			out.flush();
			out.close();
			
			return f;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return f;
	}
	
	public static void clearAllMappings() {
		pureMapping.clear();
		pureHelperMapping.clear();
		strictlyPureMapping.clear();
		specPubNonNullMapping.clear();
		nullableMapping.clear();
	}
}
