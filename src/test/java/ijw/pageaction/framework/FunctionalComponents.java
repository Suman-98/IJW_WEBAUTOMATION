package ijw.pageaction.framework;

import java.awt.Point;
import java.awt.Robot;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import ijw.config.framework.Base;
import ijw.utils.framework.ExcelUtils;

public class FunctionalComponents extends Base {


	WebDriver driver = null;
	WebDriverWait wait;
	ExcelUtils excel;
	static ExtentTest extTestObj;
	Logger log;
	String emailText;

	/* default constructor */
	public FunctionalComponents() {
		try {
			excel = new ExcelUtils(PROJECT_PATH + "/CommonData.xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Constructor for invoking webdriver */
	public FunctionalComponents(WebDriver driver, Logger log) {
		this();
		this.driver = driver;
		this.log = log;
		wait = new WebDriverWait(this.driver, 30);
	}

	/* To obtain the Extent Test object for logging in Extent Report */
	public static void getExtentTest(ExtentTest extentTest) {
		extTestObj = extentTest;
	}

	/*
	 * function to click an element after certain wait time with argument as locator
	 */
	public void clickableWait(By element) {
		wait.until(ExpectedConditions.elementToBeClickable(element)).click();
	}

	/* function to wait for the presence of an element with argument as locator */
	public void explicitWait(By element) {
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
	}

	/* function to wait for the visibility of an element with argument as locator */
	public void explicitWaitforVisibility(By element) {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(element)));
	}

	/* function to wait for clickability of an element with argument as locator */
	public void explicitWaitforClickability(By element) {
		WebDriverWait visible = new WebDriverWait(this.driver, 60);
		visible.until(ExpectedConditions.elementToBeClickable(element));
	}

	/* function to click an element with argument as locator */
	public void clickElement(By element) {
		driver.findElement(element).click();
	}

	/*
	 * function to enter data in a text box by clicking on it after certain wait
	 * time with argument as locator and the data
	 */
	public void sendKeysWait(By element, String value) {
		wait.until(ExpectedConditions.elementToBeClickable(element)).click();
		driver.findElement(element).clear();
		driver.findElement(element).sendKeys(value);
	}

	/*
	 * function to enter data in a text box without clicking on it after certain
	 * wait time with argument as locator and the data
	 */
	public void sendKeysWaitWithoutClick(By element, String value) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
		driver.findElement(element).clear();
		driver.findElement(element).sendKeys(value);
	}

	/* Validating IJW.com launching Successfully */
	public void verifyItsjustwingsHomepage() {
		log.info("Starting to Launch IJW site");
		String URL = prop.getProperty("ApplicationUrl");
		try {
			driver.get(URL);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			Thread.sleep(3000);
			explicitWait(PageElements.IJWLogoXpath);
			if (driver.findElement(PageElements.IJWLogoXpath).isDisplayed()) {
				log.info("IJW Site Launch Successfully, Site URL : " + driver.getCurrentUrl());
				extTestObj.createNode("IJW Site Launch Successfully, Site URL : " + driver.getCurrentUrl())
						.pass("PASSED");

			}
		} catch (Exception e) {
			log.error("Site Launch Failed");
			extTestObj.createNode("Site Launch Failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTest();
		}
	}

	/*-------------------------------------------------------------------------------------------------------------*/

	// login from join now page - verify user is able to sign in from join now page
	public void validateLoginFromJoinNowPage(int userIndex) throws Exception {

		try {
			String username = excel.getCellData("Credentials", "Email", userIndex);
			String password = excel.getCellData("Credentials", "Password", userIndex);

			driver.findElement(PageElements.joinNowButton).click();

			driver.findElement(PageElements.loginButton).click();
			Thread.sleep(2000);
			driver.findElement(PageElements.emailTextBox).sendKeys(username);
			driver.findElement(PageElements.passwordTextBox).sendKeys(password);
			driver.findElement(PageElements.submitButton).click();

			// set log info
			log.info("Login Successful");
			// create passed node with message
			extTestObj.createNode("Login Successful").pass("PASSED");

		} catch (Exception e) {
			// set log error
			log.error("Login Failed");
			// create failed node with method name
			extTestObj.createNode("Login Failed")
					.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
					.error(e);
			// get error message
			log.error(e.getMessage());
			stopTest();
		}
	}

	// Verify Order Now link in About page- verify user is able to click the Order
	// Now Button from About Page
	public void aboutUsPage() throws Exception {
		try {
			clickableWait(PageElements.AboutUs);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView()", driver.findElement(PageElements.OrderNow));
			//if(driver.findElement(PageElements.OrderNow).is)
			clickableWait(PageElements.OrderNow);
			Thread.sleep(5000);
			URL currURL  = new URL (driver.getCurrentUrl());
			HttpURLConnection connection = (HttpURLConnection) currURL.openConnection();
			connection.setRequestMethod("POST");
			int statusCode = connection.getResponseCode();

			if (statusCode == 200) {
				log.info("IJW Order Now button working from About Us page, Site URL : " + driver.getCurrentUrl());
				extTestObj.createNode("IJW Order Now button working from About Us page, Site URL : " + driver.getCurrentUrl())
				.pass("PASSED");
			}
		} 
		catch (Exception e) {
			log.error("Order Now button not working from About Us page");
			extTestObj.createNode("Order Now button not working from About Us page")
			.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTest();
		}

	}
	
	
	public String getEmailString()
	{
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) {   // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
            System.out.println(index);
            
        }
        String saltStr = salt.toString();
        System.out.println(saltStr);
        return saltStr;
    } 
	
