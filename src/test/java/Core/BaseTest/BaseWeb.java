package Core.BaseTest;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import Core.Helper.Report.Report;
import Core.Helper.Web.BaseClass;

public class BaseWeb extends BaseTest{
	BaseClass base = new BaseClass();
    @BeforeMethod
    public void beforeMethod(ITestResult result) {
        System.out.println("BasePage Before Test Method");
        Report.setTest(result.getMethod().getMethodName());
		BaseClass.initChromeBrowser();
    }
    @AfterMethod
    public void afterMethod(ITestResult result) {
        System.out.println("BasePage After Test Method");
        BaseClass.quitBrowser();
    }
}
