package notification;

import statemodels.ClefModeChangeNotifier;

import java.util.ArrayList;

public class ClefModeChangeNotifierImp implements ClefModeChangeNotifier {
    private final ArrayList<ClefModeChangeObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(ClefModeChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (ClefModeChangeObserver observer : observers) {
            observer.modeChanged();
        }
    }
}
