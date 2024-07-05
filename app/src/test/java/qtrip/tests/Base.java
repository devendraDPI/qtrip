package qtrip.tests;

import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.LogStatus;

import qtrip.browser.BrowserHandler;
import qtrip.utilities.Log;
import qtrip.utilities.SeleniumUtils;
import qtrip.utilities.reports.ExtentReportManager;
import qtrip.utilities.reports.ExtentTestManager;

public class Base {
    static WebDriver driver;
    static String lastGeneratedUsername;

    @BeforeSuite(alwaysRun = true)
    public static void createDriver() {
        Log.log("createDriver", "Initializing driver");
        driver = BrowserHandler.getDriver("chrome");
        Log.log("createDriver", "Initialized driver");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTest(Method method) {
        ExtentTestManager.startTest(method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult result) throws IOException {
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                ExtentTestManager.testLogger(LogStatus.PASS, "Test step PASS");
                break;
            case ITestResult.FAILURE:
                ExtentTestManager.testLogger(LogStatus.FAIL, ExtentTestManager.getTest().addScreenCapture(SeleniumUtils.capture(driver)) + "Test fail");
                break;
            default:
                ExtentTestManager.testLogger(LogStatus.SKIP, "Test step SKIP");
        }
        ExtentTestManager.endTest();
    }

    @AfterSuite(alwaysRun = true)
    public static void quitDriver() {
        Log.log("quitDriver", "Quitting driver");
        BrowserHandler.quitDriver();
        Log.log("quitDriver", "Quitted driver");
        Log.log("quitDriver", "Quitting reports");
        ExtentReportManager.getReports().flush();
        Log.log("quitDriver", "Quitted reports");
    }
}
