package uicomponents.renderer;

import notification.StaffChangeObserver;
import statemodels.NoteState;

import javax.swing.*;
import java.awt.*;

public class NoteTextRenderer implements StaffChangeObserver {
    private final JTextArea textArea;
    private final NoteState noteState;

    public NoteTextRenderer(NoteState noteState){
        this.noteState = noteState;
        this.textArea = new JTextArea();

    }

    public JPanel getPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(BorderLayout.CENTER, textArea);
        return panel;
    }

    @Override
    public void updateStaff() {
        textArea.setText(noteState.getActiveNotes().toString());
    }
}
