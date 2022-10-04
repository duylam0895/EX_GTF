package Core.BaseTest;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.reporter.configuration.Theme;

import Core.Helper.Excel.Data;
import Core.Helper.Report.Report;


public class BaseTestNG {
	protected static String dataFilePath;
	protected static String sheet;
	protected static boolean isTDD;
	protected static String url;

	@Parameters({"url", "reportName", "dataFilePath", "isTDD"})
	@BeforeSuite
    public void beforeSuite(@Optional("") String url, @Optional("") String reportName, @Optional("") String dataFilePath, @Optional("") String isTDDStr) {
		BaseTestNG.url = url;
		String name = reportName + "_" + Report.getDate();
		String filePath = System.getProperty("user.dir") + File.separator + "result" + File.separator + name + ".html";
		name = reportName + "_" + Report.getDate("dd-MM-yyyy_hh:mm:ss");
		Report.setReports(Report.getSparkReporter(filePath, Theme.STANDARD, name));
		boolean isTDD = isTDDStr.toUpperCase().equals("TRUE");
		BaseTestNG.isTDD = isTDD;
		if(isTDD) {
			BaseTestNG.dataFilePath = dataFilePath;
		}
    }

    @AfterSuite
    public void afterSuite() {
    	Report.closeReports();
    }
    
    @BeforeTest
    @Parameters({"sheetName"})
    public void beforeTest(@Optional("") String sheetName) {
    	if(isTDD) {
    		BaseTestNG.sheet = sheetName;
    	}
    }
    
    @DataProvider(name = "testdatasupply")
    public Object[][] ReadDetails() {
        if(isTDD) {

        	try {
    			Data.loadDataFromExcel(dataFilePath, sheet);
    			
    		} catch (IOException e) {
    			Report.print("IMPORT DATA", String.format("Failed to import data path [%s] and sheet [%s]", dataFilePath, sheet), false);
    			e.printStackTrace();
    		}
        	List<Hashtable<String, String>> data = Data.getListData();
        	Object[][] data1 =new Object[data.size()][data.get(0).size()];
        	for(int colIdx = 0; colIdx < data.size(); colIdx++) {
        		data1[colIdx][0] = data.get(colIdx);
        	}
            return asTwoDimensionalArray(data);
        }else {
        	return null;
        }
    }
    
    private static Object[][] asTwoDimensionalArray(List<Hashtable<String, String>> data) {
        Object[][] results = new Object[data.size()][1];
        int index = 0;
        for (Map<String, String> interimResult : data) {
          results[index++] = new Object[] {interimResult};
        }
        return results;
      }
}
