package script;

import org.testng.Reporter;
import org.testng.annotations.Test;

import generic.BaseTest;

public class Demo2 extends BaseTest {
	@Test(dataProvider = "getData", priority = 2, groups = "smoke", enabled = true)
	public void test2(String[] data) {
		String title = driver.getTitle();
		Reporter.log(title, true);
		test.info(title);
		for (String v : data) {
			Reporter.log(v, true);
		}

	}
}
