package com.jag2k2.statemodels;

import com.jag2k2.notification.ConfigChangeObserver;

public interface ConfigChangeNotifier {
    void addObserver(ConfigChangeObserver observer);
    void notifyObservers();
}
