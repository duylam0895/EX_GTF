package Core.Helper.Web;

import java.io.File;
import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import Core.Helper.Report.Report;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class BaseTest extends Report{

	public static WebDriver driver;
	private final String DIR_CHROME_DRIVER = System.getProperty("user.dir") + File.separator + "driver\\chromedriver.exe";
	private Logger logger = LogManager.getLogger(BaseTest.class);
	
	public BaseTest() {
		
	}
	/***
	 * Initialize a Chrome browser
	 * 
	 * @return
	 * @since 0.0.1
	 * @author Lam
	 */
	public boolean initChromeBrowser() {
		String stepDes = "InitializeChrome Browser.";
		logger.info(stepDes);
		System.setProperty("webdriver.chrome.driver", DIR_CHROME_DRIVER);
		ChromeOptions options = new ChromeOptions();
		//TODO: Add more option here in the future
		try {
			if(driver != null) driver.close();
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
		}catch(Exception e) {
			e.printStackTrace();
			logger.debug(stepDes + ": " + e.toString());
		}
		return driver != null ? true : false;
	}
	
	/***
	 * Navigate browser to url
	 * 
	 * @param url
	 * @since 0.0.1
	 * @author Lam
	 */
	public void navigate(String url) {
		String stepDes = String.format("Navigate to [%s]", url);
		logger.info(stepDes);
		driver.navigate().to(url);
	}
	
	/***
	 * Wait Page load done until timeout
	 * 
	 * @param timeout
	 * @return
	 * @throws TimeoutException
	 * @since 0.0.1
	 * @author Lam
	 */
	public boolean waitPageLoad(int secondTimes) {
		String stepDes = String.format("Wait page load successfull until [%s] s", secondTimes);
		logger.info(stepDes);
		Duration timeout = Duration.ofSeconds(secondTimes);
		try {
			new WebDriverWait(driver, timeout).until(
					driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
		}catch(TimeoutException e) {
			logger.error(stepDes + ": " + e.toString());
			return false;
		}
		return true;
	}
	
	/***
	 * Click a Object Element
	 * 
	 * @param objEle
	 * @throws StaleElementReferenceException
	 * @since 0.0.1
	 * @author Lam
	 * @return
	 */
	public boolean click(WebElement objEle) {
		String stepDes = String.format("Click Object [%s]", objEle.toString());
		String title = "CLICK";
		logger.info(stepDes);
		
		try {
			objEle.click();
			logPass(title, stepDes);
		}catch(StaleElementReferenceException e) {
			logger.error(stepDes + ": " + e.toString());
			logFailed(title, stepDes, e.toString());
			return false;
		}
		
		return true;
	}
	
	/***
	 * Get Element of Object
	 * 
	 * @param objBy
	 * @throws NoSuchElementException
	 * @since 0.0.1
	 * @author Lam
	 * @return
	 */
	public WebElement getElement(By objBy) {
		String stepDes = String.format("Get Object Element [%s]", objBy.toString());
		String title = "GET ELEMENT";
		logger.info(stepDes);
		
		WebElement objEle = null;
		try {
			objEle = driver.findElement(objBy);
			logPass(title, stepDes);
		}catch(NoSuchElementException exp) {
			logger.error(stepDes + ": " + exp.toString());
			logFailed(title, stepDes, exp.toString());
		}
		return objEle;
	}
	
	/***
	 * Get Element of Object
	 * 
	 * @param propertyPath
	 * @param objPath
	 * @throws NoSuchElementException
	 * @since 0.0.1
	 * @author Lam
	 * @return
	 */
	public WebElement getElement(String objPath) {
		return getElement(By.xpath(objPath));
	}
	
	/***
	 * Send keys to Object element
	 * 
	 * @param objEle
	 * @param value
	 * @return
	 * @thrown {@link IllegalArgumentException} 
	 * @since 0.0.1
	 * @author Lam
	 */
	public boolean sendKeys(WebElement objEle, String value) {
		String stepDes = String.format("Key [%s] value to Object [%s]", value, objEle.toString());
		String title = "SEND KEYS";
		logger.info(stepDes);
		
		try {
			objEle.sendKeys(value);
			logPass(title, stepDes);
		}catch(IllegalArgumentException e) {
			logger.error(stepDes + ": " + e.toString());
			logFailed(title, stepDes, e.toString());
			return false;
		}
		return true;
	}
	
	public boolean waitUntilDisplay(WebElement objEle, int secondTimes) {
		Duration timeout = Duration.ofSeconds(secondTimes);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		boolean result = false;
		try {
			result = wait.until(ExpectedConditions.visibilityOf(objEle)) != null ? true : false;
		}catch(TimeoutException exp) {
			
		}
		return result;
	}
	
	public void quitBrowser() {
		System.out.println("Quit browser");
		driver.quit();
	}
}
