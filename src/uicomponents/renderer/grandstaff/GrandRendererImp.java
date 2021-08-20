package uicomponents.renderer.grandstaff;

import imageprocessing.ImageFactory;
import music.staff.Staff;
import notification.*;
import uicomponents.renderer.records.ClefImages;
import uicomponents.renderer.records.NoteImages;
import uicomponents.renderer.records.RenderConstants;

import javax.swing.*;
import java.awt.*;

public class GrandRendererImp extends JComponent implements FlashcardChangeObserver {
    private final KeyStateDrawable keyboardState;
    private final StaffModeEvaluator staffMode;
    private final FlashcardDrawable flashcards;
    private final NoteNameDrawable noteNameMode;
    private final ScoreDrawable score;
    private final CanCheckEnabled trainer;

    private final ClefImages clefImages = ImageFactory.createClefImages();
    private final NoteImages noteImages = ImageFactory.createNoteImages();

    private final StaffDrawable trebleStaff = new Staff(RenderConstants.trebleStaff);
    private final StaffDrawable bassStaff = new Staff(RenderConstants.bassStaff);

    public GrandRendererImp(KeyStateDrawable keyboardState, FlashcardDrawable flashcards,
                            StaffModeEvaluator staffMode, NoteNameDrawable noteNameMode, ScoreDrawable score, CanCheckEnabled trainer){
        this.keyboardState = keyboardState;
        this.staffMode = staffMode;
        this.flashcards = flashcards;
        this.noteNameMode = noteNameMode;
        this.score = score;
        this.trainer = trainer;
    }

    @Override
    public Dimension getPreferredSize() {
        return RenderConstants.canvasSize;
    }

    @Override
    public void flashcardChanged() {
        Thread thread = new Thread(flashcards);
        flashcards.setScrollableComponent(this);
        thread.start();
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
            score.draw(graphics2D);
        }
    }
}
