package uicomponents.renderer;

import notification.StaffChangeObserver;
import statemodels.KeyboardState;
import uicomponents.staffselector.StaffModeHolder;

import java.awt.*;

public class GrandStaffRendererImp extends Component implements StaffChangeObserver {

    private final KeyboardState keyboardState;
    private final StaffModeHolder staffModeHolder;

    public GrandStaffRendererImp(KeyboardState keyboardState, StaffModeHolder staffModeHolder){
        this.keyboardState = keyboardState;
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
        noteDrawer.drawNotes(keyboardState.getActiveNotes(), staffModeHolder);
    }


}
