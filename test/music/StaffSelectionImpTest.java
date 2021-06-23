package music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uicomponents.staffselector.StaffOptions;

import static org.junit.jupiter.api.Assertions.*;

class StaffSelectionImpTest {
    private StaffSelectionImp trebleSelection;
    private StaffSelectionImp bassSelection;
    private StaffSelectionImp grandSelection;

    @BeforeEach
    void setup(){
        trebleSelection = new StaffSelectionImp(StaffOptions.Treble);
        bassSelection = new StaffSelectionImp(StaffOptions.Bass);
        grandSelection = new StaffSelectionImp(StaffOptions.Grand);
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