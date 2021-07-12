package statemodels.notelimit;

import music.Note;
import music.NoteName;
import notification.LimitChangeNotifierImp;
import notification.LimitChangeObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteModifierTest implements LimitChangeObserver {
    private LimitChangeNotifier limitChangeNotifier;
    private NoteModifierImp noteLimit;
    private final Note note = new Note(NoteName.C, 4);
    Boolean notificationFired;

    @Override
    public void rangeChanged() {
        notificationFired = true;
    }

    @BeforeEach
    void setup(){
        limitChangeNotifier = new LimitChangeNotifierImp();
        noteLimit = new NoteModifierImp(note, limitChangeNotifier);

        limitChangeNotifier.addObserver(this);
        notificationFired = false;
    }

    @Test
    void canCheckEquality() {
        NoteModifierImp modelToCompare = new NoteModifierImp(note, new LimitChangeNotifierImp());
        assertEquals(modelToCompare, noteLimit);

        modelToCompare = new NoteModifierImp(new Note(NoteName.A, 2), new LimitChangeNotifierImp());
        assertNotEquals(modelToCompare, noteLimit);
    }

    @Test
    void canCompareToNote(){
        Note compareLow = new Note(NoteName.B, 3);
        assertTrue(noteLimit.compareTo(compareLow) > 0);

        Note compareHigh = new Note(NoteName.D, 4);
        assertTrue(noteLimit.compareTo(compareHigh) < 0);

        Note compareEquals = new Note(NoteName.C, 4);
        assertEquals(noteLimit.compareTo(compareEquals), 0);
    }

    @Test
    void canDisplayAsString(){
        String compareString = "active: C4";
        assertEquals(compareString, noteLimit.toString());
    }

    @Test
    void willSetNewLimit(){
        Note newNote = new Note(NoteName.C, 3);
        noteLimit.setLimit(newNote);
        NoteModifierImp modelToCompare = new NoteModifierImp(newNote, new LimitChangeNotifierImp());
        assertEquals(modelToCompare, noteLimit);
        assertTrue(notificationFired);
    }

    @Test
    void wontSetSameLimit(){
        Note newNote = new Note(NoteName.C, 4);
        noteLimit.setLimit(newNote);
        NoteModifierImp modelToCompare = new NoteModifierImp(newNote, new LimitChangeNotifierImp());
        assertEquals(modelToCompare, noteLimit);
        assertFalse(notificationFired);
    }

    @Test
    void canDecrement(){
        Note newNote = new Note(NoteName.B, 3);
        NoteModifierImp modelToCompare = new NoteModifierImp(newNote, new LimitChangeNotifierImp());
        noteLimit.decrement();
        assertEquals(modelToCompare, noteLimit);
        assertTrue(notificationFired);
    }

    @Test
    void canIncrement(){
        Note newNote = new Note(NoteName.D, 4);
        NoteModifierImp modelToCompare = new NoteModifierImp(newNote, new LimitChangeNotifierImp());
        noteLimit.increment();
        assertEquals(modelToCompare, noteLimit);
        assertTrue(notificationFired);
    }

    @Test
    void canGetLineNumber(){
        assertEquals(28, noteLimit.getLineNumber());
        noteLimit.setLimit(new Note(NoteName.B, 2));
        assertEquals(36, noteLimit.getLineNumber());
    }
}