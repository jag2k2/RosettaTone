package notification;

import statemodels.RangeChangeNotifier;

import java.util.ArrayList;

public class RangeChangeNotifierImp implements RangeChangeNotifier {
    private final ArrayList<RangeChangeObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(RangeChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (RangeChangeObserver observer : observers){
            observer.rangeChanged();
        }
    }
}
