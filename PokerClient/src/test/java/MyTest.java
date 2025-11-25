import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

class MyTest {
    GamePlayController controller;



    @BeforeEach
    void setup() {
        controller = new GamePlayController();
    }

    @Test
    void testPlayerFoldedDefault() {
        // By default, playerFolded should be false
        assertFalse(controller.isPlayerFolded());
    }

    @Test
    void testNewLookDefault() {
        // By default, newLookActive should be false
        assertFalse(controller.isNewLookActive());
    }

    @Test
    void testPlayerHandInitiallyNull() {
        // playerHand should be null before any cards are drawn
        assertNull(controller.getPlayerHand());
    }

    @Test
    void testDealerHandInitiallyNull() {
        // dealerHand should be null before any cards are drawn
        assertNull(controller.getDealerHand());
    }

}



