package PageUtilities;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class verificationUtils {

    WebDriver driver;
    public verificationUtils(WebDriver driver){
        this.driver = driver;
    }
    
    public static String verificationForNonEmptyStrings(WebElement element){
        String elementText = element.getText();
        Assert.assertFalse("Element Text should not be empty", elementText.trim().isEmpty());
        return elementText;
    }

    public static int verificationForNonNegativeValues(WebElement element){
        int value = Integer.parseInt(element.getText());
        Assert.assertTrue("Value should be greate than 0", value >= 0);
        return value;
    } 

    public static int verificationForNonNegativeValues(int value){
        Assert.assertTrue("Value should be greate than 0", value >= 0);
        return value;
    } 

    public static int filterDigitsFromMixedString(WebElement element){
        int value = Integer.parseInt(element.getText().replaceAll("[^0-9]", ""));
        return value;
    } 

    public static void verifyElementPresenceInDOM(WebDriver driver, String Message, By locator){
        Assert.assertTrue(Message, driver.findElements(locator).size() > 0);
    }
}
