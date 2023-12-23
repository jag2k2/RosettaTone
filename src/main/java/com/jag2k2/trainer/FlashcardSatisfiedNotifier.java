package com.jag2k2.trainer;

import com.jag2k2.notification.FlashcardSatisfiedObserver;

public interface FlashcardSatisfiedNotifier {
    void addObserver(FlashcardSatisfiedObserver observer);
    void notifyFlashcardSatisfied();
}
