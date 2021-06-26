package notification;

import java.util.ArrayList;
import java.util.List;

public class RangeChangeNotifierImp implements RangeChangeNotifier{
    private final List<RangeChangeObserver> observers;

    public RangeChangeNotifierImp(){
        this.observers = new ArrayList<>();
    }

    @Override
    public void addObserver(RangeChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for(RangeChangeObserver observer : observers){
            observer.updateRange();
        }
    }
}
