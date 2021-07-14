package trainer;

import collections.NoteCollectionImp;
import music.*;
import notification.KeyboardChangeObserver;
import notification.LimitChangeObserver;
import uicomponents.renderer.FlashcardDrawable;
import uicomponents.renderer.StaffModeDrawable;
import uicomponents.renderer.records.RenderConstants;
import utility.Maybe;
import utility.NoteCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public class SightReadTrainerImp implements FlashcardDrawable, LimitChangeObserver, KeyboardChangeObserver {
    static private final int targetCount = 8;

    private final KeyStateEvaluator keyboardState;
    private final RandomNoteGenerator randomNoteGenerator;
    private final List<NoteCollection> flashcardList;
    private final FlashcardSatisfiedNotifier flashcardSatisfiedNotifier;
    private final FlashcardChangeNotifier flashcardChangeNotifier;

    private Maybe<JComponent> flashcardDisplay = new Maybe<JComponent>();
    private boolean satisfied = false;
    private int xTraveled = RenderConstants.noteXSpacing;

    public SightReadTrainerImp(KeyStateEvaluator keyboardState, RandomNoteGenerator randomNoteGenerator, FlashcardSatisfiedNotifier flashcardSatisfiedNotifier, FlashcardChangeNotifier flashcardChangeNotifier){
        this.keyboardState = keyboardState;
        this.randomNoteGenerator = randomNoteGenerator;
        this.flashcardList  = new ArrayList<>();
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
        for (NoteCollection currentFlashcard : getTopFlashcard()){
            if(keyboardState.containsAll(currentFlashcard)){
                satisfied = true;
                flashcardSatisfiedNotifier.notifyFlashcardSatisfied();
            }
            if(!keyboardState.containsAll(currentFlashcard) && satisfied){
                satisfied = false;
                if (flashcardList.size() > 0)
                    flashcardList.remove(0);
                addNewFlashcard();
                flashcardChangeNotifier.notifyFlashcardChanged();
            }
        }
    }

    @Override
    public void setScrollableComponent(JComponent flashcardDisplay) {
        this.flashcardDisplay = new Maybe<>(flashcardDisplay);
    }

    @Override
    public void draw(Graphics2D graphics2D, BufferedImage noteImage, BufferedImage sharpImage, BufferedImage naturalImage, BufferedImage flatImage, StaffModeDrawable staffMode) {
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

    protected Maybe<NoteCollection> getTopFlashcard(){
        if (flashcardList.size() > 0){
            return new Maybe<>(flashcardList.get(0));
        }
        return new Maybe<>();
    }

    @Override
    public void run() {

        int deltaX = 5;
        int delay = 5;
        xTraveled = 0;

        for (JComponent component : flashcardDisplay) {
            long beforeTime, timeDiff, sleepTime;
            beforeTime = System.currentTimeMillis();

            while (xTraveled < RenderConstants.noteXSpacing) {

                xTraveled += deltaX;
                component.repaint();

                timeDiff = System.currentTimeMillis() - beforeTime;
                sleepTime = delay - timeDiff;

                if (sleepTime < 0) {
                    sleepTime = 2;
                }

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                beforeTime = System.currentTimeMillis();
            }
        }
    }
}
