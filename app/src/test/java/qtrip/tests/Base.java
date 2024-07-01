package qtrip.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import qtrip.utilities.Log;

public class Base {
    static WebDriver driver;
    static String lastGeneratedUsername;

    @BeforeSuite(alwaysRun = true)
    public static void createDriver() {
        Log.log("createDriver", "Initializing driver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");

        driver = new ChromeDriver(options);

        Log.log("createDriver", "Initialized driver");
    }

    @AfterSuite(alwaysRun = true)
    public static void quitDriver() {
        Log.log("quitDriver", "Quitting driver");

        if (driver != null) {
            driver.quit();
        }

        Log.log("quitDriver", "Quitted driver");
    }
}
