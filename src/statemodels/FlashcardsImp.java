package statemodels;

import collections.NoteSetImp;
import imageprocessing.StaffImage;
import music.Note;
import notification.LimitChangeObserver;
import trainer.FlashcardChangeNotifier;
import trainer.FlashcardGenerator;
import trainer.RandomNoteGenerator;
import uicomponents.renderer.NoteNameDrawable;
import uicomponents.renderer.FlashcardDrawable;
import uicomponents.renderer.StaffModeDrawable;
import uicomponents.renderer.records.NoteImages;
import uicomponents.renderer.records.RenderConstants;
import utility.Maybe;
import utility.NoteSet;
import utility.NoteSetList;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public class FlashcardsImp implements NoteSetList, FlashcardGenerator, FlashcardDrawable, LimitChangeObserver {
    private final RandomNoteGenerator randomNoteGenerator;
    private final List<NoteSet> flashcardList;

    private Maybe<FlashcardChangeNotifier> flashcardChangeNotifier = new Maybe<>();
    private Maybe<JComponent> flashcardDisplay = new Maybe<>();

    private int xTraveled = RenderConstants.noteXSpacing;

    public FlashcardsImp(RandomNoteGenerator randomNoteGenerator){
        this.randomNoteGenerator = randomNoteGenerator;
        this.flashcardList = new ArrayList<>();
    }

    public void addFlashcardChangeNotifier(FlashcardChangeNotifier flashcardChangeNotifier){
        this.flashcardChangeNotifier = new Maybe<>(flashcardChangeNotifier);
    }

    @Override
    public void limitChanged() {
        int flashcardCount = RenderConstants.flashcardCount;
        generateAllNewFlashcards(flashcardCount);
    }

    @Override
    public Maybe<NoteSet> peekAtTopFlashcard() {
        if (flashcardList.size() > 0){
            return new Maybe<>(flashcardList.get(0));
        }
        return new Maybe<>();
    }

    @Override
    public void removeTopFlashcard() {
        if (flashcardList.size() > 0){
            flashcardList.remove(0);
        }
    }

    @Override
    public void addNewGeneratedFlashcard() {
        Note randomNote = randomNoteGenerator.generateSingleNote();
        NoteSet flashcard = new NoteSetImp();
        flashcard.add(randomNote);
        flashcardList.add(flashcard);
        for (FlashcardChangeNotifier notifier : flashcardChangeNotifier){
            notifier.notifyFlashcardChanged();
        }
    }

    @Override
    public void generateAllNewFlashcards(int count) {
        flashcardList.clear();

        for (int i = 0; i < count; i++) {
            addNewGeneratedFlashcard();
        }
    }

    @Override
    public void add(NoteSet flashcard) {
        flashcardList.add(flashcard);
    }

    @Override
    public int size() {
        return flashcardList.size();
    }

    @Override
    public void draw(Graphics2D graphics2D, NoteImages noteImages, StaffModeDrawable staffMode, NoteNameDrawable noteNameMode) {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        int i = 0;
        for (NoteSet flashcard : flashcardList){
            int noteX = RenderConstants.getNoteXOffset(i) + RenderConstants.noteXSpacing - xTraveled;
            for (Note note : flashcard){
                int lineNumber = note.getLineNumber();
                boolean drawNoteName = noteNameMode.isEnabled(i);
                note.draw(graphics2D, noteImages, flashcard,  noteX, staffMode.getLedgerLines(lineNumber), drawNoteName);
            }
            i++;
        }
    }

    @Override
    public void setScrollableComponent(JComponent flashcardDisplay) {
        this.flashcardDisplay = new Maybe<>(flashcardDisplay);
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
