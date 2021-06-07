package music;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class NoteTest {

    @Test
    void displayC() {
        Note note = new Note(60);
        String expected = "Note: C4 key=60";
        assertEquals(expected, note.toString());
    }

    @Test
    void displayCSharp() {
        Note note = new Note(61);
        String expected = "Note: C#4 key=61";
        assertEquals(expected, note.toString());
    }

    @Test
    void displayD() {
        Note note = new Note(62);
        String expected = "Note: D4 key=62";
        assertEquals(expected, note.toString());
    }

    @Test
    void displayDSharp() {
        Note note = new Note(63);
        String expected = "Note: D#4 key=63";
        assertEquals(expected, note.toString());
    }

    @Test
    void displayE() {
        Note note = new Note(64);
        String expected = "Note: E4 key=64";
        assertEquals(expected, note.toString());
    }

    @Test
    void displayF() {
        Note note = new Note(65);
        String expected = "Note: F4 key=65";
        assertEquals(expected, note.toString());
    }

    @Test
    void displayFSharp() {
        Note note = new Note(66);
        String expected = "Note: F#4 key=66";
        assertEquals(expected, note.toString());
    }

    @Test
    void displayG() {
        Note note = new Note(67);
        String expected = "Note: G4 key=67";
        assertEquals(expected, note.toString());
    }

    @Test
    void displayGSharp() {
        Note note = new Note(68);
        String expected = "Note: G#4 key=68";
        assertEquals(expected, note.toString());
    }

    @Test
    void displayA() {
        Note note = new Note(69);
        String expected = "Note: A4 key=69";
        assertEquals(expected, note.toString());
    }

    @Test
    void displayASharp() {
        Note note = new Note(70);
        String expected = "Note: A#4 key=70";
        assertEquals(expected, note.toString());
    }

    @Test
    void displayB() {
        Note note = new Note(71);
        String expected = "Note: B4 key=71";
        assertEquals(expected, note.toString());
    }
}