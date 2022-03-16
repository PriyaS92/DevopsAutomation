package com.automation.devops.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.devops.CommonFunctions;
import com.automation.devops.config.Utilities;



public class Homepage extends CommonFunctions{
	
	Utilities util = new Utilities();
	CommonFunctions cf = new CommonFunctions();
	
	@FindBy(xpath="//*[@class='login']")
    WebElement btnSignin;
	@FindBy(id="email")
	WebElement userid_txtbox;
	@FindBy(id="passwd")
	WebElement password_txtbox;
	@FindBy(id="SubmitLogin")
	WebElement signin_button;
	@FindBy(xpath="//*[text()='Women']")
	WebElement women_tab;
	@FindBy(id="search_query_top")
	WebElement searchbox;
	@FindBy(xpath="//*[@name='submit_search']")
	WebElement search_button;
	@FindBy(xpath="//div[@id='pagination']//following::div[1]")
	WebElement items_count;
	
	
	public Homepage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	public void login()
    {
		try{
			cf.waitforpageload(10,"second");
			btnSignin.click();
			cf.waitforpageload(1,"minute");
			userid_txtbox.sendKeys(util.getpropdata("username"));
			password_txtbox.sendKeys(util.getpropdata("password"));
			signin_button.click();
			cf.waitforpageload(15,"second");
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void navigateto_womentab() {
		try {
			women_tab.click();
			cf.waitforpageload(1,"minute");
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void search_functionality(String product) {
		try {
			searchbox.click();
			searchbox.sendKeys(product);
			search_button.click();
			cf.waitforpageload(10,"second");
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void validate_searchresults() {
		try {
			cf.viewelement(items_count);
			String count = items_count.getText().trim().split("of")[1].trim().split(" ")[0];
			int count_val = Integer.parseInt(count);
			for(int i=1;i<count_val+1;i++) {
				String product_name = driver.findElement(By.xpath("//*[@id='center_column']/ul/li["+i+"]/div/div[2]/h5/a")).getText().trim();
				String product_price = driver.findElement(By.xpath("//*[@id='center_column']/ul/li["+i+"]/div/div[2]/div[1]/span[1]")).getText().trim();
				String stock_status = driver.findElement(By.xpath("//*[@id='center_column']/ul/li["+i+"]/div/div[2]/span/span")).getText().trim();
				System.out.println(product_name+" is "+stock_status+" and price is "+product_price);
			}
		}
		catch(Exception e){e.printStackTrace();}
	}
}
