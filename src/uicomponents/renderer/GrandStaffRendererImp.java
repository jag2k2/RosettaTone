package uicomponents.renderer;

import notification.StaffChangeObserver;
import statemodels.NoteState;
import statemodels.StaffState;

import java.awt.*;

public class GrandStaffRendererImp extends Component implements StaffChangeObserver {

    private final NoteState noteState;
    private final StaffState staffState;

    public GrandStaffRendererImp(NoteState noteState, StaffState staffState){
        this.noteState = noteState;
        this.staffState = staffState;
    }

    @Override
    public void updateStaff() {
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
        noteDrawer.drawEnabledStaffs(staffState);
        noteDrawer.drawNotes(noteState.getActiveNotes(), staffState);
    }


}
