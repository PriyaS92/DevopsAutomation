package com.automation.devops;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverConfig {
	
	public WebDriver getChrome(){
		if(System.getProperty("user.dir").contains("jenkins")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("PropFilePath"));
		}
		else {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//drivers//chromedriver.exe");
		}
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		return new ChromeDriver();
	}
}