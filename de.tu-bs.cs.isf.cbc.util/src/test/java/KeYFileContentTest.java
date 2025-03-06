package test.java;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.Visibility;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.KeYFileContent;

class KeYFileContentTest {
	KeYFileContent content;

	Collection<Resource> createMockResources() {
		Collection<Resource> resources = new ArrayList<>();
		Resource resource = mock(Resource.class);
		resources.add(resource);

		Field field = CbcclassFactory.eINSTANCE.createField();
		field.setName("foo");
		field.setType("int");

		EList<EObject> classes = new BasicEList<>();
		ModelClass class_ = CbcclassFactory.eINSTANCE.createModelClass();
		class_.setName("TestClass");
		class_.getFields().add(field);

		classes.add(class_);

		when(resource.getContents()).thenReturn(classes);

		return resources;
	}

	@BeforeEach
	void before() {
		IProject mockedProject = mock(IProject.class);
		Collection<Resource> resources = createMockResources();

		try (MockedStatic<FileUtil> mocked = mockStatic(FileUtil.class)) {
			mocked.when(() -> FileUtil.getProjectFromProjectPath("location/")).thenReturn(mockedProject);
			mocked.when(() -> FileUtil.getCbCClasses(mockedProject)).thenReturn(resources);

			content = new KeYFileContent("location/");
			content.setSrcFolder("src");
		}
	}

	@Test
	void testEmptyKeyFile() {
		assertEqualsNormalized(
				"\\javaSource \"location/src\";" + "\\include \"helper.key\";" + "\\programVariables {"
						+ "    Heap heapAtPre;" + "}" + "\\problem {" + "    (wellFormed(heap)) -> {"
						+ "        heapAtPre := heap" + "    } " + "    \\<{}\\> ()" + "}",
				content.getKeYStatementContent());
	}

	@Test
	void testAddNullVariables() {
		assertNull(content.readVariables(null));
	}

	@Test
	void testAddVariables() {
		EList<JavaVariable> list = new BasicEList<>();
		JavaVariable var;

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("int foo");
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("static int bar");
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("non-null int foobar");
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("static non-null int baz");
		list.add(var);

		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Variables(), list);