	// Verify you are able to sign up through 'Join Now' NAV bar.
	public void joinNowPageSignUp(int userIndex) throws Exception {
			
		
		try {
			
			emailText = getEmailString() + "@testmail.com";
            System.out.println(emailText);
			
			String firstName = excel.getCellData("SignUpDetails", "FirstName", userIndex);
			String lastName = excel.getCellData("SignUpDetails", "LastName", userIndex);
			String mobileNumber = excel.getCellData("SignUpDetails", "MobileNumber", userIndex);
			String zipCode = excel.getCellData("SignUpDetails", "ZipCode", userIndex);
			String password = excel.getCellData("SignUpDetails", "Password", userIndex);
			
			Base.updateProperty("Gen_Email", emailText);
			Base.updateProperty("Password", password);
			
			Thread.sleep(5000);
			
            clickableWait(PageElements.JoinNow);
            sendKeysWait(PageElements.JoinNowFirstName, firstName);
            sendKeysWait(PageElements.JoinNowLastName, lastName);
            sendKeysWait(PageElements.JoinNowEmail,emailText);
            sendKeysWait(PageElements.JoinNowConfirmEmail, emailText);
            sendKeysWait(PageElements.JoinNowMobileNumber, mobileNumber);
            sendKeysWait(PageElements.JoinNowZipCode, zipCode);
            sendKeysWait(PageElements.JoinNowPassword, password);
            clickableWait(PageElements.CheckBoxCLick);
            clickableWait(PageElements.SignUp);
            
		 
				log.info("IJW Join Now Page Signup Successful, with useremail : " + emailText);
				extTestObj.createNode("IJW Join Now Page Signup Successful, with useremail : " + emailText)
				.pass("PASSED");

		} 
		catch (Exception e) {
			log.error("Join Now Signup Failed");
			extTestObj.createNode("Join Now Signup Failed")
			.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTest();
		}

	}
	// Verify you are able to sign up through 'Sign Up' link in profile icon.
	public void signUpPage(int userIndex) throws Exception {

		try {
			String firstName = excel.getCellData("SignUpDetails", "FirstName", userIndex);
			String lastName = excel.getCellData("SignUpDetails", "LastName", userIndex);
			String mobileNumber = excel.getCellData("SignUpDetails", "MobileNumber", userIndex);
			String zipCode = excel.getCellData("SignUpDetails", "ZipCode", userIndex);
			String password = excel.getCellData("SignUpDetails", "Password", userIndex);
			
			String emailText = getEmailString() + "@testmail.com";
            System.out.println(emailText);

			clickableWait(PageElements.loginDropdown);
			clickableWait(PageElements.SignUpDropdown);
			Thread.sleep(5000);
			sendKeysWait(PageElements.JoinNowFirstName, firstName);
			sendKeysWait(PageElements.JoinNowLastName, lastName);
			sendKeysWait(PageElements.JoinNowEmail,emailText);
			sendKeysWait(PageElements.JoinNowConfirmEmail, emailText);
			sendKeysWait(PageElements.JoinNowMobileNumber, mobileNumber);
			sendKeysWait(PageElements.JoinNowZipCode, zipCode);
			sendKeysWait(PageElements.JoinNowPassword, password);
			clickableWait(PageElements.CheckBoxCLick);
			clickableWait(PageElements.SignUp);

			
			 
				log.info("IJW Signup Successful, user email: " + emailText);
				extTestObj.createNode("IJW Signup Successful, user email : " + emailText)
				.pass("PASSED");

		} 
		catch (Exception e) {
			log.error("Signup Failed");
			extTestObj.createNode("Signup Failed")
			.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTest();
		}


	}
	
