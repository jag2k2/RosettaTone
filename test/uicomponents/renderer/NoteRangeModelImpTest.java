package uicomponents.renderer;

import music.Note;
import music.NoteName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.NoteRangeModelImp;

import static org.junit.jupiter.api.Assertions.*;

class NoteRangeModelImpTest {
    private NoteRangeModelImp noteRangeModel;
    private Note noteC4;
    private Note noteA4;
    private Note noteB4;

    @BeforeEach
    void setup(){
        noteC4 = new Note(NoteName.C, 4);
        noteA4 = new Note(NoteName.A, 4);
        noteB4 = new Note(NoteName.B, 4);
        noteRangeModel = new NoteRangeModelImp(noteC4, noteA4);
    }

    @Test
    void testToString() {
        assertEquals("[C4, A4]", noteRangeModel.toString());
    }

    @Test
    void canCheckEquality() {
        NoteRangeModelImp modelToCompare = new NoteRangeModelImp(noteC4, noteA4);
        assertEquals(modelToCompare, noteRangeModel);
        assertEquals(noteRangeModel, modelToCompare);
        modelToCompare = new NoteRangeModelImp(noteC4, noteB4);
        assertNotEquals(modelToCompare, noteRangeModel);
    }
}