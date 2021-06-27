package uicomponents.renderer;

import music.Note;
import music.NoteName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.NoteLimitModelImp;

import static org.junit.jupiter.api.Assertions.*;

class NoteLimitModelImpTest {
    private NoteLimitModelImp noteRangeModel;
    private Note noteC4;
    private Note noteA4;
    private Note noteB4;

    @BeforeEach
    void setup(){
        noteC4 = new Note(NoteName.C, 4);
        noteA4 = new Note(NoteName.A, 4);
        noteB4 = new Note(NoteName.B, 4);
        noteRangeModel = new NoteLimitModelImp(noteC4);
    }

    @Test
    void testToString() {
        assertEquals("C4", noteRangeModel.toString());
    }

    @Test
    void canCheckEquality() {
        NoteLimitModelImp modelToCompare = new NoteLimitModelImp(noteC4);
        assertEquals(modelToCompare, noteRangeModel);
        assertEquals(noteRangeModel, modelToCompare);
        modelToCompare = new NoteLimitModelImp(noteB4);
        assertNotEquals(modelToCompare, noteRangeModel);
    }
}