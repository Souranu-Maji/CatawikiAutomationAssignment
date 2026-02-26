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
import StepDefinitions.SearchSteps;

public class homePage {

    WebDriver driver;
    private static final Logger log = LogManager.getLogger(SearchSteps.class);
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


    public void searchForProductName(String searchTerm){
        log.info("User searches for: " + searchTerm);
        // Waiting for searchBar to be visible
        waitUtils.waitForElementToBeVisible(driver, searchBar).sendKeys(searchTerm); 
        searchButton.click();
    }

    public void changeUserLanguageAndValidateChange(String nativelang){
        languageBtn.click();
        WebDriverWait waitForLanguageDropdown = new WebDriverWait(driver, Duration.ofSeconds(5));
        waitForLanguageDropdown.until(ExpectedConditions.visibilityOf(languageDropdownMenu));
        if(verifyNativeLangSupportedByWebsite(nativelang)){
            lang = language.valueOf(nativelang);
            driver.findElement(By.xpath("//div[@role='menu']//button//p[contains(text(),'"+lang.getDisplayLanguage()+"')]")).click(); 
        }
        validateChangedLanguageOnWebpage(nativelang);
    }

    public void validateChangedLanguageOnWebpage(String nativelang){
        language lang = language.valueOf(nativelang);
        log.info("WebURL changed to: "+driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains(lang.getDisplayCode().toLowerCase()));
        Assert.assertTrue(languageBtnText.getText().equals(lang.getDisplayCode()));

    }

    public boolean verifyNativeLangSupportedByWebsite(String nativeLanguage){
        boolean islanguageSupported = false;
        try{
            if(language.valueOf(nativeLanguage) != null){
                log.info("User has Selected "+language.valueOf(nativeLanguage));
                islanguageSupported = true;
            }
        }
        catch(IllegalArgumentException error)
        {
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
