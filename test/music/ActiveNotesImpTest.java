package music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.Maybe;

import static org.junit.jupiter.api.Assertions.*;

class ActiveNotesImpTest {
    ActiveNotesImp activeNotes;
    Note noteC4;
    Note noteD4;
    Note noteE4;
    Note noteF4;
    Note noteA4;

    @BeforeEach
    void setup(){
        noteC4 = new Note(NoteName.C, 4, NoteClef.Treble);
        noteD4 = new Note(NoteName.D, 4, NoteClef.Treble);
        noteE4 = new Note(NoteName.E, 4, NoteClef.Treble);
        noteF4 = new Note(NoteName.F, 4, NoteClef.Treble);
        noteA4 = new Note(NoteName.A, 4, NoteClef.Treble);

        activeNotes = new ActiveNotesImp();
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
        assertFalse(activeNotes.isShifted(noteC4));
        assertTrue(activeNotes.isShifted(noteD4));
        assertFalse(activeNotes.isShifted(noteE4));
        assertTrue(activeNotes.isShifted(noteF4));
        assertFalse(activeNotes.isShifted(noteA4));
    }
}