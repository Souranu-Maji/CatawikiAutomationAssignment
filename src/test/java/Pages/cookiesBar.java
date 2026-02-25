package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class cookiesBar {

    WebDriver driver;

    public cookiesBar(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "button.gtm-cookie-bar-decline")
    public WebElement declineCookiesButton;

    @FindBy(css = "button#cookie_bar_agree_button")
    public WebElement acceptCookiesButton;

    @FindBy(css = "button.gtm-cookie-bar-manage")
    public WebElement manageCookiesButton;
    
    public void declineCookies() {
        declineCookiesButton.click();
    }

    public void acceptCookies() {
        acceptCookiesButton.click();
    }
}


