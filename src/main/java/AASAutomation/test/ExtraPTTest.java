package AASAutomation.test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import AASAutomation.base.TestBase;
import AASAutomation.pages.*;
import AASAutomation.util.AASUtil;

public class ExtraPTTest extends TestBase{
	
	LoginPage loginPage;
	HomePage homePage;
	ExtraPTPage extraPtPage;
	AASUtil AASUtil;
	String menu;
	String item;
	String category;
	
	List<List<String>> ProductDataList = new ArrayList<List<String>>();
	
	public ExtraPTTest(){
		super();
	}
	
	@DataProvider(name = "RealTimeProductData")
	public Object[][] getProductData() {
		Object[][] data = new Object[ProductDataList.size()][3];
		// Iterate through the 2D list using nested loops
		for (int i = 0; i < ProductDataList.size(); i++) {
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
		item = prop.getProperty("ExtraPT");
		category = prop.getProperty("ProductCategory");
		AASUtil = new AASUtil();
		extraPtPage = new ExtraPTPage();
		loginPage = new LoginPage();
		homePage = loginPage.loginAction();
		homePage.MenuFinder(menu);
		extraPtPage = homePage.clickOnExtraPT(item,category);
		extraPtPage.findTestingAccount(prop.getProperty("AG1"),prop.getProperty("Seamless"));
	}
	
	@Test(priority=1)
    public void SeamlessSectionSetUp(ITestContext context) {
		context.setAttribute("Steps", "1");
		context.setAttribute("Process", "Get Active Product Data");
		ProductDataList = null;
		ProductDataList = extraPtPage.getALLProductList();
    }
	
	@Test(priority=2, dataProvider = "RealTimeProductData")
	public void SeamlessExtraPTSingleUpdate(String prdName, String prdType, String innitialExtraPT, ITestContext context) throws InterruptedException {
		double expectedPT = Double.parseDouble(innitialExtraPT) + 0.1;
		context.setAttribute("Steps", "2");
		context.setAttribute("Process", "Test with Product : " + prdName + " with PT Value : " + expectedPT);
		extraPtPage.ExtraPTSingleTest(prdType, prdName);
		DecimalFormat formatter = new DecimalFormat("#0.00");
		String result = extraPtPage.getExtraPT(prdType, prdName);
		context.setAttribute("Result", "Result expected PT : " + formatter.format(expectedPT) + " and received PT : " + result);
		Assert.assertEquals(result,formatter.format(expectedPT));
	}
	
	@Test(priority=3)
	public void SeamlessExtraPTGroupUpdate(ITestContext context) throws InterruptedException {
		extraPtPage.findTestingAccount(prop.getProperty("AG1"),prop.getProperty("Seamless"));
		context.setAttribute("Steps", "1");
		context.setAttribute("Process", "Test Set All Product with Extral PT Value : 1.00 ");
		extraPtPage.ExtraPTMultipleTest();
	}
	
	@Test(priority=4, dataProvider = "RealTimeProductData")
	public void SeamlessExtraPTGroupCheck(String prdName, String prdType, String innitialExtraPT, ITestContext context) throws InterruptedException {
		context.setAttribute("Steps", "2");
		context.setAttribute("Process", "Extra PT Test On Check on Product : " + prdName + " with PT Value : " + innitialExtraPT);		
		double expectedPT = Double.parseDouble(innitialExtraPT);
		DecimalFormat formatter = new DecimalFormat("#0.00");
		String result = extraPtPage.getExtraPT(prdType, prdName);
		context.setAttribute("Result", "Result expected PT : " + formatter.format(expectedPT) + " and received PT : " + result);
		Assert.assertEquals(result,formatter.format(expectedPT));
	}
	
	@Test(priority=5)
	public void WalletSectionSetUp(ITestContext context) {
		context.setAttribute("Steps", "1");
		context.setAttribute("Process", "Get Active Product Data");
		extraPtPage.findTestingAccount(prop.getProperty("AGT"),prop.getProperty("Transfer"));
		ProductDataList = null;
		ProductDataList = extraPtPage.getALLProductList();
	}
	
	@Test(priority=6, dataProvider = "RealTimeProductData")
	public void WalletExtraPTSingleUpdate(String prdName, String prdType, String innitialExtraPT, ITestContext context) throws InterruptedException {
		double expectedPT = Double.parseDouble(innitialExtraPT) + 0.1;
		context.setAttribute("Steps", "2");
		context.setAttribute("Process", "Test with Product : " + prdName + " with PT Value : " + expectedPT);
		extraPtPage.ExtraPTSingleTest(prdType, prdName);
		DecimalFormat formatter = new DecimalFormat("#0.00");
		String result = extraPtPage.getExtraPT(prdType, prdName);
		context.setAttribute("Result", "Result expected PT : " + formatter.format(expectedPT) + " and received PT : " + result);
		Assert.assertEquals(result,formatter.format(expectedPT));
	}
	
	@Test(priority=7)
	public void WalletExtraPTGroupUpdate(ITestContext context) throws InterruptedException {
		extraPtPage.findTestingAccount(prop.getProperty("AGT"),prop.getProperty("Transfer"));
		//String newPT = prop.getProperty("CA_Innitial_GPT");
		context.setAttribute("Steps", "1");
		context.setAttribute("Process", "Test Set All Product with Extral PT Value : 1.00");
		extraPtPage.ExtraPTMultipleTest();
	}

	@Test(priority=8, dataProvider = "RealTimeProductData")
	public void WalletExtraPTGroupCheck(String prdName, String prdType, String innitialExtraPT, ITestContext context) throws InterruptedException {
		context.setAttribute("Steps", "2");
		context.setAttribute("Process", "Extra PT Test On Check on Product : " + prdName + " with PT Value : " + innitialExtraPT);		
		double expectedPT = Double.parseDouble(innitialExtraPT);
		DecimalFormat formatter = new DecimalFormat("#0.00");
		String result = extraPtPage.getExtraPT(prdType, prdName);
		context.setAttribute("Result", "Result expected PT : " + formatter.format(expectedPT) + " and received PT : " + result);
		Assert.assertEquals(result,formatter.format(expectedPT));
	}
	
}
