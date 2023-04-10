package KPLAYTestTools;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestRun {
	
	WebDriver driver;
	
	public void launchBrowser() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver","//Users//chanjy//Documents//chromedriver");	
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(chromeOptions);
		driver.get("https://uat.kplay88.com/");
		
		Thread.sleep(2000);
		driver.findElement(By.id("username")).sendKeys("void");
		driver.findElement(By.id("password")).sendKeys("aaaa8888");
		driver.findElement(By.id("login-btn")).click();
		
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[contains(@class,'sidebar')]/nav/ul/li[2]/a")).click();
		driver.findElement(By.xpath("//div[contains(@class,'sidebar')]/nav/ul/li[2]/ul/li[5]/a")).click();
		
		//div[contains(@class,'table-responsive')]/table/thead/tr/th[5]
		Thread.sleep(4000);
		int j=0;

		List<WebElement> products = driver.findElements(By.cssSelector("th"));
		
		System.out.println("List Of Table : " + products.size());
		
		for(int i=0; i < products.size() ; i ++) {
			String name = products.get(i).getText();
			if(name.contains("Bota")) {
				String pt = driver.findElements(By.xpath("//div[contains(@class,'table-responsive')]/table/tbody/tr/td")).get(i).getText();
				System.out.println("PT Value : " + pt);
			}
		}
				
	}
	
	public static void main(String[] args) throws InterruptedException {
		TestRun run = new TestRun();
		
		run.launchBrowser();
		
		
	}

}
