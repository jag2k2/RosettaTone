package com.jag2k2.statemodels.limitstate;

import com.jag2k2.music.Note;
import com.jag2k2.music.NoteAccidental;
import com.jag2k2.notification.LimitChangeObserver;
import com.jag2k2.uicomponents.rangeselector.noteselector.LimitModifier;
import com.jag2k2.uicomponents.rangeselector.noteselector.BoundedNoteModifier;
import com.jag2k2.utility.Maybe;

import javax.swing.*;

abstract public class AbstractBoundedLimitState implements BoundedNoteModifier, LimitChangeObserver {
    private final LimitModifier limitModifier;
    private Note lowerBound;
    private Note upperBound;
    private Maybe<LimitChangeNotifier> boundChangeNotifier = new Maybe<>();

    protected AbstractBoundedLimitState(LimitModifier limitModifier, Note lowerBound, Note upperBound){
        this.limitModifier = limitModifier;
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
        return limitModifier.getLimit();
    }

    @Override
    public void setLimit(Note note){
        if ((note.compareTo(lowerBound) >= 0) && (note.compareTo(upperBound) <= 0)) {
            limitModifier.setLimit(note);
        }
    }

    @Override
    public void increment() {
        if (limitModifier.compareTo(upperBound) < 0){
            limitModifier.increment();
        }
    }

    @Override
    public void decrement() {
        if (limitModifier.compareTo(lowerBound) > 0){
            limitModifier.decrement();
        }
    }

    @Override
    public void refreshJComboBoxOptions(JComboBox<Note> comboBox) {
        comboBox.removeAllItems();
        for (Note noteIterator = upperBound; noteIterator.compareTo(lowerBound) >= 0; noteIterator = noteIterator.getPrevious(NoteAccidental.NATURAL)){
            comboBox.addItem(noteIterator);
            if (limitModifier.compareTo(noteIterator) == 0)
                comboBox.setSelectedItem(noteIterator);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractBoundedLimitState){
            AbstractBoundedLimitState toCompare = (AbstractBoundedLimitState) obj;
            return lowerBound.equals(toCompare.lowerBound)
                    && limitModifier.equals(toCompare.limitModifier)
                    && upperBound.equals(toCompare.upperBound);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[lower: " + lowerBound + ", limit: " + getLimit().toString() + ", upper: " + upperBound + "]";
    }

    @Override
    public int compareTo(Note note) {
        return limitModifier.compareTo(note);
    }
}
