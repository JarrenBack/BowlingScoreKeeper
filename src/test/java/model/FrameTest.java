package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrameTest {
   @Test
    public void testWasSpareFalse() {
       Frame frame = new Frame();
       frame.setFirstPinCount(1);
       frame.setSecondPinCount(2);
       boolean wasSpare = frame.wasSpare();
       assertEquals(false, wasSpare);
   }

   @Test
   public void testWasSpareTrue() {
      Frame frame = new Frame();
      frame.setFirstPinCount(3);
      frame.setSecondPinCount(7);
      boolean wasSpare = frame.wasSpare();
      assertEquals(true, wasSpare);
   }

   @Test
   public void testWasStrikeFalse() {
      Frame frame = new Frame();
      frame.setFirstPinCount(1);
      frame.setSecondPinCount(2);
      boolean wasStrike = frame.wasStrike();
      assertEquals(false, wasStrike);
   }

   @Test
   public void testWasStrikeTrue() {
      Frame frame = new Frame();
      frame.setFirstPinCount(10);
      frame.setSecondPinCount(0);
      boolean wasStrike = frame.wasStrike();
      assertEquals(true, wasStrike);
   }

   @Test
   public void testWontSetSecondPinIfStrike() {
      Frame frame = new Frame();
      frame.setFirstPinCount(10);
      RuntimeException thrown = assertThrows(
              RuntimeException.class,
              () -> frame.setSecondPinCount(1),
              ""
      );
      assertEquals(String.format(ErrorMessages.firstPlusSecondExceedsMax, 10, 1, 10), thrown.getMessage());
   }

   @Test
   public void testWontSetSecondPinIfMoreThanTotal() {
      Frame frame = new Frame();
      frame.setFirstPinCount(7);
      RuntimeException thrown = assertThrows(
              RuntimeException.class,
              () -> frame.setSecondPinCount(4),
              ""
      );
      assertEquals(String.format(ErrorMessages.firstPlusSecondExceedsMax, 7, 4, 10), thrown.getMessage());
   }

   @Test
   public void testWillSetThirdPinIfStrike() {
      Frame frame = new Frame();
      frame.setLastFrame(true);
      frame.setFirstPinCount(10);
      frame.setSecondPinCount(7);
      boolean wasThirdPinCountSuccess = frame.setThirdPinCount(1);
      assertEquals(true, wasThirdPinCountSuccess);
   }

   @Test
   public void testWillSetThirdPinIfStrikeStrike() {
      Frame frame = new Frame();
      frame.setLastFrame(true);
      frame.setFirstPinCount(10);
      frame.setSecondPinCount(10);
      boolean wasThirdPinCountSuccess = frame.setThirdPinCount(1);
      assertEquals(true, wasThirdPinCountSuccess);
   }

   @Test
   public void testWillSetThirdPinIfXXX() {
      Frame frame = new Frame();
      frame.setLastFrame(true);
      frame.setFirstPinCount(10);
      frame.setSecondPinCount(10);
      boolean wasThirdPinCountSuccess = frame.setThirdPinCount(10);
      assertEquals(true, wasThirdPinCountSuccess);
   }

   @Test
   public void testWillSetThirdPinIfSpare() {
      Frame frame = new Frame();
      frame.setLastFrame(true);
      frame.setFirstPinCount(3);
      frame.setSecondPinCount(7);
      boolean wasThirdPinCountSuccess = frame.setThirdPinCount(1);
      assertEquals(true, wasThirdPinCountSuccess);
   }

   @Test
   public void testWontSetThirdPinIfNotStrikeOrSpare() {
      Frame frame = new Frame();
      frame.setLastFrame(true);
      frame.setFirstPinCount(3);
      frame.setSecondPinCount(6);
      RuntimeException thrown = assertThrows(
              RuntimeException.class,
              () -> frame.setThirdPinCount(1),
              ""
      );
      assertEquals(ErrorMessages.noThirdPinIfNoStrikeOrSpare, thrown.getMessage());
   }

   @Test
   public void testWontSetThirdPinIfMoreThanSpare() {
      Frame frame = new Frame();
      frame.setLastFrame(true);
      frame.setFirstPinCount(10);
      frame.setSecondPinCount(6);
      RuntimeException thrown = assertThrows(
              RuntimeException.class,
              () -> frame.setThirdPinCount(5),
              ""
      );

      assertEquals(String.format(ErrorMessages.noThirdPinValueExceedsMaxGivenStrike, 5, 10, 6), thrown.getMessage());
   }

   @Test
   public void testWontSetThirdPinIfNotLastFrame() {
      Frame frame = new Frame();
      frame.setFirstPinCount(10);
      frame.setSecondPinCount(0);
      RuntimeException thrown = assertThrows(
              RuntimeException.class,
              () -> frame.setThirdPinCount(5),
              ""
      );

      assertEquals(String.format(ErrorMessages.noThirdPinIfNotLastFrame), thrown.getMessage());
   }

   @Test
   public void testWontSetThirdPinIfNoSecondPinPresent() {
      Frame frame = new Frame();
      frame.setLastFrame(true);
      frame.setFirstPinCount(10);
      RuntimeException thrown = assertThrows(
              RuntimeException.class,
              () -> frame.setThirdPinCount(5),
              ""
      );

      assertEquals(String.format(ErrorMessages.noThirdPinIfNoSecondPin), thrown.getMessage());
   }

   @Test
   public void testWontSetThirdPinIfThirdPinExceedsMax() {
      Frame frame = new Frame();
      frame.setLastFrame(true);
      frame.setFirstPinCount(3);
      frame.setSecondPinCount(7);
      RuntimeException thrown = assertThrows(
              RuntimeException.class,
              () -> frame.setThirdPinCount(11),
              ""
      );

      assertEquals(String.format(ErrorMessages.pinExceedsMaxValue, 11, 10), thrown.getMessage());
   }

   @Test
   public void testWontSetSecondPinIfFirstPinNotPresent() {
      Frame frame = new Frame();
      RuntimeException thrown = assertThrows(
              RuntimeException.class,
              () -> frame.setSecondPinCount(7),
              ""
      );

      assertEquals(ErrorMessages.firstPinMustBePresentBeforeSecond, thrown.getMessage());
   }

   @Test
   public void testWontSetSecondPinIfFirstPlusSecondIsMoreThanMax() {
      Frame frame = new Frame();
      frame.setFirstPinCount(3);
      RuntimeException thrown = assertThrows(
              RuntimeException.class,
              () -> frame.setSecondPinCount(8),
              ""
      );

      assertEquals(String.format(ErrorMessages.firstPlusSecondExceedsMax, 3, 8, 10), thrown.getMessage());
   }

   @Test
   public void testWontSetSecondPinIfExceedsMax() {
      Frame frame = new Frame();
      frame.setFirstPinCount(0);
      RuntimeException thrown = assertThrows(
              RuntimeException.class,
              () -> frame.setSecondPinCount(11),
              ""
      );

      assertEquals(String.format(ErrorMessages.pinExceedsMaxValue, 11, 10), thrown.getMessage());
   }

   @Test
   public void testWontSetSecondFrameScoreIfGreaterThanMax() {
      Frame frame = new Frame();
      RuntimeException thrown = assertThrows(
              RuntimeException.class,
              () -> frame.setFrameScore(301),
              ""
      );

      assertEquals(String.format(ErrorMessages.invalidFrameScore, 301), thrown.getMessage());
   }
}