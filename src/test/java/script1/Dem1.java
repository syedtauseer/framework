package script1;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import generic1.BaseTest1;
import generic1.Utility1;

public class Dem1 extends BaseTest1 {
	@Test(priority = 1, groups = "smoke", enabled = true)
	public void test1() {
		String title = driver.getTitle();
		Reporter.log(title, true);
		test.info(title);
		int rc = Utility1.getXLRowCount(EXCELDATA_PATH, "Test1");
		for (int i = 1; i <= rc; i++) {
			String un = Utility1.getXLData(EXCELDATA_PATH, "Test1", i, 0);
			Reporter.log(un, true);
			test.info("UserName : " + un);
			String pw = Utility1.getXLData(EXCELDATA_PATH, "Test1", i, 1);
			Reporter.log(pw, true);
			test.info("Password : " + pw);
		}
		Assert.assertEquals("bcd", "anull");
	}
}
