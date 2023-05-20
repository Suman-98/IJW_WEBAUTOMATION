package ijw.pageaction.framework;

import org.openqa.selenium.By;

public class PageElements {

	// public static final String Something = "";
	public static final By IJWLogoXpath = By.xpath("(//*[@id=\"navbarTogglerHeader\"]//img)[1]");
	public static final By setLocationXpath = By.xpath("//span[contains(text(),'Set Location')]");
	public static final By searchBoxXpath = By.xpath("//div[@class='locationHolder']/input");
	public static final By clickSearchIconXpath = By.xpath("//div[@class='locationHolder']/button[2]");
	public static final By pickUpButtonXpath = By
			.xpath("//ul[@class='list-group hidden-xs hidden-sm ng-star-inserted']/li[2]/div/div[2]/button");
	public static final By deliveryButtonXpath = By
			.xpath("//ul[@class='list-group hidden-xs hidden-sm ng-star-inserted']/li[2]/div/div[2]/div/button");

	/*--------------------------------------------------------------------------------------------------------------------------*/
	public static final By loginDropdown = By.xpath("//*[@id='navHeader']/div[2]/nav/div[3]/button");
	public static final By SignUpDropdown = By.xpath("//a[contains(text(), 'Sign Up')]");
	
	// select Join Now page
	public static final By loginButton = By.xpath("//div[@class='web-signin']//a[text()='Log In']");
	public static final By aboutUsButton = By.xpath("//app-header//li[5]//a[1]");
	public static final By orderNowButton = By.xpath("(//button[@type='button'])[5]");
	public static final By joinNowButton = By.xpath("//a[contains(text(),'Join now')]");

	// login functionality from join now page
	public static final By emailTextBox = By.xpath("//input[@formcontrolname = 'email']");
	public static final By passwordTextBox = By
			.xpath("//input[@formcontrolname = 'password']");
	public static final By submitButton = By
			.xpath("//button[@class = 'sign-in']");

	// --------------------------------------------------------
	// order now from about us page
	public static final By AboutUs = By.xpath("//a[contains(text(),'About Us')]");
	public static final By OrderNow = By.xpath("//button[contains(text(),'Order now')]");
	
	// Join Now navigation bar
	public static final By JoinNow = By.xpath("//a[contains(text(),'Join now')]");
	public static final By JoinNowFirstName = By.xpath("//input[@id='firstName']");
	public static final By JoinNowLastName = By.xpath("//input[@id='lastName']");
	public static final By JoinNowEmail = By.xpath("//input[@id='email']");
	public static final By JoinNowConfirmEmail = By.xpath("//input[@id='confirmEmail']");
	public static final By JoinNowMobileNumber = By.xpath("//input[@id='mobileNumber']");
	public static final By JoinNowZipCode = By.xpath("//input[@id='zip']");
	public static final By JoinNowPassword = By.xpath("//input[@id='password']");
	public static final By CheckBoxCLick = By.xpath("//*[@id='sms-contest']");
	public static final By SignUp = By.xpath("//button[(@class ='sign-in')]");
	public static final By dropdownClick = By.xpath("//*[@id='navHeader']/div[2]/nav/div[3]/button"); //Change Xpath
	public static final By Signupclick = By.xpath("//a[contains(text(),'Sign Up')]");
	public static final By ProfileLoginclick = By.xpath("//button[contains(text(),'Log In')]/parent::a");
	public static final By welcomeText = By.xpath("//parent::button/label");
	public static final By userName = By.xpath("//div[3]/button/span");

	// Personal Details
	public static final By FirstName = By.xpath("//input[@formcontrolname='firstName']");
	public static final By LastName = By.xpath("//input[@formcontrolname='lastName']");
	public static final By Email = By.xpath("//input[@id='email']");
	public static final By MobileNumber = By.xpath("//input[@id='mobilephone']");

	// Logout User
	public static final By profileSignoutClick = By.xpath("//button[contains(text(),'Sign Out')]/ancestor::li//button");
	public static final By confirmLogout = By.xpath("//button[contains(text(),'Ok')]");

	// Set Location
	public static final By ijwLogo = By.xpath("//img[@alt='mobile-logo']");
	public static final By setLocation = By.xpath("//button[contains(@class,'set-location')]");
	public static final By locationSearchBar = By.xpath("//app-set-location//div[4]/input");
	public static final By searchIcon = By.xpath("//img[@alt='search']");
	public static final By clickOrderPickUp = By.xpath("//*[@id='location-list']//li[2]//button");
	public static final By closePopUp = By.xpath("//modal-container//img");

	// Add Items to Cart
	public static final By addToCartPopup = By.xpath("//*[@id=\'item-add-to-order\']");
	public static final By clickCheckoutButton = By
			.xpath("//button[contains(text(), 'Checkout')]/child::span");
	public static final By checkoutText = By.xpath("//h2[contains(text(), 'Checkout')]");
	public static final By increaseQty = By.xpath("//*[@id='pickup-time-details']/div/div[2]//app-cart-section//div[@class='col-1 ml-md-0 pl-0']/button//em");
	public static final By decreaseQty = By.xpath("//*[@id='pickup-time-details']/div/div[2]//app-cart-section//div[@class='col-1 pl-1 pl-md-0 pr-0']/button//em");
	public static final By itemQtyNumber = By.xpath("//*[@id='pickup-time-details']/div/div[2]//app-cart-section//button[@class='itemColor']");
	
