package statemodels;

import music.Note;
import uicomponents.rangeselector.noteselector.NoteLimitModel;

public class NoteLimitModelImp implements NoteLimitModel {
    private Note noteLimit;
    private final RangeChangeNotifier rangeChangeNotifier;

    public NoteLimitModelImp(Note noteLimit, RangeChangeNotifier rangeChangeNotifier){
        this.noteLimit = noteLimit;
        this.rangeChangeNotifier = rangeChangeNotifier;
    }

    @Override
    public void setLimit(Note note){
        if (!note.equals(noteLimit)) {
            this.noteLimit = note;
            rangeChangeNotifier.notifyObservers();
        }
    }

    @Override
    public Note getLimit(){
        return noteLimit;
    }

    @Override
    public String toString() {
        return noteLimit.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NoteLimitModelImp){
            NoteLimitModelImp toCompare = (NoteLimitModelImp) obj;
            return noteLimit.equals(toCompare.noteLimit);
        }
        return false;
    }
}
