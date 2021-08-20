package uicomponents;

import notification.LimitChangeObserver;
import trainer.FlashcardChangeNotifier;
import trainer.FlashcardGenerator;
import uicomponents.renderer.grandstaff.FlashcardDrawable;
import utility.NoteSetList;

public interface FlashcardState extends NoteSetList, FlashcardGenerator, FlashcardDrawable, LimitChangeObserver {
    void addFlashcardChangeNotifier(FlashcardChangeNotifier flashcardChangeNotifier);
}
