package udemy.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import udemy.constants.Constants;


public class Utilities {
	public static Logger log=LogManager.getLogger(Utilities.class.getName());
	public static String getProperty(String key) throws IOException
	{
		Properties prop=new Properties();
		String baseProjectPath = System.getProperty(Constants.USER_DIR);
		FileInputStream fis=new FileInputStream(baseProjectPath.concat(Constants.CONFIG_PROPERTY));
		prop.load(fis);
		String value=prop.getProperty(key).trim();
		return value;
	}
	public static String getSQLProperty(String key) throws IOException
	{
		Properties prop=new Properties();
		String baseProjectPath = System.getProperty(Constants.USER_DIR);
		FileInputStream fis=new FileInputStream(baseProjectPath.concat(Constants.DB_PROPERTY));
		prop.load(fis);
		String value=prop.getProperty(key).trim();
		return value;
	}
	public static void highLightElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			log.info(e.getMessage());
		}
		js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);

	}
	
	/**
	 * Utility to read test data from external test data excel
	 * @param testDataSheetPath
	 * @param sheetName
	 * @param testCaseName
	 * @return
	 * @throws IOException
	 */
	public static List<String> getData(String testDataSheetPath,String sheetName,String testCaseName,String uniqueColumnIdentifier) throws IOException
	{
		System.out.println("inside get data");
		List<String> list=new ArrayList<String>();
		FileInputStream fis=null;
		XSSFWorkbook workBook=null;
		try {
			 fis=new FileInputStream(testDataSheetPath);
			//Accepts FileInputStream arg
			workBook=new XSSFWorkbook(fis);
			XSSFSheet sheet;
			//getting no of sheets
			int noOfSheets=workBook.getNumberOfSheets();
			for(int i=0;i<noOfSheets;i++)
			{
				//Navigating to a particular sheet
				if(workBook.getSheetName(i).equalsIgnoreCase(sheetName))
				{
					 sheet=workBook.getSheetAt(i); //sheet is collection of rows
					 //it will give all the rows
					 Iterator<Row> rows= sheet.iterator();
					 Row firstRow=rows.next();//Row is collection of cells
					 int count=0;
					 int column=0;
					 //it will give all the cells of first row
					 Iterator<Cell> cells= firstRow.cellIterator();
					 while(cells.hasNext())
					 {
						 Cell value=cells.next();
						 //checking whether any cell of first row contains TC or not
						 if(value.getStringCellValue().equalsIgnoreCase(uniqueColumnIdentifier))
						 {
							 column=count;
						 }
						 //increasing the count to get the particular column where we have TC cell value
						 count++;
					 }
					 log.info(column);
					 //we are iterating all the rows again
					 while(rows.hasNext())
					 {
						 Row r=rows.next();
						 //we are iterating over all the rows and a particular column which we have already got
						 //And checking whether that particular cell value is equal to TC_2 or not
						 if(r.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName))
						 {
							Iterator<Cell> cellValues=r.cellIterator();
							while(cellValues.hasNext())
							{
								//getting all the cell values
								//storing the cell values in an Array List
								Cell c=cellValues.next();
								if(c.getCellType()==CellType.STRING)
									list.add(c.getStringCellValue());
								else
									list.add(NumberToTextConverter.toText(c.getNumericCellValue()));
								
							}
						 }
					 }
				}
			}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
			log.error("Exception occured while reading excel"+ioe.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
		}finally
		{
			if(workBook!=null)
				workBook.close();
			if(fis!=null)
				fis.close();
		}
		
		
		return list;
	}
	
	/**
	 * this method will connect to MySql DB and will return the Connection object
	 * @param host
	 * @param port
	 * @param dbName
	 * @param dbUsername
	 * @param dbPassword
	 * @param query
	 * @return
	 * @throws IOException 
	 */
	public static Connection connectDB() throws IOException
	{
		String host=Utilities.getProperty("host");
		String port=Utilities.getProperty("port");
		String dbName=Utilities.getProperty("dbName");
		String dbUsername=Utilities.getProperty("dbUsername");
		String dbPassword=Utilities.getProperty("dbPassword");
		
		Connection con=null;
		try {
			log.info("jdbc:mysql://"+host+":"+port+"/"+dbName+"");
			con=DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dbName+"",dbUsername ,dbPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;
		
	}
	/**
	 * This method will execute the prepared statement and will return the POJO object
	 * @param applicationDetailsDto
	 * @return
	 */
	public ApplicationDetails getApplicationDetails(ApplicationDetails applicationDetailsDto,String queryKey)
	{
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con=null;
		ApplicationDetails applicationDetails = null;
		
		try {
			String query=Utilities.getSQLProperty(queryKey);
			con = connectDB();
			st = con.prepareStatement(query);
			st.setString(1, applicationDetailsDto.getUserNameFromDB());
			rs = st.executeQuery();
			applicationDetails = new ApplicationDetails();
			while(rs.next())
			{
				applicationDetails.setUserNameFromDB(rs.getString("Username"));
				applicationDetails.setPasswordFromDB(rs.getString("Password"));
			}
			return applicationDetails;
		} catch (SQLException|IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return applicationDetails;
	}

	public static Map<String, String> readExcel(String testDataSheetPath, String sheetName, String testCaseName)
			throws IOException {
		Map<String, String> map = new HashMap<>();
		FileInputStream fis = null;
		XSSFWorkbook workBook = null;
		try {
			fis = new FileInputStream(testDataSheetPath);

			// Create Workbook instance holding reference to .xlsx file
			workBook = new XSSFWorkbook(fis);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workBook.getSheet(sheetName);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					// Check the cell type and format accordingly
					switch (cell.getCellType()) {
					case NUMERIC:
						log.info(cell.getNumericCellValue() + "t");
						break;
					case STRING:
						log.info(cell.getStringCellValue() + "t");
						break;
					default:
						break;
					}
				}
				log.info("");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			log.error("Exception occured while reading excel" + ioe.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workBook != null)
				workBook.close();
			if (fis != null)
				fis.close();
		}

		return map;
	}

}
