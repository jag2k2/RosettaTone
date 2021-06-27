package uicomponents.renderer;

import notification.StaffChangeObserver;
import statemodels.NoteState;
import uicomponents.UIComponent;

import javax.swing.*;
import java.awt.*;

public class NoteTextRenderer implements UIComponent, StaffChangeObserver {
    private final JTextArea textArea;
    private final NoteState noteState;

    public NoteTextRenderer(NoteState noteState){
        this.noteState = noteState;
        this.textArea = new JTextArea();

    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(BorderLayout.CENTER, textArea);
        return panel;
    }

    @Override
    public void updateStaff() {
        textArea.setText(noteState.getActiveNotes().toString());
    }
}
