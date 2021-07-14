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
        KeyboardChangeObserver, FlashcardSatisfiedObserver, FlashcardChangeObserver {
    private final KeyStateDrawable keyboardState;
    private final StaffModeDrawable staffMode;
    private final FlashcardDrawable flashcards;

    private final BufferedImage trebleImage = ImageLoaderImp.createTrebleImage();
    private final BufferedImage bassImage = ImageLoaderImp.createBassImage();
    private final BufferedImage noteImage = ImageLoaderImp.createNoteImage();
    private final BufferedImage naturalImage = ImageLoaderImp.createNaturalImage();
    private final BufferedImage sharpImage = ImageLoaderImp.createSharpImage();
    private final BufferedImage flatImage = ImageLoaderImp.createFlatImage();

    private boolean drawName = false;

    public GrandStaffRendererImp(KeyStateDrawable keyboardState, StaffModeDrawable staffMode, FlashcardDrawable flashcards){
        this.keyboardState = keyboardState;
        this.staffMode = staffMode;
        this.flashcards = flashcards;
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
        Thread thread = new Thread(flashcards);
        flashcards.setScrollableComponent(this);
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
        flashcards.draw(graphics2D, noteImage, sharpImage, naturalImage, flatImage, staffMode);
    }
}
