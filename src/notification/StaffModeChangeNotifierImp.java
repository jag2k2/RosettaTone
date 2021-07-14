package notification;

import statemodels.StaffModeChangeNotifier;

import java.util.ArrayList;

public class StaffModeChangeNotifierImp implements StaffModeChangeNotifier {
    private final ArrayList<StaffModeChangeObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(StaffModeChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (StaffModeChangeObserver observer : observers) {
            observer.staffModeChanged();
        }
    }
}
