package Core.BaseTest;

import Core.Helper.Appium.DeviceProperties;
import Core.Helper.Appium.DriverClass;
import Core.Helper.Report.Report;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.java.AfterAll;
import io.cucumber.java.en.Given;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;

public class BaseAppium {
//    @BeforeMethod
//    @Parameters({"deviceName", "platformName", "appPackage", "appActivity", "hostUrl", "hostPost", "hostPath"})
//    public void beforeMethod(ITestResult result, @Optional("") String deviceName,
//                             @Optional("") String platformName, @Optional("") String appPackage,
//                             @Optional("") String appActivity, @Optional("") String hostUrl,
//                             @Optional("") String hostPost, @Optional("") String hostPath) {
//        DeviceProperties properties = new DeviceProperties(deviceName, platformName, appPackage, appActivity, hostUrl, hostPost, hostPath);
//        Report.setTest(result.getMethod().getMethodName());
//        DriverClass.initDriver(properties);
//    }
//    @AfterMethod
//    public void afterMethod(ITestResult result) {
//        System.out.println("BasePage After Test Method");
//        DriverClass.quitBrowser();
//    }

    @Given("Run Test with deviceName: {string}, platformName: {string}, appPackage: {string}, appActivity: {string}")
    public void loginTest(String os) {
        String reportName = Constant.REPORT_NAME + "_" + os;
        String name = reportName + "_" + Report.getDate();
        String filePath = System.getProperty("user.dir") + File.separator + "result" + File.separator + name + ".html";
        name = reportName + "_" + Report.getDate("dd-MM-yyyy_hh:mm:ss");
        Report.setReports(Report.getSparkReporter(filePath, Theme.STANDARD, name));
    }

    @AfterAll
    public void tearDown(){
        DriverClass.quitBrowser();
        Report.closeReports();
    }
}
