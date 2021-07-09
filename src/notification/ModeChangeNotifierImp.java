package notification;

import java.util.ArrayList;

public class ModeChangeNotifierImp implements ModeChangeNotifier {
    private final ArrayList<ModeChangeObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(ModeChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (ModeChangeObserver observer : observers) {
            observer.modeChanged();
        }
    }
}
