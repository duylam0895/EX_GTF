package Core.BaseTest;

import java.lang.reflect.Field;

public class Constant {
	public static String WEBSITE_PATH;
	
	public final static int IMPLIXIT_WAIT = Integer.parseInt(System.getProperty("ImplixitWait"));
	public final static int EXPLIXIT_WAIT = Integer.parseInt(System.getProperty("ExplixitWait"));
	//public final static int CASE_FROM = Integer.parseInt(System.getProperty("CaseFrom"));
	public final static String REPORT_NAME = System.getProperty("ReportName");
	public final static String PROPERTIES_PATH = System.getProperty("PropertiesPath");
	static String URL = System.getProperty("Url");
	
	public static void setValue(String key, String value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Object obj = new Object();
		Field field = Constant.class.getDeclaredField(key);
		field.set(obj, value);
	}
	
	public static Object getValue(String key){
		Object obj = new Object();
		try {
			Field field = Constant.class.getDeclaredField(key);
			return field.get(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Field[] getListField() {
		return Constant.class.getDeclaredFields();
	}
}
