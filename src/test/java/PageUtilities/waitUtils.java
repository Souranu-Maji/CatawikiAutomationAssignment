package PageUtilities;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class waitUtils {

    WebDriver driver;
    private static final int defaultExplicitWaitTime = 10;

    public waitUtils(WebDriver driver){
        this.driver = driver;
    }

    public static WebDriverWait wait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(defaultExplicitWaitTime));
    }

    public static WebElement waitForElementToBeVisible(WebDriver driver, WebElement element){
        return wait(driver).until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForElementToBeClickable(WebDriver driver, WebElement element){
        return wait(driver).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForElementToBePresent(WebDriver driver, By locator){
        return wait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));

    }

    public static Boolean waitForTitleText(WebDriver driver, String title){
        return wait(driver).until(ExpectedConditions.titleContains(title));
    }
    
}
