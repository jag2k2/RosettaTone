package trainer;

import notification.FlashcardChangeObserver;

public interface FlashcardChangeNotifier {
    void addObserver(FlashcardChangeObserver observer);
    void notifyFlashcardChanged();
}
