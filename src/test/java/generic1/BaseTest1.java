package generic1;

import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest1 implements IAutoConst1 {
	public WebDriver driver;
	public WebDriverWait wait;

	@Parameters({ "config" })
	@BeforeMethod(alwaysRun = true)
	public void openApp(@Optional(CONFIF_PATH) String config) {
		String browser = Utility1.getProperty(config, "BROWSER");
		String grid = Utility1.getProperty(config, "GRID");
		if (grid.equalsIgnoreCase("Yes")) {
			try {
				String remote = Utility1.getProperty(config, "REMOTE_URL");
				URL remoteUrl = new URL(remote);
				DesiredCapabilities capability = new DesiredCapabilities();
				capability.setBrowserName(browser);
				driver = new RemoteWebDriver(remoteUrl, capability);
			} catch (Exception e) {
				e.printStackTrace();
			}
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

		String sITO = Utility1.getProperty(config, "ITO");
		long ITO = Long.parseLong(sITO);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ITO));

		String sETO = Utility1.getProperty(config, "ETO");
		long ETO = Long.parseLong(sETO);
		wait = new WebDriverWait(driver, Duration.ofSeconds(ETO));

		String appURL = Utility1.getProperty(config, "APP_URL");
		driver.get(appURL);
	}

	@AfterMethod(alwaysRun = true)
	public void closeApp() {
		driver.quit();
	}

	@DataProvider
	public Iterator<String[]> getData(Method testMethod) {
		String sheetName = testMethod.getName();
		Iterator<String[]> data = Utility1.getDataFromExcel(EXCELDATA_PATH, sheetName);
		return data;
	}
}
