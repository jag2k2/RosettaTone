package com.jag2k2.trainer;

import com.jag2k2.instrument.Key;
import com.jag2k2.music.Note;
import com.jag2k2.music.NoteName;
import com.jag2k2.notification.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.jag2k2.statemodels.*;
import com.jag2k2.statemodels.limitstate.LimitStateImp;
import com.jag2k2.trainer.randomnotegenerator.LineNumerable;
import com.jag2k2.trainer.randomnotegenerator.RandomNoteGeneratorImp;
import com.jag2k2.uicomponents.notenamemode.NoteNameMode;
import com.jag2k2.utility.NoteSet;

import static org.junit.jupiter.api.Assertions.*;

class SightReadTrainerImpTest implements FlashcardSatisfiedObserver, FlashcardChangeObserver {
    private NoteNameModeStateImp noteNameMode;
    private KeyboardStateImp keyboardState;
    private LineNumerable lowerLineNumerable;
    private LineNumerable upperLineNumerable;
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
        lowerLineNumerable = new LimitStateImp(new Note(NoteName.C, 4));
        upperLineNumerable = new LimitStateImp(new Note(NoteName.C, 5));
        randomNoteGenerator = new RandomNoteGeneratorImp(lowerLineNumerable, upperLineNumerable);
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