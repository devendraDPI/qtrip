package qtrip.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import qtrip.utilities.SeleniumWrapper;

public class Home {
    WebDriver driver;
    String url = "https://qtripdynamic-qa-frontend.vercel.app";

    @FindBy(xpath="//a[contains(text(), 'Register')]")
    private WebElement registerButton;

    @FindBy(xpath="//div[contains(text(), 'Logout')]")
    private WebElement logoutButton;

    @FindBy(xpath="//input[contains(@placeholder, 'Search a City')]")
    private WebElement searchTextBox;

    @FindBy(xpath="//ul[contains(@id, 'results')]")
    private WebElement suggestionsList;

    @FindBy(xpath="//ul[contains(@id, 'results')]/h5[contains(text(), 'No City found')]")
    private WebElement noSuggestionsList;

    public Home(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public void navigateToHomePage() {
        SeleniumWrapper.navigateAW(driver, url);
    }

    public void navigateToRegisterPage() {
        if (!driver.getCurrentUrl().equals(url + "/pages/register")) {
            SeleniumWrapper.clickAW(driver, registerButton);
        }
    }

    public Boolean isUserLoggedIn() {
        return logoutButton.isDisplayed();
    }

    public Boolean logoutUser() {
        return SeleniumWrapper.clickAW(driver, logoutButton);
    }

    public void searchCity(String city) throws InterruptedException {
        Thread.sleep(1000);
        SeleniumWrapper.sendKeysAW(searchTextBox, city);
    }

    public Boolean isNoCityFound() {
        try {
            return noSuggestionsList.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean isCityFound() {
        try {
            return suggestionsList.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void selectCity() {
        try {
            Thread.sleep(1000);
            SeleniumWrapper.clickAW(driver, suggestionsList);
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
