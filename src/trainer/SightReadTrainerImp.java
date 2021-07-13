package trainer;

import collections.NoteCollectionImp;
import collections.NoteCollectionListImp;
import music.*;
import notification.KeyboardChangeObserver;
import notification.LimitChangeObserver;
import uicomponents.renderer.FlashcardNoteGetter;
import utility.NoteCollection;

public class SightReadTrainerImp implements FlashcardNoteGetter, LimitChangeObserver, KeyboardChangeObserver {
    static private final int targetCount = 8;

    private final RandomNoteGenerator randomNoteGenerator;
    //private final KeyboardEvaluator keyboardEvaluator;
    private final NoteCollectionList flashcardList = new NoteCollectionListImp();
    private final FlashcardSatisfiedNotifier flashcardSatisfiedNotifier;
    private final FlashcardChangeNotifier flashcardChangeNotifier;

    private boolean satisfied = false;

    public SightReadTrainerImp(RandomNoteGenerator randomNoteGenerator, FlashcardSatisfiedNotifier flashcardSatisfiedNotifier, FlashcardChangeNotifier flashcardChangeNotifier){
        this.randomNoteGenerator = randomNoteGenerator;
        //this.keyboardEvaluator = keyboardEvaluator;
        this.flashcardSatisfiedNotifier = flashcardSatisfiedNotifier;
        this.flashcardChangeNotifier = flashcardChangeNotifier;
        generateAllNewFlashcards();
    }

    @Override
    public NoteCollectionList getFlashcardNotes() {
        return flashcardList;
    }

    @Override
    public void limitChanged() {
        generateAllNewFlashcards();
        flashcardChangeNotifier.notifyFlashcardChanged();
    }

    @Override
    public void KeyboardChanged() {
        /*for (NoteCollection currentFlashcard : flashcardList.getFirstItem()){
            if(keyboardEvaluator.contains(currentFlashcard)){
                satisfied = true;
                flashcardSatisfiedNotifier.notifyFlashcardSatisfied();
            }
            if(!keyboardEvaluator.contains(currentFlashcard) && satisfied){
                satisfied = false;
                flashcardList.removeFirstItem();
                addNewFlashcard();
                flashcardChangeNotifier.notifyFlashcardChanged();
            }
        }*/
    }

    protected void generateAllNewFlashcards(
    ){
        flashcardList.clear();

        for (int i = 0; i < targetCount; i++) {
            addNewFlashcard();
        }
    }

    protected void addNewFlashcard(){
        Note randomNote = randomNoteGenerator.generateSingleNote();
        NoteCollection noteTarget = new NoteCollectionImp();
        noteTarget.add(randomNote);
        flashcardList.add(noteTarget);
    }

}
