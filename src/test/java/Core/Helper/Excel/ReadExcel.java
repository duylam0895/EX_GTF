package Core.Helper.Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;

public class ReadExcel {
	
	private static XSSFSheet sheet;
	
	/***
	 * Load Excel sheet with path and sheetName
	 * @param filePath
	 * @param sheetName
	 * @throws IOException
	 * @since 1.0.0
	 * @author Lam
	 */
	public static XSSFSheet loadExcel(String filePath, String sheetName) throws FileNotFoundException, IOException, SecurityException {
		File file = new File(filePath);
		FileInputStream input = new FileInputStream(file); 
		XSSFWorkbook workbook = new XSSFWorkbook(input);
		sheet = workbook.getSheet("Data");
		workbook.close();
		return sheet;
	}
	
	/**
	 * Get collection of column that contains data
	 * @param sheet
	 * @return
	 * @since 1.0.0
	 * @author Lam
	 */
	public static ArrayList<Integer> collectDataColumn(XSSFSheet sheet) throws IndexOutOfBoundsException{
		XSSFRow row = sheet.getRow(0);
		int idxOfLastCol = row.getLastCellNum();
		ArrayList<Integer> idxCollection = new ArrayList<>();
		
		for(int idx = 1; idx < idxOfLastCol; idx++) {
			if(row.getCell(idx) != null) {
				idxCollection.add(idx);
			}
		}
		return idxCollection;
	}
	
	/**
	 * Get Number of Rows contains data
	 * @param sheet
	 * @param colIdx
	 * @return
	 * @since 1.0.0
	 * @author Lam
	 */
	public static int getRowsNumber(XSSFSheet sheet, int colIdx) throws IndexOutOfBoundsException{
		boolean isStop = false;
		int rowIdx = 0;
		while(!isStop) {
			try {
				if(getCell(sheet, rowIdx, colIdx) == null) {
					break;
				}
			}catch(NullPointerException e) {
				rowIdx--;
				break;
			}
			rowIdx++;
		}
		return ++rowIdx;
	}
	
	/**
	 * Get cell at row and column
	 * @param sheet
	 * @param idxRow
	 * @param idxCol
	 * @return
	 * @throws IllegalArgumentException
	 * @throws NumberFormatException
	 * @since 1.0.0
	 * @author Lam
	 */
	public static String getCell(XSSFSheet sheet, int idxRow, int idxCol) throws NullPointerException, IllegalArgumentException, NumberFormatException{
		XSSFRow row = sheet.getRow(idxRow);
		XSSFCell cell = row.getCell(idxCol);
		return getCellValue(cell);
	}
	
	/**
	 * Get value of cell
	 * @param cell
	 * @return
	 * @throws IllegalArgumentException
	 * @throws NumberFormatException
	 * @since 1.0.0
	 * @author Lam
	 */
	private static String getCellValue(XSSFCell cell) throws  IllegalArgumentException, NumberFormatException{
		String result = "";
		try {
			CellType cellType = cell.getCellType();
			
			switch(cellType) {
			case BOOLEAN:
				result = String.valueOf(cell.getBooleanCellValue());
				break;
			case FORMULA:
				CellType formatType = cell.getCachedFormulaResultType();
				if(formatType == CellType.NUMERIC) result = readNummericCell(cell);
				if(formatType == CellType.STRING) result = cell.getStringCellValue().trim();
				break;
			case NUMERIC:
				result = readNummericCell(cell);
				break;
			case STRING:
				result = cell.getStringCellValue().trim();
				break;
			case BLANK:
				result = "";
				break;
			default:
				break;
			}
		}catch(NullPointerException exp) {
		}
		return result;
	}
	
	/**
	 * Round up cell value convert to string
	 * @param cell
	 * @return
	 * @throws NumberFormatException
	 * @throws IllegalArgumentException
	 * @since 1.0.0
	 * @author Lam
	 */
	private static String readNummericCell(XSSFCell cell) throws NumberFormatException, IllegalArgumentException{
		double value = cell.getNumericCellValue();
		if(value == 0) {
			return "0";
		}else {
			double roundValue = Math.round(value);
			String valueStr = String.format("%.0f", roundValue);
			int size = valueStr.length();
			return valueStr.substring(0, size);
		}
	}
	
	/**
	 * Parse data of Excel to Hashtable<colIdx Hashtabl<rowIdx, Content>>
	 * @param sheet
	 * @return
	 * @since 1.0.0
	 * @author Lam
	 */
	public static Hashtable<Integer, Hashtable<Integer, String>> parseExcelToHash(XSSFSheet sheet){
		Hashtable<Integer, Hashtable<Integer, String>> result = new Hashtable<>();
		ArrayList<Integer> listCol = collectDataColumn(sheet);
		int maxRowIdx = getRowsNumber(sheet, 0);
		
		result.put(0, parseColTo(sheet, 0, maxRowIdx));
		for(int colIdx : listCol) {
			result.put(colIdx, parseColTo(sheet, colIdx, maxRowIdx));
		}
		return result;
	}
	
	/**
	 * Parse a Column to Hashtable<rowIdx, Content>
	 * @param sheet
	 * @param colIdx
	 * @param maxRowIdx
	 * @return
	 * @since 1.0.0
	 * @author Lam
	 */
	private static Hashtable<Integer, String> parseColTo(XSSFSheet sheet, int colIdx, int maxRowIdx){
		Hashtable<Integer, String> result = new Hashtable<>();
		for(int rowIdx = 0; rowIdx < maxRowIdx; rowIdx++) {
			result.put(rowIdx, getCell(sheet, rowIdx, colIdx));
		}
		return result;
	}
}
