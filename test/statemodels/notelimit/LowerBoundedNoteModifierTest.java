package statemodels.notelimit;

import music.Note;
import music.NoteName;
import notification.LimitChangeNotifierImp;
import notification.LimitChangeObserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import uicomponents.rangeselector.noteselector.NoteModifier;

public class LowerBoundedNoteModifierTest implements LimitChangeObserver {

    private LowerBoundedNoteModifierImp boundedNoteLimit;
    private NoteModifier lowerNoteModifier;
    private LimitChangeNotifier limitChangeNotifier;
    private LimitChangeNotifier otherLimitChangeNotifier;
    private LimitChangeNotifier boundChangeNotifier;

    private NoteModifier otherLimit;

    private final Note lowerBoundNote = new Note(NoteName.C, 4);
    private final Note lowerLimitNote = new Note(NoteName.G, 4);
    private final Note upperBoundNote = new Note(NoteName.B, 4);

    private final Note otherLimitNote = new Note(NoteName.A, 4);

    private int notificationsFired;

    @Override
    public void rangeChanged() {
        notificationsFired++;
    }

    @BeforeEach
    void setup(){
        limitChangeNotifier = new LimitChangeNotifierImp();
        otherLimitChangeNotifier = new LimitChangeNotifierImp();
        boundChangeNotifier = new LimitChangeNotifierImp();
        lowerNoteModifier = new NoteModifierImp(lowerLimitNote, limitChangeNotifier);
        otherLimit = new NoteModifierImp(otherLimitNote, otherLimitChangeNotifier);
        boundedNoteLimit = new LowerBoundedNoteModifierImp(lowerNoteModifier, otherLimit, lowerBoundNote, upperBoundNote, boundChangeNotifier);

        limitChangeNotifier.addObserver(this);
        boundChangeNotifier.addObserver(this);
        otherLimitChangeNotifier.addObserver(boundedNoteLimit);

        notificationsFired = 0;
    }

    @Test
    void canMatchUpperBoundToOtherNoteLimit(){
        otherLimitChangeNotifier.notifyObservers();
        LowerBoundedNoteModifierImp expected = new LowerBoundedNoteModifierImp(lowerNoteModifier, otherLimit, lowerBoundNote, otherLimitNote, new LimitChangeNotifierImp());
        assertEquals(expected, boundedNoteLimit);
        assertEquals(notificationsFired, 1);
    }
}
