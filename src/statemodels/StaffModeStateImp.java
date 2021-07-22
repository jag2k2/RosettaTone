package statemodels;

import uicomponents.renderer.grandstaff.StaffModeEvaluator;
import uicomponents.staffmode.StaffMode;
import uicomponents.staffmode.StaffModeModifier;
import utility.Maybe;

import javax.swing.*;


public class StaffModeStateImp implements StaffModeModifier, StaffModeEvaluator {
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
    public void setUISelected(JComboBox<StaffMode> comboBox) {
        comboBox.setSelectedItem(staffMode);
    }

    @Override
    public void setMode(StaffMode staffMode) {
        if (!this.staffMode.equals(staffMode)){
            this.staffMode = staffMode;
            for(ConfigChangeNotifier notifier : staffModeChangeNotifier)
                notifier.notifyObservers();
        }
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
