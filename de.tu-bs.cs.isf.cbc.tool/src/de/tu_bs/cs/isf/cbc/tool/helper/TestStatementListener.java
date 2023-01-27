package de.tu_bs.cs.isf.cbc.tool.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.testng.ITestContext;
import org.testng.ITestResult;

import de.tu_bs.cs.isf.cbc.tool.features.TestStatement;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

/**
 * Listener for checking the execution of statements.
 * @author Fynn
 */
public class TestStatementListener extends TestAndAssertionListener {
	private static final Color HIGHLIGHT_COLOR = new Color(new RGB(255, 85, 85));
	
	public TestStatementListener(final URI projectPath, final List<String> globalVars, final List<TestCaseData> inputDataTupels) {
		super(projectPath, globalVars, inputDataTupels);	
	}
	
	private int getErrorLineNr(final StackTraceElement[] stackTrace, final String className, final String methodName, final String methodCode) {
		  int errorLineNr = -1;
		  for (var trace : stackTrace) {
			  if (trace.getMethodName().equals(methodName)) {
				  errorLineNr = trace.getLineNumber();
				  break;
			  }
		  }
		  var classCode = getClassCode(className);
		  var lines = classCode.split("\\n");
		  var line = lines[errorLineNr-1].trim();
		  var methodLines = methodCode.split("\\n");
		  for (int i = 0; i < methodLines.length; i++) {
			  if (methodLines[i].trim().equals(line)) {
				  return i;
			  }
		  }
		  return -1;
	}
	
	/*
	private int getStatementLineNr(final String className, final String methodCode) {
		var lines = methodCode.split("\\n");
		int relativeLineNr = -1;
		int lineCounter = 0;
		for (var line : lines) {
			if (line.trim().equals(TestStatement.EXECUTED_TAG)) {
				relativeLineNr = lineCounter + 1;
			}
			lineCounter++;
		}
		var classCode = getClassCode(className);
		int offset = getLineCount(classCode) - getLineCount(methodCode);
		return relativeLineNr - offset + 2;
	}*/
	
	private int getLineCount(final String code) {
		return code.split("\\n").length;
	}
	
	protected String getLine(final String code, int lineNr) {
		var lines = code.split("\\n");
		return lines[lineNr];
	}
	
	private String getClassCode(String className) {
		var dir = new File(FileUtil.getProjectLocation(this.projectPath) + "\\tests");	
		if (!dir.exists()) {
			return "";
		}
		var javaFile = new File(FileUtil.getProjectLocation(this.projectPath) + "\\tests\\" + className + ".java");
		if (!javaFile.exists()){
			return "";
	    }
		try {
			var code = Files.readString(Paths.get(javaFile.getPath()));
			return code;
		} catch (IOException e) {}
		return "";
	}
	
	private void printLines(String code) {
		var lines = code.split("\n");
		for (var line : lines) {
			if (line.contains("-->")) {
				Console.println(line, HIGHLIGHT_COLOR);
			} else if (line.contains("<s>")) {
				line = line.substring(0, line.indexOf("<s>")) + line.substring(line.indexOf("<s>") + 3, line.length());
				Console.println(line, HIGHLIGHT_COLOR);
			}
			else {
				Console.println(line);
			}
		}
		Console.println();
	}
		
	/**
	* Invoked each time a test succeeds.
	*
	* @param result <code>ITestResult</code> containing information about the run test
	* @see ITestResult#SUCCESS
	*/
	@Override
	public void onTestSuccess(ITestResult result) {
		  if (result.getTestContext().getAttribute("skip") != null) {
			  Console.println(" > Generated data doesn't satisfy the precondition [" + result.getTestContext().getAttribute("skip") + "].", red);
			  Console.println();
			  result.setStatus(ITestResult.SKIP);
			  result.getTestContext().getSkippedTests().addResult(result);
			  result.getTestContext().removeAttribute("skip");
			  return;
		  }
	}
	
