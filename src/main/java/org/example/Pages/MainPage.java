package org.example.Pages;

import org.example.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Random;


public class MainPage {

    private WebDriver driver;
    @FindBy(xpath = "//div[@class='battlefield-start-button']")
    private WebElement startButton;
    @FindBy(xpath = "//div[@class='battlefield battlefield__rival']//td[@class='battlefield-cell battlefield-cell__empty']/div")
    private List<WebElement> possibleShotList;
    @FindBy(xpath = "//div[@class='battlefield battlefield__rival']//td[contains(@class, 'battlefield-cell__hit')]")
    private List<WebElement> hittedCellsList;
    @FindBy(xpath = "//div[@class='battlefield battlefield__rival']//div[contains(@class, 'ship-box')]")
    private List<WebElement> sinkedList;

    @FindBy(xpath = "//div[@class='battlefield battlefield__rival']")
    private List<WebElement> enemyBoard;

    public MainPage(String url) {
        WebDriver driver;
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get(url);
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void reinitializeElements() {
        PageFactory.initElements(this.driver, this);
    }

    public void startGame() {
        startButton.click();
    }

    public boolean IsEnemyTurn() {
        return enemyBoard.isEmpty();
    }

    public int hittedCellsNumber() {
        return hittedCellsList.size();
    }

    public void hitRandomCell() {
        Random random = new Random();
        int size = possibleShotList.size();
        possibleShotList.get(random.nextInt(size)).click();
    }

    public int sinkedShipsNumber() {
        return sinkedList.size();
    }
}
