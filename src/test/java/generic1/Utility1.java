package generic1;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Utility1 {
	public static String getProperty(String path, String key) {
		String v = "";
		try {
			Properties p = new Properties();
			p.load(new FileInputStream(path));
			v = p.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	public static String getXLData(String path, String sheetName, int row, int col) {
		String v = "";
		try {
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			v = wb.getSheet(sheetName).getRow(row).getCell(col).toString();
		} catch (Exception e) {

		}
		return v;
	}

	public static int getXLRowCount(String path, String sheetName) {
		int row = 0;
		try {
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			row = wb.getSheet(sheetName).getLastRowNum();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public static int getXLColCount(String path, String sheetName, int row) {
		int col = 0;
		try {
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			col = wb.getSheet(sheetName).getRow(row).getLastCellNum();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return col;
	}

	public static Iterator<String[]> getDataFromExcel(String path, String sheetName) {
		ArrayList<String[]> data = new ArrayList<String[]>();
		try {
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			Sheet sheet = wb.getSheet(sheetName);
			int rowCounts = sheet.getLastRowNum();
			for (int i = 1; i <= rowCounts; i++) {
				try {
					short colCount = sheet.getRow(i).getLastCellNum();
					String[] cell = new String[colCount];
					for (int j = 0; j < colCount; j++) {
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
