package uicomponents.clefmode;

import static org.junit.jupiter.api.Assertions.*;

import notification.ModeChangeNotifierImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClefModeSelectorImpTest {
    private ClefModeSelectorImp trebleSelection;
    private ClefModeSelectorImp bassSelection;
    private ClefModeSelectorImp grandSelection;

    @BeforeEach
    void setup(){
        trebleSelection = new ClefModeSelectorImp(ClefMode.Treble, new ModeChangeNotifierImp());
        bassSelection = new ClefModeSelectorImp(ClefMode.Bass, new ModeChangeNotifierImp());
        grandSelection = new ClefModeSelectorImp(ClefMode.Grand, new ModeChangeNotifierImp());
    }

    @Test
    void canDetectTrebleEnabled() {
        assertTrue(trebleSelection.trebleEnabled());
        assertFalse(bassSelection.trebleEnabled());
        assertTrue(grandSelection.trebleEnabled());
    }

    @Test
    void canDetectBassEnabled() {
        assertFalse(trebleSelection.bassEnabled());
        assertTrue(bassSelection.bassEnabled());
        assertTrue(grandSelection.bassEnabled());
    }
}