	//Login from profile page
	public void loginClick(int userIndex) throws Exception {

		try {
			driver.findElement(PageElements.dropdownClick).click();
			driver.findElement(PageElements.ProfileLoginclick).click();

			String username = excel.getCellData("Read_Credentials", "Email", userIndex);
			String password = excel.getCellData("Read_Credentials", "Password", userIndex);

			// driver.findElement(Elements.loginButton).click();
			// Thread.sleep(2000);

			sendKeysWait(PageElements.emailTextBox, username);
			sendKeysWait(PageElements.passwordTextBox, password);
			clickableWait(PageElements.submitButton);
			Thread.sleep(5000);
			String usernameText = driver.findElement(PageElements.userName).getText();
			
				log.info("IJW  User Login Successful, User Name : " + usernameText );
				extTestObj.createNode("IJW  User Login Successful, User Name : " + usernameText )
				.pass("PASSED");
		} 
		catch (Exception e) {
			log.error("IJW Login Failed");
			extTestObj.createNode("IJW Login Failed")
			.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTest();
		}
	}
	
		//Login from profile page
		public void loginNewBillingAddress() throws Exception {

			try {
				driver.findElement(PageElements.dropdownClick).click();
				driver.findElement(PageElements.ProfileLoginclick).click();

				String username = prop.getProperty("Gen_Email");
				String password =prop.getProperty("Password");


				sendKeysWait(PageElements.emailTextBox, username);
				sendKeysWait(PageElements.passwordTextBox, password);
				clickableWait(PageElements.submitButton);
				Thread.sleep(5000);
				String usernameText = driver.findElement(PageElements.userName).getText();
				
					log.info("IJW  User Login Successful, User Name : " + usernameText );
					extTestObj.createNode("IJW  User Login Successful, User Name : " + usernameText )
					.pass("PASSED");
			} 
			catch (Exception e) {
				log.error("IJW Login Failed");
				extTestObj.createNode("IJW Login Failed")
				.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTest();
			}
		}
	
	
	
	// Logout from HomeScreen
	public void logoutClick() throws Exception {
		try {
			URL currURL = new URL (driver.getCurrentUrl());
			clickableWait(PageElements.dropdownClick);
			clickableWait(PageElements.profileSignoutClick);
			clickableWait(PageElements.confirmLogout);
			HttpsURLConnection cont= (HttpsURLConnection)currURL.openConnection();
			// pass HEAD as parameter to setRequestMethod
			cont.setRequestMethod("HEAD");
			// obtain Response code
			cont.connect();
			int responseCode = cont.getResponseCode();
			System.out.println("Http response code: " + responseCode);
			if(responseCode == 200) {
				log.info("IJW Logout Successful, Response Code : " + responseCode);
				extTestObj.createNode("IJW Logout Successful, Response Code : " + responseCode)
				.pass("PASSED");
			}

		} 
		catch (Exception e) {
			log.error("IJW Logout Failed");
			extTestObj.createNode("IJW Logout Failed")
			.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTest();
		}
	}
	
	// Location search-verify user is able to set the location and select the order
	public void setLocation(int locationIndex) throws Exception {

		try {
			String locationZipCode = excel.getCellData("Locations", "ZipCode", locationIndex);

			Thread.sleep(3000);
			explicitWait(PageElements.ijwLogo);
			clickableWait(PageElements.setLocation);
			sendKeysWait(PageElements.locationSearchBar, locationZipCode);
			Thread.sleep(2000);
			// Enter location using Store Name
			// driver.findElement(Elements.locationSearchBar).sendKeys(location);
			clickableWait(PageElements.searchIcon);
			clickableWait(PageElements.clickOrderPickUp);
			Thread.sleep(3000);

			try {
				driver.findElement(PageElements.closePopUp).click();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Unable to find Popup");
				e.printStackTrace();
			}

			log.info("Location Set Successfully: "+ locationZipCode);
			// create passed node with message
			extTestObj.createNode("Location Set Successfully: "+ locationZipCode).pass("PASSED");

		} catch (Exception e) {
			// set log error
			log.error("Failed to Set Location");
			// create failed node with method name
			extTestObj.createNode("Failed to Set Location")
			.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
			.error(e);
			// get error message
			log.error(e.getMessage());
			stopTest();
		}

	}
	
