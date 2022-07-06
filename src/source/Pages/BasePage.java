package Pages;

import java.io.File;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.reporter.configuration.Theme;

import Core.Helper.Report.Report;
import Core.Helper.Web.BaseTest;



@Listeners()
public class BasePage extends BaseTest{
	@Parameters({ "reportName"})
	@BeforeSuite
    public void beforeSuite(String reportName) {
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
        System.out.println("BasePage Before Test Method");
        Report.setTest(result.getMethod().getMethodName());
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        System.out.println("BasePage After Test Method");
        if(driver !=null) {
            driver.close();
            driver.quit();
        }
    }
}
