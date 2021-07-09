package trainer;

import music.*;
import notification.KeyboardChangeObserver;
import notification.FlashcardChangeNotifier;
import notification.RangeChangeObserver;
import statemodels.KeyboardState;
import statemodels.NoteRangeLimits;
import uicomponents.renderer.CanvasRender;

public class SightReadTrainerImp implements SightReadTrainer, RangeChangeObserver, KeyboardChangeObserver {
    static private final int targetCount = 8;

    private final NoteRangeLimits noteRangeLimits;
    private final KeyboardState keyboardState;
    private final NoteCollectionList flashcardList;
    private final FlashcardChangeNotifier flashcardChangeNotifier;

    public SightReadTrainerImp(NoteRangeLimits noteRangeLimits, KeyboardState keyboardState, FlashcardChangeNotifier flashcardChangeNotifier){
        this.noteRangeLimits = noteRangeLimits;
        this.keyboardState = keyboardState;
        this.flashcardList = new NoteCollectionListImp();
        this.flashcardChangeNotifier = flashcardChangeNotifier;
        generateAllNewFlashcards();
    }

    @Override
    public NoteCollectionList getFlashcardNotes() {
        return flashcardList;
    }

    @Override
    public void rangeChanged() {
        generateAllNewFlashcards();
        flashcardChangeNotifier.notifyObservers();
    }

    @Override
    public void keyboardChanged() {
        NoteCollection activeNotes = keyboardState.getActiveNotes();
        for (NoteCollection currentTarget : flashcardList.getFirstItem()){
            if(activeNotes.contains(currentTarget)){
                flashcardList.removeFirstItem();
                addNewFlashcard();
                flashcardChangeNotifier.notifyObservers();
            }
        }
    }

    protected void generateAllNewFlashcards(
    ){
        flashcardList.clear();

        for (int i = 0; i < targetCount; i++) {
            addNewFlashcard();
        }
    }

    protected void addNewFlashcard(){
        Note lowerNote = noteRangeLimits.getLowerLimitNote();
        Note upperNote = noteRangeLimits.getUpperLimitNote();

        Note randomNote = CanvasRender.getRandomNote(lowerNote, upperNote);
        NoteCollection noteTarget = new NoteCollectionImp();
        noteTarget.add(randomNote);
        flashcardList.add(noteTarget);
    }

}
