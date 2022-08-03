package generic;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Utility {
	public static String getProperty(String path, String Key) {
		String v = "";
		try {
			Properties p = new Properties();
			p.load(new FileInputStream(path));
			v = p.getProperty(Key);
		} catch (Exception E) {

		}
		return v;
	}

	public static String getXLData(String path, String Sheet, int row, int col) {
		String v = "";
		try {
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			v = wb.getSheet(Sheet).getRow(row).getCell(col).toString();
		} catch (Exception e) {

		}
		return v;
	}

	public static int getXLRowCount(String path, String sheet) {
		int row = 0;
		try {
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			row = wb.getSheet(sheet).getLastRowNum();
		} catch (Exception e) {

		}
		return row;
	}

	public static int getXLColCount(String path, String Sheet, int row) {
		int col = 0;
		try {
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			col = wb.getSheet(Sheet).getRow(row).getLastCellNum();
		} catch (Exception e) {
		}
		return col;
	}

	public static Iterator<String[]> getDataFromExcel(String path, String sheetName) {
		ArrayList<String[]> data = new ArrayList<String[]>();
		try {
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheet(sheetName);
			int rc = sheet.getLastRowNum();
			for (int i = 1; i <= rc; i++) {
				try {
					short cc = sheet.getRow(i).getLastCellNum();
					String[] cell = new String[cc];
					for (int j = 0; j < cc; j++) {
						try {
							String value = sheet.getRow(i).getCell(j).toString();
							cell[j] = value;
						} catch (Exception e) {
							cell[j] = " ";
						}

					}
					data.add(cell);

				} catch (Exception e) {

				}
				System.out.println();
			}
			wb.close();
		} catch (Exception e) {

		}
		return data.iterator();
	}
}
