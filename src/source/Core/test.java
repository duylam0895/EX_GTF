package Core;

import java.io.File;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Core.Helper.Report.Report;

public class test {

	public static void main(String[] args) {
		String filePath = System.getProperty("user.dir") + File.separator + "result" + File.separator + "temp";
		Report.setReports(Report.getSparkReporter(filePath, Theme.STANDARD, "TEMP"));
		Report.setTest("TEST_1");
		Report.log(Status.PASS, "111111", filePath, filePath);
		Report.setTest("TEST_2");
		Report.log(Status.PASS, "222222", filePath, filePath);
		Report.closeReports();
	}

}
