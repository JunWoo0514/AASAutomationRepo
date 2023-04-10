package AASAutomation.extentreports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

private static ExtentReports extent;
private static Properties prop;

	//Gets data from properties file
	public ExtentManager(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/AASAutomation/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ExtentReports createInstance() {
		String filename = getReportName();
		String directory = System.getProperty("user.dir")+"/reports/";
		new File(directory).mkdirs();
		String path = directory + filename;
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter(path);
		//htmlReporter = new ExtentSparkReporter("./reports/AASTestReport.html");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setDocumentTitle("AASAutomationSmokeTestReport");
		htmlReporter.config().setReportName("AAS Automation Test Result");
		//htmlReporter.setAnalysisStrategy(AnalysisStrategy.SUITE);
		htmlReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.setSystemInfo("Provider","Ciphertech AAS QA Team");
		extent.setSystemInfo("Browser", "Chrome");
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
		extent.setSystemInfo("Machine Name : ", System.getProperty("machine.name"));
		extent.setSystemInfo("IP Address : ", System.getProperty("machine.address"));
		extent.attachReporter(htmlReporter);
		
		return extent;
	}
	
	public static String getReportName() {
		Date d = new Date();
		
		String filename = "AASAutomationReport_"+d.toString().replace(":", "_").replace(" ", "_")+".html";
		return filename;
	}
	
}
