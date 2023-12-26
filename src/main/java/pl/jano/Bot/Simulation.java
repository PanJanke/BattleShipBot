package pl.jano.Bot;

import pl.jano.Logic.Coordinates;
import pl.jano.Logic.EnemyBoard;
import pl.jano.Logic.ShipChaser;
import pl.jano.Pages.MainPage;

import java.util.List;

public class Simulation {
    private static final long SLEEP_TIME_MILLISECONDS = 500;
    public static final List<Integer> CLASSIC_FLEET = List.of(5, 4, 3, 3, 2);
    public static final List<Integer> RUSSIAN_FLEET = List.of(4, 3, 3, 2, 2, 2, 1, 1, 1, 1);


    public void start(String url, List<Integer> inputFleet) throws InterruptedException {

        int moveCounter = 0;
        MainPage mainPage = new MainPage(url);
        mainPage.clickRandomFleetPLacing();
        mainPage.startGame();

        EnemyBoard enemyBoard = new EnemyBoard();
        ShipChaser shipChaser = new ShipChaser(false, inputFleet);


        while (mainPage.isGameContinue()) {
            mainPage.reinitializeElements();

            if (mainPage.IsEnemyTurn()) {
                Thread.sleep(SLEEP_TIME_MILLISECONDS);
            } else {

                enemyBoard.setEmptyCells(mainPage.getCoordinatesOfEmptyCells());
                int sinkedCounter = mainPage.sankShipsNumber();
                int hitedCounter = mainPage.hitCellsNumber();
                Coordinates nextTarget;


                if (shipChaser.isChase()) {

                    if (shipChaser.getHitCells().size() == 1) {
                        Coordinates hittedCell = shipChaser.getHitCells().get(0);
                        Coordinates direction = enemyBoard.getLongestEmptyDirection(hittedCell);

                        nextTarget = Coordinates.addCoordinates(hittedCell, direction);
                    } else {
                        Coordinates[] candidates = shipChaser.getEdgedCoordinatesFromList();
                        Coordinates direction = enemyBoard.getLongestEmptyDirectionInOneAxis(candidates);
                        Coordinates candidate = shipChaser.chooseCandidate(candidates, direction);

                        nextTarget = Coordinates.addCoordinates(candidate, direction);
                    }
                } else {
                    enemyBoard.setProbability(shipChaser.getFleet());
                    nextTarget = enemyBoard.findCellWithHighestProbability();
                }

                mainPage.hitCell(nextTarget);
                mainPage.reinitializeElements();
                moveCounter++;

                if (mainPage.hitCellsNumber() > hitedCounter) {
                    shipChaser.setChase(true);
                    shipChaser.addHitCell(nextTarget);
                }

                if (mainPage.sankShipsNumber() > sinkedCounter) {
                    shipChaser.chaseFinished();
                }

            }
        }

        System.out.println();
        if (mainPage.winChecker()) {
            System.out.print("Win");
        } else if (mainPage.looseChecker()) {
            System.out.print("Loose");
        } else if (mainPage.rivalLeaveChecker()) {
            System.out.print("Rival Left");
        }
        System.out.print(": " + moveCounter + "\n");


        mainPage.quit();
    }


}

