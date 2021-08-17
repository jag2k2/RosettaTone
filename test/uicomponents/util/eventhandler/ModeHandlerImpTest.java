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
import uicomponents.staffmode.StaffMode;
import uicomponents.staffmode.selectorfactory.JComboStaffSelectorFactory;

import static org.junit.jupiter.api.Assertions.*;

class ModeHandlerImpTest {
    private CanSetMode<StaffMode> staffMode;
    private SelectorFactory<StaffMode> staffSelectorFactory;
    private ModeHandler<StaffMode> staffModeHandler;

    private CanSetMode<NoteNameMode> noteNameMode;
    private SelectorFactory<NoteNameMode> noteNameSelectorFactory;
    private ModeHandler<NoteNameMode> noteNameModeHandler;

    @BeforeEach
    void setup(){
        staffMode = new StaffModeStateImp();
        staffSelectorFactory = new JComboStaffSelectorFactory();
        staffModeHandler = new ModeHandlerImp<>(staffMode);

        noteNameMode = new NoteNameModeStateImp();
        noteNameSelectorFactory = new JComboNoteNameSelectorFactory();
        noteNameModeHandler = new ModeHandlerImp<>(noteNameMode);
    }

    @Test
    void canSelectStaffMode(){
        StaffMode oldMode = StaffMode.Treble;
        StaffMode newMode = StaffMode.Grand;
        JSelector<StaffMode> selector = staffModeHandler.createModeSelector(staffSelectorFactory, oldMode);
        StaffModeState expected = new StaffModeStateImp(newMode);
        assertNotEquals(expected, staffMode);
        selector.setSelectedItem(newMode);
        assertEquals(expected, staffMode);
    }

    @Test
    void canSelectNoteNameMode(){
        NoteNameMode oldMode = NoteNameMode.Always;
        NoteNameMode newMode = NoteNameMode.Correct;
        JSelector<NoteNameMode> selector = noteNameModeHandler.createModeSelector(noteNameSelectorFactory, oldMode);
        NoteNameModeState expected = new NoteNameModeStateImp(newMode);
        assertNotEquals(expected, noteNameMode);
        selector.setSelectedItem(newMode);
        assertEquals(expected, noteNameMode);
    }
}