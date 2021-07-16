package statemodels;

import notification.FlashcardChangeObserver;
import notification.FlashcardSatisfiedObserver;
import trainer.FlashcardAdvancer;
import uicomponents.notenamemode.NoteNameMode;
import uicomponents.notenamemode.NoteNameModeChangeNotifier;
import uicomponents.notenamemode.NoteNameModeModifier;
import uicomponents.renderer.NoteNameDrawable;
import utility.Maybe;

public class NoteNameModeStateImp implements NoteNameModeModifier, NoteNameDrawable, FlashcardAdvancer, FlashcardSatisfiedObserver, FlashcardChangeObserver {
    private NoteNameMode noteNameMode;
    private boolean flashcardSatisfied = false;
    private Maybe<NoteNameModeChangeNotifier> alphabetModeChangeNotifier = new Maybe<>();

    public NoteNameModeStateImp(NoteNameMode defaultMode) {
        this.noteNameMode = defaultMode;
    }

    public void addAlphabetModeChangeNotifier(NoteNameModeChangeNotifier noteNameModeChangeNotifier){
        this.alphabetModeChangeNotifier = new Maybe<>(noteNameModeChangeNotifier);
    }

    @Override
    public void setMode(NoteNameMode noteNameMode) {
        if(!this.noteNameMode.equals(noteNameMode)) {
            this.noteNameMode = noteNameMode;
            for (NoteNameModeChangeNotifier notifier : alphabetModeChangeNotifier) {
                notifier.notifyObservers();
            }
        }
    }

    @Override
    public void flashcardSatisfied() {
        flashcardSatisfied = true;
    }

    @Override
    public void flashcardChanged() {
        flashcardSatisfied = false;
    }

    @Override
    public boolean isEnabled(int flashcardIndex) {
        return ((noteNameMode == NoteNameMode.Always) || (noteNameMode == NoteNameMode.Correct && flashcardSatisfied && flashcardIndex == 0));
    }

    @Override
    public boolean immediatelyAdvance() {
        return (noteNameMode == NoteNameMode.Off || noteNameMode == NoteNameMode.Always);
    }

    @Override
    public boolean readyToAdvance() {
        return flashcardSatisfied;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof NoteNameModeStateImp){
            NoteNameModeStateImp toCompare = (NoteNameModeStateImp) obj;
            return noteNameMode.equals(toCompare.noteNameMode);
        }
        return false;
    }

    @Override
    public String toString() {
        return noteNameMode.toString();
    }
}
