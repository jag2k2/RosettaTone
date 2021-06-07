package music;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class NoteTest {

    @Test
    void stringDisplay() {
        Note noteC4 = new Note(60, 0);
        String expected = "Note -> C4 key=60 velocity=0";
        assertEquals(expected, noteC4.toString());

        Note noteA0 = new Note(21, 0);
        expected = "Note -> A0 key=21 velocity=0";
        assertEquals(expected, noteA0.toString());

        Note noteG5Sharp = new Note(80, 0);
        expected = "Note -> G_Sharp5 key=80 velocity=0";
        assertEquals(expected, noteG5Sharp.toString());


    }
}