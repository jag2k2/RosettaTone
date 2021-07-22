package tuples;

import music.Note;
import music.NoteAccidental;
import music.NoteName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.NoteSet;

import static org.junit.jupiter.api.Assertions.*;

class NoteSetImpTest {
    NoteSet activeNotes;
    NoteSet expected;
    Note noteCSharp4;
    Note noteD4;
    Note noteE4;
    Note noteF4;
    Note noteG4;
    Note noteA4;

    @BeforeEach
    void setup(){
        noteCSharp4 = new Note(NoteName.C, 4, NoteAccidental.SHARP);
        noteD4 = new Note(NoteName.D, 4);
        noteE4 = new Note(NoteName.E, 4);
        noteF4 = new Note(NoteName.F, 4);
        noteG4 = new Note(NoteName.G, 4);
        noteA4 = new Note(NoteName.A, 4);

        activeNotes = new NoteSetImp();
        activeNotes.add(noteCSharp4);
        activeNotes.add(noteD4);
        activeNotes.add(noteE4);
        activeNotes.add(noteF4);
        activeNotes.add(noteA4);

        expected = new NoteSetImp();
        expected.add(noteCSharp4);
        expected.add(noteD4);
        expected.add(noteE4);
        expected.add(noteF4);
        expected.add(noteA4);
    }

    @Test
    void canCheckEquality(){
        assertEquals(expected, activeNotes);

        activeNotes.add(noteG4);
        assertNotEquals(expected, activeNotes);

        expected.add(noteG4);
        assertEquals(expected, activeNotes);
    }

    @Test
    void canDisplayAsString(){
        String expected = "[C#4, D4, E4, F4, A4]";
        assertEquals(expected, activeNotes.toString());
    }

    @Test
    void wontAddDuplicate(){
        Note otherNoteD4 = new Note(NoteName.D, 4);
        activeNotes.add(otherNoteD4);
        assertEquals(expected, activeNotes);
    }

    @Test
    void canCheckForContainsAll(){
        NoteSet checkList = new NoteSetImp();

        checkList.add(new Note(NoteName.C, 4, NoteAccidental.SHARP));
        assertTrue(activeNotes.containsAll(checkList));

        checkList.add(new Note(NoteName.E, 4));
        assertTrue(activeNotes.containsAll(checkList));

        checkList.add(new Note(NoteName.C, 2));
        assertFalse(activeNotes.containsAll(checkList));
    }
}