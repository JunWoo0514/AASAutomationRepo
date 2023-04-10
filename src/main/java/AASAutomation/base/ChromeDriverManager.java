package AASAutomation.base;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import AASAutomation.base.DriverManager;

public class ChromeDriverManager  extends DriverManager{
	
	public void createWebDriver() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		String projectPath = System.getProperty("user.dir");
		System.out.println("Result : " + projectPath);
		System.setProperty("webdriver.chrome.driver",projectPath+"//drivers//chromedriver");	
		this.driver = new ChromeDriver(options);
	}

}
