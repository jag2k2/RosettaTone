package music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteCollectionImpTest {
    NoteCollectionImp activeNotes;
    Note noteC4;
    Note noteD4;
    Note noteE4;
    Note noteF4;
    Note noteA4;

    @BeforeEach
    void setup(){
        noteC4 = new Note(NoteName.C, 4, NoteAccidental.NATURAL);
        noteD4 = new Note(NoteName.D, 4, NoteAccidental.NATURAL);
        noteE4 = new Note(NoteName.E, 4, NoteAccidental.NATURAL);
        noteF4 = new Note(NoteName.F, 4, NoteAccidental.NATURAL);
        noteA4 = new Note(NoteName.A, 4, NoteAccidental.NATURAL);

        activeNotes = new NoteCollectionImp();
        activeNotes.add(noteC4);
        activeNotes.add(noteD4);
        activeNotes.add(noteE4);
        activeNotes.add(noteF4);
        activeNotes.add(noteA4);
    }

    @Test
    void canCheckSqueezed() {
        assertFalse(activeNotes.isSqueezed(noteC4));
        assertTrue(activeNotes.isSqueezed(noteD4));
        assertFalse(activeNotes.isSqueezed(noteE4));
        assertTrue(activeNotes.isSqueezed(noteF4));
        assertFalse(activeNotes.isSqueezed(noteA4));
    }

    @Test
    void canCheckForContains(){
        NoteCollection checkList = new NoteCollectionImp();
        checkList.add(new Note(NoteName.C, 4, NoteAccidental.NATURAL));
        assertTrue(activeNotes.contains(checkList));
        checkList.add(new Note(NoteName.E, 4, NoteAccidental.NATURAL));
        assertTrue(activeNotes.contains(checkList));
        checkList.add(new Note(NoteName.C, 2, NoteAccidental.NATURAL));
        assertFalse(activeNotes.contains(checkList));
    }
}