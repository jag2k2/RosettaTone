package com.jag2k2.trainer.randomnotegenerator;

import com.jag2k2.music.Note;
import com.jag2k2.music.NoteName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.jag2k2.statemodels.limitstate.LimitStateImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomNoteGeneratorImpTest {
    private RandomNoteGeneratorImp randomNoteGenerator;

    private Note noteC4 = new Note(NoteName.C, 4);
    private Note noteD4 = new Note(NoteName.D, 4);
    private Note noteE4 = new Note(NoteName.E, 4);
    private Note noteF4 = new Note(NoteName.F, 4);
    private Note noteG4 = new Note(NoteName.G, 4);
    private Note noteA4 = new Note(NoteName.A, 4);
    private Note noteB4 = new Note(NoteName.B, 4);

    @BeforeEach
    void setUp() {
        LineNumerable lowerLimit = new LimitStateImp(noteC4);
        LineNumerable upperLimit = new LimitStateImp(noteB4);
        randomNoteGenerator = new RandomNoteGeneratorImp(lowerLimit, upperLimit);
    }

    @Test
    void canGenerateRandomNote(){
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

        for (int i = 0; i < iterations; i++){
            Note randomNote = randomNoteGenerator.generateSingleNote();
            if (octave4.contains(randomNote)){
                int noteIndex = octave4.indexOf(randomNote);
                int count = histogram.getOrDefault(octave4.get(noteIndex), 0);
                count++;
                histogram.put(octave4.get(noteIndex), count);
            }
        }

        for (Note note : octave4){
            int total = histogram.getOrDefault(note, 0);
            System.out.println(note + " -> " + total);
            int minimumCount = (int) ((float) iterations / intervalSize * 0.85);
            assertTrue(total > minimumCount);
        }

        assertEquals(intervalSize, histogram.size());
    }

}