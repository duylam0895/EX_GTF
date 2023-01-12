package Core.Helper.Appium;

import Core.BaseTest.Constant;
import Core.Helper.Report.ReportHandle;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class DriverClass extends Core.Helper.Web.DriverClass {

	public static AppiumDriver driver;
	private static Logger logger = LogManager.getLogger(DriverClass.class);
	
	public DriverClass() {
		
	}
	/***
	 * Initialize a driver
	 * 
	 * @return
	 * @since 0.0.1
	 * @author Lam
	 */
	public static boolean initDriver(DeviceProperties properties) {
		String stepDes = "Initialize Platform Driver";
		logger.info(stepDes);

		DesiredCapabilities options = new DesiredCapabilities();

		options.setCapability("deviceName", properties.deviceName());
		options.setCapability("platformName", properties.platformName());
		options.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, properties.appPackage());
		options.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, properties.appActivity());

		String appiumServerHost = properties.hostUrl() + ":" + properties.hostPost() + properties.hostPath();
		Duration timeout = Duration.ofSeconds(Constant.IMPLIXIT_WAIT);

		try {
			if(properties.platformName().equalsIgnoreCase("android")){
				driver = new AndroidDriver(new URL(appiumServerHost), options);
			}
			driver.manage().timeouts().implicitlyWait(Constant.IMPLIXIT_WAIT, TimeUnit.SECONDS);
		}catch(Exception e) {
			e.printStackTrace();
			logger.debug(stepDes + ": " + e.toString());
		}
		return driver != null ? true : false;
	}
	
	/***
	 * Quit browser
	 *
	 * @return
	 * @since 0.0.1
	 * @author Lam
	 */
	public static void quitBrowser() {
		System.out.println("Quit browser");
		driver.quit();
	}

	public static ReportHandle click(MobileElement objEle){
		String stepDes = String.format("Click Object [%s]", objEle.toString());
		logger.info(stepDes);
		ReportHandle handle = new ReportHandle();
		try {
			objEle.click();
		}catch(StaleElementReferenceException e) {
			logger.error(stepDes + ": " + e.toString());
			handle.updateStatus(false, "", e);
			return handle;
		}
		handle.updateStatus(true, "", null);
		return handle;
	}
}
