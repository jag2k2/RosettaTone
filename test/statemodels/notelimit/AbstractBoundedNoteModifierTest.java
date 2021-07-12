package statemodels.notelimit;

import music.Note;
import music.NoteCollectionImp;
import music.NoteName;
import notification.LimitChangeNotifierImp;
import notification.LimitChangeObserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uicomponents.rangeselector.noteselector.NoteModifier;
import utility.NoteCollection;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractBoundedNoteModifierTest implements LimitChangeObserver {

    private LowerBoundedNoteModifierImp boundedNoteLimit;
    private LimitChangeNotifier limitChangeNotifier;
    private LimitChangeNotifier otherLimitChangeNotifier;
    private LimitChangeNotifier boundChangeNotifier;

    private NoteModifier otherLimit;

    private final Note lowerBound = new Note(NoteName.C, 4);
    private final Note lowerLimit = new Note(NoteName.D, 4);
    private final Note upperBound = new Note(NoteName.B, 4);
    private final Note otherLimitNote = new Note(NoteName.G, 4);

    private boolean notificationFired;

    @Override
    public void rangeChanged() {
        notificationFired = true;
    }

    @BeforeEach
    void setup(){
        limitChangeNotifier = new LimitChangeNotifierImp();
        otherLimitChangeNotifier = new LimitChangeNotifierImp();
        boundChangeNotifier = new LimitChangeNotifierImp();
        NoteModifier noteModifier = new NoteModifierImp(lowerLimit, limitChangeNotifier);
        otherLimit = new NoteModifierImp(otherLimitNote, otherLimitChangeNotifier);
        boundedNoteLimit = new LowerBoundedNoteModifierImp(noteModifier, otherLimit, lowerBound, upperBound, boundChangeNotifier);

        limitChangeNotifier.addObserver(this);
        boundChangeNotifier.addObserver(this);
        otherLimitChangeNotifier.addObserver(boundedNoteLimit);

        notificationFired = false;
    }

    @Test
    void canCheckEquality() {
        NoteModifier noteModifier = new NoteModifierImp(new Note(NoteName.D, 4), new LimitChangeNotifierImp());
        LowerBoundedNoteModifierImp expected = new LowerBoundedNoteModifierImp(noteModifier, otherLimit, lowerBound, upperBound, boundChangeNotifier);
        assertEquals(expected, boundedNoteLimit);

        expected = expected = new LowerBoundedNoteModifierImp(noteModifier, otherLimit, new Note(NoteName.B,1), upperBound, boundChangeNotifier);
        assertNotEquals(expected, boundedNoteLimit);
    }

    @Test
    void canDisplayAsString(){
        String compareString = "[lower: C4, limit: D4, upper: B4]";
        assertEquals(compareString, boundedNoteLimit.toString());
    }

    @Test
    void canSetNewLowerBound() {
        boundedNoteLimit.setLowerBound(otherLimit);
        assertTrue(notificationFired);
    }

    @Test
    void wontOverwriteSameLowerBound() {
        NoteModifier noteModifier = new NoteModifierImp(lowerBound, new LimitChangeNotifierImp());
        boundedNoteLimit.setLowerBound(noteModifier);
        assertFalse(notificationFired);
    }

    @Test
    void canSetNewUpperBound() {
        boundedNoteLimit.setUpperBound(otherLimit);
        assertTrue(notificationFired);
    }

    @Test
    void wontOverwriteSameUpperBound(){
        NoteModifier noteModifier = new NoteModifierImp(upperBound, new LimitChangeNotifierImp());
        boundedNoteLimit.setUpperBound(noteModifier);
        assertFalse(notificationFired);
    }

    @Test
    void canSetActiveWithinRange(){
        Note newActiveNote = new Note(NoteName.A, 4);
        boundedNoteLimit.setLimit(newActiveNote);
        assertTrue(notificationFired);
    }

    @Test
    void wontSetLimitBelowBounds(){
        Note noteTooLow = new Note(NoteName.C, 0);
        boundedNoteLimit.setLimit(noteTooLow);
        assertFalse(notificationFired);
    }

    @Test
    void wontSetLimitAboveBounds(){
        Note noteTooHigh = new Note(NoteName.C, 6);
        boundedNoteLimit.setLimit(noteTooHigh);
        assertFalse(notificationFired);
    }

    @Test
    void willDecrementWithinBounds(){
        boundedNoteLimit.decrement();
        assertTrue(notificationFired);
    }

    @Test
    void wontDecrementLowerThanBounds(){
        boundedNoteLimit.setLimit(lowerBound);
        notificationFired = false;
        boundedNoteLimit.decrement();
        assertFalse(notificationFired);
    }

    @Test
    void canIncrementWithinBounds(){
        boundedNoteLimit.increment();
        assertTrue(notificationFired);
    }

    @Test
    void wontIncrementHigherThanBounds(){
        boundedNoteLimit.setLimit(upperBound);
        notificationFired = false;
        boundedNoteLimit.increment();
        assertFalse(notificationFired);
    }

    @Test
    void canUpdateComboBoxOptions() {
        NoteCollection expectedOptions = new NoteCollectionImp();
        expectedOptions.add(new Note(NoteName.B, 4));
        expectedOptions.add(new Note(NoteName.A, 4));
        expectedOptions.add(new Note(NoteName.G, 4));
        expectedOptions.add(new Note(NoteName.F, 4));
        expectedOptions.add(new Note(NoteName.E, 4));
        expectedOptions.add(new Note(NoteName.D, 4));
        expectedOptions.add(new Note(NoteName.C, 4));

        JComboBox<Note> comboBox = new JComboBox<>();
        Note noteLimit = new Note(NoteName.D, 4);

        boundedNoteLimit.refreshJComboBoxOptions(comboBox);
        ComboBoxModel<Note> comboBoxModel = comboBox.getModel();
        NoteCollection comboBoxOptions = new NoteCollectionImp();
        for (int i = 0; i < comboBoxModel.getSize(); i++) {
            comboBoxOptions.add(comboBoxModel.getElementAt(i));
        }

        assertEquals(expectedOptions, comboBoxOptions);
        assertEquals(noteLimit, comboBox.getSelectedItem());
    }
}
