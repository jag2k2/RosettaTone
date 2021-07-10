package statemodels;

import notification.ClefModeChangeNotifier;
import uicomponents.clefmode.ClefMode;

public class ClefModeStateImp implements ClefModeState{
    private final ClefModeChangeNotifier clefModeChangeNotifier;
    private ClefMode clefMode;

    public ClefModeStateImp(ClefMode clefMode, ClefModeChangeNotifier clefModeChangeNotifier){
        this.clefMode = clefMode;
        this.clefModeChangeNotifier = clefModeChangeNotifier;
    }

    @Override
    public ClefMode getState() {
        return clefMode;
    }

    @Override
    public void setState(ClefMode clefMode) {
        if (!this.clefMode.equals(clefMode)){
            this.clefMode = clefMode;
            clefModeChangeNotifier.notifyObservers();
        }
    }

    @Override
    public boolean trebleEnabled() {
        return (clefMode == ClefMode.Treble || clefMode == ClefMode.Grand);
    }

    @Override
    public boolean bassEnabled() {
        return (clefMode == ClefMode.Bass || clefMode == ClefMode.Grand);
    }

}
