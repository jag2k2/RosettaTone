package com.jag2k2.statemodels;

import com.jag2k2.collections.NoteSetImp;
import com.jag2k2.music.Note;
import com.jag2k2.music.NoteName;
import com.jag2k2.notification.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.jag2k2.statemodels.limitstate.LimitChangeNotifier;
import com.jag2k2.statemodels.limitstate.LimitStateImp;
import com.jag2k2.trainer.FlashcardChangeNotifier;
import com.jag2k2.trainer.FlashcardSatisfiedNotifier;
import com.jag2k2.trainer.RandomNoteGenerator;
import com.jag2k2.trainer.randomnotegenerator.RandomNoteGeneratorImp;
import com.jag2k2.utility.NoteSet;

import static org.junit.jupiter.api.Assertions.*;

class FlashcardsImpTest implements FlashcardSatisfiedObserver, FlashcardChangeObserver {
    private LimitStateImp lowerLimit;
    private LimitStateImp upperLimit;
    private RandomNoteGenerator randomNoteGenerator;
    private FlashcardsImp flashcardsImp;

    private boolean flashcardSatisfied = false;
    private boolean flashcardChanged = false;

    @Override
    public void flashcardSatisfied() {
        flashcardSatisfied = true;
    }

    @Override
    public void flashcardChanged() {
        flashcardChanged = true;
    }

    @BeforeEach
    void setup(){
        LimitChangeNotifier lowerLimitChangeNotifier = new LimitChangeNotifierImp();
        LimitChangeNotifier upperLimitChangeNotifier = new LimitChangeNotifierImp();
        FlashcardSatisfiedNotifier flashcardSatisfiedNotifier = new FlashcardSatisfiedNotifierImp();
        FlashcardChangeNotifier flashcardChangeNotifier = new FlashcardChangeNotifierImp();

        lowerLimit = new LimitStateImp(new Note(NoteName.C, 4));
        lowerLimit.addLimitChangeNotifier(lowerLimitChangeNotifier);

        upperLimit = new LimitStateImp(new Note(NoteName.C, 5));
        upperLimit.addLimitChangeNotifier(upperLimitChangeNotifier);

        randomNoteGenerator = new RandomNoteGeneratorImp(lowerLimit, upperLimit);

        flashcardsImp = new FlashcardsImp(randomNoteGenerator);
        flashcardsImp.addFlashcardChangeNotifier(flashcardChangeNotifier);

        lowerLimitChangeNotifier.addObserver(flashcardsImp);
        upperLimitChangeNotifier.addObserver(flashcardsImp);
        flashcardSatisfiedNotifier.addObserver(this);
        flashcardChangeNotifier.addObserver(this);

        flashcardSatisfied = false;
        flashcardChanged = false;
    }

    @Test
    void generateNewFlashcardsWhenLimitChanges() {
        lowerLimit.setLimit(new Note(NoteName.B, 3));
        assertTrue(flashcardChanged);
    }

    @Test
    void peekAtTopFlashcard() {
        NoteSet expected = new NoteSetImp();
        Note outOfRangeNote = new Note(NoteName.E, 5);
        expected.add(outOfRangeNote);
        flashcardsImp.add(expected);

        for(NoteSet flashcard : flashcardsImp.peekAtTopFlashcard()) {
            assertEquals(expected, flashcard);
        }
    }

    @Test
    void canRemoveTopFlashcardFromEmpty() {
        flashcardsImp.removeTopFlashcard();
    }

    @Test
    void canRemoveTopFlashcard() {
        NoteSet flashcard1 = new NoteSetImp();
        NoteSet flashcard2 = new NoteSetImp();

        flashcard1.add(new Note(NoteName.D, 4));
        flashcard2.add(new Note(NoteName.E, 4));
        flashcardsImp.add(flashcard1);
        flashcardsImp.add(flashcard2);

        for(NoteSet flashcard : flashcardsImp.peekAtTopFlashcard())
            assertEquals(flashcard1, flashcard);

        flashcardsImp.removeTopFlashcard();
        for(NoteSet flashcard : flashcardsImp.peekAtTopFlashcard())
            assertEquals(flashcard2, flashcard);
    }

    @Test
    void canAddNewGeneratedFlashcard() {
        flashcardsImp.addNewGeneratedFlashcard();
        assertEquals(1, flashcardsImp.size());
        assertTrue(flashcardChanged);
    }

    @Test
    void generateAllNewFlashcards() {
        int flashcardCount = 5;
        flashcardsImp.generateAllNewFlashcards(flashcardCount);
        assertEquals(flashcardCount, flashcardsImp.size());
        assertTrue(flashcardChanged);
    }
}