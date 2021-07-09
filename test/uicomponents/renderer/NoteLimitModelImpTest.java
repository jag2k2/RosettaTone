package uicomponents.renderer;

import music.Note;
import music.NoteName;
import notification.RangeChangeNotifierImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.NoteLimitModelImp;

import static org.junit.jupiter.api.Assertions.*;

class NoteLimitModelImpTest {
    private NoteLimitModelImp noteRangeModel;
    private Note noteC4;
    private Note noteB4;

    @BeforeEach
    void setup(){
        noteC4 = new Note(NoteName.C, 4);
        noteB4 = new Note(NoteName.B, 4);
        noteRangeModel = new NoteLimitModelImp(noteC4, new RangeChangeNotifierImp());
    }

    @Test
    void testToString() {
        assertEquals("C4", noteRangeModel.toString());
    }

    @Test
    void canCheckEquality() {
        NoteLimitModelImp modelToCompare = new NoteLimitModelImp(noteC4, new RangeChangeNotifierImp());
        assertEquals(modelToCompare, noteRangeModel);
        assertEquals(noteRangeModel, modelToCompare);
        modelToCompare = new NoteLimitModelImp(noteB4, new RangeChangeNotifierImp());
        assertNotEquals(modelToCompare, noteRangeModel);
    }
}