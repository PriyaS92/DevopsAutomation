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

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusResult;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.IpRange;
import com.amazonaws.services.ec2.model.MonitorInstancesRequest;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.UnmonitorInstancesRequest;
import com.amazonaws.util.EC2MetadataUtils;
import com.automation.devops.config.Utilities;
import com.amazonaws.services.health.AWSHealth;
import com.amazonaws.services.health.AWSHealthClient;
import com.amazonaws.services.health.model.DescribeEventsRequest;
import com.amazonaws.services.health.model.DescribeEventsResult;
import com.amazonaws.services.health.model.Event;
import com.amazonaws.services.health.model.EventFilter;

public class CommonFunctions{
	
	Utilities util = new Utilities();
	WebDriverConfig dc = new WebDriverConfig();
	
	public static WebDriver driver;
	public static String url;
	public static String BROWSER_NAME;
	public String Chrome_Path;
	Actions a;
	AmazonEC2 ec2Client;
	
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
	
	public void monitorstatus_ec2win() {
		try {
			// create our EC2 client
			BasicAWSCredentials awsCreds = new BasicAWSCredentials(System.getProperty("Accesskey"), System.getProperty("Secretkey"));
			ec2Client = AmazonEC2ClientBuilder
					  .standard()
					  .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
					  .withRegion(Regions.AP_SOUTH_1)
					  .build();
			
			// Monitoring the instance request
			MonitorInstancesRequest monitorInstancesRequest = new MonitorInstancesRequest()
					  .withInstanceIds(util.getpropdata("ec2_instanceid"));
			ec2Client.monitorInstances(monitorInstancesRequest);
			
			// Describing EC2 instance
			DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
			DescribeInstancesResult response = ec2Client.describeInstances(describeInstancesRequest);
			DescribeInstanceStatusRequest describeInstanceRequest = new DescribeInstanceStatusRequest().withInstanceIds(util.getpropdata("ec2_instanceid"));
		    DescribeInstanceStatusResult describeInstanceResult = ec2Client.describeInstanceStatus(describeInstanceRequest);
		    
			// Status check of windows ec2 instance 
		    	System.out.println("EC2 Instance Platform: "+response.getReservations().get(0).getInstances().get(0).getPlatform().toString());
		    	System.out.println("EC2 Instance Machine ID: "+response.getReservations().get(0).getInstances().get(0).getImageId().toString());
				System.out.println("EC2 Instance State: "+response.getReservations().get(0).getInstances().get(0).getState().getName());
				System.out.println("EC2 Instance Status: "+describeInstanceResult.getInstanceStatuses().get(0).getInstanceStatus().getDetails());
				System.out.println("EC2 Instance System Status: "+describeInstanceResult.getInstanceStatuses().get(0).getSystemStatus().getDetails());
		   }
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void teardowninstance() {
		try {
			UnmonitorInstancesRequest unmonitorInstancesRequest = new UnmonitorInstancesRequest().withInstanceIds(util.getpropdata("ec2_instanceid"));
			ec2Client.unmonitorInstances(unmonitorInstancesRequest);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
