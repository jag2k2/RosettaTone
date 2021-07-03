package music;

import instrument.Key;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class NoteTest {
    private Note noteB3;
    private Note noteC3;
    private Note noteC4;
    private Note noteD4;
    private Note noteE4;
    private Note noteB4;
    private Note noteC5;
    private Note noteCompare;

    @BeforeEach
    void setup(){
        noteB3 = new Note(NoteName.B, 3);
        noteC4 = new Note(NoteName.C, 4);
        noteC4.setAccidental(NoteAccidental.NATURAL, true);
        noteD4 = new Note(NoteName.D, 4);
        noteE4 = new Note(NoteName.E, 4);
        noteB4 = new Note(NoteName.B, 4);
        noteC5 = new Note(NoteName.C, 5);

        noteC3 = new Note(new Key(48));
        noteC3.setAccidental(NoteAccidental.SHARP, true);
    }

    @Test
    void canConstructFromKey(){
        noteCompare = new Note(new Key(60));
        assertEquals(noteCompare, noteC4);
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

        noteCompare = new Note(NoteName.C, 3);
        noteCompare.setAccidental(NoteAccidental.SHARP, true);
        assertEquals(noteC3, noteCompare);
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

    @Test
    void canIncrement(){
        Note noteTest = new Note(NoteName.C, 4);
        noteTest.increment();
        assertEquals(noteTest, noteD4);

        noteTest = new Note(NoteName.B, 4);
        noteTest.increment();
        assertEquals(noteTest, noteC5);
    }

    @Test
    void canDecrement(){
        Note noteTest = new Note(NoteName.C, 4);
        noteTest.decrement();
        assertEquals(noteTest, noteB3);

        noteTest = new Note(NoteName.E, 4);
        noteTest.decrement();
        assertEquals(noteTest, noteD4);
    }

    @Test
    void canCompare() {
        noteCompare = new Note(NoteName.C, 4);
        assertTrue(noteC4.compareTo(noteC3) > 0);
        assertTrue(noteC4.compareTo(noteCompare) == 0);
        assertTrue(noteC4.compareTo(noteC5) < 0);
    }

    @Test
    void canDisplayNoAccidentals(){
        assertEquals("C5", noteC5.toString());
    }

    @Test
    void canDisplayWithOnlyNatural(){
        noteC5.setAccidental(NoteAccidental.NATURAL, true);
        assertEquals("C5", noteC5.toString());
    }

    @Test
    void canDisplayOnlySharp(){
        noteC5.setAccidental(NoteAccidental.SHARP, true);
        assertEquals("C#5", noteC5.toString());
    }

    @Test
    void canDisplayOnlyFlat(){
        noteC5.setAccidental(NoteAccidental.FLAT, true);
        assertEquals("Cb5", noteC5.toString());
    }

    @Test
    void canDisplayNaturalAndSharp(){
        noteC5.setAccidental(NoteAccidental.NATURAL, true);
        noteC5.setAccidental(NoteAccidental.SHARP, true);
        assertEquals("Cnat#5", noteC5.toString());
    }

    @Test
    void canDisplayNaturalAndFlat(){
        noteC5.setAccidental(NoteAccidental.NATURAL, true);
        noteC5.setAccidental(NoteAccidental.FLAT, true);
        assertEquals("Cnatb5", noteC5.toString());
    }

    @Test
    void canDisplayAllAccidentals(){
        noteC5.setAccidental(NoteAccidental.NATURAL, true);
        noteC5.setAccidental(NoteAccidental.SHARP, true);
        noteC5.setAccidental(NoteAccidental.FLAT, true);
        assertEquals("Cnat#b5", noteC5.toString());
    }
}