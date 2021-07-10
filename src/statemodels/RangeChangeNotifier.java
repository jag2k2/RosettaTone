package statemodels;

import notification.RangeChangeObserver;

public interface RangeChangeNotifier {
    void addObserver(RangeChangeObserver observer);
    void notifyObservers();
}
