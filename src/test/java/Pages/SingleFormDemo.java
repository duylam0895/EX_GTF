package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Core.BaseTest.Constant;
import Core.BaseTest.Page;

public class SingleFormDemo extends Page{
	WebDriver driver;
	public SingleFormDemo(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	public String getURL() {
		return Constant.getValue("URL").toString()+"simple-form-demo";
	}
	
	public By getPageLocator() {
		return By.xpath("//input[@id='user-message']");
	}
	
	@FindBy(xpath="//input[@id='user-message']")
	public WebElement messageInput;
	@FindBy(xpath="//*[@id='message']")
	public WebElement messageOutput;
	@FindBy(xpath="//*[@id='showInput']")
	public WebElement buttonShowInput;
}
