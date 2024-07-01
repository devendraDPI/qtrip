package qtrip.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import qtrip.pages.Login;
import qtrip.pages.Register;
import qtrip.utilities.ExternalDataProvider;

public class Testcase01 extends Base {
    @Test(
        description = "Verify if new user can be created and logged in",
        enabled = true,
        dataProvider = "qtripTestDataSet",
        dataProviderClass = ExternalDataProvider.class
    )
    public void TC01(String username, String password) {
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        Assert.assertTrue(registration.registerUser(username, password, password, true), "Unable to register");
        lastGeneratedUsername = registration.lastGeneratedUsername;

        Login login = new Login(driver);
        login.navigateToLoginPage();
        Assert.assertTrue(login.loginUser(lastGeneratedUsername, password), "Unable to login");
    }
}
