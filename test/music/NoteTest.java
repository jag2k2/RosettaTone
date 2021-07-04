package music;

import instrument.Key;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;

class NoteTest {
    private Note noteB3;
    private Note noteC3;
    private Note noteC4;
    private Note noteD4;
    private Note noteESharp4;
    private Note noteB4;
    private Note noteC5;
    private Note noteD5;
    private Note noteCompare;
    private Set<NoteAccidental> naturalSharp;
    private Set<NoteAccidental> naturalFlat;
    private Set<NoteAccidental> naturalSharpFlat;

    @BeforeEach
    void setup(){
        this.naturalSharp = new HashSet<>();
        naturalSharp.add(NoteAccidental.NATURAL);
        naturalSharp.add(NoteAccidental.SHARP);

        this.naturalFlat = new HashSet<>();
        naturalFlat.add(NoteAccidental.NATURAL);
        naturalFlat.add(NoteAccidental.FLAT);

        this.naturalSharpFlat = new HashSet<>();
        naturalSharpFlat.add(NoteAccidental.NATURAL);
        naturalSharpFlat.add(NoteAccidental.SHARP);
        naturalSharpFlat.add(NoteAccidental.FLAT);

        noteB3 = new Note(NoteName.B, 3);
        noteC4 = new Note(NoteName.C, 4);
        noteD4 = new Note(NoteName.D, 4);
        noteESharp4 = new Note(NoteName.E, 4, NoteAccidental.SHARP);
        noteB4 = new Note(NoteName.B, 4);
        noteC5 = new Note(NoteName.C, 5);
        noteD5 = new Note(NoteName.D, 5);

        Key key48 = new Key(48);
        noteC3 = key48.getNote();
    }

    @Test
    void canConstructFromKey(){
        Key key60 = new Key(60);
        noteCompare = key60.getNote();
        assertEquals(noteCompare, noteC4);
    }

    @Test
    void equalsChecks(){
        noteCompare = new Note(NoteName.C, 5);
        assertNotEquals(noteC4, noteCompare);

        noteCompare = new Note(NoteName.C, 4);
        assertEquals(noteC4, noteCompare);

        noteCompare = new Note(NoteName.E, 4, NoteAccidental.SHARP);
        assertEquals(noteESharp4, noteCompare);

        noteCompare = new Note(NoteName.C, 3);
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
        assertFalse(noteC4.isAdjacent(noteESharp4));
        assertFalse(noteC5.isAdjacent(noteD4));
        assertFalse(noteB3.isAdjacent(noteC5));
    }

    @Test
    void canIncrement(){
        Note noteTest = noteC4.getNext(NoteAccidental.NATURAL);
        assertEquals(noteTest, noteD4);

        noteTest = noteB4.getNext(NoteAccidental.NATURAL);
        assertEquals(noteTest, noteC5);
    }

    @Test
    void canDecrement(){
        Note noteTest = noteC4.getPrevious(NoteAccidental.NATURAL);
        assertEquals(noteTest, noteB3);

        noteTest = noteESharp4.getPrevious(NoteAccidental.NATURAL);
        assertEquals(noteTest, noteD4);
    }

    @Test
    void canCompare() {
        noteCompare = new Note(NoteName.C, 4);
        assertTrue(noteC4.compareTo(noteC3) > 0);
        assertEquals(noteC4.compareTo(noteCompare), 0);
        assertTrue(noteC4.compareTo(noteC5) < 0);
    }

    @Test
    void canGenerateHashCode(){
        Note newNote = new Note(NoteName.C, 4, NoteAccidental.SHARP);
        assertEquals(noteC4.hashCode(), newNote.hashCode());
    }

    @Test
    void canDisplayWithOnlyNatural(){
        assertEquals("C5", noteC5.toString());
    }

    @Test
    void canDisplayOnlySharp(){
        Note noteCSharp5 = new Note(NoteName.C, 5, NoteAccidental.SHARP);
        assertEquals("C#5", noteCSharp5.toString());
    }

    @Test
    void canDisplayOnlyFlat(){
        Note noteCFlat5 = new Note(NoteName.C, 5, NoteAccidental.FLAT);
        assertEquals("Cb5", noteCFlat5.toString());
    }

    @Test
    void canDisplayNaturalAndSharp(){
        Note noteCNatSharp5 = new Note(NoteName.C, 5, naturalSharp);
        assertEquals("Cn#5", noteCNatSharp5.toString());
    }

    @Test
    void canDisplayNaturalAndFlat(){
        Note noteCNatFlat5 = new Note(NoteName.C, 5, naturalFlat);
        assertEquals("Cnb5", noteCNatFlat5.toString());
    }

    @Test
    void canDisplayAllAccidentals(){
        Note noteCNatSharpFlat5 = new Note(NoteName.C, 5, naturalSharpFlat);
        assertEquals("Cn#b5", noteCNatSharpFlat5.toString());
    }
}