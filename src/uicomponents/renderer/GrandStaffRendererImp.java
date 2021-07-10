package uicomponents.renderer;

import notification.KeyboardChangeObserver;
import notification.ModeChangeObserver;
import notification.FlashcardChangeObserver;
import statemodels.KeyboardState;
import trainer.SightReadTrainer;
import uicomponents.UIComponent;
import uicomponents.clefmode.ClefModeSelector;

import javax.swing.*;
import java.awt.*;

public class GrandStaffRendererImp extends JComponent implements UIComponent, ModeChangeObserver, KeyboardChangeObserver, FlashcardChangeObserver, Runnable {
    private final KeyboardState keyboardState;
    private final ClefModeSelector clefModeSelector;
    private final SightReadTrainer sightReadTrainer;
    private int xTraveled = RenderConstants.noteXSpacing;

    public GrandStaffRendererImp(KeyboardState keyboardState, ClefModeSelector clefModeSelector, SightReadTrainer sightReadTrainer){
        this.keyboardState = keyboardState;
        this.clefModeSelector = clefModeSelector;
        this.sightReadTrainer = sightReadTrainer;
    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel();
        panel.add(this);
        panel.setBackground(Color.WHITE);
        return panel;
    }

    @Override
    public void modeChanged() {
        repaint();
    }

    @Override
    public void keyboardChanged() {
        repaint();
    }

    @Override
    public void flashcardChanged() {
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
        NoteDrawer noteDrawer = new NoteDrawer(graphics2D, clefModeSelector);
        noteDrawer.drawEnabledStaffs();
        noteDrawer.drawKeyboardNotes(keyboardState.getActiveNotes());
        noteDrawer.drawFlashcardNotes(sightReadTrainer.getFlashcardNotes(), xTraveled);
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
