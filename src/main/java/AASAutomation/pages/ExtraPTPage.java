package AASAutomation.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.sun.tools.javac.code.Attribute.Array;

import AASAutomation.base.TestBase;

public class ExtraPTPage extends TestBase{
	
	//Page Factory
	//Function Button Element
	@FindBy(id="ag_code") 
	WebElement filterName;
	
	@FindBy(xpath="//div[contains(@class,'card')]/form/div[2]/div/button") 
	WebElement filterBtn;

	@FindBy(css="i[title='Edit']") 
	WebElement editBtn;
	
	@FindBy(css="i[title='Update']") 
	WebElement saveBtn;
	
	@FindBy(id="wallet_type") 
	WebElement walletSelect;
	
	@FindBy(css="i[title='Cancel']") 
	WebElement cancelBtn;
	
	//Tab Element
	@FindBy(id="tab1") 
	WebElement LCTAB;
	
	@FindBy(id="tab2") 
	WebElement S1TAB;
	
	@FindBy(id="tab3") 
	WebElement SBTAB;
	
	@FindBy(id="tab4") 
	WebElement S2TAB;
	
	@FindBy(css="th") 
	List<WebElement> products;
	
	@FindBy(css="tr td") 
	List<WebElement> ExtraPTView;
	
	@FindBy(css="tr td input") 
	List<WebElement> ExtraPTInput;
	
	By walletBy = By.id("wallet_type");
	
	String ModalBtnCSS = "button[class='btn btn-sm btn-secondary']";
	
	By modalBtn = By.id("button[class='btn btn-sm btn-secondary']");
	
	By editBy = By.cssSelector("i[title='Edit']");
	
	@FindBy(css="button[class='btn btn-sm btn-secondary']") 
	WebElement ModalConfirmBtn;
	
	//List<WebElement> products = driver.findElements(By.cssSelector("th"));
	
	//Initializing the Page Objects:
	public ExtraPTPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void findTestingAccount(String agCode, String walletType) {
		waitVisibilityLocate(walletBy);
		selectItem(walletSelect,walletType);
		clearText(filterName);
		writeText(filterName, agCode);
		waitVisibilityLocate(editBy);
		click(filterBtn);
	}
	
	public void ExtraPTSingleTest(String prdType, String prdName) throws InterruptedException{
		waitVisibilityLocate(editBy);
		this.checkProductTypeAuto(Integer.parseInt(prdType));
		click(editBtn);
		this.updateSingleExtraPT(prdName);
		click(saveBtn);
		CustomWaitClick(ModalConfirmBtn, ModalBtnCSS);
	}
	
	public void ExtraPTMultipleTest( ) throws InterruptedException {
		waitVisibilityLocate(editBy);
		click(editBtn);
		this.updateAllExtraPT();
		click(saveBtn);
		CustomWaitClick(ModalConfirmBtn, ModalBtnCSS);
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
	
	public List<List<String>> getALLProductList() {
		List<List<String>> elementDataList = new ArrayList<List<String>>();
		int type = 1;
		for (int i = 4; i < products.size(); i++) {
			boolean thisFieldStatus = false;
			do {
				this.checkProductTypeAuto(type);
				int w = i + 1;
				String ProductName = products.get(i).getText();
				WebElement ptOrigin = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tbody/tr/td["+w+"]"));
				thisFieldStatus = elementDisplayCheck(ptOrigin);
				if (thisFieldStatus == true) {
					String innitialPT = readText(ptOrigin);
					System.out.println("List pt value : " + innitialPT);
					System.out.println("List Product : " + ProductName);
					System.out.println("List type : " + type);
					elementDataList.add(Arrays.asList(ProductName, Integer.toString(type), innitialPT));
				}else {
					type++;
				}
			}while(thisFieldStatus == false);
			int j = i + 2;
			if(j <= products.size()) {
				WebElement nextInput = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table//thead[1]/tr[1]/th["+j+"]"));
				boolean nextFieldStatus = elementDisplayCheck(nextInput);
				if (nextFieldStatus == false) {
					type ++;
				}
			}
		}
		return elementDataList;	
	}

	
	public void updateSingleExtraPT(String prdName) {
		for(int i=4; i < products.size() ; i ++) {
			String name = products.get(i).getText();
			if(name.contains(prdName)) {
				int j = i + 1;
				WebElement ptInput = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tbody/tr/td["+j+"]/input"));
				OnclickUP(ptInput);
				break;
			}
		}
	}

	public String getExtraPT(String prdType, String prdName) {
		waitVisibilityLocate(editBy);
		this.checkProductTypeAuto(Integer.parseInt(prdType));
		String newPT = "";
		for(int i=0; i < products.size() ; i ++) {
			String name = products.get(i).getText();
			if(name.contains(prdName)) {
				int j = i + 1;
				WebElement ptInput = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tbody/tr/td["+j+"]"));
				newPT = readText(ptInput);
				break;
			}
		}
		
		return newPT;
	}
	
	public void updateAllExtraPT() {
		int type = 1;
		for(int i=0; i < ExtraPTInput.size() ; i ++) {
			boolean thisFieldStatus = false;
			do {
				this.checkProductTypeAuto(type);
				int j = i + 5;
				WebElement ptInputOrigin = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tbody/tr/td["+j+"]/input"));
				thisFieldStatus = elementDisplayCheck(ptInputOrigin);
				if(thisFieldStatus == true) {
					WebElement ptInput = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tbody/tr/td["+j+"]/input"));
					OnclickDOWN(ptInput);
					int k = j + 1;
					if(k <= products.size()) {
						WebElement nextInput = driver.findElement(By.xpath("//div[contains(@class,'table-responsive')]/table/tbody/tr/td["+k+"]/input"));
						boolean nextFieldStatus = elementDisplayCheck(nextInput);
						if (nextFieldStatus == false) {
							type ++;
						}
					}
					
				}else {
					type ++;
				}
			}while(thisFieldStatus == false);
			
			
		}
	}
	
}