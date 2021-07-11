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
    private final NoteCollectionList flashcardList = new NoteCollectionListImp();
    private final FlashcardSatisfiedNotifier flashcardSatisfiedNotifier;
    private final FlashcardChangeNotifier flashcardChangeNotifier;

    private boolean satisfied = false;

    public SightReadTrainerImp(NoteRangeLimits noteRangeLimits, KeyboardEvaluator keyboardEvaluator,
                               FlashcardSatisfiedNotifier flashcardSatisfiedNotifier, FlashcardChangeNotifier flashcardChangeNotifier){
        this.noteRangeLimits = noteRangeLimits;
        this.keyboardEvaluator = keyboardEvaluator;
        this.flashcardSatisfiedNotifier = flashcardSatisfiedNotifier;
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
                flashcardSatisfiedNotifier.notifyFlashcardSatisfied();
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
        Note randomNote = noteRangeLimits.generateRandomNote();
        NoteCollection noteTarget = new NoteCollectionImp();
        noteTarget.add(randomNote);
        flashcardList.add(noteTarget);
    }

}
