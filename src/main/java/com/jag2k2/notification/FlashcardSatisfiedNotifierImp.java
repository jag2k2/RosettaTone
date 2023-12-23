package com.jag2k2.notification;

import com.jag2k2.trainer.FlashcardSatisfiedNotifier;
import java.util.ArrayList;

public class FlashcardSatisfiedNotifierImp implements FlashcardSatisfiedNotifier {
    private final ArrayList<FlashcardSatisfiedObserver> observers;

    public FlashcardSatisfiedNotifierImp(){
        observers = new ArrayList<>();
    }

    @Override
    public void addObserver(FlashcardSatisfiedObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyFlashcardSatisfied() {
        for (FlashcardSatisfiedObserver observer : observers){
            observer.flashcardSatisfied();
        }
    }
}
