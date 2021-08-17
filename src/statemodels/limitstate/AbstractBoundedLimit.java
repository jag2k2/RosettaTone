package statemodels.limitstate;

import music.note.Note;
import music.note.NoteAccidental;
import notification.LimitChangeObserver;
import uicomponents.rangeselector.noteselector.LimitModifier;
import uicomponents.rangeselector.noteselector.BoundedNoteModifier;
import utility.Maybe;

import javax.swing.*;

abstract public class AbstractBoundedLimit implements BoundedNoteModifier, LimitChangeObserver {
    private final LimitModifier limit;
    private Note lowerBound;
    private Note upperBound;
    private Maybe<LimitChangeNotifier> boundChangeNotifier = new Maybe<>();

    protected AbstractBoundedLimit(LimitModifier limit, Note lowerBound, Note upperBound){
        this.limit = limit;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public void addBoundChangeNotifier(LimitChangeNotifier boundChangeNotifier){
        this.boundChangeNotifier = new Maybe<>(boundChangeNotifier);
    }

    protected void setLowerBound(LimitModifier limitModifier) {
        if (limitModifier.compareTo(lowerBound) != 0){
            lowerBound = limitModifier.getLimit();
            notifyAnyObservers();
        }
    }

    protected void setUpperBound(LimitModifier limitModifier) {
        if (limitModifier.compareTo(upperBound) != 0) {
            upperBound = limitModifier.getLimit();
            notifyAnyObservers();
        }
    }

    protected void notifyAnyObservers(){
        for (LimitChangeNotifier notifier : boundChangeNotifier){
            notifier.notifyObservers();
        }
    }

    @Override
    public Note getLimit() {
        return limit.getLimit();
    }

    @Override
    public void setLimit(Note note){
        if ((note.compareTo(lowerBound) >= 0) && (note.compareTo(upperBound) <= 0)) {
            limit.setLimit(note);
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
    public void refreshJComboBoxOptions(JComboBox<Note> comboBox) {
        comboBox.removeAllItems();
        for (Note noteIterator = upperBound; noteIterator.compareTo(lowerBound) >= 0; noteIterator = noteIterator.getPrevious(NoteAccidental.NATURAL)){
            comboBox.addItem(noteIterator);
            if (limit.compareTo(noteIterator) == 0)
                comboBox.setSelectedItem(noteIterator);
        }
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
