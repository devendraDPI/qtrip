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

public class Testcase04 extends Base {
    @Test(
        description = "Verify reliability flow",
        enabled = true,
        priority = 4,
        groups = {"Reliability flow"},
        dataProvider = "qtripTestDataSet",
        dataProviderClass = ExternalDataProvider.class
    )
    public void TC04(String username, String password, String dataSet1, String dataSet2, String dataSet3) throws InterruptedException {
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
        String[] dataSets = new String[]{dataSet1, dataSet2, dataSet3};

        for (int i=0; i<dataSets.length; i++) {
            String[] dataset = dataSets[i].split(";");

            ExtentTestManager.testLogger(LogStatus.INFO, "Navigating to home page");
            home.navigateToHomePage();
            ExtentTestManager.testLogger(LogStatus.PASS, String.format("User logged in successfully (%s, %s)", username, password));
            ExtentTestManager.testLogger(LogStatus.INFO, String.format("Searching city (%s)", dataset[0]));
            home.searchCity(dataset[0]);
            Assert.assertTrue(home.isNoCityFound() || home.isCityFound(), "No city found");
            ExtentTestManager.testLogger(LogStatus.PASS, String.format("City found (%s)", dataset[0]));
            ExtentTestManager.testLogger(LogStatus.INFO, String.format("Selecting city (%s)", dataset[0]));
            home.selectCity();

            Adventure adventure = new Adventure(driver);
            ExtentTestManager.testLogger(LogStatus.INFO, String.format("Searching adventure (%s)", dataset[1]));
            adventure.searchAdventure(dataset[1]);
            ExtentTestManager.testLogger(LogStatus.INFO, String.format("Clicking on adventure (%s)", dataset[1]));
            adventure.clickAdventure();

            AdventureDetails adventureDetails = new AdventureDetails(driver);
            ExtentTestManager.testLogger(LogStatus.INFO, String.format("Booking adventure (%s, %s, %s)", dataset[2], dataset[3], dataset[4]));
            adventureDetails.bookAdventure(dataset[2], dataset[3], dataset[4]);
            Assert.assertTrue(adventureDetails.verifyBookingSuccessful(), "Unable to book adventure");
            ExtentTestManager.testLogger(LogStatus.PASS, String.format("Booked adventure (%s, %s, %s)", dataset[2], dataset[3], dataset[4]));
            ExtentTestManager.testLogger(LogStatus.INFO, "Clicking on reservation link");
            adventureDetails.reservationLinkClick();
        }

        History history = new History(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, history.getReservation());
        Assert.assertTrue(home.logoutUser(), "Unable to log out");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("User logged out successfully (%s)", username));
    }
}