	// SignOut Scenario
	public static final By SignOutButtonDropdowm = By.xpath("//button[@class='btn dropdown-toggle ng-tns-c17-3']");
	public static final By SignOutButtonClick = By.xpath("//button[@class='ng-tns-c17-3']");
	public static final By SignOutConfirm = By.xpath("//button[contains(text(),'Ok')]");

	//LaterToday Scenerio
	public static final By clickLaterToday = By.xpath("//button[(@class='dropdown-item ng-star-inserted') and contains(text(),'05:00 PM-05:15 PM')]");
	public static final By laterTodayDropdown  = By.xpath("//button[contains(@class,'dropdown-toggle btn')]");


	// Billing details in checkout page
	public static final By clickAddNewButton = By.xpath("//a[contains(text(),'Add New')]");
	public static final By clickChooseButton = By.xpath("//input[@formcontrolname= 'deliveryAddress1']");
	public static final By locationTextbox = By.xpath("//input[@formcontrolname= 'locationName']");
	public static final By addPromo = By.xpath("//*[@id=\"pickup-time-details\"]/div/div[2]/div/div/div/div[2]/div[3]/form/div/div[1]/input");
	public static final By applyButton = By.xpath("//button[@id = 'web-apply-link']");
	public static final By checkDiscount = By.xpath("//*[@id=\"pickup-time-details\"]/div/div[2]/div/div/div/div[2]/div[4]/div[1]/div[2]/div[2]");
	public static final By streetAddress = By.xpath("//input[@formcontrolname='addressLine1']");
	public static final By zipcode = By.xpath("//input [@type='text' and @formcontrolname='zipCode']");
	public static final By city = By.xpath("//input [@type='text' and @formcontrolname='city']");
	public static final By state = By.xpath("//input [@id='state']");
	public static final By new_streetAddress = By.xpath("//input[@placeholder='Street address or P.O. Box']");
	public static final By new_zipcode = By.xpath("//label[contains(text(),'Zip Code')]//parent::div//div[@class='manage-address-add-inputbox-control']/input");
	public static final By new_city = By.xpath("//label[contains(text(),'City')]//parent::div[@class='form-group']//input");
	public static final By new_state = By.xpath("//div[@class='form-group']//label[contains(text(),'State')]//parent::div//input");
	public static final By selectAddButton = By.xpath("//button[(@class='sign-in') or contains(text(),'ADD')]");


	//Tip Details
	public static final By selectTip = By.xpath("//button/ancestor::div[@class='sticky-cart-details']//button[@value='15']");
	public static final By clickOther = By.xpath("//div[@id='tip-details']/following::button[contains(text(),'Other')]");
	public static final By sendTip = By.xpath("//div[@id='tip-details']/following::input[@type='tel']");

	//Payment Details
	public static final By clickCreditDebitCard = By.xpath("//*[contains(text(),'Credit/Debit Card')]");
	public static final By cardHolderName = By.xpath("//input[@id='card-holder-name']");
	public static final By cardNumber = By.xpath("//input[@id='card-number']");
	public static final By cvvNumber = By.xpath("//input[@id='cvv-number']");
	public static final By expiryDate = By.xpath("//input[@id='expiry-date']");

	// Place Pickup ASAP by logged in user
	public static final By clickPlaceOrderButton = By.xpath("//*[@id='custom-panel-1']/div/div/div[5]/button");

	// Order Details- Need to shortened the XPATH
	public static final By getOrderID = By.xpath(
			"//h6[@class='pt-0 pb-1']");
	public static final By getTicketID = By.xpath(
			"//h6[@class='pb-1 ticket-id ng-star-inserted']");
	public static final By itemName = By.xpath("(//div[@class='cart-header'])[2]");
	public static final By subTotal_before = By.xpath("(//div[@class='sub-total-amt tax-detail font-bold'])[2]");
	public static final By tip_before = By.xpath("(//div[@class='total-tax-amt tax-detail'])[4]");
	public static final By Total_before = By.xpath("(//div[@class='total-amt ng-star-inserted'])[2]");

	public static final By subTotal_after = By.xpath("//div[@class='sub-total-amt tax-detail font-bold']");
	public static final By tip_after = By.xpath("(//div[@class='total-tax-amt tax-detail'])[2]");
	public static final By Total_after = By.xpath("//div[@class='total-amt ng-star-inserted']");

	// Place Delivery ASAP Order through log in user
	public static final By clickDeliveryRadioButton = By.xpath("//*[text()='Delivery']");
	public static final By deliveryTextBox = By.xpath("//input[@formcontrolname='location']");
	public static final By aptTextBox = By.xpath("//input[@formcontrolname='suiteNo']");
	public static final By chooseDeliveryAddress = By.xpath("//*[@id='delivery-timeslot-details']//div[1]/input");

	// Place Delivery ASAP Order as Guest User
	public static final By clickContinueAsGuestUser = By.xpath("//modal-container//div/app-account-sign-in//button[4]");

	// After Successful order
	public static final By successMessage = By.xpath("//h6");

}