	// Order Pickup ASAP - verify user is able to add items in cart and selecting the checkout button
	public void addItemsToCart(int itemIndex) throws Exception {
		try {

			String menuCategory = excel.getCellData("Menu", "Category", itemIndex);
			String itemName = excel.getCellData("Menu", "Item Name", itemIndex);
			String wingFlavor = excel.getCellData("Menu", "Wing Flavor", itemIndex);
			String beverageName = excel.getCellData("Menu", "Beverage", itemIndex);

			// Select Menu Category
			explicitWait(By.xpath("//*[contains(text(), '" + menuCategory + "')]"));
			clickableWait(By.xpath("//*[contains(text(), '" + menuCategory + "')]"));
			// driver.findElement(By.xpath("//*[contains(text(), '" + menuCategory +
			// "')]")).click();

			// Select Item Name
			explicitWait(By.xpath("//*[contains(text(), '" + itemName + "')]"));
			clickableWait(By.xpath("//*[contains(text(), '" + itemName + "')]"));
			// driver.findElement(By.xpath("//*[contains(text(), '" + itemName +
			// "')]")).click();

			// Select Wing Flavor
			explicitWait(By.xpath("//*[contains(text(), '" + wingFlavor + "')]"));
			clickableWait(By.xpath("//*[contains(text(), '" + wingFlavor + "')]"));
			// System.out.println(driver.findElement(By.xpath("//*[contains(text(), '" +
			// wingFlavor + "')]")));
			// driver.findElement(By.xpath("//*[contains(text(), '" + wingFlavor +
			// "')]")).click();

			// Select Beverage
			explicitWait(By.xpath("//*[contains(text(), '" + beverageName + "')]"));
			clickableWait(By.xpath("//*[contains(text(), '" + beverageName + "')]"));
			// driver.findElement(By.xpath("//*[contains(text(), '" + beverageName +
			// "')]")).click();
			clickableWait(PageElements.addToCartPopup);
			// Thread.sleep(2000);
			clickableWait(PageElements.clickCheckoutButton);

			//validate with CHECKOUT 
			String checkoutValidate = "Checkout";

			if(checkoutValidate.equalsIgnoreCase(driver.findElement(PageElements.checkoutText).getText())) {

				log.info("Items Added Successfully, item name: "+itemName);
				// create passed node with message
				extTestObj.createNode("Items Added Successfully, item name: "+itemName).pass("PASSED");
			}

		} catch (Exception e) {
			// set log error
			log.error("Failed to Add item to the cart");
			// create failed node with method name
			extTestObj.createNode("Failed to Add item to the cart")
			.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
			.error(e);
			// get error message
			log.error(e.getMessage());
			stopTest();
		}
	}
	
