package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Encapsulates of scorecard in bowling and has the following attributes:
 * List<@see org.example.BowlingScoreKeeper.Frame>, totalScore, and indexOfCurrentFrame
 *
 * Example Usage:
 * Scorecard scorecard = new Scorecard();
 * scorecard.addScoreToScorecard(5);
 */
public class Scorecard {
    private static final int maxNumberOfFrames = 10;
    private static final int maxNumberOfPins = 10;
    private int totalScore;
    private List<Frame> frames = new ArrayList<>();
    private int indexOfCurrentFrame;
    private final static Logger logger = Logger.getLogger(Scorecard.class.getName());

    /**
     * Sets indexOfCurrentFrame to 0
     */
    public Scorecard() {
        this.indexOfCurrentFrame = 0;
        Frame frame = new Frame();
        frames.add(frame);
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Determines if the scorecard should go onto the next frame
     * @return boolean representing if the current frame has been completed
     */
    public boolean isReadyForNextFrame() {
        if (this.isScorecardComplete() || this.indexOfCurrentFrame > frames.size()) {
            return true;
        } else if (frames.get(this.indexOfCurrentFrame).getSecondPinCount().isPresent()) {
            Frame frame = frames.get(this.indexOfCurrentFrame);
            if (frame.getLastFrame() && (frame.wasSpare() || frame.wasStrike())) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the scorecard has all frames filled in
     * @return boolean representing if the scorecard is complete
     */
    public boolean isScorecardComplete() {
        if (this.frames.size() == maxNumberOfFrames) {
            Frame lastFrame = this.frames.get(maxNumberOfFrames - 1);
            return lastFrame.getSecondPinCount().isPresent() && (lastFrame.getThirdPinCount().isPresent()
                    || (!lastFrame.wasStrike() && !lastFrame.wasSpare()));
        }
        return false;
    }

    /**
     * Adds a frame to the scorecard. If the frame is the last frame, the frame will be marked as such.
     * When the frame is added the indexOfCurrentFrame is also incremented
     * @param frame the frame to be added to the scorecard.
     * @throws RuntimeException will throw an exception if the number of frames exceeds the max
     */
    protected void addFrame (Frame frame) throws RuntimeException {
        if (this.frames.size() >= maxNumberOfFrames) {
            throw new RuntimeException(String.format(ErrorMessages.numberOfFramesExceedsMax, this.frames.size() + 1,
                    maxNumberOfFrames));
        }
        if (this.frames.size() + 1 == maxNumberOfFrames) {
            frame.setLastFrame(true);
        }
        this.frames.add(frame);
        indexOfCurrentFrame++;
        logger.info("Adding a new frame");
    }

    /**
     * Adds a score to the scorecard. If the current frames are all filled in, it will add a new frame. If a strike
     * is passed in for the score, it will automatically set the second pin to 0 unless it's the last frame. It will
     * also call calculateScore() so that the frames and total score are always up to date
     * @param score the score to be added
     * @throws RuntimeException will throw an exception if the scorecard has already been completely filled in
     */
    public void addScoreToScorecard (int score) throws RuntimeException {
        if (isScorecardComplete()) {
            throw new RuntimeException(ErrorMessages.scorecardAlreadyComplete);
        }
        if (this.isReadyForNextFrame()) {
            this.addFrame(new Frame());
        }
        Frame currentFrame = frames.get(this.indexOfCurrentFrame);
        if (!currentFrame.getFirstPinCount().isPresent()) {
            currentFrame.setFirstPinCount(score);
            if (score == maxNumberOfPins && !currentFrame.getLastFrame()) {
                currentFrame.setSecondPinCount(0);
            }
        } else if (!currentFrame.getSecondPinCount().isPresent()) {
            currentFrame.setSecondPinCount(score);
        } else {
            currentFrame.setThirdPinCount(score);
        }
        this.calculateScore();
        logger.info("Added new score of " + score);
    }

    /**
     * Calculates the total score for the scorecard, sets the totalScore on the model, populates the score of each frame.
     * @return returns the total score of the scorecard
     */
    public int calculateScore() {
        int totalScore = 0;
        // creating a copy of the score to make the calculation logic simpler
        List<Optional<Integer>> scores = this.getCopyOfScores();

        for (int i = 0; i < frames.size(); i++) {
            // I want to make sure this frame has both values populated. Otherwise, no need to calculate the score.
            if (scores.get(i * 2).isPresent() && scores.get(i * 2 + 1).isPresent()) {
                if (frames.get(i).wasSpare()) {
                    // In the case of spare, need first pin of next frame to calculate
                    if (scores.get(i * 2 + 2).isPresent()) {
                        totalScore += maxNumberOfPins + scores.get(i * 2 + 2).get();
                    }
                } else if (frames.get(i).wasStrike()) {
                    // In the case of strike, need second pin of next frame to calculate the score.
                    if (scores.get(i * 2 + 2).isPresent()) {
                        if (frames.get(i).getLastFrame()) {
                            // If it's the last frame, we need to add the next two scores to the strike
                            totalScore += maxNumberOfPins + scores.get(i * 2 + 1).get() + scores.get(i * 2 + 2).get();
                        } else if (scores.get(i * 2 + 2).get() == maxNumberOfPins && scores.get(i * 2 + 4).isPresent()) {
                            // If the next frame is a strike, we need to add the first roll of 2 frames over to the two strikes
                            totalScore += 2 * maxNumberOfPins + scores.get(i * 2 + 4).get();
                        } else if (scores.get(i * 2 + 2).get() != maxNumberOfPins && scores.get(i * 2 + 3).isPresent()) {
                            // If the next frame is not a strike, we need to add the first and second roll of the next frame
                            totalScore += maxNumberOfPins + scores.get(i * 2 + 2).get() + scores.get(i * 2 + 3).get();
                        }
                    }
                } else {
                    // in the case where it's neither a strike or spare, we simply add the two scores together
                    totalScore += scores.get(i * 2).get() + scores.get(i * 2 + 1).get();
                }
            }
            logger.info("Setting frame " + i + " to score of " + totalScore);
            frames.get(i).setFrameScore(totalScore);
        }
        this.totalScore = totalScore;
        return totalScore;
    }

    /**
     * This is a utility method to return the scores of a scorecard as a list of integers to make the logic of
     * calculateScore() simpler
     * @return a list of score from the scorecard
     */
    private List<Optional<Integer>> getCopyOfScores() {
        // creating a copy of the score to make the calculation logic simpler
        ArrayList<Optional<Integer>> scores = new ArrayList<>();
        for (int i = 0; i < maxNumberOfFrames; i++) {
            if (i < frames.size()) {
                scores.add(frames.get(i).getFirstPinCount());
                scores.add(frames.get(i).getSecondPinCount());
                if (frames.get(i).getLastFrame()) {
                    scores.add(frames.get(i).getThirdPinCount());
                }
            } else {
                // if the frame is not present, I want to add the rest of the scores so I do not need to worry about
                // out of bounds exceptions
                Frame f = new Frame();
                if (i + 1 == maxNumberOfFrames) {
                    f.setLastFrame(true);
                    scores.add(f.getThirdPinCount());
                }
                scores.add(f.getFirstPinCount());
                scores.add(f.getSecondPinCount());
            }
        }
        return scores;
    }
}
