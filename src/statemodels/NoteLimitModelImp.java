package statemodels;

import music.Note;
import music.NoteAccidental;
import notification.RangeChangeNotifierImp;
import uicomponents.rangeselector.noteselector.NoteLimitModel;
import uicomponents.rangeselector.noteselector.DisplayDirection;

import javax.swing.*;

public class NoteLimitModelImp implements NoteLimitModel {
    private final Note lowerLimit;
    private Note activeLimit;
    private final Note upperLimit;
    private final RangeChangeNotifier rangeChangeNotifier;

    public NoteLimitModelImp(Note lowerLimit, Note activeLimit, Note upperLimit, RangeChangeNotifier rangeChangeNotifier){
        this.lowerLimit = lowerLimit;
        this.activeLimit = activeLimit;
        this.upperLimit = upperLimit;
        this.rangeChangeNotifier = rangeChangeNotifier;
    }

    public NoteLimitModelImp(Note lowerLimit, Note activeLimit, Note upperLimit){
        this.lowerLimit = lowerLimit;
        this.activeLimit = activeLimit;
        this.upperLimit = upperLimit;
        this.rangeChangeNotifier = new RangeChangeNotifierImp();
    }

    @Override
    public void setActiveLimit(Note note){
        if (!activeLimit.equals(note) && (note.compareTo(lowerLimit) >= 0) && (note.compareTo(upperLimit) <= 0)) {
            activeLimit = note;
            rangeChangeNotifier.notifyObservers();
        }
    }

    @Override
    public void incrementActive() {
        if (activeLimit.compareTo(upperLimit) < 0){
            activeLimit = activeLimit.getNext(NoteAccidental.NATURAL);
        }
    }

    @Override
    public void decrementActive() {
        if (activeLimit.compareTo(lowerLimit) > 0){
            activeLimit = activeLimit.getPrevious(NoteAccidental.NATURAL);
        }
    }

    @Override
    public void refreshJComboBoxOptions(JComboBox<Note> comboBox) {
        comboBox.removeAllItems();
        for (Note noteIterator = upperLimit; noteIterator.compareTo(lowerLimit) >= 0; noteIterator = noteIterator.getPrevious(NoteAccidental.NATURAL)){
            comboBox.addItem(noteIterator);
            if (noteIterator.equals(activeLimit))
                comboBox.setSelectedItem(noteIterator);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NoteLimitModelImp){
            NoteLimitModelImp toCompare = (NoteLimitModelImp) obj;
            return lowerLimit.equals(toCompare.lowerLimit)
                    && activeLimit.equals(toCompare.activeLimit)
                    && upperLimit.equals(toCompare.upperLimit);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[absolute: " + lowerLimit + ", active: " + activeLimit + ", available: " + upperLimit + "]";
    }
}
