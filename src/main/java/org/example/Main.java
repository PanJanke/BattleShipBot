package org.example;

import org.example.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class Main {

    private static int getRandomIndex(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }



    public static void main(String[] args) {

        WebDriver driver;
        driver = DriverFactory.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("http://pl.battleship-game.org/id33781256/classic");


        WebElement startButton = driver.findElement(By.xpath("//div[@class='battlefield-start-button']"));
        //List<WebElement>  = driver.findElements(By.xpath("//div[@class='battlefield battlefield__rival']"));
       // WebElement enemyBoard  = driver.findElement(By.xpath("//div[@class='battlefield battlefield__rival']"));
        WebElement enemyBoardOnWait  = driver.findElement(By.xpath("//div[@class='battlefield battlefield__rival battlefield__wait']"));
        startButton.click();


        while(true){
            List<WebElement> enemyBoard = driver.findElements(By.xpath("//div[@class='battlefield battlefield__rival']"));
            if(enemyBoard.size()==0){
                System.out.println("Czekam");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


            }
            else{

                List<WebElement> possibleShotsList = driver.findElements(By.xpath("//div[@class='battlefield battlefield__rival']//td[@class='battlefield-cell battlefield-cell__empty']/div"));
                WebElement randomShot = possibleShotsList.get(getRandomIndex(possibleShotsList.size()));
                randomShot.getAttribute("data-y");
                String dataYValue = randomShot.getAttribute("data-y");
                String dataXValue = randomShot.getAttribute("data-x");
                System.out.println("strzelam na pozycji x:" + dataXValue +"  y:"+dataYValue);
                randomShot.click();
            }


        }


    }
}