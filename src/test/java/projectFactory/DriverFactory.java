package projectFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CommandExecutor;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DriverFactory {

    private static List<WebDriverThread> webDriverThreadPool = Collections.synchronizedList(new ArrayList<WebDriverThread>());
    private static ThreadLocal<WebDriverThread> driverThread;
    protected static WebDriver driver;
    
    protected static ExtentReports extn;
	protected static ExtentTest test;
	protected static ExtentHtmlReporter htmlreporter;
	//private static String extnReporthtml;
	protected static String browser; 
	protected static String  nodeip;
	public static String workingDirectory = System.getProperty("user.dir");
	public static String scrcaptureFilepath;
    
	public DriverFactory(){
		File dir = new File("TestReport");
		dir.mkdir();
		File subdir = new File(dir+"/Screenshot/");
		subdir.mkdir();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
		String datefile = dateFormat.format(now);
		File datefodler = new File(dir+"/Screenshot/"+datefile);
		datefodler.mkdir();
		scrcaptureFilepath = new File(dir+"/Screenshot/"+datefile).getAbsolutePath();
		extn = new ExtentReports();
		htmlreporter = new ExtentHtmlReporter(dir+"/Report.html");
		extn.attachReporter(htmlreporter);
	}

    @BeforeSuite
    public static void instantiateDriverObject() {
        driverThread = new ThreadLocal<WebDriverThread>() {
            @Override
            protected WebDriverThread initialValue() {
                WebDriverThread webDriverThread = new WebDriverThread();
                webDriverThreadPool.add(webDriverThread);
           	
                return webDriverThread;
            }
        };
    }

    public static WebDriver getDriver() throws Exception {
        return driverThread.get().getDriver();
    }
    
    public static void createreprot(String reportname){
		test = extn.createTest(reportname);
    }
        
   @DataProvider
   public Object[][] getData(){
	
	   return WrapperMethods.fectchdata(WrapperMethods.dataSheetName);
	   
   }
    

    @AfterMethod
    public static void clearCookies() throws Exception {
        getDriver().manage().deleteAllCookies();
    }

    @AfterSuite
    public static void closeDriverObjects() {
    	 extn.flush();
        for (WebDriverThread webDriverThread : webDriverThreadPool) {
            webDriverThread.quitDriver();
        }
       
    }
}