package statemodels;

import notification.AlphabetModeChangeNotifierImp;
import notification.AlphabetModeChangeObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uicomponents.alphabetmode.AlphabetMode;

import static org.junit.jupiter.api.Assertions.*;

class AlphabetModeStateImpTest implements AlphabetModeChangeObserver {
    private AlphabetModeStateImp alphabetModeStateImp;

    private boolean alphabetModeChanged = false;

    @Override
    public void alphabetModeChanged() {
        alphabetModeChanged = true;
    }

    @BeforeEach
    void setup(){
        AlphabetModeChangeNotifierImp alphabetModeChangeNotifierImp = new AlphabetModeChangeNotifierImp();
        alphabetModeStateImp = new AlphabetModeStateImp(AlphabetMode.Off);
        alphabetModeStateImp.addAlphabetModeChangeNotifier(alphabetModeChangeNotifierImp);

        alphabetModeChangeNotifierImp.addObserver(this);
        alphabetModeChanged = false;
    }

    @Test
    void canCheckEquality() {
        AlphabetModeStateImp expected = new AlphabetModeStateImp(AlphabetMode.Off);
        assertEquals(expected, alphabetModeStateImp);

        expected = new AlphabetModeStateImp(AlphabetMode.Always);
        assertNotEquals(expected, alphabetModeStateImp);
    }

    @Test
    void canDisplayAsString() {
        assertEquals("Off", alphabetModeStateImp.toString());
    }

    @Test
    void setMode() {
        AlphabetModeStateImp expected = new AlphabetModeStateImp(AlphabetMode.Correct);
        alphabetModeStateImp.setMode(AlphabetMode.Correct);
        assertEquals(expected, alphabetModeStateImp);
        assertTrue(alphabetModeChanged);
    }

    @Test
    void wontSetModeIfSame(){
        AlphabetModeStateImp expected = new AlphabetModeStateImp(AlphabetMode.Off);
        alphabetModeStateImp.setMode(AlphabetMode.Off);
        assertEquals(expected, alphabetModeStateImp);
        assertFalse(alphabetModeChanged);
    }

    @Test
    void canCheckIfEnabled(){
        assertFalse(alphabetModeStateImp.isEnabled());

        alphabetModeStateImp.setMode(AlphabetMode.Always);
        assertTrue(alphabetModeStateImp.isEnabled());

        alphabetModeStateImp.setMode(AlphabetMode.Correct);
        //assertTrue(alphabetModeStateImp.isEnabled());
    }
}