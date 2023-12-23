package com.jag2k2.statemodels.limitstate;

import com.jag2k2.notification.LimitChangeObserver;

public interface LimitChangeNotifier {
    void addObserver(LimitChangeObserver observer);
    void notifyObservers();
}
