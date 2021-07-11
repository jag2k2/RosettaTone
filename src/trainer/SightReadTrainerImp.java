package trainer;

import music.*;
import notification.KeyboardChangeObserver;
import notification.RangeChangeObserver;
import uicomponents.renderer.RenderConstants;
import uicomponents.renderer.FlashcardNoteGetter;
import utility.NoteCollection;

public class SightReadTrainerImp implements FlashcardNoteGetter, RangeChangeObserver, KeyboardChangeObserver {
    static private final int targetCount = 8;

    private final NoteRangeLimits noteRangeLimits;
    private final KeyboardEvaluator keyboardEvaluator;
    private final NoteCollectionList flashcardList;
    private final FlashcardChangeNotifier flashcardChangeNotifier;

    private boolean satisfied = false;

    public SightReadTrainerImp(NoteRangeLimits noteRangeLimits, KeyboardEvaluator keyboardEvaluator, FlashcardChangeNotifier flashcardChangeNotifier){
        this.noteRangeLimits = noteRangeLimits;
        this.keyboardEvaluator = keyboardEvaluator;
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
        flashcardChangeNotifier.notifyFlashcardChanged();
    }

    @Override
    public void notifyKeyboardChanged() {
        for (NoteCollection currentTarget : flashcardList.getFirstItem()){
            if(keyboardEvaluator.contains(currentTarget)){
                satisfied = true;
                flashcardChangeNotifier.notifyFlashcardSatisfied();
            }
            if(!keyboardEvaluator.contains(currentTarget) && satisfied){
                satisfied = false;
                flashcardList.removeFirstItem();
                addNewFlashcard();
                flashcardChangeNotifier.notifyFlashcardChanged();
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

        Note randomNote = RenderConstants.getRandomNote(lowerNote, upperNote);
        NoteCollection noteTarget = new NoteCollectionImp();
        noteTarget.add(randomNote);
        flashcardList.add(noteTarget);
    }

}
