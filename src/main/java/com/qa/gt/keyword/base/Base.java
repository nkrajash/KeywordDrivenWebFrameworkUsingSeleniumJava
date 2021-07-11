package com.qa.gt.keyword.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Base {

	public WebDriver driver;
	public Properties props;
	
	//initialize the driver
	public WebDriver init_driver(String browser) {
		if (browser.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", "C:\\Data\\Selenium\\Softwares\\chrome_v91\\chromedriver.exe");
		}
		if(props.getProperty("headless").equals("yes")) {
			//headless mode
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("ignore-certificate-errors");
			chromeOptions.addArguments("--no-sandbox");			
			chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
			driver = new ChromeDriver(chromeOptions);
		}
		else {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("ignore-certificate-errors");
			chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
			driver = new ChromeDriver(chromeOptions);
		}
		return driver;
	}
	
	
	//initialize the properties
	public Properties init_properties() throws IOException {
		props = new Properties();
		try {
			FileInputStream file = new FileInputStream("E:\\Data\\Selenium-workspace\\KeywordDrivenFrameworkUsingSelenium"
				+ "\\src\\main\\java\\com\\qa\\gt\\keyword\\config\\config.properties");
			props.load(file);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException io) {
			io.printStackTrace();
		}
		return props;
	}
	
}
