package com.jag2k2.statemodels.limitstate;

import com.jag2k2.music.Note;
import com.jag2k2.music.NoteName;
import com.jag2k2.notification.LimitChangeNotifierImp;
import com.jag2k2.notification.LimitChangeObserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LowerBoundedLimitStateTest implements LimitChangeObserver {

    private LowerBoundedLimitStateImp boundedNoteLimit;
    private LimitStateImp lowerNoteLimit;
    private LimitChangeNotifier limitChangeNotifier;
    private LimitChangeNotifier otherLimitChangeNotifier;
    private LimitChangeNotifier boundChangeNotifier;

    private LimitStateImp otherLimit;

    private final Note lowerBoundNote = new Note(NoteName.C, 4);
    private final Note lowerLimitNote = new Note(NoteName.G, 4);
    private final Note upperBoundNote = new Note(NoteName.B, 4);

    private final Note otherLimitNote = new Note(NoteName.A, 4);

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
        lowerNoteLimit = new LimitStateImp(lowerLimitNote);
        lowerNoteLimit.addLimitChangeNotifier(limitChangeNotifier);
        otherLimit = new LimitStateImp(otherLimitNote);
        otherLimit.addLimitChangeNotifier(otherLimitChangeNotifier);
        boundedNoteLimit = new LowerBoundedLimitStateImp(lowerNoteLimit, otherLimit, lowerBoundNote, upperBoundNote);
        boundedNoteLimit.addBoundChangeNotifier(boundChangeNotifier);

        limitChangeNotifier.addObserver(this);
        boundChangeNotifier.addObserver(this);
        otherLimitChangeNotifier.addObserver(boundedNoteLimit);

        notificationsFired = 0;
    }

    @Test
    void canMatchUpperBoundToOtherNoteLimit(){
        otherLimitChangeNotifier.notifyObservers();
        LowerBoundedLimitStateImp expected = new LowerBoundedLimitStateImp(lowerNoteLimit, otherLimit, lowerBoundNote, otherLimitNote);
        assertEquals(expected, boundedNoteLimit);
        assertEquals(notificationsFired, 1);
    }
}
