package uicomponents.renderer;

import music.Note;
import music.NoteName;
import org.junit.jupiter.api.Test;
import uicomponents.renderer.records.RenderConstants;

import static org.junit.jupiter.api.Assertions.*;

class RenderConstantsTest {
    private Note noteC4 = new Note(NoteName.C, 4);
    private Note noteD4 = new Note(NoteName.D, 4);
    private Note noteE4 = new Note(NoteName.E, 4);
    private Note noteF4 = new Note(NoteName.F, 4);
    private Note noteG4 = new Note(NoteName.G, 4);
    private Note noteA4 = new Note(NoteName.A, 4);
    private Note noteB4 = new Note(NoteName.B, 4);

    private Note actualNote;
    private Note expectedNote;

    @Test
    void getLineNumber() {
        assertEquals(28, RenderConstants.getLineNumber(noteC4));
    }

    @Test
    void getNoteFromLineNumber(){
        actualNote = RenderConstants.getNote(28);
        assertEquals(noteC4, actualNote);

        actualNote = RenderConstants.getNote(40);
        expectedNote = new Note(NoteName.E, 2);
        assertEquals(expectedNote, actualNote);

        actualNote = RenderConstants.getNote(8);
        expectedNote = new Note(NoteName.B, 6);
        assertEquals(expectedNote, actualNote);
    }
}