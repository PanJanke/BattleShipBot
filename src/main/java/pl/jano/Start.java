package pl.jano;

import pl.jano.Logic.ShipChaser;
import pl.jano.Logic.Coordinates;
import pl.jano.Logic.EnemyBoard;
import pl.jano.Pages.MainPage;

public class Start {
    public static void main(String[] args) {

        MainPage mainPage = new MainPage("http://pl.battleship-game.org/id33781256/classic");
        mainPage.startGame();

        EnemyBoard enemyBoard = new EnemyBoard();
        ShipChaser shipChaser = new ShipChaser(false, 5);


        while (true) {

            // CZEKAM BO NIE MOJA TURA
            if (mainPage.IsEnemyTurn()) {
                System.out.println("Czekam");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {

                int sinkedCounter = mainPage.sinkedShipsNumber();
                Coordinates nextTarget = null;


                //POLUJE -> wybieram potencjalny cel
                if (shipChaser.isChase()) {

                    //nieustalono osi -> lista posiada tylko jeden element!!!
                    if (!shipChaser.getPivotSetted()) {
                        System.out.println("Lista powwina byc 1: " + shipChaser.getHittedCells().size());

                        Coordinates hittedCell = shipChaser.getHittedCells().get(0);
                        Coordinates direction = enemyBoard.getLongestEmptyDirection(hittedCell);
                        hittedCell.addCoordinates(direction);
                        nextTarget = hittedCell;

                    }

                    //ustalono oś
                    else {
                        Coordinates[] candidates = shipChaser.getEdgedCoordinatesFromList();
                        Coordinates direction = enemyBoard.getLongestEmptyDirectionInOneAxis(candidates,shipChaser.getHorizontal());
                        /// TESTY DO choose Candidate
                        Coordinates candidate = shipChaser.chooseCandidate(candidates,direction);



                    }

                }

                //CEL losowy
                else {
                    nextTarget = mainPage.getRandomCellCoord();
                }

                System.out.println("strzelam w : " + nextTarget.getxCoord() + " " + nextTarget.getyCoord());
                mainPage.hitCell(nextTarget);
                mainPage.reinitializeElements();


                if (mainPage.IsEnemyTurn()) {
                    System.out.println("pudło - wychodzę z pętli");
                    continue;
                } else {
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
            }

            enemyBoard.setEmptyCells(mainPage.getCoordinatesOfEmptyCells());
            enemyBoard.printOutBoard();
        }

    }

}

