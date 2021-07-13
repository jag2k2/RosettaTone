package uicomponents.renderer;

import notification.FlashcardSatisfiedObserver;
import notification.KeyboardChangeObserver;
import notification.ClefModeChangeObserver;
import notification.FlashcardChangeObserver;
import uicomponents.MusicDrawable;
import uicomponents.UIComponent;
import uicomponents.renderer.records.RenderConstants;

import javax.swing.*;
import java.awt.*;

public class GrandStaffRendererImp extends JComponent implements UIComponent, ClefModeChangeObserver,
        KeyboardChangeObserver, FlashcardSatisfiedObserver, FlashcardChangeObserver, Runnable {
    private final KeyboardStateNoteGetter keyboardStateNoteGetter;
    private final MusicDrawable clefMode;
    private final FlashcardNoteGetter flashcardNoteGetter;
    private final ImageLoader imageLoader;
    private int xTraveled = RenderConstants.noteXSpacing;
    private boolean drawName = false;

    public GrandStaffRendererImp(KeyboardStateNoteGetter keyboardStateNoteGetter, MusicDrawable clefMode, FlashcardNoteGetter flashcardNoteGetter, ImageLoader imageLoader){
        this.keyboardStateNoteGetter = keyboardStateNoteGetter;
        this.clefMode = clefMode;
        this.flashcardNoteGetter = flashcardNoteGetter;
        this.imageLoader = imageLoader;
    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel();
        panel.add(this);
        panel.setBackground(Color.WHITE);
        return panel;
    }

    @Override
    public void clefModeChanged() {
        repaint();
    }

    @Override
    public void KeyboardChanged() {
        repaint();
    }

    @Override
    public void flashcardSatisfied() {
        drawName = true;
    }

    @Override
    public void flashcardChanged() {
        drawName = false;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return RenderConstants.canvasSize;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D)g;
        NoteDrawer noteDrawer = new NoteDrawer(graphics2D, imageLoader);
        clefMode.draw(graphics2D);
        noteDrawer.drawKeyboardNotes(keyboardStateNoteGetter.getActiveNotes());
        noteDrawer.drawFlashcardNotes(flashcardNoteGetter.getFlashcardNotes(), xTraveled, drawName);

        /*keyboardState.draw(graphics2D);
        flashCards.draw(graphics2D);
         */
    }

    @Override
    public void run() {

        int deltaX = 5;
        int delay = 5;
        xTraveled = 0;

        long beforeTime, timeDiff, sleepTime;
        beforeTime = System.currentTimeMillis();

        while (xTraveled < RenderConstants.noteXSpacing){

            xTraveled += deltaX;
            repaint();

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
