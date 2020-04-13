package pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LandingPage {
	public static Logger log=LogManager.getLogger(LandingPage.class.getName());
	public  WebDriver driver;
	//Constructor(default)
    public LandingPage() {
    }
    //Constructor(parameterized)
	 public LandingPage(WebDriver driver) {
		 this.driver=driver;
	     PageFactory.initElements(driver, this);
	 }

	 @FindBy(how=How.CSS,using="a[href*='sign_in']")
	 private WebElement lnkLogin;
	 @FindBy(how=How.CSS,using="[class='text-center']>h2")
	 private WebElement lblTitle;
	 
	 public WebElement getLogin()
	 {
		 WebDriverWait wait=new WebDriverWait(driver,30);
		 wait.until(ExpectedConditions.elementToBeClickable(lnkLogin));
		 return lnkLogin;
	 }
	 public WebElement getTitle()
	 {
		 return lblTitle;
	 }
}
