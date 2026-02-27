package PageUtilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class takesScreenshotUtils {

    WebDriver driver;
    private static final Logger log = LogManager.getLogger(takesScreenshotUtils.class);

    public takesScreenshotUtils(WebDriver driver){
        this.driver = driver;
    }
    
    public static void takeScreenshot(WebDriver driver, String fileName) throws IOException {
    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

    // Target Path - target/screenshots/<fileName>.png
    String targetDir = "target/screenshots/";
    String destinationPath = targetDir + fileName + ".png";

    // Create directory if it doesn't exist
    File directory = new File(targetDir);
    if (!directory.exists()) {
        directory.mkdirs();
    }

    // Copy the screenshot file in the destination folder
    try {
        Files.copy(screenshot.toPath(), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
        log.info("Screenshot saved at: " + destinationPath);
    } catch (IOException e) {
        log.error(e.getMessage());
    }
}
}
