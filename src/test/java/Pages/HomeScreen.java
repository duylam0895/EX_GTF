package Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomeScreen{
	AppiumDriver driver;
	public HomeScreen(AppiumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
	}
	
	@FindBy(xpath="//android.widget.Button[@resource-id='android:id/button1']")
	public MobileElement ok_btn;
}
