package qtrip.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class BrowserHandler {
    private static WebDriver driver = null;
    private static final String CHROME = "chrome";
    private static final String EDGE = "edge";

    private BrowserHandler() {}

    public static WebDriver getDriver(String browserName) {
        if (driver == null) {
            switch (browserName) {
                case CHROME -> driver = getChrome();
                case EDGE -> driver = getEdge();
                default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
            }
        }
        return driver;
    }

    private static WebDriver getChrome() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
        return new ChromeDriver(chromeOptions);
    }

    private static WebDriver getEdge() {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("start-maximized");
        edgeOptions.addArguments("--disable-blink-features=AutomationControlled");
        return new EdgeDriver(edgeOptions);
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
