package uicomponents.util;

import music.note.Note;
import music.note.NoteName;
import notification.LimitChangeNotifierImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.limitstate.AbstractBoundedLimit;
import statemodels.limitstate.LimitChangeNotifier;
import statemodels.limitstate.LimitStateImp;
import statemodels.limitstate.UpperBoundedLimitStateImp;
import uicomponents.LimitState;
import uicomponents.util.selectors.JComboSelector;
import static org.junit.jupiter.api.Assertions.*;

class JSelectorTest {
    private JSelector<Note> selector;
    private LimitState noteLimit;
    private LimitState otherLimit;
    private AbstractBoundedLimit boundedNoteLimit;

    @BeforeEach
    void setup(){
        LimitChangeNotifier limitChangeNotifier = new LimitChangeNotifierImp();

        Note lowerBoundNote = new Note(NoteName.A, 3);
        Note defaultLowerLimitNote = new Note(NoteName.B, 3);
        Note defaultUpperLimitNote = new Note(NoteName.D, 4);
        noteLimit = new LimitStateImp(defaultLowerLimitNote);

        otherLimit = new LimitStateImp(defaultUpperLimitNote);
        otherLimit.addLimitChangeNotifier(limitChangeNotifier);

        boundedNoteLimit = new UpperBoundedLimitStateImp(lowerBoundNote, noteLimit, defaultUpperLimitNote);
        boundedNoteLimit.setOtherLimit(otherLimit);
        limitChangeNotifier.addObserver(boundedNoteLimit);

        selector = new JComboSelector<>(boundedNoteLimit);
    }

    @Test
    void testToString() {
        selector.refreshSelections();
        String expected = "[lower: A3, limit: B3, upper: D4], [D4, C4, *B3, A3]";
        assertEquals(expected, selector.toString());
    }

    @Test
    void canCheckEquals() {
        JSelector<Note> expected = new JComboSelector<>(boundedNoteLimit);
        assertEquals(expected, selector);

        selector.addItem(new Note(NoteName.D, 4));
        selector.addItem(new Note(NoteName.C, 4));
        selector.addItem(new Note(NoteName.B, 3));
        selector.addItem(new Note(NoteName.A, 3));
        selector.setSelectedItem(new Note(NoteName.B, 3));

        expected.addItem(new Note(NoteName.D, 4));
        expected.addItem(new Note(NoteName.C, 4));
        expected.addItem(new Note(NoteName.B, 3));
        expected.addItem(new Note(NoteName.A, 3));
        expected.setSelectedItem(new Note(NoteName.B, 3));

        assertEquals(expected, selector);

        expected.setSelectedItem(new Note(NoteName.C, 4));
        assertNotEquals(expected, selector);
    }

    @Test
    void canRefreshSelections() {
        JSelector<Note> expected = new JComboSelector<>(boundedNoteLimit);
        expected.addItem(new Note(NoteName.D, 4));
        expected.addItem(new Note(NoteName.C, 4));
        expected.addItem(new Note(NoteName.B, 3));
        expected.addItem(new Note(NoteName.A, 3));
        expected.setSelectedItem(new Note(NoteName.B, 3));

        selector.refreshSelections();
        assertEquals(expected, selector);

        otherLimit.update(new Note(NoteName.F, 3));
        selector.refreshSelections();
        expected.addItem(new Note(NoteName.G, 3));
        expected.addItem(new Note(NoteName.F, 3));
        assertEquals(expected, selector);
    }

    @Test
    void canChangeState(){
        selector.refreshSelections();
        selector.setSelectedItem(new Note(NoteName.C,4));

        LimitState expected = new LimitStateImp(new Note(NoteName.C, 4));
        assertEquals(expected, noteLimit);
    }
}