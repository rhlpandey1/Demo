package udemy.driverconfig;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;

import udemy.constants.Constants;
import udemy.utils.Utilities;

public class Base {
	public static Logger log=LogManager.getLogger(Base.class.getName());
	private static String baseProjectPath = System.getProperty(Constants.USER_DIR);
	//we can't make the driver static for parallel execution
	public static WebDriver driver=null;
	public ChromeOptions options=null;
	public WebDriver initializeDriver() throws IOException
	{
		//This is step is required to run the test using maven command
		//String browserType=System.getProperty("browser");
		String browserType=Utilities.getProperty("browser");
		/** The base project path. */
		switch(browserType)
		{
			case "chrome":
				System.setProperty(Constants.CHROME_WEBDRIVER,
						baseProjectPath.concat(Constants.CHROME_DRIVER_PATH));
				//added to remove pop ups and warnings
				options = new ChromeOptions();
				options.addArguments("test-type");
				options.addArguments("no-sandbox");
				//Fix for cannot get automation extension
				options.addArguments("disable-extensions");
				options.addArguments("start-maximized");
				options.addArguments("--js-flags=--expose-gc");         
				options.addArguments("disable-plugins");
				options.addArguments("--enable-precise-memory-info"); 
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--disable-default-apps");
				options.addArguments("test-type=browser");
				options.addArguments("disable-infobars");
				options.addArguments("--start-maximized");
				options.setExperimentalOption("useAutomationExtension", false);
				driver=new ChromeDriver(options);
				break;
			case "firefox":
				System.setProperty(Constants.GEKO_DRIVER,
						baseProjectPath.concat(Constants.GEKO_DRIVER_PATH));
				driver=new FirefoxDriver();
				break;
			case "ie":
				System.setProperty(Constants.IE_DRIVER,
						baseProjectPath.concat(Constants.IE_DRIVER_PATH));
				driver=new InternetExplorerDriver();
				break;
			case "headless":
				System.setProperty(Constants.CHROME_WEBDRIVER,
						baseProjectPath.concat(Constants.CHROME_DRIVER_PATH));
				//added to remove pop ups and warnings
				options = new ChromeOptions();
				options.addArguments("test-type");
				options.addArguments("no-sandbox");
				//Fix for cannot get automation extension
				options.addArguments("disable-extensions");
				options.addArguments("start-maximized");
				options.addArguments("--js-flags=--expose-gc");         
				options.addArguments("disable-plugins");
				options.addArguments("--enable-precise-memory-info"); 
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--disable-default-apps");
				options.addArguments("test-type=browser");
				options.addArguments("disable-infobars");
				options.addArguments("--headless");
				options.addArguments("--start-maximized");
				options.setExperimentalOption("useAutomationExtension", false);
				driver=new ChromeDriver(options);
				break;
			case "edge":
				System.setProperty(Constants.EDGE_DRIVER,
						baseProjectPath.concat(Constants.EDGE_DRIVER_PATH));
				driver=new EdgeDriver();
				break;
		}
		driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_TIMEOUT_VALUE, TimeUnit.SECONDS);
		
		return driver;
	}
	public static void takeScreenShot(String resName) throws IOException
	{
		String userDir=System.getProperty("user.dir");
		String scDir=userDir.concat("\\").concat("ScreenShots");
		Date d=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("ddMMyyyyhhmmss");
		String date=sdf.format(d);
		File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String dest=scDir+"/"+resName+"_"+date+".jpg";
		FileHandler.copy(src, new File(dest));
	}
	public static String takeScreenShot() throws IOException
	{
		String userDir=System.getProperty("user.dir");
		String scDir=userDir.concat("\\").concat("ScreenShots");
		Date d=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("ddMMyyyyhhmmss");
		String date=sdf.format(d);
		File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String dest=scDir+"/"+"_"+date+".jpg";
		FileHandler.copy(src, new File(dest));
		return dest;
	}
}
