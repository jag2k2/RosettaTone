package trainer;

import instrument.Key;
import music.*;
import notification.KeyboardChangeObserver;
import notification.NoteTargetChangeNotifier;
import notification.NoteTargetChangeObserver;
import notification.RangeChangeObserver;
import statemodels.KeyboardState;
import statemodels.NoteLimitModel;

import java.util.ArrayList;
import java.util.List;

public class SightReadTrainerImp implements SightReadTrainer, RangeChangeObserver, KeyboardChangeObserver, NoteTargetChangeNotifier {
    static private final int targetCount = 8;

    private final NoteLimitModel lowerLimit;
    private final NoteLimitModel upperLimit;
    private final KeyboardState keyboardState;
    private final NoteCollectionList flashcardList;
    private final List<NoteTargetChangeObserver> observers;

    public SightReadTrainerImp(NoteLimitModel lowerLimit, NoteLimitModel upperLimit, KeyboardState keyboardState){
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.keyboardState = keyboardState;
        this.flashcardList = new NoteCollectionListImp();
        this.observers = new ArrayList<>();
        generateAllNewFlashcards();
    }

    @Override
    public void addObserver(NoteTargetChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (NoteTargetChangeObserver observer : observers){
            observer.noteTargetChanged();
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

        Key lowerKey = new Key(lowerNote.getOctave(), lowerNote.getNoteName(), NoteAccidental.NATURAL);
        Key upperKey = new Key(upperNote.getOctave(), upperNote.getNoteName(), NoteAccidental.NATURAL);
        Key randomKey = lowerKey.generateRandomKeyBetweenThisAnd(upperKey);
        Note randomNote = new Note(randomKey);
        NoteCollection noteTarget = new NoteCollectionImp();
        noteTarget.add(randomNote);
        flashcardList.add(noteTarget);
    }

}
