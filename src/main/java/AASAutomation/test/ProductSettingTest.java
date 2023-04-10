package AASAutomation.test;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import AASAutomation.base.TestBase;
import AASAutomation.pages.*;
import AASAutomation.util.*;

public class ProductSettingTest extends TestBase{
	
	LoginPage loginPage;
	HomePage homePage;
	ProductSettingPage productSettingPage;
	AASUtil AASUtil;
	String menu;
	String item;
	String category;
	String active, disable;
	String WalletType;
	String TestAccount;
	String SMA,MA,AG;
	List<List<String>> ProductDataList = new ArrayList<List<String>>();
	
	public ProductSettingTest(){
		super();
	}
	
	@DataProvider(name = "RealTimeProductData")
	public Object[][] getProductData() {
		Object[][] data = new Object[ProductDataList.size()][2];
		// Iterate through the 2D list using nested loops
		
		for (int i = 0; i < ProductDataList.size(); i++) {
			System.out.println("Product Data Size : " + ProductDataList.size());
		    List<String> innerList = ProductDataList.get(i);
		    for (int j = 0; j < innerList.size(); j++) {
		    	String value = innerList.get(j);
		    	data[i][j] = value;
		    }
		}
        return data;
	}
	
	@BeforeClass
	public void setUp(){
		initialization();
		menu = prop.getProperty("Product");
		item = prop.getProperty("ProductSetting");
		category = prop.getProperty("ProductCategory");
		WalletType = System.getProperty("walletType")!=null ? System.getProperty("walletType") : prop.getProperty("walletType");
		if(WalletType.equals(prop.getProperty("Seamless"))) {
			TestAccount = prop.getProperty("SeamlessAccount");
		}else if(WalletType.equals(prop.getProperty("Transfer"))) {
			TestAccount = prop.getProperty("WalletAccount");
		}
		SMA = TestAccount.substring(0,3);
		MA = TestAccount.substring(0,5);
		AG = TestAccount;
		AASUtil = new AASUtil();
		productSettingPage = new ProductSettingPage();
		loginPage = new LoginPage();
		homePage = loginPage.loginAction();
		homePage.MenuFinder(menu);
		productSettingPage = homePage.clickOnProductSetting(item,category);
		//productSettingPage.findTestingAccount(SMA,WalletType);
		active = prop.getProperty("active");
		disable = prop.getProperty("disable");
	}
	
	@Test(priority=1)
    public void ProceedToSMA(ITestContext context) throws InterruptedException {
		context.setAttribute("Steps", "1");
		context.setAttribute("Process", "Click down to SMA Tier");
		productSettingPage.findTestingAccount(SMA,WalletType);
    }
	
	
	@Test(priority=2)
    public void SectionSetUpSMA(ITestContext context) {
		context.setAttribute("Steps", "1");
		context.setAttribute("Process", "Get SMA Tier Active Product Data");
		ProductDataList = null;
		ProductDataList = productSettingPage.getALLProductList();
    }
	
	@Test(priority=3, dataProvider = "RealTimeProductData")
	public void ProductSettingSingleUpdate_SMA(String prdName, String innitialStatus, ITestContext context) throws InterruptedException {
		System.out.println("Get in 003 : "+innitialStatus);
		String newStatus = "";
		if(innitialStatus.equals(active)) {
			newStatus = disable;
		}else if(innitialStatus.equals(disable)) {
			newStatus = active;
		}
		System.out.println("Get in 004 : " + newStatus);
		context.setAttribute("Steps", "2");
		context.setAttribute("Process", WalletType + "Wallet Test - SMA Tier Test with Product : " + prdName + " with Status Value : " + newStatus);
		productSettingPage.ProductSettingSingleTest(prdName,newStatus);
		String result = productSettingPage.getProductStatus(prdName);
		context.setAttribute("Result", "Result expected value : " + newStatus + " and received value : " + result);
		Assert.assertEquals(result,newStatus);
	}
	
	@Test(priority=4)
	public void ProductSettingGroupUpdate_SMA(ITestContext context) throws InterruptedException {
		//productSettingPage.findTestingAccount(SMA,WalletType);
		context.setAttribute("Steps", "1");
		context.setAttribute("Process", WalletType + "Wallet Test - SMA Tier Test All Product with status Value : Positive");
		productSettingPage.ProductSettingMultipleTest(prop.getProperty("active"));
	}
	
	@Test(priority=5, dataProvider = "RealTimeProductData")
	public void ProductSettingGroupCheck_SMA(String prdName, String innitialStatus, ITestContext context) throws InterruptedException {
		context.setAttribute("Steps", "2");
		context.setAttribute("Process", WalletType + "Wallet Test - SMA Tier Check on Product : " + prdName + " with Status Value : " + active);		
		String result = productSettingPage.getProductStatus(prdName);
		context.setAttribute("Result", "Result expected value : " + active + " and received value : " + result);
		Assert.assertEquals(result, active);
	}
	
