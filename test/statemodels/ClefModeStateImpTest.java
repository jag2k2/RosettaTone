package statemodels;

import notification.ClefModeChangeNotifierImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uicomponents.clefmode.ClefMode;

import static org.junit.jupiter.api.Assertions.*;

class ClefModeStateImpTest {
    private ClefModeStateImp clefModeState;

    @BeforeEach
    void setup(){
        clefModeState = new ClefModeStateImp(ClefMode.Grand, new ClefModeChangeNotifierImp());
    }


    @Test
    void canDetectTrebleEnabled() {
        clefModeState.setState(ClefMode.Treble);
        assertTrue(clefModeState.trebleEnabled());
        assertFalse(clefModeState.bassEnabled());
    }

    @Test
    void canDetectBassEnabled() {
        clefModeState.setState(ClefMode.Bass);
        assertFalse(clefModeState.trebleEnabled());
        assertTrue(clefModeState.bassEnabled());
    }

    @Test
    void canDetectGrandEnabled() {
        clefModeState.setState(ClefMode.Grand);
        assertTrue(clefModeState.trebleEnabled());
        assertTrue(clefModeState.bassEnabled());
    }
}