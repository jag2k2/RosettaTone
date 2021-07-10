package statemodels;

import notification.ClefModeChangeObserver;

public interface ClefModeChangeNotifier {
    void addObserver(ClefModeChangeObserver observer);
    void notifyObservers();
}
