package statemodels;

import notification.ConfigChangeNotifierImp;
import notification.ConfigChangeObserver;
import notification.FlashcardChangeNotifierImp;
import notification.FlashcardSatisfiedNotifierImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trainer.FlashcardChangeNotifier;
import trainer.FlashcardSatisfiedNotifier;
import uicomponents.notenamemode.NoteNameMode;

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
        noteNameModeStateImp.update(NoteNameMode.Correct);
        assertEquals(expected, noteNameModeStateImp);
        assertTrue(configChanged);
    }

    @Test
    void wontSetModeIfSame(){
        NoteNameModeStateImp expected = new NoteNameModeStateImp(NoteNameMode.Off);
        noteNameModeStateImp.update(NoteNameMode.Off);
        assertEquals(expected, noteNameModeStateImp);
        assertFalse(configChanged);
    }

    @Test
    void canCheckIfEnabled(){
        noteNameModeStateImp.update(NoteNameMode.Off);
        assertFalse(noteNameModeStateImp.isEnabled(0));

        noteNameModeStateImp.update(NoteNameMode.Always);
        assertTrue(noteNameModeStateImp.isEnabled(0));
        assertTrue(noteNameModeStateImp.isEnabled(1));

        noteNameModeStateImp.update(NoteNameMode.Correct);
        assertFalse(noteNameModeStateImp.isEnabled(0));
        flashcardSatisfiedNotifier.notifyFlashcardSatisfied();
        assertTrue(noteNameModeStateImp.isEnabled(0));
        assertFalse(noteNameModeStateImp.isEnabled(1));
        flashcardChangeNotifier.notifyFlashcardChanged();
        assertFalse(noteNameModeStateImp.isEnabled(0));
    }

    @Test
    void delayAdvance(){
        noteNameModeStateImp.update(NoteNameMode.Off);
        assertTrue(noteNameModeStateImp.immediatelyAdvance());

        noteNameModeStateImp.update(NoteNameMode.Always);
        assertTrue(noteNameModeStateImp.immediatelyAdvance());
    }

    @Test
    void correctModeDelaysAdvance(){
        noteNameModeStateImp.update(NoteNameMode.Correct);
        assertFalse(noteNameModeStateImp.readyToAdvance());

        flashcardSatisfiedNotifier.notifyFlashcardSatisfied();
        assertFalse(noteNameModeStateImp.immediatelyAdvance());
        assertTrue(noteNameModeStateImp.readyToAdvance());
    }
}