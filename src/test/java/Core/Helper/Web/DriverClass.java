package Core.Helper.Web;

import java.io.File;
import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import Core.BaseTest.Constant;
import Core.BaseTest.Page;
import Core.Helper.Report.ReportHandle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class DriverClass{

	public static WebDriver driver;
	private static final String DIR_CHROME_DRIVER = System.getProperty("user.dir") + File.separator + "driver\\chromedriver.exe";
	private static Logger logger = LogManager.getLogger(DriverClass.class);
	
	public DriverClass() {
		
	}
	/***
	 * Initialize a Chrome browser
	 * 
	 * @return
	 * @since 0.0.1
	 * @author Lam
	 */
	public static boolean initChromeBrowser() {
		String stepDes = "InitializeChrome Browser.";
		logger.info(stepDes);
		System.setProperty("webdriver.chrome.driver", DIR_CHROME_DRIVER);
		ChromeOptions options = new ChromeOptions();
//		options.setHeadless(true);
		Duration timeout = Duration.ofSeconds(Constant.IMPLIXIT_WAIT);
		//TODO: Add more option here in the future
		try {
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(timeout);
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
	public static boolean navigate(String url) {
		String stepDes = String.format("Navigate to [%s]", url);
		logger.info(stepDes);
		driver.navigate().to(url);
		return waitPageLoad(Constant.IMPLIXIT_WAIT);
	}
	
	/***
	 * Navigate browser to url
	 * 
	 * @param url
	 * @since 0.0.1
	 * @author Lam
	 */
	public static ReportHandle navigate(Page page) {
		String stepDes = String.format("Navigate to [%s]", page.getURL());
		logger.info(stepDes);
		ReportHandle handle = new ReportHandle();
		boolean isDone = false;
		try {
			driver.navigate().to(page.getURL());
			isDone = waitPageLoad(Constant.IMPLIXIT_WAIT);
			if(isDone) {
				isDone = waitUntilDisplay(driver.findElement(page.getPageLocator()), Constant.EXPLIXIT_WAIT);
			}
		}catch (Exception e) {
			handle.updateStatus(false, String.format("Time out after %s seconds", Constant.EXPLIXIT_WAIT), e);
		}
		handle.updateStatus(isDone, "", null);
		return handle;
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
	public static boolean waitPageLoad(int secondTimes) {
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
	public static ReportHandle click(WebElement objEle) {
		String stepDes = String.format("Click Object [%s]", objEle.toString());
		logger.info(stepDes);
		ReportHandle handle = new ReportHandle();
		try {
			objEle.click();
		}catch(Exception e) {
			logger.error(stepDes + ": " + e.toString());
			handle.updateStatus(false, "", e);
			return handle;
		}
		handle.updateStatus(true, "", null);
		return handle;
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
	public static WebElement getElement(By objBy) {
		String stepDes = String.format("Get Object Element [%s]", objBy.toString());
		logger.info(stepDes);
		
		WebElement objEle = null;
		try {
			objEle = driver.findElement(objBy);
		}catch(NoSuchElementException exp) {
			logger.error(stepDes + ": " + exp.toString());
		}
		return objEle;
	}
	
	/***
	 * Get Element of Object
	 * 
	 * @param propertyPath
	 * @param objPath
	 * @throws {@link NoSuchElementException}
	 * @since 0.0.1
	 * @author Lam
	 * @return
	 */
	public static WebElement getElement(String objPath) {
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
	public static ReportHandle sendKeys(WebElement objEle, String value) {
		String stepDes = String.format("Key [%s] value to Object [%s]", value, objEle.toString());
		logger.info(stepDes);
		ReportHandle handle = new ReportHandle();
		try {
			objEle.sendKeys(value);
		}catch(Exception e) {
			logger.error(stepDes + ": " + e.toString());
			handle.updateStatus(false, "", e);
			return handle;
		}
		handle.updateStatus(true, "", null);
		return handle;
	}
	
	/***
	 * Wait until Element display
	 * 
	 * @param objEle
	 * @param secondTimes
	 * @return
	 * @thrown {@link TimeoutException} 
	 * @since 0.0.1
	 * @author Lam
	 */
	public static boolean waitUntilDisplay(WebElement objEle, int secondTimes) {
		Duration timeout = Duration.ofSeconds(secondTimes);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		boolean result = false;
		try {
			result = wait.until(ExpectedConditions.visibilityOf(objEle)) != null ? true : false;
		}catch(TimeoutException exp) {
			
		}
		return result;
	}
	
	/***
	 * Wait until Element display
	 * 
	 * @param objEle
	 * @param secondTimes
	 * @return
	 * @thrown {@link TimeoutException} 
	 * @since 0.0.1
	 * @author Lam
	 */
	public static boolean waitUntilClickAble(WebElement objEle, int secondTimes) {
		Duration timeout = Duration.ofSeconds(secondTimes);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		boolean result = false;
		try {
			result = wait.until(ExpectedConditions.elementToBeClickable(objEle)) != null ? true : false;
		}catch(TimeoutException exp) {
			
		}
		return result;
	}
	
	/***
	 * Wait until Element display
	 * 
	 * @param objEle
	 * @param secondTimes
	 * @return
	 * @thrown {@link TimeoutException} 
	 * @since 0.0.1
	 * @author Lam
	 */
	public static boolean waitUntilDisplay(WebElement objEle) {
		Duration timeout = Duration.ofSeconds(Constant.EXPLIXIT_WAIT);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		boolean result = false;
		try {
			result = wait.until(ExpectedConditions.visibilityOf(objEle)) != null ? true : false;
		}catch(TimeoutException exp) {
			
		}
		return result;
	}
	
	/***
	 * Quit browser
	 * 
	 * @param element
	 * @return
	 * @since 0.0.1
	 * @author Lam
	 */
	public static void quitBrowser() {
		System.out.println("Quit browser");
		driver.close();
		driver.quit();
	}
	
	/***
	 * Get Element Text
	 * 
	 * @param element
	 * @return
	 * @thrown {@link NullPointerException} 
	 * @since 0.0.1
	 * @author Lam
	 */
	public static String getText(WebElement element) {
		try {
			return element.getText() != null? element.getText() : "";
		}catch (NullPointerException e) {
			e.printStackTrace();
			return e.toString();
		}
	}
	
	/***
	 * Get Element Text
	 * 
	 * @param element
	 * @return
	 * @thrown {@link NullPointerException} 
	 * @since 0.0.1
	 * @author Lam
	 */
	public static ReportHandle switchWindow() {
		ReportHandle handle = new ReportHandle();
		handle.isTrue = true;
		try {
			for(String winHandle : driver.getWindowHandles()){
			    driver.switchTo().window(winHandle);
			}
		    return handle;
		}catch (NullPointerException e) {
			e.printStackTrace();
			return handle;
		}
	}	
	
	/***
	 * Get Element Text
	 * 
	 * @param element
	 * @return
	 * @thrown {@link NullPointerException} 
	 * @since 0.0.1
	 * @author Lam
	 */
	public static ReportHandle selectDropdown(WebElement element, String visibleText) {
		ReportHandle handle = new ReportHandle();
		handle.isTrue = true;
		try {
			Select select = new Select(element);
			select.selectByVisibleText(visibleText);
		    return handle;
		}catch (NullPointerException e) {
			e.printStackTrace();
			return handle;
		}
	}
}
