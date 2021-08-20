package notification;

import uicomponents.renderer.grandstaff.FlashcardDrawable;

import javax.swing.*;

public class FlashCardChangeObserverImp implements FlashcardChangeObserver {
    private final FlashcardDrawable flashcards;
    private final JComponent grandStaffRenderer;

    public FlashCardChangeObserverImp(FlashcardDrawable flashcards, JComponent grandStaffRenderer) {
        this.flashcards = flashcards;
        this.grandStaffRenderer = grandStaffRenderer;
    }

    @Override
    public void flashcardChanged() {
        Thread thread = new Thread(flashcards);
        flashcards.setScrollableComponent(grandStaffRenderer);
        thread.start();
    }
}
