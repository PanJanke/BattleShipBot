package pl.jano;

import pl.jano.Bot.Simulation;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Simulation simulation = new Simulation();
        simulation.start("http://pl.battleship-game.org/", simulation.RUSSIAN_FLEET);

    }
}
