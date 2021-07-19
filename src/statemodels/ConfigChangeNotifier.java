package statemodels;

import notification.ConfigChangeObserver;

public interface ConfigChangeNotifier {
    void addObserver(ConfigChangeObserver observer);
    void notifyObservers();
}
