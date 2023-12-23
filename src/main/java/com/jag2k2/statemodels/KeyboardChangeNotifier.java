package com.jag2k2.statemodels;

import com.jag2k2.notification.KeyboardChangeObserver;

public interface KeyboardChangeNotifier {
    void addObserver(KeyboardChangeObserver observer);
    void notifyKeyDown();
    void notifyKeyUp();
}
