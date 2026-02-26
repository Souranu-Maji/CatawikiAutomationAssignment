package StepDefinitions;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import PageUtilities.takesScreenshotUtils;
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
    private int sleepTimer = 2;
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
        Assert.assertTrue("Page title does not contain the search term", searchResultsPage.searchResultsTitle.getText().toLowerCase().contains(searchString.toLowerCase()));
    }


    @Then("Click on Lot {string} in search results page and verify")
    public void Click_on_lot_in_search_results_page_and_verify(String s) {
        log.info("Total lots found on first Seearch Results Page:" +searchResultsPage.getProductsCountPerPage());
        /* 
         * Validating more than one search result present in the search page
         * If No matching product is found, then it should throw assertion error
         */
        if(searchResultsPage.getProductsCountPerPage() > 0) {
            searchResultsPage.searchResultsItems.get(Integer.parseInt(s) - 1).click(); // Clicking the nth search product
        }
        else{
            Assert.fail("No matching product found!!!");
        }
    }

    @Then("Validate Lot details with spcified attributes")
    public void Validate_lot_details_with_spcified_attributes() throws InterruptedException{
        waitUtils.waitForElementToBeClickable(driver, searchResultsPage.favoriteCounter);
        /*
         * implemented validation for Non-empty strings
         * and validations for non-negative integer values
         * and logging LoT Page Title, Favorite Counter, Current Bid Amount
         */
        log.info("Lot's Page Title: " +verificationUtils.verificationForNonEmptyStrings(searchResultsPage.lotTitle));
        Thread.sleep(3000);
        log.info("Favorite Counter: " +verificationUtils.verificationForNonNegativeValues(searchResultsPage.favoriteCounter));
        log.info("Current Bid Amount: " +verificationUtils.verificationForNonNegativeValues(
            verificationUtils.filterDigitsFromMixedString(searchResultsPage.currentBidAmount)));

        Thread.sleep(Duration.ofSeconds(sleepTimer));
    }

    @Then("Check for Age restricted content warning with {string}")
    public void Check_for_Age_restricted_content_warning(String permission) throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(sleepTimer));
        lotProductPage.checkForAgeRestrictedContentWarning(permission);
        Thread.sleep(Duration.ofSeconds(sleepTimer));
    }

    @When("User change language to {string} and validate the language change")
    public void User_change_language_to_and_validate_the_language_change(String language) {
        homePage.changeUserLanguageAndValidateChange(language);
    }

    @Then("Validate expected outcome and verify error message")
    public void Validate_expected_outcome_and_verify_error_message() throws InterruptedException, IOException {
        takesScreenshotUtils.takeScreenshot(driver, "InvalidProductTerm");
        verificationUtils.verifyElementPresenceInDOM(driver, "Warning Message should be Visible for Invalid Search term",By.xpath("//h1[contains(@class,'typography')]//following-sibling::p"));
        Thread.sleep(Duration.ofSeconds(2));
    }


}
