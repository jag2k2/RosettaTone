package trainer;

import music.*;
import notification.KeyboardChangeObserver;
import notification.FlashcardChangeNotifier;
import notification.FlashcardChangeObserver;
import notification.RangeChangeObserver;
import statemodels.KeyboardState;
import statemodels.NoteLimitModel;
import uicomponents.renderer.CanvasRender;

import java.util.ArrayList;
import java.util.List;

public class SightReadTrainerImp implements SightReadTrainer, RangeChangeObserver, KeyboardChangeObserver, FlashcardChangeNotifier {
    static private final int targetCount = 8;

    private final NoteLimitModel lowerLimit;
    private final NoteLimitModel upperLimit;
    private final KeyboardState keyboardState;
    private final NoteCollectionList flashcardList;
    private final List<FlashcardChangeObserver> observers;

    public SightReadTrainerImp(NoteLimitModel lowerLimit, NoteLimitModel upperLimit, KeyboardState keyboardState){
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.keyboardState = keyboardState;
        this.flashcardList = new NoteCollectionListImp();
        this.observers = new ArrayList<>();
        generateAllNewFlashcards();
    }

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

    @Override
    public NoteCollectionList getFlashcardNotes() {
        return flashcardList;
    }

    @Override
    public void rangeChanged() {
        generateAllNewFlashcards();
        notifyObservers();
    }

    @Override
    public void keyboardChanged() {
        NoteCollection activeNotes = keyboardState.getActiveNotes();
        for (NoteCollection currentTarget : flashcardList.getFirstItem()){
            if(activeNotes.contains(currentTarget)){
                flashcardList.removeFirstItem();
                addNewFlashcard();
                notifyObservers();
            }
        }
    }

    protected void generateAllNewFlashcards(
    ){
        flashcardList.clear();

        for (int i = 0; i < targetCount; i++) {
            addNewFlashcard();
        }
    }

    protected void addNewFlashcard(){
        Note lowerNote = lowerLimit.getLimit();
        Note upperNote = upperLimit.getLimit();

        Note randomNote = CanvasRender.getRandomNote(lowerNote, upperNote);
        NoteCollection noteTarget = new NoteCollectionImp();
        noteTarget.add(randomNote);
        flashcardList.add(noteTarget);
    }

}
