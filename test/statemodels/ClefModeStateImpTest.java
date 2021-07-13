package statemodels;

import imageprocessing.ImageLoaderImp;
import music.Staff;
import notification.ClefModeChangeNotifierImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uicomponents.clefmode.ClefMode;
import uicomponents.renderer.ImageLoader;
import uicomponents.renderer.records.RenderConstants;

import static org.junit.jupiter.api.Assertions.*;

class ClefModeStateImpTest {
    private ClefModeStateImp clefModeState;

    @BeforeEach
    void setup(){
        ImageLoader imageLoader = new ImageLoaderImp();
        Staff trebleStaff = new Staff(imageLoader.getTrebleImage(), RenderConstants.trebleStaff);
        Staff bassStaff = new Staff(imageLoader.getBassImage(), RenderConstants.bassStaff);

        clefModeState = new ClefModeStateImp(ClefMode.Grand, trebleStaff, bassStaff);
    }

    @Test
    void canDetectAboveVisible() {
        int lineNumber = 22;
        clefModeState.setState(ClefMode.Grand);
        assertFalse(clefModeState.isLineAboveVisible(lineNumber));
        clefModeState.setState(ClefMode.Treble);
        assertFalse(clefModeState.isLineAboveVisible(lineNumber));
        clefModeState.setState(ClefMode.Bass);
        assertTrue(clefModeState.isLineAboveVisible(lineNumber));

        clefModeState.setState(ClefMode.Grand);
        lineNumber = 14;
        assertTrue(clefModeState.isLineAboveVisible(lineNumber));
        lineNumber = 28;
        assertTrue(clefModeState.isLineAboveVisible(lineNumber));
    }

    @Test
    void canDetectBelowVisible() {
        int lineNumber = 34;
        clefModeState.setState(ClefMode.Grand);
        assertFalse(clefModeState.isLineBelowVisible(lineNumber));
        clefModeState.setState(ClefMode.Treble);
        assertTrue(clefModeState.isLineBelowVisible(lineNumber));
        clefModeState.setState(ClefMode.Bass);
        assertFalse(clefModeState.isLineBelowVisible(lineNumber));

        clefModeState.setState(ClefMode.Grand);
        lineNumber = 42;
        assertTrue(clefModeState.isLineBelowVisible(lineNumber));
        lineNumber = 28;
        assertTrue(clefModeState.isLineBelowVisible(lineNumber));
    }

    @Test
    void canDetectIfBetweenStaffs() {
        int lineNumber = 28;
        clefModeState.setState(ClefMode.Grand);
        assertTrue(clefModeState.isLineAboveVisible(lineNumber));
        assertTrue(clefModeState.isLineBelowVisible(lineNumber));
    }

    @Test
    void canFindClosestVisibleLineHigh() {
        int lineNumber = 16;
        clefModeState.setState(ClefMode.Grand);
        assertEquals(18, clefModeState.getClosestVisibleLine(lineNumber));
        clefModeState.setState(ClefMode.Treble);
        assertEquals(18, clefModeState.getClosestVisibleLine(lineNumber));
        clefModeState.setState(ClefMode.Bass);
        assertEquals(30, clefModeState.getClosestVisibleLine(lineNumber));
    }

    @Test
    void canFindClosestVisibleLineLow(){
        int lineNumber = 40;
        clefModeState.setState(ClefMode.Grand);
        assertEquals(38, clefModeState.getClosestVisibleLine(lineNumber));
        clefModeState.setState(ClefMode.Treble);
        assertEquals(26, clefModeState.getClosestVisibleLine(lineNumber));
        clefModeState.setState(ClefMode.Bass);
        assertEquals(38, clefModeState.getClosestVisibleLine(lineNumber));
    }
}