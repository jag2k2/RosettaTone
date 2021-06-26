package uicomponents.renderer;

import notification.StaffChangeObserver;
import music.*;
import statemodels.NoteState;

import java.awt.*;

public class GrandStaffRendererImp extends Component implements StaffChangeObserver {

    private final NoteState noteState;
    private final StaffSelection staffSelection;

    public GrandStaffRendererImp(NoteState noteState, StaffSelection staffSelection){
        this.noteState = noteState;
        this.staffSelection = staffSelection;
    }

    @Override
    public void update() {
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return CanvasRender.getCanvasSize();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        NoteRender noteRender = new NoteRender(graphics2D);
        noteRender.paintBackground();
        noteRender.drawEnabledStaffs(staffSelection);
        noteRender.drawNotes(noteState.getActiveNotes(), staffSelection);
    }
}
