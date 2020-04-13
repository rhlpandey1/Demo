package pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	public static Logger log=LogManager.getLogger(LoginPage.class.getName());
	public WebDriver driver;
	//Constructor(default)
    public LoginPage() {
    }
    //Constructor(parameterized)
	 public LoginPage(WebDriver driver) {
		 this.driver=driver;
	     PageFactory.initElements(driver, this);
	 }

	 @FindBy(how=How.CSS,using="[id='user_email']")
	 private WebElement txtbxEmailAddress;
	 @FindBy(how=How.CSS,using="[id='user_password']")
	 private WebElement txtbxPassword;
	 @FindBy(how=How.CSS,using="input[class*='login-button']")
	 private WebElement btnLogIn;
	 
	 public WebElement getEmail()
	 {
		 return txtbxEmailAddress;
	 }
	 public WebElement getPassword()
	 {
		 return txtbxPassword;
	 }
	 public WebElement getLogInButton()
	 {
		 return btnLogIn;
	 }
}
