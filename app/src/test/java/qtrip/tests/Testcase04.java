package qtrip.tests;

import org.testng.annotations.Test;

import qtrip.pages.Adventure;
import qtrip.pages.AdventureDetails;
import qtrip.pages.History;
import qtrip.pages.Home;
import qtrip.pages.Login;
import qtrip.pages.Register;
import qtrip.utilities.ExternalDataProvider;

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
        Home home= new Home(driver);
        home.navigateToHomePage();

        Register register = new Register(driver);
        register.navigateToRegisterPage();
        register.registerUser(username, password, password, true);
		lastGeneratedUsername = register.lastGeneratedUsername;

		Login login= new Login(driver);
		login.loginUser(lastGeneratedUsername, password);
        String[] dataset1 = dataSet1.split(";");
        String[] dataset2 = dataSet2.split(";");
        String[] dataset3 = dataSet3.split(";");
        home.searchCity(dataset1[0]);
        home.selectCity();

        Adventure adventure = new Adventure(driver);
        adventure.searchAdventure(dataset1[1]);
        adventure.clickAdventure();
        Thread.sleep(2000);

        AdventureDetails adventureDetails = new AdventureDetails(driver);
        adventureDetails.bookAdventure(dataset1[2], dataset1[3], dataset1[4]);
        adventureDetails.verifyBookingSuccessful();
        adventureDetails.reservationLinkClick();
        Thread.sleep(2000);

        home.navigateToHomePage();
        home.searchCity(dataset2[0]);
        home.selectCity();

        Adventure adventure1 = new Adventure(driver);
        adventure1.searchAdventure(dataset2[1]);
        adventure1.clickAdventure();
        Thread.sleep(2000);

        AdventureDetails adventureDetails1 = new AdventureDetails(driver);
        adventureDetails1.bookAdventure(dataset2[2], dataset2[3], dataset2[4]);
        adventureDetails1.verifyBookingSuccessful();
        adventureDetails1.reservationLinkClick();
        Thread.sleep(2000);

        home.navigateToHomePage();
        home.searchCity(dataset3[0]);
        home.selectCity();

        Adventure adventure2 = new Adventure(driver);
        adventure2.searchAdventure(dataset3[1]);
        adventure2.clickAdventure();
        Thread.sleep(2000);

        AdventureDetails adventureDetails2 = new AdventureDetails(driver);
        adventureDetails2.bookAdventure(dataset3[2], dataset3[3], dataset3[4]);
        adventureDetails2.verifyBookingSuccessful();
        adventureDetails2.reservationLinkClick();
        Thread.sleep(2000);

        History history = new History(driver);
        history.getReservation();
        home.logoutUser();
    }
}
