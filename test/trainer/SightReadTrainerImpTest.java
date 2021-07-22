package trainer;

import instrument.Key;
import music.Note;
import music.NoteName;
import notification.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.*;
import statemodels.limitstate.LimitStateImp;
import trainer.randomnotegenerator.BoundedNoteGenerator;
import trainer.randomnotegenerator.NoteGeneratorImp;
import uicomponents.notenamemode.NoteNameMode;
import utility.NoteSet;

import static org.junit.jupiter.api.Assertions.*;

class SightReadTrainerImpTest implements FlashcardSatisfiedObserver, FlashcardChangeObserver {
    private NoteNameModeStateImp noteNameMode;
    private KeyboardStateImp keyboardState;
    private BoundedNoteGenerator lowerLimit;
    private BoundedNoteGenerator upperLimit;
    private RandomNoteGenerator randomNoteGenerator;
    private FlashcardsImp flashcardsImp;
    private SightReadTrainerImp sightReadTrainer;

    private boolean satisfiedNotified;
    private boolean flashcardChangedNotified;

    @Override
    public void flashcardSatisfied() {
        satisfiedNotified = true;
    }

    @Override
    public void flashcardChanged() {
        flashcardChangedNotified = true;
    }

    @BeforeEach
    void setup(){
        KeyboardChangeNotifier keyboardChangeNotifier = new KeyboardChangeNotifierImp();
        FlashcardSatisfiedNotifier flashcardSatisfiedNotifier = new FlashcardSatisfiedNotifierImp();
        FlashcardChangeNotifier flashcardChangeNotifier = new FlashcardChangeNotifierImp();

        noteNameMode = new NoteNameModeStateImp(NoteNameMode.Off);
        keyboardState = new KeyboardStateImp();
        keyboardState.addKeyboardChangeNotifier(keyboardChangeNotifier);
        lowerLimit = new LimitStateImp(new Note(NoteName.C, 4));
        upperLimit = new LimitStateImp(new Note(NoteName.C, 5));
        randomNoteGenerator = new NoteGeneratorImp(lowerLimit, upperLimit);
        flashcardsImp = new FlashcardsImp(randomNoteGenerator);
        flashcardsImp.addFlashcardChangeNotifier(flashcardChangeNotifier);

        sightReadTrainer = new SightReadTrainerImp(keyboardState, flashcardsImp, noteNameMode, new ScoreImp());
        sightReadTrainer.addFlashcardSatisfiedNotifier(flashcardSatisfiedNotifier);
        sightReadTrainer.enable();

        keyboardChangeNotifier.addObserver(sightReadTrainer);
        flashcardSatisfiedNotifier.addObserver(this);
        flashcardChangeNotifier.addObserver(this);

        satisfiedNotified = false;
        flashcardChangedNotified = false;
    }

    @Test
    void trainerCanEvaluateSingleNotePress(){
        for(NoteSet topFlashcard : flashcardsImp.peekAtTopFlashcard()){
            for (Note flashcardNote : topFlashcard){
                Key rightKey = new Key(flashcardNote);
                keyboardState.keyPressed(rightKey);
            }
        }
        assertTrue(satisfiedNotified);
        assertTrue(flashcardChangedNotified);
    }

    @Test
    void trainerCanPickOutFlashcardNoteFromSuperSet(){
        for(NoteSet topFlashcard : flashcardsImp.peekAtTopFlashcard()){
            Key wrongKey = new Key(21);
            Key wrongKey2 = new Key(22);
            keyboardState.keyPressed(wrongKey);
            keyboardState.keyPressed(wrongKey2);

            for (Note flashcardNote : topFlashcard){
                Key rightKey = new Key(flashcardNote);
                keyboardState.keyPressed(rightKey);
                keyboardState.keyReleased(rightKey);
            }
        }
        assertTrue(satisfiedNotified);
        assertTrue(flashcardChangedNotified);
    }
}