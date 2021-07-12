package statemodels.notelimit;

import music.Note;
import music.NoteName;
import notification.LimitChangeNotifierImp;
import notification.LimitChangeObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uicomponents.rangeselector.noteselector.NoteModifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpperBoundedNoteModifierTest implements LimitChangeObserver {

    private UpperBoundedNoteModifierImp boundedNoteLimit;
    private NoteModifier lowerNoteModifier;
    private LimitChangeNotifier limitChangeNotifier;
    private LimitChangeNotifier otherLimitChangeNotifier;
    private LimitChangeNotifier boundChangeNotifier;

    private NoteModifier otherLimit;

    private final Note lowerBoundNote = new Note(NoteName.C, 4);
    private final Note lowerLimitNote = new Note(NoteName.G, 4);
    private final Note upperBoundNote = new Note(NoteName.B, 4);

    private final Note otherLimitNote = new Note(NoteName.D, 4);

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
        boundedNoteLimit = new UpperBoundedNoteModifierImp(lowerNoteModifier, otherLimit, lowerBoundNote, upperBoundNote, boundChangeNotifier);

        limitChangeNotifier.addObserver(this);
        boundChangeNotifier.addObserver(this);
        otherLimitChangeNotifier.addObserver(boundedNoteLimit);

        notificationsFired = 0;
    }

    @Test
    void canMatchUpperBoundToOtherNoteLimit(){
        otherLimitChangeNotifier.notifyObservers();
        UpperBoundedNoteModifierImp expected = new UpperBoundedNoteModifierImp(lowerNoteModifier, otherLimit, otherLimitNote, upperBoundNote, new LimitChangeNotifierImp());
        assertEquals(expected, boundedNoteLimit);
        assertEquals(notificationsFired, 1);
    }
}
