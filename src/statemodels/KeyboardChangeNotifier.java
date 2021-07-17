package statemodels;

import notification.KeyboardChangeObserver;

public interface KeyboardChangeNotifier {
    void addObserver(KeyboardChangeObserver observer);
    void notifyKeyDown();
    void notifyKeyUp();
}
