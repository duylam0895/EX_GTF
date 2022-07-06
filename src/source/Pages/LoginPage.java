package Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Core.Constant;
import Core.Helper.Report.Report;
import Core.Helper.Web.BaseTest;

public class LoginPage extends BaseTest{
	private Logger logger = LogManager.getLogger(LoginPage.class);
	private static final String USERNAME = "login.username.input.Id";
	private static final String PASSWORD = "login.password.input.Id";
	private static final String LOGIN_BTN = "login.login.button.Id";
	private static final String USERNAME_DATA = "Username";
	private static final String PASSWORD_DATA = "Password";
	
	/*
	 * public boolean getPage(String propertiesPath) { String stepDes =
	 * "Get Login page"; logger.info(stepDes); Report.logInfo("Login", stepDes, "");
	 * 
	 * waitPageLoad(10); return waitUntilDisplay(getElement(getBy(propertiesPath,
	 * USERNAME)), 30); }
	 * 
	 * public boolean login(String propertiesPath, String dataName) { String stepDes
	 * = "Select Login page"; logger.info(stepDes);
	 * 
	 * sendKeys(getElement(getBy(propertiesPath, USERNAME)), Constant.USER_NAME);
	 * sendKeys(getElement(getBy(propertiesPath, PASSWORD)), Constant.USER_NAME);
	 * getElement(getBy(propertiesPath, USERNAME)).click(); return
	 * click(getElement(getBy(propertiesPath, LOGIN_BTN))); }
	 */
}
