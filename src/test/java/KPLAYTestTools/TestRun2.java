package KPLAYTestTools;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class TestRun2 {
	
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
		driver.findElement(By.xpath("//div[contains(@class,'sidebar')]/nav/ul/li[2]/ul/li[1]/a")).click();
		
		
		//html/body/div[2]/main/div/div/div[2]/div[4]/div[2]/table/tfoot/tr/td[5]/select
		Thread.sleep(6000);
		int j=0;

		List<WebElement> foot = driver.findElements(By.cssSelector("tfoot td"));
		
		System.out.println("List Of Table : " + foot.size());
		for(int i=0; i < foot.size() ; i ++) {
			
			List<WebElement> prod = driver.findElements(By.xpath("//div[contains(@class,'table-responsive')]/table/tfoot/tr/td["+i+"]/select"));
			if (prod.size() > 0) {
				WebElement combo = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tfoot/tr/td["+i+"]/select"));
				Select s = new Select(combo);
				String name = s.getFirstSelectedOption().getText();
				System.out.println("Product Name : " + name);
				if(name.contains("Big Gaming")) {
				    System.out.println("Cell "+i+" : Element exists");
				    System.out.println("I Value : " + i);
					String gpt = driver.findElements(By.xpath("//div[contains(@class,'table-responsive')]/table/tbody/tr[1]/td")).get(i+1).getText();
					String ept = driver.findElements(By.xpath("//div[contains(@class,'table-responsive')]/table/tbody/tr[1]/td")).get(i+2).getText();
					System.out.println("Given PT Value : " + gpt);
					System.out.println("EFF PT Value : " + ept);
				break;
				}
			}
			
			
		}

		
		driver.close();
		driver.quit();
				
	}
	
	public static void main(String[] args) throws InterruptedException {
		TestRun2 run = new TestRun2();
		
		run.launchBrowser();
		
		
	}

}
