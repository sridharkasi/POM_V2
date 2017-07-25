package pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import projectFactory.DriverFactory;

public class GoogleResultsPage {
	@FindBy(tagName="a")
	private List<WebElement> getlinks;
	
	public  GoogleResultsPage() throws Exception{
		PageFactory.initElements(DriverFactory.getDriver(), this);
		
	}
	public GoogleResultsPage verifyresults() throws InterruptedException{
		
		System.out.println("Total Links count :"+ getlinks.size());
		Thread.sleep(5000);
		return this;
	
	}

}
