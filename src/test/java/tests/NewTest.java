package tests;

import org.testng.annotations.Test;

import Core.BaseTest.Constant;
import Core.Helper.Report.Report;
import Core.Helper.Web.BaseClass;
import Pages.LoginPage;

public class NewTest extends LoginPage{
  @Test
  public void test() {
	  Report.print("Browser", "Init browser", BaseClass.navigate(Constant.URL));
	  System.out.print("HELLO");
  }
}
