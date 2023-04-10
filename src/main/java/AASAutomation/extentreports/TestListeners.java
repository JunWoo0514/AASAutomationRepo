package AASAutomation.extentreports;


import java.io.File;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import AASAutomation.base.TestBase;

public class TestListeners implements ITestListener{
	
	private static ExtentReports extent = ExtentManager.createInstance();
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

	public void onTestStart(ITestResult result) {
		ExtentTest testName = extent.createTest(result.getTestClass().getName() + " :: " 
												+ result.getMethod().getMethodName()); 
		extentTest.set(testName);
		
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		String className = result.getTestClass().getName();
		String methodName = result.getMethod().getMethodName().toString();
		String NodeCount = (String) result.getTestContext().getAttribute("Steps");
		
		switch (NodeCount) {	
		case "1":
			extentTest.get().createNode(""+result.getTestContext().getAttribute("Process"));											
			break;	
		case "2":
			extentTest.get().createNode(""+result.getTestContext().getAttribute("Process"));		
			extentTest.get().createNode(""+result.getTestContext().getAttribute("Result"));	
			break;
		case "3":
			extentTest.get().createNode(""+result.getTestContext().getAttribute("Process1"));	
			extentTest.get().createNode(""+result.getTestContext().getAttribute("Process2"));	
			extentTest.get().createNode(""+result.getTestContext().getAttribute("Result"));	
		break;
		}
		
		String logText = "<b>Test Method " + result.getMethod().getMethodName() + " Successful</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		extentTest.get().log(Status.PASS, m);	
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String className = result.getTestClass().getName();
		String methodName = result.getMethod().getMethodName().toString();
		String NodeCount = (String) result.getTestContext().getAttribute("Steps");
		
		switch (NodeCount) {	
		case "1":
			extentTest.get().fail(""+result.getTestContext().getAttribute("Process"));											
			break;	
		case "2":
			extentTest.get().fail(""+result.getTestContext().getAttribute("Process"));		
			extentTest.get().fail(""+result.getTestContext().getAttribute("Result"));	
			break;
		case "3":
			extentTest.get().fail(""+result.getTestContext().getAttribute("Process1"));	
			extentTest.get().fail(""+result.getTestContext().getAttribute("Process2"));	
			extentTest.get().fail(""+result.getTestContext().getAttribute("Result"));	
		break;
		}
		
		String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		extentTest.get().fail("<details><summary><b><font color=red>" + 
							  "Exception Occured, click to see details:" + "</font></b></summary>" +
							  exceptionMessage.replaceAll(",", "<br>") + "</details> \n");
		
		//WebDriver driver = ((TestClassUsingListeners)result.getInstance()).driver;
		WebDriver driver = TestBase.driver;
		String path = takeScreenshot(driver, result.getMethod().getMethodName());
		try {
			extentTest.get().fail("<b><font color=red>" + "Screenshot of failure" + "</font></b>", 
								  MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		}catch (Exception e) {
			extentTest.get().fail("Test Failed, cannot attatch screenshot.");
		}
		
		String logText = "<b>Test Method " + methodName + " Failed</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
		extentTest.get().log(Status.FAIL, m);	
		
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		String logText = "<b>Test Method " + result.getMethod().getMethodName() + " Skipped</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.BLUE);
		extentTest.get().log(Status.SKIP, m);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		if(extent != null) {
			extent.flush();
		}
		
	}
	
	public String takeScreenshot(WebDriver driver, String methodName) {
		String filename = getScreenshotName(methodName);
		
		String directory = System.getProperty("user.dir")+"/screenshots/"; 
		new File(directory).mkdirs();
		String path = directory + filename;
		
		try {
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(path));
			System.out.println("***************************");
			System.out.println("Screenshot stored at " + path);
			System.out.println("***************************");
		}catch (Exception e){
			e.printStackTrace();
		}
		return path;
	}
	
	public static String getScreenshotName(String methodName) {
		Date d = new Date();
		String filename = methodName + "_" +d.toString().replace(":", "_").replace(" ", "_") + ".png";
		return filename;
	}

}
