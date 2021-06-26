package music;

import instrument.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.NoteState;
import statemodels.NoteStateImp;

import static org.junit.jupiter.api.Assertions.*;

class NoteStateImpTest {
    NoteState noteState = new NoteStateImp();
    Key keyC4 = new Key(60);
    Key keyCSharp4 = new Key(61);
    Key keyGSharp2 = new Key(44);
    Key keyDSharp7 = new Key(99);

    Note noteC4;
    Note noteG2;
    Note noteD7;
    NoteListImp expected;

    @BeforeEach
    void setup(){
        noteC4 = new Note(NoteName.C, 4);
        noteG2 = new Note(NoteName.G, 2);
        noteD7 = new Note(NoteName.D, 7);

        expected = new NoteListImp();
    }

    @Test
    void changeNoteStates() {
        noteState.NoteOn(keyC4);
        noteState.NoteOn(keyCSharp4);
        noteState.NoteOn(keyGSharp2);
        noteState.NoteOn(keyDSharp7);

        expected.add(noteG2);
        noteG2.setAccidental(NoteAccidental.SHARP, true);
        expected.add(noteC4);
        noteC4.setAccidental(NoteAccidental.NATURAL, true);
        noteC4.setAccidental(NoteAccidental.SHARP, true);
        expected.add(noteD7);
        noteD7.setAccidental(NoteAccidental.SHARP, true);

        assertEquals(expected, noteState.getActiveNotes());
    }
}