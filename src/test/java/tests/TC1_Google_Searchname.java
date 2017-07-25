package tests;


import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import projectFactory.DriverFactory;
import projectFactory.WebDriverThread;
import projectFactory.WrapperMethods;
import pages.GooglePage;
import pages.GoogleResultsPage;

public class TC1_Google_Searchname extends DriverFactory{
	
	@BeforeTest
	@Parameters({"browser","Node"})
	  public void remotewebdriver(@Optional String browserval, String nodeval){
		browser = browserval;
		nodeip = nodeval;	  
		   
	  }
	
	@BeforeClass
	public void startTestCase(){
		
		WrapperMethods.dataSheetName 	= "Search";
			
	}
	
	@Test(dataProvider = "getData")
	public static void namesearch(String SearchString) throws Exception{

		DriverFactory.createreprot("TC1_Google_Searchname");
		driver = DriverFactory.getDriver();
		driver.get("https://www.google.com");
		 new GooglePage()
		 .EnterSearch(SearchString)
		 .clicksearch();
		
		 new GoogleResultsPage()
		 
		 .verifyresults();
		
		 
		}
	
	
	
}
