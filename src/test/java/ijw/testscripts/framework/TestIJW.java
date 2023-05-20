package ijw.testscripts.framework;

import org.testng.annotations.Test;
import ijw.pageaction.framework.FunctionalComponents;
import ijw.utils.framework.TestResult;
import junit.framework.Assert;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.apache.commons.mail.EmailException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import ijw.config.framework.Base;


public class TestIJW extends Base {

	public WebDriver driver = null;
	FunctionalComponents components;
	/*
	 * public TestIJW() throws Exception { //Logger log =
	 * LogManager.getLogger("loginLogout_TestCase"); components = new
	 * FunctionalComponents(); }
	 */

	@BeforeMethod
	public void initialize() throws Exception {
		driver = initializeWebDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}
	
	/* START from Here */
	@Test(priority = 1)
	public void Login_Logout_Scenario() throws Exception {

		Logger log = LogManager.getLogger("Validate Login-Logout Scenario");
		log.info("****** TC01 Starting to Validate Login-Logout testcase in IJW ******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.verifyItsjustwingsHomepage();
		components.loginClick(2);
		components.logoutClick();
	}
	@Test(priority = 2)
	public void VerifyOrderNowLinkinAboutpage() throws Exception {

		Logger log = LogManager.getLogger("Validate Order Now link in About Page");
		log.info("****** TC02 Starting to Validate Order Now link in IJW ******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.verifyItsjustwingsHomepage();
		components.aboutUsPage();
		Thread.sleep(5000);
	}

	@Test(priority = 3)   
	public void VerifySignupFromJoinNowPage() throws Exception {

		Logger log = LogManager.getLogger("Validate Signup From Join Now Page");
		log.info("****** TC03 Starting to Validate Signup From Join Now Page in IJW ******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.verifyItsjustwingsHomepage();
		components.joinNowPageSignUp(2);
		Thread.sleep(5000);
	}

	@Test(priority = 4)  
	public void VerifySignupFromProfile() throws Exception {

		Logger log = LogManager.getLogger("Validate Signup From Profile Page");
		log.info("****** TC04 Starting to Validate Signup From Profile Page in IJW ******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.verifyItsjustwingsHomepage();
		components.signUpPage(3);

	}
	@Test(priority = 5)
	public void VerifyAddNewAddressfromCheckoutPage() throws Exception {

		Logger log = LogManager.getLogger("Validate Add New Address From Checkout Page");
		log.info("****** TC05 Starting to Validate Add New Address From Checkout Page in IJW ******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.verifyItsjustwingsHomepage();
		components.loginClick(2);
		components.setLocation(2);
		components.addItemsToCart(2);
		components.increaseDecreaseItemQty();
		components.billingDetailswithAddNewAddress(2);
		
	}
	@Test(priority = 6)
	public void Verify_EditAutoPopulatedText_LoggedInUser() throws Exception {

		Logger log = LogManager.getLogger("Validate Logged in User is able to edit auto populated text");
		log.info("****** TC06 Starting to Validate Logged in User is able to edit auto populated text ******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.verifyItsjustwingsHomepage();
		components.loginClick(2);
		components.setLocation(2);
		components.addItemsToCart(3);
		components.fillPersonalDetails(2);
		components.fillBillingDetailsForGuest(2);
	}
	
	@Test(priority = 7)
	public void PlaceOrderWithPromo() throws Exception {

		Logger log = LogManager.getLogger("Validate User is able to place order with promo");
		log.info("****** TC07 Starting to Validate user is able to place order with promo ******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.verifyItsjustwingsHomepage();
		components.loginClick(2);
		components.setLocation(2);
		components.addItemsToCart(3);
		components.increaseDecreaseItemQty();
		components.addPromo(2);
		components.paymentDetails(3);
		components.placeOrder();
		Thread.sleep(3000);
		components.getSuccessfulMessage();
	}

	
	/*Ends Here */
	
	

	// Pickup-ASAP order by logged-in user. In checkout page user will select the
	// address already in user's account.//
	@Test(priority = 8)
	public void PlacePickUpAsap_LoggedInUser_ExistingBilingAddress() throws Exception {

		Logger log = LogManager.getLogger("PickupAsap for LoggedIn user");
		log.info("****** TC08 Starting to Validate PickupAsap testcase for LoggedIn user of IJW ******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.verifyItsjustwingsHomepage();
		components.loginClick(3);
		components.setLocation(2);
		components.addItemsToCart(2);
		components.fillBillingDetailsForGuest(3);
		components.selectPercentageTip(4);
		components.paymentDetails(2);
		components.placeOrder();
		Thread.sleep(3000);
		components.getSuccessfulMessage();

	}

	// Pickup-ASAP order by Guest user.//
	@Test(priority = 9)
	public void PlacePickUpAsap_GuestUser() throws Exception {

		Logger log = LogManager.getLogger("PickupAsap for Guest user");
		log.info("****** TC09 Starting to Validate PickupAsap testcase for Guest user of IJW ******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.verifyItsjustwingsHomepage();
		components.setLocation(3);
		components.addItemsToCart(3);
		components.fillPersonalDetails(2);
		components.fillBillingDetailsForGuest(3);
		components.selectCustomTip();
		components.paymentDetails(2);
		ArrayList<String> priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		Thread.sleep(3000);
		components.getSuccessfulMessage();
		ArrayList<String> priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);

	}

	// Pickup-LaterToday order by logged-in user. In checkout page user will add new
	// address for billing details//
	@Test(priority = 10)
	public void PlacePickUpLaterToday_LoggedInUser_AddNewBillingAddress() throws Exception {

		Logger log = LogManager.getLogger("Pickup-LaterToday for LoggedIn user");
		log.info(
				"***** TC10 Starting to Validate PickupLaterToday for LoggedIn user by adding new billing Address *****");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.verifyItsjustwingsHomepage();
		components.loginNewBillingAddress();
		components.setLocation(3);
		components.addItemsToCart(2);
		components.selectLaterToday(6);
		components.billingDetailswithAddNewAddress(3);
		components.selectCustomTip();
		components.paymentDetails(2);
		ArrayList<String> priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		Thread.sleep(3000);
		components.getSuccessfulMessage();
		ArrayList<String> priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);

	}

	// Pickup-LaterToday order by Guest user. //
	@Test(priority = 11)
	public void PlacePickUpLaterToday_GuestUser() throws Exception {

		Logger log = LogManager.getLogger("Pickup-LaterToday for Guest user");
		log.info("****** TC11 Starting to Validate PickupLater testcase for Guest user of IJW ******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.verifyItsjustwingsHomepage();
		components.setLocation(3);
		components.addItemsToCart(2);
		components.selectLaterToday(6);
		components.fillPersonalDetails(2);
		components.fillBillingDetailsForGuest(3);
		components.selectPercentageTip(2);
		components.paymentDetails(2);
		ArrayList<String> priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		Thread.sleep(3000);
		components.getSuccessfulMessage();
		ArrayList<String> priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);

	}

	// Delivery Asap by logged in user. In checkout page user will select the
	// address already in user's account.//
	@Test(priority = 12)
	public void PlaceDeliveryAsap_LoggedInUser_ExistingBilingAddress() throws Exception {

		Logger log = LogManager.getLogger("DeliveryAsap for LoggedIn user");
		log.info("****** TC12 Starting to Validate Delivery-Asap testcase for LoggedIn user of IJW ******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.verifyItsjustwingsHomepage();
		components.loginClick(3);
		components.setLocation(2);
		components.addItemsToCart(3);
		components.addDeliveryAddress(3);
		components.selectPercentageTip(4);
		components.fillBillingDetailsForGuest(3);
		components.paymentDetails(4); 
		ArrayList<String> priceBeforePlacingOrder = components.OrderTotal_Delivery();
		components.placeOrder();
		Thread.sleep(3000);
		components.getSuccessfulMessage();
		ArrayList<String> priceAfterPlacingOrder = components.returnOrderPrice_Delivery();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}

	// Delivery-Asap by Guest user. //
	@Test(priority = 13)
	public void PlaceDeliveryAsap_GuestUser() throws Exception {

		Logger log = LogManager.getLogger("Delivery-Asap for Guest user");
		log.info("****** TC13 Starting to Validate Delivery-Asap testcase for Guest user of IJW ******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.verifyItsjustwingsHomepage();
		components.setLocation(3);
		components.addItemsToCart(3);
		components.addDeliveryAddress(3);
		components.selectPercentageTip(2);
		components.fillPersonalDetails(2);
		components.fillBillingDetailsForGuest(3);
		components.paymentDetails(2);
		ArrayList<String> priceBeforePlacingOrder = components.OrderTotal_Delivery();
		components.placeOrder();
		Thread.sleep(3000);
		components.getSuccessfulMessage();
		ArrayList<String> priceAfterPlacingOrder = components.returnOrderPrice_Delivery();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}

	// Delivery-LaterToday by logged in user. In checkout page user will add new
	// address for billing details//
	@Test(priority = 14)
	public void PlaceDeliveryLaterToday_LoggedInUser_AddNewBillingAddress() throws Exception {

		Logger log = LogManager.getLogger("Delivery-LaterToday for LoggedIn user");
		log.info(
				"***** TC14 Starting to Validate Delivery-LaterToday for LoggedIn user by adding new billing Address *****");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.verifyItsjustwingsHomepage();
		components.loginNewBillingAddress();
		components.setLocation(2);
		components.addItemsToCart(2);
		components.addDeliveryAddress(2);
		components.selectPercentageTip(4);
		components.billingDetailswithAddNewAddress(2);
		components.paymentDetails(2);
		ArrayList<String> priceBeforePlacingOrder = components.OrderTotal_Delivery();
		components.placeOrder();
		Thread.sleep(3000);
		components.getSuccessfulMessage();
		ArrayList<String> priceAfterPlacingOrder = components.returnOrderPrice_Delivery();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);

	}

	// Delivery-LaterToday by Guest User.
	@Test(priority = 15)
	public void PlaceDeliveryLaterToday_GuestUser() throws Exception {

		Logger log = LogManager.getLogger("Delivery-LaterToday for Guest user");
		log.info("***** TC15 Starting to Validate Delivery-LaterToday for Guest User *****");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.verifyItsjustwingsHomepage();
		components.setLocation(3);
		components.addItemsToCart(2);
		components.addDeliveryAddress(3);
		components.selectLaterToday(6);
		components.selectCustomTip();
		components.fillPersonalDetails(2);
		components.fillBillingDetailsForGuest(3);
		components.paymentDetails(2);
		ArrayList<String> priceBeforePlacingOrder = components.OrderTotal_Delivery();
		components.placeOrder();
		Thread.sleep(3000);
		components.getSuccessfulMessage();
		ArrayList<String> priceAfterPlacingOrder = components.returnOrderPrice_Delivery();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);

	}
	
	

	@AfterMethod
	public void closeDriver() {

		driver.close();
		driver.quit();

	}

	@AfterSuite
	public void sendEmail() throws EmailException {

		try {
			TestResult.updateTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


/*-----------------------------------------------------------------------------------------------------------------------*/
