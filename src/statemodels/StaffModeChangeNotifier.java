package statemodels;

import notification.StaffModeChangeObserver;

public interface StaffModeChangeNotifier {
    void addObserver(StaffModeChangeObserver observer);
    void notifyObservers();
}
