package controller;

import model.Game;
import model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * GameController is the go-between for the view and the model. It is also
 * the contact point for the hardware of the bowling alley to communicate the number of pins
 * that were knocked down.
 *
 * Usage example:
 * List<String> names = new Arrays.asList("Billy", "Joe");
 * GameController gameController = new GameController(names);
 *
 * List<Boolean> fivePins = Arrays.asList(true, true, true, true, true, false, false, false, false, false);
 * gameController.addScore(fivePins);
 */
public class GameController {
    private Game game;

    public GameController(ArrayList<String> names) {
        this.game = new Game(names);
    }

    public List<Player> getPlayers() {
        return this.game.getPlayers();
    }

    // true means pins are there, false is pins aren't there
    public void addScore(List<Boolean> pins) {
        this.game.addScore(pins);
    }

    public boolean isGameOver() { return this.game.isGameOver(); }
}
