package uicomponents.util.eventhandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.NoteNameModeStateImp;
import statemodels.StaffModeStateImp;
import uicomponents.NoteNameModeState;
import uicomponents.StaffModeState;
import uicomponents.notenamemode.NoteNameMode;
import uicomponents.notenamemode.selectorFactory.JComboNoteNameSelectorFactory;
import uicomponents.util.*;
import statemodels.StaffMode;
import uicomponents.staffmode.selectorfactory.JComboStaffSelectorFactory;

import static org.junit.jupiter.api.Assertions.*;

class ModeHandlerImpTest {
    private SelectableState<StaffMode> staffMode;
    private SelectorFactory<StaffMode> staffSelectorFactory;
    private ModeHandler<StaffMode> staffModeHandler;

    private SelectableState<NoteNameMode> noteNameMode;
    private SelectorFactory<NoteNameMode> noteNameSelectorFactory;
    private ModeHandler<NoteNameMode> noteNameModeHandler;

    @BeforeEach
    void setup(){
        staffMode = new StaffModeStateImp(StaffMode.Treble);
        staffSelectorFactory = new JComboStaffSelectorFactory();
        staffModeHandler = new ModeHandlerImp<>(staffMode);

        noteNameMode = new NoteNameModeStateImp(NoteNameMode.Always);
        noteNameSelectorFactory = new JComboNoteNameSelectorFactory();
        noteNameModeHandler = new ModeHandlerImp<>(noteNameMode);
    }

    @Test
    void canSelectStaffMode(){
        StaffMode newMode = StaffMode.Grand;
        JSelector<StaffMode> selector = staffModeHandler.createModeSelector(staffSelectorFactory);
        StaffModeState expected = new StaffModeStateImp(newMode);
        assertNotEquals(expected, staffMode);
        selector.setSelectedItem(newMode);
        assertEquals(expected, staffMode);
    }

    @Test
    void canSelectNoteNameMode(){
        NoteNameMode newMode = NoteNameMode.Correct;
        JSelector<NoteNameMode> selector = noteNameModeHandler.createModeSelector(noteNameSelectorFactory);
        NoteNameModeState expected = new NoteNameModeStateImp(newMode);
        assertNotEquals(expected, noteNameMode);
        selector.setSelectedItem(newMode);
        assertEquals(expected, noteNameMode);
    }
}