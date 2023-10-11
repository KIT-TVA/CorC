package src.mujava.op.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import openjava.mop.FileEnvironment;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CompilationUnit;
import openjava.ptree.ForStatement;
import openjava.ptree.ParseTreeException;
import openjava.ptree.StatementList;
import openjava.ptree.UnaryExpression;

public class PS extends MethodLevelMutator {
	public PS(FileEnvironment file_env, ClassDeclaration cdecl, CompilationUnit comp_unit) {
		super(file_env, comp_unit);
	}

	public String genNewContract(String[] lines, String change, int index) {
		String[] strs = Arrays.copyOf(lines, lines.length);
		strs[index] = change;
		return String.join(System.getProperty("line.separator"), strs);
	}

	public void visit(openjava.ptree.MethodDeclaration m) throws ParseTreeException {
		super.visit(m);
		// == -> >=,<=
		String original = m.getComment();

		if (original == null)
			return;

		String[] lines = original.split("\\n");
		int i = 0;
		for (String line : lines) {
			if (line.contains("ensures")) {

				{/////////// Strengthen >=
					String[] parts = line.split(">=");
					for (int j = 1; j < parts.length; ++j) {
						String prefix = "";
						for (int k = 0; k < j; ++k) {
							prefix += parts[k];
							if(k != j-1)
								prefix += ">=";
						}

						String postfix = "";
						for (int k = j; k < parts.length; ++k) {
							postfix += parts[k];
							if(k != parts.length-1)
								postfix += ">=";
						}
						
						// Create mutants
						String mutant1 = genNewContract(lines, prefix + ">" + postfix, i);
						String mutant2 = genNewContract(lines, prefix + "==" + postfix, i);

						m.setComment(mutant1);
						outputToFile(m);

						m.setComment(mutant2);
						outputToFile(m);
					}
				}
				
				{/////////// Strengthen >=
					String[] parts = line.replace("<==>", "#BIIMPL#").split("<=");
					for (int j = 1; j < parts.length; ++j) {
						String prefix = "";
						for (int k = 0; k < j; ++k) {
							prefix += parts[k];
							if(k != j-1)
								prefix += "<=";
						}

						String postfix = "";
						for (int k = j; k < parts.length; ++k) {
							postfix += parts[k];
							if(k != parts.length-1)
								postfix += "<=";
						}
						
						prefix = prefix.replace("#BIIMPL#", "<==>");
						postfix = postfix.replace("#BIIMPL#", "<==>");

						// Create mutants
						String mutant1 = genNewContract(lines, prefix + "<" + postfix, i);
						String mutant2 = genNewContract(lines, prefix + "==" + postfix, i);

						m.setComment(mutant1);
						outputToFile(m);

						m.setComment(mutant2);
						outputToFile(m);
					}
				}
				
				{/////////// Strengthen !=
					String[] parts = line.split("!=");
					for (int j = 1; j < parts.length; ++j) {
						String prefix = "";
						for (int k = 0; k < j; ++k) {
							prefix += parts[k];
							if(k != j-1)
								prefix += "!=";
						}

						String postfix = "";
						for (int k = j; k < parts.length; ++k) {
							postfix += parts[k];
							if(k != parts.length-1)
								postfix += "!=";
						}

						// Create mutants
						String mutant1 = genNewContract(lines, prefix + ">" + postfix, i);
						String mutant2 = genNewContract(lines, prefix + "<" + postfix, i);

						m.setComment(mutant1);
						outputToFile(m);

						m.setComment(mutant2);
						outputToFile(m);
					}
				}
				
				
				{/////////// Strengthen ||
					String[] parts = line.split("\\|\\|");
					for (int j = 1; j < parts.length; ++j) {
						String prefix = "";
						for (int k = 0; k < j; ++k) {
							prefix += parts[k];
							if(k != j-1)
								prefix += "||";
						}

						String postfix = "";
						for (int k = j; k < parts.length; ++k) {
							postfix += parts[k];
							if(k != parts.length-1)
								postfix += "||";
						}

						// Create mutants
						String mutant1 = genNewContract(lines, prefix + "&&" + postfix, i);

						m.setComment(mutant1);
						outputToFile(m);
					}
				}
				
				{/////////// Strengthen exists
					String[] parts = line.split("exists");
					for (int j = 1; j < parts.length; ++j) {
						String prefix = "";
						for (int k = 0; k < j; ++k) {
							prefix += parts[k];
							if(k != j-1)
								prefix += "exists";
						}

						String postfix = "";
						for (int k = j; k < parts.length; ++k) {
							postfix += parts[k];
							if(k != parts.length-1)
								postfix += "exists";
						}

						// Create mutants
						String mutant1 = genNewContract(lines, prefix + "forall" + postfix, i);

						m.setComment(mutant1);
						outputToFile(m);
					}
				}
				
				{/////////// Strengten P>Q ... replace biimplikation
					String[] parts = line.replaceAll(">=", "GEQ").replaceAll("==>", "IMPLIES").replaceAll("<==>", "BIIMP").replaceAll(" > ", ">").replaceAll(">", " > ").replaceAll("  ", " ").split(" ");
					
					for (int j = 0; j < parts.length; ++j) {
						String[] tmp = parts;
						if(tmp[j].equals(">")) {
							tmp[j+1] = tmp[j+1] + " + 1";
							if(tmp[j+1].contains(";")) {
								tmp[j+1] = tmp[j+1].replace(";", "") + ";";
							}
							// Create mutants
							
							
							String mutant1 = genNewContract(lines, String.join(" ", tmp).replaceAll("GEQ", ">=").replaceAll("IMPLIES","==>").replaceAll("BIIMP","<==>"), i);

							m.setComment(mutant1);
							outputToFile(m);
						}
					}
				}
				
				{/////////// Strengten P<Q 
					String[] parts = line.replaceAll("<=", "##").replaceAll(" < ", "<").replaceAll("<", " < ").replaceAll("  ", " ").split(" ");
					
					for (int j = 0; j < parts.length; ++j) {
						String[] tmp = parts;
						if(tmp[j].equals(">")) {
							tmp[j+1] = tmp[j+1] + " - 1";
							if(tmp[j+1].contains(";")) {
								tmp[j+1] = tmp[j+1].replace(";", "") + ";";
							}
							// Create mutants
							
							
							String mutant1 = genNewContract(lines, String.join(" ", tmp).replaceAll("##", "<="), i);

							m.setComment(mutant1);
							outputToFile(m);
						}
					}
				}
			
			}
			i++;
		}

	}

