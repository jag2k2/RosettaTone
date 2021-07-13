package statemodels;

import notification.ClefModeChangeObserver;

public interface StaffModeChangeNotifier {
    void addObserver(ClefModeChangeObserver observer);
    void notifyObservers();
}
