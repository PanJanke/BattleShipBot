package pl.jano;

import pl.jano.Logic.Coordinates;
import pl.jano.Logic.EnemyBoard;
import pl.jano.Logic.ShipChaser;
import pl.jano.Pages.MainPage;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private static final long SLEEP_TIME_MILLISECONDS = 500;


    public List<Integer> classicFleet() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(4);
        list.add(3);
        list.add(3);
        list.add(2);

        return list;
    }


    public List<Integer> russianFleet() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(3);
        list.add(3);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);

        return list;
    }

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
                Coordinates nextTarget = null;


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

                if (mainPage.hitCellsNumber()>hitedCounter) {
                    shipChaser.setChase(true);
                    shipChaser.addHitCell(nextTarget);
                }

                if (mainPage.sankShipsNumber() > sinkedCounter) {
                    shipChaser.chaseFinished();
                }

            }
        }


        if (mainPage.winChecker()) {
            System.out.println("Win");
        } else if (mainPage.looseChecker()) {
            System.out.println("Loose");
        } else if (mainPage.rivalLeaveChecker()) {
            System.out.println("Rival Left");
        }
        System.out.print(" : " + moveCounter);

        mainPage.quit();
    }


    public static void main(String[] args) throws InterruptedException {

        Simulation simulation = new Simulation();
        simulation.start("http://pl.battleship-game.org/", simulation.russianFleet());




    }

}

