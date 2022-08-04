package script1;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Demo4 {
	@Test(enabled = false)
	public void test_report() {
		ExtentReports report = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark/Spark.html");
		report.attachReporter(spark);

		ExtentTest test = report.createTest("MyTest1");
		test.info("this is info");
		test.pass("this is pass");

		ExtentTest test1 = report.createTest("MyTest2");
		test1.warning("this is warning");
		test1.fail("this is fail");
		report.flush();
	}
}
