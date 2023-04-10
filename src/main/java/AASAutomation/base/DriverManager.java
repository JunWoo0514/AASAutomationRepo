package AASAutomation.base;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {
	
	protected WebDriver driver;
	protected abstract void createWebDriver();
	
	protected void quitWebDriver() {
		if(null != driver) {
			driver.quit();
			driver = null;
		}
	}
	
	public WebDriver getWebDriver() {
		if(null == driver) {
			createWebDriver();
		}
		return driver;
	}

}
