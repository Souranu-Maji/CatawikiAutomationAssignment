package Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import StepDefinitions.SearchSteps;

public class searchResultsPage {
    
    WebDriver driver;
    private static final Logger log = LogManager.getLogger(SearchSteps.class);
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



    public int getProductsCountPerPage() {
        return searchResultsItems.size();
    }

}
