package uicomponents;

import notification.KeyboardChangeObserver;
import trainer.FlashcardSatisfiedNotifier;

public interface SightReadTrainer extends KeyboardChangeObserver {
    void addFlashcardSatisfiedNotifier(FlashcardSatisfiedNotifier flashcardSatisfiedNotifier);
}
