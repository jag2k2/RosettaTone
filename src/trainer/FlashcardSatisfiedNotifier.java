package trainer;

import notification.FlashcardSatisfiedObserver;

public interface FlashcardSatisfiedNotifier {
    void addObserver(FlashcardSatisfiedObserver observer);
    void notifyFlashcardSatisfied();
}
