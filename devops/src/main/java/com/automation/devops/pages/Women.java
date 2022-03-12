package com.automation.devops.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automation.devops.CommonFunctions;
import com.automation.devops.config.Utilities;


public class Women extends CommonFunctions{

	Utilities util = new Utilities();
	CommonFunctions cf = new CommonFunctions();
	
	@FindBy(xpath="//*[@id='categories_block_left']/div/ul/li[1]/span")
	WebElement expand_tops;
	@FindBy(xpath="//*[@id='categories_block_left']/div/ul/li[1]/ul/li[1]/a")
	WebElement tshirt;
	@FindBy(xpath="//*[@title='Faded Short Sleeve T-shirts']")
	WebElement shirtimg;
	@FindBy(xpath="//*[@id='center_column']/ul/li/div/div[2]/span/span")
	WebElement stock_status;
	@FindBy(xpath="//span[text()='Add to cart']")
	WebElement addcart_button;
	@FindBy(xpath="//div[@class='layer_cart_product col-xs-12 col-md-6']//h2")
	WebElement message;
	@FindBy(xpath="//span[@class='ajax_block_cart_total']")
	WebElement total_price;
	@FindBy(xpath="//a[@title='Proceed to checkout']")
	WebElement checkout_button;
	
	public Women(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void addtocart() {
		try {
			cf.viewelement(expand_tops);
			expand_tops.click();
			cf.viewelement(tshirt);
			tshirt.click();
			cf.waitforpageload(15, "second");
			cf.viewelement(stock_status);
			String availability = stock_status.getText().trim();
			if(availability.equals("In stock")) {
				cf.mousehover(shirtimg);
				addcart_button.click();
			}
			else {
				System.out.println("Product availability status: "+availability);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void validatecart() {
		try {
			if(message.getText().startsWith("Product successfully")) {
				System.out.println(message.getText()+"and total price is:"+total_price.getText().trim());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
