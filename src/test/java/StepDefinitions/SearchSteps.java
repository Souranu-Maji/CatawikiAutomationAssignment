package StepDefinitions;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import PageUtilities.verificationUtils;
import PageUtilities.waitUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Pages.cookiesBar;
import Pages.homePage;
import Pages.lotProductPage;
import Pages.searchResultsPage;

import java.io.IOException;
import java.time.Duration;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SearchSteps {
    
    WebDriver driver = null;
    private static final Logger log = LogManager.getLogger(SearchSteps.class);
    private final int sleepTimer = 2;

    homePage homePage;
    cookiesBar cookiesBar;
    searchResultsPage searchResultsPage;
    lotProductPage lotProductPage;

    @Before
    public void browserSetUp(){
        // set the property before creating the driver instance
        String driverPath = System.getProperty("user.dir") + "/src/test/resources/Drivers/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverPath);
        
        // Initialize Chrome Driver
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Initialize Page Objects
        homePage = new homePage(driver);
        cookiesBar = new cookiesBar(driver);
        searchResultsPage = new searchResultsPage(driver);
        lotProductPage = new lotProductPage(driver);
    }

    @After
    public void browserTeardown(){
        if (driver != null){
            driver.quit();
        }
    }

    @Given("User is on the Catawiki homepage")
    public void User_is_on_the_Catawiki_homepage() throws InterruptedException {
        log.info("Launching the browser and navigating to the Catawiki homepage");
        driver.get("https://www.catawiki.com/");
        Thread.sleep(Duration.ofSeconds(sleepTimer)); // Wait for the page to load completely

        // Handle the cookie consent bar
        WebDriverWait waitForCookieBar = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForCookieBar.until(ExpectedConditions.elementToBeClickable(cookiesBar.declineCookiesButton));
        cookiesBar.declineCookies();

        // Verifying the existence of the Category Navigation Search bar on HomePage
        verificationUtils.verifyElementPresenceInDOM(driver,"Category Navigation Search bar Not Present", By.cssSelector("div[data-sentry-component='FeedAndCategoryNavigation"));
        Assert.assertTrue("User not on the HomePage", homePage.verifyHomepageTitle());
        log.info("Catawiki HomePage Title Verified");
    }

    @When("User searches for {string}")
    public void User_searches_for(String searchTerm) {
        homePage.searchForProductName(searchTerm);
    }

    @When("Verify the designated {string} page details")
    public void Verify_the_designated_page_details(String searchString) throws InterruptedException {
        waitUtils.waitForElementToBeVisible(driver, searchResultsPage.searchResultsTitle);
        Thread.sleep(5000);
        // Validating the LOT page contains the search term in the Title
        log.info("Search Item Page Title:" +searchResultsPage.searchResultsTitle.getText());
        Assert.assertTrue("Page title does not contain the search term", searchResultsPage.searchResultsTitle.getText().toLowerCase().contains(searchString.toLowerCase()));
    }

    /**
     * Clicks on the specified lot (by index) from the search results
     * after verifying that results are available.
     */
    @Then("Click on Lot {string} in search results page and verify")
    public void Click_on_lot_in_search_results_page_and_verify(String s) {
        log.info("Total lots found on first Seearch Results Page:" +searchResultsPage.getProductsCountPerPage());
        // Ensure at least one result exists before attempting to click
        if(searchResultsPage.getProductsCountPerPage() > 0) {
            searchResultsPage.searchResultsItems.get(Integer.parseInt(s) - 1).click(); // Clicking the nth search product
        }
        else{
            Assert.fail("No matching product found!!!");
        }
    }

    /**
     * Validates key Lot details by ensuring required fields are non-empty
     * and numeric values are non-negative, then logs the Page Title, Favorite Counter and Current Bid Amount.
     */
    @Then("Validate Lot details with spcified attributes")
    public void Validate_lot_details_with_spcified_attributes() throws InterruptedException{
        waitUtils.waitForElementToBeClickable(driver, searchResultsPage.favoriteCounter);
        log.info("Lot's Page Title: " +verificationUtils.verificationForNonEmptyStrings(searchResultsPage.lotTitle));
        log.info("Favorite Counter: " +verificationUtils.verificationForNonNegativeValues(searchResultsPage.favoriteCounter));
        log.info("Current Bid Amount: " +verificationUtils.verificationForNonNegativeValues(
            verificationUtils.filterDigitsFromMixedString(searchResultsPage.currentBidAmount)));

        // Pause for observation
        Thread.sleep(Duration.ofSeconds(sleepTimer));
    }

    /**
     * Handles the age-restricted content warning based on user permission
     * and allows time for observation.
     */
    @Then("Check for Age restricted content warning with {string}")
    public void Check_for_Age_restricted_content_warning(String permission) throws InterruptedException {
        // Pause for observation
        Thread.sleep(Duration.ofSeconds(sleepTimer));
        lotProductPage.checkForAgeRestrictedContentWarning(permission);
        
        // Pause for observation
        Thread.sleep(Duration.ofSeconds(sleepTimer));
    }

    @When("User change language to {string} and validate the language change")
    public void User_change_language_to_and_validate_the_language_change(String language) {
        homePage.changeUserLanguageAndValidateChange(language);
    }

    @Then("Validate expected outcome for Invalid_Product_Term and verify {string} message and screenshot")
    public void Validate_expected_outcome_for_Invalid_Product_Term_and_verify_message_and_screenshot(String warningMsg) throws IOException, InterruptedException {
        verificationUtils.verifyMessageAndScreenshot(driver, "InvalidProductTerm", searchResultsPage.warningMsgBannerInvalidProduct, warningMsg);
    }

    @Then("Validate expected outcome for Blank_Term and verify {string} message and screenshot")
    public void Validate_expected_outcome_for_Blank_Term_and_verify_message_and_screenshot(String warnMsg) throws IOException, InterruptedException {
        verificationUtils.verifyMessageAndScreenshot(driver, "BlankTerm", searchResultsPage.warningMsgBannerBlankTerm, warnMsg);
    }

    @Then("User navigates back to {string} search results page")
    public void User_navigates_back_to_search_results_page(String searchTerm) {
        lotProductPage.navigateToSearchResultsPage();
        Assert.assertTrue(driver.getCurrentUrl().contains(searchTerm));
    }

}

    
