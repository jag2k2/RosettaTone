package music;

import collections.NoteCollectionImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.NoteCollection;

import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

class NoteCollectionImpTest {
    NoteCollectionImp activeNotes;
    Note noteC4;
    Note noteD4;
    Note noteE4;
    Note noteF4;
    Note noteA4;
    HashSet<NoteAccidental> accidentals = new HashSet<>();

    @BeforeEach
    void setup(){

        accidentals.add(NoteAccidental.NATURAL);
        accidentals.add(NoteAccidental.SHARP);

        noteC4 = new Note(NoteName.C, 4, accidentals);
        noteD4 = new Note(NoteName.D, 4);
        noteE4 = new Note(NoteName.E, 4);
        noteF4 = new Note(NoteName.F, 4);
        noteA4 = new Note(NoteName.A, 4);

        activeNotes = new NoteCollectionImp();
        activeNotes.add(noteC4);
        activeNotes.add(noteD4);
        activeNotes.add(noteE4);
        activeNotes.add(noteF4);
        activeNotes.add(noteA4);
    }

    @Test
    void canCheckEquality(){
        NoteCollection collection1 = new NoteCollectionImp();
        NoteCollection collection2 = new NoteCollectionImp();
        assertEquals(collection1, collection2);

        collection1.add(noteD4);
        assertNotEquals(collection1, collection2);

        collection2.add(noteD4);
        assertEquals(collection1, collection2);

        collection1.add(noteE4);
        assertNotEquals(collection1, collection2);
    }

    @Test
    void wontAddDuplicate(){
        Note otherNoteD4 = new Note(NoteName.D, 4, NoteAccidental.SHARP);
        System.out.println(activeNotes);
        activeNotes.add(otherNoteD4);
        System.out.println(activeNotes);
    }

    @Test
    void canCheckForContains(){
        NoteCollection checkList = new NoteCollectionImp();
        checkList.add(new Note(NoteName.C, 4, accidentals));
        assertTrue(activeNotes.contains(checkList));
        checkList.add(new Note(NoteName.E, 4));
        assertTrue(activeNotes.contains(checkList));
        checkList.add(new Note(NoteName.C, 2));
        assertFalse(activeNotes.contains(checkList));
    }
}