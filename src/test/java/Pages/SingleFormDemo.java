package Pages;

import org.openqa.selenium.By;

import Core.BaseTest.Constant;

public class SingleFormDemo{
	public SingleFormDemo() {
	}

	public String pageURL = Constant.getValue("URL").toString()+"simple-form-demo";
	
	public By messageInput = By.xpath("//input[@id='user-message']");
	public By messageOutput = By.xpath("//*[@id='message']");
	public By buttonShowInput = By.xpath("//*[@id='showInput']");
	
}
