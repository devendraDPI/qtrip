package qtrip.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SeleniumWrapper {
    public static Boolean clickAW(WebDriver driver, WebElement element) {
        try {
            Thread.sleep(1000);
            if (element.isDisplayed() && element.isEnabled()) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView();", element);
                Thread.sleep(1000);
                element.click();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean sendKeysAW(WebElement element, String text) {
        try {
            Thread.sleep(500);
            element.clear();
            Thread.sleep(500);
            element.sendKeys(text);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean navigateAW(WebDriver driver, String url) {
        try {
            if (!driver.getCurrentUrl().equals(url)) {
                driver.get(url);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static WebElement findElementWithRetryAW(WebDriver driver, By locator, Integer retries) {
        WebElement element = null;
        try {
            for (int attempt=1; attempt<retries; attempt++) {
                try {
                    element = driver.findElement(locator);
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    wait.until(ExpectedConditions.visibilityOf(element));
                    System.out.println(String.format("Element displayed after %d attempts", attempt));
                    break;
                } catch (NoSuchElementException | StaleElementReferenceException e) {
                    System.out.println("Element not found or stale, retrying... (Attempt " + attempt + ")");
                    if (attempt == retries) {
                        throw e;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return element;
    }
}
