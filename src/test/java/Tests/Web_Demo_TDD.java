package Tests;

import java.util.Map;

import org.testng.annotations.Test;

import Core.BaseTest.BaseTestNG;
import Core.BaseTest.BaseWeb;
import Core.BaseTest.Constant;
import Core.Helper.Report.Report;
import Core.Helper.Web.DriverClass;
import Pages.SingleFormDemo;

public class Web_Demo_TDD extends BaseWeb{
  @Test(dataProvider = "testdatasupply",dataProviderClass = BaseTestNG.class)
  public void test(Map<String,String> data) {
	  
	  String A = data.get("Index");
	  String B = data.get("Value");
	  System.out.println(String.format("Get DATA index [%s] and value [%s]", A, B));
	  
	  
	  SingleFormDemo singleFormDemo = new SingleFormDemo(DriverClass.driver);
	  Report.isTrue("Browser", "Init browser", DriverClass.navigate(Constant.getValue("URL").toString()));
	  Report.isTrue("SINGLE FORM DEMO", "Navigate to Single form demo", DriverClass.navigate(singleFormDemo));
	  Report.print("SINGLE FORM DEMO", "Sendkeys to Message", DriverClass.sendKeys(singleFormDemo.messageInput, "ABC"));
	  Report.print("SINGLE FORM DEMO", "Click button", DriverClass.click(singleFormDemo.buttonShowInput));
	  Report.compareEqual("SINGLE FORM DEMO", "Verify Message", DriverClass.getText(singleFormDemo.messageOutput), "ABC");
	  Report.waitCondition("SINGLE FORM DEMO", "Wait for condition sastify", () -> DriverClass.click(singleFormDemo.buttonShowInput), 10, 5);
	  Report.waitCondition("SINGLE FORM DEMO", "Wait for condition sastify", () -> DriverClass.getText(singleFormDemo.messageOutput).equals("ABC1"), 10, 5);
  }
}
