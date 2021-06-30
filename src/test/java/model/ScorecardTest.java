package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ScorecardTest {
    @Test
    public void testCalculateScorecardSimple() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 10; i++) {
            scorecard.addScoreToScorecard(3);
            scorecard.addScoreToScorecard(3);
        }
        int totalScore = scorecard.calculateScore();
        assertEquals(60, totalScore);
    }

    @Test
    public void testCalculateScorecardPartiallyFilledSimple() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 5; i++) {
            Frame f = new Frame();
            f.setFirstPinCount(5);
            f.setSecondPinCount(3);
            scorecard.addFrame(f);
        }

        int totalScore = scorecard.calculateScore();
        assertEquals(40, totalScore);
    }

    @Test
    public void testCalculateScorecardWithSpares() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 2; i++) {
            Frame f = new Frame();
            f.setFirstPinCount(5);
            f.setSecondPinCount(3);
            scorecard.addFrame(f);
        }

        Frame spare = new Frame();
        spare.setFirstPinCount(5);
        spare.setSecondPinCount(5);
        scorecard.addFrame(spare);

        for (int i = 0; i < 2; i++) {
            Frame f = new Frame();
            f.setFirstPinCount(5);
            f.setSecondPinCount(3);
            scorecard.addFrame(f);
        }

        int totalScore = scorecard.calculateScore();
        assertEquals(47, totalScore);
    }

    @Test
    public void testCalculateScorecardWithSpare() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 2; i++) {
            Frame f = new Frame();
            f.setFirstPinCount(5);
            f.setSecondPinCount(3);
            scorecard.addFrame(f);
        }

        Frame spare = new Frame();
        spare.setFirstPinCount(5);
        spare.setSecondPinCount(5);
        scorecard.addFrame(spare);

        int totalScore = scorecard.calculateScore();
        assertEquals(16, totalScore);
    }

    @Test
    public void testCalculateScorecardWithLastIsSpare() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 9; i++) {
            scorecard.addScoreToScorecard(5);
            scorecard.addScoreToScorecard(3);
        }

        scorecard.addScoreToScorecard(5);
        scorecard.addScoreToScorecard(5);
        scorecard.addScoreToScorecard(10);

        int totalScore = scorecard.calculateScore();
        assertEquals(92, totalScore);
    }

    @Test
    public void testCalculateScorecardIsCalledTwiceWithNoChanges() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 2; i++) {
            Frame f = new Frame();
            f.setFirstPinCount(5);
            f.setSecondPinCount(3);
            scorecard.addFrame(f);
        }

        int totalScore = scorecard.calculateScore();
        int totalScore2 = scorecard.calculateScore();
        assertEquals(totalScore, totalScore2);
    }

    @Test
    public void testCalculateScorecardIsCalledTwiceWithChanges() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 2; i++) {
            Frame f = new Frame();
            f.setFirstPinCount(5);
            f.setSecondPinCount(3);
            scorecard.addFrame(f);
        }

        int totalScore = scorecard.calculateScore();

        for (int i = 0; i < 2; i++) {
            Frame f = new Frame();
            f.setFirstPinCount(5);
            f.setSecondPinCount(3);
            scorecard.addFrame(f);
        }

        int totalScore2 = scorecard.calculateScore();
        assertEquals(totalScore + 16, totalScore2);
    }

    @Test
    public void testCalculateScorecardWithSpareAndNextHasOneScore() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 2; i++) {
            Frame f = new Frame();
            f.setFirstPinCount(5);
            f.setSecondPinCount(3);
            scorecard.addFrame(f);
        }

        Frame spare = new Frame();
        spare.setFirstPinCount(5);
        spare.setSecondPinCount(5);
        scorecard.addFrame(spare);

        Frame last = new Frame();
        last.setFirstPinCount(5);
        scorecard.addFrame(last);

        int totalScore = scorecard.calculateScore();
        assertEquals(31, totalScore);
    }

    @Test
    public void testCalculateScorecardTwoSparesInARow() {
        Scorecard scorecard = new Scorecard();
        Frame spare = new Frame();
        spare.setFirstPinCount(6);
        spare.setSecondPinCount(4);
        scorecard.addFrame(spare);

        Frame spare2 = new Frame();
        spare2.setFirstPinCount(8);
        spare2.setSecondPinCount(2);
        scorecard.addFrame(spare2);

        Frame frame = new Frame();
        frame.setFirstPinCount(6);
        scorecard.addFrame(frame);

        int totalScore = scorecard.calculateScore();
        assertEquals(34, totalScore);
    }

    @Test
    public void testCalculateScorecardWithStrikeNotStrike() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 2; i++) {
            Frame f = new Frame();
            f.setFirstPinCount(5);
            f.setSecondPinCount(3);
            scorecard.addFrame(f);
        }

        Frame strike = new Frame();
        strike.setFirstPinCount(10);
        strike.setSecondPinCount(0);
        scorecard.addFrame(strike);

        Frame notStrike = new Frame();
        notStrike.setFirstPinCount(7);
        notStrike.setSecondPinCount(2);
        scorecard.addFrame(notStrike);

        int totalScore = scorecard.calculateScore();
        assertEquals(44, totalScore);
    }

    @Test
    public void testCalculateScorecardWithLastIsStrike() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 2; i++) {
            Frame f = new Frame();
            f.setFirstPinCount(5);
            f.setSecondPinCount(3);
            scorecard.addFrame(f);
        }

        Frame strike = new Frame();
        strike.setFirstPinCount(10);
        strike.setSecondPinCount(0);
        scorecard.addFrame(strike);

        int totalScore = scorecard.calculateScore();
        assertEquals(16, totalScore);
    }

    @Test
    public void testCalculateScorecardWithLastIsXX() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 2; i++) {
            Frame f = new Frame();
            f.setFirstPinCount(5);
            f.setSecondPinCount(3);
            scorecard.addFrame(f);
        }

        Frame strike = new Frame();
        strike.setFirstPinCount(10);
        strike.setSecondPinCount(0);
        scorecard.addFrame(strike);
        scorecard.addFrame(strike);

        int totalScore = scorecard.calculateScore();
        assertEquals(16, totalScore);
    }

    @Test
    public void testCalculateScorecardWithXXNotStrike() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 2; i++) {
            Frame f = new Frame();
            f.setFirstPinCount(5);
            f.setSecondPinCount(3);
            scorecard.addFrame(f);
        }

        Frame strike = new Frame();
        strike.setFirstPinCount(10);
        strike.setSecondPinCount(0);
        scorecard.addFrame(strike);

        Frame strike2 = new Frame();
        strike2.setFirstPinCount(10);
        strike2.setSecondPinCount(0);
        scorecard.addFrame(strike2);

        Frame notStrike = new Frame();
        notStrike.setFirstPinCount(7);
        notStrike.setSecondPinCount(2);
        scorecard.addFrame(notStrike);

        int totalScore = scorecard.calculateScore();
        assertEquals(71, totalScore);
    }

    @Test
    public void testCalculateScorecardWithTurkeyInMiddle() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 2; i++) {
            Frame f = new Frame();
            f.setFirstPinCount(5);
            f.setSecondPinCount(3);
            scorecard.addFrame(f);
        }

        Frame strike = new Frame();
        strike.setFirstPinCount(10);
        strike.setSecondPinCount(0);
        scorecard.addFrame(strike);

        Frame strike2 = new Frame();
        strike2.setFirstPinCount(10);
        strike2.setSecondPinCount(0);
        scorecard.addFrame(strike2);

        Frame strike3 = new Frame();
        strike3.setFirstPinCount(10);
        strike3.setSecondPinCount(0);
        scorecard.addFrame(strike3);

        int totalScore = scorecard.calculateScore();
        assertEquals(46, totalScore);
    }

    @Test
    public void testCalculateScorecardWithAllStrike() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 9; i++) {
            scorecard.addScoreToScorecard(10);
        }
        scorecard.addScoreToScorecard(10);
        scorecard.addScoreToScorecard(10);
        scorecard.addScoreToScorecard(10);

        int totalScore = scorecard.calculateScore();
        assertEquals(300, totalScore);
    }

    @Test
    public void addScoreToScorecardFirstAndSecondPin() {
        Scorecard scorecard = new Scorecard();
        scorecard.addScoreToScorecard(5);
        List<Frame> frame = scorecard.getFrames();
        assertEquals(5, frame.get(0).getFirstPinCount().get());
        scorecard.addScoreToScorecard(4);
        assertEquals(4, frame.get(0).getSecondPinCount().get());
    }

    @Test
    public void addScoreToScorecardTwoGutters() {
        Scorecard scorecard = new Scorecard();
        scorecard.addScoreToScorecard(0);
        List<Frame> frame = scorecard.getFrames();
        assertEquals(0, frame.get(0).getFirstPinCount().get());
        scorecard.addScoreToScorecard(0);
        assertEquals(0, frame.get(0).getSecondPinCount().get());
    }

    @Test
    public void addScoreToScorecardStrike() {
        Scorecard scorecard = new Scorecard();
        scorecard.addScoreToScorecard(10);
        List<Frame> frame = scorecard.getFrames();
        assertEquals(10, frame.get(0).getFirstPinCount().get());
        assertEquals(0, frame.get(0).getSecondPinCount().get());
        scorecard.addScoreToScorecard(4);
        assertEquals(4, frame.get(1).getFirstPinCount().get());
    }

    @Test
    public void addScoreToScorecardAllStrikesLastFrame() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 9; i++) {
            scorecard.addScoreToScorecard(5);
            scorecard.addScoreToScorecard(3);
        }
        scorecard.addScoreToScorecard(10);
        scorecard.addScoreToScorecard(10);
        scorecard.addScoreToScorecard(10);

        List<Frame> frames = scorecard.getFrames();
        assertEquals(10, frames.get(9).getFirstPinCount().get());
        assertEquals(10, frames.get(9).getSecondPinCount().get());
        assertEquals(10, frames.get(9).getThirdPinCount().get());
    }

    @Test
    public void calculateScoreCardOneStrikeInLastFrame() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 9; i++) {
            scorecard.addScoreToScorecard(5);
            scorecard.addScoreToScorecard(3);
        }
        scorecard.addScoreToScorecard(10);

        List<Frame> frames = scorecard.getFrames();
        assertEquals(10, frames.get(9).getFirstPinCount().get());
        assertEquals(72, scorecard.calculateScore());
    }

    @Test
    public void calculateScoreNoRolls() {
        Scorecard scorecard = new Scorecard();
        assertEquals(0, scorecard.calculateScore());
    }

    @Test
    public void addScoreToScorecardSpareLastFrame() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 9; i++) {
            scorecard.addScoreToScorecard(5);
            scorecard.addScoreToScorecard(3);
        }
        scorecard.addScoreToScorecard(4);
        scorecard.addScoreToScorecard(6);
        scorecard.addScoreToScorecard(7);

        List<Frame> frames = scorecard.getFrames();
        assertEquals(4, frames.get(9).getFirstPinCount().get());
        assertEquals(6, frames.get(9).getSecondPinCount().get());
        assertEquals(7, frames.get(9).getThirdPinCount().get());
        assertEquals(89, scorecard.calculateScore());
    }

    @Test
    public void addScoreToScorecardTryToAddInvalidThirdScoreToFrame() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 9; i++) {
            scorecard.addScoreToScorecard(5);
            scorecard.addScoreToScorecard(3);
        }
        scorecard.addScoreToScorecard(7);
        scorecard.addScoreToScorecard(2);

        Assertions.assertThrows(RuntimeException.class, () -> {
            scorecard.addScoreToScorecard(1);
        });

        List<Frame> frames = scorecard.getFrames();
        assertEquals(7, frames.get(9).getFirstPinCount().get());
        assertEquals(2, frames.get(9).getSecondPinCount().get());
        assertEquals(false, frames.get(9).getThirdPinCount().isPresent());
    }


    @Test
    public void isReadyForNextFrameAfterTwoScores() {
        Scorecard scorecard = new Scorecard();

        scorecard.addScoreToScorecard(7);
        scorecard.addScoreToScorecard(2);

        assertEquals(true, scorecard.isReadyForNextFrame());
    }

    @Test
    public void isReadyForNextFrameAfterStrike() {
        Scorecard scorecard = new Scorecard();
        scorecard.addScoreToScorecard(10);
        assertEquals(true, scorecard.isReadyForNextFrame());
    }

    @Test
    public void isReadyForNextFrameAfterThreeStrikesLastFrame() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 9; i++) {
            scorecard.addScoreToScorecard(5);
            scorecard.addScoreToScorecard(3);
        }
        scorecard.addScoreToScorecard(10);
        assertEquals(false, scorecard.isReadyForNextFrame());
        scorecard.addScoreToScorecard(10);
        assertEquals(false, scorecard.isReadyForNextFrame());
        scorecard.addScoreToScorecard(10);
        assertEquals(true, scorecard.isReadyForNextFrame());
        assertEquals(true, scorecard.isScorecardComplete());
    }

    @Test
    public void isReadyForNextFrameAfterSpareLastFrame() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 9; i++) {
            scorecard.addScoreToScorecard(5);
            scorecard.addScoreToScorecard(3);
        }
        scorecard.addScoreToScorecard(5);
        assertEquals(false, scorecard.isReadyForNextFrame());
        scorecard.addScoreToScorecard(5);
        assertEquals(false, scorecard.isReadyForNextFrame());
        scorecard.addScoreToScorecard(10);
        assertEquals(true, scorecard.isReadyForNextFrame());
        assertEquals(true, scorecard.isScorecardComplete());
    }

    @Test
    public void isReadyForNextFrameAfterSpareOnLastFrame() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 9; i++) {
            scorecard.addScoreToScorecard(5);
            scorecard.addScoreToScorecard(3);
        }
        scorecard.addScoreToScorecard(7);
        scorecard.addScoreToScorecard(3);

        assertEquals(false, scorecard.isReadyForNextFrame());
    }

    @Test
    public void isReadyForNextFrameAfterNoSpareOnLastFrame() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 9; i++) {
            scorecard.addScoreToScorecard(5);
            scorecard.addScoreToScorecard(3);
        }
        scorecard.addScoreToScorecard(7);
        scorecard.addScoreToScorecard(2);

        assertEquals(true, scorecard.isReadyForNextFrame());
        assertEquals(true, scorecard.isScorecardComplete());
    }

    @Test
    public void cannotAddScoreIfScorecardIsComplete() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 9; i++) {
            scorecard.addScoreToScorecard(5);
            scorecard.addScoreToScorecard(3);
        }
        scorecard.addScoreToScorecard(7);
        scorecard.addScoreToScorecard(2);

        assertEquals(true, scorecard.isReadyForNextFrame());
        assertEquals(true, scorecard.isScorecardComplete());

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> scorecard.addScoreToScorecard(7),
                ""
        );

        assertEquals(ErrorMessages.scorecardAlreadyComplete, thrown.getMessage());
    }

    @Test
    public void cannotAddScoreAddFrameIfExceedsMaxFrames() {
        Scorecard scorecard = new Scorecard();
        for (int i = 0; i < 9; i++) {
            scorecard.addScoreToScorecard(5);
            scorecard.addScoreToScorecard(3);
        }
        scorecard.addScoreToScorecard(7);
        scorecard.addScoreToScorecard(2);

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> scorecard.addFrame(new Frame()),
                ""
        );

        assertEquals(String.format(ErrorMessages.numberOfFramesExceedsMax, 11, 10), thrown.getMessage());
    }
}