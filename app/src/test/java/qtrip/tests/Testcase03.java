package qtrip.tests;

import org.testng.annotations.Test;

import qtrip.pages.Adventure;
import qtrip.pages.AdventureDetails;
import qtrip.pages.History;
import qtrip.pages.Home;
import qtrip.pages.Login;
import qtrip.pages.Register;
import qtrip.utilities.ExternalDataProvider;

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
        home.navigateToHomePage();

        Register register = new Register(driver);
        register.navigateToRegisterPage();
        register.registerUser(username, password, password, true);
		lastGeneratedUsername=register.lastGeneratedUsername;

		Login login = new Login(driver);
		login.loginUser(lastGeneratedUsername, password);
        home.searchCity(city);
        home.selectCity();

        Adventure adventure = new Adventure(driver);
        adventure.searchAdventure(adventureName);
        adventure.clickAdventure();

        AdventureDetails adventureDetails = new AdventureDetails(driver);
        adventureDetails.bookAdventure(personName, date, persons);
        adventureDetails.verifyBookingSuccessful();
        adventureDetails.reservationLinkClick();

        History history = new History(driver);
        history.getReservation();
        home.logoutUser();
    }
}