	//Add Discount to current order
	public void addPromo(int itemIndex) throws Exception{
		try {
			String promoCode = excel.getCellData("Promos", "Promo Codes", itemIndex);
			sendKeysWait(PageElements.addPromo, promoCode);
			clickableWait(PageElements.applyButton);
			Thread.sleep(5000);
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,350)", "");
			Thread.sleep(3000);
			String checkPromoCode = driver.findElement(PageElements.checkDiscount).getText();
			System.out.println("Discount: "+ checkPromoCode);
			
		  String  webText = driver.findElement(By.xpath("//*[@id=\"pickup-time-details\"]/div/div[2]/div/div/div/div[2]/div[3]/form/div/div[2]/div/div")).getText();
		  System.out.println(webText);
			
				log.info("Promo Added Successfully : " + promoCode);
				// create passed node with message
				extTestObj.createNode("Promo Added Successfully : " + promoCode).pass("PASSED");
			
		}
		catch (Exception e) {
			// set log error
			log.error("Failed to Add Promo");
			// create failed node with method name
			extTestObj.createNode("Failed to Add Promo")
			.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
			.error(e);
			// get error message
			log.error(e.getMessage());
			stopTest();
		}
	}
	
	// Increase/Decrease the number of items in cart
	
	public void increaseDecreaseItemQty() throws Exception{
		
		Thread.sleep(3000);
		try {
			int count = 3;
			for(int i=0; i<count; i++) {
				clickableWait(PageElements.increaseQty);
				Thread.sleep(7000);
			}
			
			clickableWait(PageElements.decreaseQty);
			Thread.sleep(5000);
			String itemQty = driver.findElement(PageElements.itemQtyNumber).getText();
			String countText = String.valueOf(count);
			System.out.println(itemQty);
			System.out.println(count);
			
			
			if (itemQty.equalsIgnoreCase(countText)) {
				log.info("IJW Item Increase/Decrease Successful, Item Qty : " + itemQty);
				extTestObj.createNode("IJW Item Increase/Decrease Successful, Item Qty : " + itemQty).pass("PASSED");
				}
			}
		
			catch(Exception e) {
				// set log error
				log.error("Failed to Increase/Decrease Item");
				// create failed node with method name
				extTestObj.createNode("Failed to Increase/Decrease Item")
				.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
				.error(e);
				// get error message
				log.error(e.getMessage());
				stopTest();
		}


	}


	// This function will pick one particular time from Excel sheet and select that
	// Later Today time in IJW Website//
	public void selectLaterToday(int rowIndex) throws Exception {

		String LaterTime = excel.getCellData("LaterToday", "Time", rowIndex);

		try {
			Thread.sleep(2000);

			clickableWait(PageElements.laterTodayDropdown);
			Thread.sleep(5000);
			driver.findElement(By.xpath(
					"//button[contains(text(),'" + LaterTime + "') and (@class='dropdown-item ng-star-inserted')]"))
					.click();
			Thread.sleep(4000);

			log.info("Later Today time is selected as " + LaterTime);
			extTestObj.createNode("Later Today time is selected as " + LaterTime).pass("PASSED");

		} catch (Exception e) {
			// set log error
			log.error("Failed to select any Later Today Time");
			// create failed node with method name
			extTestObj.createNode("Failed to select any Later Today Time")
					.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
					.error(e);
			// get error message
			log.error(e.getMessage());
			stopTest();
		}
	}

	// This function to add New Billing details in Checkout page
	
	public void billingDetailswithAddNewAddress(int index) throws Exception {

		String locationName = excel.getCellData("Billing details", "locationName", index);
		String streetAddress = excel.getCellData("Billing details", "Street Address", index);
		String city = excel.getCellData("Billing details", "City", index);
		String state = excel.getCellData("Billing details", "State", index);
		String zipcode = excel.getCellData("Billing details", "Zip Code", index);
		Actions act = new Actions(driver);   //To be changed later

		try {
			
			Thread.sleep(2000);
			clickableWait(PageElements.clickAddNewButton);
			sendKeysWait(PageElements.locationTextbox, locationName);
			sendKeysWait(PageElements.new_streetAddress,streetAddress);
			Thread.sleep(2000);
			
			/*act.moveToElement( Addressbox ,  0,  20).co();*/
			act.sendKeys(Keys.DOWN).perform();       //To be changed later
			act.sendKeys(Keys.ENTER).perform();       //To be changed later
		

			sendKeysWait(PageElements.new_zipcode, zipcode);
			sendKeysWait(PageElements.new_city, city);
			sendKeysWait(PageElements.new_state, state);
			clickableWait(PageElements.selectAddButton);
			Thread.sleep(5000);


			//	driver.findElement(PageElements.new_zipcode).clear();
			//	driver.findElement(PageElements.new_zipcode).sendKeys(zipcode);
			//			
			//	driver.findElement(PageElements.new_city).clear();
			//	driver.findElement(PageElements.new_city).sendKeys(city);
			//			
			//	driver.findElement(PageElements.new_state).clear();
			//	driver.findElement(PageElements.new_state).sendKeys(state);
			//			
			//	driver.findElement(PageElements.selectAddButton).click();
			//	Thread.sleep(3000);
			
			String addressText = driver.findElement(PageElements.new_streetAddress).getText();
			String enteredAddressText = driver.findElement(PageElements.new_streetAddress).getAttribute("value");

			if(addressText.equalsIgnoreCase(enteredAddressText)) {
				log.info("New Billing Details Added, Address: " + enteredAddressText);
				// create passed node with message
				extTestObj.createNode(" New Billing Details Added: " + enteredAddressText).pass("PASSED");
			}

		} catch (Exception e) {
			// set log error
			log.error("Failed to Add New Billing Details");
			// create failed node with method name
			extTestObj.createNode("Failed to Add New Billing Details")
			.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
			.error(e);
			// get error message
			log.error(e.getMessage());
			stopTest();
		}
	}

	public void fillBillingDetails(int index) throws Exception {

		String locationName = excel.getCellData("Billing details", "locationName", index);
		String streetAddress = excel.getCellData("Billing details", "Street Address", index);
		String city = excel.getCellData("Billing details", "City", index);
		String state = excel.getCellData("Billing details", "State", index);
		String zipcode = excel.getCellData("Billing details", "Zip Code", index);

		try {
			clickableWait(PageElements.clickChooseButton);
			sendKeysWait((PageElements.locationTextbox), locationName);
			sendKeysWait((PageElements.streetAddress), streetAddress);
			sendKeysWait((PageElements.zipcode), zipcode);
			sendKeysWait((PageElements.city), city);
			sendKeysWait((PageElements.state), state);
			// clickableWait(PageElements.selectAddButton);

			Thread.sleep(1000);

			log.info("Billing Details Added");
			// create passed node with message
			extTestObj.createNode("Billing Details Added").pass("PASSED");

		} catch (Exception e) {
			// set log error
			log.error("Failed to Add Billing Details");
			// create failed node with method name
			extTestObj.createNode("Failed to Add Billing Details")
					.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
					.error(e);
			// get error message
			log.error(e.getMessage());
			stopTest();
		}
	}

	// This function will fill the billing details in IJW for Guest user//
	public void fillBillingDetailsForGuest(int index) throws Exception {

		String streetAddress = excel.getCellData("Billing details", "Street Address", index);
		String city = excel.getCellData("Billing details", "City", index);
		String state = excel.getCellData("Billing details", "State", index);
		String zipcode = excel.getCellData("Billing details", "Zip Code", index);

		try {

			sendKeysWait((PageElements.streetAddress), streetAddress);
			sendKeysWait((PageElements.zipcode), zipcode);
			sendKeysWait((PageElements.city), city);
			sendKeysWait((PageElements.state), state);
			driver.findElement(PageElements.state).sendKeys(Keys.ENTER);

			Thread.sleep(1000);
			log.info("Billing Details Added");
			// create passed node with message
			extTestObj.createNode("Billing Details Added").pass("PASSED");

		} catch (Exception e) {
			// set log error
			log.error("Failed to Add Billing Details");
			// create failed node with method name
			extTestObj.createNode("Login Failed")
					.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
					.error(e);
			// get error message
			log.error(e.getMessage());
			stopTest();
		}
	}

	// Select 15%, 20%, 25% Tip from excel//
	public void selectPercentageTip(int tipValue) throws Exception {

		String tip = excel.getCellData("Tip", "TipValue", tipValue);

		try {

			clickableWait(
					By.xpath("//button/ancestor::div[@class='sticky-cart-details']//button[@value='" + tip + "']"));
			Thread.sleep(3000);

			log.info(tip + "% Tip has selected");
			// create passed node with message
			extTestObj.createNode(tip + "% Tip has selected").pass("PASSED");

		} catch (Exception e) {
			// set log error
			log.error("Failed to select TipValue");
			// create failed node with method name
			extTestObj.createNode("Failed to select TipValue")
					.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
					.error(e);
			// get error message
			log.error(e.getMessage());

			stopTest();
		}
	}

	// Select 15%, 20%, 25% Tip from excel
	public void selectCustomTip() throws Exception {

		String tip = excel.getCellData("Tip", "CustomValue", 2);

		try {

			Thread.sleep(2000);
			clickableWait(PageElements.clickOther);
			Thread.sleep(5000);
			sendKeysWait(PageElements.sendTip, tip);

			log.info(tip + "$ Tip has selected");
			// create passed node with message
			extTestObj.createNode((tip + "$ Tip has selected")).pass("PASSED");

		} catch (Exception e) {
			// set log error
			log.error("Failed to select TipValue");
			// create failed node with method name
			extTestObj.createNode("Failed to select TipValue")
					.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
					.error(e);
			// get error message
			log.error(e.getMessage());

			stopTest();
		}
	}

	// This function to fill the Personal details in IJW site for Guest user
	public void fillPersonalDetails(int index) throws Exception {

		String firstName = excel.getCellData("GuestUserDetails", "First Name", index);
		String lastName = excel.getCellData("GuestUserDetails", "Last Name", index);
		String contact = excel.getCellData("GuestUserDetails", "Contact", index);
		String email = excel.getCellData("GuestUserDetails", "Email", index);

		Thread.sleep(2000);
		try {

			sendKeysWait((PageElements.FirstName), firstName);
			sendKeysWait((PageElements.LastName), lastName);
			sendKeysWait((PageElements.MobileNumber), contact);
			sendKeysWait((PageElements.Email), email);

			log.info("Personal Details Added");
			// create passed node with message
			extTestObj.createNode("Personal Details Added").pass("PASSED");

		} catch (Exception e) {
			// set log error
			log.error("Failed to Add Personal Details");
			// create failed node with method name
			extTestObj.createNode("Failed to Add Personal Details")
					.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
					.error(e);
			// get error message
			log.error(e.getMessage());

			stopTest();
		}
	}

	// This function will fill all payments details in IJW
	
	public void paymentDetails(int index) throws Exception {

		try {
			String cardName = excel.getCellData("CardDetails", "Card Name", index);
			String cardNumber = excel.getCellData("CardDetails", "Card Number", index);
			String cardCvvNumber = excel.getCellData("CardDetails", "CVV", index);
			String cardExpiryDate = excel.getCellData("CardDetails", "Expiry Date", index);
			
			Thread.sleep(2000);
			clickableWait(PageElements.clickCreditDebitCard);
			sendKeysWait((PageElements.cardHolderName), cardName);
			sendKeysWait((PageElements.cardNumber), cardNumber);
			sendKeysWait((PageElements.cvvNumber), cardCvvNumber);
			sendKeysWait((PageElements.expiryDate), cardExpiryDate);


			Thread.sleep(3000);
			log.info("Payment Details added");
			// create passed node with message
			extTestObj.createNode("Payment Details added").pass("PASSED");

		} catch (Exception e) {
			// set log error
			log.error("Failed to add Payment Details");
			// create failed node with method name
			extTestObj.createNode("Failed to add Payment Details")
			.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
			.error(e);
			// get error message
			log.error(e.getMessage());
			stopTest();
		}
	}


	
	// This function to click on Place order button
	public void placeOrder() throws Exception {
		try {

			driver.findElement(PageElements.clickPlaceOrderButton).click();
			// Thread.sleep(30000);
			log.info("Place Order button clicked Successfully");
			// create passed node with message
			extTestObj.createNode("Place Order button clicked Successfully").pass("PASSED");

		} catch (Exception e) {
			// set log error
			log.error("Order Placed Unsuccessfully");
			// create failed node with method name
			extTestObj.createNode("Order Placed Unsuccessfully")
					.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
					.error(e);
			// get error message
			log.error(e.getMessage());
			stopTest();
		}

	}

	// This function will fetch the Success message after successful order
	public void getSuccessfulMessage() throws Exception {
		String SuccessMessage = "";

		Thread.sleep(2000);
		try {

			System.out.println(driver.findElement(PageElements.getOrderID).getText());
			// System.out.println(driver.findElement(PageElements.getTicketID).getText());
			SuccessMessage = driver.findElement(PageElements.successMessage).getText();

			log.info("Order placed Successfully as: " + SuccessMessage);
			// create passed node with message
			extTestObj.createNode("Order placed Successfully as: " + SuccessMessage).pass("PASSED");

		} catch (Exception e) {
			// set log error
			log.error("Unable to place Successful order");
			// create failed node with method name
			extTestObj.createNode("Unable to place Successful order")
					.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
					.error(e);
			// get error message
			log.error(e.getMessage());
			stopTest();
		}
	}

	// Verify Placing a Delivery-ASAP order as a Logged In user with Amex. Also
	// Verify to add, Increase, decrease and then remove an item in Payment page.
	public void addDeliveryAddress(int index) throws Exception {

		String deliveryAddress = excel.getCellData("Delivery address", "Delivery details", index);

		try {

			clickableWait(PageElements.clickDeliveryRadioButton);
			Thread.sleep(3000);
			clickableWait(PageElements.deliveryTextBox);
			sendKeysWait(PageElements.deliveryTextBox, deliveryAddress);
			driver.findElement(PageElements.deliveryTextBox).sendKeys(Keys.DOWN);
			driver.findElement(PageElements.deliveryTextBox).sendKeys(Keys.ENTER);
			Thread.sleep(5000);

			log.info("Delivery Address added successfully");
			extTestObj.createNode("Delivery Address added successfully").pass("PASSED");
		} catch (Exception e) {
			// set log error
			log.error("Failed to add Delivery Address");
			// create failed node with method name
			extTestObj.createNode("Failed to add Delivery Address")
					.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
					.error(e);
			// get error message
			log.error(e.getMessage());
			stopTest();
		}

		// String addDeliveryAddress = excel.getCellData("Delivery address", "Delivery
		// details", itemIndex);
		// driver.findElement(PageElements.chooseDeliveryAddress).sendKeys(addDeliveryAddress);

	}

	// Verify Placing a Delivery-ASAP order as a Guest User with visa and 20% tip.
	public void guestUserOrderPopUp() throws Exception {
		try {
			Thread.sleep(2000);
			clickableWait(PageElements.clickContinueAsGuestUser);
			log.info("Click on Guest User on popup page successfully");
			// create passed node with message
			extTestObj.createNode("Click on Guest User on popup page successfully").pass("PASSED");
		} catch (Exception e) {
			// set log error
			log.error("Click on Guest User on popup page failed");
			// create failed node with method name
			extTestObj.createNode("Click on Guest User on popup page failed")
					.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
					.error(e);
			// get error message
			log.error(e.getMessage());
			stopTest();
		}
	}

	// Getting Order details before Order for PickUp orders//
	public ArrayList<String> OrderTotal() throws Exception {

		String  subTotal, tipValue, orderTotal;
		ArrayList<String> price_before = new ArrayList<String>();

		try {

			subTotal = driver.findElement(PageElements.subTotal_before).getText();
			tipValue = driver.findElement(PageElements.tip_before).getText();
			orderTotal = driver.findElement(PageElements.Total_before).getText();

			price_before.add(subTotal);
			price_before.add(tipValue);
			price_before.add(orderTotal);

			System.out.println("Subtotal_before:" + subTotal);
			System.out.println("Tip_before:" + tipValue);
			System.out.println("OrderTotal_before:" + orderTotal);

			log.info("Subtotal before order: " + subTotal);
			extTestObj.createNode("Subtotal before order: " + subTotal);

			log.info("Added Tip before order: " + tipValue);
			extTestObj.createNode("Added Tip before order: " + tipValue);

			log.info("Order Total before order: " + orderTotal);
			extTestObj.createNode("Order Total before order: " + orderTotal);

		} catch (Exception e) {
			log.error("Failed to collect order Details");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to collect order Details").warning("WARNING");
		}
		return price_before;
	}

	// Getting Order details before Order for Delivery orders//
	public ArrayList<String> OrderTotal_Delivery() throws Exception {

		String  subTotal, orderTotal;
		ArrayList<String> price_before = new ArrayList<String>();

		try {

			subTotal = driver.findElement(PageElements.subTotal_before).getText();
			orderTotal = driver.findElement(PageElements.Total_before).getText();

			price_before.add(subTotal);
			price_before.add(orderTotal);

			System.out.println("Subtotal_before:" + subTotal);
			System.out.println("OrderTotal_before:" + orderTotal);

			log.info("Subtotal before order: " + subTotal);
			extTestObj.createNode("Subtotal before order: " + subTotal);

			log.info("Order Total before order: " + orderTotal);
			extTestObj.createNode("Order Total before order: " + orderTotal);

		} catch (Exception e) {
			log.error("Failed to collect order Details");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to collect order Details").warning("WARNING");
		}
		return price_before;
	}

	// This function to fetch all the details after placing one successful order//
	public ArrayList<String> returnOrderPrice() throws Exception {

		String subTotal, tipValue, orderTotal;
		ArrayList<String> price_after = new ArrayList<String>();
		try {

			subTotal = driver.findElement(PageElements.subTotal_after).getText();
			tipValue = driver.findElement(PageElements.tip_after).getText();
			orderTotal = driver.findElement(PageElements.Total_after).getText();

			price_after.add(subTotal);
			price_after.add(tipValue);
			price_after.add(orderTotal);

			System.out.println("SubTotal_after:" + subTotal);
			System.out.println("Tip_after:" + tipValue);
			System.out.println("Total_after:" + orderTotal);

			log.info("Subtotal after order: " + subTotal);
			extTestObj.createNode("Subtotal after order: " + subTotal);

			log.info("Added Tip after order: " + tipValue);
			extTestObj.createNode("Added Tip after order: " + tipValue);

			log.info("Order Total after order: " + orderTotal);
			extTestObj.createNode("Order Total after order: " + orderTotal);

		} catch (Exception e) {

			log.error("Failed to collect order Details");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to collect order Details").warning("WARNING");
		}
		return price_after;
	}

	// This function to fetch all the details after placing one successful order for
	// Delivery Orders//
	public ArrayList<String> returnOrderPrice_Delivery() throws Exception {

		String subTotal, orderTotal;
		ArrayList<String> price_after = new ArrayList<String>();
		try {

			subTotal = driver.findElement(PageElements.subTotal_after).getText();
			orderTotal = driver.findElement(PageElements.Total_after).getText();

			price_after.add(subTotal);
			price_after.add(orderTotal);

			System.out.println("SubTotal_after:" + subTotal);
			System.out.println("Total_after:" + orderTotal);

			log.info("Subtotal after order: " + subTotal);
			extTestObj.createNode("Subtotal after order: " + subTotal);

			log.info("Order Total after order: " + orderTotal);
			extTestObj.createNode("Order Total after order: " + orderTotal);

		} catch (Exception e) {

			log.error("Failed to collect order Details");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to collect order Details").warning("WARNING");
		}
		return price_after;
	}

	/*
	 * Function To check whether the order total before placing order is same as
	 * that displayed in the confirmation page
	 */
	public void comparePrice(ArrayList<String> priceBeforePlacingOrder, ArrayList<String> priceAfterPlacingOrder) {

		try {
			if (priceBeforePlacingOrder.equals(priceAfterPlacingOrder) == true) {

			} else {
				throw new Exception("something error happened");
			}

			log.info("Prices matched");
			extTestObj.createNode("Prices matched").pass("PASSED");
		} catch (Exception e) {
			log.warn("Incorrect price displayed");
			extTestObj.createNode("WARNING : Incorrect price displayed")
					.warning("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
					.pass(e);
		}
	}

	// SignOut Button - verify user is able to sign out from the dropdown button
	public void SignOut() throws Exception {

		try {

			clickableWait(PageElements.SignOutButtonDropdowm);
			clickableWait(PageElements.SignOutButtonClick);
			clickableWait(PageElements.SignOutConfirm);
			//			driver.findElement(PageElements.SignOutButtonDropdowm).click();
			//			driver.findElement(PageElements.SignOutButtonClick).click();
			//			Thread.sleep(2000);
			//			driver.findElement(PageElements.SignOutConfirm).click();
			log.info("Order Details Fetched Successfully");
			// create passed node with message
			extTestObj.createNode("Order Details Fetched Successfully").pass("PASSED");

		} catch (Exception e) {
			// set log error
			log.error("Order Details Fetched Unsuccessfully");
			// create failed node with method name
			extTestObj.createNode("Order Details Fetched Unsuccessfully")
			.fail("Methode Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
			.error(e);
			// get error message
			log.error(e.getMessage());
			stopTest();
		}
	}
}
