package script1;

import org.testng.Reporter;
import org.testng.annotations.Test;

import generic.BaseTest;

public class Demo3 extends BaseTest {
	@Test(dataProvider = "getData", priority = 3, groups = "smoke", enabled = true)
	public void test3(String... data) {
		String title = driver.getTitle();
		Reporter.log(title, true);
		test.info(title);
		for (String v : data) {
			Reporter.log(v, true);
		}
	}
}
