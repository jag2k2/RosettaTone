package uicomponents.renderer;

import notification.StaffChangeObserver;
import statemodels.NoteState;
import uicomponents.staffselector.StaffModeHolder;

import java.awt.*;

public class GrandStaffRendererImp extends Component implements StaffChangeObserver {

    private final NoteState noteState;
    private final StaffModeHolder staffModeHolder;

    public GrandStaffRendererImp(NoteState noteState, StaffModeHolder staffModeHolder){
        this.noteState = noteState;
        this.staffModeHolder = staffModeHolder;
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
        noteDrawer.drawEnabledStaffs(staffModeHolder);
        noteDrawer.drawNotes(noteState.getActiveNotes(), staffModeHolder);
    }


}
