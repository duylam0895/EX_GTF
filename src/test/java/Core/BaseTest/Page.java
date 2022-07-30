package Core.BaseTest;

import org.openqa.selenium.By;

public abstract class Page extends BaseWeb{

	public abstract String getURL();
	public abstract By getPageLocator();
}
