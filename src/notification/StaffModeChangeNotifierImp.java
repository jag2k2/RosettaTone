package notification;

import statemodels.StaffModeChangeNotifier;

import java.util.ArrayList;

public class StaffModeChangeNotifierImp implements StaffModeChangeNotifier {
    private final ArrayList<ClefModeChangeObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(ClefModeChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (ClefModeChangeObserver observer : observers) {
            observer.clefModeChanged();
        }
    }
}
