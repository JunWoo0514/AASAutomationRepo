package AASAutomation.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AASAutomation.base.TestBase;
import AASAutomation.pages.*;


public class HomePage extends TestBase{
	
		//Page Factory
		//Menu List
		//Manu Category Element List
		@FindBy(css="a[class='nav-link nav-dropdown-toggle']") 
		List<WebElement> SideMenuTitle;
		
		//Sub Menu Item Element List
		@FindBy(css="li[data-name='1'] ul li a") 
		List<WebElement> SideMenuItem1;
		
		@FindBy(css="li[data-name='2'] ul li a") 
		List<WebElement> SideMenuItem2;
		
		
		//Initializing the Page Objects:
		public HomePage() {
			PageFactory.initElements(driver, this);
		}
		
		//Actions:
		public String validateHomePageTitle(){
			return driver.getTitle();
		}
		
		public void MenuFinder(String Title) {
			for(int i=0; i < SideMenuTitle.size() ; i ++) {
				String name = SideMenuTitle.get(i).getText();
				if(name.contains(Title)) {
					int j = i + 1;
					WebElement menuObject = driver.findElement(By.xpath("//div[contains(@class,'sidebar')]/nav/ul/li["+j+"]/a"));
					click(menuObject);
					break;
				}
			}
		}
		
		public void MenuItemFinder(String item, String category) {
			List<WebElement> itemList = null;
			
			switch (category) {
			case "1":
				itemList = SideMenuItem1;
				break;
			case "2":
				itemList = SideMenuItem2;
				break;	
			}
			
			for(int i=0; i < itemList.size() ; i ++) {
				String name = itemList.get(i).getText();
				if(name.contains(item)) {
					int j = i + 1;
					WebElement itemObject = driver.findElement(By.xpath("//div[contains(@class,'sidebar')]/nav/ul/li["+category+"]/ul/li["+j+"]/a"));
					click(itemObject);
					break;
				}
			}
			
		}
		
		public ExtraPTPage clickOnExtraPT(String item, String category) {
			MenuItemFinder(item, category);
			return new ExtraPTPage();
		}
		
		public ProductSettingPage clickOnProductSetting(String item, String category) {
			MenuItemFinder(item, category);
			return new ProductSettingPage();
		}
		
		public PTSettingPage clickOnPTSetting(String item, String category) {
			MenuItemFinder(item, category);
			return new PTSettingPage();
		}

}
