package statemodels;

import notification.StaffChangeNotifier;
import notification.StaffChangeObserver;
import uicomponents.staffselector.StaffOptions;
import java.util.ArrayList;
import java.util.List;

public class StaffStateImp implements StaffState, StaffChangeNotifier {
    private final List<StaffChangeObserver> observers;
    private StaffOptions staffOptions;

    public StaffStateImp(StaffOptions staffOptions){
        this.observers = new ArrayList<>();
        this.staffOptions = staffOptions;
    }

    @Override
    public void setSelection(StaffOptions staffOptions){
        this.staffOptions = staffOptions;
        notifyObservers();
    }

    @Override
    public StaffOptions getSelection(){
        return staffOptions;
    }

    @Override
    public boolean trebleEnabled() {
        return (staffOptions == StaffOptions.Treble || staffOptions == StaffOptions.Grand);
    }

    @Override
    public boolean bassEnabled() {
        return (staffOptions == StaffOptions.Bass || staffOptions == StaffOptions.Grand);
    }

    @Override
    public void addObserver(StaffChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (StaffChangeObserver observer : observers){
            observer.update();
        }
    }
}
