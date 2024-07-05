package qtrip.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import qtrip.utilities.SeleniumWrapper;

public class AdventureDetails {
    WebDriver driver;

    @FindBy(xpath="//input[@name='name']")
    private WebElement nameTextBox;

    @FindBy(xpath="//input[@name='date']")
    private WebElement datePicker;

    @FindBy(xpath="//input[@name='person']")
    private WebElement personsCount;

    @FindBy(xpath="//button[text()='Reserve']")
    private WebElement reserveButton;

    @FindBy(xpath="//div[contains(text(),'Greetings! Reservation for this adventure is successful')]")
    private WebElement bookingSuccessMessage;

    @FindBy(xpath="//a[text()='Reservations']")
    private WebElement reservationLink;

    public AdventureDetails(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public void bookAdventure(String name, String date, String persons) throws InterruptedException {
        Thread.sleep(2000);
        SeleniumWrapper.sendKeysAW(nameTextBox, name);
        SeleniumWrapper.sendKeysAW(datePicker, date);
        SeleniumWrapper.sendKeysAW(personsCount, persons);
        SeleniumWrapper.clickAW(driver, reserveButton);
        Thread.sleep(2000);
    }

    public Boolean verifyBookingSuccessful() {
        return bookingSuccessMessage.getText().contains("Greetings! Reservation for this adventure is successful");
    }

    public void reservationLinkClick() {
        SeleniumWrapper.clickAW(driver, reservationLink);
    }
}
