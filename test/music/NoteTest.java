package music;

import tuples.NoteSetImp;
import instrument.Key;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import utility.NoteSet;

class NoteTest {
    private Note noteB3;
    private Note noteC3;
    private Note noteC4;
    private Note noteD4;
    private Note noteE4;
    private Note noteESharp4;
    private Note noteF4;
    private Note noteG4;
    private Note noteA4;
    private Note noteB4;
    private Note noteC5;
    private Note noteD5;
    private Note noteCompare;

    @BeforeEach
    void setup(){
        noteB3 = new Note(NoteName.B, 3);
        noteC4 = new Note(NoteName.C, 4);
        noteD4 = new Note(NoteName.D, 4);
        noteE4 = new Note(NoteName.E, 4);
        noteESharp4 = new Note(NoteName.E, 4, NoteAccidental.SHARP);
        noteF4 = new Note(NoteName.F, 4);
        noteA4 = new Note(NoteName.A, 4);
        noteB4 = new Note(NoteName.B, 4);
        noteC5 = new Note(NoteName.C, 5);
        noteD5 = new Note(NoteName.D, 5);

        Key key48 = new Key(48);
        noteC3 = key48.convertToNote();
    }

    @Test
    void canConstructFromKey(){
        Key key60 = new Key(60);
        noteCompare = key60.convertToNote();
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
    void canGetMidiNumber(){
        assertEquals(60, noteC4.getMidiNumber());
        assertEquals(65, noteF4.getMidiNumber());
        assertEquals(65, noteESharp4.getMidiNumber());
    }

    @Test
    void getLineNumber() {
        assertEquals(new Line(28), noteC4.getCenterLine());
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
    void canCheckSqueezed() {
        NoteSet notes = new NoteSetImp();
        notes.add(noteC4);
        notes.add(noteD4);
        notes.add(noteE4);
        notes.add(noteF4);
        notes.add(noteA4);

        assertFalse(noteC4.isSqueezed(notes));
        assertTrue(noteD4.isSqueezed(notes));
        assertFalse(noteE4.isSqueezed(notes));
        assertTrue(noteF4.isSqueezed(notes));
        assertFalse(noteA4.isSqueezed(notes));
    }
}