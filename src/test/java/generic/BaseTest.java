package generic;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest implements IAutoConst {
	public WebDriver driver;
	public WebDriverWait wait;
	public static ExtentReports report;
	public ExtentTest test;

	@BeforeSuite
	public void initReport() {
		report = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter(REPORT_PATH);
		report.attachReporter(spark);
	}

	@AfterSuite
	public void publishReport() {
		report.flush();
	}

	@BeforeMethod
	public void createTest(Method testMethod) {
		String testname = testMethod.getName();
		test = report.createTest(testname);
	}

	@Parameters({ "config" })
	@BeforeMethod(alwaysRun = true)
	public void openApp(@Optional(CONFIG_PATH) String config) throws MalformedURLException {
		String browser = Utility.getProperty(config, "BROWSER");
		String grid = Utility.getProperty(config, "GRID");
		if (grid.equalsIgnoreCase("Yes")) {
			String remote = Utility.getProperty(config, "REMOTE_URL");
			URL remoteURL = new URL(remote);
			DesiredCapabilities capability = new DesiredCapabilities();
			capability.setBrowserName(browser);
			driver = new RemoteWebDriver(remoteURL, capability);
		} else {

			if (browser.equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			} else if (browser.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			}
		}
		driver.manage().window().maximize();

		String sITO = Utility.getProperty(config, "ITO");
		long ITO = Long.parseLong(sITO);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ITO));

		String sETO = Utility.getProperty(config, "ETO");
		long ETO = Long.parseLong(sETO);
		wait = new WebDriverWait(driver, Duration.ofSeconds(ETO));

		String APP_URL = Utility.getProperty(config, "APP_URL");
		driver.get(APP_URL);
	}

	@AfterMethod(alwaysRun = true)
	public void closeApp(ITestResult result, Method testMethod) {
		String testScriptName = testMethod.getName();

		int status = result.getStatus();
		if (status == 2) {
			try {
				Object timeStamp = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss").format(new Date());
				TakesScreenshot t = (TakesScreenshot) driver;
				File scrFile = t.getScreenshotAs(OutputType.FILE);
				File dstpath = new File(SCREENSHOT_PATH + testScriptName + "-" + timeStamp);
				FileUtils.copyFile(scrFile, dstpath);
				test.addScreenCaptureFromPath("./../img/test.png");
				test.fail(MediaEntityBuilder.createScreenCaptureFromPath("./../img/test.png").build());
				String msg = result.getThrowable().getMessage();
				test.fail("Failed Reason : " + msg);
			} catch (Exception E) {

			}
		}
		driver.quit();
	}

	@DataProvider()
	public Iterator<String[]> getData(Method testMethod) {
		String sheetName = testMethod.getName();
		Iterator<String[]> data = Utility.getDataFromExcel(DATA_PATH, sheetName);
		return data;
	}
}
