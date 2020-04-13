package udemy.testcases;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.LandingPage;
import pageobjects.LoginPage;
import udemy.driverconfig.Base;
import udemy.utils.ApplicationDetails;
import udemy.utils.Utilities;

public class DemoTest extends Base {
	public static Logger log=LogManager.getLogger(DemoTest.class.getName());
	@BeforeTest
	public void initialize() throws IOException
	{
		driver=initializeDriver();
		//driver.get(Utilities.getProperty("url"));
	}
	
	@Test
	public void loginToTheApp() throws IOException, InterruptedException
	{
		Utilities util=new Utilities();
		ApplicationDetails appDetails=new ApplicationDetails();
		appDetails.setUserNameFromDB(Utilities.getProperty("userName"));
		ApplicationDetails app=util.getApplicationDetails(appDetails,"GET_PASSWORD");
		//We have added driver.get here because we are executing this for multiple set of test data.
		//If we don't add application launch step here the for the second run the script will be at the same place
		driver.get(Utilities.getProperty("url"));
		log.info("Application launched");
		driver.manage().window().maximize();
		LandingPage lp=new LandingPage(driver);
		Utilities.highLightElement(driver, lp.getLogin());
		lp.getLogin().click();
		LoginPage loginPage=new LoginPage(driver);
		//Reading from properties file
		/*loginPage.getEmail().sendKeys(Utilities.getProperty("userName"));
		loginPage.getPassword().sendKeys("password");*/
		
		//Reading from DB
		Utilities.highLightElement(driver, loginPage.getEmail());
		loginPage.getEmail().sendKeys(app.getUserNameFromDB());
		Utilities.highLightElement(driver, loginPage.getPassword());
		loginPage.getPassword().sendKeys(app.getPasswordFromDB());
		log.info("Username "+app.getUserNameFromDB()+" and password entered "+app.getPasswordFromDB()+" ");
		loginPage.getLogInButton().click();
	}
	@Test(dataProvider="getData",enabled=false)
	public void loginToTheAppUsingDataProvider(String userName,String password,String loginText) throws IOException, InterruptedException
	{
		ApplicationDetails appDetails=new ApplicationDetails();
		appDetails.setUserNameFromDB(Utilities.getProperty("userName"));
		//We have added driver.get here because we are executing this for multiple set of test data.
		//If we don't add application launch step here the for the second run the script will be at the same place
		driver.get(Utilities.getProperty("url"));
		log.info("Application launched");
		driver.manage().window().maximize();
		LandingPage lp=new LandingPage(driver);
		Utilities.highLightElement(driver, lp.getLogin());
		lp.getLogin().click();
		LoginPage loginPage=new LoginPage(driver);
		//Reading from properties file
		/*loginPage.getEmail().sendKeys(Utilities.getProperty("userName"));
		loginPage.getPassword().sendKeys("password");*/
		
		//Reading from dataProvider
		Utilities.highLightElement(driver, loginPage.getEmail());
		loginPage.getEmail().sendKeys(userName);
		Utilities.highLightElement(driver, loginPage.getPassword());
		loginPage.getPassword().sendKeys(password);
		log.info("Username "+userName+" and password entered "+password+" ");
		log.info(loginText);
		loginPage.getLogInButton().click();
	}
	
	@DataProvider
	public Object[][] getData()
	{
		//Row stands for no of different data
		//column stands for how many values for each test 
		//[* * *]
		//[* * *] it's like a 2D matrix
		Object[][] data=new Object[2][3];
		data[0][0]="abc@cde.com";
		data[0][1]="Pwd12345";
		data[0][2]="Login set 1";
		data[1][0]="xyz@cde.com";
		data[1][1]="Pwd12345";
		data[1][2]="Login set 2";
		return data;
	}
	
	@AfterTest
	public void tearDown()
	{
		driver.close();
	}
}
