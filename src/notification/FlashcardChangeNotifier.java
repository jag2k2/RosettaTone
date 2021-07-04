package notification;

public interface FlashcardChangeNotifier {
    void addObserver(FlashcardChangeObserver observer);
    void notifyObservers();
}
