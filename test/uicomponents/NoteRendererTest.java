package uicomponents;

import music.Note;
import music.NoteName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteRendererTest {

    @Test
    void calculateLineNumbers(){
        Note note = new Note(NoteName.C, 4);
        assertEquals(20, NoteRenderer.calcLineNumber(note));

        note = new Note(NoteName.F, 5);
        assertEquals(30, NoteRenderer.calcLineNumber(note));
    }
}