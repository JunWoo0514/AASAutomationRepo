package AASAutomation.test;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import AASAutomation.base.TestBase;
import AASAutomation.pages.HomePage;
import AASAutomation.pages.LoginPage;
import AASAutomation.pages.PTSettingPage;
import AASAutomation.pages.ProductSettingPage;
import AASAutomation.util.AASUtil;

public class PTSettingTest extends TestBase{
	
	LoginPage loginPage;
	HomePage homePage;
	PTSettingPage ptSettingPage;
	AASUtil AASUtil;
	String menu;
	String item;
	String category;
	String active, disable;
	String WalletType;
	String TestAccount;
	String SMA,MA,AG;
	String CAPositivePT,CANegativePT01,CANegativePT02;
	String SMAPositivePT,SMANegativePT01,SMANegativePT02;
	String MAPositivePT,MANegativePT;
	double initialGPT, initialEPT, newGPT, finalGPT, finalEPT, maxPT;
	double maxCAPT, maxSMAPT, maxMAPT;
	List<List<String>> ProductDataList = new ArrayList<List<String>>();
	
	public PTSettingTest(){
		super();
	}
	
	@DataProvider(name = "RealTimeProductData")
	public Object[][] getProductData() {
		Object[][] data = new Object[ProductDataList.size()][4];
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
		item = prop.getProperty("PTSetting");
		category = prop.getProperty("ProductCategory");
		active = prop.getProperty("active");
		disable = prop.getProperty("disable");
		//CA Tier Test PT data declare
		CAPositivePT = prop.getProperty("CA_Positive_GPT");
		CANegativePT01 = prop.getProperty("CA_Negative_GPT1");
		CANegativePT02 = prop.getProperty("CA_Negative_GPT2");
		//SMA Tier Test PT data declare
		SMAPositivePT = prop.getProperty("SMA_Positive_GPT");
		SMANegativePT01 = prop.getProperty("SMA_Negative_GPT1");
		SMANegativePT02 = prop.getProperty("SMA_Negative_GPT2");
		//SMA Tier Test PT data declare
		MAPositivePT = prop.getProperty("MA_Positive_GPT");
		MANegativePT = prop.getProperty("MA_Negative_GPT");
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
		ptSettingPage = new PTSettingPage();
		loginPage = new LoginPage();
		homePage = loginPage.loginAction();
		homePage.MenuFinder(menu);
		ptSettingPage = homePage.clickOnPTSetting(item,category);
		
	}
	
	@Test(priority=1)
    public void ProceedToSMA(ITestContext context) throws InterruptedException {
		context.setAttribute("Steps", "1");
		context.setAttribute("Process", "Click down to SMA Tier");
		ptSettingPage.findTestingAccount(SMA,WalletType);
    }
	
	@Test(priority=2)
    public void SectionSetUpSMA(ITestContext context) {
		context.setAttribute("Steps", "1");
		context.setAttribute("Process", "Get SMA Tier Active Product Data");
		ProductDataList = null;
		ProductDataList = ptSettingPage.getALLProductList();
    }
	
	///////CA Tier Test///////
	@Test(priority=3, dataProvider = "RealTimeProductData")
	public void PTSettingCAPositiveTest01(String prdName, String prdType, String GivenPT, String EffPT, ITestContext context) throws InterruptedException {
		context.setAttribute("Steps", "3");
		initialGPT = Double.parseDouble(GivenPT);
		initialEPT = Double.parseDouble(EffPT);
		maxCAPT = initialGPT + initialEPT;
		context.setAttribute("Process1", WalletType + "Wallet Test  - CA Tier Test On Product : " + prdName + " initial Given PT Value : " + GivenPT 
							+ ", initial Effective PT Value : " + EffPT);
		context.setAttribute("Process2", "Test with Product : " + prdName + " with PT Value : " + CAPositivePT);
		ptSettingPage.PTSettingTestSingle(prdName, CAPositivePT, prdType);
		String[] ptResult = ptSettingPage.getProductPT(prdName, prdType);
		finalGPT = Double.parseDouble(ptResult[0]);
		finalEPT = Double.parseDouble(ptResult[1]);
		maxPT = finalGPT + finalEPT;
		context.setAttribute("Result", "Result expected value : " + CAPositivePT + " and received value : " + finalGPT);
		Assert.assertEquals(maxCAPT,maxPT);
		Assert.assertEquals(CAPositivePT,finalGPT);
	}

}
