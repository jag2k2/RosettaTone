package music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KeyboardImpTest {
    Keyboard keyboard = new KeyboardImp();
    Key keyC4 = new Key(60);
    Key keyCSharp4 = new Key(61);
    Key keyGSharp2 = new Key(44);
    Key keyDSharp7 = new Key(99);

    Note noteC4;
    Note noteG2;
    Note noteD7;
    List<Note> expected;

    @BeforeEach
    void setup(){
        noteC4 = new Note(NoteName.C, 4);
        noteG2 = new Note(NoteName.G, 2);
        noteD7 = new Note(NoteName.D, 7);

        expected = new ArrayList<>();
    }

    @Test
    void changeNoteStates() {
        keyboard.pressKey(keyC4);
        keyboard.pressKey(keyCSharp4);
        keyboard.pressKey(keyGSharp2);
        keyboard.pressKey(keyDSharp7);

        expected.add(noteG2);
        noteG2.setAccidental(NoteAccidental.SHARP, true);
        expected.add(noteC4);
        noteC4.setAccidental(NoteAccidental.NATURAL, true);
        noteC4.setAccidental(NoteAccidental.SHARP, true);
        expected.add(noteD7);
        noteD7.setAccidental(NoteAccidental.SHARP, true);

        System.out.println(keyboard.getPressedNotes());
        assertEquals(expected, keyboard.getPressedNotes());
    }
}