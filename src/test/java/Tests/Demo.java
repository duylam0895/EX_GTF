package Tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import Core.BaseTest.BaseWeb;
import Core.BaseTest.Constant;
import Core.Helper.Report.Report;
import Core.Helper.Web.BaseClass;
import Pages.SingleFormDemo;

public class Demo extends BaseWeb{
  @Test
  public void test() {
	  SingleFormDemo singleFormDemo = new SingleFormDemo();
	  Report.isTrue("Browser", "Init browser", BaseClass.navigate(Constant.getValue("URL").toString()));
	  Report.isTrue("SINGLE FORM DEMO", "Navigate to Single form demo", BaseClass.navigate(singleFormDemo.pageURL));
	  BaseClass.getElement(By.xpath("//input[@id='user-message']"));
	  Report.print("SINGLE FORM DEMO", "Sendkeys to Message", BaseClass.sendKeys(BaseClass.getElement(singleFormDemo.messageInput), "ABC"));
	  Report.print("SINGLE FORM DEMO", "Click button", BaseClass.click(BaseClass.getElement(singleFormDemo.buttonShowInput)));
	  Report.compareEqual("SINGLE FORM DEMO", "Verify Message", BaseClass.getText(BaseClass.getElement(singleFormDemo.messageOutput)), "ABC");
  }
}
