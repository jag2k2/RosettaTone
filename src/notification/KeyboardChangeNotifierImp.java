package notification;

import statemodels.KeyboardChangeNotifier;

import java.util.ArrayList;

public class KeyboardChangeNotifierImp implements KeyboardChangeNotifier {
    private final ArrayList<KeyboardChangeObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(KeyboardChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyKeyDown() {
        for (KeyboardChangeObserver observer : observers) {
            observer.boardChangedWithKeyDown();
        }
    }

    @Override
    public void notifyKeyUp() {
        for (KeyboardChangeObserver observer : observers) {
            observer.boardChangedWithKeyUp();
        }
    }
}
