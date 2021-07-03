package trainer;

import instrument.Key;
import music.Note;
import music.NoteAccidental;
import music.NoteList;
import music.NoteListImp;
import notification.KeyboardChangeObserver;
import notification.NoteTargetChangeNotifier;
import notification.NoteTargetChangeObserver;
import notification.RangeChangeObserver;
import statemodels.KeyboardState;
import statemodels.NoteLimitModel;

import java.util.ArrayList;
import java.util.List;

public class SightReadTrainerImp implements SightReadTrainer, RangeChangeObserver, KeyboardChangeObserver, NoteTargetChangeNotifier {
    private final NoteLimitModel lowerLimit;
    private final NoteLimitModel upperLimit;
    private final KeyboardState keyboardState;
    private final NoteList noteTarget;
    private final List<NoteTargetChangeObserver> observers;

    public SightReadTrainerImp(NoteLimitModel lowerLimit, NoteLimitModel upperLimit, KeyboardState keyboardState){
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.keyboardState = keyboardState;
        this.noteTarget = new NoteListImp();
        this.observers = new ArrayList<>();
        generateNoteTarget();
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
    public NoteList getNoteTarget() {
        return noteTarget;
    }

    @Override
    public void rangeChanged() {
        generateNoteTarget();
    }

    @Override
    public void keyboardChanged() {
        NoteList activeNotes = keyboardState.getActiveNotes();
        if(activeNotes.contains(noteTarget)){
            generateNoteTarget();
        }
    }

    protected void generateNoteTarget(){
        noteTarget.clear();

        Note lowerNote = lowerLimit.getLimit();
        Note upperNote = upperLimit.getLimit();

        Key lowerKey = new Key(lowerNote.getOctave(), lowerNote.getNoteName(), NoteAccidental.NATURAL);
        Key upperKey = new Key(upperNote.getOctave(), upperNote.getNoteName(), NoteAccidental.NATURAL);
        Key randomKey = lowerKey.generateRandomKeyBetweenThisAnd(upperKey);
        Note randomNote = new Note(randomKey);

        noteTarget.add(randomNote);
        notifyObservers();
    }

}
