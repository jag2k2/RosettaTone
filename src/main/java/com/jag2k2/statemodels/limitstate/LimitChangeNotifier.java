package statemodels.limitstate;

import notification.LimitChangeObserver;

public interface LimitChangeNotifier {
    void addObserver(LimitChangeObserver observer);
    void notifyObservers();
}
