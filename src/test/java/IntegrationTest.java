import org.junit.jupiter.api.Test;
import pl.jano.Logic.Coordinates;
import pl.jano.Logic.EnemyBoard;
import pl.jano.Logic.ShipChaser;
import pl.jano.Pages.MainPage;
import pl.jano.Simulation;

import java.util.ArrayList;
import java.util.List;

public class IntegrationTest extends Simulation {

    //corner points nie dziala jak powinien
    // przyjzyj sie outputowi.

    @Override
    public void start() throws InterruptedException {
        List<Integer> fleet = new ArrayList<>();
        fleet.add(5);
        fleet.add(4);
        fleet.add(3);
        fleet.add(3);
        fleet.add(2);

        List<Coordinates> shootList = new ArrayList<>();
        shootList.add(new Coordinates(1,1));
        shootList.add(new Coordinates(0,4));
        shootList.add(new Coordinates(0,1));

        MainPage mainPage = new MainPage("http://pl.battleship-game.org/id33781256/classic");
        mainPage.startGame();

        EnemyBoard enemyBoard = new EnemyBoard();
        ShipChaser shipChaser = new ShipChaser(false, fleet);


        int iterator=0;

        while (true) {

            if (mainPage.IsEnemyTurn()) {
                Thread.sleep(500);
            } else {

                enemyBoard.setEmptyCells(mainPage.getCoordinatesOfEmptyCells());
                int sinkedCounter = mainPage.sinkedShipsNumber();
                Coordinates nextTarget = null;


                if (shipChaser.isChase()) {
                    if (!shipChaser.getPivotSetted()) {
                        Coordinates hittedCell = shipChaser.getHittedCells().get(0);
                        Coordinates direction = enemyBoard.getLongestEmptyDirection(hittedCell);

                        nextTarget = Coordinates.addCoordinates(hittedCell, direction);
                    } else {
                        Coordinates[] candidates = shipChaser.getEdgedCoordinatesFromList();
                        System.out.println("Corners points from hitted list");
                        System.out.println(candidates.length);
                        candidates[0].print();
                        candidates[1].print();
                        Coordinates direction = enemyBoard.getLongestEmptyDirectionInOneAxis(candidates, !shipChaser.getHorizontal());
                        Coordinates candidate = shipChaser.chooseCandidate(candidates, direction);

                        nextTarget = Coordinates.addCoordinates(candidate, direction);
                    }
                } else {
                    //enemyBoard.setProbabilty(shipChaser.getFleet());
                    //nextTarget = enemyBoard.findCellWithHighestProbability();
                    nextTarget=shootList.get(iterator);
                    iterator++;

                }

                enemyBoard.printProbability();
                System.out.println("Shoot at: ");
                nextTarget.print();
                mainPage.hitCell(nextTarget);
                Thread.sleep(300);
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

                    System.out.println("Zostalo statkow: " + shipChaser.getFleet().size());
                    if (shipChaser.getFleet().isEmpty()) {
                        System.out.println("Wygralem!!");
                        break;
                    }
                }
            }
        }
    }

    @Test
    void LeftCornerUpTest() throws InterruptedException {
            IntegrationTest simulation = new IntegrationTest();
            simulation.start();
    }
}
