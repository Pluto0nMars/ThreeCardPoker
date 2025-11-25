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

//    @Test
//    void testPlayerFoldedDefault() {
//        assertFalse(controller.isPlayerFolded());
//    }
//
//    @Test
//    void testNewLookDefault() {
//        assertFalse(controller.isNewLookActive());
//    }
//
//    @Test
//    void testGetPlayerHandInitiallyNull() {
//        assertNull(controller.getPlayerHand());
//    }



    @Test
    void testGetDealerHandInitiallyNull() {
        assertNull(controller.getDealerHand());
    }

}



