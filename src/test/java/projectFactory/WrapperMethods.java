package projectFactory;




import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WrapperMethods {
	
	public static String filepath;
	
	public static String dataSheetName;
	public static String testCaseName;
	
    
	public void enterbyname(WebElement searchtext, String Serachvalue){
		searchtext.sendKeys(Serachvalue);
		
		DriverFactory.test.pass("Test PasseD");
	}
	
	public void submit(WebElement object) throws Exception{
		object.submit();
		filepath = Screencapture(DriverFactory.getDriver());
		DriverFactory.test.pass("Test Passed Screeencapture");
		DriverFactory.test.pass(filepath);
		DriverFactory.test.addScreenCaptureFromPath(filepath);
	}
	
	
	public static String Screencapture(WebDriver driver) throws IOException {
		// TODO Auto-generated method stub
		
		Date now = new Date();
		SimpleDateFormat timeformat = new SimpleDateFormat("hhmmss");
		String scrFilename = DriverFactory.scrcaptureFilepath+"/ScreenCaptureFile_"+timeformat.format(now)+".png";
		 File src= ((TakesScreenshot)driver). getScreenshotAs(OutputType. FILE);
		
		 FileUtils. copyFile(src, new File(scrFilename));
		 
		 
		return  scrFilename;
	}
	
	public static String[][] fectchdata(String dataSheetName){
		String[][] data = null;

		try {
			FileInputStream fis = new FileInputStream(new File(DriverFactory.workingDirectory+"/Data/TestData.xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			//XSSFSheet sheet = workbook.getSheetAt(0);	
			XSSFSheet sheet =workbook.getSheet(dataSheetName);
			// get the number of rows
			int rowCount = sheet.getLastRowNum();

			// get the number of columns
			int columnCount = sheet.getRow(0).getLastCellNum();
			data = new String[rowCount][columnCount];


			// loop through the rows
			for(int i=1; i <rowCount+1; i++){
				try {
					XSSFRow row = sheet.getRow(i);
					for(int j=0; j <columnCount; j++){ // loop through the columns
						try {
							String cellValue = "";
							try{
								cellValue = row.getCell(j).getStringCellValue();
							}catch(NullPointerException e){

							}

							data[i-1][j]  = cellValue; // add to the data array
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}				
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			fis.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
		
	}
	
}
