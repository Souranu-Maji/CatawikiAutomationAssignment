package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import PageUtilities.waitUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;


public class lotProductPage {

    WebDriver driver;
    private static final Logger log = LogManager.getLogger(lotProductPage.class);

    public lotProductPage(WebDriver driver){
        this.driver = driver;
    }
    
    By warningFlag = By.cssSelector("h1[class*='heading']");
    By proceedToAuctionBtn = By.cssSelector("div[class*='LotExplicitContentModal']>button");
    By backToHomePageLnk = By.xpath("//div[contains(@class,'LotExplicitContentModal')]//child::span[contains(text(),'Back to Homepage')]//ancestor::a");
    By backToSearchLnk = By.xpath("//div[contains(@data-sentry-component,'DesktopNavigation')]//child::span[contains(text(),'Back to search')]//ancestor::a");


    /**
     * Handles the age-restricted content warning if it appears.
     * Proceeds to the content when permission is "Yes",
     * otherwise navigates back to the homepage.
     *
     * @param permission user's choice ("Yes" to proceed, anything else to go back)
     */
    public void checkForAgeRestrictedContentWarning(String permission){
        try {
            // Check if the warning banner is present on the page
            if(driver.findElement(warningFlag) != null){
                if("Yes".equals(permission))
                {
                    // Permission granted proceed to restricted content
                    log.warn(driver.findElement(warningFlag).getText() + " Found !!! Proceeding with Caution...");
                    waitUtils.waitForElementToBePresent(driver, proceedToAuctionBtn).click();
                }
                else{
                    // Permission denied return to homepage
                    log.warn(driver.findElement(warningFlag).getText() + " Found !!! Going Back to Homepage...");
                    waitUtils.waitForElementToBePresent(driver, backToHomePageLnk).click();
                    Assert.assertTrue(waitUtils.waitForTitleText(driver, homePage.getHomePageTitle()));
                }
                
            }
        } catch (NoSuchElementException error) {
            log.info("Explicit Content Warning Not Present...");
        } finally{
            System.out.println("Proceeding");
        }

    }

    public void navigateToSearchResultsPage(){
        waitUtils.waitForElementToBePresent(driver, backToSearchLnk).click();;
    }
}
