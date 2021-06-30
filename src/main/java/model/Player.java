package model;

/**
 * Player encapsulates a player in bowling with the following attributes:
 * List<@see org.example.BowlingScoreKeeper.Scorecard>, name, isActivePlayer
 *
 * Example Usage:
 * Player player = new Player("Brad");
 * player.addScoreToPlayer(8);
 *
 */
public class Player {
    private Scorecard scorecard;
    private String name;
    private boolean isActivePlayer;
    private static final int maxLengthOfName = 32;

    /**
     * Sets the players name, gives them a new scorecard, and sets isActivePlayer to false
     * @param name the name that is given to the player
     */
    public Player(String name) {
        setName(name);
        this.scorecard = new Scorecard();
        isActivePlayer = false;
    }

    public boolean isActivePlayer() {
        return isActivePlayer;
    }

    public void setActivePlayer(boolean activePlayer) {
        isActivePlayer = activePlayer;
    }

    public Scorecard getScorecard() {
        return scorecard;
    }

    public String getName() {
        return name;
    }

    /**
     * Sets the name and validates that the name is between 1 and 31 (inclusive) characters
     * @param name name of the player
     * @throws RuntimeException if the name is less than 1 character or more than 31 characters
     */
    public void setName(String name) throws RuntimeException {
        if (name.length() > 0 && name.length() < maxLengthOfName) {
            this.name = name;
        } else {
            throw new RuntimeException(ErrorMessages.invalidNameLength);
        }
    }

    public void addScoreToPlayer(int score) {
        scorecard.addScoreToScorecard(score);
    }

    public boolean isPlayerScoreCardComplete() {
        return scorecard.isScorecardComplete();
    }

    public boolean isPlayersTurnOver() {
        return scorecard.isReadyForNextFrame();
    }
}
