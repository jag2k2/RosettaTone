package com.jag2k2.notification;

import com.jag2k2.statemodels.ConfigChangeNotifier;

import java.util.ArrayList;

public class ConfigChangeNotifierImp implements ConfigChangeNotifier {
    private final ArrayList<ConfigChangeObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(ConfigChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (ConfigChangeObserver observer : observers) {
            observer.configChanged();
        }
    }
}
