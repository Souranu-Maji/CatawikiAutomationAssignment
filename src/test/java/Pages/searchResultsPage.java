package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class searchResultsPage {
    
    WebDriver driver;
    private static final Logger log = LogManager.getLogger(searchResultsPage.class);
    public searchResultsPage( WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css = "h1[class*='typography']")
    public WebElement searchResultsTitle;

    @FindBy(css = "div[data-sentry-component='ListingLotsWrapper']")
    public List<WebElement> searchResultsItems;

    @FindBy(css = "h1[class*='LotTitle']")
    public WebElement lotTitle;

    @FindBy(css = "div[data-sentry-component='LotDetailsFavoriteButton']>button>span")
    public WebElement favoriteCounter;

    @FindBy(xpath = "//*[contains(@class,'bid-amount')]")
    public WebElement currentBidAmount;

    public By warningMsgBannerInvalidProduct = By.xpath("//h1[contains(@class,'typography')]//following-sibling::p");

    public By warningMsgBannerBlankTerm = By.xpath("//*[@data-sentry-component='NoSearchResults']//child::*[contains(text(),'No')]");

    // Returns the number of product items displayed on the current page.
    public int getProductsCountPerPage() {
        return searchResultsItems.size();
    }

}
