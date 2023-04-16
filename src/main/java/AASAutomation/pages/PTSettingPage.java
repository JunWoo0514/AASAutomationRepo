package AASAutomation.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import AASAutomation.base.TestBase;

public class PTSettingPage extends TestBase{
	
	//Page Factory
	@FindBy(id="f_name") 
	WebElement filterName;
	
	@FindBy(xpath="//div[contains(@class,'form-group ml-2')]/button") 
	WebElement filterBtn;
	
	//Breadcrumb
	@FindBy(xpath="//div[contains(@class,'card-header-headerbg')]//ol/li[2]/a") 
	WebElement CAbreadcrumb;
	
	//Select (Account Status Combo Box) Element
	@FindBy(id="f_status") 
	WebElement accountSelect;
	
	//Downline Account Link
	@FindBy(xpath="//div[contains(@class,'table-responsive')]/table/tbody/tr/td[2]/a") 
	WebElement DownlineAccountLink;
	
	//Check Box (only for single record filter used)
	@FindBy(id="check_3") 
	WebElement accountBox;
	
	@FindBy(id="wallet_type") 
	WebElement walletSelect;
	
	//Tab Element
	@FindBy(id="tab1") 
	WebElement LCTAB;
	
	@FindBy(id="tab2") 
	WebElement S1TAB;
	
	@FindBy(id="tab3") 
	WebElement SBTAB;
	
	@FindBy(id="tab4") 
	WebElement S2TAB;
	
	//Apply Button
	@FindBy(id="btnSubmit") 
	WebElement submitBtn;
	
	@FindBy(css="tfoot td") 
	List<WebElement> products;
	
	@FindBy(css="button[class='btn btn-sm btn-secondary']") 
	WebElement ModalConfirmBtn;

	//button[normalize-space()='OK']
	@FindBy(xpath="button[normalize-space()='OK']") 
	WebElement ModalOKBtn;
	
	By walletBy = By.id("wallet_type");
	By submitBy = By.id("btnSubmit");
	
	String ModalOKBtnXpath = "button[normalize-space()='OK']";
	
	
	//Initializing the Page Objects:
	public PTSettingPage(){
		PageFactory.initElements(driver, this);
	}
	
	//Actions:
	public String validateHomePageTitle(){
		return driver.getTitle();
	}
	
	
	public void findTestingAccount(String agCode, String walletType) throws InterruptedException  {
		waitVisibilityLocate(walletBy);
		selectItem(walletSelect,walletType);
		clearText(filterName);
		writeText(filterName, agCode);
		waitVisibilityLocate(submitBy);
		clickWait(filterBtn);
	}
	
	
	public void PTSettingTestSingle(String prdName, String givenPT, String prdType) throws InterruptedException {
		System.out.println("Reached Here!!!" + prdName + "  " + prdType );
		this.checkProductTypeAuto(Integer.parseInt(prdType));
		click(accountBox);
		this.updateSinglePTSetting(prdName, givenPT);
		click(submitBtn);
		CustomWaitClickXpath(ModalOKBtn, ModalOKBtnXpath);
		//driver.switchTo().alert().accept();
	}
	
	public void DownlineClick() {
		click(DownlineAccountLink);
	}
	
	public void CABreadCrumbClick() {
		click(CAbreadcrumb);
	}
	
	public void checkProductTypeAuto(int type) {
		switch (type) {
		case 1:
			click(LCTAB);
			break;
		case 2:
			click(SBTAB);
			break;
		case 3:
			click(S1TAB);
			break;
		case 4:
			click(S2TAB);
			break;
		}

	}
	
	public void updateSinglePTSetting(String prdName, String newPT) {
		waitVisibilityLocate(submitBy);
		System.out.println("Check State : " + prdName + newPT);
		System.out.println("Check State 2 : " + products.size());
		for(int i=4; i < products.size() ; i ++) {
			int j = i + 1;
			List<WebElement> prod = driver.findElements(By.xpath("//div[contains(@class,'table-responsive')]/table/tfoot/tr/td["+j+"]/select"));
			if (prod.size() > 0) {
				WebElement ProductSelect = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tfoot/tr/td["+j+"]/select"));
				String ProductName = selectItemFirstItem(ProductSelect);
				System.out.println("I Value : " + j);
				System.out.println("Product Name : " + ProductName);
				if(ProductName.contains(prdName)) {
					selectItem(ProductSelect,newPT);	
				break;
				}
			}
		}
	}
	
