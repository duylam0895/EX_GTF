package Core.BaseTest;

import Core.Helper.Appium.DriverClass;
import Core.Helper.Report.Report;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.File;

public class BaseCucumber {
    @Given("Run Test with {string} os")
    public void loginTest(String os) {
        String reportName = Constant.REPORT_NAME + "_" + os;
        String name = reportName + "_" + Report.getDate();
        String filePath = System.getProperty("user.dir") + File.separator + "result" + File.separator + name + ".html";
        name = reportName + "_" + Report.getDate("dd-MM-yyyy_hh:mm:ss");
        Report.setReports(Report.getSparkReporter(filePath, Theme.STANDARD, name));
    }

    @AfterAll
    public void tearDown(){
        Report.closeReports();
    }
}
