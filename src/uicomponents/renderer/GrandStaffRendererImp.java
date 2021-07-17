package uicomponents.renderer;

import imageprocessing.ImageFactory;
import imageprocessing.StaffImage;
import notification.NoteNameModeChangeObserver;
import notification.KeyboardChangeObserver;
import notification.StaffModeChangeObserver;
import notification.FlashcardChangeObserver;
import uicomponents.UIComponent;
import uicomponents.renderer.records.RenderConstants;

import javax.swing.*;
import java.awt.*;

public class GrandStaffRendererImp extends JComponent implements UIComponent, StaffModeChangeObserver,
        NoteNameModeChangeObserver, KeyboardChangeObserver, FlashcardChangeObserver {
    private final KeyStateDrawable keyboardState;
    private final StaffModeDrawable staffMode;
    private final FlashcardDrawable flashcards;
    private final NoteNameDrawable noteNameMode;

    private final StaffImage trebleImage = ImageFactory.createTrebleImage();
    private final StaffImage bassImage = ImageFactory.createBassImage();
    private final StaffImage noteImage = ImageFactory.createNoteImage();
    private final StaffImage naturalImage = ImageFactory.createNaturalImage();
    private final StaffImage sharpImage = ImageFactory.createSharpImage();
    private final StaffImage flatImage = ImageFactory.createFlatImage();

    public GrandStaffRendererImp(KeyStateDrawable keyboardState, FlashcardDrawable flashcards,
                                 StaffModeDrawable staffMode, NoteNameDrawable noteNameMode){
        this.keyboardState = keyboardState;
        this.staffMode = staffMode;
        this.flashcards = flashcards;
        this.noteNameMode = noteNameMode;
    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(boxLayout);
        panel.add(this);
        panel.setBackground(Color.WHITE);
        return panel;
    }

    @Override
    public void staffModeChanged() {
        repaint();
    }

    @Override
    public void alphabetModeChanged() {
        repaint();
    }

    @Override
    public void KeyboardChanged() {
        repaint();
    }

    @Override
    public void flashcardChanged() {
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
        flashcards.draw(graphics2D, noteImage, sharpImage, naturalImage, flatImage, staffMode, noteNameMode);
    }
}
