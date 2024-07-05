package qtrip.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import qtrip.pages.Adventure;
import qtrip.pages.Home;
import qtrip.utilities.ExternalDataProvider;
import qtrip.utilities.reports.ExtentTestManager;

public class Testcase02 extends Base {
    @Test(
        description = "Verify search and filter flow",
        enabled = true,
        priority = 2,
        groups = {"Search and filter flow"},
        dataProvider = "qtripTestDataSet",
        dataProviderClass = ExternalDataProvider.class
    )
    public void TC02(String cityName, String categoryFilter, String durationFilter, String resultFilter, String resultUnFilter) throws InterruptedException {
        Home home = new Home(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, "Navigating to home page");
        home.navigateToHomePage();
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Searching city (%s)", cityName));
        home.searchCity(cityName);
        Assert.assertTrue(home.isNoCityFound() || home.isCityFound(), "No city found");
        ExtentTestManager.testLogger(LogStatus.PASS, String.format("City found (%s)", cityName));
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Selecting city (%s)", cityName));
        home.selectCity();

        Adventure adventure = new Adventure(driver);
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Selecting duration (%s)", durationFilter));
        adventure.setDurationFilterValue(durationFilter);
        ExtentTestManager.testLogger(LogStatus.INFO, String.format("Selecting category (%s)", categoryFilter));
        adventure.setCategoryValue(categoryFilter);
        ExtentTestManager.testLogger(LogStatus.INFO, "Verifying adventure contents (filter)");
        adventure.verifyAdventureContents(resultFilter);
        ExtentTestManager.testLogger(LogStatus.INFO, "Verifying adventure contents (unfiltered)");
        adventure.verifyAdventureContents(resultUnFilter);
    }
}
