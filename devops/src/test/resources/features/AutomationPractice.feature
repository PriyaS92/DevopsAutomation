#Author: Priya S
#CreatedOn: 11-Mar-2022

Feature: ECommerce Application E2E test
  User wants to perform end to end testing on the ecommerce application

	@functionaltest @TC001
  Scenario: TC001_Verify whether user is able to place the tshirt order in the store
    Given user logins to the application
   	When user want to purchase tshirts
   	Then product should be added to cart successfully
  
  @functionaltest @TC002
  Scenario Outline: TC002_Validate whether results are displayed using search functionality in homepage
  	Given user logins to the application
  	When user searches with the "<product>" in searchbox
  	Then results should be displayed corresponding to filter
  Examples:
  |product|
  |Printed Dress|