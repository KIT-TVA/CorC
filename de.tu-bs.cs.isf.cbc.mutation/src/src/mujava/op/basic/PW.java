package src.mujava.op.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import openjava.mop.FileEnvironment;
import openjava.ptree.BinaryExpression;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CompilationUnit;
import openjava.ptree.ForStatement;
import openjava.ptree.ParseTreeException;
import openjava.ptree.StatementList;
import openjava.ptree.UnaryExpression;

public class PW extends MethodLevelMutator {
	public PW(FileEnvironment file_env, ClassDeclaration cdecl, CompilationUnit comp_unit) {
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

		String[] lines = original.split(System.getProperty("line.separator"));
		int i = 0;
		for (String line : lines) {
			if (line.contains("requires")) {

				{/////////// Weaken ==
					//A.length > 0 && (\forall int i; i>=0 && i<A.length; A[i] , 0 || A[i] , 1 || A[i] , 2); [size=4]
					String[] parts = line.replace("==>", "#IMPL#").split("==");
					for (int j = 1; j < parts.length; ++j) {
						String prefix = "";
						for (int k = 0; k < j; ++k) {
							prefix += parts[k];
							if(k != j-1)
								prefix += "==";
						}
						
						//prefix
						//j=1:  A.length > 0 && (\forall int i; i>=0 && i<A.length; A[i] 

						String postfix = "";
						for (int k = j; k < parts.length; ++k) {//k=1..3
							postfix += parts[k];
							if(k != parts.length-1)
								postfix += "==";
						}
						
						//postfix: 
						//j=1:   0 || A[i]

						// Create mutants
						prefix = prefix.replace("#IMPL#", "==>");
						postfix = postfix.replace("#IMPL#", "==>");
						
						String mutant1 = genNewContract(lines, prefix + ">=" + postfix, i);
						String mutant2 = genNewContract(lines, prefix + "<=" + postfix, i);

						m.setComment(mutant1);
						outputToFile(m);

						m.setComment(mutant2);
						outputToFile(m);
					}
				}
				
				{/////////// Weaken >
					String[] parts = line.replaceAll(">=", "##").replaceAll("==>", "IMP").split(">");
					for (int j = 1; j < parts.length; ++j) {
						String prefix = "";
						for (int k = 0; k < j; ++k) {
							prefix += parts[k];
							if(k != j-1)
								prefix += ">";
						}

						String postfix = "";
						for (int k = j; k < parts.length; ++k) {
							postfix += parts[k];
							if(k != parts.length-1)
								postfix += ">";
						}

						// Create mutants
						String mutant1 = genNewContract(lines, prefix + ">=" + postfix, i).replaceAll("##", ">=").replaceAll("IMP", "==>");
						String mutant2 = genNewContract(lines, prefix + "!=" + postfix, i).replaceAll("##", ">=").replaceAll("IMP", "==>");

						m.setComment(mutant1);
						outputToFile(m);

						m.setComment(mutant2);
						outputToFile(m);
					}
				}
				
				{/////////// Weaken <
					String[] parts = line.replaceAll("<=", "##").replaceAll("<==>", "BIIMP").split("<");
					for (int j = 1; j < parts.length; ++j) {
						String prefix = "";
						for (int k = 0; k < j; ++k) {
							prefix += parts[k];
							if(k != j-1)
								prefix += "<";
						}

						String postfix = "";
						for (int k = j; k < parts.length; ++k) {
							postfix += parts[k];
							if(k != parts.length-1)
								postfix += "<";
						}

						// Create mutants
						String mutant1 = genNewContract(lines, prefix + "<=" + postfix, i).replaceAll("##", "<=").replaceAll("BIIMP", "<==>");
						String mutant2 = genNewContract(lines, prefix + "!=" + postfix, i).replaceAll("##", "<=").replaceAll("BIIMP", "<==>");

						m.setComment(mutant1);
						outputToFile(m);

						m.setComment(mutant2);
						outputToFile(m);
					}
				}
				/*
				 * requires A.length > 0 && (\forall int i; i>=0 && i<A.length; A[i] == 0 || A[i] == 1 || A[i] == 2);
				 */
				
				{/////////// Weaken &&
					String[] parts = line.split("&&");
					/*requires A.length > 0, (\forall int i; i>=0, i<A.length; A[i] == 0 || A[i] == 1 || A[i] == 2)*/
					for (int j = 1; j < parts.length; ++j) {
						String prefix = "";
						for (int k = 0; k < j; ++k) {
							prefix += parts[k];
							if(k != j-1)
								prefix += "&&";
						}
						
						//prefix: j=1: A.length > 0 
						//prefix: j=2: A.length > 0 && (\forall int i; i>=0

						String postfix = "";
						for (int k = j; k < parts.length; ++k) {
							postfix += parts[k];
							if(k != parts.length-1)
								postfix += "&&";
						}
						//postfix: j=1: (\forall int i; i>=0 && i<A.length; A[i] == 0 || A[i] == 1 || A[i] == 2);
						//postfix: j=2: i<A.length; A[i] == 0 || A[i] == 1 || A[i] == 2);

						// Create mutants
						String mutant1 = genNewContract(lines, prefix + "||" + postfix, i);

						m.setComment(mutant1);
						outputToFile(m);
					}
				}
				
				{/////////// Weaken forall
					String[] parts = line.split("forall");
					/*requires A.length > 0(\, int i; i>=0, i<A.length; A[i] == 0 || A[i] == 1 || A[i] == 2)*/
					for (int j = 1; j < parts.length; ++j) {
						String prefix = "";
						for (int k = 0; k < j; ++k) {
							prefix += parts[k];
							if(k != j-1)
								prefix += "forall";
						}

						String postfix = "";
						for (int k = j; k < parts.length; ++k) {
							postfix += parts[k];
							if(k != parts.length-1)
								postfix += "forall";
						}

						// Create mutants
						String mutant1 = genNewContract(lines, prefix + "exists" + postfix, i);

						m.setComment(mutant1);
						outputToFile(m);
					}
				}
				
				{/////////// Weaken P>=Q 
					String[] parts = line.replaceAll(" >= ", ">=").replaceAll(">=", " >= ").replaceAll("  ", " ").split(" ");
					
					for (int j = 0; j < parts.length; ++j) {
						String[] tmp = parts;
						if(tmp[j].equals(">=")) {
							tmp[j+1] = tmp[j+1] + " - 1";
							if(tmp[j+1].contains(";")) {
								tmp[j+1] = tmp[j+1].replace(";", "") + ";";
							}
							// Create mutants
							String mutant1 = genNewContract(lines, String.join(" ", tmp), i);

							m.setComment(mutant1);
							outputToFile(m);
						}
					}
				}
				
				{/////////// Weaken P<=Q 
					String[] parts = line.replaceAll("<==>", "BIIMP").replaceAll(" <= ", "<=").replaceAll("<=", " <= ").replaceAll("  ", " ").split(" ");
					
					for (int j = 0; j < parts.length; ++j) {
						String[] tmp = parts;
						if(tmp[j].equals("<=")) {
							tmp[j+1] = tmp[j+1] + " + 1";
							if(tmp[j+1].contains(";")) {
								tmp[j+1] = tmp[j+1].replace(";", "") + ";";
							}
							// Create mutants
							String mutant1 = genNewContract(lines, String.join(" ", tmp).replaceAll("BIIMP","<==>"), i);

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
		f_name = getSourceName("PW");
		String mutant_dir = getMuantID("PW");

		try {
			PrintWriter out = getPrintWriter(f_name);
			PW_Writer writer = new PW_Writer(mutant_dir, out);
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
		f_name = getSourceName("PW");
		String mutant_dir = getMuantID("PW");

		try {
			PrintWriter out = getPrintWriter(f_name);
			PW_Writer writer = new PW_Writer(mutant_dir, out);
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
