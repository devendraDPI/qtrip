package qtrip.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import qtrip.utilities.SeleniumWrapper;

public class Login {
    WebDriver driver;
    String homeUrl = "https://qtripdynamic-qa-frontend.vercel.app/";
    String loginUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/login";

    @FindBy(xpath="//input[@name='email']")
    private WebElement usernameTextBox;

    @FindBy(xpath="//input[@name='password']")
    private WebElement passwordTextBox;

    @FindBy(xpath="//button[contains(text(), 'Login')]")
    private WebElement loginButton;

    @FindBy(xpath="//div[contains(text(), 'Logout')]")
    WebElement logoutButton;

    public Login(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public void navigateToLoginPage() {
        SeleniumWrapper.navigateAW(driver, loginUrl);
    }

    public Boolean verifyUserIsLoggedIn() {
        return logoutButton.isDisplayed();
    }

    public Boolean loginUser(String username, String password) {
        try {
            SeleniumWrapper.sendKeysAW(usernameTextBox, username);
            SeleniumWrapper.sendKeysAW(passwordTextBox, password);
            SeleniumWrapper.clickAW(driver, loginButton);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlToBe(homeUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return verifyUserIsLoggedIn();
    }
}