	@Test(priority=6)
	public void ProceedToMA(ITestContext context) throws InterruptedException{
		context.setAttribute("Steps", "1");
		context.setAttribute("Process", "Click down to MA Tier");
		productSettingPage.DownlineClick();
		productSettingPage.findTestingAccount(MA,WalletType);
	}
	
	@Test(priority=7)
	public void SectionSetUpMA(ITestContext context){
		context.setAttribute("Steps", "1");
		context.setAttribute("Process", "Get MA Tier Active Product Data");
		ProductDataList = null;
		ProductDataList = productSettingPage.getALLProductList();
	}
	
	@Test(priority=8, dataProvider = "RealTimeProductData")
	public void ProductSettingSingleUpdate_MA(String prdName, String innitialStatus, ITestContext context) throws InterruptedException {
		System.out.println("Get in 003 : "+innitialStatus);
		String newStatus = "";
		if(innitialStatus.equals(active)) {
			newStatus = disable;
		}else if(innitialStatus.equals(disable)) {
			newStatus = active;
		}
		System.out.println("Get in 004 : " + newStatus);
		context.setAttribute("Steps", "2");
		context.setAttribute("Process", WalletType + "Wallet Test - MA Tier Test with Product : " + prdName + " with Status Value : " + newStatus);
		productSettingPage.ProductSettingSingleTest(prdName,newStatus);
		String result = productSettingPage.getProductStatus(prdName);
		context.setAttribute("Result", "Result expected value : " + newStatus + " and received value : " + result);
		Assert.assertEquals(result,newStatus);
	}
	
	@Test(priority=9)
	public void ProductSettingGroupUpdate_MA(ITestContext context) throws InterruptedException {
		//productSettingPage.findTestingAccount(MA,WalletType);
		context.setAttribute("Steps", "1");
		context.setAttribute("Process", WalletType + "Wallet Test - MA Tier Test All Product with status Value : Positive");
		productSettingPage.ProductSettingMultipleTest(prop.getProperty("active"));
	}
	
	@Test(priority=10, dataProvider = "RealTimeProductData")
	public void ProductSettingGroupCheck_MA(String prdName, String innitialStatus, ITestContext context) throws InterruptedException {
		context.setAttribute("Steps", "2");
		context.setAttribute("Process", WalletType + "Wallet Test - MA Tier Check on Product : " + prdName + " with Status Value : " + active);		
		String result = productSettingPage.getProductStatus(prdName);
		context.setAttribute("Result", "Result expected value : " + active + " and received value : " + result);
		Assert.assertEquals(result, active);
	}
	
	@Test(priority=11)
	public void ProceedToAG(ITestContext context) throws InterruptedException{
		context.setAttribute("Steps", "1");
		context.setAttribute("Process", "Click down to AG Tier");
		productSettingPage.DownlineClick();
		productSettingPage.findTestingAccount(AG,WalletType);
	}
	
	@Test(priority=12)
	public void SectionSetUpAG(ITestContext context){
		context.setAttribute("Steps", "1");
		context.setAttribute("Process", "Get AG Tier Active Product Data");
		ProductDataList = null;
		ProductDataList = productSettingPage.getALLProductList();
	}

	@Test(priority=13, dataProvider = "RealTimeProductData")
	public void ProductSettingSingleUpdate_AG(String prdName, String innitialStatus, ITestContext context) throws InterruptedException {
		System.out.println("Get in 003 : "+innitialStatus);
		String newStatus = "";
		if(innitialStatus.equals(active)) {
			newStatus = disable;
		}else if(innitialStatus.equals(disable)) {
			newStatus = active;
		}
		System.out.println("Get in 004 : " + newStatus);
		context.setAttribute("Steps", "2");
		context.setAttribute("Process", WalletType + "Wallet Test - AG Tier Test with Product : " + prdName + " with Status Value : " + newStatus);
		productSettingPage.ProductSettingSingleTest(prdName,newStatus);
		String result = productSettingPage.getProductStatus(prdName);
		context.setAttribute("Result", "Result expected value : " + newStatus + " and received value : " + result);
		Assert.assertEquals(result,newStatus);
	}
	
	@Test(priority=14)
	public void ProductSettingGroupUpdate_AG(ITestContext context) throws InterruptedException {
		//productSettingPage.findTestingAccount(SMA,WalletType);
		context.setAttribute("Steps", "1");
		context.setAttribute("Process", WalletType + "Wallet Test - AG Tier Test All Product with status Value : Positive");
		productSettingPage.ProductSettingMultipleTest(prop.getProperty("active"));
	}
	
	@Test(priority=15, dataProvider = "RealTimeProductData")
	public void ProductSettingGroupCheck_AG(String prdName, String innitialStatus, ITestContext context) throws InterruptedException {
		context.setAttribute("Steps", "2");
		context.setAttribute("Process", WalletType + "Wallet Test - AG Tier Check on Product : " + prdName + " with Status Value : " + active);		
		String result = productSettingPage.getProductStatus(prdName);
		context.setAttribute("Result", "Result expected value : " + active + " and received value : " + result);
		Assert.assertEquals(result, active);
	}
}
