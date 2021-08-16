package instrument.key;

import instrument.key.Key;
import music.note.NoteAccidental;
import music.note.NoteName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyTest {
    Key keyDSharp2 = new Key(39);
    Key keyC4 = new Key(60);
    Key keyG4 = new Key(67);
    Key keyGSharp4 = new Key(68);
    Key keyA4 = new Key(69);
    Key keyGSharp5 = new Key(80);

    @Test
    void canEquals() {
        Key compare = new Key(60);
        assertEquals(keyC4, compare);
        assertNotEquals(keyDSharp2, compare);
    }

    @Test
    void getOctaves() {
        assertEquals(2, keyDSharp2.getOctave());
        assertEquals(4, keyC4.getOctave());
        assertEquals(4, keyA4.getOctave());
        assertEquals(5, keyGSharp5.getOctave());
    }

    @Test
    void getOctaveKeys() {
        assertEquals(0, keyC4.getOctavePosition());
        assertEquals(3, keyDSharp2.getOctavePosition());
        assertEquals(8, keyGSharp5.getOctavePosition());
        assertEquals(9, keyA4.getOctavePosition());
    }

    @Test
    void getNaturalIndexes() {
        assertEquals(NoteName.C, keyC4.getNoteName());
        assertEquals(NoteName.D, keyDSharp2.getNoteName());
        assertEquals(NoteName.G, keyGSharp5.getNoteName());
        assertEquals(NoteName.A, keyA4.getNoteName());
    }

    @Test
    void getAccidentals() {
        assertEquals(NoteAccidental.SHARP, keyDSharp2.getAccidental());
        assertEquals(NoteAccidental.NATURAL, keyC4.getAccidental());
        assertEquals(NoteAccidental.SHARP, keyGSharp4.getAccidental());
        assertEquals(NoteAccidental.NATURAL, keyG4.getAccidental());
    }

    @Test
    void isCDorEChecks() {
        assertTrue(keyC4.isCDorE());
        assertTrue(keyDSharp2.isCDorE());
        assertFalse(keyGSharp5.isCDorE());
        assertFalse(keyA4.isCDorE());
    }

    @Test
    void isEvenChecks() {
        assertTrue(keyC4.isEven());
        assertFalse(keyDSharp2.isEven());
        assertTrue(keyGSharp5.isEven());
        assertFalse(keyA4.isEven());
    }
}