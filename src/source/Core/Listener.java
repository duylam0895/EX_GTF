package Core;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

public class Listener implements ITestListener {
	@Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Passed Test");
        Report.getTest().log(Status.PASS, result.getMethod().getMethodName());
    }
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Failed Test");
        Report.getTest().log(Status.FAIL, result.getMethod().getMethodName());
    }
}
