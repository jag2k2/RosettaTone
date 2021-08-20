package statemodels;

import uicomponents.StaffModeState;
import utility.Maybe;

public class StaffModeStateImp implements StaffModeState {
    private Maybe<ConfigChangeNotifier> staffModeChangeNotifier = new Maybe<>();
    private StaffMode staffMode;

    public StaffModeStateImp(StaffMode staffMode){
        this.staffMode = staffMode;
    }

    public void addConfigChangeNotifier(ConfigChangeNotifier configChangeNotifier){
        this.staffModeChangeNotifier = new Maybe<>(configChangeNotifier);
    }

    @Override
    public boolean isGrand() {
        return staffMode == StaffMode.Grand;
    }

    @Override
    public boolean isTrebleOnly() {
        return staffMode == StaffMode.Treble;
    }

    @Override
    public boolean isBassOnly() {
        return staffMode == StaffMode.Bass;
    }

    @Override
    public void update(StaffMode newValue) {
        if (!this.staffMode.equals(newValue)){
            this.staffMode = newValue;
            for(ConfigChangeNotifier notifier : staffModeChangeNotifier)
                notifier.notifyObservers();
        }
    }

    @Override
    public StaffMode getActive() {
        return staffMode;
    }

    @Override
    public StaffMode[] getOptions() {
        return StaffMode.values();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StaffModeStateImp){
            StaffModeStateImp toCompare = (StaffModeStateImp) obj;
            return staffMode.equals(toCompare.staffMode);
        }
        return false;
    }

    @Override
    public String toString() {
        return staffMode.toString();
    }
}
