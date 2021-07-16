package statemodels;

import notification.NoteNameModeChangeNotifierImp;
import notification.NoteNameModeChangeObserver;
import notification.FlashcardChangeNotifierImp;
import notification.FlashcardSatisfiedNotifierImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trainer.FlashcardChangeNotifier;
import trainer.FlashcardSatisfiedNotifier;
import uicomponents.notenamemode.NoteNameMode;

import static org.junit.jupiter.api.Assertions.*;

class NoteNameModeStateImpTest implements NoteNameModeChangeObserver {
    private NoteNameModeStateImp noteNameModeStateImp;
    private FlashcardSatisfiedNotifier flashcardSatisfiedNotifier;
    private FlashcardChangeNotifier flashcardChangeNotifier;

    private boolean alphabetModeChanged = false;

    @Override
    public void alphabetModeChanged() {
        alphabetModeChanged = true;
    }

    @BeforeEach
    void setup(){
        NoteNameModeChangeNotifierImp alphabetModeChangeNotifierImp = new NoteNameModeChangeNotifierImp();
        flashcardSatisfiedNotifier = new FlashcardSatisfiedNotifierImp();
        flashcardChangeNotifier = new FlashcardChangeNotifierImp();

        noteNameModeStateImp = new NoteNameModeStateImp(NoteNameMode.Off);
        noteNameModeStateImp.addAlphabetModeChangeNotifier(alphabetModeChangeNotifierImp);

        alphabetModeChangeNotifierImp.addObserver(this);
        flashcardSatisfiedNotifier.addObserver(noteNameModeStateImp);
        flashcardChangeNotifier.addObserver(noteNameModeStateImp);

        alphabetModeChanged = false;
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
        assertTrue(alphabetModeChanged);
    }

    @Test
    void wontSetModeIfSame(){
        NoteNameModeStateImp expected = new NoteNameModeStateImp(NoteNameMode.Off);
        noteNameModeStateImp.setMode(NoteNameMode.Off);
        assertEquals(expected, noteNameModeStateImp);
        assertFalse(alphabetModeChanged);
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