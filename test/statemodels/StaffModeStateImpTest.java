package statemodels;

import notification.ConfigChangeObserver;
import notification.ConfigChangeNotifierImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaffModeStateImpTest implements ConfigChangeObserver {
    private StaffModeStateImp staffModeStateImp;
    private boolean staffModeChanged = false;

    @Override
    public void configChanged() {
        staffModeChanged = true;
    }

    @BeforeEach
    void setup(){
        ConfigChangeNotifier configChangeNotifier = new ConfigChangeNotifierImp();
        staffModeStateImp = new StaffModeStateImp(StaffMode.Grand);
        staffModeStateImp.addConfigChangeNotifier(configChangeNotifier);

        configChangeNotifier.addObserver(this);
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
        staffModeStateImp.update(StaffMode.Treble);
        assertEquals(expected, staffModeStateImp);
        assertTrue(staffModeChanged);
    }

    @Test
    void wontChangeStaffModeIfSame(){
        StaffModeStateImp expected = new StaffModeStateImp(StaffMode.Grand);
        staffModeStateImp.update(StaffMode.Grand);
        assertEquals(expected, staffModeStateImp);
        assertFalse(staffModeChanged);
    }
}