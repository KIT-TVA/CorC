package helper;

public class ModelClassHelper {
	
	private static Object obj = new Object();
	private static int index;

	
	public static Object getObject() {
		return obj;
	}
	
	public static void setObject(Object obj) {
		ModelClassHelper.obj = obj;
	}
	
	public static int getIndex() {
		return index;
	}
	
	public static void setIndex(int index) {
		ModelClassHelper.index = index;
	}
}
