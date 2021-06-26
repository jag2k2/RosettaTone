package uicomponents.renderer;

import notification.StaffChangeObserver;
import statemodels.NoteState;

import javax.swing.*;
import java.awt.*;

public class NoteTextRenderer implements StaffChangeObserver {
    private final JPanel panel;
    private final JTextArea textArea;
    private final NoteState noteState;

    public NoteTextRenderer(NoteState noteState){
        this.noteState = noteState;
        this.textArea = new JTextArea();
        this.panel = new JPanel(new BorderLayout());
        this.panel.add(BorderLayout.CENTER, textArea);
    }

    public JPanel getPanel(){
        return panel;
    }

    @Override
    public void update() {
        textArea.setText(noteState.getActiveNotes().toString());
    }
}
