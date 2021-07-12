package notelimit;

import music.Note;
import music.NoteName;
import notification.LimitChangeNotifierImp;
import notification.LimitChangeObserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LowerBoundedNoteModifierTest implements LimitChangeObserver {

    private LowerBoundedNoteLimitImp boundedNoteLimit;
    private NoteLimitImp lowerNoteLimit;
    private LimitChangeNotifier limitChangeNotifier;
    private LimitChangeNotifier otherLimitChangeNotifier;
    private LimitChangeNotifier boundChangeNotifier;

    private NoteLimitImp otherLimit;

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
        lowerNoteLimit = new NoteLimitImp(lowerLimitNote);
        lowerNoteLimit.addLimitChangeNotifier(limitChangeNotifier);
        otherLimit = new NoteLimitImp(otherLimitNote);
        otherLimit.addLimitChangeNotifier(otherLimitChangeNotifier);
        boundedNoteLimit = new LowerBoundedNoteLimitImp(lowerNoteLimit, otherLimit, lowerBoundNote, upperBoundNote);
        boundedNoteLimit.addBoundChangeNotifier(boundChangeNotifier);

        limitChangeNotifier.addObserver(this);
        boundChangeNotifier.addObserver(this);
        otherLimitChangeNotifier.addObserver(boundedNoteLimit);

        notificationsFired = 0;
    }

    @Test
    void canMatchUpperBoundToOtherNoteLimit(){
        otherLimitChangeNotifier.notifyObservers();
        LowerBoundedNoteLimitImp expected = new LowerBoundedNoteLimitImp(lowerNoteLimit, otherLimit, lowerBoundNote, otherLimitNote);
        assertEquals(expected, boundedNoteLimit);
        assertEquals(notificationsFired, 1);
    }
}
