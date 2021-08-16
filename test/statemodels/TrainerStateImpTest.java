package statemodels;

import notification.ConfigChangeNotifierImp;
import notification.ConfigChangeObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uicomponents.trainer.eventhandler.Enableable;

import static org.junit.jupiter.api.Assertions.*;

class TrainerStateImpTest implements ConfigChangeObserver {
    private TrainerStateImp trainerState;
    private boolean notifiedOfConfigChange;

    @Override
    public void configChanged() {
        notifiedOfConfigChange = true;
    }

    @BeforeEach
    void setup(){
        ConfigChangeNotifier configChangeNotifier = new ConfigChangeNotifierImp();
        configChangeNotifier.addObserver(this);
        trainerState = new TrainerStateImp();
        trainerState.addConfigChangeNotifier(configChangeNotifier);
        notifiedOfConfigChange = false;
    }

    @Test
    void canDisplayAsString(){
        assertEquals("Disabled", trainerState.toString());
        trainerState.enable();
        assertEquals("Enabled", trainerState.toString());
    }

    @Test
    void canCheckForEquality(){
        Enableable expected = new TrainerStateImp();
        assertEquals(expected, trainerState);
        trainerState.enable();
        assertNotEquals(expected, trainerState);
    }

    @Test
    void canCheckEnabled(){
        trainerState.enable();
        assertTrue(trainerState.isEnabled());
        trainerState.disable();
        assertFalse(trainerState.isEnabled());
    }

    @Test
    void doesNotifyWhenStateChanges(){
        trainerState.enable();
        assertTrue(notifiedOfConfigChange);
    }

    @Test
    void wontNotifyWhenStateDoesntChange(){
        trainerState.enable();
        notifiedOfConfigChange = false;
        trainerState.enable();
        assertFalse(notifiedOfConfigChange);
    }
}