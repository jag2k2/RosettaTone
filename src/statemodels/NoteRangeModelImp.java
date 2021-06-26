package statemodels;

import music.Note;
import notification.RangeChangeNotifier;
import notification.RangeChangeObserver;
import java.util.ArrayList;
import java.util.List;

public class NoteRangeModelImp implements NoteRangeModel, RangeChangeNotifier {
    private Note lowerNote;
    private Note upperNote;
    private final List<RangeChangeObserver> observers;

    public NoteRangeModelImp(Note lowerNote, Note upperNote){
        this.lowerNote = lowerNote;
        this.upperNote = upperNote;
        this.observers = new ArrayList<>();
    }

    public void changeUpperLimit(Note note){
        this.upperNote = note;
        notifyObservers();
    }

    public void changeLowerLimit(Note note){
        this.lowerNote = note;
        notifyObservers();
    }

    public Note getUpperLimit(){
        return upperNote;
    }

    public Note getLowerLimit(){
        return lowerNote;
    }

    @Override
    public void addObserver(RangeChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for(RangeChangeObserver observer : observers){
            observer.updateRange();
        }
    }

    @Override
    public String toString() {
        return "[" + lowerNote.toString() + ", " + upperNote.toString() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NoteRangeModelImp){
            NoteRangeModelImp toCompare = (NoteRangeModelImp) obj;
            return (upperNote.equals(toCompare.upperNote) && lowerNote.equals(toCompare.lowerNote));
        }
        return false;
    }
}
