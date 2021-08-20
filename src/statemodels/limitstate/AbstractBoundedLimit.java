package statemodels.limitstate;

import music.note.Note;
import music.note.NoteAccidental;
import notification.LimitChangeObserver;
import uicomponents.rangeselector.noteselector.SteppableState;
import uicomponents.util.SelectableState;
import utility.Maybe;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractBoundedLimit implements SelectableState<Note>, SteppableState<Note>, LimitChangeObserver {
    private Note lowerBound;
    private final SteppableState<Note> limit;
    private Note upperBound;
    private Maybe<SteppableState<Note>> otherLimit = new Maybe<>();
    private Maybe<LimitChangeNotifier> boundChangeNotifier = new Maybe<>();

    protected AbstractBoundedLimit(Note lowerBound, SteppableState<Note> limit, Note upperBound){
        this.limit = limit;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public void addBoundChangeNotifier(LimitChangeNotifier boundChangeNotifier){
        this.boundChangeNotifier = new Maybe<>(boundChangeNotifier);
    }

    public void setOtherLimit(SteppableState<Note> otherLimit){
        this.otherLimit = new Maybe<>(otherLimit);
    }

    protected void setLowerBound() {
        for (SteppableState<Note> otherState : otherLimit) {
            if (otherState.compareTo(lowerBound) != 0){
                lowerBound = otherState.getActive();
                notifyAnyObservers();
            }
        }
    }

    protected void setUpperBound() {
        for (SteppableState<Note> otherState : otherLimit) {
            if (otherState.compareTo(upperBound) != 0) {
                upperBound = otherState.getActive();
                notifyAnyObservers();
            }
        }
    }

    protected void notifyAnyObservers(){
        for (LimitChangeNotifier notifier : boundChangeNotifier){
            notifier.notifyObservers();
        }
    }

    @Override
    public Note getActive() {
        return limit.getActive();
    }

    @Override
    public void update(Note newValue) {
        if ((newValue.compareTo(lowerBound) >= 0) && (newValue.compareTo(upperBound) <= 0)) {
            limit.update(newValue);
        }
    }

    @Override
    public void increment() {
        if (limit.compareTo(upperBound) < 0){
            limit.increment();
        }
    }

    @Override
    public void decrement() {
        if (limit.compareTo(lowerBound) > 0){
            limit.decrement();
        }
    }

    @Override
    public Note[] getOptions() {
        List<Note> options = new ArrayList<>();
        for (Note noteIterator = upperBound; noteIterator.compareTo(lowerBound) >= 0; noteIterator = noteIterator.getPrevious(NoteAccidental.NATURAL)){
            options.add(noteIterator);
        }
        return options.toArray(new Note[0]);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractBoundedLimit){
            AbstractBoundedLimit toCompare = (AbstractBoundedLimit) obj;
            return lowerBound.equals(toCompare.lowerBound)
                    && limit.equals(toCompare.limit)
                    && upperBound.equals(toCompare.upperBound);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[lower: " + lowerBound + ", limit: " + limit + ", upper: " + upperBound + "]";
    }

    @Override
    public int compareTo(Note note) {
        return limit.compareTo(note);
    }
}
