package trainer;

import collections.NoteCollectionImp;
import collections.NoteCollectionListImp;
import music.*;
import notification.KeyboardChangeObserver;
import notification.LimitChangeObserver;
import uicomponents.renderer.FlashcardDrawable;
import uicomponents.renderer.StaffModeDrawable;
import uicomponents.renderer.records.RenderConstants;
import utility.NoteCollection;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SightReadTrainerImp implements FlashcardDrawable, LimitChangeObserver, KeyboardChangeObserver {
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

    @Override
    public void draw(Graphics2D graphics2D, BufferedImage noteImage, BufferedImage sharpImage, BufferedImage naturalImage, BufferedImage flatImage, StaffModeDrawable staffMode, int xTraveled) {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        int i = 0;
        for (NoteCollection flashcard : flashcardList){
            i++;
            int noteX = RenderConstants.getNoteXOffset(i) + RenderConstants.noteXSpacing - xTraveled;
            for (Note note : flashcard){
                int lineNumber = RenderConstants.getLineNumber(note);
                note.draw(graphics2D, noteImage, sharpImage, naturalImage, flatImage, flashcard,  noteX, staffMode.getLedgerLines(lineNumber));
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
        Note randomNote = randomNoteGenerator.generateSingleNote();
        NoteCollection noteTarget = new NoteCollectionImp();
        noteTarget.add(randomNote);
        flashcardList.add(noteTarget);
    }

}
