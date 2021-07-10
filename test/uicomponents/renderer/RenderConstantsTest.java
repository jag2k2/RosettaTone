package uicomponents.renderer;

import music.Note;
import music.NoteName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @Test
    void canGenerateRandomKey(){
        int iterations = 10000;
        List<Note> octave4 = new ArrayList<>();
        octave4.add(noteC4);
        octave4.add(noteD4);
        octave4.add(noteE4);
        octave4.add(noteF4);
        octave4.add(noteG4);
        octave4.add(noteA4);
        octave4.add(noteB4);
        int intervalSize = octave4.size();

        HashMap<Note, Integer> histogram = new HashMap<>();

        Note lowerNote = noteC4;
        Note upperNote = noteB4;
        for (int i = 0; i < iterations; i++){
            Note randomNote = RenderConstants.getRandomNote(lowerNote, upperNote);
            if (octave4.contains(randomNote)){
                int noteIndex = octave4.indexOf(randomNote);
                int count = histogram.getOrDefault(octave4.get(noteIndex), 0);
                count++;
                histogram.put(octave4.get(noteIndex), count);
            }
        }

        assertEquals(intervalSize, histogram.size());

        for (Note note : octave4){
            int total = histogram.getOrDefault(note, 0);
            System.out.println(note + " -> " + total);
            int minimumCount = (int) ((float) iterations / intervalSize * 0.85);
            assertTrue(total > minimumCount);
        }
    }
}