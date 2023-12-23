package com.jag2k2.statemodels;

import com.jag2k2.notification.ConfigChangeNotifierImp;
import com.jag2k2.notification.ConfigChangeObserver;
import com.jag2k2.notification.FlashcardChangeNotifierImp;
import com.jag2k2.notification.FlashcardSatisfiedNotifierImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.jag2k2.trainer.FlashcardChangeNotifier;
import com.jag2k2.trainer.FlashcardSatisfiedNotifier;
import com.jag2k2.uicomponents.notenamemode.NoteNameMode;

import static org.junit.jupiter.api.Assertions.*;

class NoteNameModeStateImpTest implements ConfigChangeObserver {
    private NoteNameModeStateImp noteNameModeStateImp;
    private FlashcardSatisfiedNotifier flashcardSatisfiedNotifier;
    private FlashcardChangeNotifier flashcardChangeNotifier;

    private boolean configChanged = false;

    @Override
    public void configChanged() {
        configChanged = true;
    }

    @BeforeEach
    void setup(){
        ConfigChangeNotifierImp alphabetModeChangeNotifierImp = new ConfigChangeNotifierImp();
        flashcardSatisfiedNotifier = new FlashcardSatisfiedNotifierImp();
        flashcardChangeNotifier = new FlashcardChangeNotifierImp();

        noteNameModeStateImp = new NoteNameModeStateImp(NoteNameMode.Off);
        noteNameModeStateImp.addConfigChangeNotifier(alphabetModeChangeNotifierImp);

        alphabetModeChangeNotifierImp.addObserver(this);
        flashcardSatisfiedNotifier.addObserver(noteNameModeStateImp);
        flashcardChangeNotifier.addObserver(noteNameModeStateImp);

        configChanged = false;
    }

    @Test
    void canCheckEquality() {
        NoteNameModeStateImp expected = new NoteNameModeStateImp(NoteNameMode.Off);
        assertEquals(expected, noteNameModeStateImp);

        expected = new NoteNameModeStateImp(NoteNameMode.Always);
        assertNotEquals(expected, noteNameModeStateImp);
    }

    @Test
    void canDisplayAsString() {
        assertEquals("Off", noteNameModeStateImp.toString());
    }

    @Test
    void setMode() {
        NoteNameModeStateImp expected = new NoteNameModeStateImp(NoteNameMode.Correct);
        noteNameModeStateImp.setMode(NoteNameMode.Correct);
        assertEquals(expected, noteNameModeStateImp);
        assertTrue(configChanged);
    }

    @Test
    void wontSetModeIfSame(){
        NoteNameModeStateImp expected = new NoteNameModeStateImp(NoteNameMode.Off);
        noteNameModeStateImp.setMode(NoteNameMode.Off);
        assertEquals(expected, noteNameModeStateImp);
        assertFalse(configChanged);
    }

    @Test
    void canCheckIfEnabled(){
        noteNameModeStateImp.setMode(NoteNameMode.Off);
        assertFalse(noteNameModeStateImp.isEnabled(0));

        noteNameModeStateImp.setMode(NoteNameMode.Always);
        assertTrue(noteNameModeStateImp.isEnabled(0));
        assertTrue(noteNameModeStateImp.isEnabled(1));

        noteNameModeStateImp.setMode(NoteNameMode.Correct);
        assertFalse(noteNameModeStateImp.isEnabled(0));
        flashcardSatisfiedNotifier.notifyFlashcardSatisfied();
        assertTrue(noteNameModeStateImp.isEnabled(0));
        assertFalse(noteNameModeStateImp.isEnabled(1));
        flashcardChangeNotifier.notifyFlashcardChanged();
        assertFalse(noteNameModeStateImp.isEnabled(0));
    }

    @Test
    void delayAdvance(){
        noteNameModeStateImp.setMode(NoteNameMode.Off);
        assertTrue(noteNameModeStateImp.immediatelyAdvance());

        noteNameModeStateImp.setMode(NoteNameMode.Always);
        assertTrue(noteNameModeStateImp.immediatelyAdvance());
    }

    @Test
    void correctModeDelaysAdvance(){
        noteNameModeStateImp.setMode(NoteNameMode.Correct);
        assertFalse(noteNameModeStateImp.readyToAdvance());

        flashcardSatisfiedNotifier.notifyFlashcardSatisfied();
        assertFalse(noteNameModeStateImp.immediatelyAdvance());
        assertTrue(noteNameModeStateImp.readyToAdvance());
    }
}