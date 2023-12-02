package org.example.Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPage {
@FindBy(xpath = "//div[@class='battlefield-start-button']")
    private WebElement startButton;
    @FindBy(xpath = "//div[@class='battlefield battlefield__rival']//td[@class='battlefield-cell battlefield-cell__empty']/div")
    private List<WebElement> possibleShotList;




}
