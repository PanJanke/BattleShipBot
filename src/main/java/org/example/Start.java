package org.example;

import org.example.Logic.Coordinates;
import org.example.Pages.MainPage;

public class Start {
    public static void main(String[] args) {



        MainPage mainPage = new MainPage("http://pl.battleship-game.org/id33781256/classic");
        mainPage.startGame();

        int hitCounter = mainPage.hittedCellsNumber();
        int sinkedCounter = mainPage.sinkedShipsNumber();
        boolean chasing = false;

        while (true) {
            if (mainPage.IsEnemyTurn()) {
                System.out.println("Czekam");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {

                Coordinates randomCellCoord =  mainPage.getRandomCell();
                System.out.println("strzelam w : "+randomCellCoord.getxCoord() + " " + randomCellCoord.getyCoord());
                mainPage.hitCell(randomCellCoord);

                mainPage.reinitializeElements();

                int hitCounterAfterShot = mainPage.hittedCellsNumber();
                int sinkedCounterAfterShot = mainPage.sinkedShipsNumber();


                if (hitCounterAfterShot > hitCounter ) {
                    chasing=true;
                    hitCounter = hitCounterAfterShot;
                    System.out.println("Trafiony");

                    if (sinkedCounterAfterShot > sinkedCounter) {
                        sinkedCounter = sinkedCounterAfterShot;
                        System.out.print(" Zatopiony!!");
                    }
                }
            }
        }
    }
}
