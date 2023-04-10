package AASAutomation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import AASAutomation.base.*;

public class LoginPage extends TestBase{
	
	//Page Element
			@FindBy(id="username") 
			WebElement username;
			
			@FindBy(id="password") 
			WebElement password;
			
			@FindBy(xpath="/html/body/div[1]/div/div/div/div/form[1]/div[1]/div/span/strong") 
			WebElement errorMessage;
			
			@FindBy(id="login-btn") 
			WebElement loginButton;
			
			@FindBy(xpath="/html/body/div[1]/div/div/div/div/form[1]/div[3]/div/select") 
			WebElement languageSelect;
			
			//Initializing the Page Objects:
			public LoginPage(){
				PageFactory.initElements(driver, this);
			}
			
			//Actions:
			public String validateLoginPageTitle(){
				return driver.getTitle();
			}
			
			public HomePage login(String userName, String passWord) {
				
				this.selectLangauge("English");
				clearText(username);
				clearText(password);
				writeText(username, userName);
				writeText(password, passWord);
				click(loginButton);
				
				return new HomePage();
			}
			
			public HomePage loginAction() {
				this.selectLangauge("English");
				clearText(username);
				clearText(password);
				String userName = System.getProperty("username")!=null ? System.getProperty("username") : prop.getProperty("username");
				String passWord = System.getProperty("password")!=null ? System.getProperty("password") : prop.getProperty("password");
				writeText(username, userName);
				writeText(password, passWord);
				click(loginButton);
				return new HomePage();
			}
			
			
			public void selectLangauge(String language) {	
				selectItem(languageSelect,language);
			}
			
			public void clickLogin() {
				loginButton.click();
			}
			
			public String getErrorMessage() {
				
				String errorMessageText = readText(errorMessage);
				
				return errorMessageText;
			}

}
