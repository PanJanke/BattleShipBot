package pl.jano;

import pl.jano.Logic.Coordinates;
import pl.jano.Logic.EnemyBoard;
import pl.jano.Logic.ShipChaser;
import pl.jano.Pages.MainPage;

import java.util.ArrayList;
import java.util.List;

public class Start {
    public static void main(String[] args) {

        List<Integer> fleet = new ArrayList<>();
        fleet.add(5);
        fleet.add(4);
        fleet.add(3);
        fleet.add(3);
        fleet.add(2);


        MainPage mainPage = new MainPage("http://pl.battleship-game.org/id33781256/classic");
        mainPage.startGame();

        EnemyBoard enemyBoard = new EnemyBoard();
        ShipChaser shipChaser = new ShipChaser(false, fleet);


        while (true) {

            if (mainPage.IsEnemyTurn()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            else {

                enemyBoard.setEmptyCells(mainPage.getCoordinatesOfEmptyCells());
                int sinkedCounter = mainPage.sinkedShipsNumber();
                Coordinates nextTarget = null;


                if (shipChaser.isChase()) {
                    if (!shipChaser.getPivotSetted()) {
                        Coordinates hittedCell = shipChaser.getHittedCells().get(0);
                        Coordinates direction = enemyBoard.getLongestEmptyDirection(hittedCell);

                        nextTarget = Coordinates.addCoordinates(hittedCell, direction);
                    }

                    else {
                        Coordinates[] candidates = shipChaser.getEdgedCoordinatesFromList();
                        Coordinates direction = enemyBoard.getLongestEmptyDirectionInOneAxis(candidates, !shipChaser.getHorizontal());
                        Coordinates candidate = shipChaser.chooseCandidate(candidates, direction);

                        nextTarget = Coordinates.addCoordinates(candidate, direction);
                    }
                }

                else {
                    enemyBoard.setProbabilty(shipChaser.getFleet());
                    nextTarget = enemyBoard.findCellWithHighestProbability();
                }

                enemyBoard.printProbability();
                System.out.println("strzelam w : " + nextTarget.getxCoord() + " " + nextTarget.getyCoord());
                mainPage.hitCell(nextTarget);
                mainPage.reinitializeElements();


                if (mainPage.IsEnemyTurn()) {
                    System.out.println("pudło - wychodzę z pętli");
                    continue;
                } else {
                    System.out.println("Trafiam");
                    shipChaser.setChase(true);
                    shipChaser.addHittedCell(nextTarget);
                    shipChaser.checkPossiblePivot();
                }

                int sinkedCounterAfterShot = mainPage.sinkedShipsNumber();
                if (sinkedCounterAfterShot > sinkedCounter) {

                    System.out.print(" Zatopiony!!");
                    //fix that
                    shipChaser.setChase(false);
                    shipChaser.removeSinkedShip();
                    shipChaser.clearHittedList();
                    shipChaser.setPivotSetted(false);
                    shipChaser.setHorizontal(false);

                    System.out.print("Zostalo statkow: "+shipChaser.getFleet().size());

                }


            }

        }

    }

}

