package qtrip.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;

import qtrip.utilities.SeleniumWrapper;

public class Adventure {
    WebDriver driver;

    @FindBy(xpath = "//select[contains(@onchange, 'selectDuration')]")
    private WebElement selectHourDropdown;

    @FindBy(xpath = "//div[contains(@onclick, 'clearDuration')]")
    private WebElement clearHourButton;

    @FindBy(xpath = "//select[contains(@onchange, 'selectCategory')]")
    private WebElement selectCategoryDropdown;

    @FindBy(xpath = "//div[contains(@onclick, 'clearCategory')]")
    private WebElement clearCategoryButton;

    @FindBy(xpath = "//input[contains(@id, 'search-adventures')]")
    private WebElement searchAdventuresTextBox;

    @FindBy(xpath = "//div[contains(@onclick, 'resetAdventures')]")
    private WebElement clearAdventuresButton;

    @FindBy(xpath = "//div[@id='data']/div")
    private WebElement adventures;

    @FindAll({
        @FindBy(xpath = "//div[@id='data']/div"),
        @FindBy(xpath = "//div[@id='data']")
    })
    List<WebElement> adventuresContents;

    public Adventure(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public void setDurationFilterValue(String duration) {
        try {
            Select select= new Select(selectHourDropdown);
            select.selectByVisibleText(duration);
            Thread.sleep(2000);
            SeleniumWrapper.clickAW(driver, selectHourDropdown);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearHours() {
        SeleniumWrapper.clickAW(driver, clearHourButton);
    }

    public void searchAdventure(String adventureName) {
        try {
            Thread.sleep(2000);
            SeleniumWrapper.sendKeysAW(searchAdventuresTextBox, adventureName);
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickAdventure() throws InterruptedException {
        Thread.sleep(2000);
        SeleniumWrapper.clickAW(driver, adventures);
        Thread.sleep(2000);
    }

    public void setCategoryValue(String category) {
        try {
            Select select= new Select(selectCategoryDropdown);
            select.selectByVisibleText(category);
            Thread.sleep(2000);
            SeleniumWrapper.clickAW(driver, selectCategoryDropdown);
        } catch(Exception e) {
            e.getStackTrace();
        }
    }

    public Boolean verifyAdventureContents(String filteredResult) {
        try {
            Integer actualResult = adventuresContents.size();
            String result = actualResult.toString();
            return result.equals(filteredResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
