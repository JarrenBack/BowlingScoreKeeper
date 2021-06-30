package model;

public class ErrorMessages {
    public static final String noThirdPinIfNotLastFrame = "Cannot set third pin if not on last frame";
    public static final String noThirdPinIfNoSecondPin = "Cannot set third pin if second pin is not present";
    public static final String noThirdPinIfNoStrikeOrSpare= "Cannot set third pin if no strike or spare was achieved";
    public static final String noThirdPinValueExceedsMaxGivenStrike = "Value of third pin(%s) exceeds maximum possible given the max " +
            "pins(%s) and second pin value(%s)";
    public static final String pinExceedsMaxValue = "Pin value(%s) exceeds max value(%s)";
    public static final String firstPinMustBePresentBeforeSecond = "First pin must be present before setting second pin";
    public static final String firstPlusSecondExceedsMax = "First pin count(%s) plus second pin count (%s) exceeds total number of pins possible(%s)";
    public static final String scorecardAlreadyComplete = "Scorecard is already complete and more scores cannot be added";
    public static final String invalidNameLength = "Invalid name length. Must be at least one character and less than 16 characters";
    public static final String invalidFrameScore = "Invalid frame score %s. Must be between 0 and 300";
    public static final String cannotStartGameWithNoNames = "Cannot start game with no player names";
    public static final String numberOfFramesExceedsMax = "The number of frames(%s) has exceeded the max number of frames(%s)";
    public static final String cannotAddScoreIfTheGameIsOver = "The game is over so a score cannot be added";
}
