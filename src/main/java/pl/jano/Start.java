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
        ShipChaser shipChaser = new ShipChaser(false,5);



        while (true) {

            // CZEKAM BO NIE MOJA TURA
            if (mainPage.IsEnemyTurn()) {
                System.out.println("Czekam");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                //POLUJE DALEJ
            } else if(shipChaser.isChase()){
                //ustalono oś
                if(shipChaser.getPivotSetted()){

                }
                //dalej nieustalono osi
                else{

                }
            }

                //STRZELAM LOSOWO
            else {


                //pobieram informacje przed strzałem
                int sinkedCounter = mainPage.sinkedShipsNumber();

                Coordinates randomCellCoord = mainPage.getRandomCellCoord();
                System.out.println("strzelam w : " + randomCellCoord.getxCoord() + " " + randomCellCoord.getyCoord());
                mainPage.hitCell(randomCellCoord);

                //ponowna reinicjalizacja elementów
                mainPage.reinitializeElements();


                //BLOK SPRAWDZAJĄCY CZY TRAFIŁEM ==wciaz moja tura
                if(mainPage.IsEnemyTurn()){
                    System.out.println("pudło - wychodzę z pętli");
                    continue;
                }


                else{
                    shipChaser.setChase(true);
                }

                //pobieram informacje po strzale
                int sinkedCounterAfterShot = mainPage.sinkedShipsNumber();
                    if (sinkedCounterAfterShot > sinkedCounter) {
                        shipChaser.setChase(false);
                        System.out.print(" Zatopiony!!");

                    }
                }

                enemyBoard.setEmptyCells(mainPage.getCoordinatesOfEmptyCells());
                enemyBoard.printOutBoard();

            }
        }
    }

