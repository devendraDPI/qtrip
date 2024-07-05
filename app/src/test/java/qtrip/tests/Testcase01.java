package qtrip.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import qtrip.pages.Home;
import qtrip.pages.Login;
import qtrip.pages.Register;
import qtrip.utilities.ExternalDataProvider;
import qtrip.utilities.reports.ExtentTestManager;

public class Testcase01 extends Base {
    @Test(
        description = "Verify if new user can be created and logged in",
        enabled = true,
        priority = 1,
        groups = {"Login flow"},
        dataProvider = "qtripTestDataSet",
        dataProviderClass = ExternalDataProvider.class
    )
    public void TC01(String username, String password) {
        Register registration = new Register(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, "Navigating to register page");
        registration.navigateToRegisterPage();
        Assert.assertTrue(registration.registerUser(username, password, password, true), "Unable to register");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("User registered successfully (%s, %s)", username, password));
        lastGeneratedUsername = registration.lastGeneratedUsername;

        Login login = new Login(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, "Navigating to login page");
        login.navigateToLoginPage();
        Assert.assertTrue(login.loginUser(lastGeneratedUsername, password), "Unable to login");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("User logged in successfully (%s, %s)", username, password));

        Home home = new Home(driver);
        Assert.assertTrue(home.logoutUser(), "Unable to log out");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("User logged out successfully (%s)", username));
    }
}
