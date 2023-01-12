package Tests;

import Core.BaseTest.BaseAppium;
import Core.Helper.Appium.DriverClass;
import Core.Helper.Report.Report;
import Pages.HomeScreen;
import Pages.PageExtension;
import org.testng.annotations.Test;

public class Web_Demo_3 extends BaseAppium {
  @Test
  public void test() throws InterruptedException {
      HomeScreen home = new HomeScreen(DriverClass.driver);
      Report.print("Home Screen", "Click OK button", DriverClass.click(PageExtension.Home().ok_btn));
      Thread.sleep(5000);
      //Report.waitCondition("Home screen", "Wait for OK button dissapeal", () -> , 10, 1);
  }
}
