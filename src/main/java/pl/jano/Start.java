package pl.jano;

import org.checkerframework.checker.units.qual.C;
import pl.jano.Logic.ShipChaser;
import pl.jano.Logic.Coordinates;
import pl.jano.Logic.EnemyBoard;
import pl.jano.Pages.MainPage;

import java.util.ArrayList;
import java.util.List;

public class Start {
    public static void main(String[] args) {

        MainPage mainPage = new MainPage("http://pl.battleship-game.org/id33781256/classic");
        mainPage.startGame();

        EnemyBoard enemyBoard = new EnemyBoard();
        ShipChaser shipChaser = new ShipChaser(false, 5);

        List<Coordinates> setUpList = new ArrayList<>();
        setUpList.add(new Coordinates(1,3));


        while (true) {

            // CZEKAM BO NIE MOJA TURA
            if (mainPage.IsEnemyTurn()) {
                System.out.println("Czekam");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            else {

                int sinkedCounter = mainPage.sinkedShipsNumber();
                Coordinates nextTarget = null;


                //POLUJE -> wybieram potencjalny cel
                if (shipChaser.isChase()) {

                    //nieustalono osi -> lista posiada tylko jeden element!!!
                    if (!shipChaser.getPivotSetted()) {
                        System.out.println("POwinenem tu wejsc tylko RAZ");
                        Coordinates hittedCell = shipChaser.getHittedCells().get(0);
                        Coordinates direction = enemyBoard.getLongestEmptyDirection(hittedCell);
                        nextTarget = Coordinates.addCoordinates(hittedCell,direction);

                    }

                    //ustalono oś
                    else {
                        Coordinates[] candidates = shipChaser.getEdgedCoordinatesFromList();
                        //horizontal to vertical ....
                        Coordinates direction = enemyBoard.getLongestEmptyDirectionInOneAxis(candidates,!shipChaser.getHorizontal());

                        System.out.println(shipChaser.getHorizontal());
                        System.out.println(candidates.length);
                        candidates[0].print();
                        candidates[1].print();
                        direction.print();

                        Coordinates candidate = shipChaser.chooseCandidate(candidates,direction);
                        nextTarget = Coordinates.addCoordinates(candidate,direction);

                    }

                }

                //CEL losowy
                else {
                    //nextTarget = mainPage.getRandomCellCoord();
                    nextTarget=setUpList.get(0);
                }

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

                //pobieram informacje po strzale
                int sinkedCounterAfterShot = mainPage.sinkedShipsNumber();
                if (sinkedCounterAfterShot > sinkedCounter) {
                    shipChaser = new ShipChaser(false, 5);
                    System.out.print(" Zatopiony!!");

                }

                enemyBoard.setEmptyCells(mainPage.getCoordinatesOfEmptyCells());
            }

        }

    }

}

