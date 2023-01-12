package Core.Helper.Appium;

public class DeviceProperties {
    private String deviceName;

    private String platformName;

    private String appPackage;

    private String appActivity;

    private String hostUrl;

    private String hostPost;

    private String hostPath;

    public DeviceProperties(String deviceName, String platformName,
                            String appPackage, String appActivity,
                            String hostUrl, String hostPost,
                            String hostPath){
        this.deviceName = deviceName;
        this.platformName = platformName;
        this.appPackage = appPackage;
        this.appActivity = appActivity;
        this.hostPath = hostPath;
        this.hostUrl = hostUrl;
        this.hostPost = hostPost;
    }

    public String deviceName(){
        return this.deviceName;
    }

    public String platformName(){
        return this.platformName;
    }

    public String appPackage(){
        return this.appPackage;
    }

    public String appActivity(){
        return this.appActivity;
    }

    public String hostPost(){
        return this.hostPost;
    }

    public String hostUrl(){
        return this.hostUrl;
    }

    public String hostPath(){
        return this.hostPath;
    }
}
