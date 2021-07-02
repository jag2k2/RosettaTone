package uicomponents.staffselector;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModeSelectorImpTest {
    private ModeSelectorImp trebleSelection;
    private ModeSelectorImp bassSelection;
    private ModeSelectorImp grandSelection;

    @BeforeEach
    void setup(){
        trebleSelection = new ModeSelectorImp(StaffMode.Treble);
        bassSelection = new ModeSelectorImp(StaffMode.Bass);
        grandSelection = new ModeSelectorImp(StaffMode.Grand);
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