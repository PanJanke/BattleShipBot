package pl.jano.Pages;

import pl.jano.Logic.Coordinates;
import pl.jano.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainPage {

    private final WebDriver driver;
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

    @FindBy(xpath = "//div[contains(@class, 'game-over-win')]")
    private List<WebElement> winNotification;

    @FindBy(xpath = "//div[contains(@class, 'game-over-loose')]")
    private List<WebElement> looseNotification;

    @FindBy(xpath = "//div[contains(@class, 'rival-leave')]")
    private List<WebElement> rivalLeaveNotification;

    @FindBy(xpath = "//li[@class='placeships-variant placeships-variant__randomly']/span")
    private WebElement randomFleetPlacingButton;

    public MainPage(String url) {
        WebDriver driver;
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get(url);
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    public void quit(){
        driver.quit();
    }

    public boolean rivalLeaveChecker(){
        return !rivalLeaveNotification.isEmpty() && rivalLeaveNotification.get(0).isDisplayed();
    }
    public boolean winChecker() {
        return !winNotification.isEmpty() && winNotification.get(0).isDisplayed();
    }

    public boolean looseChecker() {
        return !looseNotification.isEmpty() && looseNotification.get(0).isDisplayed();
    }

    public boolean isGameContinue() {
        return !looseChecker() && !winChecker() && !rivalLeaveChecker();
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

    public int hitCellsNumber() {
        return hittedCellsList.size();
    }

    public Coordinates getRandomCellCoord() {
        Random random = new Random();
        int size = possibleShotList.size();
        WebElement randomCell = possibleShotList.get(random.nextInt(size));
        int xCoord = Integer.parseInt(randomCell.getAttribute("data-x"));
        int yCoord = Integer.parseInt(randomCell.getAttribute("data-y"));
        return new Coordinates(yCoord, xCoord);
    }

    public void clickRandomFleetPLacing(){
        randomFleetPlacingButton.click();
    }

    public void hitCell(Coordinates coordinates) {
        for (WebElement element : possibleShotList) {
            int x = Integer.parseInt(element.getAttribute("data-x"));
            int y = Integer.parseInt(element.getAttribute("data-y"));
            if (x == coordinates.getxCoord() && y == coordinates.getyCoord()) {
                element.click();
                return;
            }
        }
    }

    public int sankShipsNumber() {
        return sinkedList.size();
    }

    public List<Coordinates> getCoordinatesOfEmptyCells() {
        List<Coordinates> coordinatesList = new ArrayList<>();

        for (WebElement element : possibleShotList) {
            int xCoord = Integer.parseInt(element.getAttribute("data-x"));
            int yCoord = Integer.parseInt(element.getAttribute("data-y"));
            Coordinates coordinates = new Coordinates(yCoord, xCoord);
            coordinatesList.add(coordinates);
        }
        return coordinatesList;
    }
}
