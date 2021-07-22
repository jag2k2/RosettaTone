package statemodels.limitstate;

import music.Note;
import music.NoteName;
import notification.LimitChangeNotifierImp;
import notification.LimitChangeObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LimitStateTest implements LimitChangeObserver {
    private LimitChangeNotifier limitChangeNotifier;
    private LimitStateImp noteLimit;
    private final Note note = new Note(NoteName.C, 4);
    Boolean notificationFired;

    @Override
    public void limitChanged() {
        notificationFired = true;
    }

    @BeforeEach
    void setup(){
        limitChangeNotifier = new LimitChangeNotifierImp();
        noteLimit = new LimitStateImp(note);
        noteLimit.addLimitChangeNotifier(limitChangeNotifier);

        limitChangeNotifier.addObserver(this);
        notificationFired = false;
    }

    @Test
    void canCheckEquality() {
        LimitStateImp modelToCompare = new LimitStateImp(note);
        assertEquals(modelToCompare, noteLimit);

        modelToCompare = new LimitStateImp(new Note(NoteName.A, 2));
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
        LimitStateImp modelToCompare = new LimitStateImp(newNote);
        assertEquals(modelToCompare, noteLimit);
        assertTrue(notificationFired);
    }

    @Test
    void canDecrement(){
        Note newNote = new Note(NoteName.B, 3);
        LimitStateImp modelToCompare = new LimitStateImp(newNote);
        noteLimit.decrement();
        assertEquals(modelToCompare, noteLimit);
        assertTrue(notificationFired);
    }

    @Test
    void canIncrement(){
        Note newNote = new Note(NoteName.D, 4);
        LimitStateImp modelToCompare = new LimitStateImp(newNote);
        noteLimit.increment();
        assertEquals(modelToCompare, noteLimit);
        assertTrue(notificationFired);
    }
}