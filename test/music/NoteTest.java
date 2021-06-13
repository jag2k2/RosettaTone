package music;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class NoteTest {
    Note noteB3;
    Note noteC4;
    Note noteD4;
    Note noteE4;
    Note noteB4;
    Note noteC5;
    Note noteCompare;

    @BeforeEach
    void setup(){
        noteB3 = new Note(NoteName.B, 3);
        noteC4 = new Note(NoteName.C, 4);
        noteC4.setAccidental(NoteAccidental.NATURAL, true);
        noteD4 = new Note(NoteName.D, 4);
        noteE4 = new Note(NoteName.E, 4);
        noteB4 = new Note(NoteName.B, 4);
        noteC5 = new Note(NoteName.C, 5);
    }

    @Test
    void equalsChecks(){
        noteCompare = new Note(NoteName.C, 5);
        noteCompare.setAccidental(NoteAccidental.NATURAL, true);
        assertNotEquals(noteC4, noteCompare);

        noteCompare = new Note(NoteName.C, 4);
        assertNotEquals(noteC4, noteCompare);

        noteCompare = new Note(NoteName.C, 4);
        noteCompare.setAccidental(NoteAccidental.NATURAL, true);
        assertEquals(noteC4, noteCompare);

        noteC4.setAccidental(NoteAccidental.SHARP, true);
        noteCompare.setAccidental(NoteAccidental.SHARP, true);
        assertEquals(noteC4, noteCompare);
    }

    @Test
    void canDetectAdjacent(){
        assertTrue(noteB3.isAdjacent(noteC4));
        assertTrue(noteC4.isAdjacent(noteB3));
        assertTrue(noteC4.isAdjacent(noteD4));
        assertTrue(noteD4.isAdjacent(noteC4));
        assertTrue(noteB4.isAdjacent(noteC5));
        assertTrue(noteC5.isAdjacent(noteB4));
    }

    @Test
    void canDetectNotAdjacent() {
        assertFalse(noteC4.isAdjacent(noteE4));
        assertFalse(noteC5.isAdjacent(noteD4));
        assertFalse(noteB3.isAdjacent(noteC5));
    }

}