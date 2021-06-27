package statemodels;

import music.Note;
import notification.RangeChangeNotifier;
import notification.RangeChangeObserver;
import java.util.ArrayList;
import java.util.List;

public class NoteLimitModelImp implements NoteLimitModel, RangeChangeNotifier {
    private Note noteLimit;
    private final List<RangeChangeObserver> observers;

    public NoteLimitModelImp(Note noteLimit){
        this.noteLimit = noteLimit;
        this.observers = new ArrayList<>();
    }

    public void changeLimit(Note note){
        if (!note.equals(noteLimit)) {
            this.noteLimit = note;
            notifyObservers();
        }
    }

    public Note getLimit(){
        return noteLimit;
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
