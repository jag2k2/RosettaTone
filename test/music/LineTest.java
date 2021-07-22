package music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.StaffModeStateImp;
import uicomponents.renderer.records.RenderConstants;
import uicomponents.staffmode.StaffMode;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {
    private StaffModeStateImp staffMode;

    @BeforeEach
    void setUp(){
        staffMode = new StaffModeStateImp(StaffMode.Grand);
    }

    @Test
    void canDisplayAsString(){
        Line line = new Line(22);
        assertEquals("22", line.toString());
    }

    @Test
    void canCheckEquality(){
        Line line = new Line(22);
        Line expected = new Line(22);
        assertEquals(expected, line);
    }

    @Test
    void isLineAboveVisible() {
        staffMode.setMode(StaffMode.Grand);
        Line line = new Line(22);
        assertFalse(line.isAboveVisible(staffMode));

        staffMode.setMode(StaffMode.Treble);
        assertFalse(line.isAboveVisible(staffMode));

        staffMode.setMode(StaffMode.Bass);
        assertTrue(line.isAboveVisible(staffMode));
    }

    @Test
    void isLineBelowVisible() {
        staffMode.setMode(StaffMode.Grand);
        Line line = new Line(34);
        assertFalse(line.isBelowVisible(staffMode));

        staffMode.setMode(StaffMode.Treble);
        assertTrue(line.isBelowVisible(staffMode));

        staffMode.setMode(StaffMode.Bass);
        assertFalse(line.isBelowVisible(staffMode));
    }

    @Test
    void canDetectAboveVisible() {
        Line line = new Line(22);
        staffMode.setMode(StaffMode.Grand);
        assertFalse(line.isAboveVisible(staffMode));

        staffMode.setMode(StaffMode.Treble);
        assertFalse(line.isAboveVisible(staffMode));
        staffMode.setMode(StaffMode.Bass);
        assertTrue(line.isAboveVisible(staffMode));

        staffMode.setMode(StaffMode.Grand);
        line = new Line(14);
        assertTrue(line.isAboveVisible(staffMode));
        line = new Line(28);
        assertTrue(line.isAboveVisible(staffMode));
    }

    @Test
    void canDetectBelowVisible() {
        Line line = new Line(34);
        staffMode.setMode(StaffMode.Grand);
        assertFalse(line.isBelowVisible(staffMode));

        staffMode.setMode(StaffMode.Treble);
        assertTrue(line.isBelowVisible(staffMode));

        staffMode.setMode(StaffMode.Bass);
        assertFalse(line.isBelowVisible(staffMode));

        staffMode.setMode(StaffMode.Grand);
        line = new Line(42);
        assertTrue(line.isBelowVisible(staffMode));
        line = new Line(28);
        assertTrue(line.isBelowVisible(staffMode));
    }

    @Test
    void canDetectIfBetweenStaffs() {
        Line line = new Line(28);
        staffMode.setMode(StaffMode.Grand);
        assertTrue(line.isAboveVisible(staffMode));
        assertTrue(line.isBelowVisible(staffMode));
    }

    @Test
    void getClosestVisibleLineTreble() {
        staffMode.setMode(StaffMode.Treble);
        int topTrebleLine = RenderConstants.trebleStaff.topVisibleLine;
        int bottomTrebleLine = RenderConstants.trebleStaff.bottomVisibleLine;

        Line line = new Line(16);
        assertEquals(topTrebleLine, line.getClosestVisibleLine(staffMode));

        line = new Line(19);
        assertEquals(topTrebleLine, line.getClosestVisibleLine(staffMode));

        line = new Line(22);
        assertEquals(bottomTrebleLine, line.getClosestVisibleLine(staffMode));

        line = new Line(25);
        assertEquals(bottomTrebleLine, line.getClosestVisibleLine(staffMode));

        line = new Line(35);
        assertEquals(bottomTrebleLine, line.getClosestVisibleLine(staffMode));
    }

    @Test
    void getClosestVisibleLineBass() {
        staffMode.setMode(StaffMode.Bass);
        int topBassLine = RenderConstants.bassStaff.topVisibleLine;
        int bottomBassLine = RenderConstants.bassStaff.bottomVisibleLine;

        Line line = new Line(28);
        assertEquals(topBassLine, line.getClosestVisibleLine(staffMode));

        line = new Line(32);
        assertEquals(topBassLine, line.getClosestVisibleLine(staffMode));

        line = new Line(34);
        assertEquals(bottomBassLine, line.getClosestVisibleLine(staffMode));

        line = new Line(38);
        assertEquals(bottomBassLine, line.getClosestVisibleLine(staffMode));

        line = new Line(42);
        assertEquals(bottomBassLine, line.getClosestVisibleLine(staffMode));
    }

    @Test
    void canFindClosestVisibleLineHigh() {
        Line line = new Line(16);
        staffMode.setMode(StaffMode.Grand);
        assertEquals(18, line.getClosestVisibleLine(staffMode));
        staffMode.setMode(StaffMode.Treble);
        assertEquals(18, line.getClosestVisibleLine(staffMode));
        staffMode.setMode(StaffMode.Bass);
        assertEquals(30, line.getClosestVisibleLine(staffMode));
    }

    @Test
    void canFindClosestVisibleLineLow(){
        Line line = new Line(40);
        staffMode.setMode(StaffMode.Grand);
        assertEquals(38, line.getClosestVisibleLine(staffMode));
        staffMode.setMode(StaffMode.Treble);
        assertEquals(26, line.getClosestVisibleLine(staffMode));
        staffMode.setMode(StaffMode.Bass);
        assertEquals(38, line.getClosestVisibleLine(staffMode));
    }
}