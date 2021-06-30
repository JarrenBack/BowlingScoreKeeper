package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    public void setName() {
        Scorecard scorecard = new Scorecard();
        Player player = new Player("Ned");
        assertEquals("Ned", player.getName());
    }

    @Test
    public void setNameInvalid() {
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> new Player("Ned Ryerson Junior and Phil Connors"),
                ""
        );

        assertEquals(ErrorMessages.invalidNameLength, thrown.getMessage());
    }
}