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
    Key keyC4 = new Key(60);
    Key keyCSharp4 = new Key(61);
    Key keyGSharp2 = new Key(44);
    Key keyDSharp7 = new Key(99);

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
        keyboardState.KeyPressed(keyC4);
        keyboardState.KeyPressed(keyCSharp4);
        keyboardState.KeyPressed(keyGSharp2);
        keyboardState.KeyPressed(keyDSharp7);

        expected.add(noteGSharp2);
        expected.add(noteCnatSharp4);
        expected.add(noteDSharp7);

        assertEquals(expected, keyboardState.getActiveNotes());
    }
}