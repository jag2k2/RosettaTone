package uicomponents.renderer;

import notification.StaffChangeObserver;
import music.*;

import java.awt.*;
import javax.swing.*;

public class GrandStaffRendererImp extends Component implements StaffChangeObserver {

    private final NoteState noteState;
    private final StaffSelection staffSelection;
    private final JTextArea textArea;

    public GrandStaffRendererImp(NoteState noteState, StaffSelection staffSelection, JTextArea texArea){
        this.noteState = noteState;
        this.staffSelection = staffSelection;
        this.textArea = texArea;
    }

    @Override
    public void update() {
        textArea.setText(noteState.getActiveNotes().toString());
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return CanvasRender.getCanvasSize();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        ImageRender imageRender = new ImageRender(graphics2D);
        imageRender.paintBackground();
        imageRender.drawEnabledStaffs(staffSelection);
        imageRender.drawNotes(noteState.getActiveNotes(), staffSelection);
    }
}
