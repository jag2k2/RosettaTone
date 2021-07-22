package statemodels.limitstate;

import music.note.Note;
import music.note.NoteName;
import notification.LimitChangeNotifierImp;
import notification.LimitChangeObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpperBoundedLimitStateTest implements LimitChangeObserver {

    private UpperBoundedLimitStateImp boundedNoteLimit;
    private LimitStateImp upperNoteLimit;
    private LimitChangeNotifier limitChangeNotifier;
    private LimitChangeNotifier otherLimitChangeNotifier;
    private LimitChangeNotifier boundChangeNotifier;

    private LimitStateImp otherLimit;

    private final Note lowerBoundNote = new Note(NoteName.C, 4);
    private final Note lowerLimitNote = new Note(NoteName.G, 4);
    private final Note upperBoundNote = new Note(NoteName.B, 4);

    private final Note otherLimitNote = new Note(NoteName.D, 4);

    private int notificationsFired;

    @Override
    public void limitChanged() {
        notificationsFired++;
    }

    @BeforeEach
    void setup(){
        limitChangeNotifier = new LimitChangeNotifierImp();
        otherLimitChangeNotifier = new LimitChangeNotifierImp();
        boundChangeNotifier = new LimitChangeNotifierImp();
        upperNoteLimit = new LimitStateImp(lowerLimitNote);
        upperNoteLimit.addLimitChangeNotifier(limitChangeNotifier);
        otherLimit = new LimitStateImp(otherLimitNote);
        otherLimit.addLimitChangeNotifier(otherLimitChangeNotifier);
        boundedNoteLimit = new UpperBoundedLimitStateImp(upperNoteLimit, otherLimit, lowerBoundNote, upperBoundNote);
        boundedNoteLimit.addBoundChangeNotifier(boundChangeNotifier);

        limitChangeNotifier.addObserver(this);
        boundChangeNotifier.addObserver(this);
        otherLimitChangeNotifier.addObserver(boundedNoteLimit);

        notificationsFired = 0;
    }

    @Test
    void canMatchUpperBoundToOtherNoteLimit(){
        otherLimitChangeNotifier.notifyObservers();
        UpperBoundedLimitStateImp expected = new UpperBoundedLimitStateImp(upperNoteLimit, otherLimit, otherLimitNote, upperBoundNote);
        assertEquals(expected, boundedNoteLimit);
        assertEquals(notificationsFired, 1);
    }
}
