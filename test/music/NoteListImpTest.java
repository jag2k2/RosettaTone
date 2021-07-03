package music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.Maybe;

import static org.junit.jupiter.api.Assertions.*;

class NoteListImpTest {
    NoteListImp activeNotes;
    Note noteC4;
    Note noteD4;
    Note noteE4;
    Note noteF4;
    Note noteA4;

    @BeforeEach
    void setup(){
        noteC4 = new Note(NoteName.C, 4);
        noteD4 = new Note(NoteName.D, 4);
        noteE4 = new Note(NoteName.E, 4);
        noteF4 = new Note(NoteName.F, 4);
        noteA4 = new Note(NoteName.A, 4);

        activeNotes = new NoteListImp();
        activeNotes.add(noteC4);
        activeNotes.add(noteD4);
        activeNotes.add(noteE4);
        activeNotes.add(noteF4);
        activeNotes.add(noteA4);
    }

    @Test
    void canGetPrevious() {
        Maybe<Note> expectedNote = new Maybe<>(noteC4);
        assertEquals(expectedNote, activeNotes.getPrevious(noteD4));
    }

    @Test
    void canCheckShifted() {
        assertFalse(activeNotes.isSandwiched(noteC4));
        assertTrue(activeNotes.isSandwiched(noteD4));
        assertFalse(activeNotes.isSandwiched(noteE4));
        assertTrue(activeNotes.isSandwiched(noteF4));
        assertFalse(activeNotes.isSandwiched(noteA4));
    }

    @Test
    void canCheckForContains(){
        NoteList checkList = new NoteListImp();
        checkList.add(new Note(NoteName.C, 4));
        assertTrue(activeNotes.contains(checkList));
        checkList.add(new Note(NoteName.E, 4));
        assertTrue(activeNotes.contains(checkList));
        checkList.add(new Note(NoteName.C, 2));
        assertFalse(activeNotes.contains(checkList));
    }
}