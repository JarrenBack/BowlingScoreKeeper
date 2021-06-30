package model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Encapsulates a game or lane of bowling with the following properties:
 * List<@see org.example.BowlingScoreKeeper.Player>
 *
 * Example usage:
 * List<String> playerNames = Arrays.asList("Billy", "Joe");
 * Game game = new Game(playerNames);
 * List<Boolean> threePins = Arrays.asList(true, true, true, false, false, false, false, false, false, false);
 * game.addScore(threePins)
 */
public class Game {
    private List<Player> players = new ArrayList<>();
    private final static Logger logger = Logger.getLogger(Game.class.getName());
    private int activePlayerIndex;

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Starts a game with player names provided and sets the first player as the active player
     * @param playerNames list of names that will determine the number of players
     * @throws RuntimeException if player name contains no names
     */
    public Game (@NotNull List<String> playerNames) throws RuntimeException {
        if (playerNames.size() > 0) {
            for (String playerName : playerNames) {
                players.add(new Player(playerName));
            }
            activePlayerIndex = 0;
            players.get(0).setActivePlayer(true);
        } else {
            throw new RuntimeException(ErrorMessages.cannotStartGameWithNoNames);
        }
    }

    /**
     * Checks each player's score card to see if it is complete
     * @return boolean representing if the game is over
     */
    public boolean isGameOver() {
        for (Player p : players) {
            if (!p.isPlayerScoreCardComplete()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds the score to the current active player.
     * @param pins list of pins that are still present on the lane. true represents that the pin is still present and
     * false represents that the pin has been knocked over
     */
    public void addScore(List<Boolean> pins) {
        int score = 0;
        for (Boolean pin : pins) {
            if (pin) { score++; }
        }
        this.addScore(score);
    }

    /**
     * Adds the score to the current active player. If their turn is over, it will move the
     * activePlayerIndex to the next player. This also sets isActivePlayer on the Player model
     * @param score the score to be added to the player
     * @throws RuntimeException if the game is already over and a new score is attempted to be added
     */
    protected void addScore(int score) {
        if (!isGameOver()) {
            Player currentActivePlayer = players.get(activePlayerIndex);
            currentActivePlayer.addScoreToPlayer(score);
            logger.info("Adding score of " + score + " to " + currentActivePlayer.getName());
            if (currentActivePlayer.isPlayersTurnOver()) {
                players.get(activePlayerIndex).setActivePlayer(false);
                activePlayerIndex = (activePlayerIndex + 1) % players.size();
                players.get(activePlayerIndex).setActivePlayer(true);
                logger.info("Setting active player to " + players.get(activePlayerIndex).getName());
            }
        } else {
            throw new RuntimeException(ErrorMessages.cannotAddScoreIfTheGameIsOver);
        }
    }
}
