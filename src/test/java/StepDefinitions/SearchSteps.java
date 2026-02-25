package StepDefinitions;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Pages.cookiesBar;
import Pages.homePage;
import Pages.searchResultsPage;

import java.time.Duration;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SearchSteps {
    
    private String driverPath = System.getProperty("user.dir") + "/src/test/resources/Drivers/chromedriver.exe";
    private static final Logger log = LogManager.getLogger(SearchSteps.class);

    WebDriver driver = new ChromeDriver();
    homePage homePage = new homePage(driver);
    cookiesBar cookiesBar = new cookiesBar(driver);
    searchResultsPage searchResultsPage = new searchResultsPage(driver);

    @Given("User is on the Catawiki homepage")
    public void User_is_on_the_Catawiki_homepage() throws InterruptedException {
        System.out.println("Launching the browser and navigating to the Catawiki homepage");
        System.setProperty("webdriver.chrome.driver", driverPath);
        
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.get("https://www.catawiki.com/");
        Thread.sleep(2000); // Wait for the page to load completely
        driver.manage().window().maximize();

        // Handle the cookie consent bar
        WebDriverWait waitForCookieBar = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForCookieBar.until(ExpectedConditions.elementToBeClickable(cookiesBar.declineCookiesButton));
        cookiesBar.declineCookies();

        System.out.println("Home Page Title: " + driver.getCurrentUrl());

    }

    @When("User searches for {string}")
    public void User_searches_for(String searchTerm) {
        System.out.println("User searches for: " + searchTerm);
        homePage.searchBar.sendKeys(searchTerm);
        homePage.searchButton.click();
    }

    @When("Verify the designated {string} page details")
    public void Verify_the_designated_page_details(String s) {
        WebDriverWait waitForSearchResultsPage = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForSearchResultsPage.until(ExpectedConditions.visibilityOf(searchResultsPage.searchResultsTitle));
        Assert.assertTrue("Page title does not contain the search term", searchResultsPage.searchResultsTitle.getText().toLowerCase()
        .contains(s));
    }


    @Then("Click on {string} lot in search results page and verify")
    public void Click_on_lot_in_search_results_page_and_verify(String s) {
        System.out.println("Total lots found on first Seearch Results Page:" +searchResultsPage.getProductsCountPerPage());
        if(searchResultsPage.getProductsCountPerPage() > 0) {
            searchResultsPage.searchResultsItems.get(Integer.parseInt(s) - 1).click();
        }
        else{
            Assert.fail("No matching product found!!!");
        }
    }

    @Then("Validate lot details with spcified attributes")
    public void Validate_lot_details_with_spcified_attributes() throws InterruptedException{
        // Write code here that turns the phrase above into concrete actions
        WebDriverWait waitForLotDetailsPage = new WebDriverWait(driver, Duration.ofSeconds(5));
        waitForLotDetailsPage.until(ExpectedConditions.elementToBeClickable(searchResultsPage.favoriteCounter));

        log.info("Lot's Page Title: " +searchResultsPage.lotTitle.getText());
        log.info("Favorite Counter: " +searchResultsPage.favoriteCounter.getText());
        log.info("Current Bid Amount: " +searchResultsPage.currentBidAmount.getText());

        Thread.sleep(Duration.ofSeconds(3));
        driver.close();
        driver.quit();
    }




}
