package uicomponents.renderer;

import imageprocessing.ImageLoaderImp;
import notification.FlashcardSatisfiedObserver;
import notification.KeyboardChangeObserver;
import notification.ClefModeChangeObserver;
import notification.FlashcardChangeObserver;
import uicomponents.UIComponent;
import uicomponents.renderer.records.RenderConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GrandStaffRendererImp extends JComponent implements UIComponent, ClefModeChangeObserver,
        KeyboardChangeObserver, FlashcardSatisfiedObserver, FlashcardChangeObserver, Runnable {
    private final KeyStateDrawable keyboardState;
    private final StaffModeDrawable staffMode;
    private final FlashcardNoteGetter flashcardNoteGetter;
    private final BufferedImage trebleImage = ImageLoaderImp.createTrebleImage();
    private final BufferedImage bassImage = ImageLoaderImp.createBassImage();
    private final BufferedImage noteImage = ImageLoaderImp.createNoteImage();
    private final BufferedImage naturalImage = ImageLoaderImp.createNaturalImage();
    private final BufferedImage sharpImage = ImageLoaderImp.createSharpImage();
    private final BufferedImage flatImage = ImageLoaderImp.createFlatImage();

    private int xTraveled = RenderConstants.noteXSpacing;
    private boolean drawName = false;

    public GrandStaffRendererImp(KeyStateDrawable keyboardState, StaffModeDrawable staffMode, FlashcardNoteGetter flashcardNoteGetter){
        this.keyboardState = keyboardState;
        this.staffMode = staffMode;
        this.flashcardNoteGetter = flashcardNoteGetter;
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
        staffMode.draw(graphics2D, trebleImage, bassImage);
        keyboardState.draw(graphics2D, noteImage, sharpImage, naturalImage, flatImage, staffMode);
        //noteDrawer.drawFlashcardNotes(flashcardNoteGetter.getFlashcardNotes(), xTraveled, drawName);

        /*keyboardState.getActiveNotes().draw(graphics2D);
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
