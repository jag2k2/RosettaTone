package uicomponents.staffmode.modehandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.StaffModeStateImp;
import uicomponents.StaffModeState;
import uicomponents.staffmode.SelectorFactory;
import uicomponents.staffmode.StaffMode;
import uicomponents.staffmode.StaffModeHandler;
import uicomponents.staffmode.selectorfactory.JComboSelectorFactory;

import static org.junit.jupiter.api.Assertions.*;

class StaffModeHandlerImpTest {
    private StaffModeChangeObserver staffMode;
    private SelectorFactory selectorFactory;
    private StaffModeHandler staffModeHandler;

    @BeforeEach
    void setup(){
        staffMode = new StaffModeStateImp();
        selectorFactory = new JComboSelectorFactory();
        staffModeHandler = new StaffModeHandlerImp(staffMode);
    }

    @Test
    void canSelectMode(){
        StaffMode oldMode = StaffMode.Treble;
        StaffMode newMode = StaffMode.Grand;
        JSelector selector = staffModeHandler.createModeSelector(selectorFactory, oldMode);
        StaffModeState expected = new StaffModeStateImp(newMode);
        assertNotEquals(expected, staffMode);
        selector.setSelectedItem(newMode);
        assertEquals(expected, staffMode);
    }
}