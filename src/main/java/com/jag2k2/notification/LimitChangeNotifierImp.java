package notification;

import statemodels.limitstate.LimitChangeNotifier;

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
