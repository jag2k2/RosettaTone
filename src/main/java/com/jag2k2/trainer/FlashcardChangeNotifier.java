package com.jag2k2.trainer;

import com.jag2k2.notification.FlashcardChangeObserver;

public interface FlashcardChangeNotifier {
    void addObserver(FlashcardChangeObserver observer);
    void notifyFlashcardChanged();
}
