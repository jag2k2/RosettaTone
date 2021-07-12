package statemodels.notelimit;

import music.Note;
import music.NoteAccidental;
import notification.LimitChangeObserver;
import uicomponents.rangeselector.noteselector.NoteModifier;
import uicomponents.rangeselector.noteselector.BoundedNoteModifier;

import javax.swing.*;

abstract public class AbstractBoundedNoteModifier implements BoundedNoteModifier, LimitChangeObserver {
    private final NoteModifier noteModifier;
    private Note lowerBound;
    private Note upperBound;
    private final LimitChangeNotifier boundChangeNotifier;

    protected AbstractBoundedNoteModifier(NoteModifier noteModifier, Note lowerBound, Note upperBound, LimitChangeNotifier boundChangeNotifier){
        this.noteModifier = noteModifier;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.boundChangeNotifier = boundChangeNotifier;
    }

    protected void setLowerBound(NoteModifier noteModifier) {
        if (noteModifier.compareTo(lowerBound) != 0){
            lowerBound = noteModifier.getLimit();
            boundChangeNotifier.notifyObservers();
        }
    }

    protected void setUpperBound(NoteModifier noteModifier) {
        if (noteModifier.compareTo(upperBound) != 0) {
            upperBound = noteModifier.getLimit();
            boundChangeNotifier.notifyObservers();
        }
    }

    @Override
    public Note getLimit() {
        return noteModifier.getLimit();
    }

    @Override
    public void setLimit(Note note){
        if ((note.compareTo(lowerBound) >= 0) && (note.compareTo(upperBound) <= 0)) {
            noteModifier.setLimit(note);
        }
    }

    @Override
    public void increment() {
        if (noteModifier.compareTo(upperBound) < 0){
            noteModifier.increment();
        }
    }

    @Override
    public void decrement() {
        if (noteModifier.compareTo(lowerBound) > 0){
            noteModifier.decrement();
        }
    }

    @Override
    public void refreshJComboBoxOptions(JComboBox<Note> comboBox) {
        comboBox.removeAllItems();
        for (Note noteIterator = upperBound; noteIterator.compareTo(lowerBound) >= 0; noteIterator = noteIterator.getPrevious(NoteAccidental.NATURAL)){
            comboBox.addItem(noteIterator);
            if (noteModifier.compareTo(noteIterator) == 0)
                comboBox.setSelectedItem(noteIterator);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractBoundedNoteModifier){
            AbstractBoundedNoteModifier toCompare = (AbstractBoundedNoteModifier) obj;
            return lowerBound.equals(toCompare.lowerBound)
                    && noteModifier.equals(toCompare.noteModifier)
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
        return noteModifier.compareTo(note);
    }
}
