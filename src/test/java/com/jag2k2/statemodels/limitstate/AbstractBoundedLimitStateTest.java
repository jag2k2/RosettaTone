package statemodels.limitstate;

import music.Note;
import collections.NoteSetImp;
import music.NoteName;
import notification.LimitChangeNotifierImp;
import notification.LimitChangeObserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uicomponents.rangeselector.noteselector.LimitModifier;
import utility.NoteSet;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractBoundedLimitStateTest implements LimitChangeObserver {

    private LowerBoundedLimitStateImp boundedNoteLimit;
    private LimitChangeNotifier limitChangeNotifier;
    private LimitChangeNotifier otherLimitChangeNotifier;
    private LimitChangeNotifier boundChangeNotifier;

    private LimitModifier otherLimit;

    private final Note lowerBound = new Note(NoteName.C, 4);
    private final Note lowerLimit = new Note(NoteName.D, 4);
    private final Note upperBound = new Note(NoteName.B, 4);
    private final Note otherLimitNote = new Note(NoteName.G, 4);

    private boolean notificationFired;

    @Override
    public void limitChanged() {
        notificationFired = true;
    }

    @BeforeEach
    void setup(){
        limitChangeNotifier = new LimitChangeNotifierImp();
        otherLimitChangeNotifier = new LimitChangeNotifierImp();
        boundChangeNotifier = new LimitChangeNotifierImp();
        LimitStateImp noteLimit = new LimitStateImp(lowerLimit);
        noteLimit.addLimitChangeNotifier(limitChangeNotifier);
        otherLimit = new LimitStateImp(otherLimitNote);
        boundedNoteLimit = new LowerBoundedLimitStateImp(noteLimit, otherLimit, lowerBound, upperBound);
        boundedNoteLimit.addBoundChangeNotifier(boundChangeNotifier);

        limitChangeNotifier.addObserver(this);
        boundChangeNotifier.addObserver(this);
        otherLimitChangeNotifier.addObserver(boundedNoteLimit);

        notificationFired = false;
    }

    @Test
    void canCheckEquality() {
        LimitModifier limitModifier = new LimitStateImp(new Note(NoteName.D, 4));
        LowerBoundedLimitStateImp expected = new LowerBoundedLimitStateImp(limitModifier, otherLimit, lowerBound, upperBound);
        assertEquals(expected, boundedNoteLimit);

        expected = new LowerBoundedLimitStateImp(limitModifier, otherLimit, new Note(NoteName.B,1), upperBound);
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
        LimitModifier limitModifier = new LimitStateImp(lowerBound);
        boundedNoteLimit.setLowerBound(limitModifier);
        assertFalse(notificationFired);
    }

    @Test
    void canSetNewUpperBound() {
        boundedNoteLimit.setUpperBound(otherLimit);
        assertTrue(notificationFired);
    }

    @Test
    void wontOverwriteSameUpperBound(){
        LimitModifier limitModifier = new LimitStateImp(upperBound);
        boundedNoteLimit.setUpperBound(limitModifier);
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
        NoteSet expectedOptions = new NoteSetImp();
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
        NoteSet comboBoxOptions = new NoteSetImp();
        for (int i = 0; i < comboBoxModel.getSize(); i++) {
            comboBoxOptions.add(comboBoxModel.getElementAt(i));
        }

        assertEquals(expectedOptions, comboBoxOptions);
        assertEquals(noteLimit, comboBox.getSelectedItem());
    }
}
