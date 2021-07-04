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
    private final NoteCollectionList noteTargets;
    private final List<NoteTargetChangeObserver> observers;

    public SightReadTrainerImp(NoteLimitModel lowerLimit, NoteLimitModel upperLimit, KeyboardState keyboardState){
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.keyboardState = keyboardState;
        this.noteTargets = new NoteCollectionListImp();
        this.observers = new ArrayList<>();
        generateNewNoteTargets();
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
    public NoteCollectionList getNoteTargets() {
        return noteTargets;
    }

    @Override
    public void rangeChanged() {
        generateNewNoteTargets();
        notifyObservers();
    }

    @Override
    public void keyboardChanged() {
        NoteCollection activeNotes = keyboardState.getActiveNotes();
        for (NoteCollection currentTarget : noteTargets.getFirstItem()){
            if(activeNotes.contains(currentTarget)){
                noteTargets.removeFirstItem();
                addNewNoteTarget();
                notifyObservers();
            }
        }
    }

    protected void generateNewNoteTargets(){
        noteTargets.clear();

        for (int i = 0; i < targetCount; i++) {
            addNewNoteTarget();
        }
    }

    protected void addNewNoteTarget(){
        Note lowerNote = lowerLimit.getLimit();
        Note upperNote = upperLimit.getLimit();

        Key lowerKey = new Key(lowerNote.getOctave(), lowerNote.getNoteName(), NoteAccidental.NATURAL);
        Key upperKey = new Key(upperNote.getOctave(), upperNote.getNoteName(), NoteAccidental.NATURAL);
        Key randomKey = lowerKey.generateRandomKeyBetweenThisAnd(upperKey);
        Note randomNote = new Note(randomKey);
        NoteCollection noteTarget = new NoteCollectionImp();
        noteTarget.add(randomNote);
        noteTargets.add(noteTarget);
    }

}
