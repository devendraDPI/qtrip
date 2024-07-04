package qtrip.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

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
        if (!driver.getCurrentUrl().equals(url)) {
            driver.get(url);
        }
    }

    public void navigateToRegisterPage() {
        if (!driver.getCurrentUrl().equals(url + "/pages/register")) {
            registerButton.click();
        }
    }

    public Boolean isUserLoggedIn() {
        return logoutButton.isDisplayed();
    }

    public void logoutUser() {
        logoutButton.click();
    }

    public void searchCity(String city) throws InterruptedException {
        searchTextBox.clear();
        Thread.sleep(2000);
        searchTextBox.sendKeys(city);
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
            Thread.sleep(2000);
            suggestionsList.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
