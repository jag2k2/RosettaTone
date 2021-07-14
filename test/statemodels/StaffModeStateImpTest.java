package statemodels;

import notification.StaffModeChangeObserver;
import notification.StaffModeChangeNotifierImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uicomponents.staffmode.StaffMode;

import static org.junit.jupiter.api.Assertions.*;

class StaffModeStateImpTest implements StaffModeChangeObserver {
    private StaffModeStateImp staffModeStateImp;
    private boolean staffModeChanged = false;

    @Override
    public void staffModeChanged() {
        staffModeChanged = true;
    }

    @BeforeEach
    void setup(){
        StaffModeChangeNotifier staffModeChangeNotifier = new StaffModeChangeNotifierImp();
        staffModeStateImp = new StaffModeStateImp(StaffMode.Grand);
        staffModeStateImp.addStaffModeChangeNotifier(staffModeChangeNotifier);

        staffModeChangeNotifier.addObserver(this);
        staffModeChanged = false;
    }

    @Test
    void canCheckEquality() {
        StaffModeStateImp expected = new StaffModeStateImp(StaffMode.Grand);
        assertEquals(expected, staffModeStateImp);

        expected = new StaffModeStateImp(StaffMode.Treble);
        assertNotEquals(expected, staffModeStateImp);
    }

    @Test
    void canDisplayAsString(){
        assertEquals("Grand", staffModeStateImp.toString());
    }

    @Test
    void canChangeStaffMode(){
        StaffModeStateImp expected = new StaffModeStateImp(StaffMode.Treble);
        staffModeStateImp.setMode(StaffMode.Treble);
        assertEquals(expected, staffModeStateImp);
        assertTrue(staffModeChanged);
    }

    @Test
    void wontChangeStaffModeIfSame(){
        StaffModeStateImp expected = new StaffModeStateImp(StaffMode.Grand);
        staffModeStateImp.setMode(StaffMode.Grand);
        assertEquals(expected, staffModeStateImp);
        assertFalse(staffModeChanged);
    }

    @Test
    void canDetectAboveVisible() {
        int lineNumber = 22;
        staffModeStateImp.setMode(StaffMode.Grand);
        assertFalse(staffModeStateImp.isLineAboveVisible(lineNumber));
        staffModeStateImp.setMode(StaffMode.Treble);
        assertFalse(staffModeStateImp.isLineAboveVisible(lineNumber));
        staffModeStateImp.setMode(StaffMode.Bass);
        assertTrue(staffModeStateImp.isLineAboveVisible(lineNumber));

        staffModeStateImp.setMode(StaffMode.Grand);
        lineNumber = 14;
        assertTrue(staffModeStateImp.isLineAboveVisible(lineNumber));
        lineNumber = 28;
        assertTrue(staffModeStateImp.isLineAboveVisible(lineNumber));
    }

    @Test
    void canDetectBelowVisible() {
        int lineNumber = 34;
        staffModeStateImp.setMode(StaffMode.Grand);
        assertFalse(staffModeStateImp.isLineBelowVisible(lineNumber));
        staffModeStateImp.setMode(StaffMode.Treble);
        assertTrue(staffModeStateImp.isLineBelowVisible(lineNumber));
        staffModeStateImp.setMode(StaffMode.Bass);
        assertFalse(staffModeStateImp.isLineBelowVisible(lineNumber));

        staffModeStateImp.setMode(StaffMode.Grand);
        lineNumber = 42;
        assertTrue(staffModeStateImp.isLineBelowVisible(lineNumber));
        lineNumber = 28;
        assertTrue(staffModeStateImp.isLineBelowVisible(lineNumber));
    }

    @Test
    void canDetectIfBetweenStaffs() {
        int lineNumber = 28;
        staffModeStateImp.setMode(StaffMode.Grand);
        assertTrue(staffModeStateImp.isLineAboveVisible(lineNumber));
        assertTrue(staffModeStateImp.isLineBelowVisible(lineNumber));
    }

    @Test
    void canFindClosestVisibleLineHigh() {
        int lineNumber = 16;
        staffModeStateImp.setMode(StaffMode.Grand);
        assertEquals(18, staffModeStateImp.getClosestVisibleLine(lineNumber));
        staffModeStateImp.setMode(StaffMode.Treble);
        assertEquals(18, staffModeStateImp.getClosestVisibleLine(lineNumber));
        staffModeStateImp.setMode(StaffMode.Bass);
        assertEquals(30, staffModeStateImp.getClosestVisibleLine(lineNumber));
    }

    @Test
    void canFindClosestVisibleLineLow(){
        int lineNumber = 40;
        staffModeStateImp.setMode(StaffMode.Grand);
        assertEquals(38, staffModeStateImp.getClosestVisibleLine(lineNumber));
        staffModeStateImp.setMode(StaffMode.Treble);
        assertEquals(26, staffModeStateImp.getClosestVisibleLine(lineNumber));
        staffModeStateImp.setMode(StaffMode.Bass);
        assertEquals(38, staffModeStateImp.getClosestVisibleLine(lineNumber));
    }
}