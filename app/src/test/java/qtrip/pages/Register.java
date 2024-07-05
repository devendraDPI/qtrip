package qtrip.pages;

import java.time.Duration;
import java.util.UUID;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import qtrip.utilities.SeleniumWrapper;

public class Register {
    WebDriver driver;
    String registerUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/register";
    String loginUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/login";
    public String lastGeneratedUsername = "";

    @FindBy(xpath="//input[@name='email']")
    private WebElement usernameTextBox;

    @FindBy(xpath="//input[@name='password']")
    private WebElement passwordTextBox;

    @FindBy(xpath="//input[@name='confirmpassword']")
    private WebElement confirmPasswordTextBox;

    @FindBy(xpath="//button[contains(text(), 'Register')]")
    private WebElement registerButton;

    public Register(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public void navigateToRegisterPage() {
        SeleniumWrapper.navigateAW(driver, registerUrl);
    }

    public Boolean registerUser(String username, String password, String confirmPassword, Boolean makeUsernameDynamic) {
        username = makeUsernameDynamic ? (UUID.randomUUID().toString() + "_" + username) : username;
        SeleniumWrapper.sendKeysAW(usernameTextBox, username);
        SeleniumWrapper.sendKeysAW(passwordTextBox, password);
        SeleniumWrapper.sendKeysAW(confirmPasswordTextBox, confirmPassword);
        SeleniumWrapper.clickAW(driver, registerButton);
        this.lastGeneratedUsername = username;

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlToBe(loginUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return driver.getCurrentUrl().endsWith("/login");
    }
}
