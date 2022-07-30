package Core.Helper.Report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Report {
	private static String timeFormat = "dd-MM-yyyy_hh-mm-ss";
    
	private static ExtentReports reports;
	private static ExtentTest test;
	
	public static ExtentSparkReporter getSparkReporter(String filePath, Theme theme, String name) {
		ExtentSparkReporter result = new ExtentSparkReporter(filePath);
		result.config().setTheme(theme);
		result.config().setReportName(name);
		return result;
	}
	
	public static void setReports(ExtentSparkReporter spark) {
		reports = new ExtentReports();
		reports.attachReporter(spark);
	}
	
	public static ExtentReports getReports() {
		return reports;
	}
	
	public static void closeReports() {
		reports.flush();
	}
	
	public static void setTest(String name) {
		test = reports.createTest(name);
	}
	
	public static ExtentTest getTest() {
		return test;
	}
	
	public static void updateDateFormat(String format) {
		timeFormat = format;
	}
	
	public static String getDate(String timeFormat) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(timeFormat);  
		LocalDateTime now = LocalDateTime.now();  
		String time = format.format(now);
		return time;
	}
	
	public static String getDate() {
		return getDate(timeFormat);
	}
	
	/**
	 * Print log
	 * @param title
	 * @param description
	 * @param message
	 */
	public static void log(Status status, String title, String description, String message) {
		String time = getDate("dd-MM-yyyy_hh:mm:ss");
		String template = "[" + time + "]-[%s]-[%s]-[%s]-%s";
		String print = String.format(template, status.toString(), title, description, message);
		System.out.println(print);
		switch(status) {
		case FAIL:
			test.fail(print);
			break;
		case PASS:
			test.pass(print);
			break;
		case WARNING:
			test.warning(print);
			break;
		default:
			test.info(print);
			break;
		}
	}
	

	/**
	 * Print info Log
	 * @param title
	 * @param description
	 * @param isSuccess
	 */
	public static void print(String title, String description, boolean isSuccess) {
		log(Status.PASS, title, description, String.format("Action %s", isSuccess ? "Successfull" : "Unsuccessfull"));
	}
	
	/**
	 * Print info Log
	 * @param title
	 * @param description
	 * @param handle
	 */
	public static void print(String title, String description, ReportHandle handle) {
		log(Status.PASS, title, description, String.format("Action %s", handle.isTrue ? "Successfull" : ("Unsuccessfull " + (handle.exceptionThrown == null ? "" : handle.exceptionThrown.getMessage()))));
	}
	
	/**
	 * Print log base on isTrue param
	 * @param title
	 * @param description
	 * @param isTrue
	 */
	public static void isTrue(String title, String description, boolean isTrue) {
		if(isTrue) {
			log(Status.PASS, title, description, String.format("Action Successfull"));
		}else {
			log(Status.FAIL, title, description, String.format("Action Unsuccessfull"));
		}
	}

	/**
	 * Print log base on handle param
	 * @param title
	 * @param description
	 * @param handle
	 */
	public static void isTrue(String title, String description, ReportHandle handle) {
		if(handle.isTrue) {
			log(Status.PASS, title, description, String.format(handle.message + " Action Successfull "));
		}else {
			log(Status.FAIL, title, description, String.format(handle.message + " Action Unsuccessfull " + (handle.exceptionThrown == null ? "" : handle.exceptionThrown.getMessage())));
		}
	}
	
	/**
	 * Print log return a compare Equal result
	 * @param title
	 * @param description
	 * @param actValue
	 * @param expValue
	 */
	public static void compareEqual(String title, String description, String actValue, String expValue) {
		actValue = actValue != null ? actValue : "";
		expValue = expValue != null ? expValue : "";
		
		boolean isTrue = actValue.contentEquals(expValue);
		
		if(isTrue) {
			String message = String.format(" Actual [%s] and Expected [%s] are Equal", actValue, expValue);
			log(Status.PASS, title, description, message);
		}else {
			String message = String.format(" Actual [%s] and Expected [%s] are not Equal", actValue, expValue);
			log(Status.FAIL, title, description, message);
		}
	}
	
	/**
	 * Print log return a compare Contains result
	 * @param title
	 * @param description
	 * @param actValue
	 * @param expValue
	 */
	public static void compareContains(String title, String description, String actValue, String expValue) {
		actValue = actValue != null ? actValue : "";
		expValue = expValue != null ? expValue : "";
		
		boolean isTrue = actValue.contains(expValue);
		
		if(isTrue) {
			String message = String.format("[%s] is Contains [%s]", actValue, expValue);
			log(Status.PASS, title, description, message);
		}else {
			String message = String.format("[%s] is not Contains [%s]", actValue, expValue);
			log(Status.FAIL, title, description, message);
		}
	}
	
	/**
	 * Print log until condition sastify
	 * @param title
	 * @param description
	 * @param method
	 */
	public static void waitCondition(String title, String description, ReportHandleInterface method, int durationSeconds, int pollingTimeSeconds) {
		int duration = durationSeconds;
		ReportHandle handle = new ReportHandle();
		String message = String.format(" Time out after %s seconds", duration);
		while(duration >= 0) {
			handle = method.operate();
			if(handle.isTrue) {
				break;
			}
			try {
				Thread.sleep(pollingTimeSeconds);
			}catch(InterruptedException e) {
				handle.isTrue = false;
				handle.exceptionThrown = e;
			}
			duration = duration - pollingTimeSeconds;
		}
		
		if(handle.isTrue) {
			log(Status.PASS, title, description, String.format(handle.message + " Action Successfull "));
		}else {
			log(Status.FAIL, title, description, String.format(handle.message + message + (handle.exceptionThrown == null ? "" : handle.exceptionThrown.getMessage())));
		}
	}
	
	/**
	 * Print log until condition sastify
	 * @param title
	 * @param description
	 * @param method
	 */
	public static void waitCondition(String title, String description, BooleanHandleInterface method, int durationSeconds, int pollingTimeSeconds) {
		int duration = durationSeconds;
		boolean handle = false;
		pollingTimeSeconds = pollingTimeSeconds*1000;
		String message = String.format(" Time out after %s seconds", duration);
		while(duration >= 0) {
			handle = method.operate();
			if(handle) {
				break;
			}
			try {
				Thread.sleep(pollingTimeSeconds);
			}catch(InterruptedException e) {
				handle = false;
			}
			duration = duration - pollingTimeSeconds;
		}
		
		if(handle) {
			log(Status.PASS, title, description, " Action Successfull ");
		}else {
			log(Status.FAIL, title, description, message);
		}
	}
}
