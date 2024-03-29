package Core.BaseTest;

import java.io.File;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.reporter.configuration.Theme;

import Core.Helper.Report.Report;


public class BaseTest {
	@Parameters({ "reportName"})
	@BeforeSuite
    public void beforeSuite(@Optional("") String reportName) {
		reportName = reportName.equals("") ? Constant.REPORT_NAME : reportName;
		String name = reportName + "_" + Report.getDate();
		String filePath = System.getProperty("user.dir") + File.separator + "result" + File.separator + name + ".html";
		name = reportName + "_" + Report.getDate("dd-MM-yyyy_hh:mm:ss");
		Report.setReports(Report.getSparkReporter(filePath, Theme.STANDARD, name));
    }

    @AfterSuite
    public void afterSuite() {
    	Report.closeReports();
    }

    @BeforeMethod
    public void beforeMethod(ITestResult result) {
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        System.out.println("BasePage After Test Method");
    }
}
