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
    public void notifyKeyboardChanged() {
        for (KeyboardChangeObserver observer : observers) {
            observer.KeyboardChanged();
        }
    }
}
