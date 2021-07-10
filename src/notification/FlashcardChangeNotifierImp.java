package notification;

import trainer.FlashcardChangeNotifier;

import java.util.ArrayList;

public class FlashcardChangeNotifierImp implements FlashcardChangeNotifier {
    private final ArrayList<FlashcardChangeObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(FlashcardChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (FlashcardChangeObserver observer : observers){
            observer.flashcardChanged();
        }
    }
}
