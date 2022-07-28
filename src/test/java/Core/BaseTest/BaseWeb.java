package Core.BaseTest;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.Status;

import Core.Helper.Report.Report;
import Core.Helper.Web.BaseClass;

public class BaseWeb extends BaseTest{
    @BeforeMethod
	@Parameters({"url"})
    public void beforeMethod(ITestResult result, String url) {
		try {
	        System.out.println("BasePage Before Test Method");
	        Report.setTest(result.getMethod().getMethodName());
			Constant.setValue("URL", url);
			BaseClass.initChromeBrowser();
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			Report.log(Status.FAIL, "INIT TEST", "Failed during init test", e.toString());
		}
    }
    @AfterMethod
    public void afterMethod(ITestResult result) {
        System.out.println("BasePage After Test Method");
        BaseClass.quitBrowser();
    }
}
