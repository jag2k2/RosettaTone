package statemodels.limitstate;

import music.note.Note;
import music.note.NoteName;
import notification.LimitChangeNotifierImp;
import notification.LimitChangeObserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uicomponents.rangeselector.noteselector.SteppableState;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractBoundedLimitTest implements LimitChangeObserver {

    private AbstractBoundedLimit boundedNoteLimit;
    private SteppableState<Note> otherLimit;

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
        LimitChangeNotifier limitChangeNotifier = new LimitChangeNotifierImp();
        LimitChangeNotifier boundChangeNotifier = new LimitChangeNotifierImp();

        LimitStateImp noteLimit = new LimitStateImp(lowerLimit);
        otherLimit = new LimitStateImp(otherLimitNote);
        noteLimit.addLimitChangeNotifier(limitChangeNotifier);

        boundedNoteLimit = new LowerBoundedLimitStateImp(lowerBound, noteLimit, upperBound);
        boundedNoteLimit.addBoundChangeNotifier(boundChangeNotifier);
        boundedNoteLimit.setOtherLimit(otherLimit);

        limitChangeNotifier.addObserver(this);
        boundChangeNotifier.addObserver(this);

        notificationFired = false;
    }

    @Test
    void canCheckEquality() {
        SteppableState<Note> limit = new LimitStateImp(new Note(NoteName.D, 4));
        LowerBoundedLimitStateImp expected = new LowerBoundedLimitStateImp(lowerBound, limit, upperBound);
        assertEquals(expected, boundedNoteLimit);

        expected = new LowerBoundedLimitStateImp(new Note(NoteName.B,1), limit, upperBound);
        assertNotEquals(expected, boundedNoteLimit);
    }

    @Test
    void canDisplayAsString(){
        String compareString = "[lower: C4, limit: D4, upper: B4]";
        assertEquals(compareString, boundedNoteLimit.toString());
    }

    @Test
    void canSetNewLowerBound() {
        boundedNoteLimit.setLowerBound();
        assertTrue(notificationFired);
    }

    @Test
    void wontOverwriteSameLowerBound() {
        otherLimit.update(lowerBound);
        boundedNoteLimit.setLowerBound();
        assertFalse(notificationFired);
    }

    @Test
    void canSetNewUpperBound() {
        boundedNoteLimit.setUpperBound();
        assertTrue(notificationFired);
    }

    @Test
    void wontOverwriteSameUpperBound(){
        otherLimit.update(upperBound);
        boundedNoteLimit.setUpperBound();
        assertFalse(notificationFired);
    }

    @Test
    void canSetActiveWithinRange(){
        Note newActiveNote = new Note(NoteName.A, 4);
        boundedNoteLimit.update(newActiveNote);
        assertTrue(notificationFired);
    }

    @Test
    void wontSetLimitBelowBounds(){
        Note noteTooLow = new Note(NoteName.C, 0);
        boundedNoteLimit.update(noteTooLow);
        assertFalse(notificationFired);
    }

    @Test
    void wontSetLimitAboveBounds(){
        Note noteTooHigh = new Note(NoteName.C, 6);
        boundedNoteLimit.update(noteTooHigh);
        assertFalse(notificationFired);
    }

    @Test
    void willDecrementWithinBounds(){
        boundedNoteLimit.decrement();
        assertTrue(notificationFired);
    }

    @Test
    void wontDecrementLowerThanBounds(){
        boundedNoteLimit.update(lowerBound);
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
        boundedNoteLimit.update(upperBound);
        notificationFired = false;

        boundedNoteLimit.increment();
        assertFalse(notificationFired);
    }

    @Test
    void canGetOptions(){
        Note[] options = boundedNoteLimit.getOptions();
        Note[] expected = {new Note(NoteName.B, 4),
                new Note(NoteName.A, 4),
                new Note(NoteName.G, 4),
                new Note(NoteName.F, 4),
                new Note(NoteName.E, 4),
                new Note(NoteName.D, 4),
                new Note(NoteName.C, 4)};
        assertArrayEquals(expected, options);
    }

    @Test
    void canCompare(){
        assertEquals(boundedNoteLimit.compareTo(new Note(NoteName.D, 4)), 0);
        assertTrue(boundedNoteLimit.compareTo(new Note(NoteName.E, 4)) < 0);
        assertTrue(boundedNoteLimit.compareTo(new Note(NoteName.C,4)) > 0);
    }
}
