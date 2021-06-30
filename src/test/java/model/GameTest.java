package model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    public void cannotStartGameWithNoNames() {
        List<String> playerNames = Arrays.asList();
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> new Game(playerNames),
                ""
        );

        assertEquals(ErrorMessages.cannotStartGameWithNoNames, thrown.getMessage());
    }

    @Test
    public void addScoreToPlayerSimple() {
        List<String> playerNames = Arrays.asList("Billy", "Joe");
        Game game = new Game(playerNames);
        game.addScore(1);
        game.addScore(3);

        game.addScore(5);
        game.addScore(5);

        assertEquals(1, game.getPlayers().get(0).getScorecard().getFrames().get(0).getFirstPinCount().get());
        assertEquals(3, game.getPlayers().get(0).getScorecard().getFrames().get(0).getSecondPinCount().get());

        assertEquals(5, game.getPlayers().get(1).getScorecard().getFrames().get(0).getFirstPinCount().get());
        assertEquals(5, game.getPlayers().get(1).getScorecard().getFrames().get(0).getSecondPinCount().get());
    }

    @Test
    public void addScoreToPlayerStrike() {
        List<String> playerNames = Arrays.asList("Billy", "Joe");
        Game game = new Game(playerNames);
        game.addScore(10);

        game.addScore(5);
        game.addScore(5);

        assertEquals(10, game.getPlayers().get(0).getScorecard().getFrames().get(0).getFirstPinCount().get());
        assertEquals(0, game.getPlayers().get(0).getScorecard().getFrames().get(0).getSecondPinCount().get());

        assertEquals(5, game.getPlayers().get(1).getScorecard().getFrames().get(0).getFirstPinCount().get());
        assertEquals(5, game.getPlayers().get(1).getScorecard().getFrames().get(0).getSecondPinCount().get());
    }

    @Test
    public void addScoreToPlayerFullGame() {
        List<String> playerNames = Arrays.asList("Billy", "Joe");
        Game game = new Game(playerNames);

        for (int i = 0; i < 9; i++) {
            game.addScore(10);
            game.addScore(5);
            game.addScore(3);
        }
        game.addScore(10);
        game.addScore(10);
        game.addScore(10);
        assertEquals(false, game.isGameOver());
        game.addScore(5);
        game.addScore(3);

        assertEquals(300, game.getPlayers().get(0).getScorecard().calculateScore());
        assertEquals(80, game.getPlayers().get(1).getScorecard().calculateScore());
        assertEquals(true, game.isGameOver());

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> game.addScore(5),
                ""
        );
        assertEquals(ErrorMessages.cannotAddScoreIfTheGameIsOver, thrown.getMessage());
    }

    @Test
    public void addScoreToOnlyOnePlayer() {
        List<String> playerNames = Arrays.asList("Billy", "Joe");
        Game game = new Game(playerNames);
        List<Boolean> fivePins = Arrays.asList(true, true, true, true, true, false,false,false,false,false);
        List<Boolean> threePins = Arrays.asList(true, true, true, false, false, false,false,false,false,false);
        game.addScore(fivePins);
        game.addScore(threePins);

        assertEquals(5, game.getPlayers().get(0).getScorecard().getFrames().get(0).getFirstPinCount().get());
        assertEquals(3, game.getPlayers().get(0).getScorecard().getFrames().get(0).getSecondPinCount().get());
    }

    @Test
    public void addInvalidScore() {
        List<String> playerNames = Arrays.asList("Billy", "Joe");
        Game game = new Game(playerNames);
        List<Boolean> fourteenPins = Arrays.asList(true, true, true, true, true, true, true, true, true, true, true, true, true, true);
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> game.addScore(fourteenPins),
                ""
        );
        assertEquals(String.format(ErrorMessages.pinExceedsMaxValue, 14, 10), thrown.getMessage());
    }
}