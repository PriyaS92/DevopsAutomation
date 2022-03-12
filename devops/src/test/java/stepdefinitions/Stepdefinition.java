package stepdefinitions;

import com.automation.devops.CommonFunctions;
import com.automation.devops.pages.Homepage;
import com.automation.devops.pages.Women;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Stepdefinition extends CommonFunctions{
	
	Homepage home = new Homepage(driver);
	Women womenpage = new Women(driver);
	
	@Given("user logins to the application")
	public void user_logins_to_the_application() {
		home.login();
	}
	
	@When("user want to purchase tshirts")
	public void user_want_to_purchase_tshirts() {
	    home.navigateto_womentab();
	    womenpage.addtocart();
	}
	
	@Then("product should be added to cart successfully")
	public void product_should_be_added_to_cart_successfully() {
		womenpage.validatecart();
	}
	
	@When("user searches with the {string} in searchbox")
	public void user_searches_with_the_in_searchbox(String product) {
	   home.search_functionality(product);
	}
	
	@Then("results should be displayed corresponding to filter")
	public void results_should_be_displayed_corresponding_to_filter() {
	   home.validate_searchresults();
	}
}
