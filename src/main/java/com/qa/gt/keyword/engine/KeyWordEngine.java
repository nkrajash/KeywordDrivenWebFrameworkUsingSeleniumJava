package com.qa.gt.keyword.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.gt.keyword.base.Base;
import com.qa.gt.keyword.keywordactions.KeywordActions;

public class KeyWordEngine {

	public WebDriver driver;
	public Properties props;
	
	public static Workbook book;
	public static Sheet sheet;
	public Base base;
	public KeywordActions keywordActions;

	public static ThreadLocal<Workbook> testBook = new ThreadLocal<Workbook>();
	public static ThreadLocal<Sheet> testSheet = new ThreadLocal<Sheet>();

	public final String SCENARIO_SHEET_PATH = "E:\\Data\\Selenium-workspace\\KeywordDrivenFrameworkUsingSelenium\\"+
			 "src\\main\\java\\com\\qa\\gt\\keyword\\scenarios\\gumtree_scenarios.xlsx";
	
	public void startExecution(String sheetname) throws IOException {
		FileInputStream file = null;
		By locator;
		String locatorName = "";
		String locatorValue= "";
		String action = "";
		String value = "";
		
		try {
			file = new FileInputStream(SCENARIO_SHEET_PATH);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		try {
			book= WorkbookFactory.create(file);
			testBook.set(book);
		}
		catch(IOException io) {
			io.printStackTrace();
		}
		

		int k=0;
		sheet= book.getSheet(sheetname);
		testSheet.set(sheet);
		for(int i=0;i<sheet.getLastRowNum();i++) {
			System.out.println(sheet.getRow(i + 1).getCell(k).toString());
			String locatorColValue = sheet.getRow(i + 1).getCell(k+1).toString().trim();
			//points from second row due to headers
			if(!locatorColValue.equalsIgnoreCase("NA")) {
				locatorName = locatorColValue.split("=")[0]; //locator
				locatorValue = locatorColValue.split("=")[1]; //String
			}
			else {
				locatorName = "NA";
				locatorValue = "NA";
			}
			action = sheet.getRow(i + 1).getCell(k+2).toString().trim();
			value = sheet.getRow(i + 1).getCell(k+3).toString().trim();
			
			switch(action) {
				case "open browser":
					base = new Base();
					props = base.init_properties();
					if(value.equalsIgnoreCase("NA") || value.isEmpty()) {
						driver = base.init_driver(props.getProperty("browser"));
					}
					else
						driver = base.init_driver(value);
					keywordActions = new KeywordActions(driver);
					break;
				case "launch url":
					keywordActions.deleteAllCookies();
					keywordActions.windowMaximize();
					keywordActions.implicitTimeout();
					keywordActions.pageLoadTimeout();
					if(value.equalsIgnoreCase("NA") || value.isEmpty()) {
						keywordActions.launchUrl(props.getProperty("url"));
					}
					else
						keywordActions.launchUrl(value);
					break;
				case "quit":
					keywordActions.quitBrowser();
					break;

				default:
					System.out.println("No Action is defined");
					break;
			}
			
			switch(locatorName) {
			case "id":
				locator = keywordActions.getId(locatorValue);
				if(action.equalsIgnoreCase("sendKeys")) {
					keywordActions.clearField(locator);
					keywordActions.sendKeys(locator,value);
				}else if(action.equalsIgnoreCase("click")) {
					keywordActions.click(locator);
				}
				System.out.println(keywordActions.getPageTitle());
				break;
			case "name":
				locator = keywordActions.getName(locatorValue);
				if(action.equalsIgnoreCase("sendKeys")) {
					keywordActions.clearField(locator);
					keywordActions.sendKeys(locator,value);
				}else if(action.equalsIgnoreCase("click")) {
					keywordActions.click(locator);
				}
				
				break;
			case "classname":
				locator = keywordActions.getClassName(locatorValue);
				if(action.equalsIgnoreCase("sendKeys")) {
					keywordActions.clearField(locator);
					keywordActions.sendKeys(locator,value);
				}else if(action.equalsIgnoreCase("click")) {
					keywordActions.click(locator);
				}
				break;			
			case "tagname":
				locator = keywordActions.getTagName(locatorValue);
				if(action.equalsIgnoreCase("sendKeys")) {
					keywordActions.clearField(locator);
					keywordActions.sendKeys(locator,value);
				}else if(action.equalsIgnoreCase("click")) {
					keywordActions.click(locator);
				}
				break;	
				
			case "linkText":
				locator = keywordActions.getLinkText(locatorValue);
				keywordActions.click(locator);
				
				break;
			case "xpath":
				locator = keywordActions.getXPATH(locatorValue);
				if(action.equalsIgnoreCase("sendKeys")) {
					keywordActions.clearField(locator);
					keywordActions.sendKeys(locator,value);
				}else if(action.equalsIgnoreCase("click")) {
					keywordActions.click(locator);
				}
				else if(action.equalsIgnoreCase("isDisplayed")) {
					if(keywordActions.isDisplayed(locator))
						System.out.println(keywordActions.getElementText(locator) + " is Displayed");
				}
				else if(action.equalsIgnoreCase("moveToElement")) {
					keywordActions.moveToElement(locator);
				}
				else if(action.equalsIgnoreCase("moveToElementandClick")) {
					keywordActions.moveToElementandClick(locator);
				}
				break;
			case "cssSelector":
				locator = keywordActions.getCSS(locatorValue);
				if(action.equalsIgnoreCase("sendKeys")) {
					keywordActions.clearField(locator);
					keywordActions.sendKeys(locator,value);
				}else if(action.equalsIgnoreCase("click")) {
					keywordActions.click(locator);
				}
				else if(action.equalsIgnoreCase("isDisplayed")) {
					if(keywordActions.isDisplayed(locator))
						System.out.println(keywordActions.getElementText(locator) + " is Displayed");
				}
				else if(action.equalsIgnoreCase("moveToElement")) {
					keywordActions.moveToElement(locator);
				}
				else if(action.equalsIgnoreCase("moveToElementandClick")) {
					keywordActions.moveToElementandClick(locator);
				}
				System.out.println(keywordActions.getPageTitle());
				break;
			default:
				System.out.println("No locator is defined");
				break;
			}
			
		}
		
	}
	
	
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {
			Row row = null;
			Cell cell = null;

			FileInputStream fis = new FileInputStream(SCENARIO_SHEET_PATH);
			book = new XSSFWorkbook(fis);

			if (rowNum <= 0)
				return false;

			int index = book.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			sheet = book.getSheetAt(index);

			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			// cell style
			// CellStyle cs = workbook.createCellStyle();
			// cs.setWrapText(true);
			// cell.setCellStyle(cs);
			cell.setCellValue(data);

			FileOutputStream fileOut = new FileOutputStream(SCENARIO_SHEET_PATH);

			book.write(fileOut);

			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
