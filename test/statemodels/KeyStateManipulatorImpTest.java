package statemodels;

import instrument.Key;
import music.Note;
import music.NoteAccidental;
import music.NoteCollectionImp;
import music.NoteName;
import notification.KeyboardChangeNotifierImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class KeyStateManipulatorImpTest {
    KeyStateManipulatorImp keyboardState = new KeyStateManipulatorImp(new KeyboardChangeNotifierImp());
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
    void checkEquals(){
        KeyStateManipulatorImp compare = new KeyStateManipulatorImp(new KeyboardChangeNotifierImp());

        keyboardState.keyPressed(keyC4);
        compare.keyPressed(keyC4);
        assertEquals(keyboardState, compare);

        keyboardState.keyReleased(keyC4);
        assertNotEquals(keyboardState, compare);
    }
}