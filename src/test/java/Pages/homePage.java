package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class homePage {

    WebDriver driver;

   public homePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
   }
    
   @FindBy(css = "button.js-register-button")
   public WebElement registerButton;

   @FindBy(xpath = "//div[contains(@class,'part-of-categories ')]//child::input[@type='search']")
   public WebElement searchBar;

   @FindBy(xpath = "//div[contains(@class,'part-of-categories ')]//button[@aria-label='Search']")
    public WebElement searchButton;

}
