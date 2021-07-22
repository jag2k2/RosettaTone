package uicomponents.renderer.grandstaff;

import imageprocessing.ImageFactory;
import music.Staff;
import notification.*;
import uicomponents.UIComponent;
import uicomponents.renderer.records.ClefImages;
import uicomponents.renderer.records.NoteImages;
import uicomponents.renderer.records.RenderConstants;

import javax.swing.*;
import java.awt.*;

public class GrandRendererImp extends JComponent implements UIComponent, ConfigChangeObserver, KeyboardChangeObserver,
        FlashcardChangeObserver {
    private final KeyStateDrawable keyboardState;
    private final StaffModeEvaluator staffMode;
    private final FlashcardDrawable flashcards;
    private final NoteNameDrawable noteNameMode;
    private final ScoreDrawable scoreDrawable;
    private final Enableable trainer;

    private final ClefImages clefImages = ImageFactory.createClefImages();
    private final NoteImages noteImages = ImageFactory.createNoteImages();

    private final StaffDrawable trebleStaff = new Staff(RenderConstants.trebleStaff);
    private final StaffDrawable bassStaff = new Staff(RenderConstants.bassStaff);

    public GrandRendererImp(KeyStateDrawable keyboardState, FlashcardDrawable flashcards,
                            StaffModeEvaluator staffMode, NoteNameDrawable noteNameMode, ScoreDrawable scoreDrawable, Enableable trainer){
        this.keyboardState = keyboardState;
        this.staffMode = staffMode;
        this.flashcards = flashcards;
        this.noteNameMode = noteNameMode;
        this.scoreDrawable = scoreDrawable;
        this.trainer = trainer;
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
    public void configChanged() {
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
        if (staffMode.isTrebleOnly() || staffMode.isGrand()){
            trebleStaff.draw(graphics2D, clefImages.trebleImage);
        }
        if (staffMode.isBassOnly() || staffMode.isGrand()){
            bassStaff.draw(graphics2D, clefImages.bassImage);
        }
        keyboardState.draw(graphics2D, noteImages, staffMode);
        if (trainer.isEnabled()){
            flashcards.draw(graphics2D, noteImages, staffMode, noteNameMode);
            scoreDrawable.draw(graphics2D);
        }
    }
}
