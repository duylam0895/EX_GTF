package Pages;

import Core.Helper.Appium.DriverClass;

public class PageExtension {

    public static HomeScreen Home(){
        return new HomeScreen(DriverClass.driver);
    }
}
