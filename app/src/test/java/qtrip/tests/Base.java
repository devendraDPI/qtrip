package qtrip.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import qtrip.browser.BrowserHandler;
import qtrip.utilities.Log;

public class Base {
    static WebDriver driver;
    static String lastGeneratedUsername;

    @BeforeSuite(alwaysRun = true)
    public static void createDriver() {
        Log.log("createDriver", "Initializing driver");
        driver = BrowserHandler.getDriver("chrome");
        Log.log("createDriver", "Initialized driver");
    }

    @AfterSuite(alwaysRun = true)
    public static void quitDriver() {
        Log.log("quitDriver", "Quitting driver");
        BrowserHandler.quitDriver();
        Log.log("quitDriver", "Quitted driver");
    }
}
