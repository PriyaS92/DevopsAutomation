package com.automation.devops;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.automation.devops.config.Utilities;

public class CommonFunctions{
	
	Utilities util = new Utilities();
	WebDriverConfig dc = new WebDriverConfig();
	
	public static WebDriver driver;
	public static String url;
	public static String BROWSER_NAME;
	public String Chrome_Path;
	Actions a;
	
	@BeforeMethod
	public void setup() {
		try {
			BROWSER_NAME = util.getpropdata("browser");
			if(BROWSER_NAME.equals("Chrome")) {
				CommonFunctions.driver = dc.getChrome();
			}
			login();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void login() {
		try {
			url = util.getpropdata("url");
			CommonFunctions.driver.navigate().to(url);
			CommonFunctions.driver.manage().window().maximize();
			waitforpageload(15, "second");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterMethod
	public void teardown(){
		CommonFunctions.driver.quit();
	}
	
	public void waitforpageload(long time,String unit) {
		switch(unit){
			case "second":
				CommonFunctions.driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
				break;
			case "minute":
				CommonFunctions.driver.manage().timeouts().implicitlyWait(time, TimeUnit.MINUTES);
				break;
			case "millisecond":
				CommonFunctions.driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
				break;
			default:
				System.out.println("Timeunit does not match with the available option");
		}	
	}
	
	public void viewelement(WebElement e) {
		try {
			JavascriptExecutor je = (JavascriptExecutor) CommonFunctions.driver;
			je.executeScript("arguments[0].scrollIntoView(true);",e);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	protected void setDesiredCapabilitiesForBrowser(DesiredCapabilities desiredCapabilities) {
		  
		  Chrome_Path = util.getpropdata("chromepath")+"//chromedriver.exe";
		  desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, BROWSER_NAME);
		  if(BROWSER_NAME.equals("Chrome")) {
			  desiredCapabilities.setCapability("chromedriverExecutable",Chrome_Path);
			}
	}
	
	public void mousehover(WebElement e) {
		a=new Actions(CommonFunctions.driver);
		a.moveToElement(e).build().perform();
	}
}
