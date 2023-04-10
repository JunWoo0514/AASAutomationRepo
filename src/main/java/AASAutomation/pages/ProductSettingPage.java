package AASAutomation.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AASAutomation.base.TestBase;

public class ProductSettingPage extends TestBase{
	
	//Page Factory
	@FindBy(id="f_name") 
	WebElement filterName;
	
	@FindBy(xpath="//div[contains(@class,'form-group ml-2')]/button") 
	WebElement filterBtn;
	
	@FindBy(id="wallet_type") 
	WebElement walletSelect;
	
	//Breadcrumb
	@FindBy(css="li[id='breadcrumb-ca'] a") 
	WebElement CAbreadcrumb;
	
	//Select (Account Status Combo Box) Element
	@FindBy(id="f_status") 
	WebElement accountSelect;
	
	//Downline Account Link
	@FindBy(xpath="//div[contains(@class,'table-responsive')]/table/tbody/tr/td[2]/a") 
	WebElement DownlineAccountLink;
	
	//Check Box
	@FindBy(id="check_1") 
	WebElement accountBox;
	
	//Apply Button
	@FindBy(id="btnSubmit") 
	WebElement submitBtn;
	
	@FindBy(css="button[class='btn btn-sm btn-secondary']") 
	WebElement ModalConfirmBtn;
	
	@FindBy(css="th") 
	List<WebElement> products;
	
	By walletBy = By.id("wallet_type");
	
	By checkBoxBy = By.cssSelector("input[id='check_1']");
	
	By submitBy = By.cssSelector("#btnSubmit");
	
	By tfootBy = By.cssSelector("tfoot");
	
	String ModalBtnCSS = "button[class='btn btn-sm btn-secondary']";
	
	By FilterBy = By.cssSelector("button[class='btn btn-sm btn-success']");
	
	By modalBtn = By.id("button[class='btn btn-sm btn-secondary']");
	
	public ProductSettingPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void DownlineClick() {
		waitVisibilityLocate(checkBoxBy);
		click(DownlineAccountLink);
	}
	
	public void CABreadCrumbClick() {
		click(CAbreadcrumb);
	}
	
	public void findTestingAccount(String agCode, String walletType) throws InterruptedException {
		waitVisibilityLocate(walletBy);
		selectItem(walletSelect,walletType);
		clearText(filterName);
		writeText(filterName, agCode);
		waitVisibilityLocate(FilterBy);
		clickWait(filterBtn);
		//CustomWaitClick(filterBtn, FilterBtnCSS);
		System.out.println("DID CLICK!!! ");
		waitVisibilityLocate(submitBy);
	}
	
	public void ProductSettingSingleTest(String prdName, String prdStatus) throws InterruptedException {
		waitVisibilityLocate(submitBy);
		waitVisibilityLocate(checkBoxBy);
		click(accountBox);
		this.updateSingleProductSetting(prdName, prdStatus);
		click(submitBtn);	
		CustomWaitClick(ModalConfirmBtn, ModalBtnCSS);
	}
	
	public void ProductSettingMultipleTest(String prdStatus) throws InterruptedException {
		waitVisibilityLocate(submitBy);
		click(accountBox);
		System.out.println("Group Update Status : " + prdStatus);
		waitVisibilityLocate(tfootBy);
		this.updateAllProductSetting(prdStatus);
		click(submitBtn);
		//Actions actions = new Actions(driver);
		//actions.moveToElement(submitBtn).click().build().perform();
		CustomWaitClick(ModalConfirmBtn, ModalBtnCSS);
	}
	
	public void updateSingleProductSetting(String prdName, String prdStatus) {
		waitVisibilityLocate(submitBy);
		System.out.println("Check State : " + prdName + prdStatus);
		System.out.println("Check State 2 : " + products.size());
		for(int i=4; i < products.size() ; i ++) {
			String name = products.get(i).getText();
			if(name.contains(prdName)) {
				System.out.println("Check State 3 : " + i);
				int j = i + 1;
				WebElement ProductSelect = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tfoot/tr/td["+j+"]/select"));
				selectItem(ProductSelect, prdStatus);
				break;
			}
		}
	}
	
	public void updateAllProductSetting(String prdStatus) {
		waitVisibilityLocate(submitBy);
		System.out.println("Check State : " + prdStatus);
		System.out.println("Check State 2 : " + products.size());
		for(int i=4; i < products.size() ; i ++) {
			waitVisibilityLocate(submitBy);
			boolean thisFieldStatus = false;
			System.out.println("Group Update i : " + i);
			int j = i + 1;
				WebElement productSelect = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tfoot/tr/td["+j+"]/select"));
				thisFieldStatus = elementDisplayCheck(productSelect);
				if(thisFieldStatus == true) {
					System.out.println("Group Clicked");
					selectItem(productSelect, prdStatus);
				}
		}
		//waitVisibilityLocate(submitBy);
		//click(submitBtn);
	}
	
	public String getProductStatus(String prdName) {
		waitVisibilityLocate(submitBy);
		String productStatus = "";
		for(int i=0; i < products.size() ; i ++) {
			waitVisibilityLocate(submitBy);
			String name = products.get(i).getText();
			if(name.contains(prdName)) {
				int j = i + 1;
				WebElement productStatuInput = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tbody/tr/td["+j+"]/font"));
				productStatus = readText(productStatuInput);
				break;
			}
		}	
		return productStatus;
	}
	
	public List<List<String>> getALLProductList() {
		waitVisibilityLocate(submitBy);
		System.out.println("List size set up : " + products.size());
		List<List<String>> elementDataList = new ArrayList<List<String>>();
		for (int i = 4; i < products.size(); i++) {
			waitVisibilityLocate(submitBy);
			boolean thisFieldStatus = false;
			int w = i + 1;
			do {
				System.out.println("W value : " + w);
				String ProductName = products.get(i).getText();
				WebElement statusOrigin = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tbody/tr/td["+w+"]/font"));
				thisFieldStatus = elementDisplayCheck(statusOrigin);
				System.out.println("List field status : " + thisFieldStatus);
				if (thisFieldStatus == true) {
					String innitialStatus = readText(statusOrigin);
					System.out.println("AG Code: " + products.get(1).getText());
					System.out.println("List i value : " + i);
					System.out.println("List Product : " + ProductName);
					System.out.println("List State : " + innitialStatus);
					elementDataList.add(Arrays.asList(ProductName, innitialStatus));
				}
			}while(thisFieldStatus == false);
			
		}
		return elementDataList;	
	}
		
}
