package music;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class NoteTest {
    Note noteC4;
    Note noteCompare;

    @BeforeEach
    void setup(){
        noteC4 = new Note(NoteName.C, 4);
        noteC4.setAccidental(NoteAccidental.NATURAL, true);
    }

    @Test
    void equalsChecks(){
        noteCompare = new Note(NoteName.C, 5);
        noteCompare.setAccidental(NoteAccidental.NATURAL, true);
        assertNotEquals(noteC4, noteCompare);

        noteCompare = new Note(NoteName.C, 4);
        noteCompare.setAccidental(NoteAccidental.NATURAL, false);
        assertNotEquals(noteC4, noteCompare);

        noteCompare = new Note(NoteName.C, 4);
        noteCompare.setAccidental(NoteAccidental.NATURAL, true);
        assertEquals(noteC4, noteCompare);

        noteC4.setAccidental(NoteAccidental.SHARP, true);
        noteCompare.setAccidental(NoteAccidental.SHARP, true);
        assertEquals(noteC4, noteCompare);
    }

}