		content.readVariables(variables);
		assertEqualsNormalized("\\javaSource \"location/src\";" + "\\include \"helper.key\";" + "\\programVariables {"
				+ "    int foo;" + "    int bar;" + "    int foobar;" + "    int baz;" + "    Heap heapAtPre;" + "}"
				+ "\\problem {" + "    (wellFormed(heap)) -> {" + "        heapAtPre := heap" + "    } \\<{}\\> ()"
				+ "}", content.getKeYStatementContent());
	}

	@Test
	void testAddVariablesArray() {
		EList<JavaVariable> list = new BasicEList<>();
		JavaVariable var;

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("int[] foo");
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("static int[] bar");
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("non-null int[] foobar");
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("static non-null int[] baz");
		list.add(var);

		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Variables(), list);

		content.readVariables(variables);
		assertEqualsNormalized("\\javaSource \"location/src\";" + "\\include \"helper.key\";" + "\\programVariables {"
				+ "    int[] foo;" + "    int[] bar;" + "    int[] foobar;" + "    int[] baz;" + "    Heap heapAtPre;"
				+ "}" + "\\problem {" + "    (" + "        foo.<created>=TRUE" + "        & bar.<created>=TRUE"
				+ "        & foobar.<created>=TRUE" + "        & baz.<created>=TRUE " + "        & wellFormed(heap)"
				+ "    ) -> {" + "        heapAtPre := heap" + "    } \\<{}\\> ()" + "}",
				content.getKeYStatementContent());
	}

	@Test
	void testAddVariablesParam() {
		EList<JavaVariable> list = new BasicEList<>();
		JavaVariable var;

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("Object foo");
		var.setKind(VariableKind.PARAM);
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("static Object bar");
		var.setKind(VariableKind.PARAM);
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("non-null Object foobar");
		var.setKind(VariableKind.PARAM);
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("static non-null Object baz");
		var.setKind(VariableKind.PARAM);
		list.add(var);

		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Variables(), list);

		content.readVariables(variables);
		assertEqualsNormalized("\\javaSource \"location/src\";" + "\\include \"helper.key\";" + "\\programVariables {"
				+ "    Object foo;" + "    Object bar;" + "    Object foobar;" + "    Object baz;"
				+ "    Heap heapAtPre;" + "}" + "\\problem {" + "    (" + "        Object::exactInstance(foobar)=TRUE"
				+ "        & foobar.<created>=TRUE" + "        & foobar!=null"
				+ "        & Object::exactInstance(baz)=TRUE" + "        & baz.<created>=TRUE" + "        & baz!=null"
				+ "        & wellFormed(heap)" + "    ) -> {" + "        heapAtPre := heap" + "    } \\<{}\\> ()" + "}",
				content.getKeYStatementContent());
	}

	@Test
	void testAddVariablesReturn() {
		EList<JavaVariable> list = new BasicEList<>();
		JavaVariable var;

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("int foo");
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("static int bar");
		list.add(var);

		JavaVariable returnVar = CbcmodelFactory.eINSTANCE.createJavaVariable();
		returnVar.setName("int here");
		returnVar.setKind(VariableKind.RETURN);
		list.add(returnVar);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("non-null int foobar");
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("static non-null int baz");
		list.add(var);

		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Variables(), list);

		// assertEquals(returnVar, content.readVariables(variables));
		content.readVariables(variables);
		assertEqualsNormalized("\\javaSource \"location/src\";" + "\\include \"helper.key\";" + "\\programVariables {"
				+ "    int foo;" + "    int bar;" + "    int here;" + "    int foobar;" + "    int baz;"
				+ "    Heap heapAtPre;" + "}" + "\\problem {" + "    (wellFormed(heap)) -> {"
				+ "        heapAtPre := heap" + "    } \\<{}\\> ()" + "}", content.getKeYStatementContent());
	}

	@Test
	void testAddFields() {
		EList<Field> list = new BasicEList<>();
		Field field;

		field = CbcclassFactory.eINSTANCE.createField();
		field.setName("foo");
		field.setType("int");
		field.setVisibility(Visibility.PUBLIC);
		// field.setIsStatic(false);
		list.add(field);

		field = CbcclassFactory.eINSTANCE.createField();
		field.setName("bar");
		field.setType("Object");
		field.setVisibility(Visibility.PUBLIC);
		// field.setIsStatic(false);
		list.add(field);

		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Fields(), list);

		content.readVariables(variables);
		assertEqualsNormalized("\\javaSource \"location/src\";" + "\\include \"helper.key\";" + "\\programVariables {"
				+ "    Heap heapAtPre;" + "}" + "\\problem {" + "    (" + "        Object::exactInstance(self.bar)=TRUE"
				+ "        & self.bar.<created>=TRUE" + "        & self.bar!=null" + "        & wellFormed(heap)"
				+ "    ) -> {" + "        heapAtPre := heap" + "    } \\<{}\\> ()" + "}",
				content.getKeYStatementContent());
	}

	/*
	 * @Test void testAddParams() { EList<Parameter> list = new BasicEList<>();
	 * Parameter param;
	 * 
	 * param = CbcclassFactory.eINSTANCE.createParameter(); param.setName("foo");
	 * param.setType("int"); list.add(param);
	 * 
	 * param = CbcclassFactory.eINSTANCE.createParameter(); param.setName("bar");
	 * param.setType("Object"); list.add(param);
	 * 
	 * JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
	 * variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Params(), list);
	 * 
	 * content.readVariables(variables);
	 * 
	 * assertEqualsNormalized( "\\javaSource \"location/src\";" +
	 * "\\include \"helper.key\";" + "\\programVariables {" + "    int foo;" +
	 * "    Object bar;" + "    Heap heapAtPre;" + "}" + "\\problem {" + "    (" +
	 * "        wellFormed(heap)" + "    ) -> {" + "        heapAtPre := heap" +
	 * "    } \\<{}\\> ()" + "}", content.getKeYStatementContent()); }
	 * 
	 * @Test void testAddParamsArray() { EList<Parameter> list = new BasicEList<>();
	 * Parameter param;
	 * 
	 * 
	 * param = CbcclassFactory.eINSTANCE.createParameter(); param.setName("foo");
	 * param.setType("int[]"); list.add(param);
	 * 
	 * param = CbcclassFactory.eINSTANCE.createParameter(); param.setName("bar");
	 * param.setType("Object[]"); list.add(param);
	 * 
	 * 
	 * JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
	 * variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Params(), list);
	 * 
	 * 
	 * content.readVariables(variables); assertEqualsNormalized(
	 * "\\javaSource \"location/src\";" + "\\include \"helper.key\";" +
	 * "\\programVariables {" + "    int[] foo;" + "    Object[] bar;" +
	 * "    Heap heapAtPre;" + "}" + "\\problem {" + "    (" +
	 * "        foo.<created> = TRUE" + "        & bar.<created> = TRUE" +
	 * "        & wellFormed(heap)" + "    ) -> {" + "        heapAtPre := heap" +
	 * "    } \\<{}\\> ()" + "}", content.getKeYStatementContent()); }
	 * 
	 * @Test void testAddParamsNonNull() { EList<Parameter> list = new
	 * BasicEList<>(); Parameter param;
	 * 
	 * 
	 * param = CbcclassFactory.eINSTANCE.createParameter(); param.setName("foo");
	 * param.setType("non-null int"); list.add(param);
	 * 
	 * param = CbcclassFactory.eINSTANCE.createParameter(); param.setName("bar");
	 * param.setType("non-null Object"); list.add(param);
	 * 
	 * 
	 * JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
	 * variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Params(), list);
	 * 
	 * 
	 * content.readVariables(variables); assertEqualsNormalized(
	 * "\\javaSource \"location/src\";" + "\\include \"helper.key\";" +
	 * "\\programVariables {" + "    int foo;" + "    Object bar;" +
	 * "    Heap heapAtPre;" + "}" + "\\problem {" + "    (" +
	 * "        Object::exactInstance(bar)=TRUE" + "        & bar.<created>=TRUE" +
	 * "        & bar!=null" + "        & wellFormed(heap)" + "    ) -> {" +
	 * "        heapAtPre := heap" + "    } \\<{}\\> ()" + "}",
	 * content.getKeYStatementContent()); }
	 * 
	 * @Test void testAddParamReturn() { EList<Parameter> list = new BasicEList<>();
	 * Parameter var;
	 * 
	 * var = CbcclassFactory.eINSTANCE.createParameter(); var.setName("foo");
	 * var.setType("int"); list.add(var);
	 * 
	 * var = CbcclassFactory.eINSTANCE.createParameter(); var.setName("bar");
	 * var.setType("int"); list.add(var);
	 * 
	 * Parameter returnParam = CbcclassFactory.eINSTANCE.createParameter();
	 * returnParam.setName("ret"); returnParam.setType("int");
	 * list.add(returnParam);
	 * 
	 * var = CbcclassFactory.eINSTANCE.createParameter(); var.setName("foobar");
	 * var.setType("int"); list.add(var);
	 * 
	 * var = CbcclassFactory.eINSTANCE.createParameter(); var.setName("baz");
	 * var.setType("int"); list.add(var);
	 * 
	 * 
	 * 
	 * JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
	 * variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Params(), list);
	 * 
	 * JavaVariable returnVar = content.readVariables(variables);
	 * 
	 * assertEquals(returnVar.getName(), returnParam.getType() + " " +
	 * returnParam.getName()); assertEqualsNormalized(
	 * "\\javaSource \"location/src\";" + "\\include \"helper.key\";" +
	 * "\\programVariables {" + "    int foo;" + "    int bar;" + "    int ret;" +
	 * "    int foobar;" + "    int baz;" + "    Heap heapAtPre;" + "}" +
	 * "\\problem {" + "    ( wellFormed(heap)) -> {" + "        heapAtPre := heap"
	 * + "    } \\<{}\\> ()" + "}", content.getKeYStatementContent()); }
	 */

	@Test
	void testAddGlobalConditions() {
		EList<Condition> list = new BasicEList<>();
		Condition cond;

		cond = CbcmodelFactory.eINSTANCE.createCondition();
		cond.setName("cond1");
		list.add(cond);

		cond = CbcmodelFactory.eINSTANCE.createCondition();
		cond.setName("cond2");
		list.add(cond);

		cond = CbcmodelFactory.eINSTANCE.createCondition();
		cond.setName("cond3");
		list.add(cond);

		GlobalConditions conditions = CbcmodelFactory.eINSTANCE.createGlobalConditions();
		conditions.eSet(CbcmodelPackage.eINSTANCE.getGlobalConditions_Conditions(), list);

		content.readGlobalConditions(conditions);
		assertEqualsNormalized("\\javaSource \"location/src\";" + "\\include \"helper.key\";" + "\\programVariables {"
				+ "    Heap heapAtPre;" + "}" + "\\problem {" + "    (" + "        cond1" + "        & cond2"
				+ "        & cond3" + "        & wellFormed(heap)" + "    ) -> {" + "        heapAtPre := heap"
				+ "    } \\<{}\\> ()" + "}", content.getKeYStatementContent());
	}

	@Test
	void testAddUnmofiableVars() {
		EList<JavaVariable> list = new BasicEList<>();
		JavaVariable var;

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("int foo");
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("static int bar");
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("non-null int foobar");
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("static non-null int baz");
		list.add(var);

		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Variables(), list);

		content.readVariables(variables);
		content.addUnmodifiableVars(list.stream().map(JavaVariable::getName).toList());
		assertEqualsNormalized(
				"\\javaSource \"location/src\";" + "\\include \"helper.key\";" + "\\programVariables {" + "    int foo;"
						+ "    int bar;" + "    int foobar;" + "    int baz;" + "    int foo_old;" + "    int bar_old;"
						+ "    int foobar_old;" + "    int baz_old;" + "    Heap heapAtPre;" + "}" + "\\problem {"
						+ "    (wellFormed(heap)) -> {" + "        heapAtPre := heap" + "        || foo_old := foo"
						+ "        || bar_old := bar" + "        || foobar_old := foobar" + "        || baz_old := baz"
						+ "    } \\<{}\\> (" + "        foo = foo_old" + "        & bar = bar_old"
						+ "        & foobar = foobar_old" + "        & baz = baz_old" + "    )" + "}",
				content.getKeYStatementContent());
	}

	@Test
	void testOld() {
		EList<JavaVariable> list = new BasicEList<>();
		JavaVariable var;

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("int foo");
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("static int bar");
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("non-null int foobar");
		list.add(var);

		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setName("static non-null int baz");
		list.add(var);

		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Variables(), list);

		content.readVariables(variables);

		EList<Condition> condList = new BasicEList<>();
		Condition cond;

		cond = CbcmodelFactory.eINSTANCE.createCondition();
		cond.setName("cond2");
		condList.add(cond);

		cond = CbcmodelFactory.eINSTANCE.createCondition();
		cond.setName("cond3");
		condList.add(cond);

		GlobalConditions conditions = CbcmodelFactory.eINSTANCE.createGlobalConditions();
		conditions.eSet(CbcmodelPackage.eINSTANCE.getGlobalConditions_Conditions(), condList);

		CbCFormula formula = mock(CbCFormula.class);
		when(formula.getClassName()).thenReturn("TestClass");
		content.addSelf(formula);

		cond = CbcmodelFactory.eINSTANCE.createCondition();
		cond.setName("\\old(foo) == foo - 1");

		content.addPostCondition(cond);

		content.readGlobalConditions(conditions);

		try (MockedStatic<Console> mocked = mockStatic(Console.class)) {
			assertEqualsNormalized("\\javaSource \"location/src\";" + "\\include \"helper.key\";"
					+ "\\programVariables {" + "    int foo;" + "    int bar;" + "    int foobar;" + "    int baz;"
					+ "    int foo1_oldVal;" + "    TestClass self;" + "    Heap heapAtPre;" + "}" + "\\problem {"
					+ "    (" + "        cond2" + "        &cond3"
					+ "        &self.<created>=TRUE&TestClass::exactInstance(self)=TRUE&!self=null&self.<inv>&wellFormed(heap)"
					+ "    ) -> {" + "        heapAtPre := heap||foo1_oldVal := foo}"
					+ "        \\<{}\\>(foo1_oldVal == foo - 1)" + "}", content.getKeYStatementContent());
		}
	}

	static void assertEqualsNormalized(String expected, String actual) {
		// KeYFileContent is really inconsistent with its spacing, so we normalize it.
		String expectedNormalized = expected.replaceAll("( |\n)+", " ").replaceAll("([^\\w]) ", "$1")
				.replaceAll(" ([^\\w])", "$1");
		String actualNormalized = actual.replaceAll("( |\n)+", " ").replaceAll("([^\\w]) ", "$1")
				.replaceAll(" ([^\\w])", "$1");

		assertEquals(expectedNormalized, actualNormalized);
	}

}