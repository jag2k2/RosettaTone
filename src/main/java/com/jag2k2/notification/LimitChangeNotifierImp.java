package com.jag2k2.notification;

import com.jag2k2.statemodels.limitstate.LimitChangeNotifier;

import java.util.ArrayList;

public class LimitChangeNotifierImp implements LimitChangeNotifier {
    private final ArrayList<LimitChangeObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(LimitChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (LimitChangeObserver observer : observers){
            observer.limitChanged();
        }
    }
}
