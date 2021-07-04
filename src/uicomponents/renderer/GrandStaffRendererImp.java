package uicomponents.renderer;

import notification.KeyboardChangeObserver;
import notification.ModeChangeObserver;
import notification.NoteTargetChangeObserver;
import statemodels.KeyboardState;
import trainer.SightReadTrainer;
import uicomponents.staffselector.ModeSelector;

import java.awt.*;

public class GrandStaffRendererImp extends Component implements ModeChangeObserver, KeyboardChangeObserver, NoteTargetChangeObserver {
    private final KeyboardState keyboardState;
    private final ModeSelector modeSelector;
    private final SightReadTrainer sightReadTrainer;

    public GrandStaffRendererImp(KeyboardState keyboardState, ModeSelector modeSelector, SightReadTrainer sightReadTrainer){
        this.keyboardState = keyboardState;
        this.modeSelector = modeSelector;
        this.sightReadTrainer = sightReadTrainer;
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
    public void noteTargetChanged() {
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return CanvasRender.getCanvasSize();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        NoteDrawer noteDrawer = new NoteDrawer(graphics2D);
        noteDrawer.paintBackground();
        noteDrawer.drawEnabledStaffs(modeSelector);
        noteDrawer.drawNotes(keyboardState.getActiveNotes(), modeSelector);
        noteDrawer.drawTargets(sightReadTrainer.getNoteTargets(), modeSelector);
    }
}
