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
        String[] dataset1 = dataSet1.split(";");
        String[] dataset2 = dataSet2.split(";");
        String[] dataset3 = dataSet3.split(";");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("User logged in successfully (%s, %s)", username, password));
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Searching city (%s)", dataset1[0]));
        home.searchCity(dataset1[0]);
        Assert.assertTrue(home.isNoCityFound() || home.isCityFound(), "No city found");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("City found (%s)", dataset1[0]));
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Selecting city (%s)", dataset1[0]));
        home.selectCity();

        Adventure adventure1 = new Adventure(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Searching adventure (%s)", dataset1[1]));
        adventure1.searchAdventure(dataset1[1]);
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Clicking on adventure (%s)", dataset1[1]));
        adventure1.clickAdventure();

        AdventureDetails adventureDetails1 = new AdventureDetails(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Booking adventure (%s, %s, %s)", dataset1[2], dataset1[3], dataset1[4]));
        adventureDetails1.bookAdventure(dataset1[2], dataset1[3], dataset1[4]);
        Assert.assertTrue(adventureDetails1.verifyBookingSuccessful(), "Unable to book adventure");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("Booked adventure (%s, %s, %s)", dataset1[2], dataset1[3], dataset1[4]));
        ExtentTestManager.testLogger(LogStatus.INFO, "Clicking on reservation link");
        adventureDetails1.reservationLinkClick();

        ExtentTestManager.testLogger(LogStatus.INFO, "Navigating to home page");
        home.navigateToHomePage();
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Searching city (%s)", dataset2[0]));
        home.searchCity(dataset2[0]);
        Assert.assertTrue(home.isNoCityFound() || home.isCityFound(), "No city found");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("City found (%s)", dataset2[0]));
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Selecting city (%s)", dataset2[0]));
        home.selectCity();

        Adventure adventure2 = new Adventure(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Searching adventure (%s)", dataset2[1]));
        adventure2.searchAdventure(dataset2[1]);
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Clicking on adventure (%s)", dataset2[1]));
        adventure2.clickAdventure();

        AdventureDetails adventureDetails2 = new AdventureDetails(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Booking adventure (%s, %s, %s)", dataset2[2], dataset2[3], dataset2[4]));
        adventureDetails2.bookAdventure(dataset2[2], dataset2[3], dataset2[4]);
        Assert.assertTrue(adventureDetails2.verifyBookingSuccessful(), "Unable to book adventure");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("Booked adventure (%s, %s, %s)", dataset2[2], dataset2[3], dataset2[4]));
        ExtentTestManager.testLogger(LogStatus.INFO, "Clicking on reservation link");
        adventureDetails2.reservationLinkClick();

        ExtentTestManager.testLogger(LogStatus.INFO, "Navigating to home page");
        home.navigateToHomePage();
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Searching city (%s)", dataset3[0]));
        home.searchCity(dataset3[0]);
        Assert.assertTrue(home.isNoCityFound() || home.isCityFound(), "No city found");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("City found (%s)", dataset3[0]));
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Selecting city (%s)", dataset3[0]));
        home.selectCity();

        Adventure adventure3 = new Adventure(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Searching adventure (%s)", dataset3[1]));
        adventure3.searchAdventure(dataset3[1]);
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Clicking on adventure (%s)", dataset3[1]));
        adventure3.clickAdventure();

        AdventureDetails adventureDetails3 = new AdventureDetails(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Booking adventure (%s, %s, %s)", dataset3[2], dataset3[3], dataset3[4]));
        adventureDetails3.bookAdventure(dataset3[2], dataset3[3], dataset3[4]);
        Assert.assertTrue(adventureDetails3.verifyBookingSuccessful(), "Unable to book adventure");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("Booked adventure (%s, %s, %s)", dataset3[2], dataset3[3], dataset3[4]));
        ExtentTestManager.testLogger(LogStatus.INFO, "Clicking on reservation link");
        adventureDetails3.reservationLinkClick();

        History history = new History(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, history.getReservation());
        Assert.assertTrue(home.logoutUser(), "Unable to log out");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("User logged out successfully (%s)", username));
    }
}
