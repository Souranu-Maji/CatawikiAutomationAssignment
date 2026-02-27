package PageUtilities;

import java.io.IOException;
import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class verificationUtils {

    WebDriver driver;
    private static final Logger log = LogManager.getLogger(verificationUtils.class);
    public verificationUtils(WebDriver driver){
        this.driver = driver;
    }
    
    // Verifies element text is not empty and returns the text
    public static String verificationForNonEmptyStrings(WebElement element){
        String elementText = element.getText();
        Assert.assertFalse("Element Text should not be empty", elementText.trim().isEmpty());
        return elementText;
    }

    // Verifies numeric text from element is non-negative and returns the value
    public static int verificationForNonNegativeValues(WebElement element){
        int value = Integer.parseInt(element.getText());
        Assert.assertTrue("Value should be greate than 0", value >= 0);
        return value;
    } 

    // Verifies the provided integer value is non-negative and returns it
    public static int verificationForNonNegativeValues(int value){
        Assert.assertTrue("Value should be greate than 0", value >= 0);
        return value;
    } 

    // Extracts digits from mixed text in element and returns the numeric value
    public static int filterDigitsFromMixedString(WebElement element){
        int value = Integer.parseInt(element.getText().replaceAll("[^0-9]", ""));
        return value;
    } 

    // Verifies that at least one element matching the locator exists in the DOM
    public static void verifyElementPresenceInDOM(WebDriver driver, String assertMessage, By locator){
        Assert.assertTrue(assertMessage, driver.findElements(locator).size() > 0);
    }

    /**
     * Checks whether the text of the specified element contains
     * the expected message (case-insensitive).
     *
     * @param driver WebDriver instance
     * @param locator locator of the element
     * @param expectedMsg expected text to verify
     * @return true if element text contains the expected message, false otherwise
     */
    public static boolean verifyElementTxtContainsExpectedMsg( WebDriver driver, By locator, String expectedMsg){
        boolean flag = false;

        // Compare element text with expected message (ignoring case)
        if(driver.findElement(locator).getText().toLowerCase().contains(expectedMsg.toLowerCase())){
            flag = true;
        }
        return flag;
    }

    /**
     * Captures a screenshot, verifies a warning element is present,
     * and checks that it contains the expected message.
     *
     * @param driver WebDriver instance
     * @param screenshotFileName name for the saved screenshot
     * @param locator locator of the warning element
     * @param warningMsg expected warning text
     */
    public static void verifyMessageAndScreenshot(WebDriver driver, String screenshotFileName,By locator, String warningMsg) throws IOException, InterruptedException{
        takesScreenshotUtils.takeScreenshot(driver, screenshotFileName); // Capture current screen
        verifyElementPresenceInDOM(driver, "Warning Message should be Visible",locator); // Ensure banner element exists in the DOM
        
        // Validate element text contains expected warning message
        if(verifyElementTxtContainsExpectedMsg(driver, locator, warningMsg)){
            log.info("Appropriate Banner Msg Present:"+warningMsg);
        }
        Thread.sleep(Duration.ofSeconds(2));
    }
}
