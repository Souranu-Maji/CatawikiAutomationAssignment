package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Enums.language;
import PageUtilities.waitUtils;

public class homePage {

    WebDriver driver;
    private static final Logger log = LogManager.getLogger(homePage.class);
    private final static String homePageTitle = "The Online Marketplace with Weekly Auctions";
    private language lang = null;

   public homePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
   }
    
    @FindBy(css = "button.js-register-button")
    public WebElement registerButton;

    @FindBy(xpath = "//div[contains(@class,'part-of-categories ')]//child::input[@type='search']")
    public WebElement searchBar;

    @FindBy(xpath = "//div[contains(@class,'part-of-categories ')]//button[@aria-label='Search']")
    public WebElement searchButton;

    @FindBy(xpath = "//div[contains(@class,'header__links')]//child::button[@aria-haspopup='menu']")
    public WebElement languageBtn;

    @FindBy(css = "div[role='menu']")
    public WebElement languageDropdownMenu;

    @FindBy(xpath = "//div[contains(@class,'header__links')]//child::button[@aria-haspopup='menu']//child::span[contains(@class,'text')]")
    public WebElement languageBtnText;

    public static String getHomePageTitle(){
        return homePageTitle;   
    }


    /**
     * Searches for a product using the given search term.
     *
     * @param searchTerm product name or keyword to search
     */
    public void searchForProductName(String searchTerm){
        log.info("User searches for: " + searchTerm);
        // Waiting for searchBar to be visible
        waitUtils.waitForElementToBeVisible(driver, searchBar).sendKeys(searchTerm); 
        searchButton.click();
    }

    /**
     * Changes the user's language from the website's language menu
     * and validates that the change is applied.
     *
     * @param nativelang language to switch to (must match enum value)
     */
    public void changeUserLanguageAndValidateChange(String nativelang){
        languageBtn.click(); // Open language selection dropdown
        WebDriverWait waitForLanguageDropdown = new WebDriverWait(driver, Duration.ofSeconds(5));
        waitForLanguageDropdown.until(ExpectedConditions.visibilityOf(languageDropdownMenu));
        if(verifyNativeLangSupportedByWebsite(nativelang)){
            lang = language.valueOf(nativelang);
            // Select the language option using its display name in the DOM
            driver.findElement(By.xpath("//div[@role='menu']//button//p[contains(text(),'"+lang.getDisplayLanguage()+"')]")).click(); 
        }
        validateChangedLanguageOnWebpage(nativelang);
    }

    /**
     * Validates that the webpage language has been updated correctly
     * by checking the URL and the language button text.
     *
     * @param nativelang language that should be applied (enum value)
     */
    public void validateChangedLanguageOnWebpage(String nativelang){
        language lang = language.valueOf(nativelang); // Convert input to language enum
        log.info("WebURL changed to: "+driver.getCurrentUrl());
        // Verify URL contains the expected language code
        Assert.assertTrue(driver.getCurrentUrl().contains(lang.getDisplayCode().toLowerCase()));

        // Verify language button shows the correct language code
        Assert.assertTrue(languageBtnText.getText().equals(lang.getDisplayCode()));

    }

    /**
     * Checks if the given native language is supported by validating
     * it against the `language` enum.
     *
     * @param nativeLanguage user-provided language string
     * @return true if supported, false otherwise
     */
    public boolean verifyNativeLangSupportedByWebsite(String nativeLanguage){
        boolean islanguageSupported = false;
        // Attempt to convert the input string to a corresponding enum constant.
        try{
            if(language.valueOf(nativeLanguage) != null){
                log.info("User has Selected "+language.valueOf(nativeLanguage));
                islanguageSupported = true;
            }
        }
        catch(IllegalArgumentException error)
        {
        // Thrown when the input does not match any enum constant (unsupported language)
            log.warn("Native Language NOT supported by website");
        }
        catch(NullPointerException error2)
        {
            error2.printStackTrace();
        }
        return islanguageSupported;
    }

    public boolean verifyHomepageTitle(){
        return waitUtils.waitForTitleText(driver, homePageTitle);
    }

}
