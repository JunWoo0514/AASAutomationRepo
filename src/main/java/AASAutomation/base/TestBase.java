package AASAutomation.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Predicate;

import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

import AASAutomation.base.*;
import AASAutomation.util.AASUtil;


public class TestBase {
	
	public static DriverManager driverManager;
	public static WebDriver driver;
	public static Properties prop;
	public static WebDriverWait wait;
	Wait<WebDriver> fwait;
	
	//Gets data from properties file
		public TestBase(){
			try {
				prop = new Properties();
				FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/AASAutomation/config/config.properties");
				prop.load(ip);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		public static void initialization(){
			String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") :  prop.getProperty("browser");
			
			if(browserName.equals("chrome")){
				driverManager = DriverManagerFactory.getDriverManager(DriverType.CHROME);
				driver = driverManager.getWebDriver();
				//ChromeOptions options = new ChromeOptions();
				//options.addArguments("--remote-allow-origins=*");
				//driver = new ChromeDriver(options);
			}
			else if(browserName.equals("FF")){
				//For Fire Fox
				driver = new FirefoxDriver();
			}
			
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			//driver.manage().timeouts().getPageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(AASUtil.PAGE_LOAD_TIMEOUT);
			driver.manage().timeouts().implicitlyWait(AASUtil.IMPLICIT_WAIT);
			
			driver.get(prop.getProperty("url"));
			
			//wait = new WebDriverWait(driver, TestUtil.ELEMENT_WAIT);

			//actions = ActionChains(driver);
			wait = (WebDriverWait) new WebDriverWait(driver, AASUtil.ELEMENT_WAIT_MID);
			
			
			
		}
		
		//Wait
		public void waitVisibility(WebElement element){

			wait.until(ExpectedConditions.visibilityOf(element));
	    }
		
		public void waitVisibilityLocate(By Byelement){
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(Byelement));

	    }
		
		public void waitInVisibilityLocate(By Byelement){
			
			wait.until(ExpectedConditions.invisibilityOfElementLocated(Byelement));

	    }
		
		public void waitClickable(WebElement element){

			wait.until(ExpectedConditions.elementToBeClickable(element));
	    }
		
		
		public void CustomWaitClick(final WebElement element, final String cssText) throws InterruptedException {
			for(int i=0;i<10;i++)
				
			{
				boolean t = driver.findElement(By.cssSelector(cssText)).isDisplayed();
				System.out.println("Btn State: " +t);
				if(t == true) {
					Thread.sleep(150);
					System.out.println("Custom " + i + "  OK");
					try {
						element.click();
						System.out.println("Custom " + i + "  OK");
					}catch(StaleElementReferenceException exc) {
						break;
					}
					
				}
	
				else
	
				{
					System.out.println("Custom " + i + "  NO");
	
				}

			}
		}
		
		
		
		//Click Method
	    public void click(WebElement element) {
	    	//System.out.print("1:"+element.toString());
	    	waitVisibility(element);
	        //waitVisibilityLocate(element);
	        element.click();
	    }
	    
	    public void justClick(WebElement element) {
	        //waitVisibilityLocate(element);
	        element.click();
	    }
	    
	    public void clickWait(WebElement element) throws InterruptedException {
	    	//System.out.print("1:"+element.toString());
	    	waitVisibility(element);
	        waitClickable(element);
	        element.click();
	        Thread.sleep(2000);
	    }
	    
	    //Write Text
	    public void writeText(WebElement element, String text) {
	    	waitVisibility(element);
	    	clearText(element);
	        element.sendKeys(text);
	    }
	    
	    public void OnclickUP(WebElement element) {
	    	waitVisibility(element);
	    	element.sendKeys(Keys.UP);
	    }
	    
	    public void OnclickDOWN(WebElement element) {
	    	waitVisibility(element);
	    	element.sendKeys(Keys.DOWN);
	    }
	    
	    
	    //Clear Text
	    public void clearText(WebElement element) {
	    	waitVisibility(element);
	        element.clear();
	    }
	 
	    //Read Text
	    public String readText(WebElement element) {
	        waitVisibility(element);
	        return element.getText();
	    }
	    
	    //Checking
	    public boolean elementDisplayCheck(WebElement element) {
	        return element.isDisplayed();
	    }
	    
	    //Read Text Box
	    public String readTextBox(WebElement element) {
	        waitVisibility(element);
	        return element.getAttribute("value");
	    }
	    
	    //Select Item From Dropdown List
	    public void selectItem(WebElement element, String text) {
	    	waitVisibility(element);
	        Select select = new Select(element);	
	        select.selectByVisibleText(text);	
	    }
	    
	    public void selectItemValue(WebElement element, int text) {
	        waitVisibility(element);
	        Select select = new Select(element);	
	        select.selectByValue(Integer.toString(text));	
	    }
	    
	    public void selectItemFirstItem(WebElement element) {
	        waitVisibility(element);
	        Select select = new Select(element);	
	        select.getFirstSelectedOption().getText();
	    }
	    
	    @AfterClass
		public void tearDown() {
	    	driver.close();
			driver.quit();
		}

}
