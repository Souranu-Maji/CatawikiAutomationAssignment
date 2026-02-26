package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import PageUtilities.waitUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import StepDefinitions.SearchSteps;

public class lotProductPage {

    WebDriver driver;
    private static final Logger log = LogManager.getLogger(SearchSteps.class);

    public lotProductPage(WebDriver driver){
        this.driver = driver;
    }
    
    By warningFlag = By.cssSelector("h1[class*='heading']");
    By proceedToAuctionBtn = By.cssSelector("div[class*='LotExplicitContentModal']>button");
    By backToHomePageLnk = By.xpath("//div[contains(@class,'LotExplicitContentModal')]//child::span[contains(text(),'Back to Homepage')]//ancestor::a");


    public void checkForAgeRestrictedContentWarning(String permission){
        try {
            /*
             * if permission granted by user, it will proceed to Explicit Content Page
             * otherwise it will back to the homepage
             */
            if(driver.findElement(warningFlag) != null){
                if("Yes".equals(permission))
                {
                    log.warn(driver.findElement(warningFlag).getText() + " Found !!! Proceeding with Caution...");
                    waitUtils.waitForElementToBePresent(driver, proceedToAuctionBtn).click();
                }
                else{
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

}
