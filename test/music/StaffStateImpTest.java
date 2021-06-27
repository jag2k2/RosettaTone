package music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.StaffStateImp;
import uicomponents.staffselector.StaffOptions;

import static org.junit.jupiter.api.Assertions.*;

class StaffStateImpTest {
    private StaffStateImp trebleSelection;
    private StaffStateImp bassSelection;
    private StaffStateImp grandSelection;

    @BeforeEach
    void setup(){
        trebleSelection = new StaffStateImp(StaffOptions.Treble);
        bassSelection = new StaffStateImp(StaffOptions.Bass);
        grandSelection = new StaffStateImp(StaffOptions.Grand);
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