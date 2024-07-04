package qtrip.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import qtrip.pages.Adventure;
import qtrip.pages.Home;
import qtrip.utilities.ExternalDataProvider;

public class Testcase02 extends Base {
    @Test(
        description = "Verify search and filter flow",
        enabled = true,
        priority = 2,
        groups = {"Search and filter flow"},
        dataProvider = "qtripTestDataSet",
        dataProviderClass = ExternalDataProvider.class
    )
    public void TC02(String cityName, String categoryFilter, String DurationFilter, String resultFilter, String resultUnFilter) throws InterruptedException {
        Home home = new Home(driver);
        home.navigateToHomePage();
        home.searchCity(cityName);
        Assert.assertTrue(home.isNoCityFound() || home.isCityFound());
        home.selectCity();

        Adventure adventure = new Adventure(driver);
        adventure.setDurationFilterValue(DurationFilter);
        adventure.setCategoryValue(categoryFilter);
        adventure.verifyAdventureContents(resultFilter);
        adventure.verifyAdventureContents(resultUnFilter);
    }
}
