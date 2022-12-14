package script1;

import org.testng.Reporter;
import org.testng.annotations.Test;
import generic1.BaseTest1;

public class Dem3 extends BaseTest1 {
	@Test(dataProvider = "getData", priority = 3, groups = "smoke", enabled = true)
	public void test3(String... data) {
		String title = driver.getTitle();
		Reporter.log(title, true);
		for (String v : data) {
			Reporter.log(v, true);
		}
	}
}
