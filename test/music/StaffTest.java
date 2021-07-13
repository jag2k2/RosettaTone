package music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uicomponents.renderer.records.RenderConstants;

import static org.junit.jupiter.api.Assertions.*;

class StaffTest {
    private Staff trebleStaff;
    private Staff bassStaff;

    @BeforeEach
    void setup(){
        trebleStaff = new Staff(RenderConstants.trebleStaff);
        bassStaff = new Staff(RenderConstants.bassStaff);
    }

    @Test
    void isLineVisible() {
        assertFalse(trebleStaff.isLineVisible(17));
        assertTrue(trebleStaff.isLineVisible(18));
        assertTrue(trebleStaff.isLineVisible(26));
        assertFalse(trebleStaff.isLineVisible(27));

        assertFalse(bassStaff.isLineVisible(29));
        assertTrue(bassStaff.isLineVisible(30));
        assertTrue(bassStaff.isLineVisible(38));
        assertFalse(bassStaff.isLineVisible(39));
    }

    @Test
    void isLineAboveVisible() {
        assertFalse(trebleStaff.isLineAboveVisible(22));
        assertTrue(bassStaff.isLineAboveVisible(22));
    }

    @Test
    void isLineBelowVisible() {
        assertTrue(trebleStaff.isLineBelowVisible(34));
        assertFalse(bassStaff.isLineBelowVisible(34));

    }

    @Test
    void getClosestVisibleLine() {
        assertEquals(18, trebleStaff.getClosestVisibleLine(16));
        assertEquals(18, trebleStaff.getClosestVisibleLine(19));
        assertEquals(26, trebleStaff.getClosestVisibleLine(22));
        assertEquals(26, trebleStaff.getClosestVisibleLine(25));
        assertEquals(26, trebleStaff.getClosestVisibleLine(35));

        assertEquals(30, bassStaff.getClosestVisibleLine(28));
        assertEquals(30, bassStaff.getClosestVisibleLine(32));
        assertEquals(38, bassStaff.getClosestVisibleLine(34));
        assertEquals(38, bassStaff.getClosestVisibleLine(37));
        assertEquals(38, bassStaff.getClosestVisibleLine(42));
    }
}