	public void visit(ForStatement p) throws ParseTreeException {
		// Do not consider conditions for "FOR STMT"
		StatementList stmts = p.getStatements();
		super.visit(stmts);
	}

	/**
	 * Write AODS mutants to files
	 * 
	 * @param original
	 */
	public void outputToFile(UnaryExpression original) {
		if (comp_unit == null)
			return;

		String f_name;
		num++;
		f_name = getSourceName("PS");
		String mutant_dir = getMuantID("PS");

		try {
			PrintWriter out = getPrintWriter(f_name);
			PS_Writer writer = new PS_Writer(mutant_dir, out);
			writer.setMethodSignature(currentMethodSignature);
			comp_unit.accept(writer);
			out.flush();
			out.close();
		} catch (IOException e) {
			System.err.println("fails to create " + f_name);
		} catch (ParseTreeException e) {
			System.err.println("errors during printing " + f_name);
			e.printStackTrace();
		}
	}

	public void outputToFile(openjava.ptree.MethodDeclaration mutant) {
		if (comp_unit == null)
			return;

		String f_name;
		num++;
		f_name = getSourceName("PS");
		String mutant_dir = getMuantID("PS");

		try {
			PrintWriter out = getPrintWriter(f_name);
			PS_Writer writer = new PS_Writer(mutant_dir, out);
			writer.setMethodSignature(currentMethodSignature);
			comp_unit.accept(writer);
			out.flush();
			out.close();
		} catch (IOException e) {
			System.err.println("fails to create " + f_name);
		} catch (ParseTreeException e) {
			System.err.println("errors during printing " + f_name);
			e.printStackTrace();
		}
	}

}
