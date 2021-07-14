package statemodels;

import collections.NoteSetImp;
import music.Note;
import music.NoteName;
import notification.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.limitstate.LimitChangeNotifier;
import statemodels.limitstate.LimitStateImp;
import trainer.FlashcardChangeNotifier;
import trainer.FlashcardSatisfiedNotifier;
import trainer.RandomNoteGenerator;
import trainer.SightReadTrainerImp;
import trainer.randomnotegenerator.RandomNoteGeneratorImp;
import utility.NoteSet;

import static org.junit.jupiter.api.Assertions.*;

class FlashcardsImpTest implements FlashcardSatisfiedObserver, FlashcardChangeObserver {
    private LimitStateImp lowerLimit;
    private LimitStateImp upperLimit;
    private RandomNoteGenerator randomNoteGenerator;
    private FlashcardsImp flashcardsImp;
    private SightReadTrainerImp sightReadTrainer;

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