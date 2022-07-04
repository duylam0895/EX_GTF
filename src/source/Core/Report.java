package Core;

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
	 * Print Passed to log
	 * @param title
	 * @param description
	 * @param message
	 */
	public static void logPass(String title, String description) {
		log(Status.PASS, title, description, "");
	}
	
	/**
	 * Print Failed to log
	 * @param title
	 * @param description
	 * @param message
	 */
	public static void logFailed(String title, String description, String message) {
		log(Status.FAIL, title, description, message);
		}
	
	/**
	 * Print Info to log
	 * @param title
	 * @param description
	 * @param message
	 */
	public static void logInfo(String title, String description, String message) {
		log(Status.INFO, title, description, message);
	}
	
	/**
	 * Print log
	 * @param title
	 * @param description
	 * @param message
	 */
	public static void log(Status status, String title, String description, String message) {
		String time = getDate("dd-MM-yyyy_hh:mm:ss");
		String template = "[" + time + "]-[%s]-[%s]-[%s]-[%s]";
		String print = String.format(template, status.toString(), title, description, message);
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
}