	/**
	* Invoked each time a test fails.
	*
	* @param result <code>ITestResult</code> containing information about the run test
	* @see ITestResult#FAILURE
	*/
	@Override
	public void onTestFailure(ITestResult result) {	 
		  if (result.getTestContext().getAttribute("skip") != null) {
			  Console.println(" > Generated data doesn't satisfy the precondition [" + result.getTestContext().getAttribute("skip") + "].", red);
			  Console.println();
			  result.setStatus(ITestResult.SKIP);
			  result.getTestContext().getSkippedTests().addResult(result);
			  result.getTestContext().removeAttribute("skip");
			  return;
		  }
		  var className =  result.getTestClass().getName();
		  var methodName = result.getName();
		  
		  Console.println(" > Test of statement failed: " + result.getThrowable().getMessage(), red);
		  Console.println(" > Test content:", red);
		  String methodCode = printMethodFromFile(methodName, FileUtil.getProjectLocation(projectPath) + 
				  "/tests/" + className.substring(className.lastIndexOf('.') + 1,
						  className.length()).split("Test")[0] + ".java");
		  methodCode = methodCode.substring(methodCode.indexOf("{") + 2, methodCode.lastIndexOf("}"));
		//  var statementLineNr = getStatementLineNr(className, methodCode);
		//  var statementLine = getLine(methodCode, statementLineNr);
		  var stackTrace = result.getThrowable().getStackTrace();
		  methodCode = removeHelperLines(methodCode);
		  methodCode = CodeHandler.removeTabs(methodCode).trim();
		  methodCode = CodeHandler.insertTabs(methodCode, 1);
		  int errorLineNr = getErrorLineNr(stackTrace, className, methodName, methodCode);
		  var lineToHighlight = getLine(methodCode, errorLineNr);
		  methodCode = methodCode.substring(0, methodCode.indexOf(lineToHighlight)) + "  -->" + methodCode.substring(methodCode.indexOf(lineToHighlight), methodCode.length());
		  printLines(methodCode);
		  //Console.println(methodCode + "\n");
	}
	
	/**
	 * Invoked after all the test methods belonging to the classes inside the &lt;test&gt; tag have run
	 * and all their Configuration methods have been called.
	 *
	 * @param context The test context
	 */
	@Override
	public void onFinish(ITestContext context) {
		  int failedTests = context.getFailedTests().size();
		  int skippedTests = context.getSkippedTests().size();
		
		  if (skippedTests == 0 && context.getAttribute("executed") == null) {
			  var methodName = context.getAllTestMethods()[0].getMethodName();
			  var className = context.getAllTestMethods()[0].getTestClass().getName();
			  Console.println(" > Couldn't reach statement.", red);
			  Console.println(" > Test content:", red);
			  String methodCode = printMethodFromFile(methodName, FileUtil.getProjectLocation(projectPath) + 
					  "/tests/" + className.substring(className.lastIndexOf('.') + 1,
							  className.length()).split("Test")[0] + ".java");
			  methodCode = methodCode.substring(methodCode.indexOf("{") + 2, methodCode.lastIndexOf("}"));
			  methodCode = removeHelperLines(methodCode);
			  methodCode = CodeHandler.removeTabs(methodCode);
			  methodCode = CodeHandler.insertTabs(methodCode, 1);
			  Console.println(methodCode + "\n");
			  try {
				  context.getFailedTests().addResult(context.getPassedTests().getAllResults().stream().findFirst().get());
			  } catch (NoSuchElementException e) {
				  e.printStackTrace();
			  }
			  return;
		  }
		  if (skippedTests == 1) {
			  Console.println(" > Test was skipped.", red);
		  } else if (failedTests == 0) {
			  Console.println(" > Test was successful.", green);
			  //Console.println("\t" + context.getAttribute("path"));
		  }
		  Console.println();
	}
}
