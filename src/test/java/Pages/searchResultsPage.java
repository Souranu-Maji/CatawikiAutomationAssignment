package Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class searchResultsPage {
    
    public searchResultsPage( WebDriver driver) {
        PageFactory.initElements(driver, this);
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
