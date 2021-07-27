package statemodels;

import instrument.key.Key;
import music.note.Note;
import music.note.NoteAccidental;
import tuples.NoteSetImp;
import music.note.NoteName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.NoteSet;

import static org.junit.jupiter.api.Assertions.*;

class KeyboardStateImpTest {
    KeyboardStateImp keyboardState = new KeyboardStateImp();
    Key keyB3 = new Key (59);
    Key keyC4 = new Key(60);
    Key keyCSharp4 = new Key(61);
    Key keyE4 = new Key(64);
    Key keyF4 = new Key(65);
    Key keyGSharp2 = new Key(44);
    Key keyDSharp7 = new Key(99);

    Note noteB3 = new Note(NoteName.B, 3);
    Note noteC4 = new Note(NoteName.C, 4);
    Note noteE4 = new Note(NoteName.E, 4);
    Note noteF4 = new Note(NoteName.F, 4);
    Note noteCSharp4 = new Note(NoteName.C, 4, NoteAccidental.SHARP);
    Note noteGSharp2;
    Note noteDSharp7;
    NoteSetImp expected;

    @BeforeEach
    void setup(){
        noteGSharp2 = new Note(NoteName.G, 2, NoteAccidental.SHARP);
        noteDSharp7 = new Note(NoteName.D, 7, NoteAccidental.SHARP);

        expected = new NoteSetImp();
    }

    @Test
    void checkEquals(){
        KeyboardStateImp compare = new KeyboardStateImp();

        keyboardState.keyPressed(keyC4);
        compare.keyPressed(keyC4);
        assertEquals(keyboardState, compare);

        keyboardState.keyReleased(keyC4);
        assertNotEquals(keyboardState, compare);
    }

    @Test
    void canDisplayAsString(){
        keyboardState.keyPressed(keyC4);
        keyboardState.keyPressed(keyCSharp4);
        keyboardState.keyPressed(keyE4);

        String expected = "[C4, C#4, E4]";
        assertEquals(expected, keyboardState.toString());
    }

    @Test
    void canConvertToNotes(){
        keyboardState.keyPressed(keyC4);
        keyboardState.keyPressed(keyCSharp4);
        keyboardState.keyPressed(keyE4);

        NoteSet expected = new NoteSetImp();
        expected.add(noteC4);
        expected.add(noteCSharp4);
        expected.add(noteE4);

        assertEquals(expected, keyboardState.convertToNotes());

    }
}