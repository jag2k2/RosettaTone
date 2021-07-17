package uicomponents.renderer;

import imageprocessing.ImageFactory;
import imageprocessing.StaffImage;
import notification.NoteNameModeChangeObserver;
import notification.KeyboardChangeObserver;
import notification.StaffModeChangeObserver;
import notification.FlashcardChangeObserver;
import uicomponents.UIComponent;
import uicomponents.renderer.records.ClefImages;
import uicomponents.renderer.records.NoteImages;
import uicomponents.renderer.records.RenderConstants;

import javax.swing.*;
import java.awt.*;

public class GrandStaffRendererImp extends JComponent implements UIComponent, StaffModeChangeObserver,
        NoteNameModeChangeObserver, KeyboardChangeObserver, FlashcardChangeObserver {
    private final KeyStateDrawable keyboardState;
    private final StaffModeDrawable staffMode;
    private final FlashcardDrawable flashcards;
    private final NoteNameDrawable noteNameMode;
    private final ScoreDrawable scoreDrawable;

    private final ClefImages clefImages = ImageFactory.createClefImages();
    private final NoteImages noteImages = ImageFactory.createNoteImages();

    public GrandStaffRendererImp(KeyStateDrawable keyboardState, FlashcardDrawable flashcards,
                                 StaffModeDrawable staffMode, NoteNameDrawable noteNameMode, ScoreDrawable scoreDrawable){
        this.keyboardState = keyboardState;
        this.staffMode = staffMode;
        this.flashcards = flashcards;
        this.noteNameMode = noteNameMode;
        this.scoreDrawable = scoreDrawable;
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
    public void boardChangedWithKeyDown() {
        repaint();
    }

    @Override
    public void boardChangedWithKeyUp() {
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
        staffMode.draw(graphics2D, clefImages);
        keyboardState.draw(graphics2D, noteImages, staffMode);
        flashcards.draw(graphics2D, noteImages, staffMode, noteNameMode);
        scoreDrawable.draw(graphics2D);
    }
}
