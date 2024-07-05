package qtrip.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import qtrip.pages.Adventure;
import qtrip.pages.AdventureDetails;
import qtrip.pages.History;
import qtrip.pages.Home;
import qtrip.pages.Login;
import qtrip.pages.Register;
import qtrip.utilities.ExternalDataProvider;
import qtrip.utilities.reports.ExtentTestManager;

public class Testcase03 extends Base {
    @Test(
        description = "Verify booking and cancellation flow",
        enabled = true,
        priority = 3,
        groups = {"Booking and cancellation flow"},
        dataProvider = "qtripTestDataSet",
        dataProviderClass = ExternalDataProvider.class
    )
    public void TC03(String username, String password, String city, String adventureName, String personName, String date, String persons) throws InterruptedException {
        Home home = new Home(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, "Navigating to home page");
        home.navigateToHomePage();

        Register register = new Register(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, "Navigating to register page");
        register.navigateToRegisterPage();
        Assert.assertTrue(register.registerUser(username, password, password, true), "Unable to register");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("User registered successfully (%s, %s)", username, password));
		lastGeneratedUsername = register.lastGeneratedUsername;

		Login login = new Login(driver);
        Assert.assertTrue(login.loginUser(lastGeneratedUsername, password), "Unable to login");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("User logged in successfully (%s, %s)", username, password));
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Searching city (%s)", city));
        home.searchCity(city);
        Assert.assertTrue(home.isNoCityFound() || home.isCityFound(), "No city found");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("City found (%s)", city));
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Selecting city (%s)", city));
        home.selectCity();

        Adventure adventure = new Adventure(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Searching adventure (%s)", adventureName));
        adventure.searchAdventure(adventureName);
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Clicking on adventure (%s)", adventureName));
        adventure.clickAdventure();

        AdventureDetails adventureDetails = new AdventureDetails(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Booking adventure (%s, %s, %s)", personName, date, persons));
        adventureDetails.bookAdventure(personName, date, persons);
        Assert.assertTrue(adventureDetails.verifyBookingSuccessful(), "Unable to book adventure");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("Booked adventure (%s, %s, %s)", personName, date, persons));
        ExtentTestManager.testLogger(LogStatus.INFO, "Clicking on reservation link");
        adventureDetails.reservationLinkClick();

        History history = new History(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, history.getReservation());
        Assert.assertTrue(home.logoutUser(), "Unable to log out");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("User logged out successfully (%s)", username));
    }
}
