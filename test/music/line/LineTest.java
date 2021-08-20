package music.line;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.StaffModeStateImp;
import uicomponents.renderer.records.RenderConstants;
import statemodels.StaffMode;

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
    void canDetectAboveVisible() {
        Line line = new Line(22);
        staffMode.update(StaffMode.Grand);
        assertFalse(line.isAboveVisible(staffMode));

        staffMode.update(StaffMode.Treble);
        assertFalse(line.isAboveVisible(staffMode));
        staffMode.update(StaffMode.Bass);
        assertTrue(line.isAboveVisible(staffMode));

        staffMode.update(StaffMode.Grand);
        line = new Line(14);
        assertTrue(line.isAboveVisible(staffMode));
        line = new Line(28);
        assertTrue(line.isAboveVisible(staffMode));
    }

    @Test
    void canDetectBelowVisible() {
        Line line = new Line(34);
        staffMode.update(StaffMode.Grand);
        assertFalse(line.isBelowVisible(staffMode));

        staffMode.update(StaffMode.Treble);
        assertTrue(line.isBelowVisible(staffMode));

        staffMode.update(StaffMode.Bass);
        assertFalse(line.isBelowVisible(staffMode));

        staffMode.update(StaffMode.Grand);
        line = new Line(42);
        assertTrue(line.isBelowVisible(staffMode));
        line = new Line(28);
        assertTrue(line.isBelowVisible(staffMode));
    }

    @Test
    void canDetectIfBetweenStaffs() {
        Line line = new Line(28);
        staffMode.update(StaffMode.Grand);
        assertTrue(line.isAboveVisible(staffMode));
        assertTrue(line.isBelowVisible(staffMode));
    }

    @Test
    void getClosestVisibleLineTreble() {
        staffMode.update(StaffMode.Treble);
        Line topTrebleLine = new Line(RenderConstants.trebleStaff.topVisibleLine);
        Line bottomTrebleLine = new Line(RenderConstants.trebleStaff.bottomVisibleLine);

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
        staffMode.update(StaffMode.Bass);
        Line topBassLine = new Line(RenderConstants.bassStaff.topVisibleLine);
        Line bottomBassLine = new Line(RenderConstants.bassStaff.bottomVisibleLine);

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
        staffMode.update(StaffMode.Grand);
        Line expected = new Line(18);
        assertEquals(expected, line.getClosestVisibleLine(staffMode));
        staffMode.update(StaffMode.Treble);
        assertEquals(expected, line.getClosestVisibleLine(staffMode));
        staffMode.update(StaffMode.Bass);
        expected = new Line(30);
        assertEquals(expected, line.getClosestVisibleLine(staffMode));
    }

    @Test
    void canFindClosestVisibleLineLow(){
        Line line = new Line(40);
        staffMode.update(StaffMode.Grand);
        Line expected = new Line(38);
        assertEquals(expected, line.getClosestVisibleLine(staffMode));
        staffMode.update(StaffMode.Treble);
        expected = new Line(26);
        assertEquals(expected, line.getClosestVisibleLine(staffMode));
        staffMode.update(StaffMode.Bass);
        expected = new Line(38);
        assertEquals(expected, line.getClosestVisibleLine(staffMode));
    }
}