package projectFactory;

import projectConfig.DriverType;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.net.MalformedURLException;
import java.net.URL;

import static projectConfig.DriverType.FIREFOX;
import static projectConfig.DriverType.valueOf;


public class WebDriverThread {

   
	private WebDriver webdriver;
    private DriverType selectedDriverType;
    private final DriverType defaultDriverType = FIREFOX;
    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");
  
  
 
  
    public WebDriver getDriver() throws Exception { 	
    	
        if (null == webdriver) {
     
            selectedDriverType = determineEffectiveDriverType(DriverFactory.browser);
            DesiredCapabilities desiredCapabilities = selectedDriverType.getDesiredCapabilities();
            instantiateWebDriver(desiredCapabilities);
        }

        return webdriver;
    }

    public void quitDriver() {
        if (null != webdriver) {
            webdriver.quit();
        }
    }

    private DriverType determineEffectiveDriverType(String browser) {
        DriverType driverType = defaultDriverType;
        try {
            driverType = valueOf(browser);
        } catch (IllegalArgumentException ignored) {
            System.err.println("Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
            System.err.println("No driver specified, defaulting to '" + driverType + "'...");
        }
        return driverType;
    }

    
	private void instantiateWebDriver(DesiredCapabilities desiredCapabilities) throws MalformedURLException {
        System.out.println(" ");
        System.out.println("Current Operating System: " + operatingSystem);
        System.out.println("Current Architecture: " + systemArchitecture);
        System.out.println("Current Browser Selection: " + selectedDriverType);
        System.out.println(" ");
      
        if(!DriverFactory.nodeip.equalsIgnoreCase(""))
        	
        {
        	
        	desiredCapabilities.setPlatform(Platform.WIN8_1);
        	 URL seleniumGridURL = new URL(DriverFactory.nodeip);
			webdriver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
        }
        else{
        	webdriver = selectedDriverType.getWebDriverObject(desiredCapabilities);
        }
       
    }
}