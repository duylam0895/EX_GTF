package Core.Helper.Excel;

import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public class Data {

	private static Hashtable<String, String> data;
	private static XSSFSheet sheet;
	private static int colIdx;
	private static final String seperateChar = "-";
	
	/**
	 * Load Data from Excel file
	 * @param filePath
	 * @param sheetName
	 * @throws IOException
	 * @since 1.0.0
	 * @author Lam
	 */
	public static void loadDataFromExcel(String filePath, String sheetName) throws IOException {
		sheet = ReadExcel.loadExcel(filePath, sheetName);
	}
	
	/**
	 * Set Index of Current Column
	 * @param columnIdx
	 * @since 1.0.0
	 * @author Lam
	 */
	public static void setColumnIdx(int columnIdx) {
		colIdx = columnIdx;
	}
	
	/**
	 * Load specific Data with prefix
	 * @param prefixTitle
	 * @since 1.0.0
	 * @author Lam
	 */
	public static void loadSpecificData() {
		data = new Hashtable<>();
		int maxRowIdx = ReadExcel.getRowsNumber(sheet, 0);
		Hashtable<Integer, Hashtable<Integer, String>> table = ReadExcel.parseExcelToHash(sheet);
		
		for(int rowIdx = 0; rowIdx < maxRowIdx; rowIdx ++) {
			String title = table.get(0).get(rowIdx);
			String value = table.get(colIdx).get(rowIdx);
			data.put(title, value);
		}
	}
	
	/**
	 * Get data of keyword
	 * @param key
	 * @return
	 * @since 1.0.0
	 * @author Lam
	 */
	public static String getData(String key) {
		return data.get(key);
	}
}
