package udemy.testcases;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import junit.framework.Assert;
import pageobjects.LandingPage;
import udemy.driverconfig.Base;
import udemy.utils.Utilities;

public class ValidateTitleTest extends Base {
	public static Logger log=LogManager.getLogger(ValidateTitleTest.class.getName());
	@BeforeTest
	public void initialize() throws IOException
	{
		driver=initializeDriver();
		log.info("driver initialized");
		driver.get(Utilities.getProperty("url"));
		log.info("Browser opened");
	}
	
	@Test
	public void validateTitle() throws IOException
	{
		log.info("Application launched");
		driver.manage().window().maximize();
		LandingPage lp=new LandingPage(driver);
		String expectedTitle="FEATURED COURSES";
		String actualTitle=lp.getTitle().getText();
		Assert.assertTrue(lp.getTitle().isDisplayed());
		Assert.assertEquals(actualTitle, expectedTitle);
	}
	
	@AfterTest
	public void tearDown()
	{
		driver.close();
		//driver=null;
	}
	
}
