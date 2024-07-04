package qtrip.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class History {
    WebDriver driver;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/";

    @FindBy(xpath="//tbody[@id='reservation-table']/tr/th")
    private List<WebElement> transactionIds;

    @FindBy(xpath="//button[text()='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath="//div[contains(text(),'Oops! You have not made any reservations yet!')]")
    private WebElement verifyCancel;

    @FindBy(xpath="//div[contains(text(), 'Logout')]")
    private WebElement logoutButton;

    public History(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public void navigateToHistoryPage() {
        if (!driver.getCurrentUrl().equals(url)) {
            driver.get(url);
        }
    }

    public void getReservation() {
        try {
            Thread.sleep(2000);
            for (WebElement transactionId : transactionIds) {
                System.out.println("Transaction Id: " + transactionId.getText());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelReservation() throws InterruptedException {
        Thread.sleep(2000);
        cancelButton.click();
        FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver)
                                    .withTimeout((Duration.ofSeconds(10)))
                                    .pollingEvery(Duration.ofMillis(250))
                                    .ignoring(Exception.class);
        fWait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(text(),'Oops! You have not made any reservations yet!')]")
        ));
        driver.navigate().refresh();
    }

    public Boolean verifyCancelReservation(){
        return verifyCancel.getText().contains("Oops! You have not made any reservations yet!");
    }
}