	public String[] getProductPT(String prdName, String prdType) {
		waitVisibilityLocate(submitBy);
		String[] ptArray = new String[2];
		this.checkProductTypeAuto(Integer.parseInt(prdType));
		
		String newPT = "";
		for(int i=4; i < products.size() ; i ++) {
			boolean thisFieldStatus = false;
			//List<WebElement> prod = driver.findElements(By.xpath("//div[contains(@class,'table-responsive')]/table/tfoot/tr/td["+i+"]/select"));
			//WebElement productOrigin = driver.findElements(By.xpath("//div[contains(@class,'table-responsive')]/table/tfoot/tr/td["+i+"]/select"));
			WebElement ProductSelect = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tfoot/tr/td["+i+"]/select"));
			thisFieldStatus = elementDisplayCheck(ProductSelect);
			System.out.println("List field status : " + thisFieldStatus);
			if(thisFieldStatus == true) {
			//if (prod.size() > 0) {
				//WebElement ProductSelect = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tfoot/tr/td["+i+"]/select"));
				String ProductName = selectItemFirstItem(ProductSelect);
				System.out.println("Get Product Product Name : " + ProductName);
				if(ProductName.contains(prdName)) {
				    System.out.println("Check Updated PT Part");
				    System.out.println("I Value : " + i);
					String gpt = driver.findElements(By.xpath("//div[contains(@class,'table-responsive')]/table/tbody/tr[1]/td")).get(i+1).getText();
					String ept = driver.findElements(By.xpath("//div[contains(@class,'table-responsive')]/table/tbody/tr[1]/td")).get(i+2).getText();
					ptArray[0] = ept;
					ptArray[1] = "World";
					System.out.println("Given PT Value : " + gpt);
					System.out.println("EFF PT Value : " + ept);
				break;
				}
			}
			
			
		}
		
		return ptArray;
	}
	
	public List<List<String>> getALLProductList() {
		waitVisibilityLocate(submitBy);
		List<List<String>> elementDataList = new ArrayList<List<String>>();
		int type = 1;
		
		for(int i=4; i < products.size() ; i ++) {
			boolean thisFieldStatus = false;
			do {
				this.checkProductTypeAuto(type);
				int w = i + 1;
				//String ProductName = products.get(i).getText();
				WebElement ptOrigin = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tfoot/tr/td["+w+"]"));
				thisFieldStatus = elementDisplayCheck(ptOrigin);
				if (thisFieldStatus == true) {
					boolean prodFieldStatus = false;
					System.out.println("Cell "+w+" : Element exists");
					//List<WebElement> prod = driver.findElements(By.xpath("//div[contains(@class,'table-responsive')]/table/tfoot/tr/td["+i+"]/select"));
					
					//prodFieldStatus = elementDisplayCheck(ProductSelect);
					String checkElementXpath = "//div[contains(@class,'table-responsive')]/table/tfoot/tr/td["+i+"]/select";
					prodFieldStatus = checkElementExist(checkElementXpath);
					if(prodFieldStatus == true) {
					//if (prod.size() > 0) {
						//WebElement ProductSelect = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tfoot/tr/td["+i+"]/select"));
						WebElement ProductSelect = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tfoot/tr/td["+i+"]/select"));
						String ProductName = selectItemFirstItem(ProductSelect);
						System.out.println("I Value : " + i + "W Value : " + w);
						System.out.println("Product Name : " + ProductName);
						String gpt = driver.findElements(By.xpath("//div[contains(@class,'table-responsive')]/table/tbody/tr[1]/td")).get(i+1).getText();
						String ept = driver.findElements(By.xpath("//div[contains(@class,'table-responsive')]/table/tbody/tr[1]/td")).get(i+2).getText();
						System.out.println("Given PT Value : " + gpt);
						System.out.println("EFF PT Value : " + ept);
						elementDataList.add(Arrays.asList(ProductName, Integer.toString(type), gpt, ept));
					}
				}else {
					type++;
				}
			}while(thisFieldStatus == false);
			int j = i + 2;
			if(j <= products.size()) {
				WebElement nextInput = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tfoot/tr/td["+j+"]"));
				boolean nextFieldStatus = elementDisplayCheck(nextInput);
				if (nextFieldStatus == false) {
					type ++;
				}
			}
		}

		return elementDataList;	
	}

	
}
