package music;

import instrument.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.KeyboardState;
import statemodels.KeyboardStateImp;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class KeyboardStateImpTest {
    KeyboardState keyboardState = new KeyboardStateImp();
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
    Note noteCnatSharp4;
    Note noteGSharp2;
    Note noteDSharp7;
    NoteCollectionImp expected;

    @BeforeEach
    void setup(){
        HashSet<NoteAccidental> accidentals = new HashSet<>();
        accidentals.add(NoteAccidental.NATURAL);
        accidentals.add(NoteAccidental.SHARP);
        noteCnatSharp4 = new Note(NoteName.C, 4, accidentals);
        noteGSharp2 = new Note(NoteName.G, 2, NoteAccidental.SHARP);
        noteDSharp7 = new Note(NoteName.D, 7, NoteAccidental.SHARP);

        expected = new NoteCollectionImp();
    }

    @Test
    void changeNoteStates() {
        keyboardState.keyPressed(keyC4);
        keyboardState.keyPressed(keyCSharp4);
        keyboardState.keyPressed(keyGSharp2);
        keyboardState.keyPressed(keyDSharp7);

        expected.add(noteGSharp2);
        expected.add(noteCnatSharp4);
        expected.add(noteDSharp7);

        assertEquals(expected, keyboardState.getActiveNotes());
    }

    @Test
    void testBCHalfStepNaturals(){
        keyboardState.keyPressed(keyB3);
        keyboardState.keyPressed(keyC4);

        expected.add(noteB3);
        expected.add(noteC4);

        assertEquals(expected, keyboardState.getActiveNotes());
    }

    @Test
    void testEFHalfStepNaturals(){
        keyboardState.keyPressed(keyE4);
        keyboardState.keyPressed(keyF4);

        expected.add(noteE4);
        expected.add(noteF4);

        assertEquals(expected, keyboardState.getActiveNotes());
    }
}