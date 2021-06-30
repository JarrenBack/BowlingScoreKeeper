package model;

import java.util.Optional;
/**
 * Encapsulates a frame in bowling and has the following attributes:
 * firstPinCount, secondPinCount, thirdPinCount, frameScore, and isLastFrame
 *
 * Example usage:
 *  Frame newFrame = new Frame();
 *  newFrame.setFirstPinCount(5);
 *  newFrame.setSecondPinCount(3);
 */
public class Frame {
    private Boolean isLastFrame;
    private Optional<Integer> firstPinCount;
    private Optional<Integer> secondPinCount;
    private Optional<Integer> thirdPinCount;
    private Optional<Integer> frameScore;
    private static final int maxPins = 10;

    /**
     * Sets all three pin counts to empty and last frame to false
     */
    public Frame() {
        this.firstPinCount = Optional.empty();
        this.secondPinCount = Optional.empty();
        this.thirdPinCount = Optional.empty();
        this.frameScore = Optional.empty();
        this.isLastFrame = false;
    }

    public Optional<Integer> getFirstPinCount() {
        return firstPinCount;
    }

    public Optional<Integer> getSecondPinCount() {
        return secondPinCount;
    }

    public Optional<Integer> getThirdPinCount() {
        return thirdPinCount;
    }

    public Optional<Integer> getFrameScore() {
        return frameScore;
    }

    public Boolean getLastFrame() {
        return isLastFrame;
    }

    public void setLastFrame(Boolean lastFrame) {
        isLastFrame = lastFrame;
    }

    /**
     * Sets the number of pins that were knocked down as part of the first throw of the frame.
     * @param firstPinCount the number of pins knocked down
     * @throws RuntimeException if pin count exceeds the maximum
     */
    public boolean setFirstPinCount(int firstPinCount) throws RuntimeException {
        if (firstPinCount <= maxPins) {
            this.firstPinCount = Optional.of(firstPinCount);
            return true;
        }
        throw new RuntimeException(String.format(ErrorMessages.pinExceedsMaxValue, firstPinCount, maxPins));
    }

    /**
     * Sets the number of pins that were knocked down as part of the second throw of the frame.
     * @param secondPinCount the number of pins knocked down
     * @throws RuntimeException if the pin count exceeds the maximum, the first pin hasn't been set, or the addition of
     * the first and second pin counts are more than the maximum
     */
    public boolean setSecondPinCount(int secondPinCount) throws RuntimeException {
        if (!firstPinCount.isPresent()) {
            throw new RuntimeException(ErrorMessages.firstPinMustBePresentBeforeSecond);
        }
        if (secondPinCount > maxPins) {
            throw new RuntimeException(String.format(ErrorMessages.pinExceedsMaxValue, secondPinCount, maxPins));
        }
        if (secondPinCount > maxPins - firstPinCount.get() && !isLastFrame) {
            throw new RuntimeException(String.format(ErrorMessages.firstPlusSecondExceedsMax, firstPinCount.get(), secondPinCount, maxPins));
        }
        this.secondPinCount = Optional.of(secondPinCount);
        return true;
    }
    /**
     * Sets the number of pins that were knocked down as part of the third throw of the frame.
     * @param thirdPinCount the number of pins knocked down
     * @throws RuntimeException if the pin count exceeds the maximum, the first or second pins haven't been set, the
     * addition of the second and third pin exceeds the max if first throw was a strike counts, or it's not the last
     * frame, or the second pin has not been set
     */
    public boolean setThirdPinCount(int thirdPinCount) throws RuntimeException {
        if (!isLastFrame) {
            throw new RuntimeException(ErrorMessages.noThirdPinIfNotLastFrame);
        }
        if (!secondPinCount.isPresent()) {
            throw new RuntimeException(ErrorMessages.noThirdPinIfNoSecondPin);
        }
        if (thirdPinCount > maxPins) {
            throw new RuntimeException(String.format(ErrorMessages.pinExceedsMaxValue, thirdPinCount, maxPins));
        }
        int valOfSecondPinCount = secondPinCount.get();
        if (!wasSpare() && !wasStrike()) {
            throw new RuntimeException(ErrorMessages.noThirdPinIfNoStrikeOrSpare);
        } else if (wasStrike() && valOfSecondPinCount != maxPins && valOfSecondPinCount + thirdPinCount > maxPins) {
            throw new RuntimeException(String.format(ErrorMessages.noThirdPinValueExceedsMaxGivenStrike, thirdPinCount,
                    maxPins, valOfSecondPinCount));
        }
        this.thirdPinCount = Optional.of(thirdPinCount);
        return true;
    }

    /**
     * Sets the score of the frame
     * @param frameScore the score of the frame
     * @throws RuntimeException if the frame score is not within [0,300]
     */
    public void setFrameScore(int frameScore) throws RuntimeException  {
        if (frameScore >= 0 && frameScore <= 300) {
            this.frameScore = Optional.of(frameScore);
        } else {
            throw new RuntimeException(String.format(ErrorMessages.invalidFrameScore, frameScore));
        }
    }

    /**
     * Determines if the frame contains a strike. Note that in the case of the third frame it will only return true
     * if the first throw was a strike
     * @return Boolean representing if the frame has a strike
     */
    public Boolean wasStrike() {
        if (firstPinCount.isPresent() && firstPinCount.get() == maxPins)
            return true;
        else return false;
    }

    /**
     * Determines if the frame contains a spare. Note that in the case of the third frame it will only return true
     * if the first and second throw resulted in a spare
     * @return Boolean representing if the frame has a spare
     */
    public Boolean wasSpare() {
        if (firstPinCount.isPresent() && secondPinCount.isPresent() && firstPinCount.get() != maxPins
                && (firstPinCount.get() + secondPinCount.get()) == maxPins) {
            return true;
        } else return false;
    }
}
