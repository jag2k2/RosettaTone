package uicomponents.renderer;

import music.Note;
import music.NoteName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanvasRenderTest {
    private Note noteC4;

    @BeforeEach
    void setup(){
        noteC4 = new Note(NoteName.C, 4);
    }

    @Test
    void getLineNumber() {
        assertEquals(28, CanvasRender.getLineNumber(noteC4));
    }
}